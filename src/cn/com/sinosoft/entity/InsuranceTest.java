package cn.com.sinosoft.entity;

import javax.persistence.Entity;

/**
 * 实体类--保险测试
 * 
 * @author zhangjing
 * 
 */
@Entity
public class InsuranceTest extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6636317999393971872L;
	private String who;//为谁测试
	private String mail; // 用户邮箱
	private String mailstatus; // 邮件是否成功接收（true/false)
	private String phone; // 用户电话
	private String sex; // 性别--M:男；W:女
	private String birthday; // 出生年月
	private String healthstatus; // 健康情况--1：很好；2：正常；3：一般，经常感冒；4：不好，经常生病
	private String education; // 教育程度--1：婴儿；2：幼儿园；3：小学/初中；4：高中；5：大学；6：工作
	private String loan; // 有无贷款(true/false)
	private String salary; // 家庭年收入--1：小于一万；2：一万到五万；3：五万到十万；4：十万以上；
	private String work; // 工作类型--1：公务员；2：事业单位；3：国企；4：中外合资；5：普通企业；6：个体；7：无业或退休；8：高危职业；
	private String travelnum; // 旅行次数--1：无；2：10次以内；3：10次以上
	private String destination; // 出行地--1：国内；2：国外；3：申根国家；4：港澳台地区
	private String yanglaobaoxian; // 养老保险--1：没有；2：社保养老；3：商业养老；4：社保和商业养老保险
	private String yiliaobaoxian; // 医疗保险--1：没有；2：商业医疗保险；3：社保和商业医疗保险
	private String sports; // 是否参加高风险运动（true/false）
	private String car; // 是否有车(true/false)
	private String memo;// 备注
	
	public String getWho() {
		return who;
	}
	public void setWho(String who) {
		this.who = who;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getMailstatus() {
		return mailstatus;
	}
	public void setMailstatus(String mailstatus) {
		this.mailstatus = mailstatus;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getHealthstatus() {
		return healthstatus;
	}
	public void setHealthstatus(String healthstatus) {
		this.healthstatus = healthstatus;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getLoan() {
		return loan;
	}
	public void setLoan(String loan) {
		this.loan = loan;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public String getTravelnum() {
		return travelnum;
	}
	public void setTravelnum(String travelnum) {
		this.travelnum = travelnum;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getYanglaobaoxian() {
		return yanglaobaoxian;
	}
	public void setYanglaobaoxian(String yanglaobaoxian) {
		this.yanglaobaoxian = yanglaobaoxian;
	}
	public String getYiliaobaoxian() {
		return yiliaobaoxian;
	}
	public void setYiliaobaoxian(String yiliaobaoxian) {
		this.yiliaobaoxian = yiliaobaoxian;
	}
	public String getSports() {
		return sports;
	}
	public void setSports(String sports) {
		this.sports = sports;
	}
	public String getCar() {
		return car;
	}
	public void setCar(String car) {
		this.car = car;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	 
	
}
