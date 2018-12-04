package com.ruirados.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.ruirados.pojo.OssMonitor;
import com.ruirados.service.OssBucketService;
import com.ruirados.service.OssMonitorService;
import com.ruirados.service.UserCoreAccessService;

public class MonitorUtil {
	
	/**
	 * 获得监控数据
	 * @param ossMonitorService
	 * @param ossBucketService
	 * @param userCoreAccessService
	 * @param times
	 * @param today
	 * @param bucketName
	 * @param zoneId
	 * @param yesterday
	 * @return
	 */
	public static List<OssMonitor> getByParam(OssMonitorService ossMonitorService, OssBucketService ossBucketService, 
			UserCoreAccessService userCoreAccessService, int times, String today, String bucketName, String zoneId, String yesterday, String companyId){
		List<OssMonitor> select = null;
		String dateFormat = " where date_format(date, '%Y-%m-%d')='";
		String strDate = " o join (SELECT id,str_to_date(date, '%Y-%c-%e') dates from oss_monitor) d on o.id = d.id where ";
		if(times == 1){
			if(!"".equals(bucketName) && bucketName != null){
				select = ossMonitorService.selectByParam(null, dateFormat + today + "' and bucketName = '" + bucketName + "' and zoneId = '" + zoneId + "' AND companyId = '" + companyId +"' ",
						"order by time asc");
			}	else{
				select = ossMonitorService.selectByParam(null, dateFormat + today + "' and zoneId = '" + zoneId + "' AND companyId = '" + companyId +"' ",
						"order by time asc");
			}
		} else if(times == 2){
			if(!"".equals(bucketName) && bucketName != null){
				select = ossMonitorService.selectByParam(null, dateFormat + yesterday + "' and bucketName = '" + bucketName + "' and zoneId = '" + zoneId + "' AND companyId = '" + companyId +"' ",
						"order by time asc");
			}	else{
				select = ossMonitorService.selectByParam(null, dateFormat + yesterday + "' and zoneId = '" + zoneId + "' AND companyId = '" + companyId +"' ",
						"order by time asc");
			}
		} else if(times == 3){
			if(!"".equals(bucketName) && bucketName != null){
				select = ossMonitorService.selectByParam("o.*",
						strDate + "date_sub(CURDATE(),INTERVAL 7 DAY) <= DATE(d.dates) and o.bucketName = '"
								+ bucketName + "' and o.zoneId = '" + zoneId + "' AND companyId = '" + companyId +"' ",
						"order by d.dates asc");
			}	else{
				select = ossMonitorService.selectByParam("o.*",
						strDate + "date_sub(CURDATE(),INTERVAL 7 DAY) <= DATE(d.dates) and o.zoneId = '"
								+ zoneId + "' AND companyId = '" + companyId +"' ",
						"order by d.dates asc");
			}
		} else{
			if(!"".equals(bucketName) && bucketName != null){
				select = ossMonitorService.selectByParam("o.*",
						strDate + "DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(d.dates) and o.bucketName = '"
								+ bucketName + "' and o.zoneId = '" + zoneId + "' AND companyId = '" + companyId +"' ",
						"order by d.dates asc");
			}	else{
				select = ossMonitorService.selectByParam("o.*",
						strDate + "DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(d.dates) and o.zoneId = '"
								+ zoneId + "' AND companyId = '" + companyId +"' ",
						"order by d.dates asc");
			}
		}
		return select;
	}
	
