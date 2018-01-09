package com.sinosoft.schema;

import com.sinosoft.schema.OtProductPriceSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class OtProductPriceSet extends SchemaSet {
	public OtProductPriceSet() {
		this(10,0);
	}

	public OtProductPriceSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public OtProductPriceSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = OtProductPriceSchema._TableCode;
		Columns = OtProductPriceSchema._Columns;
		NameSpace = OtProductPriceSchema._NameSpace;
		InsertAllSQL = OtProductPriceSchema._InsertAllSQL;
		UpdateAllSQL = OtProductPriceSchema._UpdateAllSQL;
		FillAllSQL = OtProductPriceSchema._FillAllSQL;
		DeleteSQL = OtProductPriceSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new OtProductPriceSet();
	}

	public boolean add(OtProductPriceSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(OtProductPriceSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(OtProductPriceSchema aSchema) {
		return super.remove(aSchema);
	}

	public OtProductPriceSchema get(int index) {
		OtProductPriceSchema tSchema = (OtProductPriceSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, OtProductPriceSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(OtProductPriceSet aSet) {
		return super.set(aSet);
	}
}
 