package com.sinosoft.framework.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class ObjectUtil {
	private static final Logger logger = LoggerFactory.getLogger(ObjectUtil.class);

	public static Comparator<String> ASCStringComparator = new Comparator() {
		public int compare(String s1, String s2) {
			if (s1 == null) {
				if (s2 == null) {
					return 0;
				}
				return -1;
			}

			if (s2 == null) {
				return 1;
			}
			return s1.compareTo(s2);
		}

		@Override
		public int compare(Object o1, Object o2) {
			return compare(o1, o2);
		}
	};

	public static Comparator<String> DESCStringComparator = new Comparator() {
		public int compare(String o1, String o2) {
			return -ObjectUtil.ASCStringComparator.compare(o1, o2);
		}

		@Override
		public int compare(Object o1, Object o2) {
			return compare(o1, o2);
		}
	};

	public static boolean in(Object[] args) {
		if ((args == null) || (args.length < 2)) {
			return false;
		}
		Object arg1 = args[0];
		for (int i = 1; i < args.length; i++) {
			if (arg1 == null) {
				if (args[i] == null)
					return true;
			} else {
				if (arg1.equals(args[i])) {
					return true;
				}

				if ((!arg1.getClass().isArray()) && (args[i].getClass().isArray())) {
					for (Object obj : (Object[]) args[i]) {
						if (arg1.equals(obj)) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	public static boolean notIn(Object[] args) {
		return !in(args);
	}

	public static boolean empty(Object obj) {
		if (obj == null) {
			return true;
		}
		if ((obj instanceof String)) {
			return obj.equals("");
		}
		if ((obj instanceof Number)) {
			return ((Number) obj).doubleValue() == 0.0D;
		}
		if (obj.getClass().isArray()) {
			return Array.getLength(obj) == 0;
		}
		if ((obj instanceof Collection)) {
			return ((Collection) obj).size() == 0;
		}
		return false;
	}

	public static boolean notEmpty(Object obj) {
		return !empty(obj);
	}

	public static boolean equal(Object obj1, Object obj2) {
		if (obj1 == obj2) {
			return true;
		}
		if (obj1 == null) {
			return obj2 == null;
		}

		return obj1.equals(obj2);
	}

	public static boolean notEqual(Object obj1, Object obj2) {
		return !equal(obj1, obj2);
	}

	public static Number minNumber(double[] args) {
		if ((args == null) || (args.length == 0)) {
			return null;
		}
		Number minus = null;
		double[] arrayOfDouble = args;
		int j = args.length;
		for (int i = 0; i < j; i++) {
			double t = arrayOfDouble[i];
			if (minus == null) {
				minus = Double.valueOf(t);
			} else if (minus.doubleValue() > t) {
				minus = Double.valueOf(t);
			}
		}
		return minus;
	}

	public static Number maxNumber(double[] args) {
		if ((args == null) || (args.length == 0)) {
			return null;
		}
		Number max = null;
		double[] arrayOfDouble = args;
		int j = args.length;
		for (int i = 0; i < j; i++) {
			double t = arrayOfDouble[i];
			if (max == null) {
				max = Double.valueOf(t);
			} else if (max.doubleValue() < t) {
				max = Double.valueOf(t);
			}
		}
		return max;
	}

	public static <T extends Comparable<T>> T min(T[] args) {
		if ((args == null) || (args.length == 0)) {
			return null;
		}
		Comparable minus = null;
		Comparable[] arrayOfComparable = args;
		int j = args.length;
		for (int i = 0; i < j; i++) {
			Comparable t = arrayOfComparable[i];
			if ((minus == null) && (t != null)) {
				minus = t;
			} else if (minus.compareTo(t) > 0) {
				minus = t;
			}
		}
		return (T) minus;
	}

	public static <T extends Comparable<T>> T max(T[] args) {
		if ((args == null) || (args.length == 0)) {
			return null;
		}
		Comparable max = null;
		Comparable[] arrayOfComparable = args;
		int j = args.length;
		for (int i = 0; i < j; i++) {
			Comparable t = arrayOfComparable[i];
			if ((max == null) && (t != null)) {
				max = t;
			} else if (max.compareTo(t) < 1) {
				max = t;
			}
		}
		return (T) max;
	}

	public static <T> T ifEmpty(T obj1, T obj2) {
		return empty(obj1) ? obj2 : obj1;
	}

	public static String toString(Object obj) {
		return obj == null ? null : obj.toString();
	}

	public static <T> List<T> toList(T[] args) {
		ArrayList list = new ArrayList();
		Object[] arrayOfObject = args;
		int j = args.length;
		for (int i = 0; i < j; i++) {
			Object t = arrayOfObject[i];
			list.add(t);
		}
		return list;
	}


	public static int[] toIntArray(Object[] arr) {
		int[] arr2 = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			arr2[i] = Integer.parseInt(String.valueOf(arr[i]));
		}
		return arr2;
	}

	public static long[] toLongArray(Object[] arr) {
		long[] arr2 = new long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			arr2[i] = Long.parseLong(String.valueOf(arr[i]));
		}
		return arr2;
	}

	public static float[] toFloatArray(Object[] arr) {
		float[] arr2 = new float[arr.length];
		for (int i = 0; i < arr.length; i++) {
			arr2[i] = Float.parseFloat(String.valueOf(arr[i]));
		}
		return arr2;
	}

	public static double[] toDoubleArray(Object[] arr) {
		double[] arr2 = new double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			arr2[i] = Double.parseDouble(String.valueOf(arr[i]));
		}
		return arr2;
	}

	public static boolean[] toBooleanArray(Object[] arr) {
		boolean[] arr2 = new boolean[arr.length];
		for (int i = 0; i < arr.length; i++) {
			arr2[i] = Boolean.valueOf(String.valueOf(arr[i])).booleanValue();
		}
		return arr2;
	}

	public static String[] toStringArray(Object[] arr) {
		String[] arr2 = new String[arr.length];
		for (int i = 0; i < arr.length; i++) {
			arr2[i] = String.valueOf(arr[i]);
		}
		return arr2;
	}

	public static <T> T[] sort(T[] arr, Comparator<T> c) {
		if (arr == null || arr.length == 0) {
			return arr;
		}
		// T[] a = (T[]) Array.newInstance(arr.getClass().getComponentType(),
		// arr.length);
		// int j = arr.length;
		// for (int i = 0; i < j; i++) {
		// a[(i++)] = arr[i];
		// }
		Arrays.sort(arr, c);
		return arr;
	}

	public static <T> List<T> sort(List<T> arr, Comparator<T> c) {
		if ((arr == null) || (arr.size() == 0)) {
			return arr;
		}
		try {
			T[] a = (T[]) Array.newInstance(arr.toArray().getClass().getComponentType(), arr.size());
			a = arr.toArray(a);
			Arrays.sort(a, c);
			List list = (List) arr.getClass().newInstance();
			for (T t : a) {
				list.add(t);
			}
			return list;
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

}