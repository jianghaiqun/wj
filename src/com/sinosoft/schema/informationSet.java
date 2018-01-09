package com.sinosoft.schema;

import com.sinosoft.schema.informationSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class informationSet extends SchemaSet {
	public informationSet() {
		this(10,0);
	}

	public informationSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public informationSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = informationSchema._TableCode;
		Columns = informationSchema._Columns;
		NameSpace = informationSchema._NameSpace;
		InsertAllSQL = informationSchema._InsertAllSQL;
		UpdateAllSQL = informationSchema._UpdateAllSQL;
		FillAllSQL = informationSchema._FillAllSQL;
		DeleteSQL = informationSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new informationSet();
	}

	public boolean add(informationSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(informationSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(informationSchema aSchema) {
		return super.remove(aSchema);
	}

	public informationSchema get(int index) {
		informationSchema tSchema = (informationSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, informationSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(informationSet aSet) {
		return super.set(aSet);
	}
}
 