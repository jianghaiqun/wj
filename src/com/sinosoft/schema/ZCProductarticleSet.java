package com.sinosoft.schema;

import com.sinosoft.schema.ZCProductarticleSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ZCProductarticleSet extends SchemaSet {
	public ZCProductarticleSet() {
		this(10,0);
	}

	public ZCProductarticleSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCProductarticleSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCProductarticleSchema._TableCode;
		Columns = ZCProductarticleSchema._Columns;
		NameSpace = ZCProductarticleSchema._NameSpace;
		InsertAllSQL = ZCProductarticleSchema._InsertAllSQL;
		UpdateAllSQL = ZCProductarticleSchema._UpdateAllSQL;
		FillAllSQL = ZCProductarticleSchema._FillAllSQL;
		DeleteSQL = ZCProductarticleSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCProductarticleSet();
	}

	public boolean add(ZCProductarticleSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCProductarticleSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCProductarticleSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCProductarticleSchema get(int index) {
		ZCProductarticleSchema tSchema = (ZCProductarticleSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCProductarticleSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCProductarticleSet aSet) {
		return super.set(aSet);
	}
}
 