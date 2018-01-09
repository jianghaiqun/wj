package com.sinosoft.schema;

import com.sinosoft.schema.SDSearchRelaProductSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDSearchRelaProductSet extends SchemaSet {
	public SDSearchRelaProductSet() {
		this(10,0);
	}

	public SDSearchRelaProductSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDSearchRelaProductSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDSearchRelaProductSchema._TableCode;
		Columns = SDSearchRelaProductSchema._Columns;
		NameSpace = SDSearchRelaProductSchema._NameSpace;
		InsertAllSQL = SDSearchRelaProductSchema._InsertAllSQL;
		UpdateAllSQL = SDSearchRelaProductSchema._UpdateAllSQL;
		FillAllSQL = SDSearchRelaProductSchema._FillAllSQL;
		DeleteSQL = SDSearchRelaProductSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDSearchRelaProductSet();
	}

	public boolean add(SDSearchRelaProductSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDSearchRelaProductSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDSearchRelaProductSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDSearchRelaProductSchema get(int index) {
		SDSearchRelaProductSchema tSchema = (SDSearchRelaProductSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDSearchRelaProductSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDSearchRelaProductSet aSet) {
		return super.set(aSet);
	}
}
 