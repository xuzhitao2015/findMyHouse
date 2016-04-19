package utill;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by zliang on 2016/4/7.
 */
public class LinkQueue {
    //已访问的URL集合
    private static Set visitedUrl = new HashSet();
    //待访问的URL队列
    private static Queue unVisitedUrl = new Queue();

    /**
     * 获取未访问的URL集合
     * @return
     */
    public static Queue getUnVisitedUrl(){
        return unVisitedUrl;
    }

    /**
     * 添加到访问过的url集合中
     * @param url
     */
    public static void addVisitedUrl(String url){
        visitedUrl.add(url);
    }

    /**
     * 移除访问过得url
     * @param url
     */
    public static void removeVisitedUrl(String url){
        visitedUrl.remove(url);
    }

    /**
     * 未访问过的url出列
     * @return
     */
    public static Object unVisitedUrlDeQueue(){
        return unVisitedUrl.deQueue();
    }

    /**
     * 保证每个url只被访问一次
     * @param url
     */
    public static void addUnVisitedUrl(String url){
        if(url!=null&&!url.trim().equals("")&&!visitedUrl.contains(url)&&!unVisitedUrl.contains(url)){
            unVisitedUrl.enQueue(url);
        }
    }

    public static void addUnVisitedUrl(Set<String> urls){
        for (String url : urls) {
            addUnVisitedUrl(url);
        }
    }

    /**
     * 获得已经访问的url数目
     * @return
     */
    public static int getVisitedUrlNum(){
        return visitedUrl.size();
    }

    /**
     * 判断未访问的url是否为空
     * @return
     */
    public static boolean unVisitedUrlEmpty(){
        return unVisitedUrl.isQueueEmpty();
    }

}
