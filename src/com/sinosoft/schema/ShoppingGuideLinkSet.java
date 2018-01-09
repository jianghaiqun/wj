package com.sinosoft.schema;

import com.sinosoft.schema.ShoppingGuideLinkSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ShoppingGuideLinkSet extends SchemaSet {
	public ShoppingGuideLinkSet() {
		this(10,0);
	}

	public ShoppingGuideLinkSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ShoppingGuideLinkSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ShoppingGuideLinkSchema._TableCode;
		Columns = ShoppingGuideLinkSchema._Columns;
		NameSpace = ShoppingGuideLinkSchema._NameSpace;
		InsertAllSQL = ShoppingGuideLinkSchema._InsertAllSQL;
		UpdateAllSQL = ShoppingGuideLinkSchema._UpdateAllSQL;
		FillAllSQL = ShoppingGuideLinkSchema._FillAllSQL;
		DeleteSQL = ShoppingGuideLinkSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ShoppingGuideLinkSet();
	}

	public boolean add(ShoppingGuideLinkSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ShoppingGuideLinkSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ShoppingGuideLinkSchema aSchema) {
		return super.remove(aSchema);
	}

	public ShoppingGuideLinkSchema get(int index) {
		ShoppingGuideLinkSchema tSchema = (ShoppingGuideLinkSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ShoppingGuideLinkSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ShoppingGuideLinkSet aSet) {
		return super.set(aSet);
	}
}
 