package com.sinosoft.schema;

import com.sinosoft.schema.authorityinfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class authorityinfoSet extends SchemaSet {
	public authorityinfoSet() {
		this(10,0);
	}

	public authorityinfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public authorityinfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = authorityinfoSchema._TableCode;
		Columns = authorityinfoSchema._Columns;
		NameSpace = authorityinfoSchema._NameSpace;
		InsertAllSQL = authorityinfoSchema._InsertAllSQL;
		UpdateAllSQL = authorityinfoSchema._UpdateAllSQL;
		FillAllSQL = authorityinfoSchema._FillAllSQL;
		DeleteSQL = authorityinfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new authorityinfoSet();
	}

	public boolean add(authorityinfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(authorityinfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(authorityinfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public authorityinfoSchema get(int index) {
		authorityinfoSchema tSchema = (authorityinfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, authorityinfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(authorityinfoSet aSet) {
		return super.set(aSet);
	}
}
 