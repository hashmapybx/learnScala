package day05;


/**
 * Created by YBX on 2017/10/21.
 */
public class Node {
    Object element;
    Node next;

    public Node(Object elemnet) {
        this.element = elemnet;
    }
    /**
     * 创建一个节点包括数据和指针
     * */
    public Node(Object element, Node n){
       this.element = element;
    }

    public Object getElement(){
       return element;
    }



}
