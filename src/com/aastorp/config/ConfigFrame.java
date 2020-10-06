package com.aastorp.config;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import com.aastorp.bibliothecaaastorpiana.interaction.MessageBoxes;
import com.aastorp.logger.Logger;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Component;

public class ConfigFrame extends JFrame {
	
	private JPanel contentPane;
	
	private Logger l = new Logger(this.getClass(), 4, true, new File("config.log"));
	
	private JButton btnApply = new JButton("Apply");
	private JButton btnCancel = new JButton("Cancel");
	
	public ConfigFrame() {
		final String F = "__constructor"; 
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
		//setBounds(100, 100, 450, 300);
		contentPane = new JPanel(new BorderLayout());
	//	contentPane.setBorder(BorderFactory.createEtchedBorder());
		setContentPane(contentPane);
		
		try {
			if (Config.getInstance() == null) {
				l.e(F, "Config is null! Cannot continue, will exit!");
				System.exit(ERROR);
			}
		} catch (Exception e) {
			l.e(F, "Could not create config: ");
			l.e(F, e);
			e.printStackTrace();
		}
	
		
		JPanel configPanel = new JPanel();
		configPanel.setBorder(new EmptyBorder(1, 2, 2, 2));
		Config configInstance = Config.getInstance();
		Settings configJTabbedPane = configInstance.getSettings();
		if (configJTabbedPane == null) {
			JOptionPane.showMessageDialog(null, "Config.getConfigJTabbedPane() returned null! Cannot continue, will exit!");
			System.exit(ERROR);
		}
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
		JPanel controlPanel = new JPanel();
		controlPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		controlPanel.setLayout(new GridLayout(0, 2, 0, 0));
		controlPanel.add(btnApply);
		controlPanel.add(btnCancel);
		contentPane.add(controlPanel);
//		MessageBoxes.inform("controlPanel prefferedSize:", controlPanel.getPreferredSize().toString());
		
		btnApply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (Setting s : configJTabbedPane.getSettings()) {
					try {
						Config.getInstance().setSetting(s.getFriendlyName(), s.getValue());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ConfigFrame.this.setVisible(false);
				ConfigFrame.this.dispose();
			}
		});
		
		/*Manually trigger the "auto" resize mechanism*/
		configInstance.resizeParent(configJTabbedPane /*doesn't matter what we pass it here, as long as it is a Component in the Config Window*/);
		Rectangle bounds = this.getBounds();
		this.setSize(bounds.width, bounds.height + 45);
		/*...after this point it is actually automatic...*/
	}
}
