package utill;

import java.util.LinkedList;

/**
 * 定义URL队列用于存储要访问的URL
 * Created by zhangll on 2016/4/5.
 */
public class Queue {
    private LinkedList queue = new LinkedList();

    public Queue() {
    }

    /**
     * 入队
     * @param obj
     */
    public void enQueue(Object obj){
        this.queue.add(obj);
    }
    /**
     * 出队
     * @return
     */
    public Object deQueue(){
        return this.queue.removeFirst();
    }
    /**
     * 判断队列是否为空
     * @return
     */
    public boolean isQueueEmpty(){
        return this.queue.isEmpty();
    }
    /**
     * 判断队列中是否包含该元素
     * @param obj
     * @return
     */
    public boolean contains(Object obj){
        return this.queue.contains(obj);
    }
    /**
     * 清空队列
     */
    public void empty(){
        this.queue.clear();
    }

    /**
     * 获取队列大小
     * @return
     */
    public int getQueueSize(){
        return this.queue.size();
    }
}
