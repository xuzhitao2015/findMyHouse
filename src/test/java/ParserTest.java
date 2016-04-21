import org.junit.Test;
import utill.HtmlParserTool;

/**
 * Created by zhangll on 2016/4/20.
 */
public class ParserTest {
    @Test
    public void getDetail(){
        HtmlParserTool
                .getDetailInfo("http://zz.58.com/zufang/25742801806527x.shtml?version=A&psid=130904203191513851873924768&entinfo=25742801806527_0&iuType=z_0&PGTID=0d300008-0015-682b-1ff2-c98e1c16e47f&ClickID=2&adtype=3");
//        HtmlParserTool
//                .getDetailInfo("http://zz.58.com/zufang/25668121681478x.shtml?version=A&psid=130904203191513851873924768&entinfo=25668121681478_0&iuType=z_0&PGTID=0d300008-0015-682b-1ff2-c98e1c16e47f&ClickID=1&adtype=3");

    }
}
