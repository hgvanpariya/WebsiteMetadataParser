package websitemetainfo.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;


import org.junit.Before;
import org.junit.Test;

import websitemetainfo.util.Parser;

public class ParserTest {


	Parser parser = new Parser();
	
	@Before
	public void setUp(){
		
	}
	
	@Test
	public void testParseWebsite() throws IOException{
		String url = "http://www.google.com";
		StringBuffer getContent = parser.parseWebsite(url);
		assertTrue(getContent  != null);
	}
	
	
	@Test
	public void testParseWebsiteWithInvalidURl() throws IOException{
		String url = "";
		StringBuffer getContent = parser.parseWebsite(url);
		assertTrue(getContent != null);
	}
	
	public void getTitletagFromContent() throws IOException{
		String url = "http://www.google.com";
		StringBuffer getContent = parser.parseWebsite(url);
		String titleTag = parser.getTitleTag(getContent);
		System.out.println("Title Tag : "+titleTag);
	}
}
