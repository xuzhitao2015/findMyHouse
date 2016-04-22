package process;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by Administrator on 2016/4/21.
 */
public class HouseDetailPageProcess implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setCharset("utf-8").setTimeOut(1000*10);
    private static String URL_LIST = "http://zz\\.58\\.com/zufang/0/(pn[0-9]+/.*)?";
    private static String URL_INFO = "http://zz\\.58\\.com/zufang/.+\\.shtml\\?version=.*&psid=.*&entinfo=.*";
    public void process(Page page) {
            if(page.getUrl().regex(URL_INFO).match()){
                //获取房屋价格
                page.putField("price",page.getHtml().$("em.house-price").regex("\\d+"));
                if (page.getResultItems().get("price") == null) {
                    //skip this page
                    page.setSkip(true);
                }
                System.out.println(page.getHtml().$("div.fl.house-type.c70").regex("[1-9](?=\\u5ba4)").toString());
                //获取户型
                page.putField("sansh",page.getHtml().$("div.fl.house-type.c70").regex("[1-9](?=\\u5ba4)").toString());
                //获取区域
                page.putField("area",page.getHtml().regex(".fl.xiaoqu.c70 a:nth-child(1)").toString());
            }else if(page.getUrl().regex(URL_LIST).match()){
                page.addTargetRequests(page.getHtml().$(".tbimg a.t").links().regex("http://zz\\.58\\.com/.*").all());
                page.addTargetRequests(page.getHtml().$("a.next").links().all());
            }else {
                System.out.println("不支持的链接格式："+page.getUrl());
            }


    }

    public Site getSite() {
        return this.site;
    }

    public static void main(String[] args) {
        Spider.create(new HouseDetailPageProcess())
                //从"https://github.com/code4craft"开始抓
                .addUrl("http://zz.58.com/zufang/0/")
                //开启5个线程抓取
                .thread(4)
                //打印输出
                .addPipeline(new ConsolePipeline())
                //启动爬虫
                .run();
    }
}
