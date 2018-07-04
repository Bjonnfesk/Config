package com.aastorp.config;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class OmniListener implements ChangeListener, KeyListener {
	@Override
	public void stateChanged(ChangeEvent arg0) {
		Window window = SwingUtilities.getWindowAncestor((Component) arg0.getSource());
		if (arg0.getSource().getClass() == JSpinner.class) {
			JSpinner jSpinner = (JSpinner)arg0.getSource();
			try {
				UIManager.setLookAndFeel((String)jSpinner.getValue());
				SwingUtilities.updateComponentTreeUI(jSpinner.getRootPane());
				SwingUtilities.getWindowAncestor(jSpinner).pack();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (arg0.getSource().getClass() == Settings.class){
			Settings settings = (Settings)arg0.getSource();
			SettingCategory settingCategory = (SettingCategory)settings.getSelectedComponent();
			Dimension d = settingCategory.getPreferredSize();
			
			Dimension nd = new Dimension(d.width, d.height);
			settings.setPreferredSize(nd);

			window.pack();
			window.repaint();
		} else {
			JOptionPane.showMessageDialog(null, arg0.getSource().getClass().getName(), "Unsupported Component type for OmniListener: " + arg0.getSource().getClass(), JOptionPane.WARNING_MESSAGE);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
//		nvm, don't care
	}

	@Override
	public void keyReleased(KeyEvent e) {
//		nvm, don't care
	}

	@Override
	public void keyTyped(KeyEvent e) {
		TextFieldSetting textFieldSetting = (TextFieldSetting)e.getSource();
		textFieldSetting.setSize(textFieldSetting.getPreferredSize());
	}
}
