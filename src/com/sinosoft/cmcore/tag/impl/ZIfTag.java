package com.sinosoft.cmcore.tag.impl;

import com.sinosoft.cmcore.tag.SimpleTag;
import com.sinosoft.cmcore.tag.TagBase;
import com.sinosoft.cmcore.template.TemplateContext;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.utility.Treex;
import com.sinosoft.framework.utility.Treex.TreeNode;
import com.sinosoft.framework.utility.Treex.TreeNodeList;

public class ZIfTag extends SimpleTag {
	public String[] getAllAttributeNames() {
		return new String[] { "condition" };
	}

	public String[] getMandantoryAttributeNames() {
		return new String[] { "condition" };
	}

	public int onTagStart() {
		String condition = attributes.getString("condition");
		Treex tree = parseCondition(condition);
		if (evalCondition(tree.getRoot(), context)) {
			return TagBase.CONTINUE;
		} else {
			return TagBase.SKIP;
		}
	}

	public static boolean evalCondition(TreeNode parent, TemplateContext context) {
		LogicClause clause = (LogicClause) parent.getData();
		if (clause != null && StringUtil.isNotEmpty(clause.getClauseString())) {
			return clause.execute(context);
		} else {// ROOT
			TreeNodeList list = parent.getChildren();
			boolean v = true;
			for (int i = 0; i < list.size(); i++) {
				clause = (LogicClause) list.get(i).getData();
				boolean isOr = clause.isOr;
				if (clause == null || StringUtil.isEmpty(clause.getClauseString())) {
					if (isOr && v) {// 当前值为真且下一子句为或关系，则下一子句不需要再判断
						continue;
					}
					if (!isOr && !v) {// 当前值为假且下一子句为与关系，则下一子句不需要再判断
						continue;
					}
					v = isOr ? v || evalCondition(list.get(i), context) : v && evalCondition(list.get(i), context);
				}
			}
			return v;
		}
	}

	public static Treex parseCondition(String condition) {
		// 解析成一棵逻辑树
		Treex tree = new Treex();
		char[] cs = condition.toCharArray();
		TreeNode parent = tree.getRoot();

		boolean isStringBegin = false;
		boolean isOr = false;
		int clauseStart = -1;
		for (int i = 0; i < cs.length; i++) {
			char c = cs[i];
			if (c == '\"') {
				if (i == 0) {
					isStringBegin = true;
				} else if (c != '\\') {
					if (isStringBegin) {
						isStringBegin = false;
					} else {
						isStringBegin = true;
					}
				}
			}
			if (c != '(' && i == 0) {// 第一个子句
				clauseStart = i;
			}
			if (c != ')' && i == cs.length - 1) {// 结束
				LogicClause clause = new LogicClause();
				String str = condition.substring(clauseStart, i + 1);
				if (str.startsWith("!")) {
					clause.isNot = true;
					str = str.substring(1);
				}
				clause.isOr = isOr;
				clause.setClauseString(str);
				parent.addChild(clause);
				clauseStart = -1;
			}
			if (!isStringBegin) {
				if (c == '(') {// 开始一组条件
					if (i != 0 && cs[i - 1] != '(' && cs[i - 1] != '|' && cs[i - 1] != '&') {// 说明是函数调用
						continue;
					}
					LogicClause clause = new LogicClause();
					clause.isOr = isOr;
					isOr = false;// 括号开始时为and
					parent = parent.addChild(clause);
					clauseStart = i + 1;
				}
				if (c == ')') {// 结束一组条件
					if (i != cs.length - 1 && cs[i + 1] != ')' && cs[i + 1] != '|' && cs[i + 1] != '&') {// 说明是函数调用
						continue;
					}
					if (clauseStart >= 0) {
						LogicClause clause = new LogicClause();
						String str = condition.substring(clauseStart, i);
						if (str.startsWith("!")) {
							clause.isNot = true;
							str = str.substring(1);
						}
						clause.isOr = isOr;
						clause.setClauseString(str);
						parent.addChild(clause);
						clauseStart = -1;
					}
					parent = parent.getParent();
				}
				if (c == '|' && i != 0 && cs[i - 1] == '|') {// 或者
					if (clauseStart >= 0) {
						LogicClause clause = new LogicClause();
						String str = condition.substring(clauseStart, i - 1);
						if (str.startsWith("!")) {
							clause.isNot = true;
							str = str.substring(1);
						}
						clause.isOr = isOr;
						clause.setClauseString(str);
						parent.addChild(clause);
					}
					clauseStart = i + 1;
					isOr = true;
				}
				if (c == '&' && i != 0 && cs[i - 1] == '&') {// 或者
					if (clauseStart >= 0) {
						LogicClause clause = new LogicClause();
						String str = condition.substring(clauseStart, i - 1);
						if (str.startsWith("!")) {
							clause.isNot = true;
							str = str.substring(1);
						}
						clause.isOr = isOr;
						clause.setClauseString(str);
						parent.addChild(clause);
					}
					clauseStart = i + 1;
					isOr = false;
				}
			}
		}
		return tree;
	}

	public String getPrefix() {
		return "z";
	}

	public String getTagName() {
		return "if";
	}

	public static void main(String[] args) {
		long t = System.currentTimeMillis();
		for (int i = 0; i < 1; i++) {
			String condition = "2!=2||(!${Article.ID}==3&&${Article.Title}.indexOf(\"A\")>0)||1==1&&(2==1||3==2)";
			Treex tree = parseCondition(condition);
//			System.out.println(tree);
		}
//		System.out.println(System.currentTimeMillis() - t);
	}
}
