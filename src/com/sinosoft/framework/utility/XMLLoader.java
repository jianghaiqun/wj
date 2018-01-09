package com.sinosoft.framework.utility;

import com.sinosoft.framework.collection.CaseIgnoreMapx;
import com.sinosoft.framework.utility.Treex.TreeNode;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 加载包下全部xml文件
 * 
 * @author guobin
 * 
 */
public class XMLLoader {
	private static final Logger logger = LoggerFactory.getLogger(XMLLoader.class);

	public XMLLoader() {
		super();
	}

	private Treex tree = new Treex();

	public void load(String path) {
		File f = new File(path);
		load(f);
	}

	public void load(File f) {
		if (f.isFile()) {
			loadOneFile(f);
		} else {
			File[] fs = f.listFiles();
			for (int i = 0; i < fs.length; i++) {
				f = fs[i];
				if ((f.isFile()) && (f.getName().toLowerCase().endsWith(".xml")))
					loadOneFile(f);
			}
		}
	}

	public void load(InputStream is) {
		loadOneFile(is);
	}

	private void loadOneFile(File f) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(f);
			loadOneFile(fis);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ex) {
				logger.error(ex.getMessage(), ex);
			}
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public void clear() {
		this.tree = new Treex();
	}

	private void loadOneFile(InputStream is) {
		SAXReader reader = new SAXReader(false);
		try {
			reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			reader.setEntityResolver(new EntityResolver() {
				ByteArrayInputStream bs = new ByteArrayInputStream("".getBytes());

				public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
					return new InputSource(this.bs);
				}
			});
			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			convertElement(root, this.tree.getRoot());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void convertElement(Element ele, TreeNode parent) {
		String name = ele.getName().toLowerCase();
		NodeData data = new NodeData();
		data.TagName = name;
		data.Body = ele.getTextTrim();
		List list = ele.attributes();
		Mapx map = new Mapx();
		for (int i = 0; i < list.size(); i++) {
			Attribute attr = (Attribute) list.get(i);
			map.put(attr.getName(), attr.getValue());
		}
		data.Attributes = map;
		Treex.TreeNode node = parent.addChild(data);
		data.treeNode = node;
		list = ele.elements();
		for (int i = 0; i < list.size(); i++) {
			Element child = (Element) list.get(i);
			convertElement(child, node);
		}
	}

	public NodeData[] getNodeDataList(String path) {
		String[] arr = path.split("\\.");
		Treex.TreeNode current = this.tree.getRoot();
		ArrayList list = new ArrayList();
		list.add(current);
		for (int i = 0; i < arr.length; i++) {
			list = getChildren(list, arr[i]);
			if (list == null) {
				return null;
			}
		}
		if (list.size() == 0) {
			return null;
		}
		NodeData[] datas = new NodeData[list.size()];
		for (int i = 0; i < list.size(); i++) {
			Treex.TreeNode node = (Treex.TreeNode) list.get(i);
			datas[i] = ((NodeData) node.getData());
		}
		return datas;
	}

	private ArrayList<Treex.TreeNode> getChildren(ArrayList<Treex.TreeNode> parentList, String pathPart) {
		ArrayList list = new ArrayList();
		for (int i = 0; i < parentList.size(); i++) {
			Treex.TreeNode node = (Treex.TreeNode) parentList.get(i);
			Treex.TreeNodeList nodes = node.getChildren();
			for (int j = 0; j < nodes.size(); j++) {
				NodeData data = (NodeData) ((Treex.TreeNode) nodes.get(j)).getData();
				if (pathPart.equals("*") || data.getTagName().equalsIgnoreCase(pathPart)) {
					list.add((Treex.TreeNode) nodes.get(j));
				}
			}
		}
		return list;
	}

	public String getNodeBody(String path) {
		return getNodeBody(path, null, null);
	}

	public String getNodeBody(String path, String attrName, String attrValue) {
		NodeData nd = getNodeData(path, attrName, attrValue);
		if (nd == null) {
			return null;
		}
		return nd.Body;
	}

	public NodeData getNodeData(String path) {
		return getNodeData(path, null, null);
	}

	public NodeData getNodeData(String path, String attrName, String attrValue) {
		NodeData[] datas = getNodeDataList(path);
		if (ObjectUtil.notEmpty(datas)) {
			if (attrName == null) {
				return datas[0];
			}
			for (int i = 0; i < datas.length; i++) {
				String v = (String) datas[i].Attributes.get(attrName);
				if (v == null) {
					if (attrValue == null) {
						return datas[i];
					}
				} else if (v.equals(attrValue)) {
					return datas[i];
				}
			}
		}

		return null;
	}

	public static class NodeData {
		private Mapx Attributes = new CaseIgnoreMapx();
		private String TagName;
		private String Body;
		private Treex.TreeNode treeNode;

		public Mapx getAttributes() {
			return this.Attributes;
		}

		public String getTagName() {
			return this.TagName;
		}

		public String getBody() {
			return this.Body;
		}

		public Treex.TreeNode getTreeNode() {
			return this.treeNode;
		}

		public NodeData[] getChildrenDataList() {
			NodeData[] arr = new NodeData[this.treeNode.getChildren().size()];
			for (int i = 0; i < this.treeNode.getChildren().size(); i++) {
				arr[i] = ((NodeData) ((Treex.TreeNode) this.treeNode.getChildren().get(i)).getData());
			}
			return arr;
		}
	}
}