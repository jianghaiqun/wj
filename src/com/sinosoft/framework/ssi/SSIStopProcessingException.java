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

/**
 * Exception used to tell SSIProcessor that it should stop processing SSI
 * commands. This is used to mimick the Apache behavior in #set with invalid
 * attributes.
 * 
 * @author Paul Speed
 * @author Dan Sandberg
 * @version $Revision: 565195 $, $Date: 2007-08-12 17:00:41 -0600 (Sun, 12 Aug
 *          2007) $
 */
public class SSIStopProcessingException extends Exception {

	private static final long serialVersionUID = 1L;
}