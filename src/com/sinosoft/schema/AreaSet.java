package com.sinosoft.schema;

import com.sinosoft.schema.AreaSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class AreaSet extends SchemaSet {
	public AreaSet() {
		this(10,0);
	}

	public AreaSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public AreaSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = AreaSchema._TableCode;
		Columns = AreaSchema._Columns;
		NameSpace = AreaSchema._NameSpace;
		InsertAllSQL = AreaSchema._InsertAllSQL;
		UpdateAllSQL = AreaSchema._UpdateAllSQL;
		FillAllSQL = AreaSchema._FillAllSQL;
		DeleteSQL = AreaSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new AreaSet();
	}

	public boolean add(AreaSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(AreaSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(AreaSchema aSchema) {
		return super.remove(aSchema);
	}

	public AreaSchema get(int index) {
		AreaSchema tSchema = (AreaSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, AreaSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(AreaSet aSet) {
		return super.set(aSet);
	}
}
 