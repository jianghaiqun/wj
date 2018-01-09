package com.sinosoft.schema;

import com.sinosoft.schema.SDWapBidMenuSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDWapBidMenuSet extends SchemaSet {
	public SDWapBidMenuSet() {
		this(10,0);
	}

	public SDWapBidMenuSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDWapBidMenuSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDWapBidMenuSchema._TableCode;
		Columns = SDWapBidMenuSchema._Columns;
		NameSpace = SDWapBidMenuSchema._NameSpace;
		InsertAllSQL = SDWapBidMenuSchema._InsertAllSQL;
		UpdateAllSQL = SDWapBidMenuSchema._UpdateAllSQL;
		FillAllSQL = SDWapBidMenuSchema._FillAllSQL;
		DeleteSQL = SDWapBidMenuSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDWapBidMenuSet();
	}

	public boolean add(SDWapBidMenuSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDWapBidMenuSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDWapBidMenuSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDWapBidMenuSchema get(int index) {
		SDWapBidMenuSchema tSchema = (SDWapBidMenuSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDWapBidMenuSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDWapBidMenuSet aSet) {
		return super.set(aSet);
	}
}
 