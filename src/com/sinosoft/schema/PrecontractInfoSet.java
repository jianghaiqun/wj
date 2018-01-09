package com.sinosoft.schema;

import com.sinosoft.schema.PrecontractInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class PrecontractInfoSet extends SchemaSet {
	public PrecontractInfoSet() {
		this(10,0);
	}

	public PrecontractInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public PrecontractInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = PrecontractInfoSchema._TableCode;
		Columns = PrecontractInfoSchema._Columns;
		NameSpace = PrecontractInfoSchema._NameSpace;
		InsertAllSQL = PrecontractInfoSchema._InsertAllSQL;
		UpdateAllSQL = PrecontractInfoSchema._UpdateAllSQL;
		FillAllSQL = PrecontractInfoSchema._FillAllSQL;
		DeleteSQL = PrecontractInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new PrecontractInfoSet();
	}

	public boolean add(PrecontractInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(PrecontractInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(PrecontractInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public PrecontractInfoSchema get(int index) {
		PrecontractInfoSchema tSchema = (PrecontractInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, PrecontractInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(PrecontractInfoSet aSet) {
		return super.set(aSet);
	}
}
 