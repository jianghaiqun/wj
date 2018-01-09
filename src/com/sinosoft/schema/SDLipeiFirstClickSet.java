package com.sinosoft.schema;

import com.sinosoft.schema.SDLipeiFirstClickSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDLipeiFirstClickSet extends SchemaSet {
	public SDLipeiFirstClickSet() {
		this(10,0);
	}

	public SDLipeiFirstClickSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDLipeiFirstClickSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDLipeiFirstClickSchema._TableCode;
		Columns = SDLipeiFirstClickSchema._Columns;
		NameSpace = SDLipeiFirstClickSchema._NameSpace;
		InsertAllSQL = SDLipeiFirstClickSchema._InsertAllSQL;
		UpdateAllSQL = SDLipeiFirstClickSchema._UpdateAllSQL;
		FillAllSQL = SDLipeiFirstClickSchema._FillAllSQL;
		DeleteSQL = SDLipeiFirstClickSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDLipeiFirstClickSet();
	}

	public boolean add(SDLipeiFirstClickSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDLipeiFirstClickSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDLipeiFirstClickSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDLipeiFirstClickSchema get(int index) {
		SDLipeiFirstClickSchema tSchema = (SDLipeiFirstClickSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDLipeiFirstClickSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDLipeiFirstClickSet aSet) {
		return super.set(aSet);
	}
}
 