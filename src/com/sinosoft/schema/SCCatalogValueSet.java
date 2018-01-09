package com.sinosoft.schema;

import com.sinosoft.schema.SCCatalogValueSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SCCatalogValueSet extends SchemaSet {
	public SCCatalogValueSet() {
		this(10,0);
	}

	public SCCatalogValueSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SCCatalogValueSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SCCatalogValueSchema._TableCode;
		Columns = SCCatalogValueSchema._Columns;
		NameSpace = SCCatalogValueSchema._NameSpace;
		InsertAllSQL = SCCatalogValueSchema._InsertAllSQL;
		UpdateAllSQL = SCCatalogValueSchema._UpdateAllSQL;
		FillAllSQL = SCCatalogValueSchema._FillAllSQL;
		DeleteSQL = SCCatalogValueSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SCCatalogValueSet();
	}

	public boolean add(SCCatalogValueSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SCCatalogValueSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SCCatalogValueSchema aSchema) {
		return super.remove(aSchema);
	}

	public SCCatalogValueSchema get(int index) {
		SCCatalogValueSchema tSchema = (SCCatalogValueSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SCCatalogValueSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SCCatalogValueSet aSet) {
		return super.set(aSet);
	}
}
 