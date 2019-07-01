package net.lese.crawler.googleimage;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class GoogleImagePageProcessor implements PageProcessor {

	static long SeqId = 10000;
	private Site site = Site.me().setRetryTimes(10).setSleepTime(5000).addHeader("User-Agent", "Chrome");

	protected Logger logger = LoggerFactory.getLogger(getClass());

	public Site getSite() {
		return site;
	}

	void DebugPageText(Page page) {
		SeqId++;
		String url = page.getRequest().getUrl();
		String digest = "/tmp/t2/HTML." + SeqId + "." + DigestUtils.md5Hex(url) + ".htm";
		try {
			FileUtils.writeByteArrayToFile(new File(digest), page.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

	public void process(Page page) {

		URLCodec encoder = new URLCodec();

		String currUrl = page.getRequest().getUrl();
		logger.info(String.format("Debug process(Page page) %s", currUrl));

		DebugPageText(page);

		if (page.getRequest().isBinaryContent()) {
			page.putField("png", page.getBytes());
		} else {

			List<String> list = page.getHtml().xpath("//*[@id=\"ires\"]/table/tbody/tr/td/a").all();
			List<String> imageList = page.getHtml().xpath("//*[@id=\"ires\"]/table/tbody/tr/td/a/img").all();

			for (String picKey : list) {
				if (picKey.trim().length() == 0) {
					continue;
				}

				String url = StringUtils.substringBetween(picKey, "<a href=\"/url?q=", "&amp;sa=U&amp;");

				String digest = App.FullSizeImage + "." + DigestUtils.md5Hex(url);
				page.putField(digest, url);

				logger.debug(String.format("Add Download source image:%s", url));
			}

			for (String picKey : imageList) {
				if (picKey.trim().length() == 0) {
					continue;
				}
				String imgurl = StringUtils.substringBetween(picKey, "\" src=\"", "\" width=\"");
				String digest = App.SampleImage + "." + DigestUtils.md5Hex(imgurl);
				page.putField(digest, imgurl);

				logger.debug(String.format("Add Download googlecache image:%s", imgurl));
			}

			List<String> pageList = page.getHtml().xpath("//*[@id=\"nav\"]/tbody/tr/td[12]/a").all();
			if (pageList.size() > 0) {
				String nextpage = pageList.get(0);

				nextpage = StringUtils.substringBetween(nextpage, "\"fl\" href=\"/", "\" style=\"text-align:left\">");

				nextpage = "https://www.google.com/" + nextpage;

				nextpage = App.decode(nextpage);

				logger.info(String.format("nextpage:%s", nextpage));
				page.addTargetRequest(nextpage);
			}
		}

	}

}
