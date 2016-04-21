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

    public void process(Page page) {
            System.out.println(page.getUrl());
            page.putField("detailPage",page.getHtml().$(".tbimg a.t").regex("\"http://zz\\.58\\.com/.*?\"").all());
    }

    public Site getSite() {
        return this.site;
    }

    public static void main(String[] args) {
        Spider.create(new HouseDetailPageProcess())
                //从"https://github.com/code4craft"开始抓
                .addUrl("http://zz.58.com/zufang/0/pn2/?PGTID=0d300008-0015-6cbd-9def-d4134f1d8258&ClickID=2")
                //开启5个线程抓取
                .thread(4)
                //打印输出
                .addPipeline(new ConsolePipeline())
                //启动爬虫
                .run();
    }
}
