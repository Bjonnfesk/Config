/**
 * 
 */
package com.aastorp.config;

import java.awt.Component;
import java.awt.LayoutManager;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.aastorp.bibliothecaaastorpiana.common.Common;
import com.aastorp.bibliothecaaastorpiana.layouts.MagicGridLayout;
import com.aastorp.logger.Logger;

// TODO: Auto-generated Javadoc
/**
 * 
 * Wraps a collection of Setting objects in a HashMap, and also provides the JTabbedPane for the GUI.
 * @author Bjørn Aastorp
 *
 */
public class Settings extends JTabbedPane {
	
	/** The l. */
	private Logger l = new Logger(this.getClass(), Logger.INFO, true, new File("config.log"));
	
	/** The settings. Why do we need this? It should probably be chopped, since the values 
	 * of the Settings are already stored by the JComponents. */
	private HashMap<String, Object> settings = new HashMap<String, Object>();
	
	/**
	 * Adds the.
	 *
	 * @param setting the setting
	 */
	public void add(Setting setting) {
		this.settings.put(setting.getName(), setting.getValue());
		SettingCategory settingCategory = this.addSettingCategory(setting.getSettingCategory());
		
		SettingPanel settingPanel = new SettingPanel(setting);
		settingCategory.add(settingPanel);
		this.validate();
	}
	
	/**
	 * Adds the setting category.
	 *
	 * @param settingCategory the setting category
	 * @return the setting category
	 */
	public SettingCategory addSettingCategory(SettingCategory settingCategory) {
		final String F = "addSettingCategory";
		if (settingCategory == null) {
			l.e(F, "settingCategory is null!");
			return null;
		}
		try {
			if (this.getSettingCategoryByName(settingCategory.getName()) != null) {
				l.d(F, "settingCategory " + settingCategory.getName() + " already exists");
				settingCategory = (SettingCategory) this.getSettingCategoryByName(settingCategory.getName());
			} else {
				this.add(settingCategory);
				settingCategory = (SettingCategory) this.getSettingCategoryByName(settingCategory.getName());
				l.d(F, "Added settingCategory " + settingCategory.getName());
			}
		} catch (IndexOutOfBoundsException e) {
			/* Normally, the preceeding else handles settingCategories that do not exist,
			 * automatically adding them. However, for good measure, this does the same.*/
			this.add(settingCategory);
			settingCategory = (SettingCategory) this.getSettingCategoryByName(settingCategory.getName());
			l.w(F, "IndexOutOfBoundsException: " + e.getMessage());
			l.d(F, "Added settingCategory " + settingCategory.getName());
		}
		return settingCategory;
	}
	
	/** 
	 * Gets the settingCategory at the specified index in Settings.
	 * 
	 * @param index the index
	 * @return the settingCategory
	 */
	public SettingCategory getSettingCategory(int index) {
		if (this.getComponentAt(index) == null) {
			throw new IndexOutOfBoundsException("No settingCategory at index " + index);
		} else {
			return (SettingCategory) this.getComponentAt(index);
		}
	}
	
	/**
	 * Gets the setting category by name.
	 *
	 * @param name the name
	 * @return the setting category by name
	 */
	public JPanel getSettingCategoryByName(String name) {
		final String F = "getSettingCategoryByName";
		l.i(F, "Started");
		name = name.toLowerCase();
		if (this.getTabCount() == 0) {
			l.d(F, "There are no Setting Categories");
			return null; // 	#PrematureOptimisation
		}
		for (int i = 0; i < this.getTabCount(); i++) {
			if (this.getTitleAt(i).toLowerCase().equals(name)) {
				return (JPanel)this.getComponentAt(i);
			}
		}
		l.w(F, "Couldn't get SettingCategory by the name of " + name);
		return null;
	}
	
	/**
	 * Gets the setting by name.
	 *
	 * @param name the name
	 * @return the setting by name
	 */
	public Setting getSettingByName(String name) {
		Setting setting = null;
		for (Setting s : this.getSettings()) {
			if (s.getName().equals(name)) {
				setting = s;
			}
		}
		return setting;
	}

	/**
	 * Gets the settings.
	 *
	 * @return the settings
	 */
	public List<Setting> getSettings() {
		List<Setting> tmpSettings = new ArrayList<Setting>();
		for (int i = 0; i <= this.getTabCount() - 1; i++) {
			//for (SettingCategory category : (List<SettingCategory>)(List<? extends Component>)Arrays.asList(this.getComponents())) { //converts the array of Component[] to a List<SettingCategory> with a hacky double-cast workaround 
			//	tmpSettings.addAll(category.getSettings());
			//}
			List<SettingCategory> settingCategories = (List<SettingCategory>)(List<? extends Component>)Arrays.asList(this.getComponents());
			for (Component possiblyCategory : settingCategories) {
				if (possiblyCategory instanceof SettingCategory) {
					tmpSettings.addAll(((SettingCategory) possiblyCategory).getSettings());
				} else {
					settingCategories.remove(possiblyCategory);
				}
			}
		}
		return tmpSettings;
	}

	/**
	 * Gets the setting categories.
	 *
	 * @return the settingCategories
	 */
	public List<SettingCategory> getSettingCategories() {
		LinkedList<SettingCategory> settingCategories = new LinkedList<SettingCategory>();
		for (Component c : this.getComponents()) {
			if (c.getClass().equals(SettingCategory.class)) {
				settingCategories.add((SettingCategory) c);
			}
		}
		return settingCategories;
	}
	

}
