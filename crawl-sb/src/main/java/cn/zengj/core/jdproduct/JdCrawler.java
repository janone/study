package cn.zengj.core.jdproduct;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.HttpStatus;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import cn.zengj.core.SpringUtil;
import cn.zengj.domain.CsdnUser;
import cn.zengj.domain.ProductDetailInfo;
import cn.zengj.service.ProductDetailInfoService;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * Hello world!
 *
 */
@Component
public class JdCrawler extends WebCrawler {

	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg" + "|png|mp3|mp4|zip|gz))$");

	private ProductDetailInfoService productDetailInfoService = null;

	public ProductDetailInfoService getService() {
		if (productDetailInfoService == null) {
			productDetailInfoService = SpringUtil.getBean(ProductDetailInfoService.class);
			if (productDetailInfoService == null) {
				throw new NullPointerException("从SpringUtil取出的bean为空");
			}
		}

		return this.productDetailInfoService;
	}

	/**
	 * This method receives two parameters. The first parameter is the page in
	 * which we have discovered this new url and the second parameter is the new
	 * url. You should implement this function to specify whether the given url
	 * should be crawled or not (based on your crawling logic). In this example,
	 * we are instructing the crawler to ignore urls that have css, js, git, ...
	 * extensions and to only accept urls that start with
	 * "http://www.ics.uci.edu/". In this case, we didn't need the referringPage
	 * parameter to make the decision.
	 */
	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase();
		return !FILTERS.matcher(href).matches() && href.matches("https://.*m.jd.com/.*");
	}

	/**
	 * This function is called when a page is fetched and ready to be processed
	 * by your program.
	 */
	@Override
	public void visit(Page page) {

		String url = page.getWebURL().getURL();
		System.out.println("URL: " + url);

		if (!url.startsWith("https://item.m.jd.com")) {
			return;
		}

		if (page.getParseData() instanceof HtmlParseData) {
			
			String getskufromurl = getskufromurl(url);
			ProductDetailInfoService service = getService();
			ProductDetailInfo bySku = service.getBySku(Long.valueOf(getskufromurl));
			if(bySku != null){
				System.out.println();
				System.out.println("商品已存在：" + url);
				System.out.println();
				return;
			}
			
			try {

				ProductDetailInfo infoBySelenuim = getInfoBySelenuim(url);

				service.save(infoBySelenuim);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	String chromedriver = "d:\\chromedriver.exe";
	{
		System.setProperty("webdriver.chrome.driver", chromedriver);
	}


	private ProductDetailInfo getInfoBySelenuim(String url) throws Exception {

		WebDriver driver = new ChromeDriver();
		ProductDetailInfo info = new ProductDetailInfo();

		try{
			
			driver.get(url);
			
			//5秒页面无响应，则直接关闭浏览器
			int i = 0;
			int t = 5;
			while(true){
				if(StringUtils.isEmpty(driver.findElement(By.tagName("body")).getText())){
					Thread.sleep(1000);
					if(i > t){
						throw new IllegalStateException("页面无法获取:"+url);
					}
					i++;
					
				}else{
					break;
				}
			}
			
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(1000);
			
			info.setSku(Long.valueOf(getskufromurl(url)));
			info.setUrl(url);
			info.setDetailImages(getdetailimagesfrompage(driver).toString());
			info.setPictures(getpicturesfrompage(driver).toString());
			info.setLogo(info.getPictures().split(",")[0]);
			info.setProductName(getnamefrompage(driver));
			info.setPrice(new BigDecimal(getpricefrompage(driver)));
			
			System.out.println(info);
		}finally{
			if(driver != null){
				driver.close();
			}
		}


		return info;
	}

	private String getskufromurl(String url) {
		String sku = url.replaceAll(".+/(\\d+)\\.html", "$1");
		return sku;
	}
	

	private static String getpricefrompage(WebDriver driver) throws Exception {
		WebElement findElement = driver.findElement(By.id("priceSale"));
		String text = findElement.getText();
		if(StringUtils.isEmpty(text)){
			WebElement specPrice = driver.findElement(By.id("specPrice"));
			if(StringUtils.isEmpty(text = specPrice.getText())){
				throw new IllegalAccessException("无法获取价格:"+driver.getCurrentUrl());
			}
		}
		String priceStr = text.substring(1);
		return priceStr;
	}

	private static String getnamefrompage(WebDriver driver) throws Exception {
		WebElement element = driver.findElement(By.id("itemName"));
		String text = element.getText();
		if(StringUtils.isEmpty(text)){
			throw new IllegalAccessException("无法获取商品名称:"+driver.getCurrentUrl());
		}
		return text;
	}

	private static StringBuffer getpicturesfrompage(WebDriver driver) throws Exception {
		WebElement picList = driver.findElement(By.className("pic_list"));
		List<WebElement> imgs = picList.findElements(By.tagName("img"));

		StringBuffer sb = new StringBuffer();
		if(imgs == null || imgs.size() == 0){
			throw new IllegalAccessException("无法获取轮播图列表:"+driver.getCurrentUrl());
		}
		for (WebElement el : imgs) {
			String img = el.getAttribute("back_src");
			if (StringUtils.isEmpty(img)) {
				img = el.getAttribute("src");
			}
			sb.append(img);
			sb.append(',');
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		return sb;
	}

	private static StringBuffer getdetailimagesfrompage(WebDriver driver) throws Exception {
		WebElement div = driver.findElement(By.id("commDesc"));
		List<WebElement> imgs = div.findElements(By.tagName("img"));
		
		if(imgs == null || imgs.size() == 0){
			throw new IllegalAccessException("无法获取详情图列表:"+driver.getCurrentUrl());
		}
		
		StringBuffer details = new StringBuffer();
		for (WebElement el : imgs) {
			String attribute = el.getAttribute("src");
			details.append(attribute);
			details.append(',');
		}
		details.deleteCharAt(details.lastIndexOf(","));
		return details;
	}

	@Override
	protected void handlePageStatusCode(WebURL webUrl, int statusCode, String statusDescription) {

		if (statusCode != HttpStatus.SC_OK) {

			if (statusCode == HttpStatus.SC_NOT_FOUND) {
				logger.warn("Broken link: {}, this link was found in page: {}", webUrl.getURL(), webUrl.getParentUrl());
			} else {
				logger.warn("Non success status for link: {} status code: {}, description: ", webUrl.getURL(),
						statusCode, statusDescription);
			}
		}

	}

	/*
	 * private static String getPosthtml(String url){
	 * 
	 * WebClient webClient = new WebClient(BrowserVersion.CHROME);
	 * 
	 * webClient.getOptions().setJavaScriptEnabled(true); //启用JS解释器，默认为true
	 * webClient.getOptions().setCssEnabled(false); //禁用css支持
	 * webClient.getOptions().setThrowExceptionOnScriptError(false);
	 * //js运行错误时，是否抛出异常 webClient.getOptions().setTimeout(20000);
	 * webClient.waitForBackgroundJavaScript(3000);
	 * 
	 * 
	 * HtmlPage postpage = null; String pageXml = null;
	 * 
	 * try {
	 * 
	 * postpage = webClient.getPage(url);
	 * 
	 * // postpage.
	 * executeJavaScript("window.scrollTo(0, document.body.scrollHeight)");
	 * 
	 * HtmlDivision div = postpage.getHtmlElementById("commDesc");
	 * 
	 * 
	 * Thread.sleep(20000);
	 * 
	 * String asXml = div.asXml(); System.out.println("======= div =======");
	 * System.out.println(asXml); // HtmlAnchor anchorByText =
	 * postpage.getAnchorByText("详情");
	 * 
	 * 
	 * pageXml = postpage.asXml(); //以xml的形式获取响应文本
	 * 
	 * 
	 * } catch (Exception e) { e.printStackTrace(); System.exit(1); } finally{
	 * webClient.close(); }
	 * 
	 * return pageXml; }
	 * 
	 * public static void main(String[] args) { String url =
	 * "https://item.m.jd.com/product/26015128249.html"; String posthtml =
	 * getPosthtml(url);
	 * 
	 * // Document parse = Jsoup.parse(posthtml); // Elements select =
	 * parse.select("#commDesc"); //
	 * System.out.println("=============================="); //
	 * System.out.println(select.html()); }
	 * 
	 * 
	 * 
	 * public static void main(String[] args) throws Exception {
	 * 
	 * String chromedriver = "d:\\chromedriver.exe";
	 * 
	 * System.setProperty("webdriver.chrome.driver", chromedriver);
	 * 
	 * WebDriver driver = new ChromeDriver();
	 * 
	 * String url = "https://item.m.jd.com/product/28013047753.html";
	 * driver.get(url); Thread.sleep(3000);
	 * 
	 * ((JavascriptExecutor)
	 * driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	 * Thread.sleep(1000);
	 * 
	 * 
	 * 
	 * ProductDetailInfo info = new ProductDetailInfo();
	 * 
	 * info.setUrl(url);
	 * info.setDetailImages(getdetailimagesfrompage(driver).toString());
	 * info.setPictures(getpicturesfrompage(driver).toString());
	 * info.setLogo(info.getPictures().split(",")[0]);
	 * info.setProductName(getnamefrompage(driver)); info.setPrice(new
	 * BigDecimal(getpricefrompage(driver)));
	 * 
	 * System.out.println(info);
	 * 
	 * driver.close();
	 * 
	 * }
	 */
	
	
	public static void main(String[] args) {
		String url = "https://item.m.jd.com/product/65461215634.html";
		String reg = ".+/(\\d+)\\.html";
		String sku = url.replaceAll(reg, "$1");
		boolean matches = url.matches(reg);
		System.out.println(matches);
		System.out.println(sku);
	}

}
