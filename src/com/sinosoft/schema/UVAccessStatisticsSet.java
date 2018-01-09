package com.sinosoft.schema;

import com.sinosoft.schema.UVAccessStatisticsSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class UVAccessStatisticsSet extends SchemaSet {
	public UVAccessStatisticsSet() {
		this(10,0);
	}

	public UVAccessStatisticsSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public UVAccessStatisticsSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = UVAccessStatisticsSchema._TableCode;
		Columns = UVAccessStatisticsSchema._Columns;
		NameSpace = UVAccessStatisticsSchema._NameSpace;
		InsertAllSQL = UVAccessStatisticsSchema._InsertAllSQL;
		UpdateAllSQL = UVAccessStatisticsSchema._UpdateAllSQL;
		FillAllSQL = UVAccessStatisticsSchema._FillAllSQL;
		DeleteSQL = UVAccessStatisticsSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new UVAccessStatisticsSet();
	}

	public boolean add(UVAccessStatisticsSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(UVAccessStatisticsSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(UVAccessStatisticsSchema aSchema) {
		return super.remove(aSchema);
	}

	public UVAccessStatisticsSchema get(int index) {
		UVAccessStatisticsSchema tSchema = (UVAccessStatisticsSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, UVAccessStatisticsSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(UVAccessStatisticsSet aSet) {
		return super.set(aSet);
	}
}
 