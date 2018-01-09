package com.sinosoft.schema;

import com.sinosoft.schema.ProductBaseInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ProductBaseInfoSet extends SchemaSet {
	public ProductBaseInfoSet() {
		this(10,0);
	}

	public ProductBaseInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ProductBaseInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ProductBaseInfoSchema._TableCode;
		Columns = ProductBaseInfoSchema._Columns;
		NameSpace = ProductBaseInfoSchema._NameSpace;
		InsertAllSQL = ProductBaseInfoSchema._InsertAllSQL;
		UpdateAllSQL = ProductBaseInfoSchema._UpdateAllSQL;
		FillAllSQL = ProductBaseInfoSchema._FillAllSQL;
		DeleteSQL = ProductBaseInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ProductBaseInfoSet();
	}

	public boolean add(ProductBaseInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ProductBaseInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ProductBaseInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public ProductBaseInfoSchema get(int index) {
		ProductBaseInfoSchema tSchema = (ProductBaseInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ProductBaseInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ProductBaseInfoSet aSet) {
		return super.set(aSet);
	}
}
 