package com.sinosoft.schema;

import com.sinosoft.schema.PKProductDutyInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class PKProductDutyInfoSet extends SchemaSet {
	public PKProductDutyInfoSet() {
		this(10,0);
	}

	public PKProductDutyInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public PKProductDutyInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = PKProductDutyInfoSchema._TableCode;
		Columns = PKProductDutyInfoSchema._Columns;
		NameSpace = PKProductDutyInfoSchema._NameSpace;
		InsertAllSQL = PKProductDutyInfoSchema._InsertAllSQL;
		UpdateAllSQL = PKProductDutyInfoSchema._UpdateAllSQL;
		FillAllSQL = PKProductDutyInfoSchema._FillAllSQL;
		DeleteSQL = PKProductDutyInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new PKProductDutyInfoSet();
	}

	public boolean add(PKProductDutyInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(PKProductDutyInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(PKProductDutyInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public PKProductDutyInfoSchema get(int index) {
		PKProductDutyInfoSchema tSchema = (PKProductDutyInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, PKProductDutyInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(PKProductDutyInfoSet aSet) {
		return super.set(aSet);
	}
}
 