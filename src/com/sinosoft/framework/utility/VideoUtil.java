package com.sinosoft.framework.utility;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.controls.UploadStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 视频工具类，调用ffmpeg进行视频处理
 */
public class VideoUtil {
	private static final Logger logger = LoggerFactory.getLogger(VideoUtil.class);

	public static String taskid = "";
	public static String videoid = "";
	public static String srcPath = "";

	public static boolean captureDefaultImage(String src, String destImage, int duration) {
		if (duration < 30) {
			return captureImage(src, destImage, duration / 3);
		} else {
			return captureImage(src, destImage, 15);
		}
	}

	public static boolean captureImage(String src, String destImage, int startSecond) {
		return captureImage(src, destImage, startSecond, 240, 180);
	}

	public static boolean captureImage(String src, String destImage, int ss, int width, int height) {
		String fileName = "ffmpeg";
		if (Config.getOSName().toLowerCase().indexOf("windows") >= 0) {
			fileName = "\"" + Config.getContextRealPath() + "Tools/" + "ffmpeg.exe\" ";
			src = "\"" + src + "\"";
			destImage = "\"" + destImage + "\"";
		}
		return exec(fileName + " -i " + src + " -y -f image2 -ss " + ss + " -t 0.001 -s " + width + "*" + height + " "
				+ destImage, null, Config.getContextRealPath() + "Tools/");
	}

	public final static String _ConvertAvi2Flv = " -of lavf -oac mp3lame -lameopts abr:br=56 -ovc lavc -lavcopts vcodec=flv:vbitrate=200:mbd=2:mv0:trell:v4mv:cbp:last_pred=3:dia=4:cmp=6:vb_strategy=1 -vf scale=512:-3 -ofps 12	 -srate 22050";

	public final static String _ConvertRm2Flv = " -of lavf -oac mp3lame -lameopts abr:br=56 -ovc lavc -lavcopts vcodec=flv:vbitrate=200:mbd=2:mv0:trell:v4mv:cbp:last_pred=3 -srate 22050";

	public static boolean convert2Flv(String src, String dest) {
		String fileName = "mencoder ";
		if (Config.getOSName().toLowerCase().indexOf("windows") >= 0) {
			fileName = "\"" + Config.getContextRealPath() + "Tools/" + "mencoder.exe\" ";
			src = "\"" + src + "\"";
			dest = "\"" + dest + "\"";
		}
		if (src.toLowerCase().lastIndexOf(".flv") != -1) {
			return true;
		} else if (src.toLowerCase().lastIndexOf(".rm") != -1 || src.toLowerCase().lastIndexOf(".rmvb") != -1) {
			return exec(fileName + src + " -o " + dest + " " + _ConvertRm2Flv, null, Config.getContextRealPath()
					+ "Tools/");
		} else {
			return exec(fileName + src + " -o " + dest + " " + _ConvertAvi2Flv, null, Config.getContextRealPath()
					+ "Tools/");
		}
	}

	public static boolean convert2FlvSlit(String src, String dest, int ss, int endpos) {
		String fileName = "mencoder ";
		if (Config.getOSName().toLowerCase().indexOf("windows") >= 0) {
			fileName = "\"" + Config.getContextRealPath() + "Tools/" + "mencoder.exe\" ";
			src = "\"" + src + "\"";
			dest = "\"" + dest + "\"";
		}
		if (src.toLowerCase().lastIndexOf(".rm") != -1 || src.toLowerCase().lastIndexOf(".rmvb") != -1) {
			return exec(fileName + src + " -o " + dest + " " + _ConvertRm2Flv, null, Config.getContextRealPath()
					+ "Tools/");
		} else {
			return exec(fileName + src + " -o " + dest + " -ss " + ss + " -endpos " + endpos + " " + _ConvertAvi2Flv,
					null, Config.getContextRealPath() + "Tools/");
		}
	}

	public final static String _Identify = " -nosound -vc dummy -vo null";

	public static int getDuration(String src) {
		String fileName = "mplayer ";
		if (Config.getOSName().toLowerCase().indexOf("windows") >= 0) {
			fileName = "\"" + Config.getContextRealPath() + "Tools/" + "mplayer.exe\" ";
			src = "\"" + src + "\"";
		}
		String command = fileName + " -identify " + src + " " + _Identify;
		int duration = 0;
		try {
			logger.info("Video.getDuration:{}", command);
			Process process = Runtime.getRuntime()
					.exec(command, null, new File(Config.getContextRealPath() + "Tools/")); // 调用外部程序

			InputStream is = process.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			try {
				while ((line = br.readLine()) != null) {
					if (line.indexOf("ID_LENGTH=") > -1) {
						duration = (int) Math.ceil(Double.parseDouble(line.substring(10)));
					}
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				return duration;
			}

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return duration;
		}
		logger.info("VodeoUtil duration:{}", duration);
		return duration;
	}

	public static int[] getWidthHeight(String src) {
		String fileName = "mplayer ";
		if (Config.getOSName().toLowerCase().toLowerCase().indexOf("windows") >= 0) {
			fileName = "\"" + Config.getContextRealPath() + "Tools/" + "mplayer.exe\" ";
			src = "\"" + src + "\"";
		}
		String command = fileName + " -identify " + src + " " + _Identify;
		int[] WidthHeight = new int[] { 0, 0 };
		try {
			logger.info("VideoUtil.getWidthHeight:{}", command);
			Process process = Runtime.getRuntime()
					.exec(command, null, new File(Config.getContextRealPath() + "Tools/")); // 调用外部程序

			InputStream is = process.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			try {
				while ((line = br.readLine()) != null) {
					if (line.indexOf("ID_VIDEO_WIDTH=") > -1) {
						WidthHeight[0] = (int) Math.ceil(Double.parseDouble(line.substring(15)));
					}
					if (line.indexOf("ID_VIDEO_HEIGHT=") > -1) {
						WidthHeight[1] = (int) Math.ceil(Double.parseDouble(line.substring(16)));
					}
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				return WidthHeight;
			}

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return WidthHeight;
		}
		logger.info("VideoUtil WidthHeight:{} x {}", WidthHeight[0], WidthHeight[1]);
		return WidthHeight;
	}

	public static boolean exec(String command, String[] args, String dir) {
		try {
			logger.info("VideoUtil.exec:{}", command);
			Process process = Runtime.getRuntime().exec(command, args, new File(dir)); // 调用外部程序

			final InputStream is1 = process.getInputStream();
			new Thread(new Runnable() {
				public void run() {
					BufferedReader br = new BufferedReader(new InputStreamReader(is1));
					String line = null;
					String task = taskid;
					String vid = videoid;
					String src = srcPath;
					try {
						while ((line = br.readLine()) != null) {
							UploadStatus.setTask(task, vid, "Video", src, line);
						}
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					}
				}
			}).start(); // 启动单独的线程来清空process.getInputStream()的缓冲区
			InputStream is2 = process.getErrorStream();
			BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
			StringBuffer buf = new StringBuffer(); // 保存输出结果流
			String line = null;
			while ((line = br2.readLine()) != null)
				buf.append(line); // 循环等待ffmpeg进程结束
			logger.info("VideoUtil 输出为：{}", buf);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
}
