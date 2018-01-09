package com.sinosoft.message;

import com.sinosoft.schema.ZCArticleSchema;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author 周翔
 * @Date 2012-5-24
 * @Mail zhouxiang@sinosoft.com
 */

public class EditBkAction extends HttpServlet {

	protected void service(HttpServletRequest request,
			HttpServletResponse response, String method)
			throws ServletException, IOException {
		String tBkId = request.getParameter("BkId");
		ZCArticleSchema tZCArticleSchema=new ZCArticleSchema();
		tZCArticleSchema.setID(tBkId);
		tZCArticleSchema.fill();
		HttpSession session = request.getSession();
		session.setAttribute("ZCArticleSchema", tZCArticleSchema);
		response.sendRedirect("./message/editBk.jsp");

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		service(request, response, "GET");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		service(request, response, "POST");
	}
}
