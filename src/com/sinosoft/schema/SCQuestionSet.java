package com.sinosoft.schema;

import com.sinosoft.schema.SCQuestionSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SCQuestionSet extends SchemaSet {
	public SCQuestionSet() {
		this(10,0);
	}

	public SCQuestionSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SCQuestionSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SCQuestionSchema._TableCode;
		Columns = SCQuestionSchema._Columns;
		NameSpace = SCQuestionSchema._NameSpace;
		InsertAllSQL = SCQuestionSchema._InsertAllSQL;
		UpdateAllSQL = SCQuestionSchema._UpdateAllSQL;
		FillAllSQL = SCQuestionSchema._FillAllSQL;
		DeleteSQL = SCQuestionSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SCQuestionSet();
	}

	public boolean add(SCQuestionSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SCQuestionSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SCQuestionSchema aSchema) {
		return super.remove(aSchema);
	}

	public SCQuestionSchema get(int index) {
		SCQuestionSchema tSchema = (SCQuestionSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SCQuestionSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SCQuestionSet aSet) {
		return super.set(aSet);
	}
	
}
 