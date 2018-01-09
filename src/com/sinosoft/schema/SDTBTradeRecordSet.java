package com.sinosoft.schema;

import com.sinosoft.schema.SDTBTradeRecordSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDTBTradeRecordSet extends SchemaSet {
	public SDTBTradeRecordSet() { 
		this(10,0);
	}

	public SDTBTradeRecordSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDTBTradeRecordSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDTBTradeRecordSchema._TableCode;
		Columns = SDTBTradeRecordSchema._Columns;
		NameSpace = SDTBTradeRecordSchema._NameSpace;
		InsertAllSQL = SDTBTradeRecordSchema._InsertAllSQL;
		UpdateAllSQL = SDTBTradeRecordSchema._UpdateAllSQL;
		FillAllSQL = SDTBTradeRecordSchema._FillAllSQL;
		DeleteSQL = SDTBTradeRecordSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDTBTradeRecordSet();
	}

	public boolean add(SDTBTradeRecordSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDTBTradeRecordSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDTBTradeRecordSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDTBTradeRecordSchema get(int index) {
		SDTBTradeRecordSchema tSchema = (SDTBTradeRecordSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDTBTradeRecordSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDTBTradeRecordSet aSet) {
		return super.set(aSet);
	}
}
 