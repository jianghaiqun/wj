package com.sinosoft.schema;

import com.sinosoft.schema.LRTemplateSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class LRTemplateSet extends SchemaSet {
	public LRTemplateSet() {
		this(10,0);
	}

	public LRTemplateSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public LRTemplateSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = LRTemplateSchema._TableCode;
		Columns = LRTemplateSchema._Columns;
		NameSpace = LRTemplateSchema._NameSpace;
		InsertAllSQL = LRTemplateSchema._InsertAllSQL;
		UpdateAllSQL = LRTemplateSchema._UpdateAllSQL;
		FillAllSQL = LRTemplateSchema._FillAllSQL;
		DeleteSQL = LRTemplateSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new LRTemplateSet();
	}

	public boolean add(LRTemplateSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(LRTemplateSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(LRTemplateSchema aSchema) {
		return super.remove(aSchema);
	}

	public LRTemplateSchema get(int index) {
		LRTemplateSchema tSchema = (LRTemplateSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, LRTemplateSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(LRTemplateSet aSet) {
		return super.set(aSet);
	}
}
 