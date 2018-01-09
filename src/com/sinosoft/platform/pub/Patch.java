package com.sinosoft.platform.pub;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.BlockingTransaction;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.utility.ZipUtil;
import com.sinosoft.schema.ZDCodeSet;
import com.sinosoft.schema.ZDConfigSchema;
import com.sinosoft.schema.ZDIPRangeSet;
import com.sinosoft.schema.ZDMenuSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class Patch {
	private static final Logger logger = LoggerFactory.getLogger(Patch.class);

	private static Object mutex = new Object();

	public synchronized static void checkUpdate() {
		if (!Config.isInstalled) {
			return;
		}
		if (!Config.isNeedCheckPatch || Config.isPatching) {
			return;
		}
		synchronized (mutex) {
			if (!Config.isNeedCheckPatch || Config.isPatching) {
				return;
			}
			Config.isNeedCheckPatch = false;
			Config.isPatching = true;
		}
		BlockingTransaction tran = null;
		try {
			tran = new BlockingTransaction();
			String version = new QueryBuilder("select Value from ZDConfig where Type=?", "System.Version")
					.executeString();
			float mainVersion = 0;
			float minorVersion = 0;
			if (StringUtil.isEmpty(version)) {
				mainVersion = 1.3f;
				minorVersion = 0.5f;
			} else {
				String[] arr = version.split("\\.");
				mainVersion = Float.parseFloat(arr[0] + "." + arr[1]);
				if (arr.length == 4) {
					minorVersion = Float.parseFloat(arr[2] + "." + arr[3]);
				} else {
					minorVersion = Float.parseFloat(arr[2]);
				}
			}
			if (mainVersion < 1.3 && minorVersion < 1.0) {// 1.3.1之下需要更新菜单
				updateTo1_3_1(tran);
			}
			tran.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			if (tran != null) {
				tran.rollback();
			}
		} finally {
			Config.isPatching = false;
		}
	}

	public static void updateTo1_3_1(BlockingTransaction tran) {
		try {
			tran
					.addWithException(new QueryBuilder(
							"INSERT INTO zdconfig  VALUES ('RewriteIndex','是否将站点首页重新生成index.html','N',NULL,NULL,NULL,NULL,NULL,'2010-01-18 14:35:52','admin',NULL,NULL)"));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		/* 投票验证码标志 2010-06-18 */
		try {
			tran
					.addWithException(new QueryBuilder(
							"update zdcode set codevalue ='Y' where codetype = 'VerifyFlag' and parentcode ='VerifyFlag' and codevalue ='S'"));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		/* 栏目树设置功能和文章来源管理功能增加数据 2010-06-22 */
		try {
			tran
					.addWithException(new QueryBuilder(
							"INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('ArticleCatalogLoadType','文章栏目树延迟加载方式','AllChild',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:03:06','admin',NULL,NULL)"));
			tran
					.addWithException(new QueryBuilder(
							"INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('ArticleCatalogShowLevel','文章栏目树默认展开层级','2',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:01:26','admin',NULL,NULL)"));
			tran
					.addWithException(new QueryBuilder(
							"INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('AttachLibLoadType','附件库栏目树延迟加载方式','AllChild',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:20:52','admin',NULL,NULL)"));
			tran
					.addWithException(new QueryBuilder(
							"INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('AttachLibShowLevel','附件库栏目树默认展开层级','2',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:19:39','admin',NULL,NULL)"));
			tran
					.addWithException(new QueryBuilder(
							"INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('AudioLibLoadType','音频库栏目树延迟加载方式','AllChild',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:22:32','admin',NULL,NULL)"));
			tran
					.addWithException(new QueryBuilder(
							"INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('AudioLibShowLevel','音频库栏目树默认展开层级','2',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:22:11','admin',NULL,NULL)"));
			tran
					.addWithException(new QueryBuilder(
							"INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('ImageLibLoadType','图片库栏目树延迟加载方式','AllChild',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:14:16','admin',NULL,NULL)"));
			tran
					.addWithException(new QueryBuilder(
							"INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('ImageLibShowLevel','图片库栏目树默认展开层级','2',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:06:30','admin',NULL,NULL)"));
			tran
					.addWithException(new QueryBuilder(
							"INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('VideoLibLoadType','视频库栏目树延迟加载方式','AllChild',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:21:47','admin',NULL,NULL)"));
			tran
					.addWithException(new QueryBuilder(
							"INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('VideoLibShowLevel','视频库栏目树默认展开层级','2',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:21:19','admin',NULL,NULL)"));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		/** 将article的prop字段转移回pageTitle 2010-06-23 */
		try {
			tran.addWithException(new QueryBuilder("update zcarticle set pageTitle=prop1 where prop1 is not null"));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		/* 将统计分析中区域统计单立出来 2010-06-23 by wyuch */
		try {
			tran.addWithException(new QueryBuilder(
					"update ZCStatItem set Type='District',SubType='PV' where Type='Client' and SubType='District'"));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		// 增加工作台配置的菜单
		ZDMenuSchema menu = new ZDMenuSchema();
		menu.setID(NoUtil.getMaxID("MenuID"));
		menu.setParentID(122);
		menu.setType("2");
		menu.setName("工作台配置");
		menu.setURL("Site/WorkBenchConfig.jsp");
		menu.setVisiable("Y");
		menu.setIcon("Icons/icon001a7.gif");
		menu.setOrderFlag("14");
		menu.setAddTime(new Date());
		menu.setAddUser("SYSTEM");
		tran.add(menu, Transaction.INSERT);

		// 加入新的IPRanges和ArticleRefer
		tran.add(new QueryBuilder("delete from ZDCode where CodeType=?", "ArticleRefer"));
		byte[] bs = FileUtil.readByte(Config.getContextRealPath() + "WEB-INF/data/installer/1.3.1.ArticleRefer.dat");
		bs = ZipUtil.unzip(bs);
		ZDCodeSet codeSet = (ZDCodeSet) FileUtil.unserialize(bs);
		tran.add(codeSet, Transaction.INSERT);
		tran.add(new QueryBuilder("delete from ZDIPRange"));

		bs = FileUtil.readByte(Config.getContextRealPath() + "WEB-INF/data/installer/1.3.1.IPRanges.dat");
		bs = ZipUtil.unzip(bs);
		ZDIPRangeSet ipSet = (ZDIPRangeSet) FileUtil.unserialize(bs);
		tran.add(ipSet, Transaction.INSERT);

		// 更新会员配置菜单
		try {
			tran
					.addWithException(new QueryBuilder(
							"UPDATE zdmenu set Name = '会员配置',URL='DataService/MemberConfig.jsp',Icon='Icons/icon025a6.gif' where Name = '会员等级'"));
			tran
					.addWithException(new QueryBuilder(
							"INSERT INTO zdmemberlevel (ID,Name,Type,Discount,IsDefault,TreeLevel,Score,IsValidate,AddUser,AddTime,ModifyUser,ModifyTime) VALUES (31,'注册会员','用户',1,'Y',0,0,'Y','admin','2010-06-29 18:50:45',NULL,NULL)"));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002049000001",
				"000020004900000001"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002049000002",
				"000020004900000002"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002049000003",
				"000020004900000003"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002049000004",
				"000020004900000004"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002049000005",
				"000020004900000005"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002049000006",
				"000020004900000006"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002049000007",
				"000020004900000008"));

		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002164000001",
				"000021006400000001"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002164000002",
				"000021006400000002"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002164000003",
				"000021006400000003"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002164000004",
				"000021006400000004"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002164000005",
				"000021006400000005"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002164000006",
				"000021006400000006"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002164000007",
				"000021006400000007"));

		tran
				.add(new QueryBuilder(
						"update ZCArticle set CatalogInnerCode=(select InnerCode from ZCCatalog where ID=ZCArticle.CatalogID)"));

		// 更新版本号
		try {
			ZDConfigSchema config = new ZDConfigSchema();
			config.setType("System.Version");
			config.setName("当前版本");
			config.setValue("1.3.1.0");
			config.setAddTime(new Date());
			config.setAddUser("SYSTEM");
			tran.add(config, Transaction.DELETE_AND_INSERT);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void main(String[] args) {
		Transaction tran = new Transaction();

		// tran.add(new QueryBuilder("delete from ZDCode where CodeType=?",
		// "ArticleRefer"));
		// byte[] bs = FileUtil.readByte(Config.getContextRealPath() +
		// "WEB-INF/data/installer/1.3.1.ArticleRefer.dat");
		// bs = ZipUtil.unzip(bs);
		// ZDCodeSet codeSet = (ZDCodeSet) FileUtil.unserialize(bs);
		// tran.add(codeSet, Transaction.INSERT);
		// tran.add(new QueryBuilder("delete from ZDIPRange"));
		//
		// bs = FileUtil.readByte(Config.getContextRealPath() +
		// "WEB-INF/data/installer/1.3.1.IPRanges.dat");
		// bs = ZipUtil.unzip(bs);
		// ZDIPRangeSet ipSet = (ZDIPRangeSet) FileUtil.unserialize(bs);
		// tran.add(ipSet, Transaction.INSERT);

		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002049000001",
				"000020004900000001"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002049000002",
				"000020004900000002"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002049000003",
				"000020004900000003"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002049000004",
				"000020004900000004"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002049000005",
				"000020004900000005"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002049000006",
				"000020004900000006"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002049000007",
				"000020004900000008"));

		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002164000001",
				"000021006400000001"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002164000002",
				"000021006400000002"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002164000003",
				"000021006400000003"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002164000004",
				"000021006400000004"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002164000005",
				"000021006400000005"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002164000006",
				"000021006400000006"));
		tran.add(new QueryBuilder("update ZCCatalog set InnerCode=? where InnerCode=?", "002164000007",
				"000021006400000007"));

		tran
				.add(new QueryBuilder(
						"update ZCArticle set CatalogInnerCode=(select InnerCode from ZCCatalog where ID=ZCArticle.CatalogID)"));

		tran.commit();
	}
}
