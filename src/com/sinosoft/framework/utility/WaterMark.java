package com.sinosoft.framework.utility;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

/**
 * 水印工具类
 * 
 * @author lanjun
 * 
 */
public class WaterMark {
	private static final Logger logger = LoggerFactory.getLogger(WaterMark.class);

	private String imageFile;

	private String fontName;

	private Color fontColor;

	private int fontSize;

	public int imageHeight;

	public int imageWidth;

	public WaterMark() {
		imageFile = "";
		fontName = "宋体";
		fontColor = Color.RED;
		fontSize = 14;
		imageHeight = 0;
		imageWidth = 0;
	}

	public void setFont(String fontName, int fontColor, int fontSize) {
		this.fontName = fontName;
		this.fontColor = new Color(fontColor);
		this.fontSize = fontSize;
	}

	public void openFile(String imgf) {
		imageFile = imgf;
		if (imageFile.equals(""))
			return;
		try {
			File _file = new File(imageFile);
			Image src = ImageIO.read(_file);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			imageWidth = wideth;
			imageHeight = height;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void draw(String text, int position, int ws) throws Exception {
		if (imageFile.equals(""))
			return;
		try {
			File img = new File(imageFile);
			Image src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			int left = 0;
			int top = 0;
			BufferedImage image = new BufferedImage(width, height, 1);
			Graphics g = image.createGraphics();

			g.drawImage(src, 0, 0, width, height, null);
			g.setColor(fontColor);
			g.setFont(new Font(fontName, 0, fontSize));
			int strlen = text.getBytes().length / 2;
			if (ws < 0)
				ws = 8;

			// 左上 右上 居中 左下 右下
			switch (position) {
			case 1:
				left = ws;
				top = fontSize + ws;
				break;

			case 2:
				left = width - strlen * fontSize - ws;
				top = fontSize + ws;
				break;

			case 3:
				left = (width - strlen * fontSize) / 2;
				top = (height - fontSize) / 2 + ws;
				break;

			case 4:
				left = ws;
				top = height - ws;
				break;

			case 5:
				left = width - strlen * fontSize - ws;
				top = height - ws;
				break;

			default:
				left = width - strlen * fontSize - ws;
				top = height - ws;
				break;
			}
			if (left < 0)
				left = 0;
			if (top < 0)
				top = 0;
			g.drawString(text, left, top);
			g.dispose();
			FileOutputStream out = new FileOutputStream(imageFile);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			out.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void main(String[] args) {
		WaterMark waterMark = new WaterMark();
		waterMark.openFile("f:/test.jpg");
		try {
			waterMark.draw("中科软软件", 5, 8);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}
	}
}
