package com.sinosoft.schema;

import com.sinosoft.schema.ProductRelaInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ProductRelaInfoSet extends SchemaSet {
	public ProductRelaInfoSet() {
		this(10,0);  
	}

	public ProductRelaInfoSet(int initialCapacity) {
		this(initialCapacity,0); 
	}

	public ProductRelaInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ProductRelaInfoSchema._TableCode;
		Columns = ProductRelaInfoSchema._Columns;
		NameSpace = ProductRelaInfoSchema._NameSpace;
		InsertAllSQL = ProductRelaInfoSchema._InsertAllSQL;
		UpdateAllSQL = ProductRelaInfoSchema._UpdateAllSQL;
		FillAllSQL = ProductRelaInfoSchema._FillAllSQL;
		DeleteSQL = ProductRelaInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ProductRelaInfoSet();
	}

	public boolean add(ProductRelaInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ProductRelaInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ProductRelaInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public ProductRelaInfoSchema get(int index) {
		ProductRelaInfoSchema tSchema = (ProductRelaInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ProductRelaInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ProductRelaInfoSet aSet) {
		return super.set(aSet);
	}
}
 