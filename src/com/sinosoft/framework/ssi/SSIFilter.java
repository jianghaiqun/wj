/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sinosoft.framework.ssi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.extend.BeforeSSIFilterAction;
import com.sinosoft.framework.extend.ExtendManager;
import com.sinosoft.framework.utility.StringUtil;

/**
 * Filter to process SSI requests within a webpage. Mapped to a content types
 * from within web.xml.
 * 
 * @author David Becker
 * @version $Revision: 565195 $, $Date: 2007-08-12 17:00:41 -0600 (Sun, 12 Aug
 *          2007) $
 * @see org.apache.catalina.ssi.SSIServlet
 */
public class SSIFilter implements Filter {
	protected FilterConfig config = null;
	/** Debug level for this servlet. */
	protected int debug = 0;
	/** Expiration time in seconds for the doc. */
	protected Long expires = null;
	/** virtual path can be webapp-relative */
	protected boolean isVirtualWebappRelative = true;

	/** regex pattern to match when evaluating content types */
	// protected Pattern contentTypeRegEx = null;
	/** default pattern for ssi filter content type matching */
	// protected Pattern shtmlRegEx =
	// Pattern.compile("text/x-server-parsed-html(;.*)?");

	// ----------------- Public methods.
	/**
	 * Initialize this servlet.
	 * 
	 * @exception ServletException
	 *                if an error occurs
	 */
	public void init(FilterConfig config) throws ServletException {
		this.config = config;

		String value = null;
		try {
			value = config.getInitParameter("debug");
			debug = Integer.parseInt(value);
		} catch (Throwable t) {
			;
		}
		try {
			value = config.getInitParameter("expires");
			if (StringUtil.isEmpty(value)) {
				value = "0";// 默认不缓存
			}
			expires = Long.valueOf(value);
		} catch (NumberFormatException e) {
			expires = null;
			config.getServletContext().log("Invalid format for expires initParam; expected integer (seconds)");
		} catch (Throwable t) {
			;
		}
		if (debug > 0)
			config.getServletContext().log("SSIFilter.init() SSI invoker started with 'debug'=" + debug);
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		if (ExtendManager.hasAction(BeforeSSIFilterAction.Type)) {
			ExtendManager.executeAll(BeforeSSIFilterAction.Type, new Object[] { request, response, chain });
		}

		// cast once
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		if (Config.ServletMajorVersion == 2 && Config.ServletMinorVersion == 3) {
			response.setContentType("text/html;charset=" + Constant.GlobalCharset);
		} else {
			response.setCharacterEncoding(Constant.GlobalCharset);
		}
		request.setCharacterEncoding(Constant.GlobalCharset);

		// indicate that we're in SSI processing
		req.setAttribute(Globals.SSI_FLAG_ATTR, "true");
		req.setAttribute(Globals.INCLUDE_CONTEXT_PATH_ATTR, "true");// 避免被Tomcat忽略掉的BUG

		// setup to capture output
		ByteArrayServletOutputStream basos = new ByteArrayServletOutputStream();
		ResponseIncludeWrapper responseIncludeWrapper = new ResponseIncludeWrapper(config.getServletContext(), req,
				res, basos);

		// process remainder of filter chain
		chain.doFilter(req, responseIncludeWrapper);

		// we can't assume the chain flushed its output
		responseIncludeWrapper.flushOutputStreamOrWriter();
		byte[] bytes = basos.toByteArray();

		// get content type
		// String contentType = responseIncludeWrapper.getContentType();

		// is this an allowed type for SSI processing?
		// 改为不判断 by wyuch
		// if (contentTypeRegEx.matcher(contentType).matches()) {
		String encoding = res.getCharacterEncoding();

		// set up SSI processing
		SSIExternalResolver ssiExternalResolver = new SSIServletExternalResolver(config.getServletContext(), req, res,
				isVirtualWebappRelative, debug, encoding);
		SSIProcessor ssiProcessor = new SSIProcessor(ssiExternalResolver, debug);

		// prepare readers/writers
		Reader reader = new InputStreamReader(new ByteArrayInputStream(bytes), encoding);
		ByteArrayOutputStream ssiout = new ByteArrayOutputStream();
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(ssiout, encoding));

		// do SSI processing
		long lastModified = ssiProcessor.process(reader, responseIncludeWrapper.getLastModified(), writer);

		// set output bytes
		writer.flush();
		bytes = ssiout.toByteArray();

		// override headers
		if (expires != null) {
			res.setDateHeader("expires", (new java.util.Date()).getTime() + expires.longValue() * 1000);
		}
		if (lastModified > 0) {
			res.setDateHeader("last-modified", lastModified);
		}
		res.setDateHeader("last-modified", System.currentTimeMillis());
		res.setContentLength(bytes.length);

		res.setContentType("text/html;charset=" + Constant.GlobalCharset);

		try {
			OutputStream out = res.getOutputStream();
			out.write(bytes);
		} catch (Throwable t) {
			try {
				Writer out = res.getWriter();
				out.write(new String(bytes));
			} catch (Throwable t2) {
				// 不输出
			}
		}
	}

	public void destroy() {
	}
}
