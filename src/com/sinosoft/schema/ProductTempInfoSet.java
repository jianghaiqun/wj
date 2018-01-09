package com.sinosoft.schema;

import com.sinosoft.schema.ProductTempInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ProductTempInfoSet extends SchemaSet {
	public ProductTempInfoSet() {
		this(10,0);
	}
 
	public ProductTempInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ProductTempInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ProductTempInfoSchema._TableCode;
		Columns = ProductTempInfoSchema._Columns;
		NameSpace = ProductTempInfoSchema._NameSpace;
		InsertAllSQL = ProductTempInfoSchema._InsertAllSQL;
		UpdateAllSQL = ProductTempInfoSchema._UpdateAllSQL;
		FillAllSQL = ProductTempInfoSchema._FillAllSQL;
		DeleteSQL = ProductTempInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ProductTempInfoSet();
	}

	public boolean add(ProductTempInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ProductTempInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ProductTempInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public ProductTempInfoSchema get(int index) {
		ProductTempInfoSchema tSchema = (ProductTempInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ProductTempInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ProductTempInfoSet aSet) {
		return super.set(aSet);
	}
}
 