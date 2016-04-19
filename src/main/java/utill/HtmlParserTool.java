package utill;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.*;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.*;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

import java.net.PasswordAuthentication;
import java.util.*;

/**
 * Created by zliang on 2016/4/7.
 */
public class HtmlParserTool {

    /**
     * 获取一个网站的链接
     * @param url
     * @return
     */
    public static Set<String> extractLinks(String url,LinkFilter filter){
        Set<String> links = new HashSet<String>();
        try {
            Parser parser = new Parser(url);
            parser.setEncoding("gb2312");
            //过来frame标签的filter,用来获取frame的src
            NodeFilter frameFilter = new NodeFilter() {
                public boolean accept(Node node) {
                    if (node.getText().startsWith("frame src=")){
                        return true;
                    }else{
                        return false;
                    }
                }
            };
            //orFilter用来设置过滤<a>标签和<frame>标签
            OrFilter orFilter = new OrFilter(new NodeClassFilter(LinkTag.class),frameFilter);
            //得到所有经过过滤的标签
            NodeList list = parser.extractAllNodesThatMatch(orFilter);
            for (int i=0;i<list.size();i++){
                Node tag = list.elementAt(i);
                if(tag instanceof LinkTag){//<a>标签
                    LinkTag link = (LinkTag) tag;
                    String linkUrl = link.getLink(); //URL
                    //过滤url
                    if(filter.accept(linkUrl)){
                        links.add(linkUrl);
                    }

                }else {//<frame>标签
                    String  frame = tag.getText();
                    int start = frame.indexOf("src=");
                    frame = frame.substring(start);
                    int end = frame.indexOf(" ");
                    if(end == -1){
                        end = frame.indexOf(">");
                    }
                    String frameUrl = frame.substring(5,end -1);
                    //过滤url
                    if(filter.accept(frameUrl)){
                        links.add(frameUrl);
                    }
                }
            }

        }catch (ParserException e){
            e.printStackTrace();
        }

        return links;
    }

    /**
     * 抓取房屋详情页面
     * @param url
     * @return
     */
    public static Set<String> extractDetailLinks(String url){
        Set<String> links = new HashSet<String>();
        try {
            Parser parser = new Parser(url);
            parser.setEncoding("utf-8");
            //查找出所有的<a>标签,并且是class="tbimg"的子节点
            NodeFilter linkFilter = new NodeClassFilter(LinkTag.class);
            HasAttributeFilter attributeFilter = new HasAttributeFilter("class","tbimg");
            HasParentFilter hasParentFilter = new HasParentFilter(attributeFilter,true);
            AndFilter andFilter = new AndFilter(linkFilter,hasParentFilter);
            NodeList list = parser.extractAllNodesThatMatch(andFilter);
            for (int i=0;i<list.size();i++){
                LinkTag tag = (LinkTag) list.elementAt(i);
                //如果<a>的class="t"并且是以http://zz.58.com的
                String classAttr = tag.getAttribute("class")==null?"":tag.getAttribute("class");
                if (classAttr.equals("t")
                        &&tag.getLink().startsWith("http://zz.58.com")){
//                    System.out.println("过滤后的link:"+tag.getLink());
                    links.add(tag.getLink());
                }
            }
        }catch (ParserException e){
            e.printStackTrace();
        }
        System.out.println(links.size());
        return links;
    }

    /**
     * 获取下一页的url
     * @param url
     * @return
     */
    public static String getNextPageURL(String url){
        String nextPage = "";
        try {
            Parser parser = new Parser(url);
            parser.setEncoding("utf-8");
            HasAttributeFilter attributeFilter = new HasAttributeFilter("class","next");
            NodeList list = parser.extractAllNodesThatMatch(attributeFilter);
            if(list.size()>0&&list.size()==1){
                for (int i=0;i<list.size();i++){
                    LinkTag tag = (LinkTag) list.elementAt(i);
                    nextPage = tag.getLink();
                }
            }

        }catch (ParserException e){
            e.printStackTrace();
        }
        return nextPage;
    }

