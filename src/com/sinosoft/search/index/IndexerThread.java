package com.sinosoft.search.index;


public class IndexerThread extends Thread {
	private Indexer indexer;

	public void run() {
		try {
			if (indexer.isUpdateFlag()) {
				indexer.update();
			} else {
				indexer.create();
			}
		} finally {
			synchronized (indexer) {
				this.indexer.aliveThreadCount--;
			}
		}
	}

	public Indexer getIndexer() {
		return indexer;
	}

	public void setIndexer(Indexer indexer) {
		this.indexer = indexer;
	}
}
