package com.aastorp.config;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class OmniListener implements ChangeListener, KeyListener {
	@Override
	public void stateChanged(ChangeEvent arg0) {
		
		if (arg0.getSource().getClass() == JSpinner.class) {
			JSpinner jSpinner = (JSpinner)arg0.getSource();
			try {
				UIManager.setLookAndFeel((String)jSpinner.getValue());
				SwingUtilities.updateComponentTreeUI(jSpinner.getRootPane());
				Window window = SwingUtilities.getWindowAncestor(jSpinner);
				window.pack();
				window.repaint();
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
//			System.out.println(settings.toString());
			settings.repaint();
			Config.getInstance().resizeParent((Component)settings);
		} else {
			JOptionPane.showMessageDialog(null, arg0.getSource().getClass().getName(), "Unsupported Component type for OmniListener -> stateChanged(): " + arg0.getSource().getClass(), JOptionPane.WARNING_MESSAGE);
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
