package com.sinosoft.cms.webservice;

public interface CmsService {
	/**
	 * 新建文章
	 * 
	 * @param catalogID
	 *            栏目id
	 * @param title
	 *            产品id
	 * @return
	 */
	public long addArticle(long catalogID, String present_id );

	/**
	 * 编辑文章
	 * 
	 * @param articleID
	 *            文章ID
	 * @param title
	 *            文章标题
	 * @param author
	 *            作者
	 * @param content
	 *            文章内容
	 * @param publishDate
	 *            发布日期
	 * @return
	 */
	public boolean editArticle(long articleID, String title, String author, String content, String publishDate);

	/**
	 * 删除文章
	 * 
	 * @param articleID
	 *            文章ID
	 * @return
	 */
	public boolean deleteArticle(long articleID);

	/**
	 * 发布文章
	 * 
	 * @param articleID
	 *            文章ID
	 * @return
	 */
	public boolean publishArticle(long articleID);

	/**
	 * 查询文章生成的url
	 * 
	 * @param articleID
	 *            文章ID
	 * @return
	 */
	public String showArticleURL(long articleID);

	/**
	 * 新建栏目
	 * 
	 * @param parentID
	 *            上级栏目ID
	 * @param name
	 *            栏目名称
	 * @param type
	 *            栏目类型
	 * @param alias
	 *            别名
	 * @return
	 */
	public long addCatalog(long parentID, String name, int type, String alias);

	/**
	 * 修改栏目
	 * 
	 * @param ID
	 *            栏目ID
	 * @param name
	 *            栏目名称
	 * @param alias
	 *            栏目别名
	 * @return
	 */
	public boolean editCatalog(long ID, String name, String alias);

	/**
	 * 删除栏目
	 * 
	 * @param ID
	 *            栏目ID
	 * @return
	 */
	public boolean deleteCatalog(long ID);

	/**
	 * 发布栏目
	 * 
	 * @param ID
	 *            栏目ID
	 * @return
	 */
	public boolean publishCatalog(long ID);

	/**
	 * 发布文章引导图片
	 * 
	 * @param ID
	 *            栏目ID
	 * @return
	 */
	public String publishArticleImage(long catalogID, String filePath, String ImageType);

	/**
	 * 新建用户
	 * 
	 * @param userName
	 *            用户名
	 * @param realName
	 *            真实姓名
	 * @param password
	 *            密码
	 * @param md5pass
	 *            是否需要加密
	 * @param departID
	 *            机构编码
	 * @param email
	 *            邮箱
	 * 
	 * @return -1 失败 0 用户已存在 1 成功
	 */
	public long addUser(String userName, String realName, String password, String departID, String email);

	/**
	 * 编辑用户
	 * 
	 * @param userName
	 *            用户名
	 * @param realName
	 *            真实姓名
	 * @param password
	 *            密码
	 * @param md5pass
	 *            是否需要加密
	 * @param departID
	 *            机构编码
	 * @param email
	 *            邮箱
	 * 
	 * @return true 成功 false 失败
	 */
	public boolean editUser(String userName, String realName, String password, String departID, String email);

	/**
	 * 删除用户
	 * 
	 * @param userName
	 * @return
	 */
	public boolean deleteUser(String userName);

	/**
	 * 增加会员
	 * 
	 * @param UserName
	 * @param PassWord
	 * @param Email
	 * @return
	 */
	public long addMember(String UserName, String RealName, String PassWord, String Email);

	/**
	 * 修改会员
	 * 
	 * @param UserName
	 * @param PassWord
	 * @param Email
	 * @return
	 */
	public boolean editMember(String UserName, String RealName, String PassWord, String Email);

	/**
	 * 删除会员
	 * 
	 * @param UserName
	 * @return
	 */
	public boolean deleteMember(String UserName);
	
	public boolean publishArticle(String riskcode, String catalogID);
}
