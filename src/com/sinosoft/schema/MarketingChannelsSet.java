package com.sinosoft.schema;

import com.sinosoft.schema.MarketingChannelsSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class MarketingChannelsSet extends SchemaSet {
	public MarketingChannelsSet() {
		this(10,0);
	}

	public MarketingChannelsSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public MarketingChannelsSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = MarketingChannelsSchema._TableCode;
		Columns = MarketingChannelsSchema._Columns;
		NameSpace = MarketingChannelsSchema._NameSpace;
		InsertAllSQL = MarketingChannelsSchema._InsertAllSQL;
		UpdateAllSQL = MarketingChannelsSchema._UpdateAllSQL;
		FillAllSQL = MarketingChannelsSchema._FillAllSQL;
		DeleteSQL = MarketingChannelsSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new MarketingChannelsSet();
	}

	public boolean add(MarketingChannelsSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(MarketingChannelsSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(MarketingChannelsSchema aSchema) {
		return super.remove(aSchema);
	}

	public MarketingChannelsSchema get(int index) {
		MarketingChannelsSchema tSchema = (MarketingChannelsSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, MarketingChannelsSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(MarketingChannelsSet aSet) {
		return super.set(aSet);
	}
}
 