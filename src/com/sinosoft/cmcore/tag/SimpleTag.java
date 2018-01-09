package com.sinosoft.cmcore.tag;

public abstract class SimpleTag extends TagBase {
	public boolean isIterative() {
		return false;
	}

	public int onTagEnd() {
		return TagBase.CONTINUE;
	}

	public boolean prepareNext() {
		return false;
	}

}
