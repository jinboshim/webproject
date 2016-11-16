package project.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pporan.maven.framework.data.EData;
import pporan.maven.framework.db.DatabaseHandler;
import project.service.SampleService;


@Service
public class SampleServiceImpl implements SampleService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
//	@Autowired
//	DatabaseHandler databaseHandler;
//	
//	@Override
//	@Transactional
//	public void _sample(EData eData) {
//		eData.put("title", "title");
//		logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ s:sample @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//		databaseHandler.selectList("db1", "Sample.sample", eData);
//		databaseHandler.insertItem("db1", "Sample.sample_insert", eData);
//		databaseHandler.insertItem("db1", "Sample.sample_insert_error", eData);
//		logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ e:sample @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//		
//		
//		
//		
//		
//		
//		eData.put("title", "title2");
//		logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ s2:sample @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//		databaseHandler.selectList("db2", "Sample2.sample", eData);
//		databaseHandler.insertItem("db2", "Sample2.sample_insert", eData);
//		databaseHandler.insertItem("db2", "Sample2.sample_insert_error", eData);
//		logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ e2:sample @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//	}
	
	
	@Autowired
	DatabaseHandler databaseHandler;
	
//	
	@Override
	public void _sample(EData eData) throws Exception  {
		eData.put("title", "title");

//		_txTest(eData);
//		_txTest2(eData);
//		ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring_setting.xml");
//		CommonDao dao = (CommonDao)context.getBean("db1CommonDao");
//		try{
//		dao.selectList("Sample.sample", eData);
//		dao.insertItem("Sample.sample_insert", eData);
//		dao.insertItem("Sample.sample_insert_error", eData);
//		}
//		catch(Exception e){
//			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//		}
		
		logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ s:sample @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		databaseHandler.selectList(DatabaseHandler.DB_MYSQL, "Sample.sample", eData);
		logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ e:sample @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

		
		
		
//		logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ s2:sample @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//		db2CommonDao.selectList("Sample2.sample", eData);
//		db2CommonDao.insertItem("Sample2.sample_insert", eData);
//		db2CommonDao.insertItem("Sample2.sample_insert_error", eData);
//		logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ e2:sample @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//		try{
//		}
//		catch(Exception e){
//			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//		}
	}

//	@Transactional(value="txManager_db1",rollbackFor={Exception.class})
//	public void _txTest(EData eData){
//		
//	}
//	@Transactional(value="txManager_db2",rollbackFor={Exception.class})
//	public void _txTest2(EData eData){
//		
//	}
	
	
}
