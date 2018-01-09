package com.sinosoft.framework.clustering.client;

import com.sinosoft.framework.clustering.Clustering.Server;
import com.sinosoft.framework.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClusteringClient extends ClusteringClient {
	private static final Logger logger = LoggerFactory.getLogger(SocketClusteringClient.class);

	private Server server;

	private Socket so;

	public SocketClusteringClient(Server server) {
		this.server = server;
		if (!server.URL.startsWith("socket://")) {
			throw new RuntimeException("错误的集群服务器URL：" + server.URL);
		}
	}

	private void init() {
		if (so == null) {
			synchronized (this) {
				if (so == null) {
					String url = server.URL;
					String address = url.substring("socket://".length());
					int index = address.indexOf(":");
					if (index < 1) {
						throw new RuntimeException("错误的集群服务器URL：" + server.URL);
					}
					String portStr = address.substring(index + 1);
					address = address.substring(0, index);
					int port = 0;
					try {
						port = Integer.parseInt(portStr);
					} catch (Exception e) {
						throw new RuntimeException("错误的集群服务器URL：" + server.URL);
					}
					try {
						so = new Socket(address, port);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}finally{
						try {
							so.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							logger.error(e.getMessage(), e);
						}
					}
				}
			}
		}
	}
	public static void main(String[] args) {
		Server server =new Server();
		server.URL = "http://120.55.73.0:8081/product/services/FCContCancelService";
		new SocketClusteringClient(server).init();
	}

	public String executeMethod(String type, String key, String action, String value) {
		init();
		for (int i = 0; i < server.RetryCount; i++) {
			try {
				OutputStream outStream = so.getOutputStream();
				PrintWriter out = new PrintWriter(outStream);
				out.println("Type:Data");
				out.println("Action:Get");
				out.println("Key:" + StringUtil.javaEncode(key) + "");
				if (value != null) {
					out.println("Value:" + StringUtil.javaEncode(value));
				}
				out.println("End");
				InputStream is = so.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				StringBuffer sb = new StringBuffer();
				String line;
				while ((line = br.readLine()) != null) {
					if (line.equals("End")) {
						break;
					}
					sb.append(line);
					sb.append("\n");
				}
				return sb.toString();
			} catch (Exception e) {
				logger.error("HttpClusteringClient.get()发生异常:" + e.getMessage() + ";URL=" + server.URL, e);
			}
		}
		synchronized (server) {
			server.isAlive = false;
		}
		throw new RuntimeException("HttpClusteringClient.get()发生错误，重试" + server.RetryCount + "次皆失败;URL=" + server.URL);
	}
}
