package client.core.gui.messages;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import share.core.conf.Configurator;
import client.core.desktop.Desktop;
import client.core.gui.fonts.FontStore;

public class Message
{
	private static WaitMessage wait = null;
	
	private static JLabel getLabel(String message)
	{
		JLabel label = new JLabel("<html>" + message + "</html>");
		label.setFont(FontStore.getDefaultFont());
		
		return label;
	}
	
	public static boolean confirm(String message)
	{
		return JOptionPane.showConfirmDialog(null, Message.getLabel(message), Configurator.getDesktop().messages.confirm, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0;
	}
	
	public static void information(String message)
	{
		JOptionPane.showMessageDialog(null, Message.getLabel(message), Configurator.getDesktop().messages.information, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void warning(String message)
	{
		JOptionPane.showMessageDialog(null, Message.getLabel(message), Configurator.getDesktop().messages.warning, JOptionPane.WARNING_MESSAGE);
	}
	
	public static void error(String message)
	{
		JOptionPane.showMessageDialog(null, Message.getLabel(message), Configurator.getDesktop().messages.error, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void communicationError()
	{
		JOptionPane.showMessageDialog(null, Message.getLabel(Configurator.getDesktop().messages.communication_error), Configurator.getDesktop().messages.error, JOptionPane.ERROR_MESSAGE);
	}
	
	public static String getBooleanString(boolean value)
	{
		return (value) ? Configurator.getDesktop().messages.true_string : Configurator.getDesktop().messages.false_string;
	}
	
	public static void showWaitMessage()
	{
		Message.wait = new WaitMessage(Desktop.getDesktop());
		Message.wait.run(Configurator.getDesktop().messages.wait);
	}
	
	public static void hideWaitMessage()
	{
		Message.wait.close();
	}
}