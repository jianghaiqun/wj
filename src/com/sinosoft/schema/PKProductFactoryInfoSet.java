package com.sinosoft.schema;

import com.sinosoft.schema.PKProductFactoryInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class PKProductFactoryInfoSet extends SchemaSet {
	public PKProductFactoryInfoSet() {
		this(10,0);
	}

	public PKProductFactoryInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public PKProductFactoryInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = PKProductFactoryInfoSchema._TableCode;
		Columns = PKProductFactoryInfoSchema._Columns;
		NameSpace = PKProductFactoryInfoSchema._NameSpace;
		InsertAllSQL = PKProductFactoryInfoSchema._InsertAllSQL;
		UpdateAllSQL = PKProductFactoryInfoSchema._UpdateAllSQL;
		FillAllSQL = PKProductFactoryInfoSchema._FillAllSQL;
		DeleteSQL = PKProductFactoryInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new PKProductFactoryInfoSet();
	}

	public boolean add(PKProductFactoryInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(PKProductFactoryInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(PKProductFactoryInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public PKProductFactoryInfoSchema get(int index) {
		PKProductFactoryInfoSchema tSchema = (PKProductFactoryInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, PKProductFactoryInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(PKProductFactoryInfoSet aSet) {
		return super.set(aSet);
	}
}
 