package com.sinosoft.cms.plan.activity.qixi;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

public class ImageHandler {

	private static final Logger logger = LoggerFactory.getLogger(ImageHandler.class);
	/**
	 * 水印透明度
	 */
	private float alpha = 0.5f;
	/**
	 * 水印图片旋转角度
	 */
	private float degree = 0f;
	/**
	 * 水印间隔
	 */
	private int interval = 0;

	private int fontSize = 0;

	private int x = 0;

	private int y = 0;

	private BufferedImage srcImage;

	private ImageHandler(String imagePath) {

		try {
			this.srcImage = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			this.srcImage = null;
		}
	}

	public static ImageHandler getInstance(String imagePath) {

		return new ImageHandler((imagePath));
	}

	/**
	 * 生成新图片到本地
	 */
	public void writeImageLocal(String newImagePath) {

		if (newImagePath != null) {
			try {
				File outputfile = new File(newImagePath);
				ImageIO.write(this.srcImage, "jpg", outputfile);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 生成新图片到本地
	 */
	public void writeImageLocal(File file) {

		if (file != null) {
			try {
				ImageIO.write(this.srcImage, "jpg", file);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 修改图片,返回修改后的图片缓冲区（只输出一行文本）
	 */
	public void modifyImage(Object content, int x, int y, int size) {

		try {
			int w = this.srcImage.getWidth();
			int h = this.srcImage.getHeight();
			Graphics2D g = this.srcImage.createGraphics();
			g.setBackground(Color.white);
			g.setColor(Color.gray);// 设置字体颜色
			Font font = new Font("宋体", Font.BOLD, size);
			g.setFont(font);

			// 验证输出位置的纵坐标和横坐标
			if (x >= w || y >= h) {
				this.x = h - this.fontSize + 2;
				this.y = w;
			} else {
				this.x = x;
				this.y = y;
			}
			if (content != null) {
				g.drawString(content.toString(), this.x, this.y);
			}
			g.dispose();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 设置水印参数，不设置就使用默认值
	 *
	 * @param alpha
	 *            水印透明度
	 * @param degree
	 *            水印图片旋转角度
	 * @param interval
	 *            水印图片间隔
	 */
	public void setImageMarkOptions(float alpha, int degree, int interval) {

		if (alpha != 0.0f) {
			this.alpha = alpha;
		}
		if (degree != 0f) {
			this.degree = degree;
		}
		if (interval != 0f) {
			this.interval = interval;
		}

	}

	/**
	 * 给图片添加水印图片、可设置水印图片旋转角度
	 *
	 * @param waterImgPath
	 *            水印图片路径
	 * @param degree
	 *            水印图片旋转角度
	 */
	public void waterMarkByImg(String waterImgPath, double degree) throws Exception {

		OutputStream os = null;
		try {
			BufferedImage bufferedImage = new BufferedImage(srcImage.getWidth(null),
					srcImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
			// 1、得到画笔对象
			Graphics2D g = bufferedImage.createGraphics();

			// 2、设置对线段的锯齿状边缘处理
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(srcImage.getScaledInstance(srcImage.getWidth(null),
					srcImage.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);

			// 4、水印图片的路径 水印图片一般为gif或者png的，这样可设置透明度
			ImageIcon imgIcon = new ImageIcon(waterImgPath);

			// 5、得到Image对象。
			Image img = imgIcon.getImage();

			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			int max = Math.max(srcImage.getHeight(), srcImage.getWidth());

			// 6、水印图片的位置
			for (int y = -interval; y < srcImage
					.getHeight() + imgIcon.getIconHeight(); y = y + interval * 4 + imgIcon.getIconHeight()) {
				for (int x = -interval; x < srcImage
						.getWidth() + imgIcon.getIconWidth(); x = x + interval + imgIcon.getIconWidth()) {
					AffineTransform transform = new AffineTransform();
					transform.translate(x, y);
					transform.rotate(degree * Math.PI / 180);
					g.drawImage(img, transform, null);
				}
			}
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			// 7、释放资源
			g.dispose();
			logger.info("图片完成添加水印图片");
			this.srcImage = bufferedImage;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		String imgPath = "D:/l/love.jpg";
		String watermarkPath = "D:/l/logo.png";
		String targerPath = "D:/l/target.jpg";

		ImageHandler imageHandler = ImageHandler.getInstance(imgPath);
		imageHandler.modifyImage("201-1-594-16-000045******", 220, 370, 23);
		imageHandler.modifyImage("李四李", 430, 450, 23);
		imageHandler.modifyImage("王五王", 430, 500, 23);
		imageHandler.modifyImage("21072619******4171", 320, 550, 30);
		imageHandler.modifyImage(DateFormatUtils.format(new Date(), "yyyy年MM月dd日"), 500, 1595, 27);
		// 给图片加入水印
		logger.info("..添加水印图片开始...");
		// 修改默认参数
		imageHandler.setImageMarkOptions(0.0f, 0,
				20);
		imageHandler.waterMarkByImg(watermarkPath, 45);
		logger.info("..添加水印图片结束...");
		imageHandler.writeImageLocal(targerPath);
	}

}
