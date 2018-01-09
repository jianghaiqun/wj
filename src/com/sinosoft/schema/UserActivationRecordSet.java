package com.sinosoft.schema;

import com.sinosoft.schema.UserActivationRecordSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class UserActivationRecordSet extends SchemaSet {
	public UserActivationRecordSet() {
		this(10,0);
	}

	public UserActivationRecordSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public UserActivationRecordSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = UserActivationRecordSchema._TableCode;
		Columns = UserActivationRecordSchema._Columns;
		NameSpace = UserActivationRecordSchema._NameSpace;
		InsertAllSQL = UserActivationRecordSchema._InsertAllSQL;
		UpdateAllSQL = UserActivationRecordSchema._UpdateAllSQL;
		FillAllSQL = UserActivationRecordSchema._FillAllSQL;
		DeleteSQL = UserActivationRecordSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new UserActivationRecordSet();
	}

	public boolean add(UserActivationRecordSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(UserActivationRecordSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(UserActivationRecordSchema aSchema) {
		return super.remove(aSchema);
	}

	public UserActivationRecordSchema get(int index) {
		UserActivationRecordSchema tSchema = (UserActivationRecordSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, UserActivationRecordSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(UserActivationRecordSet aSet) {
		return super.set(aSet);
	}
}
 