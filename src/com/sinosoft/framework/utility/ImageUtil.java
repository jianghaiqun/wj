package com.sinosoft.framework.utility;

import java.awt.Dimension;
import java.io.IOException;

import magick.MagickException;

import com.sinosoft.framework.Config;


public class ImageUtil {
	/**
	 * @param args
	 * @throws IOException
	 * @throws MagickException
	 */
	public static void main(String[] args) throws IOException, MagickException {

	}

	public static boolean existsImageMagick() {
		if ("1".equals(Config.getValue("ImageLibType"))) {
			return true;
		} else {
			return false;
		}
	}

	public static Dimension getDimension(String fileName) throws MagickException, IOException {
		/* ${_ZVING_LICENSE_CODE_} */

		if (existsImageMagick()) {
			return ImageMagickUtil.getDimension(fileName);
		} else {
			return ImageJDKUtil.getDimension(fileName);
		}
	}

	public static void scaleRateImageFile(String fromFileName, String toFileName, int toWidth, int toHeight)
			throws IOException, MagickException {
		if (existsImageMagick()) {
			ImageMagickUtil.scaleRateImageFile(fromFileName, toFileName, toWidth, toHeight);
		} else {
			ImageJDKUtil.scaleRateImageFile(fromFileName, toFileName, toWidth, toHeight);
		}
	}

	public static void scaleRateImageFile(String fromFileName, String toFileName, double rate) throws MagickException,
			IOException {
		if (existsImageMagick()) {
			ImageMagickUtil.scaleRateImageFile(fromFileName, toFileName, rate);
		} else {
			ImageJDKUtil.scaleRateImageFile(fromFileName, toFileName, rate);
		}
	}

	public static void scaleFixedImageFile(String fromFileName, String toFileName, int toWidth, int toHeight)
			throws MagickException, IOException {
		if (existsImageMagick()) {
			ImageMagickUtil.scaleFixedImageFile(fromFileName, toFileName, toWidth, toHeight);
		} else {
			ImageJDKUtil.scaleFixedImageFile(fromFileName, toFileName, toWidth, toHeight);
		}
	}

	public static void pressText(String filePath, String pressText, int color, int fontSize, int position) {
		if (existsImageMagick()) {
			ImageMagickUtil.pressText(filePath, pressText, color, fontSize, position);
		} else {
			ImageJDKUtil.pressText(filePath, pressText, color, fontSize, position);
		}
	}

	public final static void pressImage(String targetImg, String pressImg, int position) {
		if (existsImageMagick()) {
			ImageMagickUtil.pressImage(targetImg, pressImg, position);
		} else {
			ImageJDKUtil.pressImage(targetImg, pressImg, position);
		}
	}

}
