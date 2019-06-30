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

	public static void main(String[] args) {

		String keyword = "cookie";

		String url = "https://www.google.com/search?q=" + keyword
				+ "&newwindow=1&source=lnms&tbm=isch&sa=X&ved=0ahUKEwje9p6u3Y_jAhWHUs0KHcZsC3cQ_AUIECgB&biw=1120&bih=636";

		String savePath = "/tmp/t1";
		int picDownloadThread = 5;
		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		Spider spider = Spider.create(new GoogleImagePageProcessor());
		spider.thread(3);
		spider.addPipeline(new GoogleImageFileDownloadPipeline(savePath, picDownloadThread));
		spider.addUrl(url);
		spider.start();
	}
}
