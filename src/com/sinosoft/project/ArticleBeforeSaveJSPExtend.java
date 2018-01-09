package com.sinosoft.project;

import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.framework.CookieImpl;
import com.sinosoft.framework.RequestImpl;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.extend.JSPExtendAction;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.workflow.Workflow;
import com.sinosoft.workflow.WorkflowUtil;
import com.sinosoft.workflow.Workflow.Node;
public class ArticleBeforeSaveJSPExtend extends JSPExtendAction{
	public String execute(RequestImpl request, CookieImpl cookie) {
		String catalogID = request.getString("CatalogID");
		String articleID = request.getString("ArticleID");
		if (StringUtil.isEmpty(catalogID)) {
			if (StringUtil.isNotEmpty(articleID)) {
				catalogID = new QueryBuilder("select catalogID from ZCArticle where ID=?", Long.parseLong(articleID)).executeString();
			} else {
				return "";
			}
		}
		StringBuffer sb = new StringBuffer();
		String workflowID = CatalogUtil.getWorkflow(catalogID);
		if (StringUtil.isNotEmpty(workflowID)) {
			Workflow flow = WorkflowUtil.findWorkflow(Long.parseLong(workflowID));
			Node[] nodes = flow.getNodes();
			
			sb.append("if(0==tsflag){");
			sb.append("if(false");
			for (int i = 0; i < nodes.length; i++) {
				if (nodes[i].getType().equals(Workflow.ACTIONNODE)) {
					//当工作流到“审核通过”时，弹出推送任务处理框
					if ("审核通过".equals(nodes[i].getName())) {
						sb.append("||actionId==" + nodes[i].getID());
					}
				}
			}
			sb.append("){");
			if(StringUtil.isNotEmpty(articleID)){
				ZCArticleSchema article = new ZCArticleSchema().query(
						new QueryBuilder("where id=?",Long.parseLong(articleID))).get(0);
				String prop4 = article.getProp4();
				if(null!=prop4&&!"".equals(prop4)&&!"null".equals(prop4)){
					String[] rs = prop4.split(";");
					if(rs.length!=0&&"ts".equals(rs[0])){
						sb.append("exe(arguments);return;");
					}
				}
			}
			sb.append("}\n");
			sb.append("}\n");
			return sb.toString();
		}
		return "";
	}

	public String getTarget() {
		return "Article.BeforeSave";
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
}
