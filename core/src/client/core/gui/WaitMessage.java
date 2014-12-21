package client.core.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class WaitMessage extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	public WaitMessage(JFrame parent) {
		super(parent, "", ModalityType.APPLICATION_MODAL);
	}
	
	public void run(String message) {
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(210, 210, 210));
		panel.setBorder(BorderFactory.createRaisedBevelBorder());
		
		JLabel label = new JLabel(message);
		label.setFont(FontStore.getDefaultFont(true, 20));
		panel.add(label);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setStringPainted(false);
		progressBar.setIndeterminate(true);
		panel.add(progressBar);
		
		Dimension dimension = label.getMaximumSize();
		int width = dimension.width + 30;
		int height = dimension.height + 35;
		
		panel.setPreferredSize(new Dimension(width, height));
		setLocation(getX() - (width / 2), getY() - (height / 2));
		
		setContentPane(panel);
		pack();
		setVisible(true);
	}
	
	public void close() {
		setVisible(false);
		dispose();
	}
}