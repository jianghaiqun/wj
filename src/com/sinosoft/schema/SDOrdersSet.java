package com.sinosoft.schema;

import com.sinosoft.schema.SDOrdersSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDOrdersSet extends SchemaSet { 
	public SDOrdersSet() {   
		this(10,0);
	}

	public SDOrdersSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDOrdersSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDOrdersSchema._TableCode;
		Columns = SDOrdersSchema._Columns;
		NameSpace = SDOrdersSchema._NameSpace;
		InsertAllSQL = SDOrdersSchema._InsertAllSQL;
		UpdateAllSQL = SDOrdersSchema._UpdateAllSQL;
		FillAllSQL = SDOrdersSchema._FillAllSQL;
		DeleteSQL = SDOrdersSchema._DeleteSQL;
	}
	
	protected SchemaSet newInstance(){
		return new SDOrdersSet();
	}

	public boolean add(SDOrdersSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDOrdersSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDOrdersSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDOrdersSchema get(int index) {
		SDOrdersSchema tSchema = (SDOrdersSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDOrdersSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDOrdersSet aSet) {
		return super.set(aSet);
	}
	
}
 