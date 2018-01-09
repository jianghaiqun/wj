package com.sinosoft.schema;

import com.sinosoft.schema.SDDREmailExtractSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDDREmailExtractSet extends SchemaSet {
	public SDDREmailExtractSet() {
		this(10,0);
	}

	public SDDREmailExtractSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDDREmailExtractSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDDREmailExtractSchema._TableCode;
		Columns = SDDREmailExtractSchema._Columns;
		NameSpace = SDDREmailExtractSchema._NameSpace;
		InsertAllSQL = SDDREmailExtractSchema._InsertAllSQL;
		UpdateAllSQL = SDDREmailExtractSchema._UpdateAllSQL;
		FillAllSQL = SDDREmailExtractSchema._FillAllSQL;
		DeleteSQL = SDDREmailExtractSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDDREmailExtractSet();
	}

	public boolean add(SDDREmailExtractSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDDREmailExtractSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDDREmailExtractSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDDREmailExtractSchema get(int index) {
		SDDREmailExtractSchema tSchema = (SDDREmailExtractSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDDREmailExtractSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDDREmailExtractSet aSet) {
		return super.set(aSet);
	}
}
 