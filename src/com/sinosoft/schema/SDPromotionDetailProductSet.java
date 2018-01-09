package com.sinosoft.schema;

import com.sinosoft.schema.SDPromotionDetailProductSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDPromotionDetailProductSet extends SchemaSet {
	public SDPromotionDetailProductSet() {
		this(10,0);
	}

	public SDPromotionDetailProductSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDPromotionDetailProductSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDPromotionDetailProductSchema._TableCode;
		Columns = SDPromotionDetailProductSchema._Columns;
		NameSpace = SDPromotionDetailProductSchema._NameSpace;
		InsertAllSQL = SDPromotionDetailProductSchema._InsertAllSQL;
		UpdateAllSQL = SDPromotionDetailProductSchema._UpdateAllSQL;
		FillAllSQL = SDPromotionDetailProductSchema._FillAllSQL;
		DeleteSQL = SDPromotionDetailProductSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDPromotionDetailProductSet();
	}

	public boolean add(SDPromotionDetailProductSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDPromotionDetailProductSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDPromotionDetailProductSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDPromotionDetailProductSchema get(int index) {
		SDPromotionDetailProductSchema tSchema = (SDPromotionDetailProductSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDPromotionDetailProductSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDPromotionDetailProductSet aSet) {
		return super.set(aSet);
	}
}
 