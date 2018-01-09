package com.sinosoft.schema;

import com.sinosoft.schema.TradeInformationSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class TradeInformationSet extends SchemaSet {
	public TradeInformationSet() {
		this(10,0);
	}

	public TradeInformationSet(int initialCapacity) { 
		this(initialCapacity,0);
	}

	public TradeInformationSet(int initialCapacity,int capacityIncrement) { 
		super(initialCapacity,capacityIncrement);
		TableCode = TradeInformationSchema._TableCode;
		Columns = TradeInformationSchema._Columns;
		NameSpace = TradeInformationSchema._NameSpace;
		InsertAllSQL = TradeInformationSchema._InsertAllSQL;
		UpdateAllSQL = TradeInformationSchema._UpdateAllSQL;
		FillAllSQL = TradeInformationSchema._FillAllSQL;
		DeleteSQL = TradeInformationSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new TradeInformationSet();
	}

	public boolean add(TradeInformationSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(TradeInformationSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(TradeInformationSchema aSchema) {
		return super.remove(aSchema);
	}

	public TradeInformationSchema get(int index) {
		TradeInformationSchema tSchema = (TradeInformationSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, TradeInformationSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(TradeInformationSet aSet) {
		return super.set(aSet);
	}
}
 