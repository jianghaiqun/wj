package com.sinosoft.schema;

import com.sinosoft.schema.SDInformationBnfSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDInformationBnfSet extends SchemaSet {
	public SDInformationBnfSet() {
		this(10,0);
	}

	public SDInformationBnfSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDInformationBnfSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDInformationBnfSchema._TableCode;
		Columns = SDInformationBnfSchema._Columns;
		NameSpace = SDInformationBnfSchema._NameSpace;
		InsertAllSQL = SDInformationBnfSchema._InsertAllSQL;
		UpdateAllSQL = SDInformationBnfSchema._UpdateAllSQL;
		FillAllSQL = SDInformationBnfSchema._FillAllSQL;
		DeleteSQL = SDInformationBnfSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDInformationBnfSet();
	}

	public boolean add(SDInformationBnfSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDInformationBnfSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDInformationBnfSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDInformationBnfSchema get(int index) {
		SDInformationBnfSchema tSchema = (SDInformationBnfSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDInformationBnfSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDInformationBnfSet aSet) {
		return super.set(aSet);
	}
}
