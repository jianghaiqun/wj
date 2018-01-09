package com.sinosoft.workflow;

import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.schema.ZWWorkflowSchema;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.util.List;

/**
 * 工作流定义
 * 
 */
public class Workflow extends ZWWorkflowSchema {
	private static final Logger logger = LoggerFactory.getLogger(Workflow.class);
	private static final long serialVersionUID = 1L;

	public static final String STARTNODE = "StartNode";

	public static final String ENDNODE = "EndNode";

	public static final String COMMONNODE = "Node";

	public static final String ACTIONNODE = "ActionNode";

	private Node[] nodes;

	private Mapx data = new Mapx();

	protected synchronized void init() {
		try {
			SAXReader sax = new SAXReader();
			Document doc = null;
			StringReader reader = new StringReader(getConfigXML());
			doc = sax.read(reader);
			List list = doc.getRootElement().elements("config");
			for (int i = 0; i < list.size(); i++) {
				Element ele = (Element) list.get(i);
				data.put(ele.attributeValue("name"), ele.attributeValue("value"));
			}
			list = doc.getRootElement().elements("node");
			nodes = new Node[list.size()];
			for (int i = 0; i < list.size(); i++) {
				Element ele = (Element) list.get(i);
				Node node = new Node(this, ele);
				nodes[i] = node;
			}
			// 统一初始化转移条件
			for (int i = 0; i < nodes.length; i++) {
				WorkflowTransition[] wts = nodes[i].getTransitions();
				for (int j = 0; j < wts.length; j++) {
					wts[j].init();
				}
			}
		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
		}

	}

	public Mapx getData() {
		return data;
	}

	public Node[] getNodes() {
		return nodes;
	}

	public Node findNode(int id) {
		if (nodes == null) {
			init();
		}
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i].getID() == id) {
				return nodes[i];
			}
		}
		return null;
	}

	public Node getStartNode() {
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i].getType().equals(Workflow.STARTNODE)) {
				return nodes[i];
			}
		}
		return null;
	}

	public Node getEndNode() {
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i].getType().equals(Workflow.ENDNODE)) {
				return nodes[i];
			}
		}
		return null;
	}

	public WorkflowTransition findTransition(int id) {
		if (nodes == null) {
			init();
		}
		for (int i = 0; i < nodes.length; i++) {
			WorkflowTransition[] wts = nodes[i].getTransitions();
			for (int j = 0; j < wts.length; j++) {
				if (wts[j].getID() == id) {
					return wts[j];
				}
			}
		}
		return null;
	}

	public static Workflow convert(ZWWorkflowSchema schema) {
		Workflow wf = new Workflow();
		for (int i = 0; i < wf.getColumnCount(); i++) {
			wf.setV(i, schema.getV(i));
		}
		wf.init();
		return wf;
	}

	/**
	 * 对应ConifgXML中的node节点
	 */
	public class Node {
		private int id;
		private String name;
		private String type;
		private Workflow wf;
		private Mapx map;
		private WorkflowTransition[] transitions;

		public Node(Workflow wf, Element ele) {
			this.wf = wf;
			String strid = ele.attributeValue("id");
			id = Integer.parseInt(strid.substring(strid.lastIndexOf("e") + 1));
			type = strid.substring(0, strid.lastIndexOf("e") + 1);
			name = ele.attributeValue("name");
			List list = ele.elements("data");
			map = new Mapx();
			for (int i = 0; i < list.size(); i++) {
				Element data = (Element) list.get(i);
				String k = data.attributeValue("type");
				String v = data.getText();
				map.put(k, v);
			}
			list = ele.elements("line");
			transitions = new WorkflowTransition[list.size()];
			for (int i = 0; i < list.size(); i++) {
				Element lineEle = (Element) list.get(i);
				transitions[i] = new WorkflowTransition(this, lineEle);
			}
		}

		public int getID() {
			return id;
		}

		public String getName() {
			return name;
		}

		public String getType() {
			return type;
		}

		public Workflow getWorkflow() {
			return wf;
		}

		/**
		 * 获取转移条件
		 */
		public WorkflowTransition[] getTransitions() {
			return transitions;
		}

		public Mapx getData() {
			return map;
		}
	}
}
