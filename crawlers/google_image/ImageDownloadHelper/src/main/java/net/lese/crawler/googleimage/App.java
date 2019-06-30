package net.lese.crawler.googleimage;

import us.codecraft.webmagic.Spider;

public class App {
//	public static void main1(String[] args) {
//
//	
//curl -k -sA "Chrome" -L 'https://www.google.com/search?hl=en&q=time' -o ssearch.html
//		Spider.create(new GoogleImagePageProcessor()).addUrl("https://github.com/code4craft").thread(5).run();
// curl -k -sA "Chrome" -L  'https://www.google.com/search?q=cookie&newwindow=1&source=lnms&tbm=isch&sa=X&ved=0ahUKEwje9p6u3Y_jAhWHUs0KHcZsC3cQ_AUIECgB&biw=1120&bih=636' -o search.html
 
//	}

	public static String FullSizeImage ="FullSize";
	public static String SampleImage ="Sample";
	
	
	public static void main(String[] args) {

		String keyword = "cookie";

		String url = "https://www.google.com/search?q=" + keyword
				+ "&newwindow=1&source=lnms&tbm=isch&sa=X&ved=0ahUKEwje9p6u3Y_jAhWHUs0KHcZsC3cQ_AUIECgB&biw=1120&bih=636";

		String savePath = "/tmp/t1";
		int picDownloadThread = 1;
		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
	 //	System.setProperty("log4j2.debug", "true");
		
		
		Spider spider = Spider.create(new GoogleImagePageProcessor());
		spider.thread(1);
		spider.getSite().setCharset("UTF-8");
		spider.addPipeline(new GoogleImageFileDownloadPipeline(savePath, picDownloadThread));
		spider.addUrl(url);
		spider.start();
	}
}
