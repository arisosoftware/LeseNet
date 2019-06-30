package net.lese.crawler.googleimage;

import java.io.File;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class GoogleImageFileDownloadPipeline implements Pipeline {
	private Logger logger = LoggerFactory.getLogger(getClass());

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

	public void process(ResultItems resultItems, Task task) {
		logger.debug("GoogleImageFileDownloadPipeline get page: " + resultItems.getRequest().getUrl());

		for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
			logger.debug(entry.getKey() + ":\t" + entry.getValue());
		}
	}
	
    
}
