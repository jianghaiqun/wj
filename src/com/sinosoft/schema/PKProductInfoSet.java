package com.sinosoft.schema;

import com.sinosoft.schema.PKProductInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class PKProductInfoSet extends SchemaSet {
	public PKProductInfoSet() {
		this(10,0);
	}

	public PKProductInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public PKProductInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = PKProductInfoSchema._TableCode;
		Columns = PKProductInfoSchema._Columns;
		NameSpace = PKProductInfoSchema._NameSpace;
		InsertAllSQL = PKProductInfoSchema._InsertAllSQL;
		UpdateAllSQL = PKProductInfoSchema._UpdateAllSQL;
		FillAllSQL = PKProductInfoSchema._FillAllSQL;
		DeleteSQL = PKProductInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new PKProductInfoSet();
	}

	public boolean add(PKProductInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(PKProductInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(PKProductInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public PKProductInfoSchema get(int index) {
		PKProductInfoSchema tSchema = (PKProductInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, PKProductInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(PKProductInfoSet aSet) {
		return super.set(aSet);
	}
}
 