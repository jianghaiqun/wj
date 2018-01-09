package com.sinosoft.schema; 

import com.sinosoft.schema.memberSchema;  
import com.sinosoft.framework.orm.SchemaSet;

public class memberSet extends SchemaSet {
	public memberSet() {
		this(10,0);
	}

	public memberSet(int initialCapacity) {
		this(initialCapacity,0); 
	}

	public memberSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = memberSchema._TableCode;
		Columns = memberSchema._Columns; 
		NameSpace = memberSchema._NameSpace;
		InsertAllSQL = memberSchema._InsertAllSQL;
		UpdateAllSQL = memberSchema._UpdateAllSQL;
		FillAllSQL = memberSchema._FillAllSQL;
		DeleteSQL = memberSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new memberSet();
	}

	public boolean add(memberSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(memberSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(memberSchema aSchema) {
		return super.remove(aSchema);
	}

	public memberSchema get(int index) {
		memberSchema tSchema = (memberSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, memberSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(memberSet aSet) {
		return super.set(aSet);
	}
}
 