package com.aastorp.config;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import com.aastorp.logger.Logger;

/**
 * A factory for creating Setting objects.
 */
public class SettingFactory {
	
	/**
	 * Creates a new Setting object.
	 *
	 * @param settingType The type of Setting to instantiate. Valid values are any class that implements the {@link com.aastorp.config.Setting Setting interface} and has a valid constructor.
	 * @param name The name of this Setting.
	 * @param value The value of this Setting.
	 * @param friendlyName The friendly name of this Setting, which will be displayed as its Label (or in the case of certain Setting types, 
	 * @param settingCategoryId The database ID of the SettingCategory this Setting belongs to. 
	 * @return the setting
	 * @throws NoSuchMethodException the no such method exception
	 * @throws SecurityException the security exception
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws InvocationTargetException the invocation target exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static Setting createSetting(String settingType, String name, Object value, String friendlyName, int settingCategoryId) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
		Class<?> settingClass = Class.forName("com.aastorp.config." + settingType + "Setting");
		if (settingClass == null) {
			
			//if settingClass is null, the type of Setting is invalid.
			
			throw new NullPointerException("Setting type " + settingType + " does not exist!");
		}
		if (Arrays.asList(settingClass.getInterfaces()).contains(Setting.class)) {
			
			//if the settingClass implements Setting, call the constructor for this type of Setting through Reflection...
			
			Constructor<?> constructor = settingClass.getConstructor(String.class, Object.class, String.class, int.class);
			return (Setting) constructor.newInstance( new Object[] {name, value, friendlyName, settingCategoryId});
		} else {
			
			//if not, complain...
			
			throw new IllegalArgumentException("Setting type " + settingClass.getName() + " is not a subclass of Setting!");
		}
	}
}
