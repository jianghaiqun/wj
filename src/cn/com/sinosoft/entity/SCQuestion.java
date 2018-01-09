package cn.com.sinosoft.entity;
 
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SCQUESTION")
public class SCQuestion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -979260599277855730L;
	
	public static final int LIST_COUNT = 15;// 推荐文章列表最大文章数
	@Id
	@Column(name = "ID", length = 32, nullable = false)
	private String  Id; 
	@Column(name = "QuestionId", length = 32, nullable = false)
    private String  questionId;
	@Column(name = "UserId", length = 32, nullable = false)
    private String  userId; 
    @Column(name = "AddUserIP", length = 20, nullable = false)
    private String  addUserIP;  
    @Column(name = "Title", length = 255, nullable = false)
    private String  title; 
    @Column(name = "Content", length = 255, nullable = false)
    private String  content; 
    @Column(name = "State", length = 2, nullable = false)
    private String  state ; 
    @Column(name = "AddDate", nullable = false)
    private String addDate ;  
   @Column(name = "Type", length = 2, nullable = false)
    private String  type; 
    @Column(name = "aState", length = 2)
    private String  aState; 
    @Column(name = "aDate")
    private Date  aDate; 
    @Column(name = "Operator", length = 100)
    private String operator ;
    private String url;
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
