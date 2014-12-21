package share.core.login;

import java.io.Serializable;
import share.core.dictionary.Dictionary;
import share.core.objects.Company;
import share.core.objects.User;

public class LoginResponse implements Serializable
{
	private static final long serialVersionUID = -2839566256766362754L;

	public static final int CODE_LOGIN_OK = 0;
	public static final int CODE_USER_ERORR = 1;
	public static final int CODE_PASS_ERROR = 2;

	private final int code;
	private final User user;
	private final Company company;
	private final Dictionary dictionary;

	public LoginResponse(int code)
	{
		this.code = code;
		this.user = new User();
		this.company = new Company();
		this.dictionary = new Dictionary();
	}

	public LoginResponse(int code, User user, Company company, Dictionary dictionary)
	{
		this.code = code;
		this.user = user;
		this.company = company;
		this.dictionary = dictionary;
	}

	public int getCode()
	{
		return this.code;
	}

	public User getUser()
	{
		return this.user;
	}

	public Company getCompany()
	{
		return this.company;
	}

	public Dictionary getDictionay()
	{
		return this.dictionary;
	}
}