package com.aastorp.config;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.aastorp.bibliothecaaastorpiana.databases.SelectQuery;
import com.aastorp.bibliothecaaastorpiana.databases.WhereClause;
import com.aastorp.logger.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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
		configPanel.add(Config.getInstance().getConfigPane(), BorderLayout.CENTER);
		contentPane.add(configPanel, BorderLayout.NORTH);
		
		
		this.pack();
	
	}

}
