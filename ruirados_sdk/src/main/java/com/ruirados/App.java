package com.ruirados;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;

import org.apache.http.HttpResponse;
import org.apache.http.message.BasicNameValuePair;

import sun.net.www.http.HttpClient;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	
    	HttpClient as = new HttpClient(new URL("http://www.baidu.com"), null, 0);
        System.out.println( "Hello World!" );
    }
    
    
    
    
}
