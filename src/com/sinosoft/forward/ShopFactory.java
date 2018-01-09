package com.sinosoft.forward;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class ShopFactory {
	private static final Logger logger = LoggerFactory.getLogger(ShopFactory.class);

	private static HashMap<String, Object>	actionMaps	= new HashMap<String, Object>();
	private static HashMap<String, String>	actions		= getConfigMap("action");

	public static ShopAction getAction(String function) {
		ShopAction action = null;
		synchronized (actionMaps) {
			action = (ShopAction) actionMaps.get(function);
			if (action == null) {
				String className = (String) actions.get(function);
				action = init(className);
				if (action != null) {
					actionMaps.put(function, action);
				}
			}
		}
		return action;
	}

	private static ShopAction init(String className) {
		try {
			Class c = Class.forName(className);
			return (ShopAction) c.newInstance();
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			return null;
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
			return null;
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public static HashMap<String, String> getConfigMap(String filename) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("test", "com.sinosoft.forward.TestAction"); 
		map.put("Question", "com.sinosoft.message.QuestionAction"); 
		map.put("Answer", "com.sinosoft.message.AnswerAction"); 
		map.put("BkEdit", "com.sinosoft.message.BKEditSaveAction"); 
		map.put("BkSave", "com.sinosoft.message.BKSaveAction");
		map.put("TagCloud", "com.sinosoft.message.TagCloudAction"); 
		map.put("present", "com.sinosoft.cms.present.PresentAction"); 
		return map;
	}
}
