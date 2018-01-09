package com.sinosoft.framework.controls;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.utility.StringUtil;

public class TreeItem extends HtmlP implements Cloneable {
	public static final String Icon_Branch_NotLast_NotExpand = "Icons/treeicon01.gif";

	public static final String Icon_Branch_Last_NotExpand = "Icons/treeicon04.gif";

	public static final String Icon_Branch_NotLast_Expand = "Icons/treeicon02.gif";

	public static final String Icon_Branch_Last_Expand = "Icons/treeicon05.gif";

	public static final String Icon_Line_Vertical = "Icons/treeicon03.gif";

	public static final String Icon_Line_Null = "Icons/treeicon08.gif";

	public static final String Icon_Leaf_Last = "Icons/treeicon07.gif";

	public static final String Icon_Leaf_NotLast = "Icons/treeicon06.gif";

	public static final String Branch_NotLast_NotExpand = "1";

	public static final String Branch_NotLast_Expand = "2";

	public static final String Branch_Last_NotExpand = "3";

	public static final String Branch_Last_Expand = "4";

	// 档案管理专用
	public static final String Icon_Special = "Framework/Images/icon_folder_special.gif";

	public static final String Icon_Image = "Framework/Images/icon_folder_image.gif";

	public static final String Icon_Video = "Framework/Images/icon_folder_video.gif";

	public static final String Icon_Audio = "Framework/Images/icon_folder_audio.gif";

	private String icon;

	private int level;

	private boolean isLast;

	private boolean isRoot;

	private boolean isBranch;

	private boolean isLeaf;

	private boolean lazy;

	private boolean resizeable;

	private TreeItem parent;

	private TreeAction action;

	private String ID;

	private String ParentID;

	private String levelStr;

	private boolean isExpanded = true;

	private DataRow data;

	public String getOuterHtml() {
		HtmlP item = new HtmlP();
		item.Attributes.putAll(this.Attributes);
		item.setAttribute("level", "" + level);
		item.setAttribute("id", action.getID() + "_" + ID);
		item.setAttribute("parentID", ParentID);
		item.setAttribute("lazy", lazy ? "1" : "0");
		item.setAttribute("resizeable", resizeable ? "1" : "0");

		// item.setAttribute("onmouseover",
		// this.getAttributeExt("onmouseover"));
		// item.setAttribute("onmouseout", this.getAttributeExt("onmouseout"));
		String onclick = this.getAttributeExt("onclick");
		if (StringUtil.isEmpty(onclick)) {
			onclick = "";
		}
		item.setAttribute("onclick", "Tree.onItemClick(event,this);" + onclick);
		item.setAttribute("ondblclick", "Tree.onItemDblClick(event,this);");
		item.setAttribute("oncontextmenu", this
				.getAttributeExt("oncontextmenu"));
		String afterDrag = (String) this.getAttribute("afterDrag");
		if (StringUtil.isNotEmpty(afterDrag)) {
			item.setAttribute("dragEnd", "Tree.dragEnd");
			item.setAttribute("onMouseUp", "DragManager.onMouseUp(event,this)");

			if (this.action.Params.getString("Header.UserAgent")
					.indexOf("msie") >= 0) {
				item.setAttribute("onMouseEnter",
						"DragManager.onMouseOver(event,this)");
			} else {
				item.setAttribute("onMouseOver",
						"DragManager.onMouseOver(event,this)");
			}
			item.setAttribute("dragOver", "Tree.dragOver");

			if (this.action.Params.getString("Header.UserAgent")
					.indexOf("msie") >= 0) {
				item.setAttribute("onMouseLeave",
						"DragManager.onMouseOut(event,this)");
			} else {
				item.setAttribute("onMouseOut",
						"DragManager.onMouseOut(event,this)");
			}
			item.setAttribute("dragOut", "Tree.dragOut");
		}

		String text = getText();
		String prefix = Config.getContextPath();
		text = new StringBuffer().append("<img src='").append(prefix).append(
				getIcon()).append("'>").append(text).toString();

		StringBuffer levelSb = new StringBuffer();

		if (isBranch && isLast && isExpanded) {
			text = new StringBuffer().append(
					"<img onclick='Tree.onBranchIconClick(event);' src='")
					.append(prefix).append(TreeItem.Icon_Branch_Last_Expand)
					.append("'>").append(text).toString();
			if (lazy) {
				levelSb.insert(0, "0");
			}

			item.setAttribute("expand", TreeItem.Branch_Last_Expand);
		}
		if (isBranch && isLast && !isExpanded) {
			text = new StringBuffer().append(
					"<img onclick='Tree.onBranchIconClick(event);' src='")
					.append(prefix).append(TreeItem.Icon_Branch_Last_NotExpand)
					.append("'>").append(text).toString();
			if (lazy) {
				levelSb.insert(0, "0");
			}
			item.setAttribute("expand", TreeItem.Branch_Last_NotExpand);
		}
		if (isBranch && !isLast && !isExpanded) {
			text = new StringBuffer().append(
					"<img onclick='Tree.onBranchIconClick(event);' src='")
					.append(prefix).append(
							TreeItem.Icon_Branch_NotLast_NotExpand)
					.append("'>").append(text).toString();
			if (lazy) {
				levelSb.insert(0, "1");
			}
			item.setAttribute("expand", TreeItem.Branch_NotLast_NotExpand);
		}
		if (isBranch && !isLast && isExpanded) {
			text = new StringBuffer().append(
					"<img onclick='Tree.onBranchIconClick(event);' src='")
					.append(prefix).append(TreeItem.Icon_Branch_NotLast_Expand)
					.append("'>").append(text).toString();
			if (lazy) {
				levelSb.insert(0, "1");
			}
			item.setAttribute("expand", TreeItem.Branch_NotLast_Expand);
		}
		if (isLeaf && isLast) {
			text = new StringBuffer().append("<img src='").append(prefix)
					.append(TreeItem.Icon_Leaf_Last).append("'>").append(text)
					.toString();
		}
		if (isLeaf && !isLast) {
			text = new StringBuffer().append("<img src='").append(prefix)
					.append(TreeItem.Icon_Leaf_NotLast).append("'>").append(
							text).toString();
		}

		TreeItem pTI = parent;
		while (pTI != null && !pTI.isRoot) {
			if (pTI.isLast) {
				text = new StringBuffer().append("<img src='").append(prefix)
						.append(TreeItem.Icon_Line_Null).append("'>").append(
								text).toString();
				if (lazy) {
					levelSb.insert(0, "0");
				}
			} else {
				text = new StringBuffer().append("<img src='").append(prefix)
						.append(TreeItem.Icon_Line_Vertical).append("'>")
						.append(text).toString();
				if (lazy) {
					levelSb.insert(0, "1");
				}
			}
			pTI = pTI.parent;
		}

		if (this.levelStr != null) {
			for (int j = levelStr.length() - 1; j >= 0; j--) {
				if (levelStr.charAt(j) == '0') {
					text = new StringBuffer().append("<img src='").append(
							prefix).append(TreeItem.Icon_Line_Null)
							.append("'>").append(text).toString();
				} else {
					text = new StringBuffer().append("<img src='").append(
							prefix).append(TreeItem.Icon_Line_Vertical).append(
							"'>").append(text).toString();
				}
			}
			if (lazy) {
				levelSb.insert(0, levelStr);
			}
		}
		if (lazy) {
			item.setAttribute("levelstr", levelSb.toString());
		}

		item.setInnerHTML(text);

		return item.getOuterHtml();
	}

