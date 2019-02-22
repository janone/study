package com.alibaba.dubbo.consumer;

import com.alibaba.dubbo.demo.DemoService;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by wy on 2017/4/13.
 */
public class Consumer {
	
	private static Logger log = Logger.getLogger(Consumer.class);
	
    public static void main(String[] args) {

        //测试常规服务
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
        System.out.println("consumer2 start");
        DemoService demoService = context.getBean(DemoService.class);
        System.out.println("consumer2");
        try{
        	System.out.println(demoService.getPermissions(2L));
        }catch(Exception e){
        	log.error("异常",e);
        }
    }
}
