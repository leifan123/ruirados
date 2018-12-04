package com.ruirados.controller;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amazonaws.services.s3.AmazonS3;
import com.ruirados.pojo.OssObject;
import com.ruirados.pojo.OssPicture;
import com.ruirados.pojo.RspData;
import com.ruirados.service.OssObjectService;
import com.ruirados.service.OssPictureService;
import com.ruirados.util.BucketAclUtil;
import com.ruirados.util.ExptNum;
import com.ruirados.util.GetFont;
import com.ruirados.util.JSONUtils;
import com.ruirados.util.MappingConfigura;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.filters.Caption;
import net.coobird.thumbnailator.filters.ImageFilter;
import net.coobird.thumbnailator.geometry.Positions;
import sun.misc.BASE64Encoder;
@Controller
@RequestMapping(MappingConfigura.PICTURE)
public class PictureManageController {
	
	
	@Autowired
	private OssObjectService ossObjectService;
	@Autowired
	private OssPictureService ossPictureService;
	
	Logger log = Logger.getLogger(getClass());
	//处理图片的输出源位置
	protected final String FILE = "WaterPicture/watertext.";
	
	//字体存放位置
	protected final String FONT_FILE = "systemFontFamliy/";

	//样本图片位置
	protected final String ORIGINAL_FILE = "WaterPicture/oringinalPic.jpg";
	
	private String filePath = PictureManageController.class.getResource("/").getPath();
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
	
