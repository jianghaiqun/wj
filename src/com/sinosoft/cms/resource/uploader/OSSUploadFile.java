package com.sinosoft.cms.resource.uploader;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.OSSObject;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class OSSUploadFile {
	private static final Logger logger = LoggerFactory.getLogger(OSSUploadFile.class);

	private static String accessKeyId = Config.getValue("AccessKeyId");
	
	private static String accessKeySecret = Config.getValue("AccessKeySecret");
	
	private static String bucketName = Config.getValue("BucketName");

	
	/**
	 * 
	* @Title: upload 
	* @Description: TODO(向服务器上传文件) 
	* @return void    返回类型 
	* @author XXX
	 */
	public static void upload(InputStream in,String path) throws Exception {
		
		if(in == null){
			in = new ByteArrayInputStream("".getBytes());
		}
		
		if(StringUtil.isEmpty(path)){
			throw new Exception("上传文件时，路径不能为空！！！");
		}
		
		String uploadFlag = Config.getValue("UploadFlag");
		
		if("local".equals(uploadFlag)){
			
			File localFile = new File(path);
			if(localFile.exists()){
				localFile.delete();
			}
			localFile.createNewFile();
	    	OutputStream os = new FileOutputStream(localFile);
	    	int bytesRead = 0;
	    	byte[] buffer = new byte[8192];
	    	while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
	    		os.write(buffer, 0, bytesRead);
	    	}
	    	os.close();
	    	in.close();
			
		}else{
			
			// 初始化一个OSSClient
			OSSClient client = new OSSClient(accessKeyId, accessKeySecret);
			// 创建上传Object的Metadata
			ObjectMetadata meta = new ObjectMetadata();
			// 必须设置ContentLength
			meta.setContentLength(in.available());

			//广告管理上传的文件路径与其他上传功能文件存放路径不同
			path = path.replaceAll("\\\\", "/");
			if(path.indexOf("/upload") > -1){
				path = path.substring(path.indexOf("/upload")+1);
			}else{
				path = path.substring(path.indexOf("/js")+1);
			}
			// 上传Object.
			client.putObject(bucketName, path, in, meta);
			in.close();
			
		}
	
	}
	
	/**
	 * 
	 * uploadAliyun:向阿里云上传文件. <br/>
	 *
	 * @author 李景川
	 * @param in
	 * @param path
	 * @throws Exception
	 */
	public static void uploadAliyun(InputStream in,String path) throws Exception {
		try{
			if(in == null){
				throw new Exception("上传文件不能为空！！！");
			}
			
			if(StringUtil.isEmpty(path)){
				throw new Exception("上传文件时，路径不能为空！！！");
			}
			// 初始化一个OSSClient
			OSSClient client = new OSSClient(accessKeyId, accessKeySecret);
			// 创建上传Object的Metadata
			ObjectMetadata meta = new ObjectMetadata();
			// 必须设置ContentLength
			meta.setContentLength(in.available());
			// 上传Object.
			client.putObject(bucketName, path, in, meta);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw e;
		}finally{
			in.close();
		}
	}
	
	/**
	 * 
	* @Title: upload 
	* @Description: TODO(向服务器上传文件) 
	* @return void    返回类型 
	* @author XXX
	 */
	
       public static void uploadFile(String uploadfilePath) throws Exception {
		
		if(StringUtil.isEmpty(uploadfilePath)){
			throw new Exception("上传文件时，路径不能为空！！！");
		}
		String uploadFlag = Config.getValue("UploadFlag");
		String pathAll[] = uploadfilePath.split(",");
		if("local".equals(uploadFlag)){
			
		}else{
			for(int i = 0;i<pathAll.length;i++){
				// 初始化一个OSSClient
				OSSClient client = new OSSClient(accessKeyId, accessKeySecret);
				// 创建上传Object的Metadata
				ObjectMetadata meta = new ObjectMetadata();
				InputStream in = new FileInputStream(new File(pathAll[i]));
				// 必须设置ContentLength
				meta.setContentLength(in.available());
				String key = pathAll[i].replaceAll("\\\\", "/");
				key = key.substring(key.indexOf("/upload")+1);
				
				// 上传Object.
				client.putObject(bucketName, key, in, meta);

				FileUtil.delete(pathAll[i].replaceAll("\\\\", "/"));
			}
			
		}
	
	}
	
	
	/**
	 * 
	* @Title: listObjects 
	* @Description: TODO(获取服务器上文件) 
	* @return void    返回类型 
	* @author XXX
	 */
	public static void listObjects(String key) {

		 // 初始化一个OSSClient
	      OSSClient client = new OSSClient(accessKeyId, accessKeySecret);

	   // 获取Object，返回结果为OSSObject对象
	      OSSObject object = client.getObject(bucketName, key);

	      // 获取Object的输入流
	      InputStream objectContent = object.getObjectContent();
	      // 处理Object
	      ByteArrayOutputStream outSteam = new ByteArrayOutputStream();  
	      byte[] buffer = new byte[1024];  
	      int len = -1; 
	      String str = "";
	      try {
			while ((len = objectContent.read(buffer)) != -1) {  
				outSteam.write(buffer, 0, len);
			  }
		 // 关闭流
			 outSteam.close();  
		     objectContent.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			  logger.error(e1.getMessage(), e1);
		}  
	     
	     
	}
	
	/**
	 * 
	* @Title: deleteObject 
	* @Description: TODO(删除文件) 
	* @return void    返回类型 
	* @author XXX
	 * @throws Exception 
	 */
	public static void deleteObject(String path) throws Exception {
		
		if(StringUtil.isEmpty(path)){
			throw new Exception("删除文件时，路径不能为空！！！");
		}
		
		String uploadFlag = Config.getValue("UploadFlag");
		
		if("local".equals(uploadFlag)){
			
			File file = new File(path);
			if (file.exists()) {
				file.delete();
			}
			
		}else{
			
			// 初始化一个OSSClient
			OSSClient client = new OSSClient(accessKeyId, accessKeySecret);
			path = path.replaceAll("\\\\", "/");
			if(path.indexOf("/upload") > -1){
				path = path.substring(path.indexOf("/upload")+1);
			}else{
				path = path.substring(path.indexOf("/js")+1);
			}
			// 删除Object
			client.deleteObject(bucketName, path);
		
		}
	}
	
	/**
	 * 
	* @Title: copyObject 
	* @Description: TODO(复制文件) 
	* @return void    返回类型 
	* @author XXX
	 */
	public static void copyObject(String srcKey,String destKey) throws Exception {
		if(StringUtil.isEmpty(srcKey)){
			throw new Exception("复制文件时，原文件路径不能为空！！！");
		}
		if(StringUtil.isEmpty(destKey)){
			throw new Exception("复制文件时，新文件路径不能为空！！！");
		}
		
		String uploadFlag = Config.getValue("UploadFlag");
		
       if("local".equals(uploadFlag)){
			File file = new File(srcKey);
			if (file.exists()) {
				if (!FileUtil.copy(srcKey, destKey)) {
					throw new Exception("复制文件失败！！！");
				}
			}
		}else{
			srcKey = srcKey.substring(srcKey.indexOf("/upload")+1);
			destKey = destKey.substring(destKey.indexOf("/upload")+1);
			// 初始化一个OSSClient
		     OSSClient client = new OSSClient(accessKeyId, accessKeySecret);
		    // 拷贝Object
		    client.copyObject(bucketName, srcKey, bucketName, destKey);
		}
	}
	
