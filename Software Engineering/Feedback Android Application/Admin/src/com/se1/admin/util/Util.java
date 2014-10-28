package com.se1.admin.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.telephony.PhoneNumberUtils;
import android.util.Patterns;

/**
 * @author Akshay
 * Class Name  : Util
 * Description : This is a Util class having validation functions for the whole application.
 */
public class Util {

	public static boolean isEmailValid(CharSequence email) {
		   return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}
	public static boolean isNumberValid(String phoneNumber) {
			
		Pattern pattern = Pattern.compile("^[0-9]{10,10}$");
	      return pattern.matcher(phoneNumber).matches();
		/*Pattern pattern = Patterns.PHONE;
        return pattern.matcher(phoneNumber).matches();*/
		//return android.util.Patterns.PHONE.matcher(phoneNumber).matches();
		  // return PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber);
	}
	
	public static boolean isValidText(String str)
	{
		boolean flag = false;
		flag =Pattern.matches("^[a-zA-Z\\d._]{3,10}$", str);
		return flag;
	}
	
	public static boolean isValidPassword(String str)
	{
		boolean flag = false;
		flag =Pattern.matches("^[a-zA-Z\\d._@]{3,10}$", str);
		return flag;
	}
	
	public static boolean isCommentsValid(String comments) {
		boolean flag =true;
		if(comments.length()<10)
			flag =false;
		return flag;
	}
}
