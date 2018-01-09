package com.sinosoft.framework.collection;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;

/**
 * 具有最大长度，支持按索引存取，支持排序的队列。<br>
 * 
 */
public class Queuex implements Serializable {
	private static final long serialVersionUID = 1L;

	private Object[] arr;

	private int max;

	private int pos;

	private int size;

	private ExitEventListener listener;

	public Queuex(int max) {
		this.max = max;
		arr = new Object[max];
	}

	public synchronized void sort(Comparator comparator) {
		Arrays.sort(arr, comparator);
		pos = 0;
	}

	public synchronized Object get(int index) {
		if (size <= index) {
			throw new RuntimeException("超出队列索引长度：" + index);
		}
		return arr[(pos + index) % max];
	}

	public synchronized Object push(Object o) {
		if (size == max) {
			Object r = arr[pos];
			arr[pos] = o;
			pos = (pos + 1) % max;
			if (listener != null) {
				listener.onExit(null, r);
			}
			return r;
		} else {
			arr[(pos + size) % max] = o;
			size++;
			return null;
		}
	}

	public synchronized boolean contains(Object v) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == v) {
				return true;
			}
		}
		return false;
	}

	public synchronized Object remove(Object v) {
		for (int i = 0; i < size; i++) {
			if (get(i) == v) {
				return remove(i);
			}
		}
		return null;
	}

	public synchronized Object remove(int index) {
		if (size <= index) {
			throw new RuntimeException("超出队列索引长度：" + index);
		}
		Object r = get(index);
		index = (index + pos) % max;
		Object[] newarr = new Object[max];
		if (pos == 0) {
			System.arraycopy(arr, 0, newarr, 0, index);
			System.arraycopy(arr, index + 1, newarr, index, max - index - 1);
		} else {
			if (index >= pos) {
				System.arraycopy(arr, pos, newarr, 0, index - pos);
				System.arraycopy(arr, index + 1, newarr, index - pos, max - index - 1);
				System.arraycopy(arr, 0, newarr, max - pos - 1, pos);
			} else {
				System.arraycopy(arr, pos, newarr, 0, max - pos);
				System.arraycopy(arr, 0, newarr, max - pos, index);
				System.arraycopy(arr, index + 1, newarr, max - pos + index, pos - index);
			}
			pos = 0;
		}
		arr = newarr;
		size--;
		return r;
	}

	public synchronized void clear() {
		arr = new Object[max];
		size = 0;
	}

	public int size() {
		return size;
	}

	/**
	 * 设置换出事件监听器,当键值队换出调用
	 */
	public void setExitEventListener(ExitEventListener listener) {
		this.listener = listener;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		for (int i = 0; i < 20 && i < size; i++) {
			if (i != 0) {
				sb.append(" , ");
			}
			sb.append(get(i));
		}
		sb.append("}");
		return sb.toString();
	}

	public int getMax() {
		return max;
	}
}
