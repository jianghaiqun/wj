package com.sinosoft.schema;

import com.sinosoft.schema.VIPLogSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class VIPLogSet extends SchemaSet {
	public VIPLogSet() {
		this(10,0);
	}

	public VIPLogSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public VIPLogSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = VIPLogSchema._TableCode;
		Columns = VIPLogSchema._Columns;
		NameSpace = VIPLogSchema._NameSpace;
		InsertAllSQL = VIPLogSchema._InsertAllSQL;
		UpdateAllSQL = VIPLogSchema._UpdateAllSQL;
		FillAllSQL = VIPLogSchema._FillAllSQL;
		DeleteSQL = VIPLogSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new VIPLogSet();
	}

	public boolean add(VIPLogSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(VIPLogSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(VIPLogSchema aSchema) {
		return super.remove(aSchema);
	}

	public VIPLogSchema get(int index) {
		VIPLogSchema tSchema = (VIPLogSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, VIPLogSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(VIPLogSet aSet) {
		return super.set(aSet);
	}
}
 