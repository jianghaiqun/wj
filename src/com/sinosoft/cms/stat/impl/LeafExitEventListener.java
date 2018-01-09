package com.sinosoft.cms.stat.impl;

import com.sinosoft.cms.stat.VisitCount.ItemValue;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.ExitEventListener;

/**
 * @Author 王育春
 * @Date 2009-5-3
 * @Mail wyuch@midding.com
 */
public class LeafExitEventListener extends ExitEventListener {
	protected String type;

	protected String subType;

	protected Object[][] arr = new Object[50][2];

	protected int index = 0;

	public LeafExitEventListener(String type, String subType) {
		this.type = type;
		this.subType = subType;
	}

	public void onExit(Object key, Object value) {
		synchronized (this) {
			arr[index][0] = key;
			arr[index][1] = value;
			if (index == 49) {
				update();
				index = 0;
			} else {
				index++;
			}
		}
	}

	public void update() {
		QueryBuilder qb = null;
		if (subType.equals("PV")) {
			qb = new QueryBuilder("update ZC" + type + " set HitCount=HitCount+? where ID=?");
		} else {
			qb = new QueryBuilder("update ZC" + type + " set StickTime=(HitCount*StickTime+?)/(HitCount+?) where ID=?");
		}
		qb.setBatchMode(true);
		for (int i = 0; i < arr.length; i++) {
			ItemValue v = (ItemValue) arr[i][1];
			if (subType.equals("PV")) {
				qb.add(v.Count);
				qb.add(arr[i][0]);
			} else {
				qb.add(v.Count);
				qb.add(v.Divisor);
				qb.add(arr[i][0]);
			}
			qb.addBatch();
		}
		qb.executeNoQuery();
	}
}
