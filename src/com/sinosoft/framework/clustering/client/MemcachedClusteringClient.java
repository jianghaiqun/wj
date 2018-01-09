package com.sinosoft.framework.clustering.client;

import com.sinosoft.framework.clustering.Clustering.Server;

/**
 * 基于Memcached的集群，暂未实现
 * 
 */
public class MemcachedClusteringClient extends ClusteringClient {
	private Server server;

	public MemcachedClusteringClient(Server server) {
		this.server = server;
	}

	public String executeMethod(String type, String action, String key, String value) {
		return null;
	}
}