	/**
	 * 获得监控数据的小时
	 * @param ossMonitorService
	 * @param ossBucketService
	 * @param userCoreAccessService
	 * @param times
	 * @param today
	 * @param bucketName
	 * @param zoneId
	 * @param yesterday
	 * @return
	 */
	public static List<Integer> getByHour(OssMonitorService ossMonitorService, OssBucketService ossBucketService, 
			UserCoreAccessService userCoreAccessService, int times, String today, String bucketName, String zoneId, String yesterday, String companyId){
		List<Integer> hourstamp = null;
		String str = " where date_format(date, '%Y-%m-%d')='";
		if(times == 1){
			if(!"".equals(bucketName) && bucketName != null){
				hourstamp = ossMonitorService.selectByHour("distinct(time)",
						str+ today + "' and bucketName = '" + bucketName + "' and zoneId = '" + zoneId + "' AND companyId = '" + companyId +"' ", "order by time asc");
			}	else{
				hourstamp = ossMonitorService.selectByHour("distinct(time)",
						str + today + "' and zoneId = '" + zoneId + "' AND companyId = '" + companyId +"' ", "order by time asc");
			}
		} else{
			if(!"".equals(bucketName) && bucketName != null){
				hourstamp = ossMonitorService.selectByHour("distinct(time)",
						str + yesterday + "' and bucketName = '" + bucketName + "'  and zoneId = '" + zoneId + "' AND companyId = '" + companyId +"' ", "order by time asc");
			}	else{
				hourstamp = ossMonitorService.selectByHour("distinct(time)",
						str + yesterday + "' and zoneId = '" + zoneId + "' AND companyId = '" + companyId +"' ", "order by time asc");
			}
		}
		return hourstamp;
	}
	
	/**
	 * 获得监控数据的日期
	 * @param ossMonitorService
	 * @param ossBucketService
	 * @param userCoreAccessService
	 * @param times
	 * @param today
	 * @param bucketName
	 * @param zoneId
	 * @param yesterday
	 * @return
	 */
	public static List<String> getByCount(OssMonitorService ossMonitorService, OssBucketService ossBucketService, 
			UserCoreAccessService userCoreAccessService, int times, String bucketName, String zoneId, String companyId){
		List<String> timestamp = null;
		
		String str =" o join (SELECT id,str_to_date(date, '%Y-%c-%e') dates from oss_monitor) d on o.id = d.id where ";
		if(times == 3){
			if(!"".equals(bucketName) && bucketName != null && bucketName != null){
				timestamp = ossMonitorService.selectByCount("distinct(date)",
						str + " date_sub(CURDATE(),INTERVAL 7 DAY) <= DATE(d.dates) and o.bucketName= '"
								+ bucketName + "' and o.zoneId = '" + zoneId + "' AND companyId = '" + companyId +"' ",
						"order by d.dates desc");
			}	else{
				timestamp = ossMonitorService.selectByCount("distinct(date)",
						str + " date_sub(CURDATE(),INTERVAL 7 DAY) <= DATE(d.dates) and o.zoneId = '"
								+ zoneId + "' AND companyId = '" + companyId +"' ",
						"order by d.dates desc");
			}
		} else{
			if(!"".equals(bucketName) && bucketName != null){
				timestamp = ossMonitorService.selectByCount("distinct(date)",
						str + " DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(d.dates) and o.bucketName = '"
								+ bucketName + "' and o.zoneId = '" + zoneId + "' AND companyId = '" + companyId +"' ",
						"order by d.dates desc");
			}	else{
				timestamp = ossMonitorService.selectByCount("distinct(date)",
						str + " DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date(d.dates) and o.zoneId = '"
								+ zoneId + "' AND companyId = '" + companyId +"' ",
						"order by d.dates desc");
			}
		}
		return timestamp;
	}
	
	/**
	 * 获取日期
	 * @param times
	 * @return
	 */
	public static List<String> getDate(int times){
		List<String> dateList = new LinkedList<String>();
		Calendar calendar1 = Calendar.getInstance();
		if(times == 3){
			for (int i = 0; i < 7; i++) {
				if (i != 0) {
					calendar1.set(Calendar.DAY_OF_YEAR, calendar1.get(Calendar.DAY_OF_YEAR) - 1);
				}
				Date today1 = calendar1.getTime();
				SimpleDateFormat format = new SimpleDateFormat("MM-dd");
				String result = format.format(today1);
				dateList.add(result);
			}
		}	else{
			for (int i = 0; i < 30; i++) {
				if (i != 0) {
					calendar1.set(Calendar.DAY_OF_YEAR, calendar1.get(Calendar.DAY_OF_YEAR) - 1);
				}
				Date today1 = calendar1.getTime();
				SimpleDateFormat format = new SimpleDateFormat("MM-dd");
				String result = format.format(today1);
				dateList.add(result);
			}
		}
		return dateList;
	}
}
