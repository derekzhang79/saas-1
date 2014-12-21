package share.core.login;

import java.io.Serializable;

import share.core.dictionary.Dictionary;
import share.core.objects.Company;
import share.core.objects.User;

public class LoginResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final int CODE_LOGIN_OK = 0;
	public static final int CODE_USER_ERORR = 1;
	public static final int CODE_PASS_ERROR = 2;
	
	private int code = 0;
	
	private User user = new User();
	private Company company = new Company();
	private Dictionary dictionary = new Dictionary();
	
	public LoginResponse(int code) {
		this.code = code;
	}
	
	public LoginResponse(int code, User user, Company company, Dictionary dictionary) {
		this.code = code;
		this.user = user;
		this.company = company;
		this.dictionary = dictionary;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public Company getCompany() {
		return this.company;
	}
	
	public Dictionary getDictionay() {
		return this.dictionary;
	}
}