package com.sinosoft.schema;

import com.sinosoft.schema.ClaimsFileSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ClaimsFileSet extends SchemaSet {
	public ClaimsFileSet() {
		this(10,0);
	}

	public ClaimsFileSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ClaimsFileSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ClaimsFileSchema._TableCode;
		Columns = ClaimsFileSchema._Columns;
		NameSpace = ClaimsFileSchema._NameSpace;
		InsertAllSQL = ClaimsFileSchema._InsertAllSQL;
		UpdateAllSQL = ClaimsFileSchema._UpdateAllSQL;
		FillAllSQL = ClaimsFileSchema._FillAllSQL;
		DeleteSQL = ClaimsFileSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ClaimsFileSet();
	}

	public boolean add(ClaimsFileSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ClaimsFileSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ClaimsFileSchema aSchema) {
		return super.remove(aSchema);
	}

	public ClaimsFileSchema get(int index) {
		ClaimsFileSchema tSchema = (ClaimsFileSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ClaimsFileSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ClaimsFileSet aSet) {
		return super.set(aSet);
	}
}
 