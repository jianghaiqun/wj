package com.sinosoft.schema;

import com.sinosoft.schema.OtTradeInformationSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class OtTradeInformationSet extends SchemaSet {
	public OtTradeInformationSet() {
		this(10,0);
	}

	public OtTradeInformationSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public OtTradeInformationSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = OtTradeInformationSchema._TableCode;
		Columns = OtTradeInformationSchema._Columns;
		NameSpace = OtTradeInformationSchema._NameSpace;
		InsertAllSQL = OtTradeInformationSchema._InsertAllSQL;
		UpdateAllSQL = OtTradeInformationSchema._UpdateAllSQL;
		FillAllSQL = OtTradeInformationSchema._FillAllSQL;
		DeleteSQL = OtTradeInformationSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new OtTradeInformationSet();
	}

	public boolean add(OtTradeInformationSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(OtTradeInformationSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(OtTradeInformationSchema aSchema) {
		return super.remove(aSchema);
	}

	public OtTradeInformationSchema get(int index) {
		OtTradeInformationSchema tSchema = (OtTradeInformationSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, OtTradeInformationSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(OtTradeInformationSet aSet) {
		return super.set(aSet);
	}
}
 