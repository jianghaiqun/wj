package com.sinosoft.datachannel;

import com.sinosoft.cms.plan.activity.qixi.DataTableHandler;
import com.sinosoft.cms.plan.activity.qixi.ImageHandler;
import com.sinosoft.cms.plan.activity.qixi.QiXiDataTable;
import com.sinosoft.cms.plan.activity.qixi.SendMessage;
import com.sinosoft.cms.plan.activity.qixi.SendMessageHandler;
import com.sinosoft.cms.resource.uploader.OSSUploadFile;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

/**
 *
 * Created by dongsheng on 2017/7/11.
 */
public class QiXiFestivalScheduleManager extends ConfigEanbleTaskManager {

	public QiXiFestivalScheduleManager() {

		sendMessage = new SendMessage();
		dataTable = new QiXiDataTable();

	}

	public static final String TAG = QiXiFestivalScheduleManager.class.getSimpleName();
	private SendMessageHandler sendMessage;
	private DataTableHandler dataTable;

	@Override
	public String getID() {

		return TAG;
	}

	@Override
	public String getName() {

		return "七夕节活动定时任务";
	}

	@Override
	public String getCode() {

		return TAG;
	}

	@Override
	public boolean isRunning(String paramString) {

		return false;
	}

	@Override
	public Mapx getConfigEnableTasks() {

		Mapx<String, String> map = new Mapx<String, String>();
		map.put("0", "发送节日证书短息");
		return map;
	}

	@Override
	public void execute(String paramString) {

		int route = Integer.parseInt(paramString);
		switch (route) {
		case 0:
			executeRoute0();
			break;
		default:
			break;
		}
	}

	private void executeRoute0() {

		String contextRealPath = Config.getContextRealPath();
		String resPath = Config.getValue("StaticResourcePath");
		String folderPath = contextRealPath.concat("Images/activity/");
		String imgPath = folderPath.concat("love.jpg");
		String watermarkPath = folderPath.concat("logo.png");

		DataTable dataTable = findPolicyListBy();
		String policyNo;
		String recognizeeName;
		String applicantName;
		String applicantIdentityId;
		String applicantMobile;
		Date svaliDate;
		for (DataRow dataRow : dataTable) {
			ImageHandler imageHandler = ImageHandler.getInstance(imgPath);
			policyNo = dataRow.getString(0);
			policyNo = policyNo == null ? null : replace(policyNo, '*', policyNo.length() - 6, policyNo.length());
			recognizeeName = dataRow.getString(1);
			applicantName = dataRow.getString(2);
			applicantMobile = dataRow.getString(3);
			applicantIdentityId = dataRow.getString(4);
			applicantIdentityId = applicantIdentityId == null ? null : replace(applicantIdentityId, '*', 8, 14);
			svaliDate = dataRow.getDate(5);
			imageHandler.modifyImage(policyNo, 220, 370, 23);
			imageHandler.modifyImage(recognizeeName, 430, 450, 23);
			imageHandler.modifyImage(applicantName, 430, 500, 23);
			imageHandler.modifyImage(applicantIdentityId, 320, 550, 30);
			imageHandler.modifyImage(DateFormatUtils.format(svaliDate, "yyyy年MM月dd日"), 500, 1595, 27);
			// 修改默认参数
			imageHandler.setImageMarkOptions(0.0f, 0, 20);
			String fileName = randomFileName();
			try {
				File file = File.createTempFile("qixi-festival", ".jpg");
				imageHandler.writeImageLocal(file);
				OSSUploadFile.upload(new
						FileInputStream(file), fileName);
				if (StringUtil.isMobileNO(applicantMobile))
					sendMessage.addMsg(applicantMobile,
							"说我爱你很容易,说我愿意很难,今天我却许下了一个承诺,3年后我们不见不散 ".concat(resPath + "/" + fileName));
				logger.info("爱情证书链接地址:{}", resPath +"/"+ fileName);

			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				break;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				break;
			}
			sendMessage.sendMessage();

		}

	}

	private String randomFileName() {

		String str0 = "l/";
		String str1 = RandomStringUtils.randomAlphanumeric(6);
		String str2 = ".jpg";
		return str0 + str1 + str2;
	}

	private String replace(String src, char c, int from, int to) {

		if (from > to) {
			int temp = from;
			from = to;
			to = temp;
		}
		if (from < 0)
			from = 0;
		if (to > src.length())
			to = src.length();

		String b = src.substring(from, to);
		char[] chars = new char[to - from];
		Arrays.fill(chars, c);
		return b = src.replace(b, new String(chars));
	}

	private void createImage() {

	}

	private DataTable findPolicyListBy() {

		return dataTable.findDataList();
	}

	private boolean sendMessage() {

		return sendMessage.sendMessage();
	}

	private String handleImage() {

		return null;
	}

	public static void main(String[] args) {

		QiXiFestivalScheduleManager q = new QiXiFestivalScheduleManager();
		q.randomFileName();

	}
}
