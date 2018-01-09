package com.sinosoft.schema;

import com.sinosoft.schema.SCCatalogTypeSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SCCatalogTypeSet extends SchemaSet {
	public SCCatalogTypeSet() {
		this(10,0);
	}

	public SCCatalogTypeSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SCCatalogTypeSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SCCatalogTypeSchema._TableCode;
		Columns = SCCatalogTypeSchema._Columns;
		NameSpace = SCCatalogTypeSchema._NameSpace;
		InsertAllSQL = SCCatalogTypeSchema._InsertAllSQL;
		UpdateAllSQL = SCCatalogTypeSchema._UpdateAllSQL;
		FillAllSQL = SCCatalogTypeSchema._FillAllSQL;
		DeleteSQL = SCCatalogTypeSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SCCatalogTypeSet();
	}

	public boolean add(SCCatalogTypeSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SCCatalogTypeSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SCCatalogTypeSchema aSchema) {
		return super.remove(aSchema);
	}

	public SCCatalogTypeSchema get(int index) {
		SCCatalogTypeSchema tSchema = (SCCatalogTypeSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SCCatalogTypeSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SCCatalogTypeSet aSet) {
		return super.set(aSet);
	}
}
 