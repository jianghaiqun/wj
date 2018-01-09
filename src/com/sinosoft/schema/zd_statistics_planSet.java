package com.sinosoft.schema;

import com.sinosoft.schema.zd_statistics_planSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class zd_statistics_planSet extends SchemaSet {
	public zd_statistics_planSet() {
		this(10,0);
	}

	public zd_statistics_planSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public zd_statistics_planSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = zd_statistics_planSchema._TableCode;
		Columns = zd_statistics_planSchema._Columns;
		NameSpace = zd_statistics_planSchema._NameSpace;
		InsertAllSQL = zd_statistics_planSchema._InsertAllSQL;
		UpdateAllSQL = zd_statistics_planSchema._UpdateAllSQL;
		FillAllSQL = zd_statistics_planSchema._FillAllSQL;
		DeleteSQL = zd_statistics_planSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new zd_statistics_planSet();
	}

	public boolean add(zd_statistics_planSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(zd_statistics_planSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(zd_statistics_planSchema aSchema) {
		return super.remove(aSchema);
	}

	public zd_statistics_planSchema get(int index) {
		zd_statistics_planSchema tSchema = (zd_statistics_planSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, zd_statistics_planSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(zd_statistics_planSet aSet) {
		return super.set(aSet);
	}
}
 