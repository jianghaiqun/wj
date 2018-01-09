package com.sinosoft.schema;

import com.sinosoft.schema.ProductHelpInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ProductHelpInfoSet extends SchemaSet {
	public ProductHelpInfoSet() {
		this(10,0);
	}

	public ProductHelpInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ProductHelpInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ProductHelpInfoSchema._TableCode;
		Columns = ProductHelpInfoSchema._Columns;
		NameSpace = ProductHelpInfoSchema._NameSpace;
		InsertAllSQL = ProductHelpInfoSchema._InsertAllSQL;
		UpdateAllSQL = ProductHelpInfoSchema._UpdateAllSQL;
		FillAllSQL = ProductHelpInfoSchema._FillAllSQL;
		DeleteSQL = ProductHelpInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ProductHelpInfoSet();
	}

	public boolean add(ProductHelpInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ProductHelpInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ProductHelpInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public ProductHelpInfoSchema get(int index) {
		ProductHelpInfoSchema tSchema = (ProductHelpInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ProductHelpInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ProductHelpInfoSet aSet) {
		return super.set(aSet);
	}
}
 