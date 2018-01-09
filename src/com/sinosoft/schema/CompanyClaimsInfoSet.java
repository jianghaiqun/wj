package com.sinosoft.schema;

import com.sinosoft.schema.CompanyClaimsInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class CompanyClaimsInfoSet extends SchemaSet {
	public CompanyClaimsInfoSet() {
		this(10,0);
	}

	public CompanyClaimsInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public CompanyClaimsInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = CompanyClaimsInfoSchema._TableCode;
		Columns = CompanyClaimsInfoSchema._Columns;
		NameSpace = CompanyClaimsInfoSchema._NameSpace;
		InsertAllSQL = CompanyClaimsInfoSchema._InsertAllSQL;
		UpdateAllSQL = CompanyClaimsInfoSchema._UpdateAllSQL;
		FillAllSQL = CompanyClaimsInfoSchema._FillAllSQL;
		DeleteSQL = CompanyClaimsInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new CompanyClaimsInfoSet();
	}

	public boolean add(CompanyClaimsInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(CompanyClaimsInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(CompanyClaimsInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public CompanyClaimsInfoSchema get(int index) {
		CompanyClaimsInfoSchema tSchema = (CompanyClaimsInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, CompanyClaimsInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(CompanyClaimsInfoSet aSet) {
		return super.set(aSet);
	}
}
 