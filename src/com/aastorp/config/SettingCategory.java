/**
 * 
 */
package com.aastorp.config;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.LayoutManager;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import com.aastorp.bibliothecaaastorpiana.databases.SelectQuery;
import com.aastorp.bibliothecaaastorpiana.databases.WhereClause;
import com.aastorp.bibliothecaaastorpiana.interaction.MessageBoxes;
import com.aastorp.bibliothecaaastorpiana.layouts.MagicGridLayout;
import com.aastorp.logger.Logger;

// TODO: Auto-generated Javadoc
/**
 * Represents a Category of Setting, and provides JPanels for Settings' JTabbedPane.
 *
 * @author Bjørn Aastorp
 */
public class SettingCategory extends JPanel {
	
	/**  The logger. */
	private Logger l = new Logger(SettingCategory.class, Integer.valueOf(Logger.INFO), true, new File("config.log"));
	
	/**  The database id of the SettingCategory. */
	private int id;
	
	/**  The name of the tab the settings in this SettingCategory will display in. */
	private String friendlyName;

	private boolean antiRecursion;
	
	
	/**
	 *  A List of the SettingGroups belonging to this SettingCategory. Each displays in its own SettingGroupBox.
	 * 
	 * @param id the id
	 * @wbp.parser.constructor
	 */ 
	//List<?> settingGroups; 
	

	public SettingCategory(int id) {
//		this(id, new FlowLayout(FlowLayout.LEADING, 5, 5));
		this(id, new MagicGridLayout());
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
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
	}
	
	
	@Override
	public Dimension getPreferredSize() {
//		Dimension superPreferredSize = super.getPreferredSize();
//		Dimension preferredSize = new Dimension();
//		
//		preferredSize.setSize(superPreferredSize.width, Math.max(80, superPreferredSize.height));
//		return preferredSize;
//		---------------------
//		Above code is strange and useless? I'll just super.getPreferredSize()...
		return super.getPreferredSize();
//		not useless anymore... now it makes sure the window is tall enough.
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
		final String F = "add";
		//int columnCount = this.calculateNewColumnCount();
		//((GridLayout)this.getLayout()).setColumns(columnCount);
		try {
			//this.setFriendlyName(String.valueOf(columnCount));
			Component addedComp = super.add(comp);
			if (this.getLayout() instanceof MagicGridLayout) { 
				((MagicGridLayout)this.getLayout()).autoResize(this);
			}
			return addedComp;
		} catch (NullPointerException e) {
			throw new NullPointerException("This component doesn't exist.");
		} catch (Exception e) {
			l.e(F, e);
			throw e;
		}
		
	}
	
	/**
	 * @see com.aastorp.config.SettingCategory#calculateNewColumnCount(int)
	 * 
	 * @return 
	 */
	private int calculateNewColumnCount() {
		// TODO Auto-generated method stub
		return 1;
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
	 * @param friendlyName The new friendlyName to set
	 */
	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}
	
	/**
	 * Returns a list of the Settings in this SettingCategory
	 * 
	 * @return The Settings in this SettingCategory
	 */
	public List<Setting> getSettings() {
		List<Component> components = new LinkedList<Component>();
		List<Setting> settings = new LinkedList<Setting>();
		components = Arrays.asList(this.getComponents());
		for (Component c : components) {
			StringBuilder sb = new StringBuilder();
			sb.append(c);
			sb.append("\r\n");
			sb.append(" IN " + this.getName());
			sb.append(" IS ");
			if (c.getClass() != SettingPanel.class) {
				sb.append(" NOT ");
			} else {
				settings.add(((SettingPanel)c).getSetting());
			}
			sb.append("a SettingPanel");
			//JOptionPane.showMessageDialog(null, sb.toString());
		}
		
		return settings;
	}
	
	@Override
	public void setVisible(boolean visibility) {
		final String F = "setVisible";
		LayoutManager layout = this.getLayout();
//		if (layout instanceof MagicGridLayout) {
//			l.d(F, "Trying to resize window...");
//			((MagicGridLayout) layout).autoResize(this, true);
//		} else {
//			Common.confirm("", "layout isn't a MagicGridLayout! It's a " + layout.getClass().getName());
//		}
		
		/*
		 * 
		 * OH MY GOD WHY DOES THE WINDOW KEEP GETTING BIGGER WHEN YOU SWITCH TABS?!
		 * PREFERREDSIZE KEEPS RETURNING HIGHER AND HIGHER NUMBERS!!!
		 * I NEED TO WRITE MY OWN PREFERREDSIZE METHOD!!!
		 * OR THE PROBLEM MIGHT BE IN CONFIG->RESIZEPARENT()! IT DOES SETPREFERREDSIZE!!
		 * Yes, the problem was in Config->ResizeParent(). Still, a new and improved 
		 * getPreferredSize() in ConfigFrame might be a good idea, as the default one
		 * returns a height that is far too small to show the Apply/Cancel buttons.
		 * 
		 */
		Window windowAncestor = SwingUtilities.getWindowAncestor(this);
		if (layout != null && layout instanceof MagicGridLayout) {
			if (this.antiRecursion == false) {
				this.antiRecursion = true;
				if (windowAncestor != null) {
					((MagicGridLayout)layout).autoResize(this);
					
//					MessageBoxes.confirm("Preferred size of " + String.valueOf(((SettingPanel) this.getComponents()[0]).getLabel()), String.valueOf(this.getComponents()[0].getPreferredSize()));
					Config.getInstance().resizeParent(this);
				}
				super.setVisible(visibility);
			} else {
				super.setVisible(visibility);
				this.antiRecursion = false;
			}
		}
		
//		if (!Arrays.asList(this.getComponents()).contains(this.btnApply)) super.addImpl(this.btnApply, null, -1);
	}
}
