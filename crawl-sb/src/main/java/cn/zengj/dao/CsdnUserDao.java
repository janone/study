package cn.zengj.dao;

import org.springframework.data.repository.CrudRepository;
import cn.zengj.domain.CsdnUser;

public interface CsdnUserDao extends CrudRepository<CsdnUser, Integer>{

	CsdnUser getByKeyName(String keyName);
	
	
}
