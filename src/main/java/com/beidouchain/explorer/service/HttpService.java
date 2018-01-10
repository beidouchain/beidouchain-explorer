package com.beidouchain.explorer.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidouchain.explorer.config.RPCServerConfig;
import com.beidouchain.explorer.entity.BeidouChainRPCParams;
import com.beidouchain.explorer.entity.BeidouChainRPCResult;
import com.beidouchain.explorer.exception.BeidouchainException;
import com.beidouchain.explorer.utils.JsonMapper;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;

@Service
public class HttpService {

	@Autowired
	private RPCServerConfig config;

	private final MediaType JSON =MediaType.parse("application/json; charset=utf-8");
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	private OkHttpClient client = null;

	private BeidouChainRPCResult beidouChainRPCResult = null;
	
	private void init() {
		client = new OkHttpClient.Builder().authenticator(new Authenticator() {

			@Override
			public Request authenticate(Route route, Response response) throws IOException {

				if (response.request().header("Authorization") != null) {
					return null; // 不重复认证
				}
				String credential = Credentials.basic(config.getRpcUser(), config.getRpcPassword());
				return response.request().newBuilder().header("Authorization", credential).build();
			}
		})
				.connectTimeout(3, TimeUnit.SECONDS)
				.readTimeout(3, TimeUnit.SECONDS)
				.writeTimeout(3, TimeUnit.SECONDS)
				.build();
	}
	
	public Object execute(String method,List<Object> params) throws BeidouchainException {
		
		synchronized (HttpService.class) {
			if(client == null){
				System.out.println("init---------------------");
				init();		
			}
		}	
		
		BeidouChainRPCParams paramObject = new BeidouChainRPCParams();
		paramObject.setMethod(method);
		paramObject.setParams(params);
		String paramToJson = JsonMapper.getInstance().toJson(paramObject);
		
		//logger.info("paramToJson:" + paramToJson);
		
		RequestBody body = RequestBody.create(JSON, paramToJson);
		final Request request = new Request.Builder().url("http://" + config.getRpcIp()+":" + config.getRpcPort()).post(body).build();
		try(Response response = client.newCall(request).execute()) {
			
			String result = response.body().string();
			
			//logger.info("result : " +  result);
			
			beidouChainRPCResult = JsonMapper.getInstance().fromJson(result, BeidouChainRPCResult.class);
			if (beidouChainRPCResult != null && beidouChainRPCResult.getError() == null) {
				return beidouChainRPCResult.getResult();
			} else if (beidouChainRPCResult != null && beidouChainRPCResult.getError() != null) {
				throw new BeidouchainException(beidouChainRPCResult.getError().get("code").toString(),
						",message : " + beidouChainRPCResult.getError().get("message").toString());
			} else {
				throw new BeidouchainException("-201", "json解析异常 返回值为空!");
			}
		}catch (IOException e) {
			logger.error("method:" + method + ",reason" + e.getMessage());			
			throw new BeidouchainException("-202", "http连接IO异常   ！");			
		}catch (BeidouchainException e) {
			throw e;
		}
	}

	@SuppressWarnings("rawtypes")
	protected boolean verifyInstance(Object obj, Class TheClass) {
		return TheClass.isInstance(obj);
	}	
	
	public RPCServerConfig getConfig() {
		return config;
	}

	public void setConfig(RPCServerConfig config) {
		this.config = config;
	}

}
