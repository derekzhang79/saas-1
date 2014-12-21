package server.core.connection;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import server.core.database.kernel.Database;
import server.core.database.tables.TableClient;
import server.core.database.tables.TableDictionary;
import server.core.database.tables.TableLogin;
import server.core.database.tables.TablePermission;
import server.core.database.tables.TableUser;
import server.core.database.tables.TableUserGroup;
import share.core.connection.Parameters;
import share.core.constants.Constants.AppDatabase;
import share.core.dictionary.Dictionary;
import share.core.dictionary.DictionaryCategory;
import share.core.login.LoginResponse;
import share.core.objects.Company;
import share.core.objects.Permission;
import share.core.objects.User;
import share.core.utils.Encoding;

public class CommunicationManager
{
	private String newTicket = "";
	private String currentSessionId = "";
	
	private final Database database;
	
	public CommunicationManager(String databaseEnvironment)
	{
		this.database = new Database(databaseEnvironment);
	}
	
	public boolean isValid(Parameters data)
	{
		boolean valid = false;
		
		String user = data.getUser();
		String ticket = data.getTicket();
		String sessionId = data.getSessionId();
		
		TableUser userTable = new TableUser(getSystemConnection());
		userTable.name = user;
		
		if (userTable.search() == 1)
		{
			if (userTable.ticket.equals(ticket) && userTable.session_id.equals(sessionId))
			{
				this.newTicket = generateCode();
				this.currentSessionId = sessionId;
				
				userTable.ticket = this.newTicket;
				
				valid = userTable.update();
				
				if (valid)
				{
					TableClient clientTable = new TableClient(getSystemConnection());
					clientTable.id = data.getClientID();
					
					if (clientTable.read())
					{
						this.database.createAppConnection(clientTable.ddbb);
					}
				}
			}
		}
		
		return valid;
	}
	
	public LoginResponse userLogin(Parameters data)
	{
		String user = data.getUser();
		String pass = data.getPass();
		
		LoginResponse response = new LoginResponse(LoginResponse.CODE_USER_ERORR);
		
		TableUser userTable = new TableUser(getSystemConnection());
		userTable.name = user;
		
		if (userTable.search() == 1)
		{
			if (userTable.password.equals(pass))
			{
				this.newTicket = generateCode();
				this.currentSessionId = generateCode();
				
				userTable.ticket = this.newTicket;
				userTable.session_id = this.currentSessionId;
				
				boolean valid = userTable.update();
				
				if (valid)
				{
					TableUserGroup userGroup = getUserGroup(userTable.user_group);
					Company company = getCompany(userGroup.client);
					User userObject = new User(userTable.id, userTable.name, userTable.password, userTable.user_group, userGroup.administrator, getUserPermissions(userGroup.id));
					
					this.database.createAppConnection(company.getDatabase());
					
					createLoginLog(data, userTable.id);
					
					response = new LoginResponse(LoginResponse.CODE_LOGIN_OK, userObject, company, getDictionary());
					
				}
				else
				{
					response = new LoginResponse(LoginResponse.CODE_PASS_ERROR);
				}
			}
			else
			{
				response = new LoginResponse(LoginResponse.CODE_PASS_ERROR);
			}
		}
		
		return response;
	}
	
	private TableUserGroup getUserGroup(int userGroupID)
	{
		TableUserGroup table = new TableUserGroup(getSystemConnection());
		table.getUserGroup(userGroupID);
		
		return table;
	}
	
	private Company getCompany(int clientID)
	{
		TableClient table = new TableClient(getSystemConnection());
		
		return table.getClient(clientID);
	}
	
	private String[] getUserPermissions(int userGroupID)
	{
		List<String> list = new ArrayList<String>();
		
		TablePermission table = new TablePermission(getSystemConnection());
		Permission[] permissions = table.getPermissions(userGroupID);
		
		for (Permission permission : permissions)
		{
			list.add(permission.taskPath);
		}
		
		String[] result = new String[list.size()];
		list.toArray(result);
		
		return result;
	}
	
	private Dictionary getDictionary()
	{
		Dictionary result = new Dictionary();
		
		TableDictionary table = new TableDictionary(getApplicationConnection());
		
		int rows = table.search("category, code");
		
		DictionaryCategory category = null;
		String currentCategory = "";
		
		for (int i = 0; i < rows; i++)
		{
			table.select(i);
			
			if (!table.category.equals(currentCategory))
			{
				if (category != null)
				{
					result.add(currentCategory, category);
					category = new DictionaryCategory();
				}
				else
				{
					category = new DictionaryCategory();
				}
				
				currentCategory = table.category;
			}
			
			category.add(table.code, table.value);
		}
		
		if (category != null)
		{
			result.add(currentCategory, category);
		}
		
		return result;
	}
	
	private String generateCode()
	{
		Random randomGenerator = new Random();
		
		return Encoding.md5(String.valueOf(randomGenerator.nextInt(100)));
	}
	
	public void closeConnections()
	{
		this.database.closeConnections();
	}
	
	public String getNewTicket()
	{
		return this.newTicket;
	}
	
	public String getSessionId()
	{
		return this.currentSessionId;
	}
	
	public Connection getApplicationConnection()
	{
		return this.database.getConnection(AppDatabase.APPLICATION);
	}
	
	public Connection getSystemConnection()
	{
		return this.database.getConnection(AppDatabase.SYSTEM);
	}
	
	private void createLoginLog(Parameters parameters, int userID)
	{
		TableLogin login = new TableLogin(getSystemConnection());
		
		login.createRequest(parameters, userID);
	}
}