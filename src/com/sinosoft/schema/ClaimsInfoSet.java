package com.sinosoft.schema;

import com.sinosoft.schema.ClaimsInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ClaimsInfoSet extends SchemaSet {
	public ClaimsInfoSet() {
		this(10,0);
	}

	public ClaimsInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ClaimsInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ClaimsInfoSchema._TableCode;
		Columns = ClaimsInfoSchema._Columns;
		NameSpace = ClaimsInfoSchema._NameSpace;
		InsertAllSQL = ClaimsInfoSchema._InsertAllSQL;
		UpdateAllSQL = ClaimsInfoSchema._UpdateAllSQL;
		FillAllSQL = ClaimsInfoSchema._FillAllSQL;
		DeleteSQL = ClaimsInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ClaimsInfoSet();
	}

	public boolean add(ClaimsInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ClaimsInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ClaimsInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public ClaimsInfoSchema get(int index) {
		ClaimsInfoSchema tSchema = (ClaimsInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ClaimsInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ClaimsInfoSet aSet) {
		return super.set(aSet);
	}
}
 