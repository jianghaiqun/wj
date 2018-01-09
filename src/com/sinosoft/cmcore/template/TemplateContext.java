package com.sinosoft.cmcore.template;

import java.util.ArrayList;

import com.sinosoft.cmcore.tag.ModifierHandler;
import com.sinosoft.cmcore.tag.RootModifierHandler;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.utility.CaseIgnoreMapx;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.Treex;
import com.sinosoft.framework.utility.Treex.TreeNode;

public class TemplateContext {
	ArrayList errors = new ArrayList();

	boolean isSSIContext = false;

	Treex tree = null;

	TreeNode current = null;

	public TemplateContext() {
		tree = new Treex();
		current = tree.getRoot();
		addModifierHandler(new RootModifierHandler());
	}

	public void setSSIContext(boolean isSSIContext) {
		this.isSSIContext = isSSIContext;
	}

	public void addError(String message) {
		errors.add(message);
		throw new RuntimeException();
	}

	public ArrayList getErrors() {
		return errors;
	}

	public boolean isSSIContext() {
		return isSSIContext;
	}

	public String getHolderValue(String holder) {
		if (holder.startsWith("${")) {
			holder = holder.substring(2);
		}
		if (holder.endsWith("}")) {
			holder = holder.substring(0, holder.length() - 1);
		}
		String modifier = null;
		if (holder.indexOf('|') > 0) {
			modifier = holder.substring(holder.indexOf('|') + 1);
			holder = holder.substring(0, holder.indexOf('|'));
		}
		TreeNode node = current;
		String parentPrefix = "parent.";
		while (true) {
			if (holder.toLowerCase().startsWith(parentPrefix)) {
				holder = holder.substring(parentPrefix.length());
				if (node.isRoot()) {
					break;
				}
				node = node.getParent();
			} else {
				break;
			}
		}
		return String.valueOf(evalHolder(node, holder, modifier));
	}

	public static Object evalHolder(TreeNode node, String holder, String modifier) {
		Object value = null;
		String[] arr = holder.split("\\.");
		String varName = arr[0];
		String fieldName = null;
		if (arr.length > 1) {
			fieldName = arr[1];
		}
		TreeNode tn = node;
		while (true) {
			Mapx map = (Mapx) tn.getData();
			Object data = map.get(varName);
			if (data == null) {
				tn = tn.getParent();
			} else {
				if (data instanceof Mapx) {
					value = ((Mapx) data).get(fieldName);
					break;
				}
				if (data instanceof DataRow) {
					value = ((DataRow) data).get(fieldName);
					break;
				}
			}
			if (tn == null || tn.isRoot()) {
				break;
			}
		}
		// 解析修饰析
		Mapx modifiers = parseModifiers(modifier);
		tn = node;
		while (true) {
			Mapx map = (Mapx) tn.getData();
			ModifierHandler mh = (ModifierHandler) map.get(ModifierHandlerKeyName);
			if (mh != null) {
				value = mh.deal(value, modifiers);
			}
			tn = tn.getParent();
			if (tn == null || tn.isRoot()) {
				break;
			}
		}
		return value;
	}

	public static Mapx parseModifiers(String str) {
		return null;
	}

	public void addDataVariable(String name, DataRow dr) {
		Mapx map = (Mapx) current.getData();
		if (map == null) {
			map = new CaseIgnoreMapx();
			tree.getRoot().setData(map);
		}
		map.put(name, dr);
	}

	public void addDataVariable(String name, Mapx dr) {
		Mapx map = (Mapx) current.getData();
		if (map == null) {
			map = new CaseIgnoreMapx();
			tree.getRoot().setData(map);
		}
		map.put(name, dr);
	}

	private static final String ModifierHandlerKeyName = "_Zving_Modifier";

	public void addModifierHandler(ModifierHandler handler) {
		Mapx map = (Mapx) current.getData();
		if (map == null) {
			map = new CaseIgnoreMapx();
			tree.getRoot().setData(map);
		}
		map.put(ModifierHandlerKeyName, handler);
	}

	public void addTagNode() {
		current = current.addChild(new CaseIgnoreMapx());
	}

	public void removeTagNode() {
		current.getParent().removeChild(current);
		current = current.getParent();
	}
}
