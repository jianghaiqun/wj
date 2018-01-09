package com.sinosoft.schema;

import com.sinosoft.schema.MemberPrivilegesSchema;
import com.sinosoft.framework.orm.SchemaSet;

@SuppressWarnings("serial")
public class MemberPrivilegesSet extends SchemaSet {
	public MemberPrivilegesSet() {
		this(10,0);
	}

	public MemberPrivilegesSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public MemberPrivilegesSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = MemberPrivilegesSchema._TableCode;
		Columns = MemberPrivilegesSchema._Columns;
		NameSpace = MemberPrivilegesSchema._NameSpace;
		InsertAllSQL = MemberPrivilegesSchema._InsertAllSQL;
		UpdateAllSQL = MemberPrivilegesSchema._UpdateAllSQL;
		FillAllSQL = MemberPrivilegesSchema._FillAllSQL;
		DeleteSQL = MemberPrivilegesSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new MemberPrivilegesSet();
	}

	public boolean add(MemberPrivilegesSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(MemberPrivilegesSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(MemberPrivilegesSchema aSchema) {
		return super.remove(aSchema);
	}

	public MemberPrivilegesSchema get(int index) {
		MemberPrivilegesSchema tSchema = (MemberPrivilegesSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, MemberPrivilegesSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(MemberPrivilegesSet aSet) {
		return super.set(aSet);
	}
}
 