package com.sinosoft.schema;

import com.sinosoft.schema.SDSearchAddressSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDSearchAddressSet extends SchemaSet {
	public SDSearchAddressSet() {
		this(10,0);
	}

	public SDSearchAddressSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDSearchAddressSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDSearchAddressSchema._TableCode;
		Columns = SDSearchAddressSchema._Columns;    
		NameSpace = SDSearchAddressSchema._NameSpace;
		InsertAllSQL = SDSearchAddressSchema._InsertAllSQL;
		UpdateAllSQL = SDSearchAddressSchema._UpdateAllSQL;
		FillAllSQL = SDSearchAddressSchema._FillAllSQL;
		DeleteSQL = SDSearchAddressSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDSearchAddressSet();
	}

	public boolean add(SDSearchAddressSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDSearchAddressSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDSearchAddressSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDSearchAddressSchema get(int index) {
		SDSearchAddressSchema tSchema = (SDSearchAddressSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDSearchAddressSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDSearchAddressSet aSet) {
		return super.set(aSet);
	}
}
 