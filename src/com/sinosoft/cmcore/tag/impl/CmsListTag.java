package com.sinosoft.cmcore.tag.impl;

import com.sinosoft.cmcore.tag.IterativeTag;

public class CmsListTag extends IterativeTag {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.cmcore.tag.TagBase#getAllAttributeNames()
	 */
	public String[] getAllAttributeNames() {
		return new String[] { "item", "type", "page", "count", "condition" };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.cmcore.tag.TagBase#getMandantoryAttributeNames()
	 */
	public String[] getMandantoryAttributeNames() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.cmcore.tag.TagBase#getPrefix()
	 */
	public String getPrefix() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.cmcore.tag.TagBase#getTagName()
	 */
	public String getTagName() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.cmcore.tag.TagBase#onTagEnd()
	 */
	public int onTagEnd() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.cmcore.tag.TagBase#onTagStart()
	 */
	public int onTagStart() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.cmcore.tag.TagBase#prepareNext()
	 */
	public boolean prepareNext() {
		return false;
	}

}
