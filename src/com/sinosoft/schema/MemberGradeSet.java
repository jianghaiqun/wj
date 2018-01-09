package com.sinosoft.schema;

import com.sinosoft.schema.MemberGradeSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class MemberGradeSet extends SchemaSet {
	public MemberGradeSet() {
		this(10,0);
	}

	public MemberGradeSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public MemberGradeSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = MemberGradeSchema._TableCode;
		Columns = MemberGradeSchema._Columns;
		NameSpace = MemberGradeSchema._NameSpace;
		InsertAllSQL = MemberGradeSchema._InsertAllSQL;
		UpdateAllSQL = MemberGradeSchema._UpdateAllSQL;
		FillAllSQL = MemberGradeSchema._FillAllSQL;
		DeleteSQL = MemberGradeSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new MemberGradeSet();
	}

	public boolean add(MemberGradeSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(MemberGradeSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(MemberGradeSchema aSchema) {
		return super.remove(aSchema);
	}

	public MemberGradeSchema get(int index) {
		MemberGradeSchema tSchema = (MemberGradeSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, MemberGradeSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(MemberGradeSet aSet) {
		return super.set(aSet);
	}
}
 