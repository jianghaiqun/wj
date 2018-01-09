package com.sinosoft.schema;

import com.sinosoft.schema.SDWapBidProductSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDWapBidProductSet extends SchemaSet {
	public SDWapBidProductSet() {
		this(10,0);
	}

	public SDWapBidProductSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDWapBidProductSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDWapBidProductSchema._TableCode;
		Columns = SDWapBidProductSchema._Columns;
		NameSpace = SDWapBidProductSchema._NameSpace;
		InsertAllSQL = SDWapBidProductSchema._InsertAllSQL;
		UpdateAllSQL = SDWapBidProductSchema._UpdateAllSQL;
		FillAllSQL = SDWapBidProductSchema._FillAllSQL;
		DeleteSQL = SDWapBidProductSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDWapBidProductSet();
	}

	public boolean add(SDWapBidProductSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDWapBidProductSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDWapBidProductSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDWapBidProductSchema get(int index) {
		SDWapBidProductSchema tSchema = (SDWapBidProductSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDWapBidProductSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDWapBidProductSet aSet) {
		return super.set(aSet);
	}
}
 