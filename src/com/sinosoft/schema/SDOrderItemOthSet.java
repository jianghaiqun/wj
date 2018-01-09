package com.sinosoft.schema;

import com.sinosoft.schema.SDOrderItemOthSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDOrderItemOthSet extends SchemaSet {
	public SDOrderItemOthSet() { 
		this(10,0);
	}

	public SDOrderItemOthSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDOrderItemOthSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDOrderItemOthSchema._TableCode; 
		Columns = SDOrderItemOthSchema._Columns;
		NameSpace = SDOrderItemOthSchema._NameSpace;
		InsertAllSQL = SDOrderItemOthSchema._InsertAllSQL;
		UpdateAllSQL = SDOrderItemOthSchema._UpdateAllSQL;
		FillAllSQL = SDOrderItemOthSchema._FillAllSQL;
		DeleteSQL = SDOrderItemOthSchema._DeleteSQL;
	} 
 
	protected SchemaSet newInstance(){
		return new SDOrderItemOthSet();
	}

	public boolean add(SDOrderItemOthSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDOrderItemOthSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDOrderItemOthSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDOrderItemOthSchema get(int index) {
		SDOrderItemOthSchema tSchema = (SDOrderItemOthSchema) super.getObject(index);
		return tSchema; 
	}

	public boolean set(int index, SDOrderItemOthSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDOrderItemOthSet aSet) { 
		return super.set(aSet);
	}
}
 