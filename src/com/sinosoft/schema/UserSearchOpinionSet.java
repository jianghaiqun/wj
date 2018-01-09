package com.sinosoft.schema;

import com.sinosoft.schema.UserSearchOpinionSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class UserSearchOpinionSet extends SchemaSet {
	public UserSearchOpinionSet() {
		this(10,0);
	}

	public UserSearchOpinionSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public UserSearchOpinionSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = UserSearchOpinionSchema._TableCode;
		Columns = UserSearchOpinionSchema._Columns;
		NameSpace = UserSearchOpinionSchema._NameSpace;
		InsertAllSQL = UserSearchOpinionSchema._InsertAllSQL;
		UpdateAllSQL = UserSearchOpinionSchema._UpdateAllSQL;
		FillAllSQL = UserSearchOpinionSchema._FillAllSQL;
		DeleteSQL = UserSearchOpinionSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new UserSearchOpinionSet();
	}

	public boolean add(UserSearchOpinionSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(UserSearchOpinionSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(UserSearchOpinionSchema aSchema) {
		return super.remove(aSchema);
	}

	public UserSearchOpinionSchema get(int index) {
		UserSearchOpinionSchema tSchema = (UserSearchOpinionSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, UserSearchOpinionSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(UserSearchOpinionSet aSet) {
		return super.set(aSet);
	}
}
 