package com.sinosoft.schema;

import com.sinosoft.schema.SDCarTransitionSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDCarTransitionSet extends SchemaSet {
	public SDCarTransitionSet() {
		this(10,0);
	}

	public SDCarTransitionSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDCarTransitionSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDCarTransitionSchema._TableCode;
		Columns = SDCarTransitionSchema._Columns;
		NameSpace = SDCarTransitionSchema._NameSpace;
		InsertAllSQL = SDCarTransitionSchema._InsertAllSQL;
		UpdateAllSQL = SDCarTransitionSchema._UpdateAllSQL;
		FillAllSQL = SDCarTransitionSchema._FillAllSQL;
		DeleteSQL = SDCarTransitionSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDCarTransitionSet();
	}

	public boolean add(SDCarTransitionSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDCarTransitionSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDCarTransitionSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDCarTransitionSchema get(int index) {
		SDCarTransitionSchema tSchema = (SDCarTransitionSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDCarTransitionSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDCarTransitionSet aSet) {
		return super.set(aSet);
	}
}
 