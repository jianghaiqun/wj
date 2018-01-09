package com.sinosoft.schema;

import com.sinosoft.schema.LRTemplateTSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class LRTemplateTSet extends SchemaSet {
	public LRTemplateTSet() {
		this(10,0);
	}

	public LRTemplateTSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public LRTemplateTSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = LRTemplateTSchema._TableCode;
		Columns = LRTemplateTSchema._Columns;
		NameSpace = LRTemplateTSchema._NameSpace;
		InsertAllSQL = LRTemplateTSchema._InsertAllSQL;
		UpdateAllSQL = LRTemplateTSchema._UpdateAllSQL;
		FillAllSQL = LRTemplateTSchema._FillAllSQL;
		DeleteSQL = LRTemplateTSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new LRTemplateTSet();
	}

	public boolean add(LRTemplateTSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(LRTemplateTSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(LRTemplateTSchema aSchema) {
		return super.remove(aSchema);
	}

	public LRTemplateTSchema get(int index) {
		LRTemplateTSchema tSchema = (LRTemplateTSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, LRTemplateTSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(LRTemplateTSet aSet) {
		return super.set(aSet);
	}
}
 