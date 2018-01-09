package com.sinosoft.schema;

import com.sinosoft.schema.PartnerPolicyReqSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class PartnerPolicyReqSet extends SchemaSet {
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = -1034769748337581690L;

	public PartnerPolicyReqSet() {
		this(10,0);
	}

	public PartnerPolicyReqSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public PartnerPolicyReqSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = PartnerPolicyReqSchema._TableCode;
		Columns = PartnerPolicyReqSchema._Columns;
		NameSpace = PartnerPolicyReqSchema._NameSpace;
		InsertAllSQL = PartnerPolicyReqSchema._InsertAllSQL;
		UpdateAllSQL = PartnerPolicyReqSchema._UpdateAllSQL;
		FillAllSQL = PartnerPolicyReqSchema._FillAllSQL;
		DeleteSQL = PartnerPolicyReqSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new PartnerPolicyReqSet();
	}

	public boolean add(PartnerPolicyReqSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(PartnerPolicyReqSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(PartnerPolicyReqSchema aSchema) {
		return super.remove(aSchema);
	}

	public PartnerPolicyReqSchema get(int index) {
		PartnerPolicyReqSchema tSchema = (PartnerPolicyReqSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, PartnerPolicyReqSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(PartnerPolicyReqSet aSet) {
		return super.set(aSet);
	}
}
 