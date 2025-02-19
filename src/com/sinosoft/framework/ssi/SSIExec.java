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

import java.io.PrintWriter;

/**
 * Implements the Server-side #exec command
 * 
 * @author Bip Thelin
 * @author Amy Roh
 * @author Paul Speed
 * @author Dan Sandberg
 * @author David Becker
 * @version $Revision: 565195 $, $Date: 2007-08-12 17:00:41 -0600 (Sun, 12 Aug
 *          2007) $
 */
public class SSIExec implements SSICommand {
	protected SSIInclude ssiInclude = new SSIInclude();
	protected final static int BUFFER_SIZE = 1024;

	/**
	 * @see SSICommand
	 */
	public long process(SSIMediator ssiMediator, String commandName, String[] paramNames, String[] paramValues,
			PrintWriter writer) {
		long lastModified = 0;
		String paramName = paramNames[0];
		String paramValue = paramValues[0];
		String substitutedValue = ssiMediator.substituteVariables(paramValue);
		if (paramName.equalsIgnoreCase("cgi")) {
			lastModified = ssiInclude.process(ssiMediator, "include", new String[] { "virtual" },
					new String[] { substitutedValue }, writer);
		} else if (paramName.equalsIgnoreCase("cmd")) {
			ssiMediator.log("SSI cmd exec is no supported! ");// 为安全起见，禁止使用此功能
			/*
			 * boolean foundProgram = false; try { Runtime rt =
			 * Runtime.getRuntime(); Process proc = rt.exec(substitutedValue);
			 * foundProgram = true; BufferedReader stdOutReader = new
			 * BufferedReader( new InputStreamReader(proc.getInputStream()));
			 * BufferedReader stdErrReader = new BufferedReader( new
			 * InputStreamReader(proc.getErrorStream())); char[] buf = new
			 * char[BUFFER_SIZE]; IOTools.flow(stdErrReader, writer, buf);
			 * IOTools.flow(stdOutReader, writer, buf); proc.waitFor();
			 * lastModified = System.currentTimeMillis(); } catch
			 * (InterruptedException e) { ssiMediator.log("Couldn't exec file: "
			 * + substitutedValue, e); writer.write(configErrMsg); } catch
			 * (IOException e) { if (!foundProgram) { //apache doesn't output an
			 * error message if it can't find // a program }
			 * ssiMediator.log("Couldn't exec file: " + substitutedValue, e); }
			 */
		}
		return lastModified;
	}
}