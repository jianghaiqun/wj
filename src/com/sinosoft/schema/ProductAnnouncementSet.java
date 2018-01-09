package com.sinosoft.schema;

import com.sinosoft.schema.ProductAnnouncementSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ProductAnnouncementSet extends SchemaSet {
	public ProductAnnouncementSet() {
		this(10,0);
	}

	public ProductAnnouncementSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ProductAnnouncementSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ProductAnnouncementSchema._TableCode;
		Columns = ProductAnnouncementSchema._Columns;
		NameSpace = ProductAnnouncementSchema._NameSpace;
		InsertAllSQL = ProductAnnouncementSchema._InsertAllSQL;
		UpdateAllSQL = ProductAnnouncementSchema._UpdateAllSQL;
		FillAllSQL = ProductAnnouncementSchema._FillAllSQL;
		DeleteSQL = ProductAnnouncementSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ProductAnnouncementSet();
	}

	public boolean add(ProductAnnouncementSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ProductAnnouncementSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ProductAnnouncementSchema aSchema) {
		return super.remove(aSchema);
	}

	public ProductAnnouncementSchema get(int index) {
		ProductAnnouncementSchema tSchema = (ProductAnnouncementSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ProductAnnouncementSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ProductAnnouncementSet aSet) {
		return super.set(aSet);
	}
}
 