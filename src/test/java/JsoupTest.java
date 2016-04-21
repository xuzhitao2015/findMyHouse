import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

/**
 * Created by zhangll on 2016/4/20.
 */
public class JsoupTest {
    @Test
    public void test(){
        String html = "<div><em>你好</em>世界<span>!!!<em>@@@@</em></span></div>";

        Document doc1 = Jsoup.parseBodyFragment(html);

        Elements em = doc1.select("em");
        Elements div = doc1.select("div > em");
        System.out.println(em.first().text());
        System.out.println(div.text());
    }
}
