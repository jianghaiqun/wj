package com.sinosoft.schema;

import com.sinosoft.schema.SDDREmailExtractRecodeSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDDREmailExtractRecodeSet extends SchemaSet {
	public SDDREmailExtractRecodeSet() {
		this(10,0);
	}

	public SDDREmailExtractRecodeSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDDREmailExtractRecodeSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDDREmailExtractRecodeSchema._TableCode;
		Columns = SDDREmailExtractRecodeSchema._Columns;
		NameSpace = SDDREmailExtractRecodeSchema._NameSpace;
		InsertAllSQL = SDDREmailExtractRecodeSchema._InsertAllSQL;
		UpdateAllSQL = SDDREmailExtractRecodeSchema._UpdateAllSQL;
		FillAllSQL = SDDREmailExtractRecodeSchema._FillAllSQL;
		DeleteSQL = SDDREmailExtractRecodeSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDDREmailExtractRecodeSet();
	}

	public boolean add(SDDREmailExtractRecodeSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDDREmailExtractRecodeSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDDREmailExtractRecodeSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDDREmailExtractRecodeSchema get(int index) {
		SDDREmailExtractRecodeSchema tSchema = (SDDREmailExtractRecodeSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDDREmailExtractRecodeSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDDREmailExtractRecodeSet aSet) {
		return super.set(aSet);
	}
}
 