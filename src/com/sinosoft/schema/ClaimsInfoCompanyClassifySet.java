package com.sinosoft.schema;

import com.sinosoft.schema.ClaimsInfoCompanyClassifySchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ClaimsInfoCompanyClassifySet extends SchemaSet {
	public ClaimsInfoCompanyClassifySet() {
		this(10,0);
	}

	public ClaimsInfoCompanyClassifySet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ClaimsInfoCompanyClassifySet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ClaimsInfoCompanyClassifySchema._TableCode;
		Columns = ClaimsInfoCompanyClassifySchema._Columns;
		NameSpace = ClaimsInfoCompanyClassifySchema._NameSpace;
		InsertAllSQL = ClaimsInfoCompanyClassifySchema._InsertAllSQL;
		UpdateAllSQL = ClaimsInfoCompanyClassifySchema._UpdateAllSQL;
		FillAllSQL = ClaimsInfoCompanyClassifySchema._FillAllSQL;
		DeleteSQL = ClaimsInfoCompanyClassifySchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ClaimsInfoCompanyClassifySet();
	}

	public boolean add(ClaimsInfoCompanyClassifySchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ClaimsInfoCompanyClassifySet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ClaimsInfoCompanyClassifySchema aSchema) {
		return super.remove(aSchema);
	}

	public ClaimsInfoCompanyClassifySchema get(int index) {
		ClaimsInfoCompanyClassifySchema tSchema = (ClaimsInfoCompanyClassifySchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ClaimsInfoCompanyClassifySchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ClaimsInfoCompanyClassifySet aSet) {
		return super.set(aSet);
	}
}
 