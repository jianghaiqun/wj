package com.sinosoft.schema;

import com.sinosoft.schema.MarketingConfigSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class MarketingConfigSet extends SchemaSet {
	public MarketingConfigSet() {
		this(10,0);
	}

	public MarketingConfigSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public MarketingConfigSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = MarketingConfigSchema._TableCode;
		Columns = MarketingConfigSchema._Columns;
		NameSpace = MarketingConfigSchema._NameSpace;
		InsertAllSQL = MarketingConfigSchema._InsertAllSQL;
		UpdateAllSQL = MarketingConfigSchema._UpdateAllSQL;
		FillAllSQL = MarketingConfigSchema._FillAllSQL;
		DeleteSQL = MarketingConfigSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new MarketingConfigSet();
	}

	public boolean add(MarketingConfigSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(MarketingConfigSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(MarketingConfigSchema aSchema) {
		return super.remove(aSchema);
	}

	public MarketingConfigSchema get(int index) {
		MarketingConfigSchema tSchema = (MarketingConfigSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, MarketingConfigSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(MarketingConfigSet aSet) {
		return super.set(aSet);
	}
}
 