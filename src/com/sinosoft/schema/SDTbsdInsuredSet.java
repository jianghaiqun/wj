package com.sinosoft.schema;

import com.sinosoft.schema.SDTbsdInsuredSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDTbsdInsuredSet extends SchemaSet {
	public SDTbsdInsuredSet() {
		this(10,0);
	}

	public SDTbsdInsuredSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDTbsdInsuredSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDTbsdInsuredSchema._TableCode;
		Columns = SDTbsdInsuredSchema._Columns;
		NameSpace = SDTbsdInsuredSchema._NameSpace;
		InsertAllSQL = SDTbsdInsuredSchema._InsertAllSQL;
		UpdateAllSQL = SDTbsdInsuredSchema._UpdateAllSQL;
		FillAllSQL = SDTbsdInsuredSchema._FillAllSQL;
		DeleteSQL = SDTbsdInsuredSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDTbsdInsuredSet();
	}

	public boolean add(SDTbsdInsuredSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDTbsdInsuredSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDTbsdInsuredSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDTbsdInsuredSchema get(int index) {
		SDTbsdInsuredSchema tSchema = (SDTbsdInsuredSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDTbsdInsuredSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDTbsdInsuredSet aSet) {
		return super.set(aSet);
	}
}
 