package com.sinosoft.schema;

import com.sinosoft.schema.ClaimsInfoCompanyInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ClaimsInfoCompanyInfoSet extends SchemaSet {
	public ClaimsInfoCompanyInfoSet() {
		this(10,0);
	}

	public ClaimsInfoCompanyInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ClaimsInfoCompanyInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ClaimsInfoCompanyInfoSchema._TableCode;
		Columns = ClaimsInfoCompanyInfoSchema._Columns;
		NameSpace = ClaimsInfoCompanyInfoSchema._NameSpace;
		InsertAllSQL = ClaimsInfoCompanyInfoSchema._InsertAllSQL;
		UpdateAllSQL = ClaimsInfoCompanyInfoSchema._UpdateAllSQL;
		FillAllSQL = ClaimsInfoCompanyInfoSchema._FillAllSQL;
		DeleteSQL = ClaimsInfoCompanyInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ClaimsInfoCompanyInfoSet();
	}

	public boolean add(ClaimsInfoCompanyInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ClaimsInfoCompanyInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ClaimsInfoCompanyInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public ClaimsInfoCompanyInfoSchema get(int index) {
		ClaimsInfoCompanyInfoSchema tSchema = (ClaimsInfoCompanyInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ClaimsInfoCompanyInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ClaimsInfoCompanyInfoSet aSet) {
		return super.set(aSet);
	}
}
 