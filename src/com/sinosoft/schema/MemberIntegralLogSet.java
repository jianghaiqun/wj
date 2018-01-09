package com.sinosoft.schema;

import com.sinosoft.schema.MemberIntegralLogSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class MemberIntegralLogSet extends SchemaSet {
	public MemberIntegralLogSet() {
		this(10,0);
	}

	public MemberIntegralLogSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public MemberIntegralLogSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = MemberIntegralLogSchema._TableCode;
		Columns = MemberIntegralLogSchema._Columns;
		NameSpace = MemberIntegralLogSchema._NameSpace;
		InsertAllSQL = MemberIntegralLogSchema._InsertAllSQL;
		UpdateAllSQL = MemberIntegralLogSchema._UpdateAllSQL;
		FillAllSQL = MemberIntegralLogSchema._FillAllSQL;
		DeleteSQL = MemberIntegralLogSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new MemberIntegralLogSet();
	}

	public boolean add(MemberIntegralLogSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(MemberIntegralLogSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(MemberIntegralLogSchema aSchema) {
		return super.remove(aSchema);
	}

	public MemberIntegralLogSchema get(int index) {
		MemberIntegralLogSchema tSchema = (MemberIntegralLogSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, MemberIntegralLogSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(MemberIntegralLogSet aSet) {
		return super.set(aSet);
	}
}
