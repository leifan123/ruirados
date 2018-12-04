package test;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ruirados.controller.BucketController;
import com.ruirados.pojo.OssOperatelog;
import com.ruirados.util.BucketAclUtil;

public class BucketTest {
	
	/*protected static ApplicationContext ctx;
	
	@BeforeClass
	public static void setup() {
		String[] resources = { "classpath*:applicationContext.xml" };
		ctx = new ClassPathXmlApplicationContext(resources);
	}*/
	
	@Test
	public void test1() throws Exception{
		
	}
}
