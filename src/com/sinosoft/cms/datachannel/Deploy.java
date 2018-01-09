package com.sinosoft.cms.datachannel;

import com.jcraft.jsch.JSchException;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.cms.site.FileList;
import com.sinosoft.datachannel.CommonFtp;
import com.sinosoft.datachannel.SFtp;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.messages.LongTimeTask;
import com.sinosoft.framework.utility.Errorx;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCDeployConfigSchema;
import com.sinosoft.schema.ZCDeployConfigSet;
import com.sinosoft.schema.ZCDeployJobSchema;
import com.sinosoft.schema.ZCDeployJobSet;
import com.sinosoft.schema.ZCDeployLogSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Deploy {
	private static final Logger logger = LoggerFactory.getLogger(Deploy.class);

	public static final int READY = 0; // 就绪

	public static final int EXECUTION = 1; // 执行中

	public static final int SUCCESS = 2; // 成功

	public static final int FAIL = 3; // 失败

	public static final String TemplateDIR = "template";
	
	public static final String OPERATION_COPY = "copy"; // 复制
	
	public static final String OPERATION_DELETE = "delete"; // 删除

	public final static Mapx depolyStatus = new Mapx();

	static {
		depolyStatus.put(READY + "", "就绪");
		depolyStatus.put(EXECUTION + "", "执行中");
		depolyStatus.put(SUCCESS + "", "成功");
		depolyStatus.put(FAIL + "", "失败");
	}

	/**
	 * 根据配置项生成执行任务
	 * 
	 * @param configID
	 *            配置任务ID
	 * @param immediate
	 *            是否立即执行
	 * @return
	 */
	public boolean addOneJob(long configID, boolean immediate) {
		ZCDeployJobSchema job = new ZCDeployJobSchema();
		ZCDeployConfigSchema config = new ZCDeployConfigSchema();
		config.setID(configID);
		if (!config.fill()) {
			return false;
		}

		String staticDir = Config.getContextRealPath() + Config.getValue("Statical.TargetDir").replace('\\', '/');
		String sourcePath = staticDir + "/" + Application.getCurrentSiteAlias() + config.getSourceDir();
		job.setID(NoUtil.getMaxID("DeployJobID"));
		job.setConfigID(config.getID());
		job.setSource(sourcePath);
		job.setMethod(config.getMethod());

		String targetDir = config.getTargetDir();
		if (StringUtil.isEmpty(targetDir)) {
			targetDir = "/";
		} else {
			if (!targetDir.endsWith("/")) {
				targetDir += "/";
			}
		}

		job.setTarget(targetDir);
		job.setSiteID(config.getSiteID());
		job.setHost(config.getHost());
		job.setPort(config.getPort());
		job.setUserName(config.getUserName());
		job.setPassword(config.getPassword());
		job.setStatus(Deploy.READY);
		job.setAddTime(new Date());
		job.setAddUser(User.getUserName());

		Transaction trans = new Transaction();
		trans.add(job, Transaction.INSERT);
		if (trans.commit()) {
			if (immediate) {
				executeJob(config,job);
			}
			return true;
		} else {
			logger.error("添加部署任务时，数据库操作失败");
			return false;
		}
	}
	
	public boolean addJobs(long siteID, ArrayList list) {
		return addJobs(siteID, list, OPERATION_COPY);
	}
	
	public boolean addJobs(long siteID, ArrayList list, String operation) {
		ZCDeployJobSet set = getJobs(siteID, list, operation);
		Transaction trans = new Transaction();
		trans.add(set, Transaction.INSERT);
		if (trans.commit()) {
			//copyOuterDir();
			return true;
		} else {
			logger.error("添加部署任务时，数据库操作失败");
			return false;
		}
	}
	
	/**
	 * 立即分发
	 * @param list
	 * @param operation
	 * @return
	 */
	public long copyOuterDir() {
		final LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				ZCDeployConfigSet configSet = new ZCDeployConfigSchema().query();
				if (configSet.size()>0) {
					for (int i = 0; i < configSet.size(); i++) {
						ZCDeployJobSchema job = new ZCDeployJobSchema();
						ZCDeployJobSet jobs = job.query(new QueryBuilder("where RetryCount=0 and status<>? and configID=? order by id",
								Deploy.SUCCESS, configSet.get(i).getID()));
						if (jobs != null && jobs.size() > 0) {
							logger.info("执行分发任务 任务数：{}", jobs.size());
							executeBatchJob(configSet.get(i),jobs);
						}
					}
				}
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}

    public ZCDeployJobSet getJobs(long siteID, ArrayList list, String operation) {
		ZCDeployJobSet jobSet = new ZCDeployJobSet();
		for (int j = 0; j < list.size(); j++) {
			String srcPath = (String) list.get(j);
			if (StringUtil.isEmpty(srcPath)) {
				continue;
			}
			srcPath = srcPath.replace('\\', '/').replaceAll("///", "/").replaceAll("//", "/");

			String baseDir = Config.getContextRealPath() + Config.getValue("Statical.TargetDir").replace('\\', '/');
			baseDir = baseDir + "/" + SiteUtil.getAlias(siteID);

			baseDir = baseDir.replaceAll("///", "/");
			baseDir = baseDir.replaceAll("//", "/");
			srcPath = srcPath.replaceAll(baseDir, "");

			ZCDeployConfigSchema config = new ZCDeployConfigSchema();

			QueryBuilder qb = new QueryBuilder(" where UseFlag =1 and siteid=? and ? like concat(sourcedir,'%')", siteID, srcPath);
			if (Config.isSQLServer()||Config.isSybase()) {
				qb.setSQL(" where siteid=? and charindex(sourcedir,?)=0");
			}
			if (Config.isDB2()) {
				qb.setSQL(" where siteid=? and locate(sourcedir,'" + srcPath + "')=0");
				qb.getParams().remove(qb.getParams().size() - 1);
			}

			ZCDeployConfigSet set = config.query(qb);

			for (int i = 0; i < set.size(); i++) {
				config = set.get(i);
				String target = config.getTargetDir();
				if (StringUtil.isEmpty(target)) {
					target = "/";
				} else {
					if (!target.endsWith("/")) {
						target += "/";
					}
				}

				String filePath = srcPath;
				if (!config.getSourceDir().equals("/")) {
					filePath = srcPath.replaceAll(config.getSourceDir(), "");
				}
				target = dealFileName(target, filePath);

				ZCDeployJobSchema job = new ZCDeployJobSchema();
				job.setID(NoUtil.getMaxID("DeployJobID"));
				job.setConfigID(config.getID());
				job.setSource(baseDir + srcPath);
				job.setMethod(config.getMethod());
				job.setTarget(target);
				job.setSiteID(config.getSiteID());
				job.setHost(config.getHost());
				job.setPort(config.getPort());
				job.setUserName(config.getUserName());
				job.setPassword(config.getPassword());
				job.setRetryCount(0);
				job.setStatus(Deploy.READY);
				job.setOperation(operation);
				job.setAddTime(new Date());
				if (User.getCurrent() != null) {
					job.setAddUser(User.getUserName());
				} else {
					job.setAddUser("SYS");
				}

				jobSet.add(job);
			}
		}

		return jobSet;
	}

	private String dealFileName(String part1, String part2) {
		if (part1.equals("") || part2.equals(""))
			return part1 + part2;
		if (!part1.endsWith("/") && !part2.startsWith("/"))
			return part1 + "/" + part2;
		if (part1.endsWith("/") && part2.startsWith("/"))
			return part1 + part2.substring(1);
		else
			return part1 + part2;
	}
	
	public boolean executeJob(ZCDeployJobSchema job) {
		ZCDeployConfigSchema config = new ZCDeployConfigSchema();
		config.setID(job.getConfigID());
		if(!config.fill()){
			return false;
		}
		
		return executeJob(config,job);
	}

	/**
	 * 执行某个部署任务
	 * 
	 * @param jobID
	 * @return
	 */
	public boolean executeJob(long jobID) {
		ZCDeployJobSchema job = new ZCDeployJobSchema();
		job.setID(jobID);
		if (!job.fill()) {
			return false;
		}
		
		ZCDeployConfigSchema config = new ZCDeployConfigSchema();
		config.setID(job.getConfigID());
		if(!config.fill()){
			return false;
		}
		
		return executeJob(config,job);
	}
	
	public boolean executeJob(ZCDeployConfigSchema config,ZCDeployJobSchema job){
		ZCDeployJobSet set = new ZCDeployJobSet();
		set.add(job);
		return executeBatchJob(config,set);
	}
	
	/**
	 * 根据列表批量执行任务
	 * @param config
	 * @param jobs
	 * @return
	 */
	public boolean executeBatchJob(ZCDeployConfigSchema config,ZCDeployJobSet jobs) {
		if(config == null || jobs.size()<1){
			return false;
		}
		
		Transaction trans = new Transaction();
		String message = "";
		boolean connectFlag = true;
		
		String deployMethod = config.getMethod();
		if("DIR".equals(deployMethod)){
			for (int i = 0; i < jobs.size(); i++) {
				ZCDeployJobSchema job = jobs.get(i);
				//处理日志
				ZCDeployLogSchema jobLog = new ZCDeployLogSchema();
				jobLog.setID(NoUtil.getMaxID("DeployLogID"));
				jobLog.setSiteID(job.getSiteID());
				jobLog.setJobID(job.getID());
				jobLog.setBeginTime(new Date());

				if (job.getStatus() == Deploy.FAIL) {
					job.setRetryCount(job.getRetryCount() + 1);
				}
				// 安全考虑，不复制模板文件
				String sourceFile = job.getSource();
				if (sourceFile.indexOf("template") != -1) {
					logger.info("模板文件{}不复制，跳过", sourceFile);
					return true;
				}
				
				String target = job.getTarget();
				target = target.replace('\\', '/');
				String targetDir = target.substring(0, target.lastIndexOf("/"));
				File dir = new File(targetDir);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				if (!targetDir.endsWith("/template")) {// 过滤掉模板目录
					if (OPERATION_DELETE.equalsIgnoreCase(job.getOperation())) {
						if (FileUtil.delete(target)) {
							message = "成功删除文件" + target;
							logger.info(message);
							job.setStatus(Deploy.SUCCESS);
						} else {
							message = "失败：删除文件" + target;
							logger.info(message);
							job.setStatus(Deploy.FAIL);
							Errorx.addError(message);
						}
					} else {
						if (FileUtil.copy(sourceFile, target)) {
							message = "成功复制文件" + sourceFile + "到" + target;
							logger.info(message);
							job.setStatus(Deploy.SUCCESS);
						} else {
							message = "失败：复制文件" + sourceFile + "到" + target;
							logger.info(message);
							job.setStatus(Deploy.FAIL);
							Errorx.addError(message);
						}
					}
				}
				
				jobLog.setMessage(message);
				jobLog.setEndTime(new Date());
				logger.info(message);
				
				trans.add(jobLog, Transaction.INSERT);
				trans.add(job, Transaction.UPDATE);
			}
		}else if ("FTP".equals(deployMethod)) {
			CommonFtp ftp = new CommonFtp();
			try {
				ftp.connect(config.getHost(), (int)config.getPort(), config.getUserName(),config.getPassword());
				connectFlag = true;
			} catch (IOException e1) {
				logger.error(e1.getMessage(), e1);
				//处理日志
				ZCDeployLogSchema jobLog = new ZCDeployLogSchema();
				jobLog.setID(NoUtil.getMaxID("DeployLogID"));
				jobLog.setSiteID(config.getSiteID());
				jobLog.setJobID(jobs.get(0).getID());
				jobLog.setBeginTime(new Date());
				jobLog.setEndTime(new Date());
				jobLog.setMessage(e1.getMessage());
				trans.add(jobLog, Transaction.INSERT);
				connectFlag = false;
			}
			if(connectFlag){
				for (int i = 0; i < jobs.size(); i++) {
					ZCDeployJobSchema job = jobs.get(i);
					//处理日志
					ZCDeployLogSchema jobLog = new ZCDeployLogSchema();
					jobLog.setID(NoUtil.getMaxID("DeployLogID"));
					jobLog.setSiteID(job.getSiteID());
					jobLog.setJobID(job.getID());
					jobLog.setBeginTime(new Date());

					if (job.getStatus() == Deploy.FAIL) {
						job.setRetryCount(job.getRetryCount() + 1);
					}
					
					String target = job.getTarget();
					target = target.replace('\\', '/');
					if (OPERATION_DELETE.equalsIgnoreCase(job.getOperation())) {
						try {
							target = target.replaceAll("///", "/");
							if (ftp.delete(target)) {
								message = "FTP删除文件成功";
								job.setStatus(Deploy.SUCCESS);
							}else{
								message = "FTP删除文件失败";
								job.setStatus(Deploy.FAIL);
							}
						} catch (Exception e) {
							job.setStatus(Deploy.FAIL);
							message = e.getMessage();
							Errorx.addError(message);
						}
					} else {
						try {
							String srcFile = job.getSource();
							srcFile = srcFile.replaceAll("///", "/");
							srcFile = srcFile.replaceAll("//", "/");
							String path = srcFile;
							ArrayList list = FileList.getAllFiles(path);
							if(list.size()==0){
								job.setStatus(Deploy.FAIL);
								message = "文件不存在："+path;
								Errorx.addError(message);
							} else {
								for (int j = 0; j < list.size(); j++) {
									String name = (String) list.get(j);
									if (name.indexOf(Deploy.TemplateDIR) != -1) {
										continue;
									}
									name = name.replace('\\', '/');
									String targetName = name.replaceAll(path, "");
									ftp.upload(name, target + targetName);
								}
								job.setStatus(Deploy.SUCCESS);
								message = "FTP上传成功";
							}
						} catch (Exception e) {
							job.setStatus(Deploy.FAIL);
							message = e.getMessage();
							Errorx.addError(message);
						}
					}
					jobLog.setMessage(message);
					jobLog.setEndTime(new Date());
					logger.info(message);
					
					trans.add(jobLog, Transaction.INSERT);
					trans.add(job, Transaction.UPDATE);
			   }
		    }
			ftp.disconnect();
		}else if ("SFTP".equals(deployMethod)) {
			SFtp sftp = new SFtp();
			try{
				try {
					sftp.connect(config.getHost(), (int)config.getPort(), config.getUserName(),config.getPassword());
					connectFlag = true;
				} catch (JSchException e1) {
					logger.error(e1.getMessage(), e1);
					//处理日志
					ZCDeployLogSchema jobLog = new ZCDeployLogSchema();
					jobLog.setID(NoUtil.getMaxID("DeployLogID"));
					jobLog.setSiteID(config.getSiteID());
					jobLog.setJobID(jobs.get(0).getID());
					jobLog.setBeginTime(new Date());
					jobLog.setEndTime(new Date());
					jobLog.setMessage(e1.getMessage());
					trans.add(jobLog, Transaction.INSERT);
					connectFlag = false;
				}
				if(connectFlag){
					for (int i = 0; i < jobs.size(); i++) {
						ZCDeployJobSchema job = jobs.get(i);
						//处理日志
						ZCDeployLogSchema jobLog = new ZCDeployLogSchema();
						jobLog.setID(NoUtil.getMaxID("DeployLogID"));
						jobLog.setSiteID(job.getSiteID());
						jobLog.setJobID(job.getID());
						jobLog.setBeginTime(new Date());
	
						if (job.getStatus() == Deploy.FAIL) {
							job.setRetryCount(job.getRetryCount() + 1);
						}
						
						String target = job.getTarget();
						target = target.replace('\\', '/');
						if (OPERATION_DELETE.equalsIgnoreCase(job.getOperation())) {
							try {
								target = target.replaceAll("///", "/");
								if (sftp.delete(target)) {
									message = "SFTP删除文件成功";
									job.setStatus(Deploy.SUCCESS);
								}else{
									message = "SFTP删除文件失败";
									job.setStatus(Deploy.FAIL);
								}
							} catch (Exception e) {
								job.setStatus(Deploy.FAIL);
								message = e.getMessage();
								Errorx.addError(message);
							}
						} else {
							try {
								String srcFile = job.getSource();
								srcFile = srcFile.replaceAll("///", "/");
								srcFile = srcFile.replaceAll("//", "/");
								String path = srcFile;
								ArrayList list = FileList.getAllFiles(path);
								if(list.size() == 0){
									job.setStatus(Deploy.FAIL);
									message = "文件不存在"+srcFile;
								}else{
									for (int j = 0; j < list.size(); j++) {
										String name = (String) list.get(j);
										if (name.indexOf(Deploy.TemplateDIR) != -1) {
											continue;
										}
										name = name.replace('\\', '/');
										String targetName = name.replaceAll(path, "");
										sftp.upload(name, target + targetName);
									}
									job.setStatus(Deploy.SUCCESS);
									message = "SFTP上传成功";
								}
							} catch (Exception e) {
								job.setStatus(Deploy.FAIL);
								message = e.getMessage();
								Errorx.addError(message);
							}
						}
						jobLog.setMessage(message);
						jobLog.setEndTime(new Date());
						logger.info(message);
						
						trans.add(jobLog, Transaction.INSERT);
						trans.add(job, Transaction.UPDATE);
				    }
				}
			} catch (Exception e) {
				logger.error("FTP出现异常" + e.getMessage(), e);
			} finally {
				if( sftp != null)
					sftp.disconnect();
			}
		}

		if (trans.commit()) {
			return true;
		} else {
			logger.info("添加部署任务时，数据库操作失败");
			Errorx.addError(message);
			return false;
		}
	}
}
