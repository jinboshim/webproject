package project.advice;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ExceptionHandler(NullPointerException.class)
	@ResponseBody
	public String nullException(NullPointerException ex){
		logger.error("@@@@@@@@@@@@@@@@@@ : "+ex.toString());
		ex.printStackTrace();
		
		JSONObject jo = new JSONObject();
		
		jo.put("result_code", "13");
		jo.put("result_msg", "");
		return jo.toString();
	}
	
	@ExceptionHandler(DataAccessException.class)
	@ResponseBody
	public String dataAccessException(DataAccessException dae){
		logger.error("@@@@@@@@@@@@@@@@@@ : "+dae.toString());
		dae.printStackTrace();
		
		JSONObject jo = new JSONObject();
		
		jo.put("result_code", "41");
		jo.put("result_msg", "");
		
		return jo.toString();
	}
	
//	@ExceptionHandler(SQLException.class)
//	@ResponseBody
//	public String sqlException(SQLException sq){
//		JSONObject jo = new JSONObject();
//		
//		jo.put("result_code", "88");
//		jo.put("result_msg", "");
//		
//		return jo.toString();
//	}
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public String exception(Exception e){
		logger.error("@@@@@@@@@@@@@@@@@@ : "+e.toString());
		JSONObject jo = new JSONObject();
		
		jo.put("result_code", "99");
		jo.put("result_msg", "");
		
		return jo.toString();
	}
	
	
	
	
	
	
}
