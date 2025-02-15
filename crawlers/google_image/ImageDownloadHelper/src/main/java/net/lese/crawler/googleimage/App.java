package net.lese.crawler.googleimage;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import org.yaml.snakeyaml.Yaml;
public class App {
//	public static void main1(String[] args) {
//
//	
//curl -k -sA "Chrome" -L 'https://www.google.com/search?hl=en&q=time' -o ssearch.html
//		Spider.create(new GoogleImagePageProcessor()).addUrl("https://github.com/code4craft").thread(5).run();
// curl -k -sA "Chrome" -L  'https://www.google.com/search?q=cookie&newwindow=1&source=lnms&tbm=isch&sa=X&ved=0ahUKEwje9p6u3Y_jAhWHUs0KHcZsC3cQ_AUIECgB&biw=1120&bih=636' -o search.html

//	}

	public static String FullSizeImage = "FullSize";
	public static String SampleImage = "Sample";

	public static void main(String[] args) {

		String keyword = "cookie";

		String url = "https://www.google.com/search?q=" + keyword
				+ "&newwindow=1&source=lnms&tbm=isch&sa=X&ved=0ahUKEwje9p6u3Y_jAhWHUs0KHcZsC3cQ_AUIECgB&biw=1120&bih=636";

		String savePath = "/tmp/t1";
		int picDownloadThread = 1;
		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		// System.setProperty("log4j2.debug", "true");

		Spider spider = Spider.create(new GoogleImagePageProcessor());
		spider.thread(1);
		spider.getSite().setRetryTimes(3).setSleepTime(1000).setUseGzip(true).setCharset("UTF-8")
				.setUserAgent("Chrome");
		// setUserAgent("Opera/9.80 (Windows NT 6.1; U; en) Presto/2.2.15
		// Version/10.00");
		spider.addPipeline(new GoogleImageFileDownloadPipeline(savePath, picDownloadThread));
		spider.addUrl(url);

		spider.start();
	}

	public static String decode(String url) {
		String prevURL = "";
		String decodeURL = url;
		while (!prevURL.equals(decodeURL)) {
			prevURL = decodeURL;
			decodeURL = Jsoup.parse(decodeURL).text();

		}
		return decodeURL;
	}

	static void TestDecode() {
//		String newUrl = "https://www.google.com/search?q=cookie&amp;newwindow=1&amp;biw=1120&amp;bih=636&amp;ie=UTF-8&amp;tbm=isch&amp;ei=UkoZXeLnMIi5tAbqvYfIBw&amp;start=20&amp;sa=N";
//		newUrl = decode(newUrl);
//		System.out.println(newUrl);
//		System.exit(0);
//	
	}
	
	static void TestYaml() 
	{
		try {
			String yaml = FileUtils.readFileToString(new File("/tmp/test.yaml"));
//			
//		     Yaml yaml = new Yaml();
//		      try (InputStream in = YamlToTreeNodeCreator.class.getResourceAsStream(yamlFile)) {
//		          Iterable<Object> itr = yaml.loadAll(in);
//		          for (Object o : itr) {
//		              if (o instanceof Map) {
//		                  Map<?, ?> map = (Map) o;
//		                  for (Map.Entry<?, ?> entry : map.entrySet()) {
//		                      DefaultMutableTreeNode root = new DefaultMutableTreeNode(entry.getKey());
//		                      createTreeNode(entry.getValue(), root);
//		                      return root;
//		                  }
//		              }
//		          }
		} catch (IOException e) {
		 
			e.printStackTrace();
		}
		
	}
	

}
