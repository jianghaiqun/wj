package com.sinosoft.schema;

import com.sinosoft.schema.SDDRLinkCheckResultSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDDRLinkCheckResultSet extends SchemaSet {
	public SDDRLinkCheckResultSet() {
		this(10,0);
	}

	public SDDRLinkCheckResultSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDDRLinkCheckResultSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDDRLinkCheckResultSchema._TableCode;
		Columns = SDDRLinkCheckResultSchema._Columns;
		NameSpace = SDDRLinkCheckResultSchema._NameSpace;
		InsertAllSQL = SDDRLinkCheckResultSchema._InsertAllSQL;
		UpdateAllSQL = SDDRLinkCheckResultSchema._UpdateAllSQL;
		FillAllSQL = SDDRLinkCheckResultSchema._FillAllSQL;
		DeleteSQL = SDDRLinkCheckResultSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDDRLinkCheckResultSet();
	}

	public boolean add(SDDRLinkCheckResultSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDDRLinkCheckResultSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDDRLinkCheckResultSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDDRLinkCheckResultSchema get(int index) {
		SDDRLinkCheckResultSchema tSchema = (SDDRLinkCheckResultSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDDRLinkCheckResultSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDDRLinkCheckResultSet aSet) {
		return super.set(aSet);
	}
}
 