    /**
     * 获取房屋详情页的信息
     * @param url
     */
    public static void getDetailInfo(String url){
        try {
            Parser parser = new Parser(url);
            parser.setEncoding("utf-8");

            NodeFilter[] nodeFilters = new NodeFilter[9];
            //获取标题
            HasAttributeFilter titleFilter = new HasAttributeFilter("class","main-title font-heiti");
            nodeFilters[0] = titleFilter;
            //获取最后更新日期
            HasAttributeFilter dateFilter = new HasAttributeFilter("class","title-right-info cb7 f14 pa");
            nodeFilters[1] = dateFilter;
            //获取图片地址
            HasAttributeFilter imgFilter = new HasAttributeFilter("id","leftImg");
            nodeFilters[2] = imgFilter;
            //获取价格
            HasAttributeFilter priceFilter = new HasAttributeFilter("class","house-primary-content-li house-primary-content-fir clearfix");
            nodeFilters[3] = priceFilter;
            //获取房屋类型
            HasAttributeFilter typeFilter = new HasAttributeFilter("class","fl house-type c70");
            nodeFilters[4] = typeFilter;
            //获取房屋小区
            HasAttributeFilter areaFilter = new HasAttributeFilter("class","fl xiaoqu c70");
            nodeFilters[5] = areaFilter;
            //获取小区详细地址

            HasAttributeFilter addressFilter1 = new HasAttributeFilter("css","fl c70");
            HasAttributeFilter parentFilter1 = new HasAttributeFilter("css","house-primary-content-li clearfix");
            HasParentFilter addressFilter2 = new HasParentFilter(parentFilter1);
            AndFilter addressFilter = new AndFilter(addressFilter1,addressFilter2);
            nodeFilters[6] = addressFilter;
            //获取小区配置信息
            HasAttributeFilter configFilter = new HasAttributeFilter("class","house-primary-content-li clearfix person-config");
            nodeFilters[7]= configFilter;
            //获取房屋描述
            HasAttributeFilter descriptionFilter = new HasAttributeFilter("class","description-content");
            nodeFilters[8]= descriptionFilter;

            OrFilter orFilter = new OrFilter(nodeFilters);
            NodeList list = parser.extractAllNodesThatMatch(orFilter);

            SimpleNodeIterator iterator = list.elements();
            System.out.println(url);
            while (iterator.hasMoreNodes()){
                CompositeTag node = (CompositeTag)iterator.nextNode();
                if (node.getAttribute("class").equals("main-title font-heiti")){
                    //System.out.println(node.getChildren().elementAt(0).getText());
                    System.out.println(node.getStringText());
                }else if (node.getAttribute("class").equals("title-right-info cb7 f14 pa")){
                    System.out.println(((Span)node.getChild(1)).getStringText().substring(5));
                }else if(node.getAttribute("id")!=null&&node.getAttribute("id").equals("leftImg")){
                    NodeList imgNodes = new NodeList();
                    TagNameFilter nameFilter = new TagNameFilter("img");
                    node.collectInto(imgNodes,nameFilter);
                   for(int i=0;i<imgNodes.size();i++){
                       ImageTag img = (ImageTag) imgNodes.elementAt(i);
                       System.out.println(img.getAttribute("lazy_src"));
                   }
                }else if(node.getAttribute("class").equals("house-primary-content-li house-primary-content-fir clearfix")){
                    NodeList priceNodes = new NodeList();
                    CssSelectorNodeFilter cssFilter = new CssSelectorNodeFilter(".fl");
                    node.collectInto(priceNodes,cssFilter);
                    System.out.println(priceNodes.elementAt(0).getChildren().elementAt(4).getText());
                } else if(node.getAttribute("class").equals("fl house-type c70")){
                    String str = node.getStringText().replaceAll("\\s","").replaceAll("&nbsp","").replaceAll("-","").replaceAll("<br>","").replaceAll(";","");
                    System.out.println(str);
                }else if(node.getAttribute("class").equals("fl xiaoqu c70")){
                    CompositeTag addr1Node = ((CompositeTag)node.getChild(1));
                    String addr1 = "";
                    CompositeTag addr2Node = ((CompositeTag)node.getChild(3));
                    String addr2 = "";
                    if(addr1Node!=null){
                        addr1 = addr1Node.getStringText();
                    }
                    if(addr2Node!=null){
                        addr2 = addr2Node.getStringText();
                    }
                    System.out.println(addr1+addr2);
                }else if(node.getAttribute("class").equals("fl c70")){
                    System.out.println(node.getStringText());
                }else if(node.getAttribute("class").equals("house-primary-content-li clearfix person-config")){
                    NodeList configs = new NodeList();
                    TagNameFilter spanFilter = new TagNameFilter("span");
                     node.collectInto(configs,spanFilter);
                    SimpleNodeIterator configIterator = configs.elements();
                    StringBuffer config = new StringBuffer();
                    while (configIterator.hasMoreNodes()){
                        Span configSpan = (Span)configIterator.nextNode();
                        config.append(configSpan.getStringText()+"-");
                    }
                    System.out.println(config);
                }else if(node.getAttribute("class").equals("description-content")){
                    System.out.println(node.toHtml());
                }
            }
        }catch (ParserException e){
            e.printStackTrace();
        }
    }
}
