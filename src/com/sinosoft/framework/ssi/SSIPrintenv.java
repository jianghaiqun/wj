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
import java.util.Collection;
import java.util.Iterator;
/**
 * Implements the Server-side #printenv command
 * 
 * @author Dan Sandberg
 * @author David Becker
 * @version $Revision: 565195 $, $Date: 2007-08-12 17:00:41 -0600 (Sun, 12 Aug 2007) $
 */
public class SSIPrintenv implements SSICommand {
    /**
     * @see SSICommand
     */
    public long process(SSIMediator ssiMediator, String commandName,
            String[] paramNames, String[] paramValues, PrintWriter writer) {
    	long lastModified = 0;
        //any arguments should produce an error
        if (paramNames.length > 0) {
            String errorMessage = ssiMediator.getConfigErrMsg();
            writer.write(errorMessage);
        } else {
            Collection variableNames = ssiMediator.getVariableNames();
            Iterator iter = variableNames.iterator();
            while (iter.hasNext()) {
                String variableName = (String)iter.next();
                String variableValue = ssiMediator
                        .getVariableValue(variableName);
                //This shouldn't happen, since all the variable names must
                // have values
                if (variableValue == null) {
                    variableValue = "(none)";
                }
                writer.write(variableName);
                writer.write('=');
                writer.write(variableValue);
                writer.write('\n');
                lastModified = System.currentTimeMillis();
            }
        }
        return lastModified;
    }
}