	protected final Integer JPG = 1;
	protected final Integer PNG = 2;
	protected final Integer GIF = 3;
	protected final Integer WEBP = 4;
	//预览图
	@RequestMapping(value = "/picturePreview", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String pictureManage(HttpServletRequest request, HttpServletResponse response, OssPicture ossPicture) throws MalformedURLException {
		 
		RspData rd = new RspData();
		log.debug("color:"+ossPicture.getColor());
		String color = ossPicture.getColor();
		int[] num = new int[3];
		if(ossPicture.getWaterprinttype()==2){
			
			try {	
				String[] colorNum = color.split(", ");
				num[0] = Integer.parseInt(colorNum[0]);
				num[1] = Integer.parseInt(colorNum[1]);
				num[2] = Integer.parseInt(colorNum[2]);

			} catch (Exception e) {
				
				rd.setStatus(ExptNum.PARAM_NO_ALL.getCode() + "");
				rd.setMsg(ExptNum.PARAM_NO_ALL.getDesc());
				return JSONUtils.createObjectJson(rd);
			}
		}
				
		
		if(ossPicture.getWatermarktransparency()!=null){
			ossPicture.setWatermarktransparency(ossPicture.getWatermarktransparency()/100);
		}
		
		log.error("获取object");
		List<OssObject> waterOssObjectList = ossObjectService.selectByParam("", "companyId='"+ossPicture.getCompanyid()+"'"+" and fileSrc='"+ossPicture.getWaterurl()+"'"+" and zoneId='"+ossPicture.getZoneid()+"'"+" and bucket_name='"+ossPicture.getBucketname()+"'");
		URL waterUrl = null;
		//获取外联
		if(ossPicture.getWaterprinttype()==1){
    		if(waterOssObjectList.isEmpty()){
    			rd.setStatus(ExptNum.PICTURE_NO_EXIT.getCode() + "");
    			rd.setMsg(ExptNum.PICTURE_NO_EXIT.getDesc());
    			return JSONUtils.createObjectJson(rd);		
    		}
    		AmazonS3 conn = BucketAclUtil.getConnByCache(ossPicture.getCompanyid(), ossPicture.getZoneid(), 1, false);
    		Calendar expirationCalendar = Calendar.getInstance();
    		expirationCalendar.add(Calendar.DAY_OF_YEAR, 999999999);
    	    waterUrl = conn.generatePresignedUrl(ossPicture.getBucketname(), ossPicture.getWaterurl(), expirationCalendar.getTime());
			
        }
		
		

		try {
			//图片位置
			log.error("处理图片");
			Builder<File> pic = Thumbnails.of(new File(filePath+ORIGINAL_FILE));
			
			Map<String,Object> bytepic= pictureRsolve(response, ossPicture, ossPicture.getWaterurl(),pic,waterUrl,num);
			log.error("处理成功");
			rd.setStatus(ExptNum.SUCCESS.getCode() + "");
			rd.setMsg(ExptNum.SUCCESS.getDesc());
			rd.setData(bytepic);
		} catch (Exception e) {
			log.error(e);
			rd.setStatus(ExptNum.FAIL.getCode() + "");
			rd.setMsg(ExptNum.FAIL.getDesc());
		}
		return JSONUtils.createObjectJson(rd);

	}
		
	//新建样式
	@RequestMapping(value = "/creatStyle", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String creatStyle(OssPicture ossPicture ) throws ParseException {
		
		RspData rd = new RspData();
		log.error("获取object");

		if(ossPicture.getWaterprinttype()==1){
			List<OssObject> ossObjectList = ossObjectService.selectByParam("", "companyId='"+ossPicture.getCompanyid()+"'"+" and zoneId='"+ossPicture.getZoneid()+"'"+" and bucket_name='"+ossPicture.getBucketname()+"'"+" and fileName='"+ossPicture.getWaterurl()+"'");
			if(ossObjectList.isEmpty()){
				rd.setStatus(ExptNum.PICTURE_NO_EXIT.getCode() + "");
				rd.setMsg(ExptNum.PICTURE_NO_EXIT.getDesc());
				return JSONUtils.createObjectJson(rd);		
			}
		}
		
		Date date1 = new Date();
		String str = dateFormat.format(date1);
		ossPicture.setCreateTime(dateFormat.parse(str));
		ossPicture.setEndtime(dateFormat.parse(str));
		try {
			ossPictureService.insert(ossPicture);
			rd.setStatus(ExptNum.SUCCESS.getCode() + "");
			rd.setMsg(ExptNum.SUCCESS.getDesc());
		} catch (Exception e) {
			log.error(e);
			rd.setStatus(ExptNum.FAIL.getCode() + "");
			rd.setMsg(ExptNum.FAIL.getDesc());
		}
		return JSONUtils.createObjectJson(rd);
		

	}
	
	//修改样式
	
	@RequestMapping(value = "/editStyle", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String editStyle(OssPicture ossPicture) {
		RspData rd = new RspData();
		if(ossPicture.getWaterprinttype()==1){
			List<OssObject> ossObjectList = ossObjectService.selectByParam("", "companyId='"+ossPicture.getCompanyid()+"'"+" and zoneId='"+ossPicture.getZoneid()+"'"+" and bucket_name='"+ossPicture.getBucketname()+"'"+" and fileName='"+ossPicture.getWaterurl()+"'");
			if(ossObjectList.isEmpty()){
				rd.setStatus(ExptNum.PICTURE_NO_EXIT.getCode() + "");
				rd.setMsg(ExptNum.PICTURE_NO_EXIT.getDesc());
				return JSONUtils.createObjectJson(rd);		
			}
		}
		
		try {
			ossPictureService.update(ossPicture);
			rd.setStatus(ExptNum.SUCCESS.getCode() + "");
			rd.setMsg(ExptNum.SUCCESS.getDesc());
		} catch (Exception e) {
			log.error(e);
			rd.setStatus(ExptNum.FAIL.getCode() + "");
			rd.setMsg(ExptNum.FAIL.getDesc());
		}
		return JSONUtils.createObjectJson(rd);
		

	}
	//删除样式
	@RequestMapping(value = "/deleteStyle", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	
	@ResponseBody
	public String deleteStyle(OssPicture ossPicture) {
		RspData rd = new RspData();
		try {
			ossPictureService.delete(ossPicture);
			rd.setStatus(ExptNum.SUCCESS.getCode() + "");
			rd.setMsg(ExptNum.SUCCESS.getDesc());
		} catch (Exception e) {
			log.error(e);
			rd.setStatus(ExptNum.FAIL.getCode() + "");
			rd.setMsg(ExptNum.FAIL.getDesc());
		}
		return JSONUtils.createObjectJson(rd);
		

	}
	
	//列出样式
	@RequestMapping(value = "/listStyle", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String listStyle(OssPicture ossPicture) {
		RspData rd = new RspData();
		try {
			List<OssPicture> ossPictureList= ossPictureService.select(ossPicture);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("ossPictureList", ossPictureList);
			rd.setStatus(ExptNum.SUCCESS.getCode() + "");
			rd.setMsg(ExptNum.SUCCESS.getDesc());
			rd.setData(map);
		} catch (Exception e) {
			log.error(e);
			rd.setStatus(ExptNum.FAIL.getCode() + "");
			rd.setMsg(ExptNum.FAIL.getDesc());
		}
		return JSONUtils.createObjectJson(rd);
		

	}
	
	
	//列出字体
	@RequestMapping(value = "/listFonts", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getFonts(){
		 String[] fonts = 
		            GraphicsEnvironment  // GraphicsEnvironment(抽象类)  图形环境类
		                .getLocalGraphicsEnvironment()  // 获取本地图形环境
		                    .getAvailableFontFamilyNames();  // 获取可用字体family名
		        List<String> fontsList = new ArrayList<String>();
		        for(String font : fonts) {
		        	fontsList.add(font);
		        }
		        log.debug("系统字体数:" + fontsList.toArray());
		        return JSONUtils.createObjectJson(fontsList);
	}

//图片处理
	public Map<String,Object> pictureRsolve(HttpServletResponse response, OssPicture ossPicture, String fileName,Builder pic ,URL waterUrl,int[] num) {
		log.error(pic.toString());
		Map<String,Object> picdata = new HashMap<String,Object>();
		 String picType = null;
		 
		
		if (ossPicture.getOutformattype() == JPG) {
			picType = "jpg";
		}
		if (ossPicture.getOutformattype() == PNG) {
			picType = "png";
		}
		if (ossPicture.getOutformattype() == GIF) {
			picType = "gif";
		}
		if (ossPicture.getOutformattype() == WEBP) {
			picType = "webp";
		}else{
			picType = "jpg";
		}
		int picresove = 0;
		
		

		try {


			File picture = new File(filePath+ORIGINAL_FILE);
			 BufferedImage	sourceImg = ImageIO.read(new FileInputStream(picture));
		
			//log.error("ossPicture.getResizertype():"+ossPicture.getResizertype()+"  ****" +ossPicture.getResizerpercent());
			
			if(ossPicture.getResizertype()==1){
				//等比缩放
				 pic = pic.scale(ossPicture.getResizerpercent()/100.0);
				log.debug("设置长度1"+ossPicture.getResizerpercent()/100.0);
			}
			//长度优先
			
			else if(ossPicture.getResizertype()==2){
				
				log.debug("设置长度2");
				
				if(ossPicture.getHeight()>=sourceImg.getHeight()){
					 pic = pic.size(ossPicture.getWidth(), sourceImg.getHeight());
				}else{
					 pic = pic.size(ossPicture.getWidth(), sourceImg.getHeight());
				}
			}
			//宽度优先
			else if(ossPicture.getResizertype()==3){
				log.debug("设置长度3");
				if(ossPicture.getWidth()>=sourceImg.getWidth()){
					 pic = pic.size(ossPicture.getWidth(), sourceImg.getWidth());
				}else{
					 pic = pic.size(ossPicture.getWidth(), sourceImg.getHeight());
				}
			}		
			else if(ossPicture.getResizertype()==4){
				log.debug("设置长度4");
				 pic = pic.size(ossPicture.getWidth(), ossPicture.getHeight());
			}else if(ossPicture.getResizertype()==0){
				log.debug("设置长度0");
				 pic = pic.size(sourceImg.getHeight(),sourceImg.getWidth());
			}
			//居中剪裁
			else if(ossPicture.getResizertype()==5){
				pic = pic.sourceRegion(Positions.CENTER, ossPicture.getWidth(), ossPicture.getHeight()).size(ossPicture.getWidth(), ossPicture.getHeight()).keepAspectRatio(false);
			}

			//图片旋转角度
			if(ossPicture.getRotatetype() !=null && ossPicture.getRotatetype() == 1){
				pic = pic.rotate(ossPicture.getRotationangle());
			}else{
				pic = pic.rotate(0);
			}
		    
			//设置图片格式
			if (picType != null) {
				 pic = pic.outputFormat(picType);
			}
			

			if(ossPicture.getWaterprinttype()==1){
				//水印
				BufferedImage waterPicture = ImageIO.read(waterUrl);
				int picPosition= ossPicture.getWateroffsettype();
				
				switch(picPosition){
				case 1 :  pic = pic.watermark(Positions.TOP_LEFT, waterPicture,ossPicture.getWatermarktransparency(),
						ossPicture.getHorizontaloff(), ossPicture.getVerticaloff());
				        break;
				case 2 : pic =  pic.watermark(Positions.TOP_CENTER, waterPicture,ossPicture.getWatermarktransparency(),
						ossPicture.getHorizontaloff(), ossPicture.getVerticaloff());
				        break;
				case 3 : pic =  pic.watermark(Positions.TOP_RIGHT, waterPicture,ossPicture.getWatermarktransparency(),
						ossPicture.getHorizontaloff(), ossPicture.getVerticaloff());
				        break;
				case 4 : pic =  pic.watermark(Positions.CENTER_LEFT, waterPicture,ossPicture.getWatermarktransparency(),
						ossPicture.getHorizontaloff(), ossPicture.getVerticaloff());
				        break;
				case 5 : pic =  pic.watermark(Positions.CENTER, waterPicture, ossPicture.getWatermarktransparency(),
						ossPicture.getHorizontaloff(), ossPicture.getVerticaloff());
				        break;
				case 6 : pic =  pic.watermark(Positions.CENTER_RIGHT, waterPicture, ossPicture.getWatermarktransparency(),
						ossPicture.getHorizontaloff(), ossPicture.getVerticaloff());
				        break;
				case 7 : pic =  pic.watermark(Positions.BOTTOM_LEFT, waterPicture,ossPicture.getWatermarktransparency(),
						ossPicture.getHorizontaloff(), ossPicture.getVerticaloff());
				        break;
				case 8 : pic =  pic.watermark(Positions.BOTTOM_CENTER, waterPicture,ossPicture.getWatermarktransparency(),
						ossPicture.getHorizontaloff(), ossPicture.getVerticaloff());
				        break;
				case 9 : pic = pic.watermark(Positions.BOTTOM_RIGHT, waterPicture, ossPicture.getWatermarktransparency(),
						ossPicture.getHorizontaloff(), ossPicture.getVerticaloff());
				        break;
				}			

			}
			
			
			
			
			
			
			
			//图片输出质量
			log.error("图片质量"+ossPicture.getOutquality()/100f);
			
			 pic = pic.outputQuality(ossPicture.getOutquality()/100f);
			 
	
				//文字水印
				BufferedImage resultImage = null ;
				if(ossPicture.getWaterprinttype()==2){
					//获取字体
					GetFont getFont = new GetFont();
					String fontfile = filePath+ FONT_FILE + ossPicture.getFont();
				
					BufferedImage originalImage1 = pic.asBufferedImage();
					int picPosition= ossPicture.getWateroffsettype();
					log.debug("11111");
					//BufferedImage originalImage1 = ImageIO.read(new File("src/main/resources/img/333.jpg"));
					//BufferedImage copyImage = BufferedImages.copy(originalImage1);
					//Color color = new Color(picPosition);
				
					switch(picPosition){
					case 1 :  
							 ImageFilter filter1 = new Caption(ossPicture.getText(),
									// new Font(ossPicture.getFont(), Integer.parseInt(ossPicture.getThickness()), ossPicture.getFontsize()),
									 getFont.getDefinedFont(fontfile, ossPicture.getFontsize()),
									 new Color(num[0],num[1],num[2]),
									 Positions.TOP_LEFT,
									 ossPicture.getWatermarktransparency(),
									 0,
									 ossPicture.getHorizontaloff(),
									 ossPicture.getVerticaloff());
							 resultImage = filter1.apply(originalImage1);
						        break;
					case 2 :ImageFilter filter2 = new Caption(ossPicture.getText(),
							  getFont.getDefinedFont(fontfile, ossPicture.getFontsize()),
							 new Color(num[0],num[1],num[2]),Positions.TOP_CENTER,ossPicture.getWatermarktransparency(),0,
							 ossPicture.getHorizontaloff(),ossPicture.getVerticaloff());
					         resultImage = filter2.apply(originalImage1);
					        break;
					case 3 :
						        ImageFilter filter3 = new Caption(ossPicture.getText(),
						          getFont.getDefinedFont(fontfile, ossPicture.getFontsize()),
								 new  Color(num[0],num[1],num[2]),Positions.BOTTOM_RIGHT,ossPicture.getWatermarktransparency(),0,
								 ossPicture.getHorizontaloff(),ossPicture.getVerticaloff());
						         resultImage = filter3.apply(originalImage1);

						     break;
					case 4 :ImageFilter filter4 = new Caption(ossPicture.getText(),
							  getFont.getDefinedFont(fontfile, ossPicture.getFontsize()),
							 new  Color(num[0],num[1],num[2]),Positions.CENTER_LEFT,ossPicture.getWatermarktransparency(),0,
							 ossPicture.getHorizontaloff(),ossPicture.getVerticaloff());
					         resultImage = filter4.apply(originalImage1);
					        break;
					case 5 :ImageFilter filter5 = new Caption(ossPicture.getText(),
							  getFont.getDefinedFont(fontfile, ossPicture.getFontsize()),
							 new  Color(num[0],num[1],num[2]),Positions.CENTER,ossPicture.getWatermarktransparency(),0,
							 ossPicture.getHorizontaloff(),ossPicture.getVerticaloff());
					         resultImage = filter5.apply(originalImage1);
					        break;
					case 6 :ImageFilter filter6 = new Caption(ossPicture.getText(),
							  getFont.getDefinedFont(fontfile, ossPicture.getFontsize()),
							 new  Color(num[0],num[1],num[2]),Positions.BOTTOM_RIGHT,ossPicture.getWatermarktransparency(),0,
							 ossPicture.getHorizontaloff(),ossPicture.getVerticaloff());
					         resultImage = filter6.apply(originalImage1);
					        break;
					case 7 :ImageFilter filter7 = new Caption(ossPicture.getText(),
							  getFont.getDefinedFont(fontfile, ossPicture.getFontsize()),
							 new  Color(num[0],num[1],num[2]),Positions.BOTTOM_LEFT,ossPicture.getWatermarktransparency(),0,
							 ossPicture.getHorizontaloff(),ossPicture.getVerticaloff());
					         resultImage = filter7.apply(originalImage1);
					        break;
					case 8 :ImageFilter filter8 = new Caption(ossPicture.getText(),
							  getFont.getDefinedFont(fontfile, ossPicture.getFontsize()),
							 new  Color(num[0],num[1],num[2]),Positions.BOTTOM_CENTER,ossPicture.getWatermarktransparency(),0,
							 ossPicture.getHorizontaloff(),ossPicture.getVerticaloff());
					         resultImage = filter8.apply(originalImage1);
					        break;
					case 9 : ImageFilter filter9 = new Caption(ossPicture.getText(),
							  getFont.getDefinedFont(fontfile, ossPicture.getFontsize()),
							 new  Color(num[0],num[1],num[2]),Positions.BOTTOM_RIGHT,ossPicture.getWatermarktransparency(),0,
							 ossPicture.getHorizontaloff(),ossPicture.getVerticaloff());
					         resultImage = filter9.apply(originalImage1);
					        break;
					}	
						
					//int type = resultImage.getType();
				}
				
				log.debug("222222");
			//输出
		   
			File file  = null;
			Builder<File> picQuality = null;
			if(ossPicture.getWaterprinttype()==2){
				log.debug("输出文字水印");
				
				Thumbnails.of(resultImage).size(resultImage.getWidth(), resultImage.getHeight()).toFile(filePath+FILE + picType);
				picQuality = Thumbnails.of(new File(filePath+FILE + picType));
				
				BufferedImage waterPictureQuality = ImageIO.read(new File(filePath+FILE + picType));
				
				picQuality.size(waterPictureQuality.getWidth(), waterPictureQuality.getHeight())
				.outputQuality(ossPicture.getOutquality()/100f).toFile(filePath+FILE + picType);
				file = new File(filePath+FILE + picType);
			}else{
				log.debug("输出图片水印");
				pic.toFile(filePath+FILE + picType);
				picQuality = Thumbnails.of(new File(filePath+FILE + picType));
				
				BufferedImage waterPictureQuality = ImageIO.read(new File(filePath+FILE + picType));
				
				picQuality.size(waterPictureQuality.getWidth(), waterPictureQuality.getHeight())
				.outputQuality(ossPicture.getOutquality()/100f).toFile(filePath+FILE + picType);
				
				file = new File(filePath+FILE + picType);
			}
			
			
			
			
			
			//InputStream ins = new FileInputStream(file);
			

		     boolean picresulve = false;
			 BufferedImage	sourceImgsize = ImageIO.read(new FileInputStream(file));
			 if(sourceImgsize.getWidth()>250 || sourceImgsize.getHeight()>250){
				 picresulve = true;
			 }
			
			 InputStream in = null;
			    byte[] data = null;
			    // 读取图片字节数组
			    try {
			        in = new FileInputStream(file);
			        data = new byte[in.available()];
			        in.read(data);
			        in.close();
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
			    // 对字节数组Base64编码
			    BASE64Encoder encoder = new BASE64Encoder();
			    // 返回Base64编码过的字节数组字符串
			    String pictures = encoder.encode(data);
			    
			    picdata.put("picresulve", picresulve);
			    picdata.put(pictures, pictures);

		} catch (IOException e) {
			log.error(e);
		}
		
		
		
		return picdata;
		
		//return picresove;
		}

}
