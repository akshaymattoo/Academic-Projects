package com.se1.admin;

/**
 * @Akshay Mattoo
 * Class Name  : Admin
 * Description : This is a POJO class for the Admin.
 *               It is a singleton class which enforces the same object in the whole application.
 */
import java.io.Serializable;

public class Admin implements Serializable{

	/**
	 * This serial version id is used for the serialization and deserialization
	 */
	private static final long serialVersionUID = 1L;
	private static Admin instance = new Admin();
	
	//static method to return the instance of the class.
	public static Admin getInstance() {
	    return instance;
	}
	//Private constructor to prevent the object to be made from outside class.
    private Admin()
    {
    	
    }
	
    //This method is to prevent the object cloning.
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("Clone Not supported Exception");
	}
	
	//Getter setters for the Admin.
	private String userName;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	private String password;
	private String number;
	private String email;
	
}
