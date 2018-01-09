package com.sinosoft.schema;

import com.sinosoft.schema.SDParseMailLogSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDParseMailLogSet extends SchemaSet {
	public SDParseMailLogSet() {
		this(10,0);
	}

	public SDParseMailLogSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDParseMailLogSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDParseMailLogSchema._TableCode;
		Columns = SDParseMailLogSchema._Columns;
		NameSpace = SDParseMailLogSchema._NameSpace;
		InsertAllSQL = SDParseMailLogSchema._InsertAllSQL;
		UpdateAllSQL = SDParseMailLogSchema._UpdateAllSQL;
		FillAllSQL = SDParseMailLogSchema._FillAllSQL;
		DeleteSQL = SDParseMailLogSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDParseMailLogSet();
	}

	public boolean add(SDParseMailLogSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDParseMailLogSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDParseMailLogSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDParseMailLogSchema get(int index) {
		SDParseMailLogSchema tSchema = (SDParseMailLogSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDParseMailLogSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDParseMailLogSet aSet) {
		return super.set(aSet);
	}
}
 