package cn.zengj.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.zengj.dao.CsdnUserDao;
import cn.zengj.dao.ProductDetailInfoDao;
import cn.zengj.domain.CsdnUser;
import cn.zengj.domain.ProductDetailInfo;

@Service
public class ProductDetailInfoService {
	
	@Resource
	private ProductDetailInfoDao dao;
	
	public void save(ProductDetailInfo productDetailInfo){
		dao.save(productDetailInfo);
	}
	
	
	public ProductDetailInfo getByUrl(String url){
		return dao.getByUrl(url);
	}
	
	public ProductDetailInfo getBySku(Long sku){
		return dao.getBySku(sku);
	}
	
	

}
