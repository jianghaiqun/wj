package com.sinosoft.schema;

import com.sinosoft.schema.SdProductActivityLinkSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SdProductActivityLinkSet extends SchemaSet {
	public SdProductActivityLinkSet() {
		this(10,0);
	}

	public SdProductActivityLinkSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SdProductActivityLinkSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SdProductActivityLinkSchema._TableCode;
		Columns = SdProductActivityLinkSchema._Columns;
		NameSpace = SdProductActivityLinkSchema._NameSpace;
		InsertAllSQL = SdProductActivityLinkSchema._InsertAllSQL;
		UpdateAllSQL = SdProductActivityLinkSchema._UpdateAllSQL;
		FillAllSQL = SdProductActivityLinkSchema._FillAllSQL;
		DeleteSQL = SdProductActivityLinkSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SdProductActivityLinkSet();
	}

	public boolean add(SdProductActivityLinkSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SdProductActivityLinkSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SdProductActivityLinkSchema aSchema) {
		return super.remove(aSchema);
	}

	public SdProductActivityLinkSchema get(int index) {
		SdProductActivityLinkSchema tSchema = (SdProductActivityLinkSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SdProductActivityLinkSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SdProductActivityLinkSet aSet) {
		return super.set(aSet);
	}
}
 