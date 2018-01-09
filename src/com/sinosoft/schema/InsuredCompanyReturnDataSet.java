package com.sinosoft.schema;

import com.sinosoft.schema.InsuredCompanyReturnDataSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class InsuredCompanyReturnDataSet extends SchemaSet {
	public InsuredCompanyReturnDataSet() {
		this(10,0);
	}

	public InsuredCompanyReturnDataSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public InsuredCompanyReturnDataSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = InsuredCompanyReturnDataSchema._TableCode;
		Columns = InsuredCompanyReturnDataSchema._Columns;
		NameSpace = InsuredCompanyReturnDataSchema._NameSpace;
		InsertAllSQL = InsuredCompanyReturnDataSchema._InsertAllSQL;
		UpdateAllSQL = InsuredCompanyReturnDataSchema._UpdateAllSQL;
		FillAllSQL = InsuredCompanyReturnDataSchema._FillAllSQL;
		DeleteSQL = InsuredCompanyReturnDataSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new InsuredCompanyReturnDataSet();
	}

	public boolean add(InsuredCompanyReturnDataSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(InsuredCompanyReturnDataSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(InsuredCompanyReturnDataSchema aSchema) {
		return super.remove(aSchema);
	}

	public InsuredCompanyReturnDataSchema get(int index) {
		InsuredCompanyReturnDataSchema tSchema = (InsuredCompanyReturnDataSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, InsuredCompanyReturnDataSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(InsuredCompanyReturnDataSet aSet) {
		return super.set(aSet);
	}
}
 