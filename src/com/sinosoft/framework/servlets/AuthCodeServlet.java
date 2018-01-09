package com.sinosoft.framework.servlets;

import com.sinosoft.framework.Constant;
import com.sinosoft.framework.User;
import com.sinosoft.framework.User.UserData;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class AuthCodeServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(AuthCodeServlet.class);

	private static final long serialVersionUID = 20060808L;

	private static final String CONTENT_TYPE = "image/jpeg";

	private static final int DEFAULT_WIDTH = 50;

	private static final int DEFAULT_HEIGHT = 14;

	private static final int DEFAULT_LENGTH = 4;

	public static final String DEFAULT_CODETYPE = "2";// 默认为标准模式

	private static String CodeType;

	private static String AuthKey;

	private static int Width;

	private static int Height;

	private static int Length;

	private static OutputStream out;

	private static Random rand = new Random(System.currentTimeMillis());

	private static String seed;

	private static BufferedImage image;
	
	private static Object mutex = new Object();

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		generate(request, response);
	}

	public static void generate(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		synchronized (mutex) {
			try {
				CodeType = request.getParameter("CodeType");
				AuthKey = request.getParameter("AuthKEY");
				String tWidth = request.getParameter("Width");
				String tHeight = request.getParameter("Height");
				String tLength = request.getParameter("Length");
				if (CodeType == null || CodeType.equals("")) {
					CodeType = DEFAULT_CODETYPE;
				}
				if (AuthKey == null || AuthKey.equals("")) {
					AuthKey = Constant.DefaultAuthKey;
				}
				if (tWidth == null || tWidth.equals("")) {
					Width = DEFAULT_WIDTH;
				}
				if (tHeight == null || tHeight.equals("")) {
					Height = DEFAULT_HEIGHT;
				}
				if (tLength == null || tLength.equals("")) {
					Length = DEFAULT_LENGTH;
				}
				try {
					Width = Integer.parseInt(tWidth);
				} catch (Exception ex) {
					Width = DEFAULT_WIDTH;
				}
				try {
					Height = Integer.parseInt(tHeight);
				} catch (Exception ex) {
					Height = DEFAULT_HEIGHT;
				}
				try {
					Length = Integer.parseInt(tLength);
				} catch (Exception ex) {
					Length = DEFAULT_LENGTH;
				}
				response.setContentType(CONTENT_TYPE);
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);

				out = response.getOutputStream();
				seed = getSeed();
				Object o = request.getSession().getAttribute(Constant.UserAttrName);
				if ("Comment".equals(request.getParameter("type")))
				{
					request.getSession().setAttribute("CommentYZM", seed);
				}
				if (o != null) {
					User.setCurrent((UserData) o);
					User.setValue(AuthKey, seed);
				} else {
					UserData u = new UserData();
					User.setCurrent(u);
					User.setValue(AuthKey, seed);
					request.getSession().setAttribute(Constant.UserAttrName, u);
				}

				if (CodeType.equals("1")) {
					code1(request, response);
				} else if (CodeType.equals("2")) {
					code2(request, response);
				} else if (CodeType.equals("3")) {
					code3(request, response);
				}
				try {
					JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
					JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(image);
					param.setQuality(1.0f, false);
					encoder.setJPEGEncodeParam(param);
					encoder.encode(image);
				} catch (Exception ex) {
					logger.error(ex.getMessage(), ex);
				}
				out.close();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 简易模式
	 */
	private static BufferedImage code1(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		image = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setColor(new Color(245, 245, 245));
		g.fillRect(0, 0, Width, Height);
		g.setColor(Color.DARK_GRAY);
		g.setFont(new Font("Arial", Font.PLAIN, 12));

		g.drawString(seed, 3, 11);
		g.dispose();
		return image;
	}

	/**
	 * 标准模式
	 */
	private static BufferedImage code2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		image = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, Width, Height);
		g.setFont(new Font("Arial", Font.PLAIN, new Double(Height * 1.0 / 14 * 12).intValue()));
		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 80; i++) {
			// int x = rand.nextInt(Width);
			// int y = rand.nextInt(Height);
			// int xl = rand.nextInt(12);
			// int yl = rand.nextInt(12);
			// g.drawLine(x, y, x + xl, y + yl);
		}
		// 取随机产生的认证码
		for (int i = 0; i < Length; i++) {
			String c = seed.substring(i, i + 1);
			g.setColor(new Color(20 + rand.nextInt(110), 20 + rand.nextInt(110), 20 + rand.nextInt(110)));// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成

			g.drawString(c, new Double(Width * 1.0 / 50 * 11).intValue() * i
					+ new Double(Width * 1.0 / 50 * 3).intValue(), new Double(Height * 1.0 / 14 * 11).intValue());
		}
		g.dispose();
		return image;
	}

	/**
	 * 复杂模式，文本随机倾斜
	 */
	private static BufferedImage code3(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		image = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, Width, Height);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = rand.nextInt(Width);
			int y = rand.nextInt(Height);
			int xl = rand.nextInt(12);
			int yl = rand.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		// 取随机产生的认证码
		AffineTransform fontAT = new AffineTransform();
		for (int i = 0; i < Length; i++) {
			String c = seed.substring(i, i + 1);
			g.setColor(new Color(20 + rand.nextInt(110), 20 + rand.nextInt(110), 20 + rand.nextInt(110)));// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			fontAT.shear(rand.nextFloat() * 0.6 - 0.3, 0.0);
			FontRenderContext frc = g.getFontRenderContext();
			Font theDerivedFont = g.getFont().deriveFont(fontAT);
			TextLayout tstring2 = new TextLayout(c, theDerivedFont, frc);// 绘制普通字符串
			tstring2.draw(g, (float) (7 * i + 2), (float) 11);// 绘制倾斜字体字符串
		}
		g.dispose();
		return image;
	}

	static char[] arr = "23456789qwertyuipasdfghjkzxcvbnm".toCharArray();

	private static String getSeed() {
		StringBuffer sb = new StringBuffer(Length);
		for (int i = 0; i < Length; i++) {
			sb.append(arr[rand.nextInt(arr.length)]);
		}
		// System.out.println(sb);
		return sb.toString();
	}

	private static Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + rand.nextInt(bc - fc);
		int g = fc + rand.nextInt(bc - fc);
		int b = fc + rand.nextInt(bc - fc);

		/* ${_ZVING_LICENSE_CODE_} */
		return new Color(r, g, b);
	}
}
