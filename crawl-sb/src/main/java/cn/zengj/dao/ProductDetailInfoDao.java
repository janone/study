package cn.zengj.dao;

import org.springframework.data.repository.CrudRepository;
import cn.zengj.domain.CsdnUser;
import cn.zengj.domain.ProductDetailInfo;

public interface ProductDetailInfoDao extends CrudRepository<ProductDetailInfo, Integer>{

	ProductDetailInfo getByUrl(String url);

	ProductDetailInfo getBySku(Long sku);
	
}
