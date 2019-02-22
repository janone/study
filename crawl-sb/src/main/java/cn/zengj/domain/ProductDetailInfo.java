package cn.zengj.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class ProductDetailInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique = true)
	private String productName;
	
	@Column(length=1000)
	private String logo;

	private BigDecimal price;
	
	@Lob
	private String pictures;
	
	@Lob
	private String detailImages;
	
	@Lob
	private String videos;
	
	@Column(length=1000)
	private String url;

	@Column(unique = true)
	private Long sku;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}


	public String getPictures() {
		return pictures;
	}

	public void setPictures(String pictures) {
		this.pictures = pictures;
	}

	public String getDetailImages() {
		return detailImages;
	}

	public void setDetailImages(String detailImages) {
		this.detailImages = detailImages;
	}

	public String getVideos() {
		return videos;
	}

	public void setVideos(String videos) {
		this.videos = videos;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public Long getSku() {
		return sku;
	}

	public void setSku(Long sku) {
		this.sku = sku;
	}

	@Override
	public String toString() {
		return "ProductDetailInfo [id=" + id + ", productName=" + productName + ", logo=" + logo + ", price=" + price
				+ ", pictures=" + pictures + ", detailImages=" + detailImages + ", videos=" + videos + ", url=" + url
				+ "]";
	}
	
	
	

}
