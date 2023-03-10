package main;

import javax.swing.*;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class main {

	public static final String g_sVersion = "v1.1";
	
	@SuppressWarnings("serial")
	public static class ToggleButton extends JButton implements ActionListener {
		
		public boolean checked = false;

		public ToggleButton(String value) {
			super(value);
			
			this.setBackground(Color.GRAY);
			this.setForeground(Color.WHITE);
			this.addActionListener(this);
		}
		
		public void actionPerformed(ActionEvent e) {
	    	checked = !checked;
	    	if (checked)
	    	{
	    		this.setBackground(Color.GREEN);
	    		this.setForeground(Color.BLACK);
	    	}
	    	else
	    	{
	    		this.setBackground(Color.GRAY);
	    		this.setForeground(Color.WHITE);
	    	}
	    }
	}
	
	public static JFrame g_cFrame = new JFrame("Masker " + g_sVersion);
	public static JPanel g_cPanelMain = new JPanel();
	public static JPanel g_cPanelSecond = new JPanel();
	public static JPasswordField g_cPassword = new JPasswordField(20);
	public static int g_iLength = 0;
	public static List<ToggleButton> g_lcButtons = new ArrayList<ToggleButton>();
	public static Timer g_cTimer = new Timer();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	    g_cFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    g_cFrame.setSize(400,80);
	    
	    JLabel label = new JLabel("Password:");
	    g_cPanelMain.add(label);
	    
	    g_cPanelMain.add(g_cPassword);
	    
	    JButton button = new JButton("Mask");
	    g_cPanelMain.add(button);
	    
	    button.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	String masked = "";
	        	
	            for (int i = 0; i < g_cPassword.getPassword().length; ++i)
	            {
	            	if (g_lcButtons.get(i).checked)
	            	{
	            		masked += g_cPassword.getPassword()[i];
	            	}
	            }
	            
	            StringSelection selection = new StringSelection(masked);
	            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	            clipboard.setContents(selection, selection);
	        }
	    });
	    
	    g_cFrame.add(g_cPanelMain);
	    g_cFrame.add(g_cPanelSecond);
	    
	    g_cFrame.setLayout(new FlowLayout());
	    g_cFrame.setVisible(true);
	    
	    g_cFrame.repaint();
	    
	    g_cTimer.scheduleAtFixedRate(new TimerTask() {
	    	@Override
	    	public void run() {
	    		if (g_iLength != g_cPassword.getPassword().length)
	    		{
	    			g_iLength = g_cPassword.getPassword().length;
	    			
		    		g_cFrame.setSize(400,80);
		  			g_cPanelSecond.removeAll();
		  			g_lcButtons.clear();
		  			
		  			for (int i = 0; i < g_iLength; ++i)
		  			{
		  				ToggleButton button = new ToggleButton(String.valueOf(i + 1)); 
		  				g_lcButtons.add(button);
		  				g_cPanelSecond.add(button);
		  			}
		  			
		  			g_cFrame.setSize(Math.max(400, g_iLength * 55), 125);
		  			
		  			g_cFrame.repaint();
	    		}
	    	}
	    }, 1, 1);
	}
}
