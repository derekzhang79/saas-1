package client.core.profile;

import share.core.login.LoginResponse;
import share.core.objects.Company;
import share.core.objects.User;

public class Profile
{
	private static User USER = new User();
	private static Company COMPANY = new Company();
	
	public static void setInformation(LoginResponse response)
	{
		Profile.USER = response.getUser();
		Profile.COMPANY = response.getCompany();
	}
	
	public static void setAdministrator(boolean value)
	{
		Profile.USER.administrator = value;
	}
	
	public static void setUserName(String name)
	{
		Profile.USER.name = name;
	}
	
	public static void setUserPassword(String password)
	{
		Profile.USER.password = password;
	}
	
	public static int getUserID()
	{
		return Profile.USER.id;
	}
	
	public static int getClientID()
	{
		return Profile.COMPANY.getClientID();
	}
	
	public static String getUserName()
	{
		return Profile.USER.name;
	}
	
	public static String getUserPassword()
	{
		return Profile.USER.password;
	}
	
	public static boolean isAdministrator()
	{
		return Profile.USER.administrator;
	}
	
	public static Company getCompany()
	{
		return Profile.COMPANY;
	}
	
	public static boolean hasPermission(String task)
	{
		boolean valid = Profile.isAdministrator();
		
		if (!valid)
		{
			for (String current : Profile.USER.permissions)
			{
				if (current.equals(task))
				{
					valid = true;
					break;
				}
			}
		}
		
		return valid;
	}
}