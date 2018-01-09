package com.sinosoft.schema;

import com.sinosoft.schema.jdproductcSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class jdproductcSet extends SchemaSet {
	public jdproductcSet() { 
		this(10,0);
	}

	public jdproductcSet(int initialCapacity) { 
		this(initialCapacity,0);
	}

	public jdproductcSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = jdproductcSchema._TableCode;
		Columns = jdproductcSchema._Columns;
		NameSpace = jdproductcSchema._NameSpace;
		InsertAllSQL = jdproductcSchema._InsertAllSQL;
		UpdateAllSQL = jdproductcSchema._UpdateAllSQL;
		FillAllSQL = jdproductcSchema._FillAllSQL;
		DeleteSQL = jdproductcSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new jdproductcSet();
	}

	public boolean add(jdproductcSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(jdproductcSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(jdproductcSchema aSchema) {
		return super.remove(aSchema);
	}

	public jdproductcSchema get(int index) {
		jdproductcSchema tSchema = (jdproductcSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, jdproductcSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(jdproductcSet aSet) {
		return super.set(aSet);
	}
}
 