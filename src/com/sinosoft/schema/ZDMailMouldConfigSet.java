package com.sinosoft.schema;

import com.sinosoft.schema.ZDMailMouldConfigSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ZDMailMouldConfigSet extends SchemaSet {
	public ZDMailMouldConfigSet() {
		this(10,0);
	}

	public ZDMailMouldConfigSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDMailMouldConfigSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDMailMouldConfigSchema._TableCode;
		Columns = ZDMailMouldConfigSchema._Columns;
		NameSpace = ZDMailMouldConfigSchema._NameSpace;
		InsertAllSQL = ZDMailMouldConfigSchema._InsertAllSQL;
		UpdateAllSQL = ZDMailMouldConfigSchema._UpdateAllSQL;
		FillAllSQL = ZDMailMouldConfigSchema._FillAllSQL;
		DeleteSQL = ZDMailMouldConfigSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDMailMouldConfigSet();
	}

	public boolean add(ZDMailMouldConfigSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDMailMouldConfigSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDMailMouldConfigSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDMailMouldConfigSchema get(int index) {
		ZDMailMouldConfigSchema tSchema = (ZDMailMouldConfigSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDMailMouldConfigSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDMailMouldConfigSet aSet) {
		return super.set(aSet);
	}
}
 