package cn.com.sinosoft.entity;
 
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "scanswer")
public class SCAnswer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5955155784857348018L;
	public static final int LIST_COUNT = 10; 
	@Id
	@Column(name = "ID", length = 32, nullable = false)
	private String id;
	@Column(name = "questionId", length = 32, nullable = false)
	private String questionId;
	@Column(name = "questionTitle", length = 255)
	private String questionTitle;
	@Column(name = "userId", length = 32, nullable = false)
	private String userId;
	@Column(name = "addUserIP", length = 20)
	private String addUserIP;
	@Column(name = "content", length = 255, nullable = false)
	private String content;
	@Column(name = "adddate", length = 20)
	private String addDate;
	@Column(name = "aState", length = 2)
	private String aState;
	@Column(name = "aDate")
	private Date aDate;
	@Column(name = "operator", length = 100)
	private String operator;
    private String url;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAddUserIP() {
		return addUserIP;
	}

	public void setAddUserIP(String addUserIP) {
		this.addUserIP = addUserIP;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	public String getaState() {
		return aState;
	}

	public void setaState(String aState) {
		this.aState = aState;
	}

	public Date getaDate() {
		return aDate;
	}

	public void setaDate(Date aDate) {
		this.aDate = aDate;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}

	public String getAddDate() {
		return addDate;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	

	

	

}
