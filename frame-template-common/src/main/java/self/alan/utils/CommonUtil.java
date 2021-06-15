package self.alan.utils;

import self.alan.expection.ResultCode;
import self.alan.model.login.TokenInfo;
import self.alan.model.login.Users;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {
	/**
	 * 生成用户token
	 */
	public static String generateToken(Users user) {
		//id password date
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Long expiredTime = Long.parseLong(Constant.map.get("expiredTime"));
//		String content = user.getId().toString()+","+user.getUser_name()+","+user.getPassword()+","+sdf.format(new Date())+","+expiredTime;
		String content = user.getId().toString()+","+user.getUserName()+","+user.getPassword();
//		String content = user.getId().toString();
		return AesUtil.encrypttoStr(content, "kedacom.com");
	}
	
	/**
	 * 生成永久有效token，ovostation用
	 * @param user
	 * @return
	 */
	public static String generateEternalToken(Users user) {
		String content = user.getId().toString();
		return AesUtil.encrypttoStr(content, "kedacom.com");
	}
	
	public static TokenInfo decodeToken(String token) {
		TokenInfo tokenBo = new TokenInfo();
		if (StringUtils.isEmpty(token)){
			tokenBo.setResult(ResultCode.RESULT_INVALID_TOKEN.getMessage());
			return tokenBo;
		}
		try {
			byte[] chars = AesUtil.decrypt(token, "kedacom.com");
			if (chars != null && chars.length > 0) {
				String result = new String(AesUtil.decrypt(token, "kedacom.com"));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String[] array = {};
				array = result.split(",");
				if(array.length >= 3){
					tokenBo.setUserId(Integer.parseInt(array[0]));
					tokenBo.setUserName(array[1]);
					tokenBo.setPassword(array[2]);
					if (array.length >= 5) {
						tokenBo.setDate(sdf.parse(array[3]));
						tokenBo.setExpires(Long.parseLong(array[4]));
					}
				// 兼容原来版本，token只保存userId
				// 直接不兼容原来的版本
				}else if(array.length==1){
					tokenBo.setUserId(Integer.parseInt(array[0]));
//					tokenBo.setResult(JsonResult.RESULT_TOKEN_EXPIRED);
				}else{
					tokenBo.setResult(ResultCode.RESULT_INVALID_TOKEN.getMessage());
				}
			} else {
				tokenBo.setResult(ResultCode.RESULT_INVALID_TOKEN.getMessage());
			}
		} catch (Exception e) {
			tokenBo.setResult(ResultCode.RESULT_INVALID_TOKEN.getMessage());
		}
		return tokenBo;
	}

	public static String createActivateCode(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz123456789"; // 生成字符串从此序列中取
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	public static List<String> str2strList(String str){
		List<String> list = new ArrayList<>();
		if(StringUtils.isNotBlank(str)) {
			String[] arr = str.split(",");
			for (String s : arr) {
				list.add(s);
			}
		}
		return list;
	}
	
	public static List<Integer> str2IntList(String str){
		List<Integer> list = new ArrayList<>();
		if(StringUtils.isNotBlank(str)) {
			String[] arr = str.split(",");
			for (String i : arr) {
				list.add(new Integer(i));
			}
		}
		return list;
	}
	
	public static void main(String[] args) {

		decodeToken("DA4363D9B2640D67942F4EA3D8E50CBE");
		Users user = new Users();
		user.setId(125327);
		user.setUserName("狐狸小妖050001");
		user.setPassword("044090c03e7b261139780ff6b46e20d9");
		System.out.println(generateEternalToken(user));
	}
//	
	
	public static List<String> getBetweenDates(String minDate, String maxDate) throws ParseException{  
        List<String> listDate = new ArrayList<String>();  
        Calendar startCalendar = Calendar.getInstance();  
        Calendar endCalendar = Calendar.getInstance();  
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date startDate = df.parse(minDate);  
        startCalendar.setTime(startDate);  
        Date endDate = df.parse(maxDate);  
        endCalendar.setTime(endDate);  
        listDate.add(df.format(startCalendar.getTime()));
        while(true){  
            startCalendar.add(Calendar.DAY_OF_MONTH, 1);  
            if(startCalendar.getTimeInMillis() <= endCalendar.getTimeInMillis()){  
                listDate.add(df.format(startCalendar.getTime()));  
            }else{  
                break;  
            }  
        }  
        return listDate;  
    }  

	/**
	 * 验证手机号码
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		boolean flag = false;
		try {
//			Pattern p = Pattern
//					.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
			Pattern p = Pattern
					.compile("^1\\d{10}$");
			Matcher m = p.matcher(mobiles);
			flag = m.matches();
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	

}
