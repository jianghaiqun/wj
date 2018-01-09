/**
 * 
 */
package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.Member;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import org.apache.struts2.convention.annotation.ParentPackage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangcaiyun
 *
 */
@ParentPackage("shop")
public class BrowseRecordAction extends BaseShopAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5960415816102337596L;

	/**
	 * 添加会员浏览记录
	 * @return
	 */
	public String recordDetailBrowse() {
		Map<String, String> jsonMap = new HashMap<String, String>();
		String productId = getParameter("productId");
		Member member = getLoginMember();
		if (StringUtil.isNotEmpty(productId) && member != null && StringUtil.isNotEmpty(member.getId())) {
			Transaction trans = new Transaction();
			String memberId = member.getId();
			// 判断是否记录该产品
			String id = new QueryBuilder("select ID from MemberBrowseRecord where memberId=? and productId=? ", memberId, productId).executeString();
			// 记录过更新浏览时间
			if (StringUtil.isNotEmpty(id)) {
				trans.add(new QueryBuilder("update MemberBrowseRecord set createDate = now() where ID=?", id));
			} else {
				// 未记录过则添加
				QueryBuilder qb = new QueryBuilder("INSERT INTO MemberBrowseRecord (ID,memberId,productId,createDate) VALUES (?, ?, ?, now());");
				qb.add(NoUtil.getMaxID("MemberBrowseRecordID"));
				qb.add(memberId);
				qb.add(productId);
				trans.add(qb);
				// 超过三个则删除最早浏览的记录
				DataTable dt = new QueryBuilder("select count(1), MIN(createDate) from MemberBrowseRecord where memberId=?", memberId).executeDataTable();
				if (dt != null) {
					if (dt.getInt(0, 0) >= 3) {
						trans.add(new QueryBuilder("delete from MemberBrowseRecord where memberId=? and createDate=?", memberId, dt.getString(0, 1)));
					}
				}
			}
			if (!trans.commit()) {
				logger.error("会员（{}）浏览产品详细页（{}）记录添加失败！", memberId, productId);
			}
		}
		return ajaxJson(jsonMap);
	}
	
	/**
	 * 清空会员浏览记录
	 * @return
	 */
	public String delBrowseRecord() {
		Map<String, String> jsonMap = new HashMap<String, String>();
		Member member = getLoginMember();
		if (member != null && StringUtil.isNotEmpty(member.getId())) {
			Transaction trans = new Transaction();
			trans.add(new QueryBuilder("delete from MemberBrowseRecord where memberId=?", member.getId()));
			if (!trans.commit()) {
				logger.error("会员（）清空浏览记录失败！", member.getId());
			}
		}
		return ajaxJson(jsonMap);
	}
}
