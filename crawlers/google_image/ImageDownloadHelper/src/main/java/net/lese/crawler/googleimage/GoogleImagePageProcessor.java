package net.lese.crawler.googleimage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class GoogleImagePageProcessor implements PageProcessor {

	private Site site = Site.me().setRetryTimes(10).setSleepTime(5000).addHeader("User-Agent", "Chrome");

	private Logger logger = LogManager.getLogger();

	public Site getSite() {
		return site;
	}

	public void process(Page page) {

		String currUrl = page.getRequest().getUrl();
		logger.info("Debug process(Page page) %s", currUrl);

		if (page.getRequest().isBinaryContent()) {
			page.putField("png", page.getBytes());
		} else {

			List<String> list = page.getHtml().xpath("//*[@id=\"ires\"]/table/tbody/tr/td/a").all();
			List<String> imageList = page.getHtml().xpath("//*[@id=\"ires\"]/table/tbody/tr/td/a/img").all();

			if (list.size() == 0 || imageList.size() == 0) {
				return;
			}

			for (String picKey : list) {
				if (picKey.trim().length() == 0) {
					continue;
				}

				String url = StringUtils.substringBetween(picKey, "<a href=\"/url?q=", "&amp;sa=U&amp;");
				// page.addTargetRequest(url);
				page.putField(App.FullSizeImage, url);
				logger.debug("Download source image:%s", url);
			}

			for (String picKey : imageList) {
				if (picKey.trim().length() == 0) {
					continue;
				}
				String imgurl = StringUtils.substringBetween(picKey, "\" src=\"", "\" width=\"");
				page.putField(App.SampleImage, imgurl);
				logger.debug("Download googlecache image:%s", imgurl);

			}

			List<String> pageList = page.getHtml().xpath("//*[@id=\"nav\"]/tbody/tr/td[12]/a").all();
			if (pageList.size() > 0) {
				String nextpage = pageList.get(0);

				nextpage = StringUtils.substringBetween(nextpage, "\"fl\" href=\"/", "\" style=\"text-align:left\">");

				nextpage = "https://google.com/" + nextpage;
				logger.debug("nextpage:%s", nextpage);
				page.addTargetRequest(nextpage);
			}
		}

	}

}
