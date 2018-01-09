package com.sinosoft.schema;

import com.sinosoft.schema.SDPromotionDetailModuleSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDPromotionDetailModuleSet extends SchemaSet {
	public SDPromotionDetailModuleSet() {
		this(10,0);
	}

	public SDPromotionDetailModuleSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDPromotionDetailModuleSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDPromotionDetailModuleSchema._TableCode;
		Columns = SDPromotionDetailModuleSchema._Columns;
		NameSpace = SDPromotionDetailModuleSchema._NameSpace;
		InsertAllSQL = SDPromotionDetailModuleSchema._InsertAllSQL;
		UpdateAllSQL = SDPromotionDetailModuleSchema._UpdateAllSQL;
		FillAllSQL = SDPromotionDetailModuleSchema._FillAllSQL;
		DeleteSQL = SDPromotionDetailModuleSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDPromotionDetailModuleSet();
	}

	public boolean add(SDPromotionDetailModuleSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDPromotionDetailModuleSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDPromotionDetailModuleSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDPromotionDetailModuleSchema get(int index) {
		SDPromotionDetailModuleSchema tSchema = (SDPromotionDetailModuleSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDPromotionDetailModuleSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDPromotionDetailModuleSet aSet) {
		return super.set(aSet);
	}
}
 