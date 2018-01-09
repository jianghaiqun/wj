package com.sinosoft.schema;

import com.sinosoft.schema.TradeInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class TradeInfoSet extends SchemaSet {
	public TradeInfoSet() {
		this(10,0);
	}

	public TradeInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public TradeInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = TradeInfoSchema._TableCode;
		Columns = TradeInfoSchema._Columns;
		NameSpace = TradeInfoSchema._NameSpace;
		InsertAllSQL = TradeInfoSchema._InsertAllSQL;
		UpdateAllSQL = TradeInfoSchema._UpdateAllSQL;
		FillAllSQL = TradeInfoSchema._FillAllSQL;
		DeleteSQL = TradeInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new TradeInfoSet();
	}

	public boolean add(TradeInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(TradeInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(TradeInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public TradeInfoSchema get(int index) {
		TradeInfoSchema tSchema = (TradeInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, TradeInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(TradeInfoSet aSet) {
		return super.set(aSet);
	}
}
 