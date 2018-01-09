package com.sinosoft.framework.controls;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.StringUtil;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
public class ExplorerTag extends BodyTagSupport {
	private static final Logger logger = LoggerFactory.getLogger(ExplorerTag.class);

	private static final long serialVersionUID = 1L;

	private String id;

	private String name;

	private int type;

	private String baseDir;

	private String filter;

	private String exclude;

	private String column;

	private boolean page;

	private int size;

	private String style;

	public int doAfterBody() throws JspException {
		try {
			String ExplorerPath = this.pageContext.getRequest().getParameter("ExplorerPath");
			if (ExplorerPath == null) {
				ExplorerPath = "";
			}
			StringBuffer sb = new StringBuffer();
			if (type == 0 || type == 1) {
				String html = FileUtil.readText(ExplorerTag.class.getResourceAsStream("File.template"), "UTF-8");// 必须是UTF-8
				String script = html.substring(html.indexOf("#0{Start}") + 10, html.indexOf("#0{End}"));
				String head = html.substring(html.indexOf("#1{Start}") + 10, html.indexOf("#1{End}"));
				String main = html.substring(html.indexOf("#2{Start}") + 10, html.indexOf("#2{End}"));
				sb.append(script);

				// 处理路径
				String[] arr = ExplorerPath.split("\\/");
				StringBuffer psb = new StringBuffer();

				psb.append(TButtonTag.getHtml("Explorer.goPath('')", "<img src='" + Config.getContextPath()
						+ "Platform/Images/none.gif'>", "/根目录", false));

				for (int i = 0; i < arr.length; i++) {
					if (arr[i] != null && !arr[i].equals("")) {
						StringBuffer jsb = new StringBuffer();
						for (int j = 0; j <= i; j++) {
							if (j != 0) {
								jsb.append("/");
							}
							jsb.append(arr[j]);
						}
						psb.append(TButtonTag.getHtml("Explorer.goPath('" + jsb + "')", "<img src='"
								+ Config.getContextPath() + "Platform/Images/none.gif'>", "/" + jsb, false));
					}
				}
				psb.append("</select>");

				Mapx map = new Mapx();
				map.put("PathSelector", psb.toString());
				sb.append(HtmlUtil.replacePlaceHolder(head, map, true));
				if (this.style == null) {
					this.style = "";
				}
				sb.append("<div style=\"margin-top:0px;height:370;overflow:auto;" + this.style + "\">");

				try {
					HtmlTable table = new HtmlTable();
					table.parseHtml(main);
					HtmlTR tr = table.getTR(1);

					String style1 = (String) tr.getAttribute("style1");
					String style2 = (String) tr.getAttribute("style2");
					String class1 = (String) tr.getAttribute("class1");
					String class2 = (String) tr.getAttribute("class2");
					tr.removeAttribute("style1");
					tr.removeAttribute("style2");
					tr.removeAttribute("class1");
					tr.removeAttribute("class2");

					String trHtml = tr.getOuterHtml();
					DataTable dt = null;
					String filePath = baseDir + "/" + ExplorerPath;
					if (filter != null && !"".equals(filter)) {
						// 显示目录
						IOFileFilter dirFilter = FileFilterUtils.directoryFileFilter();
						// 显示指定类型文件
						IOFileFilter suffixFilter = new SuffixFileFilter(filter.split("\\|"));
						// 显示目录和指定类型文件
						IOFileFilter allFilter = FileFilterUtils.orFileFilter(dirFilter, suffixFilter);
						if ("dir".equals(filter.toLowerCase())) {
							dt = getFileInfoDataTable(filePath, FileFilterUtils.makeSVNAware(dirFilter));
						} else {
							dt = getFileInfoDataTable(filePath, FileFilterUtils.makeSVNAware(allFilter));
						}
					} else {
						dt = getFileInfoDataTable(filePath, FileFilterUtils.makeSVNAware(FileFilterUtils
								.trueFileFilter()));
					}

					dt.setWebMode(true);
					table.Children.remove(1);
					for (int i = 0; i < dt.getRowCount(); i++) {
						tr = new HtmlTR();
						tr.parseHtml(HtmlUtil.replacePlaceHolder(trHtml, dt.getDataRow(i).toMapx(), false, true));
						if (i % 2 == 1) {
							if (style1 != null) {
								tr.setAttribute("style", style1);
							}
							if (class1 != null) {
								tr.setAttribute("class", class1);
							}
						} else {
							if (style2 != null) {
								tr.setAttribute("style", style2);
							}
							if (class2 != null) {
								tr.setAttribute("class", class2);
							}
						}
						table.Children.add(tr);
					}
					table.setAttribute("id", id);
					table.setAttribute("baseDir", baseDir);
					sb.append(table.getOuterHtml());
					sb.append("</div>");
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}

			this.getPreviousOut().write(sb.toString());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return EVAL_PAGE;
	}

	public static DataTable getFileInfoDataTable(String path, FileFilter filter) {
		path = FileUtil.normalizePath(path);
		String context = Config.getContextRealPath();
		context = context.replace('\\', '/');
		context = context.replaceAll("/+", "/");
		if (!path.startsWith(context)) {
			return new DataTable();
		}
		return getFileInfoDataTable(new File(path), filter);
	}

	public static DataTable getFileInfoDataTable(File f, FileFilter filter) {
		if (f.exists() && f.isDirectory()) {
			File[] fs = f.listFiles(filter);
			DataColumn[] dcs = new DataColumn[6];
			Object[][] values = new Object[fs.length][dcs.length];
			String[] names = new String[] { "Name", "Title", "ModifyTime", "Size", "Icon", "Type" };
			for (int i = 0; i < dcs.length; i++) {
				DataColumn dc = new DataColumn();
				dc.setColumnName(names[i]);
				dc.setColumnType(DataColumn.STRING);
				dcs[i] = dc;
			}
			String extPrefix = Config.getContextPath() + "Framework/Images/FileType/";
			ArrayList arrayDir = new ArrayList();
			ArrayList arrayFile = new ArrayList();
			Object[] row = null;
			for (int i = 0; i < fs.length; i++) {
				File cf = fs[i];
				String name = cf.getName();
				row = new Object[dcs.length];
				row[0] = name;
				if (cf.isDirectory()) {
					row[4] = extPrefix + "folder.gif";
				} else {
					if (name.indexOf('.') > 0) {
						String ext = name.substring(name.lastIndexOf('.') + 1).toLowerCase();
						String str = "." + ext + ".";
						if (".html.htm.shtml.".indexOf(str) >= 0) {
							ext = "html";
						} else if (".zip.rar.jar.war.ear.".indexOf(str) >= 0) {
							ext = "zip";
						} else if (".wma.mpg.mpeg.avi.".indexOf(str) >= 0) {
							ext = "wmp";
						} else if (".rm.rmvb.".indexOf(str) >= 0) {
							ext = "rm";
						} else if (".doc.docx.rtf.".indexOf(str) >= 0) {
							ext = "doc";
						} else if (".xls.xlsx.csv.".indexOf(str) >= 0) {
							ext = "xls";
						} else if (".aspx.asp.jsp.php.js.bmp.exe.swf.flv.wmv.gif.jpg.png.mov.mp3.mp4.".indexOf(str) >= 0) {
							// 各自有专用图标
						} else if (".css.txt.xml.sql.".indexOf(str) >= 0) {
							ext = "txt";
						} else {
							ext = "unknown";
						}
						if (".html.htm.shtml.jsp.php.asp.aspx.".indexOf(str) >= 0) {
							row[1] = StringUtil.getHtmlTitle(cf);
						}
						row[4] = extPrefix + ext + ".gif";
					} else {
						row[4] = extPrefix + "unknown.gif";
					}
				}

				row[2] = DateUtil.toDateTimeString(new Date(cf.lastModified()));
				long len = cf.length();
				if (len < 1024) {
					row[3] = String.valueOf(len);
				} else if (len < 1048576) {
					row[3] = NumberUtil.round(len * 1.0 / 1024, 1) + "K";
				} else if (len < 1073741824) {
					row[3] = NumberUtil.round(len * 1.0 / 1048576, 1) + "M";
				} else if (len < 1099511627776L) {
					row[3] = NumberUtil.round(len * 1.0 / 1073741824, 1) + "G";
				}
				if (cf.isDirectory()) {
					row[3] = "";
					row[5] = "D";
					arrayDir.add(row);
				} else {
					row[5] = "F";
					arrayFile.add(row);
				}
			}
			if (fs.length <= 0) {
				values = new Object[1][dcs.length];
				row = new Object[dcs.length];
				row[0] = "本文件夹没有任何文件,您可以先上传文件";
				row[1] = "&nbsp;";
				row[2] = "&nbsp;";
				row[3] = "&nbsp;";
				row[4] = extPrefix + "none.gif";
				row[5] = "N";
				arrayFile.add(row);
			}
			int j = 0;
			for (int i = 0; i < arrayDir.size(); i++, j++) {
				values[j] = (Object[]) arrayDir.get(i);
			}
			for (int i = 0; i < arrayFile.size(); i++, j++) {
				values[j] = (Object[]) arrayFile.get(i);
			}
			DataTable dt = new DataTable(dcs, values);
			dt.sort(new Comparator() {
				public int compare(Object row1, Object row2) {
					String name1 = ((DataRow) row1).getString("Name").toLowerCase();
					String type1 = ((DataRow) row1).getString("Type");
					String name2 = ((DataRow) row2).getString("Name").toLowerCase();
					String type2 = ((DataRow) row2).getString("Type");
					if (type1.compareTo(type2) == 0) {
						return name1.compareTo(name2);
					} else {
						return type1.compareTo(type2);
					}
				}
			});
			return dt;
		}
		return new DataTable();
	}

	public String getBaseDir() {
		return baseDir;
	}

	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getExclude() {
		return exclude;
	}

	public void setExclude(String exclude) {
		this.exclude = exclude;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public boolean isPage() {
		return page;
	}

	public void setPage(boolean page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
		/* ${_ZVING_LICENSE_CODE_} */
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
}
