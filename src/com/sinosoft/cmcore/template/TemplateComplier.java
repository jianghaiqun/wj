package com.sinosoft.cmcore.template;

import com.sinosoft.cmcore.tag.TagBase;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.utility.Errorx;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.utility.Treex;
import com.sinosoft.framework.utility.Treex.TreeNode;
import com.sinosoft.framework.utility.Treex.TreeNodeList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateComplier {
	private static final Logger logger = LoggerFactory.getLogger(TemplateComplier.class);
	private int VarID = 0;
	private String fileName;

	public void complieFile(String templateFileName) {
		fileName = templateFileName;
		fileName = StringUtil.replaceEx(fileName, "\\", "/");
		fileName = fileName.replaceAll("\\/+", "/");
		TemplateParser parser = new TemplateParser(fileName);
		parser.parse();
		if (Errorx.hasError()) {
			logger.info(StringUtil.join(Errorx.getMessages(), "\n"));
			return;
		}
		// 遍历树，生成待编译的文件
		Treex tree = parser.getTree();
		TemplateConfig config = parser.getConfig();

		// 编译
		try {
			compile(tree, config);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		String[] messages = Errorx.getMessages();
		if (messages.length > 0) {
			logger.info(StringUtil.join(messages, "\n"));
		}
	}

	public void compile(Treex tree, TemplateConfig config) throws Exception {
		String className = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.lastIndexOf("."));
		className = className.substring(0, 1).toUpperCase() + className.substring(1);
		VarID = 0;

		StringBuffer sb = new StringBuffer();
		sb.append("package com.sinosoft.cmcore.test;\n\n");
		sb.append("import com.sinosoft.cmcore.template.*;\n");
		sb.append("import com.sinosoft.cmcore.tag.*;\n");
		sb.append("import com.sinosoft.cmcore.tag.impl.*;\n");
		sb.append("\npublic class " + className + " extends TemplateBase{\n");

		// getConfig()方法
		sb.append("\tpublic TemplateConfig getConfig() {\n");
		if (config.getName() == null) {
			sb.append("\t\tString Name = null;\n");
		} else {
			sb.append("\t\tString Name = \"" + config.getName() + "\";\n");
		}
		if (config.getType() == null) {
			sb.append("\t\tString Type = null;\n");
		} else {
			sb.append("\t\tString Type = \"" + config.getType() + "\";\n");
		}
		if (config.getAuthor() == null) {
			sb.append("\t\tString Author = null;\n");
		} else {
			sb.append("\t\tString Author = \"" + config.getAuthor() + "\";\n");
		}
		if (config.getVersion() == null) {
			sb.append("\t\tString Version = null;\n");
		} else {
			sb.append("\t\tString Version = \"" + config.getVersion() + "\";\n");
		}
		if (config.getDescription() == null) {
			sb.append("\t\tString Description = null;\n");
		} else {
			sb.append("\t\tString Description = \"" + config.getDescription() + "\";\n");
		}
		sb.append("\t\tString ScriptStart = \"" + config.getScriptStart() + "\";\n");
		sb.append("\t\tString ScriptEnd = \"" + config.getScriptEnd() + "\";\n");
		sb.append("\t\tTemplateConfig config = new TemplateConfig(Name, Type, Author, Version, "
				+ "Description, ScriptStart, ScriptEnd);\n");
		sb.append("\t\treturn config;\n");
		sb.append("\t}\n\n");

		// getTemplateFilePath()方法
		sb.append("\tpublic String getTemplateFilePath() {\n");
		sb.append("\t\treturn \"" + fileName.substring(0, fileName.lastIndexOf("/") + 1) + "\";\n");
		sb.append("\t}\n\n");

		// execute()方法
		sb.append("\tpublic void execute(){\n");

		TreeNodeList list = tree.getRoot().getChildren();
		for (int i = 0; i < list.size(); i++) {
			compileNode(list.get(i), sb, "\t\t");
		}
		sb.append("\t}\n}");
		String path = Config.getContextRealPath();
		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		path = path.substring(0, path.lastIndexOf("/"));
		FileUtil.writeText(path + "/Java/com/zving/cmcore/test/" + className + ".java", sb.toString());
	}

	public void compileNode(TreeNode node, StringBuffer sb, String prefix) {
		TemplateFragment tf = (TemplateFragment) node.getData();
		if (tf.Type == TemplateFragment.FRAGMENT_HTML) {
			String[] arr = tf.FragmentText.split("\\n");
			for (int i = 0; i < arr.length; i++) {
				String line = arr[i];
				if (i != arr.length - 1) {
					line = StringUtil.rightTrim(line);
					sb.append(prefix + "out.println(\"");
				} else {
					sb.append(prefix + "out.print(\"");
				}
				sb.append(StringUtil.javaEncode(line) + "\");\n");
			}
		}
		if (tf.Type == TemplateFragment.FRAGMENT_HOLDER) {
			sb.append(prefix + "out.print(context.getHolderValue(\"" + tf.FragmentText + "\"));\n");
		}
		if (tf.Type == TemplateFragment.FRAGMENT_SCRIPT) {
			if (tf.FragmentText.startsWith("=")) {
				sb.append(prefix + "out.print(" + tf.FragmentText.substring(1) + ");\n");
			} else {
				sb.append(prefix + tf.FragmentText);
			}
		}
		if (tf.Type == TemplateFragment.FRAGMENT_TAG) {
			try {
				compileTag(node, sb, prefix);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	private static String[] iterativeVarNames = new String[] { "i", "j", "k", "m", "n", "p", "q", "t", "a", "b", "c",
			"d", "e", "f", "g", "h", "l", "o", "r", "s", "u", "v", "w", "x", "y", "z" };

	public void compileTag(TreeNode node, StringBuffer sb, String prefix) throws Exception {
		TemplateFragment tf = (TemplateFragment) node.getData();
		String className = tf.TagPrefix.substring(0, 1).toUpperCase() + tf.TagPrefix.substring(1)
				+ tf.TagName.substring(0, 1).toUpperCase() + tf.TagName.substring(1) + "Tag";
		String varName = className.substring(0, 1).toLowerCase() + className.substring(1) + VarID++;
		sb.append(prefix + className + " " + varName + " = new " + className + "();\n");
		sb.append(prefix + varName + ".setContext(context);\n");
		sb.append(prefix + varName + ".setTemplate(this);\n");
		sb.append(prefix + "context.addTagNode();\n");
		Object[] ks = tf.Attributes.keyArray();
		for (int i = 0; i < ks.length; i++) {
			sb.append(prefix + varName + ".setAttribute(\"" + ks[i] + "\",\"" + tf.Attributes.getString(ks[i])
					+ "\");\n");
		}
		Class c = TemplateClassLoader.getClass("com.sinosoft.cmcore.tag.impl." + className);
		TagBase tag = (TagBase) c.newInstance();
		sb.append(prefix + "if(" + varName + ".onTagStart()!=TagBase.SKIP){\n");
		if (tag.isIterative()) {
			if (node.getLevel() > 26) {
				throw new RuntimeException("子循环层次超过26层！");
			}
			String varIterative = iterativeVarNames[node.getLevel() - 1];
			sb.append(prefix + "\tfor(int " + varIterative + "=0;" + varName + ".prepareNext();" + varIterative
					+ "++){\n");
			TreeNodeList list = node.getChildren();
			for (int i = 0; i < list.size(); i++) {
				compileNode(list.get(i), sb, prefix + "\t\t");
			}
			sb.append(prefix + "\t}\n");
		} else {
			TreeNodeList list = node.getChildren();
			for (int i = 0; i < list.size(); i++) {
				compileNode(list.get(i), sb, prefix + "\t");
			}
		}
		sb.append(prefix + "\tcontext.removeTagNode();\n");
		sb.append(prefix + "\tif(" + varName + ".onTagEnd()==TagBase.END){\n");
		sb.append(prefix + "\t\treturn;\n");
		sb.append(prefix + "\t}\n");
		sb.append(prefix + "}\n");
	}

	public static void main(String[] args) {
		TemplateComplier tc = new TemplateComplier();
		tc.complieFile("F:/Workspace_Product/ZCMS/UI/wwwroot/ZCMSDemo/template/detail.html");
		tc.complieFile("F:/Workspace_Product/ZCMS/UI/wwwroot/ZCMSDemo/template/kjindex.html");
		tc.complieFile("F:/Workspace_Product/ZCMS/UI/wwwroot/ZCMSDemo/template/list.html");
		tc.complieFile("F:/Workspace_Product/ZCMS/UI/wwwroot/ZCMSDemo/template/head.html");
		tc.complieFile("F:/Workspace_Product/ZCMS/UI/wwwroot/ZCMSDemo/template/footer.html");
		tc.complieFile("F:/Workspace_Product/ZCMS/UI/wwwroot/ZCMSDemo/template/vote.html");
		tc.complieFile("F:/Workspace_Product/ZCMS/UI/wwwroot/ZCMSDemo/template/form.html");
		tc.complieFile("F:/Workspace_Product/ZCMS/UI/wwwroot/ZCMSDemo/template/image.html");
		tc.complieFile("F:/Workspace_Product/ZCMS/UI/wwwroot/ZCMSDemo/template/imagelist.html");
	}
}
