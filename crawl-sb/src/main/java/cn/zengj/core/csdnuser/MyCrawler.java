package cn.zengj.core.csdnuser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.http.HttpStatus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import cn.zengj.core.SpringUtil;
import cn.zengj.domain.CsdnUser;
import cn.zengj.service.CsdnUserService;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * Hello world!
 *
 */
@Component
public class MyCrawler extends WebCrawler {
	
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
                                                           + "|png|mp3|mp4|zip|gz))$");
    
    private CsdnUserService csdnUserService = null;
    

    public CsdnUserService getService() {
    	if(csdnUserService == null){
    		csdnUserService = SpringUtil.getBean(CsdnUserService.class);
    	}
    	
    	return csdnUserService;
	}

	/**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the crawler to ignore urls that
     * have css, js, git, ... extensions and to only accept urls that start
     * with "http://www.ics.uci.edu/". In this case, we didn't need the
     * referringPage parameter to make the decision.
     */
     @Override
     public boolean shouldVisit(Page referringPage, WebURL url) {
         String href = url.getURL().toLowerCase();
         return !FILTERS.matcher(href).matches()
                && href.matches("https://my.csdn.net/.+");
     }

     /**
      * This function is called when a page is fetched and ready
      * to be processed by your program.
      */
     @Override
     public void visit(Page page) {
         String url = page.getWebURL().getURL();
         System.out.println("URL: " + url);

         if (page.getParseData() instanceof HtmlParseData) {
             HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
             String html = htmlParseData.getHtml();
             
             Document doc = Jsoup.parse(html);
             boolean empty = doc.select(".person_info_con").isEmpty();
             
             if(empty){
            	 System.out.println("页面不包含用户数据：url="+url);
            	 return;
             }

             CsdnUser csdnUser = new CsdnUser();
             
             //关注数
             Integer concernCount = Integer.valueOf(doc.select(".focus_num").get(0).child(0).text());
             csdnUser.setConcernCount(concernCount);
             
             //被关注数
             Integer beConcernCount = Integer.valueOf(doc.select(".fans_num").get(0).child(0).text());
             csdnUser.setBeConcernCount(beConcernCount);

             //用户名
             String name = doc.select(".person-nick-name").get(0).child(0).text();
             csdnUser.setNickname(name);

             //基本信息
             String lable = doc.select(".person-detail").get(0).text();
             csdnUser.setLable(lable);
             
             //自述
             String desc = doc.select(".person-sign").get(0).text();
             csdnUser.setDesc(desc);

             String ac = doc.select(".csdn-pagination").text().replaceAll(".*共(\\d+)个.*", "$1");
             
             Integer articleCount = Integer.valueOf(ac.equals("")?"0":ac);
             csdnUser.setArticleCount(articleCount);
             
             String keyName = url.substring(url.lastIndexOf("/")+1);
             csdnUser.setKeyName(keyName);
             
             CsdnUser exist = getService().getByKeyName(keyName);
             if(exist != null){
            	 System.out.println("已存在的keyname="+keyName);
            	 return;
             }
             
             getService().save(csdnUser);
             System.out.println("录入一个用户数据："+csdnUser);
             
         }
    }
     
     
    public static void main(String[] args) {
    	String str = "这里共56个商品";
    	str = str.replaceAll(".*共(\\d+)个.*", "$1");
    	
    	System.out.println(str);
	}
     
     
     @Override
    protected void handlePageStatusCode(WebURL webUrl, int statusCode, String statusDescription) {
         
         if (statusCode != HttpStatus.SC_OK) {

             if (statusCode == HttpStatus.SC_NOT_FOUND) {
                 logger.warn("Broken link: {}, this link was found in page: {}", webUrl.getURL(),
                             webUrl.getParentUrl());
             } else {
                 logger.warn("Non success status for link: {} status code: {}, description: ",
                             webUrl.getURL(), statusCode, statusDescription);
             }
         }
         
    }
}
