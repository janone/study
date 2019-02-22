package cn.zengj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.zengj.domain.ProductDetailInfo;
import cn.zengj.service.ProductDetailInfoService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlSbApplicationTests {
	
	@Autowired
	private ProductDetailInfoService service;

	@Test
	public void save() {
		
		ProductDetailInfo info = new ProductDetailInfo();
		info.setProductName("商品名称");
		
		service.save(info);
		
	}
	
	@Test
	public void getByUrl() {
		
		ProductDetailInfo byUrl = service.getByUrl("https://item.m.jd.com/product/28146968688.html");
		
		System.out.println(byUrl);
		
	}

}
