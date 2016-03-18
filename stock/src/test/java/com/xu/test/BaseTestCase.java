package com.xu.test;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * 单元测试基类
 * 
 * @version
 * 
 * 			<pre>
 * Author	Version		Date		Changes
 * lunan.xu 	1.0  		2016年3月17日 	Created
 *
 *          </pre>
 * 
 * @since 1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:config/applicationContext.xml")
public abstract class BaseTestCase {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

}

/*
   @Transactional //测试时，事务将会被回滚
   @DirtiesContext//以下这种方式会不停启动spring
   @ContextConfiguration("classpath*:config/applicationContext.xml") 
   public abstract class BaseTestCase extends AbstractTransactionalJUnit4SpringContextTests {
 */
