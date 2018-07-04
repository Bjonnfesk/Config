/**
 * 
 */
package com.aastorp.config;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.LayoutManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import com.aastorp.bibliothecaaastorpiana.common.Common;
import com.aastorp.bibliothecaaastorpiana.databases.SelectQuery;
import com.aastorp.bibliothecaaastorpiana.databases.WhereClause;
import com.aastorp.linguistics.Wrapper;

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
//		this(id, new FlowLayout(FlowLayout.LEADING, 5, 5));
		this(id, new GridLayout(0, 1));
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
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
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
		switch (this.getComponentCount()) {
		case 0:
			this.setLayout(new GridLayout(1, 0));
			break;
		case 1:
			this.setLayout(new GridLayout(0, 1));
			break;
		case 2: 
			this.setLayout(new GridLayout(0, 2));
			break;
		case 3:
			this.setLayout(new GridLayout(0, 3));
			break;
		case 4:
			this.setLayout(new GridLayout(0, 2));
		case (5-1000):
			JOptionPane.showMessageDialog(null, "that's a lot!");
			this.setLayout(new GridLayout(0, 4));
		}
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


	/**
	 * @return The friendlyName of the SettingCategory
	 */
	public String getFriendlyName() {
		return friendlyName;
	}


	/**
	 * @param friendlyName The new friendlyName to set.
	 */
	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}

}