	private String getAttributeExt(String attr) {
		String v = (String) getAttribute(attr);
		if (StringUtil.isEmpty(v)) {
			if (parent != null) {
				return parent.getAttributeExt(attr);
			} else {
				return null;
			}
		}
		return v;
	}
	
	public String getIcon() {
		if (icon == null) {
			if (isRoot) {
				return action.getRootIcon();
			}
			if (isLeaf) {
				return action.getLeafIcon();
			}
			if (isBranch) {
				return action.getBranchIcon();
			}
		}
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isBranch() {
		return isBranch;
	}

	public void setBranch(boolean isBranch) {
		this.isBranch = isBranch;
		this.isLeaf = !isBranch;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
		this.isBranch = !isLeaf;
	}

	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getOnClick() {
		return getAttributeExt("onclick");
	}

	public void setOnClick(String onClick) {
		setAttribute("onclick", onClick);
	}

	public String getOnContextMenu() {
		return getAttributeExt("oncontextmenu");
	}

	public void setOnContextMenu(String onContextMenu) {
		setAttribute("oncontextmenu", onContextMenu);
	}

	public String getOnMouseOut() {
		return getAttributeExt("onmouseout");
	}

	public void setOnMouseOut(String onMouseOut) {
		setAttribute("onmouseout", onMouseOut);
	}

	public String getOnMouseOver() {
		return getAttributeExt("onmouseover");
	}

	public void setOnMouseOver(String onMouseOver) {
		setAttribute("onmouseover", onMouseOver);
	}

	public TreeItem getParent() {
		return parent;
	}

	public void setParent(TreeItem parent) {
		this.parent = parent;
	}

	public String getText() {
		return this.getInnerHTML();
	}

	public void setText(String text) {
		this.setInnerHTML(text);
	}

	public TreeAction getAction() {
		return action;
	}

	public void setAction(TreeAction action) {
		this.action = action;
	}

	public String getID() {
		return ID;
	}

	public void setID(String id) {
		ID = id;
	}

	public String getParentID() {
		return ParentID;
	}

	public void setParentID(String parentID) {
		ParentID = parentID;
	}

	public boolean isExpanded() {
		return isExpanded;
	}

	public void setExpanded(boolean isExpanded) {
		this.isExpanded = isExpanded;
	}

	public boolean isLast() {
		return isLast;
	}

	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}

	public boolean isLazy() {
		return lazy;
	}

	public void setLazy(boolean lazy) {
		this.lazy = lazy;
	}

	public boolean isResizeable() {
		return resizeable;
	}

	public void setResizeable(boolean resizeable) {
		this.resizeable = resizeable;
	}

	public String getLevelStr() {
		return levelStr;
	}

	public void setLevelStr(String levelStr) {
		this.levelStr = levelStr;
	}

	public DataRow getData() {
		if (isRoot && data == null) {
			throw new RuntimeException("Root节点没有Data.");
		}
		return data;
	}

	public void setData(DataRow data) {
		this.data = data;
	}
}
