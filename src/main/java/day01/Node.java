package day01;

/**
 * Created by YBX on 2017/9/28.
 */
public class Node {
    protected Node next;  //指针域
    protected  int data;   //数据域

    public Node(int data){
        this.data = data;
    }
    //显示此节点
    public void dispaly(){
        System.out.println(data + ' ');
    }
}
