package com.sinosoft.schema;

import com.sinosoft.schema.selfdriveactivitySchema;
import com.sinosoft.framework.orm.SchemaSet;

public class selfdriveactivitySet extends SchemaSet {
	public selfdriveactivitySet() {
		this(10,0);
	}

	public selfdriveactivitySet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public selfdriveactivitySet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = selfdriveactivitySchema._TableCode;
		Columns = selfdriveactivitySchema._Columns;
		NameSpace = selfdriveactivitySchema._NameSpace;
		InsertAllSQL = selfdriveactivitySchema._InsertAllSQL;
		UpdateAllSQL = selfdriveactivitySchema._UpdateAllSQL;
		FillAllSQL = selfdriveactivitySchema._FillAllSQL;
		DeleteSQL = selfdriveactivitySchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new selfdriveactivitySet();
	}

	public boolean add(selfdriveactivitySchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(selfdriveactivitySet aSet) {
		return super.add(aSet);
	}

	public boolean remove(selfdriveactivitySchema aSchema) {
		return super.remove(aSchema);
	}

	public selfdriveactivitySchema get(int index) {
		selfdriveactivitySchema tSchema = (selfdriveactivitySchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, selfdriveactivitySchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(selfdriveactivitySet aSet) {
		return super.set(aSet);
	}
}
 