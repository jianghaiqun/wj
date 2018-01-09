package com.sinosoft.schema;

import com.sinosoft.schema.agreementSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class agreementSet extends SchemaSet {
	public agreementSet() {
		this(10,0);
	}

	public agreementSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public agreementSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = agreementSchema._TableCode;
		Columns = agreementSchema._Columns;
		NameSpace = agreementSchema._NameSpace;
		InsertAllSQL = agreementSchema._InsertAllSQL;
		UpdateAllSQL = agreementSchema._UpdateAllSQL;
		FillAllSQL = agreementSchema._FillAllSQL;
		DeleteSQL = agreementSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new agreementSet();
	}

	public boolean add(agreementSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(agreementSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(agreementSchema aSchema) {
		return super.remove(aSchema);
	}

	public agreementSchema get(int index) {
		agreementSchema tSchema = (agreementSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, agreementSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(agreementSet aSet) {
		return super.set(aSet);
	}
}
 