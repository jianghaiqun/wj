package com.sinosoft.schema;

import com.sinosoft.schema.LIMessageServiceSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class LIMessageServiceSet extends SchemaSet {
	public LIMessageServiceSet() {
		this(10,0);
	}

	public LIMessageServiceSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public LIMessageServiceSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = LIMessageServiceSchema._TableCode;
		Columns = LIMessageServiceSchema._Columns;
		NameSpace = LIMessageServiceSchema._NameSpace;
		InsertAllSQL = LIMessageServiceSchema._InsertAllSQL;
		UpdateAllSQL = LIMessageServiceSchema._UpdateAllSQL;
		FillAllSQL = LIMessageServiceSchema._FillAllSQL;
		DeleteSQL = LIMessageServiceSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new LIMessageServiceSet();
	}

	public boolean add(LIMessageServiceSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(LIMessageServiceSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(LIMessageServiceSchema aSchema) {
		return super.remove(aSchema);
	}

	public LIMessageServiceSchema get(int index) {
		LIMessageServiceSchema tSchema = (LIMessageServiceSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, LIMessageServiceSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(LIMessageServiceSet aSet) {
		return super.set(aSet);
	}
}
 