package net.lese.crawler.googleimage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class GoogleImagePageProcessor implements PageProcessor {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private Site site = Site.me().setRetryTimes(10).setSleepTime(5000).addHeader("User-Agent", "Chrome");
	// "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.6) Gecko/20091201
	// Firefox/3.5.6");

	public Site getSite() {
		return site;
	}

	public void process(Page page) {

		Set<String> picUrlSet = new HashSet<String>();
 
		List<String> list = page.getHtml().xpath("//*[@id=\"ires\"]/table/tbody/tr/td/a").all();

		List<String> imageList = page.getHtml().xpath("//*[@id=\"ires\"]/table/tbody/tr/td/a/img").all();

		for (String picKey : list) {
			if (picKey.trim().length() == 0) {
				continue;
			}
			
			String url = StringUtils.substringBetween(picKey, "<a href=\"/url?q=", "&amp;sa=U&amp;")  ;
			
			page.putField("src", url);
			logger.debug("download:"+url  );
		}

		
		for (String picKey : imageList) {
			if (picKey.trim().length() == 0) {
				continue;
			}
			String imgurl = StringUtils.substringBetween(picKey, "\" src=\"", "\" width=\"")  ;
			
			page.putField("img", imgurl);
			logger.debug("tinyImage:"+imgurl  );

		}
		
		List<String> pageList = page.getHtml().xpath("//*[@id=\"nav\"]/tbody/tr/td[12]/a").all();
		String nextpage = pageList.get(pageList.size()-1);
		
		logger.debug("nextpage:"+nextpage  );
	//	page.addTargetRequest(nextpage);
		
		 
	}

}
