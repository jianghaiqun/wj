package com.sinosoft.framework.clustering.client;

import com.sinosoft.framework.clustering.Clustering.Server;
import com.sinosoft.framework.clustering.server.ClusteringData;
import com.sinosoft.framework.utility.Mapx;

public abstract class ClusteringClient {
	Mapx clientMap = new Mapx();

	public static ClusteringClient getClient(Server server) {
		if (server.URL.startsWith("http")) {
			return new HttpClusteringClient(server);
		} else if (server.URL.startsWith("socket")) {
			return new SocketClusteringClient(server);
		} else if (server.URL.startsWith("memcached")) {
			return new MemcachedClusteringClient(server);
		}
		throw new RuntimeException("不支持的集群服务器类型:" + server.URL);
	}

	public boolean containsKey(String key) {
		String result = executeMethod("Data", "Get", key, null);
		return result.startsWith(ClusteringData.RESULT_TRUE);
	}

	public String get(String key) {
		String result = executeMethod("Data", "Get", key, null);
		if (result.startsWith(ClusteringData.RESULT_NULL)) {
			return null;
		}
		return result.substring(result.indexOf('\n') + 1);
	}

	public void put(String key, String value) {
		executeMethod("Data", "Put", key, value);
	}

	public void remove(String key) {
		executeMethod("Data", "Remove", key, null);
	}

	public double add(String key, double number) {
		String result = executeMethod("Data", "Add", key, "" + number);
		return Double.parseDouble(result);
	}

	public double addAverage(String key, long number) {
		return addAverage(key, number, 1);
	}

	public double addAverage(String key, long total, int count) {
		String result = executeMethod("Data", "AddAverage", key, total + "|" + count);
		return Double.parseDouble(result);
	}

	public abstract String executeMethod(String type, String action, String key, String value);
}
