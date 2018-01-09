package com.sinosoft.schema;

import com.sinosoft.schema.TradeSummaryInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class TradeSummaryInfoSet extends SchemaSet {
	public TradeSummaryInfoSet() {
		this(10,0);
	}

	public TradeSummaryInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public TradeSummaryInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = TradeSummaryInfoSchema._TableCode;
		Columns = TradeSummaryInfoSchema._Columns;
		NameSpace = TradeSummaryInfoSchema._NameSpace;
		InsertAllSQL = TradeSummaryInfoSchema._InsertAllSQL;
		UpdateAllSQL = TradeSummaryInfoSchema._UpdateAllSQL;
		FillAllSQL = TradeSummaryInfoSchema._FillAllSQL;
		DeleteSQL = TradeSummaryInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new TradeSummaryInfoSet();
	}

	public boolean add(TradeSummaryInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(TradeSummaryInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(TradeSummaryInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public TradeSummaryInfoSchema get(int index) {
		TradeSummaryInfoSchema tSchema = (TradeSummaryInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, TradeSummaryInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(TradeSummaryInfoSet aSet) {
		return super.set(aSet);
	}
}
 