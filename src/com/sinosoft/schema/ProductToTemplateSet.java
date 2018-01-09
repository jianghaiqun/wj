package com.sinosoft.schema;

import com.sinosoft.schema.ProductToTemplateSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ProductToTemplateSet extends SchemaSet {
	public ProductToTemplateSet() {
		this(10,0);
	}

	public ProductToTemplateSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ProductToTemplateSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ProductToTemplateSchema._TableCode;
		Columns = ProductToTemplateSchema._Columns;
		NameSpace = ProductToTemplateSchema._NameSpace;
		InsertAllSQL = ProductToTemplateSchema._InsertAllSQL;
		UpdateAllSQL = ProductToTemplateSchema._UpdateAllSQL;
		FillAllSQL = ProductToTemplateSchema._FillAllSQL;
		DeleteSQL = ProductToTemplateSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ProductToTemplateSet();
	}

	public boolean add(ProductToTemplateSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ProductToTemplateSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ProductToTemplateSchema aSchema) {
		return super.remove(aSchema);
	}

	public ProductToTemplateSchema get(int index) {
		ProductToTemplateSchema tSchema = (ProductToTemplateSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ProductToTemplateSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ProductToTemplateSet aSet) {
		return super.set(aSet);
	}
}
 