package com.sinosoft.schema;

import com.sinosoft.schema.InitiateRefundSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class InitiateRefundSet extends SchemaSet {
	public InitiateRefundSet() {
		this(10,0);
	}

	public InitiateRefundSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public InitiateRefundSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = InitiateRefundSchema._TableCode;
		Columns = InitiateRefundSchema._Columns;
		NameSpace = InitiateRefundSchema._NameSpace;
		InsertAllSQL = InitiateRefundSchema._InsertAllSQL;
		UpdateAllSQL = InitiateRefundSchema._UpdateAllSQL;
		FillAllSQL = InitiateRefundSchema._FillAllSQL;
		DeleteSQL = InitiateRefundSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new InitiateRefundSet();
	}

	public boolean add(InitiateRefundSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(InitiateRefundSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(InitiateRefundSchema aSchema) {
		return super.remove(aSchema);
	}

	public InitiateRefundSchema get(int index) {
		InitiateRefundSchema tSchema = (InitiateRefundSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, InitiateRefundSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(InitiateRefundSet aSet) {
		return super.set(aSet);
	}
}
 