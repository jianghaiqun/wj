package com.sinosoft.product;

import com.sinosoft.cms.publish.DlrSynProducts;
import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.User;
import com.sinosoft.framework.messages.LongTimeTask;

public class DlrSynAllProduct extends Ajax {
	


	public void syncAllProduct() {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				DlrSynProducts.allProduct(this);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		ltt.setCurrentInfo("开始重置......");
		$S("TaskID", "" + ltt.getTaskID());
	}
	public void syncAllProductArea() {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				DlrSynProducts.allProductArea(this);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		ltt.setCurrentInfo("开始重置......");
		$S("TaskID", "" + ltt.getTaskID());
	}
	public void syncAllProductHI() {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				DlrSynProducts.allProductHI(this);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		ltt.setCurrentInfo("开始重置......");
		$S("TaskID", "" + ltt.getTaskID());
	}
}
