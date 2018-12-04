package com.ruirados.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class GetFont {
	private Font definedFont = null; 
	//Fontfile  字体文件所在的目录
	public Font getDefinedFont(String Fontfile ,float size) {
		//String fontUrl="E:/Program Files/feiq/Recv Files/SourceHanSansSC/"+ft;
        if (definedFont == null) {  
            InputStream is = null;  
            BufferedInputStream bis = null;  
            try {  
                is =new FileInputStream(new File(Fontfile));
                bis = new BufferedInputStream(is);  
                definedFont = Font.createFont(Font.TRUETYPE_FONT, is);
                //设置字体大小，float型
               definedFont = definedFont.deriveFont(size);
            } catch (FontFormatException e) {  
                e.printStackTrace();  
            } catch (IOException e) {  
                e.printStackTrace();  
            } finally { 
               try {  
                    if (null != bis) {  
                        bis.close();  
                    }  
                    if (null != is) {  
                        is.close();  
                    }  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }
            }  
        }  
        return definedFont;  
    }  
	
}