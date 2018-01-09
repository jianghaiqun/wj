package com.sinosoft.schema;

import com.sinosoft.schema.SDInterIntegralSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDInterIntegralSet extends SchemaSet {
	public SDInterIntegralSet() {
		this(10,0);
	}

	public SDInterIntegralSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDInterIntegralSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDInterIntegralSchema._TableCode;
		Columns = SDInterIntegralSchema._Columns;
		NameSpace = SDInterIntegralSchema._NameSpace;
		InsertAllSQL = SDInterIntegralSchema._InsertAllSQL;
		UpdateAllSQL = SDInterIntegralSchema._UpdateAllSQL;
		FillAllSQL = SDInterIntegralSchema._FillAllSQL;
		DeleteSQL = SDInterIntegralSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDInterIntegralSet();
	}

	public boolean add(SDInterIntegralSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDInterIntegralSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDInterIntegralSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDInterIntegralSchema get(int index) {
		SDInterIntegralSchema tSchema = (SDInterIntegralSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDInterIntegralSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDInterIntegralSet aSet) {
		return super.set(aSet);
	}
}
 