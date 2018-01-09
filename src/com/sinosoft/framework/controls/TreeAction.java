package com.sinosoft.framework.controls;

import com.sinosoft.framework.Constant;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class TreeAction implements IControlAction {
	private static final Logger logger = LoggerFactory.getLogger(TreeAction.class);

	private String rootIcon = "Icons/treeicon10.gif";

	private String leafIcon = "Icons/treeicon09.gif";

	private String branchIcon = "Icons/treeicon09.gif";

	private String onClick;

	private String onMouseOver;

	private String onMouseOut;

	private String onContextMenu;

	private HtmlP template;

	private ArrayList items = new ArrayList();

	private DataTable DataSource;

	ArrayList a1 = new ArrayList();

	ArrayList a2 = new ArrayList();

	private String IdentifierColumnName = "ID";

	private String ParentIdentifierColumnName = "ParentID";

	private String rootText;

	private String ID;

	private int level;

	private int parentLevel;

	private boolean lazy;

	private boolean resizeable;

	private boolean lazyLoad;// 标志是否是延迟加载的进入

	private boolean expand; // 是否在延迟加载是全部展开

	private String style;

	private String TagBody;

	private TreeItem root;

	protected Mapx Params = new Mapx();

	private String method;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Mapx getParams() {
		return Params;
	}

	public void setParams(Mapx params) {
		Params = params;
	}

	/**
	 * 获取指定参数名的值
	 * 
	 * @param key
	 * @return
	 */
	public String getParam(String key) {
		return Params.getString(key);
	}

	public void setTemplate(HtmlP p) {
		this.onMouseOver = (String) p.getAttribute("onMouseOver");
		this.onMouseOut = (String) p.getAttribute("onMouseOut");
		this.onClick = (String) p.getAttribute("onClick");
		this.onContextMenu = (String) p.getAttribute("onContextMenu");

		// if (StringUtil.isEmpty(this.onMouseOut)) {
		// this.onMouseOut = "Tree.onMouseOut(event);";
		// }
		// if (StringUtil.isEmpty(this.onMouseOver)) {
		// this.onMouseOver = "Tree.onMouseOver(event);";
		// }

		// p.removeAttribute("onMouseOver");
		// p.removeAttribute("onMouseOut");
		p.removeAttribute("onClick");
		p.removeAttribute("onContextMenu");

		template = p;
		String html = template.getOuterHtml();
		Matcher m = Constant.PatternField.matcher(html);
		int lastEndIndex = 0;

		while (m.find(lastEndIndex)) {
			a1.add(html.substring(lastEndIndex, m.start()));
			a2.add(m.group(1));
			lastEndIndex = m.end();
		}
		a1.add(html.substring(lastEndIndex));
	}

	public List getItemList() {
		return items;
	}

	/**
	 * 根据索引获取菜单项
	 * 
	 * @param index
	 * @return
	 */
	public TreeItem getItem(int index) {
		return (TreeItem) items.get(index);
	}

	/**
	 * 获取菜单项的个数
	 * 
	 * @return
	 */
	public int getItemSize() {
		return items.size();
	}

	/**
	 * 添加菜单项
	 * 
	 * @param item
	 */
	public void addItem(TreeItem item) {
		items.add(item);
	}

	/**
	 * 添加菜单项至指定位置
	 * 
	 * @param item
	 * @param index
	 */
	public void addItem(TreeItem item, int index) {
		items.add(index, item);
	}

	public void bindData(DataTable dt) {
		this.DataSource = dt;
		try {
			this.bindData();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void bindData(SchemaSet set) {
		bindData(set.toDataTable());
	}

	private void bindData() throws Exception {
		if (DataSource == null) {
			throw new RuntimeException("必须在bindData方法中设定DataSource");
		}
		// DataSource.setWebMode(true);
		items.clear();

		root = new TreeItem();
		root.setID("_TreeRoot");
		root.setParentID("");
		root.setRoot(true);
		root.setText(rootText);
		root.setAction(this);
		root.setLevel(0);

		root.setAttribute("onMouseOver", this.onMouseOver);
		root.setAttribute("onContextMenu", this.onContextMenu);
		root.setAttribute("onClick", this.onClick);
		root.setAttribute("onMouseOut", this.onMouseOut);

		items.add(root);

		Mapx map = new Mapx();
		for (int i = 0; i < DataSource.getRowCount(); i++) {
			map.put(DataSource.getString(i, IdentifierColumnName), DataSource.getString(i, ParentIdentifierColumnName));
		}
		try {
			TreeItem last = null;
			for (int i = 0; i < DataSource.getRowCount(); i++) {
				DataRow dr = DataSource.getDataRow(i);
				String parentID = dr.getString(ParentIdentifierColumnName);
				if (StringUtil.isEmpty(parentID) || !map.containsKey(parentID)) {
					TreeItem item = new TreeItem();
					item.setData(dr);
					item.parseHtml(getItemInnerHtml(dr));
					item.setAction(this);
					item.setID(dr.getString(this.IdentifierColumnName));
					item.setParentID(parentID);
					if (lazyLoad) {
						item.setLevel(parentLevel + 1);
						item.setLevelStr((String) this.getParams().get("LevelStr"));
					} else {
						item.setLevel(1);
					}
					item.setParent(root);
					items.add(item);
					addChild(item);
					last = item;
				}
			}

			if (last != null) {
				last.setLast(true);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void addChild(TreeItem parent) throws Exception {
		boolean childFlag = false;
		TreeItem last = null;
		int index = -1;// parent在items中的位置
		for (int i = items.size() - 1; i >= 0; i--) {// 默认情况下是最后一个
			if (items.get(i) == parent) {
				index = i;
				break;
			}
		}
		ArrayList list = new ArrayList();
		for (int i = 0; i < DataSource.getRowCount(); i++) {
			DataRow dr = DataSource.getDataRow(i);
			String pid = dr.getString(this.ParentIdentifierColumnName);
			String id = dr.getString(this.IdentifierColumnName);
			if (parent.getID().equals(pid) && !StringUtil.isEmpty(id) && !id.equals(pid)) {// 要注意避免死循环
				childFlag = true;
				if (parent.getLevel() >= level && (!lazyLoad || !expand)) {
					parent.setLazy(lazy);
					parent.setExpanded(false);
					parent.setBranch(childFlag);
					return;
				}
				TreeItem item = new TreeItem();
				item.setData(dr);
				item.parseHtml(getItemInnerHtml(dr));
				item.setAction(this);
				item.setID(dr.getString(this.IdentifierColumnName));
				item.setParentID(parent.getID());
				item.setLevel(parent.getLevel() + 1);
				item.setParent(parent);
				if (lazyLoad) {
					item.setLevelStr((String) this.getParams().get("LevelStr"));
				}
				list.add(item);
				items.add(index + list.size(), item);
				last = item;
			}
		}
		for (int i = 0; i < list.size(); i++) {
			addChild((TreeItem) list.get(i));
		}
		if (last != null) {
			last.setLast(true);
		}
		if (!lazy && parent.getLevel() + 1 == level) {
			parent.setExpanded(false);
		}
		parent.setBranch(childFlag);
	}

	public String getItemInnerHtml(DataRow dr) {
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < a1.size(); j++) {
			sb.append(a1.get(j));
			if (j < a2.size()) {
				sb.append(dr.getString(a2.get(j).toString()));
			}
		}
		return sb.toString();
	}

	public String getHtml() {
		StringBuffer sb = new StringBuffer();
		String styleStr = "";
		if (StringUtil.isNotEmpty(style)) {
			styleStr = styleStr + style;
		}
		if (!lazyLoad) {
			sb.append("<div id='" + this.ID + "_container' class='treeContainer' style='-moz-user-select:none;"
					+ styleStr + "'><div ztype='_Tree' onselectstart='stopEvent(event);' id='" + this.ID + "' method='"
					+ method + "' class='treeItem'><table><tr><td>");
		}
		for (int i = 0; i < items.size(); i++) {
			if (lazyLoad && getItem(i).getLevel() <= parentLevel) {
				continue;
			}
			if (i != 0 && getItem(i).getLevel() > getItem(i - 1).getLevel()) {
				if (getItem(i).getLevel() == level && !lazyLoad && !lazy) {
					sb.append("<div style='display:none'>");
				} else {
					sb.append("<div>");
				}

			}
			sb.append(((TreeItem) items.get(i)).getOuterHtml());
			if (i != items.size() - 1 && getItem(i).getLevel() > getItem(i + 1).getLevel()) {
				for (int j = 0; j < getItem(i).getLevel() - getItem(i + 1).getLevel(); j++) {
					sb.append("</div>");
				}
			}
			if (i == items.size() - 1) {
				for (int j = 0; j < getItem(i).getLevel() - parentLevel; j++) {
					sb.append("</div>");
				}
			}
		}
		if (!lazyLoad) {
			sb.append("</td></tr></table></div></div>\n\r");
			HtmlScript script = new HtmlScript();
			script.setInnerHTML(getScript());
			sb.append(script.getOuterHtml());
		}
		// System.out.println(sb.toString());
		return sb.toString();
	}

	public String getScript() {
		StringBuffer sb = new StringBuffer();
		// 加入标签本身
		sb.append("$('" + ID + "').TagBody = \"" + StringUtil.htmlEncode(getTagBody().replaceAll("\\s+", " ")) + "\";");
		Object[] ks = Params.keyArray();
		Object[] vs = Params.valueArray();
		for (int i = 0; i < Params.size(); i++) {
			Object key = ks[i];
			if (!key.equals(Constant.TagBody) && !key.toString().startsWith("Cookie.")
					&& !key.toString().startsWith("Header.") && vs[i] != null) {
				sb.append("Tree.setParam('" + ID + "','" + key + "',\"" + StringUtil.javaEncode(vs[i].toString())
						+ "\");");
			}
		}
		sb.append("Tree.setParam('" + ID + "','" + Constant.TreeStyle + "',\"" + this.style + "\");");
		sb.append("Tree.setParam('" + ID + "','" + Constant.TreeLevel + "'," + this.level + ");");
		sb.append("Tree.setParam('" + ID + "','" + Constant.TreeLazy + "',\"" + this.lazy + "\");");
		sb.append("Tree.setParam('" + ID + "','" + Constant.TreeExpand + "',\"" + this.expand + "\");");
		sb.append("Tree.init('" + ID + "'," + this.resizeable + ");");
		String content = sb.toString();
		Matcher matcher = Constant.PatternField.matcher(content);
		sb = new StringBuffer();
		int lastEndIndex = 0;
		while (matcher.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, matcher.start()));
			sb.append("$\\{");
			sb.append(matcher.group(1));
			sb.append("}");
			lastEndIndex = matcher.end();
		}
		sb.append(content.substring(lastEndIndex));

		return sb.toString();

	}

	public String getRootText() {
		return rootText;
	}

	/**
	 * 设置树形菜单根节点文本内容
	 * 
	 * @param rootText
	 */
	public void setRootText(String rootText) {
		this.rootText = rootText;
	}

	/**
	 * 设置根节点的图标
	 * 
	 * @param iconFileName
	 */
	public void setRootIcon(String iconFileName) {
		rootIcon = iconFileName;
	}

	/**
	 * 设置叶子节点图标
	 * 
	 * @param iconFileName
	 */
	public void setLeafIcon(String iconFileName) {
		leafIcon = iconFileName;
	}

	/**
	 * 设置分支节点图标
	 * 
	 * @param iconFileName
	 */
	public void setBranchIcon(String iconFileName) {
		branchIcon = iconFileName;
	}

	/**
	 * 获取分支节点图标相对路径
	 * 
	 * @return
	 */
	public String getBranchIcon() {
		return branchIcon;
	}

	public String getLeafIcon() {
		return leafIcon;
	}

	public String getRootIcon() {
		return rootIcon;
	}

	public String getIdentifierColumnName() {
		return IdentifierColumnName;
	}

	/**
	 * 设置主键列名
	 * 
	 * @param identifierColumnName
	 */
	public void setIdentifierColumnName(String identifierColumnName) {
		IdentifierColumnName = identifierColumnName;
	}

	public String getParentIdentifierColumnName() {
		return ParentIdentifierColumnName;
	}

	/**
	 * 设置父节点列名
	 * 
	 * @param parentIdentifierColumnName
	 */
	public void setParentIdentifierColumnName(String parentIdentifierColumnName) {
		ParentIdentifierColumnName = parentIdentifierColumnName;
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getID() {
		return ID;
	}

	public void setID(String id) {
		ID = id;
	}

	public String getOnClick() {
		return onClick;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

	public String getOnContextMenu() {
		return onContextMenu;
	}

	public void setOnContextMenu(String onContextMenu) {
		this.onContextMenu = onContextMenu;
	}

	public String getOnMouseOut() {
		return onMouseOut;
	}

	public void setOnMouseOut(String onMouseOut) {
		this.onMouseOut = onMouseOut;
	}

	public String getOnMouseOver() {
		return onMouseOver;
	}

	public void setOnMouseOver(String onMouseOver) {
		this.onMouseOver = onMouseOver;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getTagBody() {
		return TagBody;
	}

	public void setTagBody(String tagBody) {
		TagBody = tagBody;
	}

	public boolean isLazyLoad() {
		return lazyLoad;
	}

	public void setLazyLoad(boolean lazyLoad) {
		this.lazyLoad = lazyLoad;
	}

	public int getParentLevel() {
		return parentLevel;
	}

	public void setParentLevel(int parentLevel) {
		this.parentLevel = parentLevel;
	}

	public boolean isExpand() {
		return expand;
	}

	public void setExpand(boolean expand) {
		this.expand = expand;
	}

	public DataTable getDataSource() {
		return DataSource;
	}
}
