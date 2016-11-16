package pporan.maven.framework.spring;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import pporan.maven.framework.data.EData;
import pporan.maven.framework.util.JsonParserUtil;
import pporan.maven.framework.util.StringUtil;

public class EDataArgumentResolver implements HandlerMethodArgumentResolver  {
	
	private static Logger logger = LoggerFactory.getLogger(EDataArgumentResolver.class);
	
	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer, NativeWebRequest nativeWebRequest,
			WebDataBinderFactory binderFactory) throws Exception {
			
		HttpServletRequest request = (HttpServletRequest)nativeWebRequest.getNativeRequest();
		
		Class<?> parameterType = methodParameter.getParameterType();
		
		//Controller method 파라미터에 EData가 있으면 request parameter값들을 EData에 저장하여 돌려준다.
		if(parameterType.equals(EData.class)){
			
			EData eData = new EData();
						
			Enumeration parameterNames = request.getParameterNames();
			
			int chk_cnt = 0;
			
			String paramName;
			while(parameterNames.hasMoreElements()){
				paramName = (String)parameterNames.nextElement();
				
				String[] parameterValues = request.getParameterValues(paramName);
				
				// 파라미터값이 배열로 넘오오면 배열로 저장
				if(parameterValues != null){
					if(parameterValues.length > 0){
						
						//SQL Injection 방지 param값들 문자 치환
						for(int i=0; i < parameterValues.length; i++){
							
							for(int j=0; j < StringUtil.SQL_INJECTION_CHAR.length; j++){
								
								parameterValues[i] = parameterValues[i].replaceAll(StringUtil.SQL_INJECTION_CHAR[j][0]
																				, StringUtil.SQL_INJECTION_CHAR[j][1]);
							}
							
						}
						
						if(parameterValues.length > 1){
							eData.put(paramName, parameterValues);
						}else{
							eData.put(paramName, parameterValues[0]);
						}
					}
				}
				chk_cnt++;
			}
			
			if(chk_cnt == 0){
				String inputString = IOUtils.toString(request.getReader());
				
				try{
					
					JSONObject jo = new JSONObject(inputString);
					EData map = JsonParserUtil.toMap(jo);
					List list = new ArrayList();
					list.add(map);
					eData.put("data", list);
					
				}
				catch (JSONException e){
					
					try{
						JSONArray ja = new JSONArray(inputString);
						
						List list = JsonParserUtil.toList(ja);
						
						eData.put("data", list);
					}
					catch (JSONException e2){
						if(!StringUtil.bNvl(inputString)){
							String[] inputStrings = inputString.split("&");
							if(inputStrings.length > 0){
								for(int i=0,j=inputStrings.length; i<j; i++){
									String[] str = inputStrings[i].split("=");
									
									if(str.length > 1){
										eData.put(str[0], str[1]);
									}
								}
							}
						}
						
					}
				}
			}
			
//			Device device = DeviceUtils.getCurrentDevice(RequestContextHolder.currentRequestAttributes());
//			
//			if(device.isMobile()){
//				eData.put("terminal", "m");
//			}
//			else if(device.isTablet()){
//				eData.put("terminal", "t");
//			}
//			else{
//				eData.put("terminal", "n");
//			}
			
			//EData에 request, response를 저장한다.
			eData.setHttpServletRequest(request);
			eData.setHttpServletResponse((HttpServletResponse)nativeWebRequest.getNativeResponse());
			
//			String uri = eData.getHttpServletRequest().getRequestURI();
//			if (uri.startsWith("/_admin")) {
//				String mid_uri = uri.substring(0, uri.lastIndexOf("/"));
//				eData.put("page_code", mid_uri.substring(mid_uri.lastIndexOf("/") + 1));
//				eData.put("page_type", uri.substring(uri.lastIndexOf("/") + 1, uri.lastIndexOf(".")));
//			}
			
			if(logger.isDebugEnabled()){
				logger.debug("@@>> request.getContentType() : " + request.getContentType());
				logger.debug("@@>> EDataArgumentResolver EData requestMapping info : " + eData.toString());
			}
			
			
			return eData;
		}
		
		return WebArgumentResolver.UNRESOLVED;
	}

	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
//		return true;
		return methodParameter.getParameterType().equals(EData.class);	}

}
