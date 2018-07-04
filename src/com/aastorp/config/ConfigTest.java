package com.aastorp.config;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerListModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.aastorp.logger.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class ConfigTest.
 */
public class ConfigTest extends JFrame {

	/** The content pane. */
	private JPanel contentPane;
	
	/** The testing pane. */
	private JPanel testingPane = new JPanel();
	
	/** The status pane. */
	private JPanel statusPane = new JPanel();
	
	/** The l. */
	private Logger l = new Logger(this.getClass(), 4, true, new File("config.log"));

	/**
	 * Launch the application.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfigTest frame = new ConfigTest();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ConfigTest() {
		final String F = "__constructor"; 
		l.header("Config Testing Program", 4);
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel(new BorderLayout());
//		contentPane.setBorder(BorderFactory.createEtchedBorder());
		setContentPane(contentPane);
		
		try {
			Config.getInstance();
		} catch (Exception e) {
			l.e(F, "Could not create config: ");
			l.e(F, e);
			e.printStackTrace();
		}
		
		JPanel configPanel = new JPanel();
		configPanel.setBorder(new EmptyBorder(1, 2, 2, 2));
		Config configInstance = Config.getInstance();
		JTabbedPane configJTabbedPane = configInstance.getConfigJTabbedPane();
		configPanel.add(configJTabbedPane, BorderLayout.CENTER);
		contentPane.add(configPanel, BorderLayout.NORTH);
		JPanel lookAndFeelPanel = new JPanel();
		JSpinner lookAndFeelJSpinner = new JSpinner();
		List<UIManager.LookAndFeelInfo> lookAndFeels = Arrays.asList(UIManager.getInstalledLookAndFeels());
		List<String> lookAndFeelNames = new ArrayList<String>();
		for (UIManager.LookAndFeelInfo lookAndFeel : lookAndFeels) {
			lookAndFeelNames.add(lookAndFeel.getClassName());
		}
		lookAndFeelJSpinner.setModel(new SpinnerListModel(lookAndFeelNames.toArray()));
		lookAndFeelJSpinner.addChangeListener(new OmniListener());
		lookAndFeelPanel.add(lookAndFeelJSpinner);
		contentPane.add(lookAndFeelPanel, BorderLayout.SOUTH);
		
		
		Config.getInstance().getSettings().setSelectedIndex(0); /* trigger the ChangeListener so 
			the window gets resized.*/
		this.pack();
	
	}

}
