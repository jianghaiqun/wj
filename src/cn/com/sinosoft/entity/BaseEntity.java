package cn.com.sinosoft.entity;

import org.hibernate.annotations.GenericGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public class BaseEntity implements Serializable {
	protected static final Logger logger = LoggerFactory.getLogger(BaseEntity.class);
	private static final long serialVersionUID = -6718838800112233445L;
	private String id;
	private Date createDate;
	private Date modifyDate;

	@Id
	@Column(length = 32, nullable = true)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(updatable = false)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public int hashCode() {
		return this.id == null ? System.identityHashCode(this) : this.id.hashCode();
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass().getPackage() != obj.getClass().getPackage()) {
			return false;
		}
		BaseEntity other = (BaseEntity) obj;
		if (this.id == null) {
			if (other.getId() != null)
				return false;
		} else if (!this.id.equals(other.getId())) {
			return false;
		}
		return true;
	}
}