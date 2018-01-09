package com.sinosoft.schema;

import com.sinosoft.schema.UserOperLogSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class UserOperLogSet extends SchemaSet {
	public UserOperLogSet() {
		this(10,0);
	}

	public UserOperLogSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public UserOperLogSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = UserOperLogSchema._TableCode;
		Columns = UserOperLogSchema._Columns;
		NameSpace = UserOperLogSchema._NameSpace;
		InsertAllSQL = UserOperLogSchema._InsertAllSQL;
		UpdateAllSQL = UserOperLogSchema._UpdateAllSQL;
		FillAllSQL = UserOperLogSchema._FillAllSQL;
		DeleteSQL = UserOperLogSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new UserOperLogSet();
	}

	public boolean add(UserOperLogSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(UserOperLogSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(UserOperLogSchema aSchema) {
		return super.remove(aSchema);
	}

	public UserOperLogSchema get(int index) {
		UserOperLogSchema tSchema = (UserOperLogSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, UserOperLogSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(UserOperLogSet aSet) {
		return super.set(aSet);
	}
}
 