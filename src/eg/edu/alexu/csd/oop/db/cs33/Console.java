package eg.edu.alexu.csd.oop.db.cs33;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Console {

	public static void main(String[] args) {
		
		new Console();
	}
	
	public JFrame frame;
	public JTextPane panel;
	public JTextField input;
	public JScrollPane scrollpane;
	public StyledDocument document;
	
	public Console()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
		}
		catch (Exception ex) {}
		
		frame = new JFrame();
		frame.setTitle("Simple DBMS");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JTextPane();
		panel.setEditable(false);
		panel.setFont(new Font("Courier New",Font.PLAIN,12));
		panel.setOpaque(false);
		
		input = new JTextField();
		input.setEditable(true);
		input.setForeground(Color.WHITE);
		input.setCaretColor(Color.WHITE);
		input.setOpaque(false);
		input.setFont(new Font("Courier New",Font.PLAIN,12));
		input.setName("Enter SQL Query Here");
		
		scrollpane = new JScrollPane(panel);
		scrollpane.setOpaque(false);
		scrollpane.getViewport().setOpaque(false);
		
		document = panel.getStyledDocument();
		
		frame.add(input,BorderLayout.SOUTH);
		frame.add(scrollpane,BorderLayout.CENTER);
	
		frame.getContentPane().setBackground(new Color(50,50,50));
		frame.setSize(660, 350);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		input.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						String text = input.getText();
						
						if(text.length() > 1)
						{			
							
							QueryParser parser = new QueryParser();
							boolean correct = parser.commandChooser(text);
							
				            parser.save();

							print(text+"\n",correct);
							scrollBottom();
							input.selectAll();
							
						}
					}
			
				});
		
	}
	
	public void scrollBottom()
	{
		panel.setCaretPosition(panel.getDocument().getLength());
	}
	
	public void print(String s,boolean correct)
	{
		Color green = new Color(127,255,0);
		Color red = new Color(255,50,0);
		
		
		Style styleCorrect = panel.addStyle("Style Correct", null);
		StyleConstants.setForeground(styleCorrect, green);
		
		Style styleWrong = panel.addStyle("Style Wrong", null);
		StyleConstants.setForeground(styleWrong, red);
		
		try
		{
			if(correct)
			{
				document.insertString(document.getLength(), s, styleCorrect);
			}
			else
			{
				document.insertString(document.getLength(), s, styleWrong);
			}
		}
		catch(Exception exp) {}
	}
	
}
