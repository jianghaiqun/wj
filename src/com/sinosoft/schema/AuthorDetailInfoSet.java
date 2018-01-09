package com.sinosoft.schema;

import com.sinosoft.schema.AuthorDetailInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class AuthorDetailInfoSet extends SchemaSet {
	public AuthorDetailInfoSet() {
		this(10,0);
	}

	public AuthorDetailInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public AuthorDetailInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = AuthorDetailInfoSchema._TableCode;
		Columns = AuthorDetailInfoSchema._Columns;
		NameSpace = AuthorDetailInfoSchema._NameSpace;
		InsertAllSQL = AuthorDetailInfoSchema._InsertAllSQL;
		UpdateAllSQL = AuthorDetailInfoSchema._UpdateAllSQL;
		FillAllSQL = AuthorDetailInfoSchema._FillAllSQL;
		DeleteSQL = AuthorDetailInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new AuthorDetailInfoSet();
	}

	public boolean add(AuthorDetailInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(AuthorDetailInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(AuthorDetailInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public AuthorDetailInfoSchema get(int index) {
		AuthorDetailInfoSchema tSchema = (AuthorDetailInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, AuthorDetailInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(AuthorDetailInfoSet aSet) {
		return super.set(aSet);
	}
}
 