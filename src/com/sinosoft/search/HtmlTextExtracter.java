package com.sinosoft.search;

import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.utility.Treex;
import com.sinosoft.framework.utility.Treex.TreeIterator;
import com.sinosoft.framework.utility.Treex.TreeNode;
import com.sinosoft.framework.utility.Treex.TreeNodeList;
import com.sinosoft.framework.utility.XMLUtil;
import org.apache.html.dom.HTMLDocumentImpl;
import org.cyberneko.html.parsers.DOMFragmentParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.html.HTMLDocument;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 从一段HTML中抽取正文
 */
public class HtmlTextExtracter {
	private static final Logger logger = LoggerFactory.getLogger(HtmlTextExtracter.class);
	private String html;

	private String url;

	private Node body;

	private Treex tree;

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getContentText() {
		try {
			return extract(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public String getContentHtml() {
		try {
			return extract(false);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	private String extract(boolean textflag) throws Exception {
		Document doc = XMLUtil.htmlToXercesDocument(html);
		body = doc.getElementsByTagName("body").item(0);
		tree = new Treex();
		if (body == null) {
			logger.info("HtmlTextExtractor没有找到Body节点:{}", url);
			return null;
		}
		TextWeight tw = getPureTextWeight(body, tree.getRoot());
		tree.getRoot().setData(tw);

		// 获取所有可能的正文节点
		TreeIterator ti = tree.iterator();
		ArrayList list = new ArrayList();
		while (ti.hasNext()) {
			TreeNode tn = (TreeNode) ti.next();
			tw = (TextWeight) tn.getData();
			if (tw.Weight > 1600 && tw.Text != null && tw.Text.length() > 10) {
				list.add(tn);
				// System.out.println(tw.Text);
			}
		}

		if (list.size() == 0) {// 说明几乎没有什么文本
			return "";
		}
		//
		// // 过滤掉级别与大多数不一样的节点
		// Mapx map = new Mapx();
		// for (int i = 0; i < list.size(); i++) {
		// TreeNode node = (TreeNode) list.get(i);
		// if(node.getData()){
		//				
		// }
		// }
		// for (int i = 0; i < map.size(); i++) {
		// int count = ((Integer) map.get(i)).intValue();
		// if (count * 1.0 / list.size() > 0.5) {
		// int level = Integer.parseInt(map.getKey(i).toString());
		// for (int k = list.size() - 1; k >= 0; k--) {
		// TreeNode node = (TreeNode) list.get(k);
		// if (node.getLevel() != level) {
		// list.remove(node);
		// }
		// }
		// break;
		// }
		// }

		// 得出权重最大的节点
		TreeNode maxNode = (TreeNode) list.get(0);
		tw = (TextWeight) maxNode.getData();
		for (int i = 1; i < list.size(); i++) {
			TreeNode node = (TreeNode) list.get(i);
			TextWeight tw2 = (TextWeight) node.getData();
			if (tw2.Weight > tw.Weight) {
				maxNode = node;
				tw = tw2;
			}
		}

		TreeNode[] tnArr = tree.toArray();
		// 判断是不是版权声明,或者是否是被隐藏
		while (isCopyrightNode(maxNode, tnArr) || !isReadableNode(maxNode, tnArr)) {
			list.remove(maxNode);
			if (list.size() == 0) {
				return "";
			}
			maxNode = (TreeNode) list.get(0);
			tw = (TextWeight) maxNode.getData();
			for (int i = 1; i < list.size(); i++) {
				TreeNode node = (TreeNode) list.get(i);
				TextWeight tw2 = (TextWeight) node.getData();
				if (tw2.Weight > tw.Weight) {
					maxNode = node;
					tw = tw2;
				}
			}
		}

		TreeNode[] contentArr = null;

		// if (tw.Weight >= 10000) {// 极有可能是正文节点
		TreeNode pn = maxNode.getParent();
		while (pn != null) {
			TreeNodeList tns = pn.getChildren();
			if (tns.size() > 10) {
				break;
			}
			int count = 0;// 有正文的节点的数量
			for (int i = 0; i < tns.size(); i++) {
				TextWeight w = (TextWeight) tns.get(i).getData();
				if (w.Weight > 40) {// 简单几个符号不算
					count++;
				}
			}
			if (count > 3) {
				break;
			}
			pn = pn.getParent();
		}
		if (pn == null || pn.getParent() == null) {
			contentArr = new TreeNode[] { maxNode };
		} else {
			TextWeight pw = (TextWeight) pn.getData();// 父节点的父节点
			if (pw.Weight - tw.Weight > 1000) {// 说明maxNode不太可能是唯一的正文节点
				// 需要重置pn
				int level = pn.getLevel() + 1;
				pn = maxNode;
				while (pn.getLevel() > level) {
					pn = pn.getParent();
				}
				int maxIndex = 0;
				for (int i = 0; i < tnArr.length; i++) {
					if (tnArr[i] == pn) {
						maxIndex = i;
						break;
					}
				}
				int startIndex = maxIndex;
				int endIndex = maxIndex;

				// 往前追溯正文节点
				int count = 0;
				for (int i = maxIndex - 1; i > 0; i--) {
					if (tnArr[i].getLevel() < pn.getLevel()) {
						break;
					}
					TextWeight w = (TextWeight) tnArr[i].getData();
					if (w.Node.getNodeName().equalsIgnoreCase("img")) {
						count = 0;
					}
					if (w.Weight == 0 || w.Weight < 400 || w.LinkCount > 0) {
						if (w.LinkCount > 0) {
							count += w.LinkCount;
						}
						if (w.Weight == 0) {
							count++;
						}
						if (StringUtil.isNotEmpty(w.Text)) {
							count--;
						}
						if (count > 10) {
							break;
						}
					} else {
						count = 0;
					}
					if (tnArr[i].getLevel() == pn.getLevel()) {
						if (w.Weight > 0 || w.Node.getNodeName().equalsIgnoreCase("img")) {
							startIndex = i;
						}
					}
				}

				// 往后遍历正文节点
				count = 0;
				for (int i = maxIndex + 1; i < tnArr.length; i++) {
					if (tnArr[i].getLevel() < pn.getLevel()) {
						break;
					}
					TextWeight w = (TextWeight) tnArr[i].getData();
					if (w.Node.getNodeName().equalsIgnoreCase("img")) {
						count = 0;
					}
					if (w.Weight == 0 || w.Weight < 400 || w.LinkCount > 0) {
						if (w.LinkCount > 0) {
							count += w.LinkCount;
						}
						if (w.Weight == 0) {
							count++;
						}
						if (StringUtil.isNotEmpty(w.Text)) {
							count--;
						}
						if (count > 10) {
							break;
						}
					} else {
						count = 0;
					}
					if (tnArr[i].getLevel() == pn.getLevel()) {
						if (isRelaArticle(tnArr[i], tnArr)) {
							break;
						}
						if (w.Weight > 0 || w.Node.getNodeName().equalsIgnoreCase("img")) {
							endIndex = i;
						}
					}
				}
				int size = 0;
				int linkCount = 0;// 有链接的节点数量
				int textCount = 0;// 有文本的节点数量
				for (int i = startIndex; i <= endIndex; i++) {
					if (tnArr[i].getLevel() == pn.getLevel()) {
						TextWeight w = (TextWeight) tnArr[i].getData();
						if (w.LinkCount >= 1 && w.Weight > 40 && w.Weight < 20000) {// 图片链接不算，文本太多的链接也不能算
							linkCount++;
						}
						if (w.Weight > 40) {
							textCount++;
						}
						if (w.Node.getNodeName().equalsIgnoreCase("img")) {// 如果有同级别的图片，则也可认为是一个文本节点
							textCount++;
						}
						size++;
					}
				}
				if (linkCount * 1.0 / textCount > 0.8) {// 说明基本上都是链接
					if (isListPage()) {
						return getMetaText();
					}
					return "";
				}
				TreeNode[] rArr = new TreeNode[size];
				for (int i = startIndex, j = 0; i <= endIndex; i++) {
					if (tnArr[i].getLevel() == pn.getLevel()) {
						rArr[j++] = tnArr[i];
					}
				}
				contentArr = rArr;
				// System.out.println(startIndex + "\t" + maxIndex + "\t" +
				// endIndex);
			} else {
				contentArr = new TreeNode[] { maxNode };
			}
		}

		if (contentArr != null) {
			String text = getNodesText(contentArr);
			if (text.length() < 100) {
				// 判断是否是首页
				if (isListPage()) {
					return getMetaText();
				}
			}
			if (textflag) {
				return text;
			} else {
				return getNodesHtml(contentArr);
			}
		}
		return "";// 无法判定正文，说明页面中纯文字很少，不提取正文
	}

	private boolean isCopyrightNode(TreeNode tn, TreeNode[] arr) {
		int index = 0;
		for (int i = 0; i < arr.length; i++) {
			if (tn == arr[i]) {
				index = i;
				break;
			}
		}
		if (index * 1.0 / arr.length > 0.9) {
			return true;
		}
		TreeNode pn = tn.getParent().getParent();
		String text = getPureText(getDOMNode(pn)).toLowerCase();
		if (text.indexOf("版权") >= 0 || text.indexOf("copyright") >= 0) {
			return true;
		}
		return false;
	}

	private boolean isReadableNode(TreeNode tn, TreeNode[] arr) {
		TreeNode pn = tn;
		while (pn != null) {
			Node node = getDOMNode(pn);
			if (node.hasAttributes()) {
				node = node.getAttributes().getNamedItem("style");
				if (node != null) {
					String style = node.getNodeValue();
					Pattern p = Pattern.compile("\\sdisplay\\s*\\:\\s*none\\s", Pattern.CASE_INSENSITIVE);
					if (p.matcher(style).find()) {
						return false;
					}
				}
			}
			pn = pn.getParent();
		}
		int index = 0;
		for (int i = 0; i < arr.length; i++) {
			if (tn == arr[i]) {
				index = i;
				break;
			}
		}
		// 末尾的节点，且有很多符号
		if (index * 1.0 / arr.length > 0.9) {
			String text = getNodesText(new TreeNode[] { tn });
			int count = 0;// 符号数量
			for (int i = 0; i < text.length(); i++) {
				if (!Character.isJavaIdentifierPart(text.charAt(i))) {
					count++;
					if (count > 20) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * 是否是相关文章
	 */
	private boolean isRelaArticle(TreeNode tn, TreeNode[] arr) {
		String text = getPureText(getDOMNode(tn)).replaceAll("\\s", "");
		if (text.length() < 8
				&& (text.indexOf("相关阅读") >= 0 || text.indexOf("相关文章") >= 0 || text.indexOf("更多文章") >= 0 || text
						.indexOf("更多阅读") >= 0)) {
			int index = 0;
			for (int i = 0; i < arr.length; i++) {
				if (tn == arr[i]) {
					index = i;
					break;
				}
			}
			for (int i = index + 1; i < arr.length; i++) {
				index = i;
				if (arr[i].getLevel() == tn.getLevel()) {
					break;
				}
			}
			for (int i = index; i < arr.length; i++) {
				TextWeight tw = (TextWeight) arr[i].getData();
				if (tw.Weight == 0) {
					continue;
				}
				if (tw.Weight != 0 && tw.LinkCount == 0) {
					return false;
				}
				if (tw.LinkCount > 0) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isListPage() {
		if (StringUtil.isNotEmpty(url)) {
			if (url.endsWith("/")) {
				return true;
			}
			if (url.indexOf("/", 8) < 0) {
				return true;
			}
			if (url.toLowerCase().indexOf("index") > 0 || url.toLowerCase().indexOf("list") > 0) {
				return true;
			}
		}
		return false;
	}

	private Pattern pKeywords = Pattern.compile(
			"<meta name=[\\\"\\\']keywords[\\\"\\\'] content=[\\\"\\\'](.*?)[\\\"\\\']>", Pattern.DOTALL
					| Pattern.CASE_INSENSITIVE);

	private Pattern pDescription = Pattern.compile(
			"<meta name=[\\\"\\\']description[\\\"\\\'] content=[\\\"\\\'](.*?)[\\\"\\\']>", Pattern.DOTALL
					| Pattern.CASE_INSENSITIVE);

	private String getMetaText() {
		if (url.indexOf("/", 8) < 0 || url.indexOf("/") == url.length() - 1) {
			String keyword = null;
			String description = null;
			Matcher m = pKeywords.matcher(html);
			if (m.find()) {
				keyword = m.group(1);
			}
			m = pDescription.matcher(html);
			if (m.find()) {
				description = m.group(1);
			}
			return description + " \n关键字：" + keyword;
		}
		return "";
	}

	private static Pattern clearPattern = Pattern.compile("[\\p{Punct}\\s*　]", Pattern.DOTALL);

	private TextWeight getPureTextWeight(Node node, TreeNode tnode) {
		TextWeight tw = new TextWeight();
		tw.Node = node;
		TreeNode tn = tnode.addChild(tw);
		if (!isFiltered(node)) {
			if (!node.hasChildNodes()) {
				if (XMLUtil.isTextNode(node)) {
					String str = StringUtil.htmlDecode(node.getNodeValue());
					node.setNodeValue(str);
					str = clearPattern.matcher(str).replaceAll("");// 需要去掉!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~
					int l = StringUtil.lengthEx(str);
					int s = l * l;
					if (node.getNodeName().equalsIgnoreCase("a")) {
						tw.Weight = s;
						tw.LinkCount = 1;// 标识为链接
						tw.Text = str;
					} else {
						tw.Weight = s;
						tw.LinkCount = 0;// 标识为纯文本
						tw.Text = str;
					}
				}
			} else {
				NodeList list = node.getChildNodes();
				for (int i = 0; i < list.getLength(); i++) {
					TextWeight cw = getPureTextWeight(list.item(i), tn);
					tw.LinkCount += cw.LinkCount;
					tw.Weight += cw.Weight;
				}
				if (node.getNodeName().equalsIgnoreCase("a")) {
					tw.LinkCount += 1;
				}
				// tw.Text = node.getNodeName();
			}
		}
		return tw;
	}

	private String getNodesText(TreeNode[] tns) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < tns.length; i++) {
			if (i != 0) {
				sb.append(" ");
			}
			sb.append(getPureText(getDOMNode(tns[i])));
		}
		return sb.toString().replaceAll("[\\s　]{2,}", " ");
	}

	private String getNodesHtml(TreeNode[] tns) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < tns.length; i++) {
			if (i != 0) {
				sb.append("\n");
			}
			sb.append(XMLUtil.toString(getDOMNode(tns[i])));
		}
		return sb.toString().replaceAll("\\r\\n", "\n").replaceAll("\\n{2,}", "\n");
	}

	private Node getDOMNode(TreeNode tn) {
		return ((TextWeight) tn.getData()).Node;
	}

	public static String getPureText(String html) {
		DOMFragmentParser parser = new DOMFragmentParser();
		HTMLDocument document = new HTMLDocumentImpl();
		DocumentFragment fragment = document.createDocumentFragment();
		try {
			parser.parse(new InputSource(new StringReader(html)), fragment);
			String txt = getPureText(fragment);
			return StringUtil.htmlDecode(txt);
		} catch (Exception e) {
			logger.error("XML中存在非法字符!");
			// e.printStackTrace();
		}
		return null;
	}

	public static String getPureText(Node node) {
		if (!node.hasChildNodes()) {
			if (XMLUtil.isTextNode(node)) {
				return node.getNodeValue();
			}
		}
		if (isFiltered(node)) {
			return "";
		}

		// 隐藏的文本不抽取
		if (node.hasAttributes()) {
			Node a = node.getAttributes().getNamedItem("style");
			if (a != null) {
				String style = a.getNodeValue();
				Pattern p = Pattern.compile("display\\s*\\:\\s*none", Pattern.CASE_INSENSITIVE);
				if (p.matcher(style).find()) {
					return "";
				}
			}
		}
		StringBuffer sb = new StringBuffer();
		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node child = list.item(i);
			String name = child.getNodeName();
			sb.append(getPureText(child));
			sb.append(" ");
			if (name.equals("TR") || name.equals("P") || name.equals("DIV")) {
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	private static boolean isFiltered(Node node) {
		short type = node.getNodeType();
		String name = node.getNodeName();
		if (type == Node.COMMENT_NODE) {
			return true;
		}
		if (name.equals("SCRIPT")) {
			return true;
		}
		if (name.equals("LINK")) {
			return true;
		}
		if (name.equals("STYLE")) {
			return true;
		}
		if (name.equals("OBJECT")) {
			return true;
		}
		return false;
	}

	/**
	 * 截减树中权重值较低的节点，使得正文和标题的特征更为明显，仅用于算法调试
	 */
	public void trimTree(TreeNode node) {
		for (int i = node.getChildren().size() - 1; i >= 0; i--) {
			TextWeight tw = (TextWeight) node.getChildren().get(i).getData();
			if (tw.Weight < 625) {
				node.removeChild(tw);
			}
		}
		for (int i = 0; i < node.getChildren().size(); i++) {
			trimTree(node.getChildren().get(i));
		}
	}

	public static class TextWeight {
		public int Weight;

		public int LinkCount;

		public String Text;

		public Node Node;

		public String toString() {
			return "{" + Weight + "," + LinkCount + (StringUtil.isNotEmpty(Text) ? "," + Text : "") + "}";
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Treex getTree() {
		return tree;
	}
}
