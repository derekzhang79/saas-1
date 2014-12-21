package client.app.system.tasks;

import java.awt.Color;
import share.core.utils.Encoding;
import client.app.system.gui.def.GUIBlocker;
import client.core.gui.taks.OptionTask;
import client.core.profile.Profile;

public class Blocker extends OptionTask<Void> {
	
	private GUIBlocker gui = new GUIBlocker();
	
	public Blocker() {
		super(GUIBlocker.PATH, TaskType.SPECIAL, true);
	}
	
	public void start() {
		setGUI(this.gui);
		addTitle(Profile.getUserName());
	}
	
	public void closing() {
	}
	
	private void checkPassword(String pass) {
		if (validate()) {
			if (Profile.getUserPassword().equals(Encoding.md5(pass))) {
				close();
			} else {
				showWarning(GUIBlocker.Literals.INVALID_PASS);
				this.gui.pass.setBorderColor(Color.RED);
				this.gui.pass.focus();
			}
		}
	}
	
	private boolean validate() {
		boolean valid = false;
		
		clearInputsBorders();
		
		if (this.gui.pass.isEmpty()) {
			showWarning(GUIBlocker.Literals.PASS_REQUIRED);
			this.gui.pass.focus();
			this.gui.pass.setBorderColor(Color.RED);
		} else {
			valid = true;
		}
		
		return valid;
	}
	
	public void event(Event event) {
		switch (event) {
		
			case ACCEPT:
				checkPassword(this.gui.pass.get());
				break;
			
			case EXIT:
				Exit exit = new Exit();
				exit.run();
				this.gui.pass.focus();
				break;
			
			default:
				break;
		}
	}
}