/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sinosoft.framework.data.nativejdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Implementation of the {@link NativeJdbcExtractor} interface for the Jakarta Commons DBCP connection pool, version 1.1
 * or higher.
 * 
 * <p>
 * Returns the underlying native Connection, Statement, etc to application code instead of DBCP's wrapper
 * implementations. The returned JDBC classes can then safely be cast, e.g. to <code>oracle.jdbc.OracleConnection</code>.
 * 
 * <p>
 * This NativeJdbcExtractor can be set just to <i>allow</i> working with a Commons DBCP DataSource: If a given object is
 * not a Commons DBCP wrapper, it will be returned as-is.
 * 
 * <p>
 * Note that this version of CommonsDbcpNativeJdbcExtractor will work against the original Commons DBCP in
 * <code>org.apache.commons.dbcp</code> as well as against Tomcat 5.5's relocated Commons DBCP version in the
 * <code>org.apache.tomcat.dbcp.dbcp</code> package.
 * 
 * @author Juergen Hoeller
 * @since 25.08.2003
 */
public class CommonsDbcpNativeJdbcExtractor {
	private static final Logger logger = LoggerFactory.getLogger(CommonsDbcpNativeJdbcExtractor.class);
	private static final String GET_INNERMOST_DELEGATE_METHOD_NAME = "getInnermostDelegate";

	/**
	 * Extracts the innermost delegate from the given Commons DBCP object. Falls back to the given object if no
	 * underlying object found.
	 * 
	 * @param obj
	 *            the Commons DBCP Connection/Statement/ResultSet
	 * @return the underlying native Connection/Statement/ResultSet
	 */
	private static Object getInnermostDelegate(Object obj) throws SQLException {
		if (obj == null) {
			return null;
		}
		try {
			Class classToAnalyze = obj.getClass();
			while (!Modifier.isPublic(classToAnalyze.getModifiers())) {
				classToAnalyze = classToAnalyze.getSuperclass();
				if (classToAnalyze == null) {
					// No public provider class found -> fall back to given object.
					return obj;
				}
			}
			Method getInnermostDelegate = classToAnalyze.getMethod(GET_INNERMOST_DELEGATE_METHOD_NAME, (Class[]) null);
			Object delegate = getInnermostDelegate.invoke(obj, new Object[0]);
			return (delegate != null ? delegate : obj);
		} catch (SecurityException ex) {
			throw new IllegalStateException("Commons DBCP getInnermostDelegate method is not accessible: " + ex);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return obj;
	}

	public static Connection doGetNativeConnection(Connection con) throws SQLException {
		return (Connection) getInnermostDelegate(con);
	}
}
