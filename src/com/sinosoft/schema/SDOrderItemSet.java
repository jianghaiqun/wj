package com.sinosoft.schema;

import com.sinosoft.schema.SDOrderItemSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDOrderItemSet extends SchemaSet {
	public SDOrderItemSet() {
		this(10,0);
	}
	public SDOrderItemSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDOrderItemSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDOrderItemSchema._TableCode;
		Columns = SDOrderItemSchema._Columns;
		NameSpace = SDOrderItemSchema._NameSpace;
		InsertAllSQL = SDOrderItemSchema._InsertAllSQL;
		UpdateAllSQL = SDOrderItemSchema._UpdateAllSQL;
		FillAllSQL = SDOrderItemSchema._FillAllSQL;
		DeleteSQL = SDOrderItemSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDOrderItemSet();
	}

	public boolean add(SDOrderItemSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDOrderItemSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDOrderItemSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDOrderItemSchema get(int index) {
		SDOrderItemSchema tSchema = (SDOrderItemSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDOrderItemSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDOrderItemSet aSet) {
		return super.set(aSet);
	}
}
 