package com.sinosoft.framework.utility;

import com.sinosoft.framework.utility.gif.GifEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 条形码生成类，目前仅支持39码
 * 
 */
public class BarCode {
	private static final Logger logger = LoggerFactory.getLogger(BarCode.class);

	// 输出文件类型
	public static String FORMAT_JPEG = "jpg";

	public static String FORMAT_GIF = "gif";

	public static String FORMAT_PNG = "png";

	public static String FORMAT_BMP = "bmp";

	// 条形码编码类型
	public static int TYPE_CODE39 = 1;

	private BufferedImage image = null;

	private String codeStr = ""; // 要编码的原始文本

	private String codeBinary = ""; // 转化成编码后的01串

	private String m_fileFormat = FORMAT_JPEG;

	private int codeType = TYPE_CODE39;

	private boolean isTextVisable = true; // 原始文本是否可见

	private String bgColor = "#FFFFFF";

	private String fgColor = "000000";

	private int xMargin = 10; // 条形码距图像边缘水平距离

	private int yMargin = 10; // 条形码距图像边缘垂直距离

	private int barHeight = 40; // 条形码码长度

	private int barWidth = 1; // 条形码码宽

	private int barRatio = 3; // 粗细宽度比值

	public BarCode(String code) {
		this.codeStr = code;
	}

	// 返回一个BufferedImage对象
	public BufferedImage getImage() {
		generatedImage();
		return image;
	}

	// 将条码图片写入到OutputStream中
	public void getOutputStream(OutputStream os) throws IOException {
		generatedImage();
		if (this.m_fileFormat.equals(FORMAT_GIF)) {
			try {
				GifEncoder gif = new GifEncoder(image);
				gif.Write(os);
			} catch (AWTException e) {
				logger.error(e.getMessage(), e);
			}
		} else {
			ImageIO.write(image, this.m_fileFormat, os);
		}
	}

	// 将条码图片写入到指定文件中
	public void writeToFile(String fileName) throws IOException {
		generatedImage();
		File f = new File(fileName);
		OutputStream os = new FileOutputStream(f);
		try {
			GifEncoder gif = new GifEncoder(image);
			gif.Write(os);
		} catch (AWTException e) {
			logger.error(e.getMessage(), e);
		}
		os.close();
	}

