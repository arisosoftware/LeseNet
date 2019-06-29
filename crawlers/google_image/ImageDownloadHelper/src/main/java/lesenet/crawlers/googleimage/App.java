package lesenet.crawlers.googleimage;

import us.codecraft.webmagic.Spider;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");

		Spider
			.create(new GithubRepoPageProcessor())
			.addUrl("https://github.com/code4craft")
			.thread(5)
			.run();
		
		
	}
}
