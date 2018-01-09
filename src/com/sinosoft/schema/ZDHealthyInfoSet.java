package com.sinosoft.schema;

import com.sinosoft.schema.ZDHealthyInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ZDHealthyInfoSet extends SchemaSet {
	private static final long serialVersionUID = -1635935352281963842L;

	public ZDHealthyInfoSet() {
		this(10,0);
	}

	public ZDHealthyInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDHealthyInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDHealthyInfoSchema._TableCode;
		Columns = ZDHealthyInfoSchema._Columns;
		NameSpace = ZDHealthyInfoSchema._NameSpace;
		InsertAllSQL = ZDHealthyInfoSchema._InsertAllSQL;
		UpdateAllSQL = ZDHealthyInfoSchema._UpdateAllSQL;
		FillAllSQL = ZDHealthyInfoSchema._FillAllSQL;
		DeleteSQL = ZDHealthyInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDHealthyInfoSet();
	}

	public boolean add(ZDHealthyInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDHealthyInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDHealthyInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDHealthyInfoSchema get(int index) {
		ZDHealthyInfoSchema tSchema = (ZDHealthyInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDHealthyInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	@SuppressWarnings("deprecation")
	public boolean set(ZDHealthyInfoSet aSet) {
		return super.set(aSet);
	}
}
 