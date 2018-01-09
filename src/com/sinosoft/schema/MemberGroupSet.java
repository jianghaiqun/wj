package com.sinosoft.schema;

import com.sinosoft.schema.MemberGroupSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class MemberGroupSet extends SchemaSet {
	public MemberGroupSet() {
		this(10,0);
	}

	public MemberGroupSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public MemberGroupSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = MemberGroupSchema._TableCode;
		Columns = MemberGroupSchema._Columns;
		NameSpace = MemberGroupSchema._NameSpace;
		InsertAllSQL = MemberGroupSchema._InsertAllSQL;
		UpdateAllSQL = MemberGroupSchema._UpdateAllSQL;
		FillAllSQL = MemberGroupSchema._FillAllSQL;
		DeleteSQL = MemberGroupSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new MemberGroupSet();
	}

	public boolean add(MemberGroupSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(MemberGroupSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(MemberGroupSchema aSchema) {
		return super.remove(aSchema);
	}

	public MemberGroupSchema get(int index) {
		MemberGroupSchema tSchema = (MemberGroupSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, MemberGroupSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(MemberGroupSet aSet) {
		return super.set(aSet);
	}
}
 