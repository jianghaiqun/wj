package com.sinosoft.schema;

import com.sinosoft.schema.SDAllCondtionSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDAllCondtionSet extends SchemaSet {
	public SDAllCondtionSet() {
		this(10,0);
	}

	public SDAllCondtionSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDAllCondtionSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDAllCondtionSchema._TableCode;
		Columns = SDAllCondtionSchema._Columns;
		NameSpace = SDAllCondtionSchema._NameSpace;
		InsertAllSQL = SDAllCondtionSchema._InsertAllSQL;
		UpdateAllSQL = SDAllCondtionSchema._UpdateAllSQL;
		FillAllSQL = SDAllCondtionSchema._FillAllSQL;
		DeleteSQL = SDAllCondtionSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDAllCondtionSet();
	}

	public boolean add(SDAllCondtionSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDAllCondtionSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDAllCondtionSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDAllCondtionSchema get(int index) {
		SDAllCondtionSchema tSchema = (SDAllCondtionSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDAllCondtionSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDAllCondtionSet aSet) {
		return super.set(aSet);
	}
}
 