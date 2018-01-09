package com.sinosoft.schema;

import com.sinosoft.schema.SDInsuredHealthSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDInsuredHealthSet extends SchemaSet {
	public SDInsuredHealthSet() {
		this(10,0);
	}

	public SDInsuredHealthSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDInsuredHealthSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDInsuredHealthSchema._TableCode;
		Columns = SDInsuredHealthSchema._Columns;
		NameSpace = SDInsuredHealthSchema._NameSpace;
		InsertAllSQL = SDInsuredHealthSchema._InsertAllSQL;
		UpdateAllSQL = SDInsuredHealthSchema._UpdateAllSQL;
		FillAllSQL = SDInsuredHealthSchema._FillAllSQL;
		DeleteSQL = SDInsuredHealthSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDInsuredHealthSet();
	}

	public boolean add(SDInsuredHealthSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDInsuredHealthSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDInsuredHealthSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDInsuredHealthSchema get(int index) {
		SDInsuredHealthSchema tSchema = (SDInsuredHealthSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDInsuredHealthSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDInsuredHealthSet aSet) {
		return super.set(aSet);
	}
}
 