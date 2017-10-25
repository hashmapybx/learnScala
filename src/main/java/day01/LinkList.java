package day01;

import sun.util.resources.cldr.sr.CalendarData_sr_Cyrl_RS;

import java.util.spi.CurrencyNameProvider;

/**
 * Created by YBX on 2017/9/28.
 * 单链表
 */
public class LinkList {
    //定义头结点
    public Node first;
    private int pos = 0; //定义节点的位置

    public LinkList() {
        this.first = null;
    }

    //插入一个头结点
    public void addFirstNode(int data) {
        Node node = new Node(data);
        node.next = first;
        first = node;
    }

    //删除一个头结点
    public Node deleteFirstNode() {
        Node tempNode = first;
        first = tempNode.next;
        return tempNode;
    }

    //在任意位置插入一个节点，在index后面插入
    public void add(int index, int data) {
        Node node = new Node(data);
        Node previous = first;
        Node current = first;
        while (pos != index) {
            previous = current;
            current = current.next;
            pos++;
        }
        node.next = current;
        previous.next = node;
        pos = 0;
    }

    //删除任意位置的节点
    public Node deleteByPos(int index){
        Node current = first;
        Node previous = first;
        while (pos != index){
            pos++;
            previous = current;
            current = current.next;
        }
        if (current == first){
            first = first.next;
        }else {
            pos = 0;
            previous.next = current.next;
        }
        return  current;
    }

    //根据节点的data删除节点
    public Node deleteByData(int data){
        Node current = first;
        Node previous = first;
        while(current.data != data){
            if (current.next == null){
                return null;
            }
            previous = current;
            current = current.next;
        }
        if (current == first){
            first = first.next;
        }else{
            previous.next = current.next;
        }
        return current;
    }

    //显示所有的节点信息
    public void dispalyAllNodes(){
        Node current = first;
        while (current != null){
            current.dispaly();
            current = current.next;
            pos++;
        }
        System.out.println();
    }

    //根据位置查找节点信息
    public Node findByNodes(int index){
        Node current = first;
        if (pos != index){
            current = current.next;
            pos++;
        }
        return current;
    }

    //根据数据查找节点信息
    public Node findByData(int data){
        Node current = first;
        while (current.data != data){
            if (current.next == null){
                return null;
            }
            current = current.next;
        }
        return current;
    }

}
