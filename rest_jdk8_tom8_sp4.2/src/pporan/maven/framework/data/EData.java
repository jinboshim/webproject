/**
 * @author jinbos
 * @version 00.00.01
 * @date 2012.11.09
 * @category kr.co.etribe.framework.data
 * @description HasMap 을 상속받아 Data를 원하는 형식으로 Output 가능(ex:String,Int,Boolean,NullCheck,Date)
 */
package pporan.maven.framework.data;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pporan.maven.framework.util.StringUtil;

public class EData extends HashMap{
	
	private static final String REQUEST_KEY = "_servletRequest";
	private static final String RESPONSE_KEY = "_servletResponse";
	
	public EData(){
		super();
	}
	private EData(HashMap map){
		super(map);
	}
	
	/**
	 * @param key
	 * @param values
	 * @description before SpecailCharacter Replace after Map Set 
	 */
	public void set(Object key, String[] values){
		if(values != null){
			for(int i=0, j=values.length; i<j; i++){
				values[i] = StringUtil.getSpecialCharacters(values[i]);
			}
		}
		
		super.put(key, values);
	}
	
	/**
	 * @param key
	 * @param value
	 * @description before SpecailCharacter Replace after Map Set 
	 */
	public void set(Object key, String value){
		value = getSpecialCharacters(value);
		super.put(key, value);
	}

	/**
	 * @param str
	 * @return
	 * @description HTML Special Change
	 * 			"&", "&amp;"
	 * 			"<", "&lt;"
	 * 			">", "&gt;"
	 * 			"'", "&acute;"
	 * 			"\"", "&quot;"
	 * 			"|", "&brvbar;"
	 * 			"\n", "<br/>"
	 * 			"\r", ""
	 */
	public String getSpecialCharacters(String str) {
		return StringUtil.getSpecialCharacters(str);
	}
	
	/**
	 * @param key
	 * @return object
	 * @description a key is mapped has value return 
	 */
	public Object getObject(Object key){
		Object obj = super.get(key);
		if(obj == null){
			return "";
		}
		return obj;
	}
	
	
	/**
	 * @param String
	 * @return String
	 */
	public String getString(String key){
		Object obj = super.get(key);
		String str = "";
		if(obj == null){
			str = "";
		}
		else{
			try{				
				str = (String)obj;	
			}catch(Exception e){
				try{
					str = obj.toString();				
				}catch(Exception ex){
					str = "";
				}
			}
		}
		
		if(str.equals("null") || (str.length() == 0)){
			str = "";
		}
		
		return str;
	}
	
	/**
	 * @param key
	 * @param nullvalue
	 * @return key is value eq null(nullvalue) or key is value
	 */
	public String getStringNvl(String key, String nullvalue){
		Object obj = super.get(key);
		
		if ((obj == null) || (obj.equals(""))) {
			return nullvalue;
		}
		else {
			 // 공백제거 한다.
			return obj.toString().trim();
		}		
	}	
	
	/**
	 * @param key
	 * @return Int
	 */
	public int getInt(String key){
		Object obj = super.get(key);
		if(obj == null){
			return 0;
		}
		return Integer.parseInt(obj.toString());
	}
	
	/**
	 * @param key
	 * @param nullvalue
	 * @return key is value eq null(nullvalue) or key is value
	 */
	public int getIntNvl(String key, int nullvalue) {
		Object obj = super.get(key);
		if(obj == null){
			return nullvalue;
		}
		return Integer.parseInt(obj.toString());
	}
	
	/**
	 * @param key
	 * @return Long
	 */
	public long getLong(String key){
		Object obj = super.get(key);
		if(obj == null){
			return 0;
		}
		return Long.parseLong(obj.toString());
		
	}
	
	/**
	 * @param key
	 * @param nullvalue
	 * @return key is value eq null(nullvalue) or key is value
	 */
	public long getLongNvl(String key, long nullvalue) {
		Object obj = super.get(key);
		if(obj == null){
			return nullvalue;
		}
		return Long.parseLong(obj.toString());
	}
	
	/**
	 * @param key
	 * @return key is empty(false) or key is value(values)
	 */
	public boolean getBoolean(String key){
		Object obj = super.get(key);
		if(obj == null || "".equals(obj)){
			return false;
		}
		return (boolean) obj;
	}

	/**
	 * @param key
	 * @return value is length(int)
	 */
	public int getLength(String key){
		Object obj = super.get(key);
		if(obj == null){
			return 0;
		}
		return obj.toString().length();
	}
	
