package com.sinosoft.schema;

import com.sinosoft.schema.LIMessageSentSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class LIMessageSentSet extends SchemaSet {
	public LIMessageSentSet() {
		this(10,0);
	}

	public LIMessageSentSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public LIMessageSentSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = LIMessageSentSchema._TableCode;
		Columns = LIMessageSentSchema._Columns;
		NameSpace = LIMessageSentSchema._NameSpace;
		InsertAllSQL = LIMessageSentSchema._InsertAllSQL;
		UpdateAllSQL = LIMessageSentSchema._UpdateAllSQL;
		FillAllSQL = LIMessageSentSchema._FillAllSQL;
		DeleteSQL = LIMessageSentSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new LIMessageSentSet();
	}

	public boolean add(LIMessageSentSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(LIMessageSentSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(LIMessageSentSchema aSchema) {
		return super.remove(aSchema);
	}

	public LIMessageSentSchema get(int index) {
		LIMessageSentSchema tSchema = (LIMessageSentSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, LIMessageSentSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(LIMessageSentSet aSet) {
		return super.set(aSet);
	}
}
 