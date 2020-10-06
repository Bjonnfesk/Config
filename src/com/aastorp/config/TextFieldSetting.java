package com.aastorp.config;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

// TODO: Auto-generated Javadoc
/**
 * The Class TextFieldSetting.
 */
public class TextFieldSetting extends JTextField implements Setting {
	
	/** Prevent excessive recursion. */
	private boolean antiRecursion = false;
	/** The SettingCategory of the setting. This controls which tab the setting gets added to. */
	private SettingCategory settingCategory;
	
	/**  The label that this Setting will show its friendly name in. */
	private JLabel label;
	
	/**
	 * Instantiates a new text field setting.
	 *
	 * @param name The name of this setting.
	 * @param value The value of this setting.
	 * @param friendlyName The friendly name (display name) of this setting.
	 * @param settingCategoryId The setting category is this setting.
	 */
	public TextFieldSetting(String name, Object value, String friendlyName, int settingCategoryId) {
		this.setName(name);
		this.setValue((String)value);
		this.setFriendlyName(friendlyName);
		this.setSettingCategory(new SettingCategory(settingCategoryId));
		this.setBorder(BorderFactory.createCompoundBorder(
				this.getBorder(),
				BorderFactory.createEmptyBorder(2, 5, 2, 5)));
		this.setFont(new Font("Helvetica", Font.PLAIN, 18));
		this.addKeyListener(new OmniListener());
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JTextField#getPreferredSize() 
	 */
	@Override 
	public Dimension getPreferredSize() {
		Dimension preferred = super.getPreferredSize();
		Dimension minimum = getMinimumSize();
		Dimension maximum = getMaximumSize();
		preferred.width = Math.min(Math.max(preferred.width, minimum.width), maximum.width);
		preferred.height = Math.min(Math.max(preferred.height, minimum.height), maximum.height);
		return preferred;
	}
	
	/** Gets the minimum size a JTextField must have in order to display all its contents.
	 * @return The minimum size or null.
	 * @see javax.swing.JTextField#getMinimumSize()
	 */
	@Override
	public Dimension getMinimumSize() {
		Dimension d = new Dimension();
		Graphics2D g = (Graphics2D)this.getGraphics();
		if (g == null) {
			//this Setting is not on screen yet, therefore it has no graphics and we cannot resize it...
		} else {
			FontMetrics fm = null;
			try {
				fm = g.getFontMetrics(this.getFont());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
			Integer correctionValue = 19; //for most Look and Feels, FontMetrics.stringWidth is off by 19.
			if (UIManager.getLookAndFeel().getName() == "Nimbus") {
				correctionValue = 23; //for Nimbus, FontMetrics.stringWidth is off by 23.
			}
			if (fm != null) {
				d.setSize(fm.stringWidth((String)this.getValue()) + correctionValue, super.getMinimumSize().getHeight() + 3);
			} else {
				return null;
			}
		}
		return d;
	}

	/* (non-Javadoc)
	 * @see com.aastorp.config.Setting#getFriendlyName()
	 */
	@Override
	public String getFriendlyName() {
		return this.getJLabel().getText();
	}

	/* (non-Javadoc)
	 * @see com.aastorp.config.Setting#setFriendlyName(java.lang.String)
	 */
	@Override
	public void setFriendlyName(String friendlyName) {
		this.setJLabel(new JLabel(friendlyName));
	}

	/* (non-Javadoc)
	 * @see com.aastorp.config.Setting#getJLabel()
	 */
	@Override
	public JLabel getJLabel() {
		return this.label;
	}

	/* (non-Javadoc)
	 * @see com.aastorp.config.Setting#setJLabel(javax.swing.JLabel)
	 */
	@Override
	public void setJLabel(JLabel label) {
		this.label = label;
		this.label.setLabelFor(this);
	}
	
	/* (non-Javadoc)
	 * @see com.aastorp.config.Setting#getValue()
	 */
	@Override
	public Object getValue() {
		return this.getText();
	}

	/* (non-Javadoc)
	 * @see com.aastorp.config.Setting#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(Object value) {
		this.setText(String.valueOf(value));
		Window windowAncestor = SwingUtilities.getWindowAncestor(this);
		if (windowAncestor == null)
			return; //since there's no window on the screen, there's no reason to resize anything.
		windowAncestor.pack(); //resizes all components to fit their values.
	}

	/**
	 * Gets the setting category.
	 *
	 * @return the settingCategory
	 */
	public SettingCategory getSettingCategory() {
		return settingCategory;
	}

	/**
	 * Sets the setting category.
	 *
	 * @param settingCategory the settingCategory to set
	 */
	public void setSettingCategory(SettingCategory settingCategory) {
		this.settingCategory = settingCategory;
	}
	
	/**
	 * Gets the Type of this Setting, usually as determined by SettingClass.getClass().
	 * 
	 * @return The Type of this Setting.
	 */
	public Class<?> getSettingType() {
		return this.getClass();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("TextFieldSetting [settingCategory=");
		sb.append(settingCategory);
		if (antiRecursion) {
			return sb.toString();
		}
		antiRecursion = true;
		sb.append(", label=");
		sb.append(label);
		antiRecursion = false;
		sb.append(", getValue()=");
		sb.append(getValue());
		sb.append(", getSettingCategory()=");
		sb.append(getSettingCategory());
		sb.append(", getSettingType()=");
		sb.append(getSettingType());
		sb.append("]");
		return sb.toString();
	}

}