	/**
	 * @param key
	 * @param iMode
	 * @return String
	 * @description iMode == 1 sFormat = MMMM dd, yyyy HH:mm:ss z // Jun 03, 2001 15:26:32 GMT+09:00
	 * 		iMode == 1) sFormat = "MMMM dd, yyyy HH:mm:ss z";   // Jun 03, 2001 15:26:32 GMT+09:00
	 * 		iMode == 2) sFormat = "MM/dd/yyyy";// 02/15/1999
	 * 		iMode == 3) sFormat = "yyyyMMdd";// 19990215
	 * 		iMode == 4) sFormat = "HHmmss";// 121241
	 * 		iMode == 5) sFormat = "dd MMM yyyy";// 15 Jan 1999
	 * 		iMode == 6) sFormat = "yyyyMMddHHMM"; //200101011010
	 * 		iMode == 7) sFormat = "yyyyMMddHHmmss"; //20010101101052
	 * 		iMode == 8) sFormat = "HHmmss";
	 * 		iMode == 9) sFormat = "yyyy/MM/dd/HH/mm"; //200101011010
	 * 		iMode == 10) sFormat = "yyyyMMddHHmmssSSS"; //200101011010000
	 * 		iMode == 11) sFormat = "yyyy/MM/dd E HH:mm"; // 2010/11/11 Wed 09:00
	 * 		else sFormat = "E MMM dd HH:mm:ss z yyyy";// Wed Feb 03 15:26:32 GMT+09:00 1999
	 */
	public String getDateFormat(String key, int iMode){
		Object obj = super.get(key);
		if(obj == null){
			return "";
		}
		
		String sFormat;
		if (iMode == 1) sFormat = "MMMM dd, yyyy HH:mm:ss z";   // Jun 03, 2001 15:26:32 GMT+09:00
		else if (iMode == 2) sFormat = "MM/dd/yyyy";// 02/15/1999
		else if (iMode == 3) sFormat = "yyyyMMdd";// 19990215
		else if (iMode == 4) sFormat = "HHmmss";// 121241
		else if (iMode == 5) sFormat = "dd MMM yyyy";// 15 Jan 1999
		else if (iMode == 6) sFormat = "yyyyMMddHHMM"; //200101011010
		else if (iMode == 7) sFormat = "yyyyMMddHHmmss"; //20010101101052
		else if (iMode == 8) sFormat = "HHmmss";
		else if (iMode == 9) sFormat = "yyyy/MM/dd/HH/mm"; //200101011010
		else if (iMode == 10) sFormat = "yyyyMMddHHmmssSSS"; //200101011010000
		else if (iMode == 11) sFormat = "yyyy/MM/dd E HH:mm"; // 2010/11/11 Wed 09:00
		else sFormat = "E MMM dd HH:mm:ss z yyyy";// Wed Feb 03 15:26:32 GMT+09:00 1999
		
		SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
		return formatter.format(obj.toString());
	}
	
	/**
	 * @param String
	 * @return String
	 */
	public String deleteHtmlTag(String key){
		
		Object obj = super.get(key);
		if(obj == null){
			return "";
		}
		String str = obj.toString();
        String textWithoutTag = "";
        textWithoutTag = str.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
//      textWithoutTag = textWithoutTag.replaceAll(" ", "").replaceAll("&nbsp;", "  ");
        textWithoutTag = textWithoutTag.replaceAll("&nbsp;", "  ");
        textWithoutTag = textWithoutTag.replaceAll("<br/>", "\n");
//      textWithoutTag = textWithoutTag.replaceAll("<!--", "");
//      textWithoutTag = textWithoutTag.replaceAll("-->", "");
        textWithoutTag = textWithoutTag.replaceAll("<style[^>]*>.*</style>", "");
        textWithoutTag = textWithoutTag.replaceAll("(?://.*\n{0,1})|(?:/\\*(?:.|\\s)*?\\*/\n{0,1})", "");
        return textWithoutTag;
	}

	/**
	 * @param key
	 * @return key is null(true) or key is value(false)
	 */
	public boolean isNull(String key){
		Object obj = super.get(key);
		if(obj == null || "".equals(obj)){
			return true;
		}
		return false;
	}
	
	/**
	 * @param String
	 * @return key is number(true) or key is not number(false)
	 */
	public boolean isNumber(String key) {
		Object obj = super.get(key);
		if(obj == null){
			return false;
		}
		String str = obj.toString();
		boolean check = true;
		if (str == null || str == "") {
			return false;
		}
		for(int i = 0; i < str.length(); i++) {
			if(!Character.isDigit(str.charAt(i))){
				check = false;
				break; 
			}// end if
		} //end for
		return check;  
	} //isNumber 
	
	/**
	 * Map에 저장된 값이 배열(array) 인지 여부
	 * @param key
	 * @return 배열인 경우 true, 아니면 false
	 */
	public boolean isArray(String key){
		
		Object obj = super.get(key);
		
		if(obj == null){
			return false;
		}
		
		if(obj.getClass().isArray()){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 해당 key 값을 String 배열로 리턴 한다.
	 * @param key
	 * @return
	 */
	public String[] getStringValues(String key){
		
		if(isArray(key)){
			return (String[])super.get(key);
		}else{
			return new String[]{ getString(key) };
		}
	}
	
	public void setHttpServletRequest(HttpServletRequest req){
		super.put(REQUEST_KEY, req);
	}
	
	public void setHttpServletResponse(HttpServletResponse res){
		super.put(RESPONSE_KEY, res);
	}
	
	/**
	 * HttpServletRequest를 리턴.
	 * @return 값이 없을시 null
	 */
	public HttpServletRequest getHttpServletRequest(){
		return (HttpServletRequest)super.get(REQUEST_KEY);
	}
	
	/**
	 * HttpServletResponse를 리턴.
	 * @return 값이 없을시 null
	 */
	public HttpServletResponse getHttpServletResponse(){
		return (HttpServletResponse)super.get(RESPONSE_KEY);
	}
}
