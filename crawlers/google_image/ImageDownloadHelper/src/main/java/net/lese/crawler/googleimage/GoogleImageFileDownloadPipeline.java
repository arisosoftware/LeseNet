package net.lese.crawler.googleimage;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.codec.digest.DigestUtils;

import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class GoogleImageFileDownloadPipeline implements Pipeline {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private DigestUtils md5Util = new DigestUtils();
	private String saveFolder = "";
	private int picDownloadThread;

	public GoogleImageFileDownloadPipeline() {
		saveFolder = "/tmp/webmagic/";
		picDownloadThread = 2;
	}

	public GoogleImageFileDownloadPipeline(String path) {
		saveFolder = path;
	}

	public GoogleImageFileDownloadPipeline(String savePath, int picDownloadThread) {
		this.saveFolder = savePath;
		this.picDownloadThread = picDownloadThread;
		File rootPath = new File(savePath);
		rootPath.mkdirs();
	}

	void SaveByteArray(Map.Entry<String, Object> entry) {
		try {
			byte[] data = (byte[]) entry.getValue();
			String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(data);
			File TargetFile = new File(String.format("%s/%s", saveFolder, md5));
			org.apache.commons.io.FileUtils.writeByteArrayToFile(TargetFile, data);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	ImageDownloadUtil util = new ImageDownloadUtil();

	void DownloadImageUrl(Map.Entry<String, Object> entry) {
		try {
			String key = entry.getKey();
			String url = (String) entry.getValue();
			String realfile = util.downLoadImage(url, saveFolder);
			logger.info(String.format( "Success DownloadImageUrl  %s from URL %s", realfile, url));
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void process(ResultItems resultItems, Task task) {

		String processUrl = resultItems.getRequest().getUrl();
		logger.debug(String.format("GoogleImageFileDownloadPipeline get page:  %s ", processUrl));

		for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {

			try {
				String key = entry.getKey();
				
				logger.info(String.format("Get key %s, value %s", key, (String)entry.getValue()));
				
				if (key.startsWith(App.FullSizeImage)) {
					DownloadImageUrl(entry);
				} else if (key.startsWith(App.SampleImage)) {
					DownloadImageUrl(entry);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
