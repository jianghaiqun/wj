package com.sinosoft.schema;

import com.sinosoft.schema.ChannelCostsSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ChannelCostsSet extends SchemaSet {
	public ChannelCostsSet() {
		this(10,0);
	}

	public ChannelCostsSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ChannelCostsSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ChannelCostsSchema._TableCode;
		Columns = ChannelCostsSchema._Columns;
		NameSpace = ChannelCostsSchema._NameSpace;
		InsertAllSQL = ChannelCostsSchema._InsertAllSQL;
		UpdateAllSQL = ChannelCostsSchema._UpdateAllSQL;
		FillAllSQL = ChannelCostsSchema._FillAllSQL;
		DeleteSQL = ChannelCostsSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ChannelCostsSet();
	}

	public boolean add(ChannelCostsSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ChannelCostsSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ChannelCostsSchema aSchema) {
		return super.remove(aSchema);
	}

	public ChannelCostsSchema get(int index) {
		ChannelCostsSchema tSchema = (ChannelCostsSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ChannelCostsSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ChannelCostsSet aSet) {
		return super.set(aSet);
	}
}
 