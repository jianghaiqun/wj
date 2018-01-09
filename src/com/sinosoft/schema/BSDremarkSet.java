package com.sinosoft.schema;

import com.sinosoft.schema.BSDremarkSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class BSDremarkSet extends SchemaSet {
	public BSDremarkSet() {
		this(10,0);
	}

	public BSDremarkSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BSDremarkSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BSDremarkSchema._TableCode;
		Columns = BSDremarkSchema._Columns;
		NameSpace = BSDremarkSchema._NameSpace;
		InsertAllSQL = BSDremarkSchema._InsertAllSQL;
		UpdateAllSQL = BSDremarkSchema._UpdateAllSQL;
		FillAllSQL = BSDremarkSchema._FillAllSQL;
		DeleteSQL = BSDremarkSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BSDremarkSet();
	}

	public boolean add(BSDremarkSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BSDremarkSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BSDremarkSchema aSchema) {
		return super.remove(aSchema);
	}

	public BSDremarkSchema get(int index) {
		BSDremarkSchema tSchema = (BSDremarkSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BSDremarkSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BSDremarkSet aSet) {
		return super.set(aSet);
	}
}
 