	// 将条码图片转换成字节数组
	public byte[] getBytes() throws IOException {
		generatedImage();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			GifEncoder gif = new GifEncoder(image);
			gif.Write(bos);
		} catch (AWTException e) {
			logger.error(e.getMessage(), e);
		}
		return bos.toByteArray();
	}

	// 生成条码图像
	public void generatedImage() {
		int w_Start = this.xMargin;
		int h_Start = this.yMargin;
		if (this.codeType == TYPE_CODE39) {
			this.codeBinary = Code39.transferCode(this.codeStr);
		}
		int barWidthTotal = 0;

		for (int i = 0; i < this.codeBinary.length(); i++) {
			if (this.codeBinary.charAt(i) == '1') {
				barWidthTotal += this.barRatio * this.barWidth;
			} else {
				barWidthTotal += this.barWidth;
			}
		}
		int imageWidth = this.xMargin * 2 + barWidthTotal;
		int imageHeight = h_Start + this.barHeight + (h_Start > 15 ? h_Start : 15);
		this.image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		setImageBgColor(image, this.bgColor);
		Graphics g = image.getGraphics();
		Font font = new Font(null, Font.PLAIN, 11); // 默认字体，11pt
		g.setFont(font);
		g.setColor(Color.decode(this.fgColor));
		for (int i = 0; i < this.codeBinary.length(); i++) {
			if (this.codeBinary.charAt(i) == '1') {
				if (i % 2 == 0) {
					g.fillRect(w_Start, h_Start, this.barRatio * this.barWidth, this.barHeight);
				}
				w_Start += this.barRatio * this.barWidth;
			} else {
				if (i % 2 == 0) {
					g.fillRect(w_Start, h_Start, this.barWidth, this.barHeight);
				}
				w_Start += this.barWidth;
			}
		}
		if (this.isTextVisable) { // 显示相应文本在条形码下方
			FontMetrics fonM = g.getFontMetrics();
			int yFont = fonM.getAscent() + fonM.getDescent();
			int xFont = fonM.stringWidth(this.codeStr);
			g.drawString(this.codeStr.toUpperCase(), imageWidth / 2 - xFont / 2, h_Start + yFont - 3 + this.barHeight);
		}
	}

	// 设置背景
	public void setBgColor(String str) {
		this.bgColor = str;
	}

	// 设置条码类型，目前只支持CODE39
	public void setCodeType(int type) {
		this.codeType = type;
	}

	// 设置输出图像格式
	public void setFormatType(String str) {
		this.m_fileFormat = str;
	}

	// 设置条码颜色
	public void setForeColor(String str) {
		this.fgColor = str;
	}

	// 设置条码的可读文字是否显示
	public void setTextVisable(boolean bFlag) {
		this.isTextVisable = bFlag;
	}

	// 设置水平边距
	public void setXMargin(int x) {
		this.xMargin = x;
	}

	// 设置垂直边距
	public void setYMargin(int y) {
		this.yMargin = y;
	}

	// 设置条码码宽
	public void setBarWidth(int bw) {
		this.barWidth = bw;
	}

	// 设置条码码长度
	public void setBarHeight(int bh) {
		this.barHeight = bh;
	}

	// 设置精细码元的宽度比
	public void setBarRatio(int br) {
		this.barRatio = br;
	}

	// 设置条码图像的背景色
	private static void setImageBgColor(BufferedImage image, String strColor) {
		Graphics g = image.getGraphics();
		String t_Str = new String(strColor);
		Color t_Color = g.getColor();
		if (!t_Str.startsWith("#")) {
			t_Str = "#" + t_Str;
		}
		if (t_Str.length() != 7) {
			logger.warn("BarCode:错误的颜色值{}", t_Str);
			return;
		}
		g.setColor(Color.decode(t_Str));
		g.fillRect(0, 0, image.getWidth(), image.getHeight());
		g.setColor(t_Color);
	}

//	W
}

class Code39 {
	private static final Logger logger = LoggerFactory.getLogger(Code39.class);

	private static String codeSet[][] = { { "000110100", "0" }, { "100100001", "1" }, { "001100001", "2" },
			{ "101100000", "3" }, { "000110001", "4" }, { "100110000", "5" }, { "001110000", "6" },
			{ "000100101", "7" }, { "100100100", "8" }, { "001100100", "9" }, { "100001001", "A" },
			{ "001001001", "B" }, { "101001000", "C" }, { "000011001", "D" }, { "100011000", "E" },
			{ "001011000", "F" }, { "000001101", "G" }, { "100001100", "H" }, { "001001100", "I" },
			{ "000011100", "J" }, { "100000011", "K" }, { "001000011", "L" }, { "101000010", "M" },
			{ "000010011", "N" }, { "100010010", "O" }, { "001010010", "P" }, { "000000111", "Q" },
			{ "100000110", "R" }, { "001000110", "S" }, { "000010110", "T" }, { "110000001", "U" },
			{ "011000001", "V" }, { "111000000", "W" }, { "010010001", "X" }, { "110010000", "Y" },
			{ "011010000", "Z" }, { "010000101", "-" }, { "110000100", "." }, { "011000100", " " },
			{ "010101000", "$" }, { "010100010", "/" }, { "010001010", "+" }, { "000101010", "%" },
			{ "010010100", "*" } };

	public static String getCode(char c) {
		for (int i = 0; i < 44; i++) {
			if (codeSet[i][1].charAt(0) == c) {
				return codeSet[i][0];
			}
		}
		return "";
	}

	public static String transferCode(String str) {
		StringBuffer bf = new StringBuffer();
		String t_Str = "";
		char[] cArray = str.toUpperCase().toCharArray();

		bf.append(getCode('*'));
		bf.append('0');
		for (int i = 0; i < cArray.length; i++) {
			t_Str = getCode(cArray[i]);
			if (!t_Str.equals("")) {
				bf.append(t_Str);
				bf.append('0');
			} else {
				logger.warn("Code39:非法字符");
				return "";
			}
		}
		bf.append(getCode('*'));
		return bf.toString();
	}
}
