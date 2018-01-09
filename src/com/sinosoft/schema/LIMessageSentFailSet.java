package com.sinosoft.schema;

import com.sinosoft.schema.LIMessageSentFailSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class LIMessageSentFailSet extends SchemaSet {
	public LIMessageSentFailSet() {
		this(10,0);
	}

	public LIMessageSentFailSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public LIMessageSentFailSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = LIMessageSentFailSchema._TableCode;
		Columns = LIMessageSentFailSchema._Columns;
		NameSpace = LIMessageSentFailSchema._NameSpace;
		InsertAllSQL = LIMessageSentFailSchema._InsertAllSQL;
		UpdateAllSQL = LIMessageSentFailSchema._UpdateAllSQL;
		FillAllSQL = LIMessageSentFailSchema._FillAllSQL;
		DeleteSQL = LIMessageSentFailSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new LIMessageSentFailSet();
	}

	public boolean add(LIMessageSentFailSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(LIMessageSentFailSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(LIMessageSentFailSchema aSchema) {
		return super.remove(aSchema);
	}

	public LIMessageSentFailSchema get(int index) {
		LIMessageSentFailSchema tSchema = (LIMessageSentFailSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, LIMessageSentFailSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(LIMessageSentFailSet aSet) {
		return super.set(aSet);
	}
}
 