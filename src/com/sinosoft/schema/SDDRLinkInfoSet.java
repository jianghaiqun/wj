package com.sinosoft.schema;

import com.sinosoft.schema.SDDRLinkInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDDRLinkInfoSet extends SchemaSet {
	public SDDRLinkInfoSet() {
		this(10,0);
	}

	public SDDRLinkInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDDRLinkInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDDRLinkInfoSchema._TableCode;
		Columns = SDDRLinkInfoSchema._Columns;
		NameSpace = SDDRLinkInfoSchema._NameSpace;
		InsertAllSQL = SDDRLinkInfoSchema._InsertAllSQL;
		UpdateAllSQL = SDDRLinkInfoSchema._UpdateAllSQL;
		FillAllSQL = SDDRLinkInfoSchema._FillAllSQL;
		DeleteSQL = SDDRLinkInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDDRLinkInfoSet();
	}

	public boolean add(SDDRLinkInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDDRLinkInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDDRLinkInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDDRLinkInfoSchema get(int index) {
		SDDRLinkInfoSchema tSchema = (SDDRLinkInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDDRLinkInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDDRLinkInfoSet aSet) {
		return super.set(aSet);
	}
}
 