//	public static void main(String[] args) {
//		/*String uploadfilePath = "qq.png";
//		// 初始化一个OSSClient
//	      OSSClient client = new OSSClient(accessKeyId, accessKeySecret);
//		// 开始Multipart Upload
//		InitiateMultipartUploadRequest initiateMultipartUploadRequest =
//		                                    new InitiateMultipartUploadRequest(bucketName, uploadfilePath);
//		InitiateMultipartUploadResult initiateMultipartUploadResult =
//		                                    client.initiateMultipartUpload(initiateMultipartUploadRequest);
//
//		// 打印UploadId
//		System.out.println("UploadId: " + initiateMultipartUploadResult.getUploadId());
//
//		// 设置每块为 5M
//		final int partSize = 1024 * 1024 * 5;
//
//		File partFile = new File("D:/qq.png");
//
//		// 计算分块数目
//		int partCount = (int) (partFile.length() / partSize);
//		if (partFile.length() % partSize != 0){
//		    partCount++;
//		}
//
//		// 新建一个List保存每个分块上传后的ETag和PartNumber
//		List<PartETag> partETags = new ArrayList<PartETag>();
//		try{
//			for(int i = 0; i < partCount; i++){
//			    // 获取文件流
//			    FileInputStream fis = new FileInputStream(partFile);
//			    // 跳到每个分块的开头
//			    long skipBytes = partSize * i;
//			    fis.skip(skipBytes);
//
//			    // 计算每个分块的大小
//			    long size = partSize < partFile.length() - skipBytes ?
//			            partSize : partFile.length() - skipBytes;
//
//			    // 创建UploadPartRequest，上传分块
//			    UploadPartRequest uploadPartRequest = new UploadPartRequest();
//			    uploadPartRequest.setBucketName(bucketName);
//			    uploadPartRequest.setKey(uploadfilePath);
//			    uploadPartRequest.setUploadId(initiateMultipartUploadResult.getUploadId());
//			    uploadPartRequest.setInputStream(fis);
//			    uploadPartRequest.setPartSize(size);
//			    uploadPartRequest.setPartNumber(i + 1);
//			    UploadPartResult uploadPartResult = client.uploadPart(uploadPartRequest);
//
//			    // 将返回的PartETag保存到List中。
//			    partETags.add(uploadPartResult.getPartETag());
//
//			    // 关闭文件
//			    fis.close();
//
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		CompleteMultipartUploadRequest completeMultipartUploadRequest =
//		new CompleteMultipartUploadRequest(bucketName, uploadfilePath, initiateMultipartUploadResult.getUploadId(), partETags);
//
//		//完成分块上传
//		CompleteMultipartUploadResult completeMultipartUploadResult =
//		client.completeMultipartUpload(completeMultipartUploadRequest);
//
//		//打印Object的ETag
//		System.out.println(completeMultipartUploadResult.getETag());*/
//
//		String a = "a";
//		String b[] = a.split(",");
//		System.out.println(b[0]);
//
//
//	}

}
