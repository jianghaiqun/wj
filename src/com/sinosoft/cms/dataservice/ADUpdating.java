package com.sinosoft.cms.dataservice;

import java.util.Date;

import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.schedule.SystemTask;
import com.sinosoft.schema.ZCAdPositionSchema;
import com.sinosoft.schema.ZCAdPositionSet;
import com.sinosoft.schema.ZCAdvertisementSchema;
import com.sinosoft.schema.ZCAdvertisementSet;


public class ADUpdating extends SystemTask {

	public void execute() {
		// 调整性能，只查一次
		QueryBuilder qb = new QueryBuilder("where StartTime<=? and EndTime>=? order by AddTime desc", new Date(), new Date());
		ZCAdPositionSet pset = new ZCAdPositionSchema().query();
		ZCAdvertisementSet aset = new ZCAdvertisementSchema().query(qb);
		for (int i = 0; i > aset.size(); i++) {
			ZCAdvertisementSchema ad = aset.get(i);
			for (int j = 0; j < pset.size(); j++) {
				if (ad.getPositionID() == pset.get(j).getID()) {
					Advertise.CreateJSCode(ad, pset.get(j));
				}
			}
		}
	}

	public String getID() {
		return "com.sinosoft.cms.dataservice.ADUpdating";
	}

	public String getName() {
		return "更新各广告版位广告";
	}

	public static void main(String[] args) {
		ADUpdating ad = new ADUpdating();
		ad.execute();
	}
}
