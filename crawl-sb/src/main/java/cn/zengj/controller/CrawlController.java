package cn.zengj.controller;

import javax.annotation.Resource;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.zengj.core.csdnuser.CrawlStarter;
import cn.zengj.core.jdproduct.JdStarter;

@RequestMapping
@Controller
public class CrawlController {
	
	@Resource
	private CrawlStarter starter;
	@Resource
	private JdStarter jdstarter;
	
	
	@RequestMapping("/do")
	public void tocrawl() throws Exception{
		System.out.println("开始");
		new Thread(()->{
			try {
				starter.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
		
	}
	
	@RequestMapping("/crawlJdProduct")
	public void crawlJdProduct() throws Exception{
		System.out.println("开始");
		new Thread(()->{
			try {
				jdstarter.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
		
	}
	
	
	
}
