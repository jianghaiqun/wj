package com.sinosoft.schema;

import com.sinosoft.schema.CompanyClaimsPayInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class CompanyClaimsPayInfoSet extends SchemaSet {
	public CompanyClaimsPayInfoSet() {
		this(10,0);
	}

	public CompanyClaimsPayInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public CompanyClaimsPayInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = CompanyClaimsPayInfoSchema._TableCode;
		Columns = CompanyClaimsPayInfoSchema._Columns;
		NameSpace = CompanyClaimsPayInfoSchema._NameSpace;
		InsertAllSQL = CompanyClaimsPayInfoSchema._InsertAllSQL;
		UpdateAllSQL = CompanyClaimsPayInfoSchema._UpdateAllSQL;
		FillAllSQL = CompanyClaimsPayInfoSchema._FillAllSQL;
		DeleteSQL = CompanyClaimsPayInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new CompanyClaimsPayInfoSet();
	}

	public boolean add(CompanyClaimsPayInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(CompanyClaimsPayInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(CompanyClaimsPayInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public CompanyClaimsPayInfoSchema get(int index) {
		CompanyClaimsPayInfoSchema tSchema = (CompanyClaimsPayInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, CompanyClaimsPayInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(CompanyClaimsPayInfoSet aSet) {
		return super.set(aSet);
	}
}
 