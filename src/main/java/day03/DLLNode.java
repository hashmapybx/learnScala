package day03;

/**
 * Created by YBX on 2017/10/5.
 * 双向链表
 */
public class DLLNode {
    private int data;  //数据
    private DLLNode next; //右指针
    private DLLNode previous; //左指针

    public DLLNode(int data){
        this.data = data;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public DLLNode getNext() {
        return next;
    }

    public void setNext(DLLNode next) {
        this.next = next;
    }

    public DLLNode getPrevious() {
        return previous;
    }

    public void setPrevious(DLLNode previous) {
        this.previous = previous;
    }

    public DLLNode insertDLLNode(DLLNode headNode, DLLNode nodeToInsert, int position){   //nodeToInsert诶新插入的节点
        if (headNode == null){   //当双链表为空的时候
            return nodeToInsert;
        }
        int size = getDLLLength(headNode);
        if (position > size +1|| position < 1){
            System.out.println("position of node to delete is invalid.the valid inputs are 1 to  " + (size+1));
            return headNode;
        }
        if (position == 1){    //在表头插入
            nodeToInsert.setNext(headNode);
            headNode.setPrevious(nodeToInsert);
            return nodeToInsert;  //返回新节点在表头
        }else {
            //在链表中间或末尾插入
            DLLNode previousNode = headNode;
            int count = 1;
            while (count < position -1){
                previousNode = previousNode.getNext();
                count++;
            }
            DLLNode currentNode = previousNode.getNext(); //指定插入的位置节点
            nodeToInsert.setNext(currentNode);
            if (currentNode != null){
                currentNode.setPrevious(nodeToInsert);

            }
            previousNode.setNext(nodeToInsert);
            nodeToInsert.setPrevious(previousNode);
        }

        return headNode;
    }

    private int getDLLLength(DLLNode headNode) {   //获取链表的长度
        int length = 0;
        DLLNode currentNOde = headNode;
        while (currentNOde != null){
            length++;
            currentNOde = currentNOde.getNext();
        }
        return length;
    }



}
