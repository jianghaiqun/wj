package com.sinosoft.schema;

import com.sinosoft.schema.ZDFEUIInfoValueSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ZDFEUIInfoValueSet extends SchemaSet {
	private static final long serialVersionUID = 1578342955237503071L;

	public ZDFEUIInfoValueSet() {
		this(10,0);
	}

	public ZDFEUIInfoValueSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDFEUIInfoValueSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDFEUIInfoValueSchema._TableCode;
		Columns = ZDFEUIInfoValueSchema._Columns;
		NameSpace = ZDFEUIInfoValueSchema._NameSpace;
		InsertAllSQL = ZDFEUIInfoValueSchema._InsertAllSQL;
		UpdateAllSQL = ZDFEUIInfoValueSchema._UpdateAllSQL;
		FillAllSQL = ZDFEUIInfoValueSchema._FillAllSQL;
		DeleteSQL = ZDFEUIInfoValueSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDFEUIInfoValueSet();
	}

	public boolean add(ZDFEUIInfoValueSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDFEUIInfoValueSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDFEUIInfoValueSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDFEUIInfoValueSchema get(int index) {
		ZDFEUIInfoValueSchema tSchema = (ZDFEUIInfoValueSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDFEUIInfoValueSchema aSchema) {
		return super.set(index, aSchema);
	}

	@SuppressWarnings("deprecation")
	public boolean set(ZDFEUIInfoValueSet aSet) {
		return super.set(aSet);
	}
}
 