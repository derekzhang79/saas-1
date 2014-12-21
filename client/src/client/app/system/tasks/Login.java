package client.app.system.tasks;

import java.awt.Color;
import share.core.login.LoginResponse;
import share.core.utils.Encoding;
import share.core.utils.Environment;
import client.app.system.dictionary.DictionaryManager;
import client.app.system.gui.def.GUILogin;
import client.app.system.operations.OperationsLogin;
import client.core.desktop.Desktop;
import client.core.gui.taks.OptionTask;
import client.core.profile.Profile;

public class Login extends OptionTask<Void>
{
	private final GUILogin gui = new GUILogin();
	
	public Login()
	{
		super(GUILogin.PATH, TaskType.MODAL, true);
	}
	
	@Override
	public void start()
	{
		setGUI(this.gui);
		String lastUser = Environment.getLastUserLogged();
		
		if (lastUser.isEmpty())
		{
			this.gui.user.focus();
		}
		else
		{
			this.gui.user.set(lastUser);
			this.gui.pass.focus();
		}
	}
	
	private boolean validate(String userName, String pass)
	{
		boolean valid = false;
		
		clearInputsBorders();
		
		if (userName.isEmpty())
		{
			showWarning(GUILogin.Literals.USER_REQUIRED);
			this.gui.user.setBorderColor(Color.RED);
			this.gui.user.focus();
		}
		else if (pass.isEmpty())
		{
			showWarning(GUILogin.Literals.PASS_REQUIRED);
			this.gui.pass.setBorderColor(Color.RED);
			this.gui.pass.focus();
		}
		else
		{
			valid = true;
		}
		
		return valid;
	}
	
	@Override
	public void closing()
	{
		System.exit(0);
	}
	
	private LoginResponse doLogin(String userName, String pass)
	{
		Profile.setUserName(userName);
		Profile.setUserPassword(Encoding.getSHA256(pass));
		
		return OperationsLogin.doLogin();
	}
	
	private void codeLoginOK(LoginResponse response)
	{
		close();
		Profile.setInformation(response);
		DictionaryManager.set(response.getDictionay());
		Environment.setLastUserLogged(Profile.getUserName());
		Desktop.getDesktop().startApplication();
	}
	
	private void codeUserError()
	{
		showWarning(GUILogin.Literals.INVALID_USER);
		this.gui.user.focus();
	}
	
	private void codePassError()
	{
		showWarning(GUILogin.Literals.INVALID_PASS);
		this.gui.pass.focus();
	}
	
	private void checkLogin(String userName, String pass)
	{
		if (validate(userName, pass))
		{
			LoginResponse response = doLogin(userName, pass);
			
			switch (response.getCode())
			{
			
				case LoginResponse.CODE_LOGIN_OK:
					codeLoginOK(response);
					break;
				
				case LoginResponse.CODE_USER_ERORR:
					codeUserError();
					break;
				
				case LoginResponse.CODE_PASS_ERROR:
					codePassError();
					break;

				default:
					break;
			}
		}
	}
	
	private void doLogin()
	{
		checkLogin(this.gui.user.get(), this.gui.pass.get());
	}
	
	@Override
	public void event(Event event)
	{
		switch (event)
		{
			case LOGIN:
				doLogin();
				break;
			
			case EXIT:
				System.exit(0);
				break;
			
			default:
				break;
		}
	}
}