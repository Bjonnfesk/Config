package com.aastorp.config;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

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
	 * @return The setting produced by the factory
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws ClassNotFoundException
	 */
	public static Setting createSetting(String settingType, String name, Object value, String friendlyName, int settingCategoryId) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
		Class<?> settingClass = Class.forName("com.aastorp.config." + settingType + "Setting");
		if (settingClass == null) {
			
			//if settingClass is null, the type of Setting is invalid.
			
			throw new IllegalArgumentException("Class " + settingType + " does not exist!");
		}
		if (Arrays.asList(settingClass.getInterfaces()).contains(Setting.class)) {
			
			//if the settingClass implements Setting, call the constructor for this type of Setting through Reflection...
			
			Constructor<?> constructor = settingClass.getConstructor(String.class, Object.class, String.class, int.class);
			
			//return the result of the constructor!
			
			return (Setting) constructor.newInstance( new Object[] {name, value, friendlyName, settingCategoryId});
		} else {
			
			//if the settingClass does not implement Setting, complain about it...
			
			throw new IllegalArgumentException("Class " + settingClass.getName() + " does not implement Setting!");
		}
	}
}
