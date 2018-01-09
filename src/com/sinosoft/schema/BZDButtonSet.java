package com.sinosoft.schema;

import com.sinosoft.schema.BZDButtonSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class BZDButtonSet extends SchemaSet {
	public BZDButtonSet() {
		this(10,0);
	}

	public BZDButtonSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZDButtonSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZDButtonSchema._TableCode;
		Columns = BZDButtonSchema._Columns;
		NameSpace = BZDButtonSchema._NameSpace;
		InsertAllSQL = BZDButtonSchema._InsertAllSQL;
		UpdateAllSQL = BZDButtonSchema._UpdateAllSQL;
		FillAllSQL = BZDButtonSchema._FillAllSQL;
		DeleteSQL = BZDButtonSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZDButtonSet();
	}

	public boolean add(BZDButtonSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZDButtonSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZDButtonSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZDButtonSchema get(int index) {
		BZDButtonSchema tSchema = (BZDButtonSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZDButtonSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZDButtonSet aSet) {
		return super.set(aSet);
	}
}
 