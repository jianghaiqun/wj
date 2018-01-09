package com.sinosoft.schema;

import com.sinosoft.schema.TopAdInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class TopAdInfoSet extends SchemaSet {
	public TopAdInfoSet() {
		this(10,0);
	}

	public TopAdInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public TopAdInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = TopAdInfoSchema._TableCode;
		Columns = TopAdInfoSchema._Columns;
		NameSpace = TopAdInfoSchema._NameSpace;
		InsertAllSQL = TopAdInfoSchema._InsertAllSQL;
		UpdateAllSQL = TopAdInfoSchema._UpdateAllSQL;
		FillAllSQL = TopAdInfoSchema._FillAllSQL;
		DeleteSQL = TopAdInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new TopAdInfoSet();
	}

	public boolean add(TopAdInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(TopAdInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(TopAdInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public TopAdInfoSchema get(int index) {
		TopAdInfoSchema tSchema = (TopAdInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, TopAdInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(TopAdInfoSet aSet) {
		return super.set(aSet);
	}
}
 