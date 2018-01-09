package com.sinosoft.schema;

import com.sinosoft.schema.PointExchangeInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class PointExchangeInfoSet extends SchemaSet {
	public PointExchangeInfoSet() {
		this(10,0);
	}

	public PointExchangeInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public PointExchangeInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = PointExchangeInfoSchema._TableCode;
		Columns = PointExchangeInfoSchema._Columns;
		NameSpace = PointExchangeInfoSchema._NameSpace;
		InsertAllSQL = PointExchangeInfoSchema._InsertAllSQL;
		UpdateAllSQL = PointExchangeInfoSchema._UpdateAllSQL;
		FillAllSQL = PointExchangeInfoSchema._FillAllSQL;
		DeleteSQL = PointExchangeInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new PointExchangeInfoSet();
	}

	public boolean add(PointExchangeInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(PointExchangeInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(PointExchangeInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public PointExchangeInfoSchema get(int index) {
		PointExchangeInfoSchema tSchema = (PointExchangeInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, PointExchangeInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(PointExchangeInfoSet aSet) {
		return super.set(aSet);
	}
}
 