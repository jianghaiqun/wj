package com.sinosoft.schema;

import com.sinosoft.schema.LRTemplateBSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class LRTemplateBSet extends SchemaSet {
	public LRTemplateBSet() {
		this(10,0);
	}

	public LRTemplateBSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public LRTemplateBSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = LRTemplateBSchema._TableCode;
		Columns = LRTemplateBSchema._Columns;
		NameSpace = LRTemplateBSchema._NameSpace;
		InsertAllSQL = LRTemplateBSchema._InsertAllSQL;
		UpdateAllSQL = LRTemplateBSchema._UpdateAllSQL;
		FillAllSQL = LRTemplateBSchema._FillAllSQL;
		DeleteSQL = LRTemplateBSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new LRTemplateBSet();
	}

	public boolean add(LRTemplateBSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(LRTemplateBSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(LRTemplateBSchema aSchema) {
		return super.remove(aSchema);
	}

	public LRTemplateBSchema get(int index) {
		LRTemplateBSchema tSchema = (LRTemplateBSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, LRTemplateBSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(LRTemplateBSet aSet) {
		return super.set(aSet);
	}
}
 