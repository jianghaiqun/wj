package com.sinosoft.schema;

import com.sinosoft.schema.SCAnswerSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SCAnswerSet extends SchemaSet {
	public SCAnswerSet() {
		this(10,0);
	}

	public SCAnswerSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SCAnswerSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SCAnswerSchema._TableCode;
		Columns = SCAnswerSchema._Columns;
		NameSpace = SCAnswerSchema._NameSpace;
		InsertAllSQL = SCAnswerSchema._InsertAllSQL;
		UpdateAllSQL = SCAnswerSchema._UpdateAllSQL;
		FillAllSQL = SCAnswerSchema._FillAllSQL;
		DeleteSQL = SCAnswerSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SCAnswerSet();
	}

	public boolean add(SCAnswerSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SCAnswerSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SCAnswerSchema aSchema) {
		return super.remove(aSchema);
	}

	public SCAnswerSchema get(int index) {
		SCAnswerSchema tSchema = (SCAnswerSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SCAnswerSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SCAnswerSet aSet) {
		return super.set(aSet);
	}
}
 