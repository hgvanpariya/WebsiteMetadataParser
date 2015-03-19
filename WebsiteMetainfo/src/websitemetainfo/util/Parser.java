package websitemetainfo.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Parser parser = new Parser();

		String url = "http://searchenginewatch.com/";
		StringBuffer getContent;
		try {
			getContent = parser.parseWebsite(url);
			String titleTag = parser.getTitleTag(getContent);
			String metaDesc = parser.getMetaDiscription(getContent);
			String[] keywords = parser.getKeywords(getContent);
			System.out.println(">>Title" + titleTag);
			System.out.println(">>Descriptions" + metaDesc);
			System.out.println(">>Keywords " + keywords);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method will parse the website and returns the content of the webpage in StringBuffer.
	 * @param urlStr URL of the website with protocol name. E.g. http://www.google.com
	 * @return Content of the webpage opened when the URL entered to browser.
	 * @throws IOException 
	 */
	public StringBuffer parseWebsite(String urlStr)
			throws IOException {
		URL url;
		StringBuffer completeWebsiteContent = new StringBuffer();
			url = new URL(urlStr);
			URLConnection uc = url.openConnection();

			InputStreamReader input = new InputStreamReader(uc.getInputStream());
			BufferedReader in = new BufferedReader(input);
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				completeWebsiteContent.append(inputLine);
			}
			in.close();
		
		return completeWebsiteContent;
	}

	/**
	 * This method will give the string which contains the title of the website.
	 * @param content Complete content of any webpage from which the title has to be fetched.
	 * @return Title of the website in {@link String} format
	 */
	public String getTitleTag(StringBuffer content) {
		String pattern = "(.*)(<[Tt][Ii][Tt][Ll][Ee]>)(.*)(</[Tt][Ii][Tt][Ll][Ee]>)(.*)";
		String titleStr = "";
		// Create a Pattern object
		Pattern titlePtrn = Pattern.compile(pattern);

		// Now create matcher object.
		Matcher titleMatcher = titlePtrn.matcher(content);
		if (titleMatcher.matches()) {
			titleStr = titleMatcher.group(3);
			titleStr =titleStr.replaceFirst("\" */>.*", "");
		}
		titleMatcher.reset();
		return titleStr;
	}

	/**
	 * This method will return the description from the website.
	 * @param content Complete content of any webpage
	 * @return description of the website.
	 */
	public String getMetaDiscription(StringBuffer content) {
		String pattern = "(.*)(<meta itemprop=\"description\" content=\")(.*)(\"/>)(.*)";
		String discription = "";
		// Create a Pattern object
		Pattern titlePtrn = Pattern.compile(pattern);

		// Now create matcher object.
		Matcher titleMatcher = titlePtrn.matcher(content);
		if (titleMatcher.matches()) {
			discription = titleMatcher.group(3);
			discription = discription.replaceFirst("\" */>.*", "");
		}
		titleMatcher.reset();
		return discription;

	}

	/**
	 * Get Keywords details from the given website content as argument.
	 * 
	 * @param content
	 *            Webpage content in String buffer.
	 * @return Keywords
	 */
	public String[] getKeywords(StringBuffer content) {
		String pattern = "(.*)(<meta name=\"keywords\" content=\")(.*)(/>)(.*)";
		String keywords = "";
		String[] allkeywords = null;
		// Create a Pattern object
		Pattern titlePtrn = Pattern.compile(pattern);

		// Now create matcher object.
		Matcher titleMatcher = titlePtrn.matcher(content);
		if (titleMatcher.matches()) {
			keywords = titleMatcher.group(3);
			keywords = keywords.replaceFirst("\" */*>.*", "");
			allkeywords = keywords.split(", *");

		} else {
			return allkeywords;
		}
		titleMatcher.reset();
		return allkeywords;

	}

}
