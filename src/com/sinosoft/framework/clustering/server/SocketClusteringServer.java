package com.sinosoft.framework.clustering.server;

import com.sinosoft.framework.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketClusteringServer {
	private static final Logger logger = LoggerFactory.getLogger(SocketClusteringServer.class);

	private static ServerSocket so;
	private static boolean stopFlag = false;
	private static Object mutex = new Object();

	public static void start(int port) {
		if (so == null) {
			synchronized (mutex) {
				if (so == null) {
					try {
						ServerSocket so = new ServerSocket(port);
						while (true) {
							try {
								if (stopFlag) {
									break;
								}
								new RequestHandler(so.accept());// 调用线程
							} catch (IOException e) {
								logger.error(e.getMessage(), e);
							}
						}
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		}
	}

	public static void stop() {
		try {
			// so.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private static class RequestHandler implements Runnable { // 线程类
		private Socket incoming;

		public RequestHandler(Socket incoming) {
			try {
				this.incoming = incoming;
				new Thread(this).start();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		public void run() {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
				StringBuffer sb = new StringBuffer();
				String line;
				while ((line = br.readLine()) != null) {
					if (line.equals("End")) {
						OutputStream outStream = incoming.getOutputStream();
						PrintWriter out = new PrintWriter(outStream);
						out.println(dealRequest(sb.toString()));
						out.println("End");
						out.flush();
						sb = new StringBuffer();
					} else if (line.equals("Shutdown")) {
						OutputStream outStream = incoming.getOutputStream();
						PrintWriter out = new PrintWriter(outStream);
						out.println(dealRequest(sb.toString()));
						out.println("End");
						out.flush();
						sb = new StringBuffer();
					} else {
						sb.append(line);
						sb.append("\n");
					}
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}

		public String dealRequest(String request) {
			String type = null, key = null, action = null, value = null;
			String[] arr = request.split("\\n");
			for (int i = 0; i < arr.length; i++) {
				String line = arr[i];
				if (StringUtil.isNotEmpty(line)) {
					int index = line.indexOf(":");
					if (index > 0) {
						String k = line.substring(0, index);
						String v = line.substring(index + 1);
						if (k.equals("Type")) {
							type = v;
						} else if (k.equals("Key")) {
							key = v;
						} else if (k.equals("Action")) {
							action = v;
						} else if (k.equals("Value")) {
							value = v;
						}
					}
				}
			}
			return ClusteringData.dealRequest(type, key, action, value);
		}
	}
}
