package cn.zengj.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.zengj.dao.CsdnUserDao;
import cn.zengj.domain.CsdnUser;

@Service
public class CsdnUserService {
	
	@Resource
	private CsdnUserDao dao;
	
	public void save(CsdnUser csdnUser){
		dao.save(csdnUser);
	}
	
	
	public CsdnUser getByKeyName(String keyName){
		return dao.getByKeyName(keyName);
	}

}
