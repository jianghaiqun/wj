package com.sinosoft.platform.pub;

import com.sinosoft.framework.utility.ImageUtil;
import com.sinosoft.framework.utility.VideoUtil;
import com.sinosoft.schema.ZCVideoSchema;

public class VideoUtilEx {
	public static boolean afterUploadVideo(ZCVideoSchema video, String AbsolutePath, boolean hasImage) throws NumberFormatException, Exception {
		int[] WidthHeight = VideoUtil.getWidthHeight(AbsolutePath + video.getSrcFileName());
		video.setWidth(WidthHeight[0]);
		video.setHeight(WidthHeight[1]);
		if (!"flv".equalsIgnoreCase(video.getSuffix())) {
			VideoUtil.convert2Flv(AbsolutePath + video.getSrcFileName(), AbsolutePath + video.getFileName());
		}

		video.setDuration(VideoUtil.getDuration(AbsolutePath + video.getFileName()));
		video.setCount(1);

		if (hasImage) {
			ImageUtil.scaleRateImageFile(AbsolutePath + video.getImageName(), AbsolutePath + video.getImageName(), 240, 240);
		} else {
			VideoUtil.captureDefaultImage(AbsolutePath + video.getFileName(), AbsolutePath + video.getImageName(),(int)video.getDuration());
		}
		return true;
	}

}
