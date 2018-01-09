package com.sinosoft.framework.clustering.server;

import com.sinosoft.framework.Constant;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;

public class ClusteringData {

	public static final String RESULT_NULL = "Null";

	public static final String RESULT_NOTFOUND = "NotFound";

	public static final String RESULT_EMPTYKEY = "EmptyKey";

	public static final String RESULT_SUCCESS = "Success";

	public static final String RESULT_TRUE = "True";

	public static final String RESULT_FALSE = "False";

	public static final String RESULT_NOSUPPORTACTION = "NoSupportAction";

	public static final String RESULT_NOTNUMBER = "NotNumber";

	protected static Mapx data = new Mapx();

	public static String get(String key) {
		return data.getString(key);
	}

	public static synchronized void put(String key, String value) {
		data.put(key, value);
	}

	public static synchronized void remove(String key) {
		data.remove(key);
	}

	public static boolean containsKey(String key) {
		return data.containsKey(key);
	}

	public static Object[] getAllKeys() {
		return data.keyArray();
	}

	public static String dealRequest(String type, String key, String action, String value) {
		if ("Command".equalsIgnoreCase(type)) {
			return dealCommand(key, value);
		} else {
			if ("Get".equals(action)) {
				Object obj = data.get(key);
				if (obj == null) {
					return RESULT_NULL + "\n";
				} else {
					return RESULT_SUCCESS + "\n" + obj.toString();
				}
			} else if ("Put".equals(action)) {
				if (value == null || value.equals(Constant.Null)) {
					value = null;
				}
				if (StringUtil.isEmpty(key)) {
					return RESULT_EMPTYKEY + "\n";
				}
				synchronized (data) {
					data.put(key, value);
				}
				return RESULT_SUCCESS + "\n";
			} else if ("Add".equals(action)) {
				if (value == null || value.equals(Constant.Null)) {
					value = null;
				}
				if (StringUtil.isEmpty(key)) {
					return RESULT_EMPTYKEY + "\n";
				}
				synchronized (data) {
					Double d = (Double) data.get(key);
					if (d == null) {
						d = new Double(0);
					}
					d = new Double(d.doubleValue() + Double.parseDouble(value));
					data.put(key, d);
					return RESULT_SUCCESS + "\n" + d.doubleValue();
				}
			} else if ("AddAverage".equals(action)) {
				if (value == null || value.equals(Constant.Null)) {
					value = "0|0";
				}
				if (StringUtil.isEmpty(key)) {
					return RESULT_EMPTYKEY + "\n";
				}
				String[] numbers = value.split("\\|");
				if (numbers.length != 2) {
					return RESULT_NOTNUMBER + "\n";
				}
				synchronized (data) {
					double[] arr = (double[]) data.get(key);
					if (arr == null) {
						arr = new double[] { 0, 0 };
					}
					arr[0] += Double.parseDouble(numbers[0]);
					arr[1] += Double.parseDouble(numbers[1]);
					data.put(key, arr);
					return RESULT_SUCCESS + "\n" + (arr[1] == 0 ? "0" : String.valueOf(arr[0] / arr[1]));
				}
			} else if ("ContainsKey".equals(action)) {
				return data.containsKey(key) ? RESULT_TRUE + "\n" : RESULT_FALSE + "\n";
			} else if ("Remove".equals(action)) {
				synchronized (data) {
					data.remove(key);
				}
				return RESULT_SUCCESS + "\n";
			} else {
				return RESULT_NOSUPPORTACTION + "\n";
			}
		}
	}

	public static String dealCommand(String key, String value) {
		return "";
	}
}
