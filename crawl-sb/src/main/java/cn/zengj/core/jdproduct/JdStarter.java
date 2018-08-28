package cn.zengj.core.jdproduct;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.apache.http.message.BasicHeader;
import org.springframework.stereotype.Component;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

@Component
public class JdStarter extends WebCrawler {
	
	
	String seed = "https://m.jd.com/";
	String crawlStorageFolder = "/crawl";
	int numberOfCrawlers = 5;

	public void start() throws Exception {
		

		CrawlConfig config = new CrawlConfig();
		
		config.setCrawlStorageFolder(crawlStorageFolder);
		config.setUserAgentString(
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
		Set<BasicHeader> collections = new HashSet<BasicHeader>();
		collections.add(new BasicHeader("Cookie", ""));
		config.setDefaultHeaders(collections);

		/*
		 * Instantiate the controller for this crawl.
		 */
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

		/*
		 * For each crawl, you need to add some seed urls. These are the first
		 * URLs that are fetched and then the crawler starts following links
		 * which are found in these pages
		 */
		controller.addSeed(seed);
		// controller.addSeed("http://www.ics.uci.edu/~welling/");
		// controller.addSeed("http://www.ics.uci.edu/");

		/*
		 * Start the crawl. This is a blocking operation, meaning that your code
		 * will reach the line after this only when crawling is finished.
		 */
		controller.start(JdCrawler.class, numberOfCrawlers);
	}

	

}
