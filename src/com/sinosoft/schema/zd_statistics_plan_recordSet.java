package com.sinosoft.schema;

import com.sinosoft.schema.zd_statistics_plan_recordSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class zd_statistics_plan_recordSet extends SchemaSet {
	public zd_statistics_plan_recordSet() {
		this(10,0);
	}

	public zd_statistics_plan_recordSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public zd_statistics_plan_recordSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = zd_statistics_plan_recordSchema._TableCode;
		Columns = zd_statistics_plan_recordSchema._Columns;
		NameSpace = zd_statistics_plan_recordSchema._NameSpace;
		InsertAllSQL = zd_statistics_plan_recordSchema._InsertAllSQL;
		UpdateAllSQL = zd_statistics_plan_recordSchema._UpdateAllSQL;
		FillAllSQL = zd_statistics_plan_recordSchema._FillAllSQL;
		DeleteSQL = zd_statistics_plan_recordSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new zd_statistics_plan_recordSet();
	}

	public boolean add(zd_statistics_plan_recordSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(zd_statistics_plan_recordSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(zd_statistics_plan_recordSchema aSchema) {
		return super.remove(aSchema);
	}

	public zd_statistics_plan_recordSchema get(int index) {
		zd_statistics_plan_recordSchema tSchema = (zd_statistics_plan_recordSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, zd_statistics_plan_recordSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(zd_statistics_plan_recordSet aSet) {
		return super.set(aSet);
	}
}
 