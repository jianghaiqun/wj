package com.sinosoft.schema;

import com.sinosoft.schema.SDInterActionSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDInterActionSet extends SchemaSet {
	public SDInterActionSet() {
		this(10,0);
	}

	public SDInterActionSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDInterActionSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDInterActionSchema._TableCode;
		Columns = SDInterActionSchema._Columns;
		NameSpace = SDInterActionSchema._NameSpace;
		InsertAllSQL = SDInterActionSchema._InsertAllSQL;
		UpdateAllSQL = SDInterActionSchema._UpdateAllSQL;
		FillAllSQL = SDInterActionSchema._FillAllSQL;
		DeleteSQL = SDInterActionSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDInterActionSet();
	}

	public boolean add(SDInterActionSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDInterActionSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDInterActionSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDInterActionSchema get(int index) {
		SDInterActionSchema tSchema = (SDInterActionSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDInterActionSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDInterActionSet aSet) {
		return super.set(aSet);
	}
}
 