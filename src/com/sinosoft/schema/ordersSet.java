package com.sinosoft.schema;

import com.sinosoft.schema.ordersSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ordersSet extends SchemaSet {
	public ordersSet() {
		this(10,0);
	}

	public ordersSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ordersSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ordersSchema._TableCode;
		Columns = ordersSchema._Columns;
		NameSpace = ordersSchema._NameSpace;
		InsertAllSQL = ordersSchema._InsertAllSQL;
		UpdateAllSQL = ordersSchema._UpdateAllSQL;
		FillAllSQL = ordersSchema._FillAllSQL;
		DeleteSQL = ordersSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ordersSet();
	}

	public boolean add(ordersSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ordersSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ordersSchema aSchema) {
		return super.remove(aSchema);
	}

	public ordersSchema get(int index) {
		ordersSchema tSchema = (ordersSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ordersSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ordersSet aSet) {
		return super.set(aSet);
	}
}
 