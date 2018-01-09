package com.sinosoft.cms.site;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZCImagePlayerSchema;

/**
 * 图片播放器预览
 */
public class ImagePlayerPreview extends Page {

	public static Mapx init(Mapx params) {
		String s = (String) params.get("ImagePlayerID");
		if (StringUtil.isEmpty(s)) {
			return null;
		}
		long ImagePlayerID = Long.parseLong(params.get("ImagePlayerID").toString());
		ZCImagePlayerSchema imagePlayer = new ZCImagePlayerSchema();
		imagePlayer.setID(ImagePlayerID);
		if (imagePlayer.fill()) {
			if ("01".equals(imagePlayer.getRemark1())||"03".equals(imagePlayer.getRemark1())) {
				params.put("_SWFObject", PubFun.getImagePlayer(imagePlayer));
			} else {
				params.put("_SWFObject", PubFun.getImagePlayer1(imagePlayer));
			}
		}
		return params;
	}
}
