package Crawler;

import com.google.gson.Gson;
import model.Hourse;
import utill.DownloadFile;
import utill.HtmlParserTool;
import utill.LinkFilter;
import utill.LinkQueue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Administrator on 2016/4/8.
 */
public class MyCrawler {
    /**
     * 使用种子初始化url队列
     * @param seeds
     */
    private void initCrawlerWithSeeds(String... seeds){
        for (int i = 0; i < seeds.length; i++) {
            LinkQueue.addUnVisitedUrl(seeds[i]);
        }
    }

    /**
     * 抓取过程
     * @param seads
     */
    public void crawling(String seads){
        //定义过滤器，提取以http://www.baidu.com开头的链接
        LinkFilter filter = new LinkFilter() {
            public boolean accept(String url) {
                if(url.startsWith("http://zz.58.com/chuzu/0/")){
                    return true;
                }else {
                    return false;
                }
            }
        };

        //初始化队列,一次先获取20页的地址
        initCrawlerWithSeeds(seads);
        for (int i=0;i<10;i++){
            String nextPage = HtmlParserTool.getNextPageURL(seads);
            initCrawlerWithSeeds(nextPage);
            seads = nextPage;
        }
        Gson gson = new Gson();
        //待抓取的链接不空且抓取连接数<=100
        while (!LinkQueue.unVisitedUrlEmpty()){
            //对头url出队
            String visitUrl = (String) LinkQueue.unVisitedUrlDeQueue();
            //获取要处理的网页
            Set<String> detailUrls = HtmlParserTool.extractDetailLinks(visitUrl);
            for (Iterator<String> iterator = detailUrls.iterator(); iterator.hasNext();){
                String detailUrl = iterator.next();
                Hourse hourse = HtmlParserTool.getDetailInfo(detailUrl);
                System.out.println(gson.toJson(hourse));
            }
            //将改url放入已处理的集合中
            LinkQueue.addVisitedUrl(visitUrl);
        }
    }

    /**
     * 爬虫入口
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(">>>>>开始运行>>>>>");
        MyCrawler crawler = new MyCrawler();
        crawler.crawling("http://zz.58.com/chuzu/0/");
        System.out.println(">>>>>运行结束>>>>>");
    }
}
