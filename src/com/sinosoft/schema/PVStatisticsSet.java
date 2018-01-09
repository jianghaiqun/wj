package com.sinosoft.schema;

import com.sinosoft.schema.PVStatisticsSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class PVStatisticsSet extends SchemaSet {
	public PVStatisticsSet() {
		this(10,0);
	}

	public PVStatisticsSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public PVStatisticsSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = PVStatisticsSchema._TableCode;
		Columns = PVStatisticsSchema._Columns;
		NameSpace = PVStatisticsSchema._NameSpace;
		InsertAllSQL = PVStatisticsSchema._InsertAllSQL;
		UpdateAllSQL = PVStatisticsSchema._UpdateAllSQL;
		FillAllSQL = PVStatisticsSchema._FillAllSQL;
		DeleteSQL = PVStatisticsSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new PVStatisticsSet();
	}

	public boolean add(PVStatisticsSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(PVStatisticsSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(PVStatisticsSchema aSchema) {
		return super.remove(aSchema);
	}

	public PVStatisticsSchema get(int index) {
		PVStatisticsSchema tSchema = (PVStatisticsSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, PVStatisticsSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(PVStatisticsSet aSet) {
		return super.set(aSet);
	}
}
 