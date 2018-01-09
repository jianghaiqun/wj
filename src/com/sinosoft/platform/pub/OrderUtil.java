package com.sinosoft.platform.pub;

import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;

import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.StringUtil;

public class OrderUtil {
	/**
	 * 更新table指定的表中OrderFlag在orders之内的记录，使这些记录排列在OrderFlag=targetOrder的记录之后(或之前，由type决定) <br>
	 * wherePart用于指定更新OrderFlag的范围.<br>
	 * 要求排序字段名为OrderFlag，排序字段默认值应为OrderUtil.getDefaultOrder()
	 */
	public static boolean updateOrder(String table, String type, String targetOrder, String orders, String wherePart) {
		return updateOrder(table, "OrderFlag", type, targetOrder, orders, wherePart);
	}

	public static boolean updateOrder(String table, String column, String type, String targetOrder, String orders, String wherePart) {
		return updateOrder(table, column, type, targetOrder, orders, wherePart, null);
	}

	public static boolean updateOrder(String table, String column, String type, String targetOrder, String orders, String wherePart, Transaction tran) {
		if (StringUtil.isEmpty(targetOrder) || targetOrder.length() < 13) {// 拖到最前时会有这种现象
			targetOrder = "" + getDefaultOrder();
		}
		if (!StringUtil.checkID(targetOrder)) {
			return false;
		}
		if (!StringUtil.checkID(orders)) {
			return false;
		}
		if (StringUtil.isEmpty(wherePart)) {
			wherePart = "1=1";
		}

		String[] arrtmp = orders.split(",");
		arrtmp = (String[]) ArrayUtils.removeElement(arrtmp, targetOrder);
		long[] arr = new long[arrtmp.length + 1];
		for (int i = 0; i < arrtmp.length; i++) {
			arr[i] = Long.parseLong(arrtmp[i]);
		}
		long target = Long.parseLong(targetOrder);
		arr[arrtmp.length] = target;
		Arrays.sort(arr);

		boolean bFlag = true;// 有连接传入
		if (tran == null) {
			tran = new Transaction();
			bFlag = false;
		}
		QueryBuilder qb = null;
		boolean flag = type.equals("After");
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == target) {
				if (flag) {
					target = target + arr.length - i - 1;
					int d = arr.length - 1;
					for (int j = 0; j < arr.length; j++) {
						if (j != i) {
							qb =
									new QueryBuilder("update " + table + " set " + column + "=? where " + column + "=?", (target - d) * 10L);
							d--;
						} else {
							qb =
									new QueryBuilder("update " + table + " set " + column + "=? where " + column + "=?", target * 10L);
						}
						qb.add(arr[j]);
						tran.add(qb);
					}
					// 排序在target之后的有i个
					for (int j = 0; j < i; j++) {
						if (arr[j] + 1 == arr[j + 1]) {
							continue;
						}
						qb =
								new QueryBuilder("update " + table + " set " + column + "=" + column + "-? where " + column
										+ " between ? and ? and " + wherePart);
						qb.add(j + 1);
						qb.add(arr[j]);
						qb.add(arr[j + 1]);
						tran.add(qb);
					}

					// 之前的有arr.length-i-1个
					for (int j = arr.length - 1; j > i; j--) {
						if (arr[j] == arr[j - 1] + 1) {
							continue;
						}
						qb =
								new QueryBuilder("update " + table + " set " + column + "=" + column + "+? where " + column
										+ " between ? and ? and " + wherePart);
						qb.add(arr.length - j);
						qb.add(arr[j - 1]);
						qb.add(arr[j]);
						tran.add(qb);
					}
				} else {
					target = target - i;
					int d = 1;
					for (int j = 0; j < arr.length; j++) {
						if (j != i) {
							qb =
									new QueryBuilder("update " + table + " set " + column + "=? where " + column + "=?", (target + d) * 10L);
							d++;
						} else {
							qb =
									new QueryBuilder("update " + table + " set " + column + "=? where " + column + "=?", target * 10L);
						}
						qb.add(arr[j]);
						tran.add(qb);
					}

					// 排序在target之后的有i个
					for (int j = 0; j < i; j++) {
						if (arr[j] + 1 == arr[j + 1]) {
							continue;
						}
						qb =
								new QueryBuilder("update " + table + " set " + column + "=" + column + "-? where " + column
										+ " between ? and ? and " + wherePart);
						qb.add(j + 1);
						qb.add(arr[j]);
						qb.add(arr[j + 1]);
						tran.add(qb);
					}

					// 之前的有arr.length-i-1个
					for (int j = arr.length - 1; j > i; j--) {
						if (arr[j] == arr[j - 1] + 1) {
							continue;
						}
						qb =
								new QueryBuilder("update " + table + " set " + column + "=" + column + "+? where " + column
										+ " between ? and ? and " + wherePart);
						qb.add(arr.length - j);
						qb.add(arr[j - 1]);
						qb.add(arr[j]);
						tran.add(qb);
					}
				}
				qb =
						new QueryBuilder("update " + table + " set " + column + "=" + column + "/10 where " + column + ">? and "
								+ wherePart, target * 9);
				tran.add(qb);
				if (bFlag) {
					return true;
				} else {
					return tran.commit();
				}
			}
		}
		return false;
	}

	private static long currentOrder = System.currentTimeMillis();

	public static synchronized long getDefaultOrder() {
		if (System.currentTimeMillis() <= currentOrder) {
			return ++currentOrder;
		} else {
			return currentOrder = System.currentTimeMillis();
		}
	}
}
