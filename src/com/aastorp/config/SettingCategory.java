/**
 * 
 */
package com.aastorp.config;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.LayoutManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import com.aastorp.bibliothecaaastorpiana.databases.SelectQuery;
import com.aastorp.bibliothecaaastorpiana.databases.WhereClause;

// TODO: Auto-generated Javadoc
/**
 * Represents a Category of Setting, and provides JPanels for Settings' JTabbedPane.
 *
 * @author Bjørn Aastorp
 */
public class SettingCategory extends JPanel {
	
	/**  The database id of the SettingCategory. */
	private int id;
	
	/**  The name of the tab the settings in this SettingCategory will display in. */
	private String friendlyName;
	
	/**
	 *  A List of the SettingGroups belonging to this SettingCategory. Each displays in its own SettingGroupBox.
	 * 
	 * @param id the id
	 */ 
	//List<?> settingGroups; 
	

	public SettingCategory(int id) {
		this(id, new FlowLayout(FlowLayout.LEADING, 5, 5));
		//this(id, new GridLayout(0, 4));
	}


	/**
	 * Instantiates a new setting category.
	 *
	 * @param id the id
	 * @param layout the layout
	 */
	public SettingCategory(int id, LayoutManager layout) {
		super(layout);
		this.setId(id);
		SelectQuery selectQuery = new SelectQuery(
				"settingCategory",
				new String[] {
						"friendlyName"
				},
				new WhereClause[] {
						new WhereClause(
								"id",
								id
							)
				});
		ResultSet rs = Config.getInstance().getSettingDatabase().select(selectQuery);
		try {
			if (!rs.next()) {
				this.setName("{missing name for id=" + id + "}");
				return;
			}
			this.setName(rs.getString("friendlyName"));
		} catch (HeadlessException e) {

		} catch (SQLException e) {
			
		}
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	/* (non-Javadoc)
	 * @see java.awt.Container#add(java.awt.Component)
	 */
	@Override
	public Component add(Component comp) {
		//Complement functionality...
		try {
			return super.add(comp);
		} catch (NullPointerException e) {
			throw new NullPointerException("This component doesn't exist.");
		}
		
	}

	/**
	 * Gets the tab name.
	 *
	 * @return The name of the tab the settings in this SettingCategory will display in
	 * @deprecated Should never have existed.
	 */
	@Deprecated
	public String getTabName() {
		final String F = "getTabName";
		return friendlyName;
	}

	/**
	 * Sets the tab name.
	 *
	 * @param tabName The new name of the tab the settings in this SettingCategory will display in
	 * @deprecated Should never have existed.
	 */
	@Deprecated
	public void setTabName(String tabName) {
		final String F = "setTabName";
		this.friendlyName = tabName;
	}

	/**
	 * Gets the id.
	 *
	 * @return The database id of the SettingCategory
	 */
	public int getId() {
		final String F = "getId";
		return id;
	}

}
