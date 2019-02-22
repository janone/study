package cn.zengj.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;


@Entity
public class CsdnUser {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;//自增长id
	
	@Column(unique=true)
	private String keyName;//uri中的唯一值
	
	private String nickname;//页面名称
	
	private String lable;//名称下的一些标签

	@Lob 
	@Column(name="[desc]")
	private String desc;//标签下的自述
	
	private Integer concernCount;//关注数
	
	private Integer beConcernCount;//被关注数
	
	private Integer articleCount;//文章数

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getConcernCount() {
		return concernCount;
	}

	public void setConcernCount(Integer concernCount) {
		this.concernCount = concernCount;
	}

	public Integer getBeConcernCount() {
		return beConcernCount;
	}

	public void setBeConcernCount(Integer beConcernCount) {
		this.beConcernCount = beConcernCount;
	}

	public Integer getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(Integer articleCount) {
		this.articleCount = articleCount;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return "CsdnUser [id=" + id + ", keyName=" + keyName + ", nickname=" + nickname + ", lable=" + lable + ", desc="
				+ desc + ", concernCount=" + concernCount + ", beConcernCount=" + beConcernCount + ", articleCount="
				+ articleCount + "]";
	}

	
	
	
	
	
}
