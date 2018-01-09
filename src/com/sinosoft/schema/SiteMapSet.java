package com.sinosoft.schema;

import com.sinosoft.schema.SiteMapSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SiteMapSet extends SchemaSet {
	public SiteMapSet() {
		this(10,0);
	}

	public SiteMapSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SiteMapSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SiteMapSchema._TableCode;
		Columns = SiteMapSchema._Columns;
		NameSpace = SiteMapSchema._NameSpace;
		InsertAllSQL = SiteMapSchema._InsertAllSQL;
		UpdateAllSQL = SiteMapSchema._UpdateAllSQL;
		FillAllSQL = SiteMapSchema._FillAllSQL;
		DeleteSQL = SiteMapSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SiteMapSet();
	}

	public boolean add(SiteMapSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SiteMapSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SiteMapSchema aSchema) {
		return super.remove(aSchema);
	}

	public SiteMapSchema get(int index) {
		SiteMapSchema tSchema = (SiteMapSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SiteMapSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SiteMapSet aSet) {
		return super.set(aSet);
	}
}
 