package com.sinosoft.schema;

import com.sinosoft.schema.JobsInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class JobsInfoSet extends SchemaSet {
	public JobsInfoSet() {
		this(10,0);
	}

	public JobsInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public JobsInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = JobsInfoSchema._TableCode;
		Columns = JobsInfoSchema._Columns;
		NameSpace = JobsInfoSchema._NameSpace;
		InsertAllSQL = JobsInfoSchema._InsertAllSQL;
		UpdateAllSQL = JobsInfoSchema._UpdateAllSQL;
		FillAllSQL = JobsInfoSchema._FillAllSQL;
		DeleteSQL = JobsInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new JobsInfoSet();
	}

	public boolean add(JobsInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(JobsInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(JobsInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public JobsInfoSchema get(int index) {
		JobsInfoSchema tSchema = (JobsInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, JobsInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(JobsInfoSet aSet) {
		return super.set(aSet);
	}
}
 