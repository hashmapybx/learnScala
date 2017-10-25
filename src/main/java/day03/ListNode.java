package day03;

import java.util.*;

/**
 * Created by YBX on 2017/10/11.
 * 关于链表操作的相关方法
 */
public class ListNode {
    private ListNode next;
    private int data;

    public ListNode(){

    }
    public ListNode(int data) {
        this.data = data;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public ListNode getNext() {
        return next;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }


    //龟兔赛跑的案例，单链表判断是否存在环Floyd判断环的算法
    public boolean DoesLinkedListContainsLoop(ListNode head) {
        if (head == null) {
            return false;
        }
        ListNode slowPtr = head, fastPtr = head;  //快指针和慢指针
        while (fastPtr.getNext() != null && fastPtr.getNext().getNext() != null) { //快指针移动2个节点，慢指针移动1个节点
            slowPtr = slowPtr.getNext();
            fastPtr = fastPtr.getNext().getNext();
            if (slowPtr == fastPtr) {
                return true;
            }
        }
        return false;
    }

    //判断给定的链表是否以NULL结尾，如果链表中存在环，返回环的长度
    public int FindLoopLength(ListNode head) {
        ListNode slowPtr = head, fastPtr = head;
        boolean loopExists = false;
        int counter = 0;
        if (head == null) {
            return 0;
        }
        while (fastPtr.getNext() != null && fastPtr.getNext().getNext() != null) {
            slowPtr = slowPtr.getNext();
            fastPtr = fastPtr.getNext().getNext();
            if (slowPtr == fastPtr) {
                loopExists = true;
                break;
            }
        }
        if (loopExists) {  //表示环存在了
            fastPtr = fastPtr.getNext();  //保持乌龟的位置不变，让兔子继续走，当兔子再次回到乌龟的身边则求出环的长度了
            while (slowPtr != fastPtr) {
                fastPtr = fastPtr.getNext();
                counter++;
            }
            return counter;
        }
        return 0;                       //表示链表中没有环
    }

    //在有序链表中插入节点
    public ListNode InsertInSortedList(ListNode head, ListNode newNode) {
        ListNode current = head;
        ListNode temp = null;
        if (head == null) {   //当前节点为null，直接插入
            return newNode;
        }
        //遍历链表直到找到比当前结点数据大于新结点的数据的位子
        while (current != null && current.getData() < newNode.getData()) {
            temp = current;
            current = current.getNext();
        }
        newNode.setNext(current);
        temp.setNext(newNode);

        return head;
    }

    //设置逆置单向链表
    public ListNode ReverseList(ListNode head) {
        ListNode temp = null, nextNode = null;
        while (head != null) {
            nextNode = head.getNext();
            head.setNext(temp);
            temp = head;
            head = nextNode;
        }
        return temp;
    }

    //求两个链表的合并节点,也可以利用栈来解决这些问题的
    public ListNode findIntersectingNode(ListNode list1, ListNode list2) {
        int L1 = 0, L2 = 0, diff = 0;
        ListNode head1 = list1, head2 = list2;
        while (head1 != null) {   //获取长度
            L1++;
            head1 = head1.getNext();
        }
        while (head2 != null) { //获取长度
            L2++;
            head2 = head2.getNext();
        }
        if (L1 > L2) {
            head1 = list1;
            head2 = list2;
            diff = L1 - L2;
        } else {
            head1 = list2;
            head2 = list1;
            diff = L2 - L1;
        }

        for (int i = 0; i < diff; i++) {
            head1 = head1.getNext();  //从较长的链表表头开始移动diff步
        }
        while (head1 != null && head2 != null) {
            if (head1 == head2) {   //如果两个指针的后继指针相同则返回数据
                head1.getData();
            }
            head1 = head1.getNext();  //指针向后移动
            head2 = head2.getNext();
        }
        return null;    //否则返回null
    }

    //问题对于求解链表的中间位置元素:可以定义两个指针，让第一个的移动是第二个的两倍
    public ListNode findMiddle(ListNode head) {
        ListNode ptr1x, ptr2x;
        ptr1x = ptr2x = head;
        int i = 0;
        while (ptr1x.getNext() != null) {
            if (i == 0) {
                ptr1x = ptr1x.getNext();
                i = 1;
            } else if (i == 1) {
                ptr1x = ptr1x.getNext();
                ptr2x = ptr2x.getNext();
                i = 0;
            }
        }
        return ptr2x;            //返回ptr2x即为中间节点的位置
    }

    //如何从表尾输出元素 采用递归的方法。判断如果第一个元素不等于null
    public void printListFromEnd(ListNode head) {
        if (head == null) {
            return;
        }
        printListFromEnd(head.getNext());
        System.out.println(head.getNext());
    }

    //如何判断链表的长度是奇数还是偶数
    public int isLinkedListLengthEvenOrOdd(ListNode head) {
        if (head != null && head.getNext() != null) {
            head = head.getNext().getNext();
        }
        if (head == null) {
            return 0;
        }
        return 1;
    }

    //如何合并两个有序的链表呢？分别把两个链表的每一个元素作比较，按顺序放到空的链表里面

    public ListNode mergeList(ListNode a, ListNode b){
        //先创建一个空的链表
        ListNode result = null;
        if (a == null) return b;
        if (b == null) return a;
        if (a.getData() >= b.getData()){
            result = b;
            result.setNext(mergeList(b.getNext(), a));
        }else{
            result = a;
            result.setNext(mergeList(a.getNext(), b));
        }
        return result;
    }


     //如何逐对逆置链表。1-2-3-4-X  2-1-4-3-x

    public ListNode ReversePairRecursive(ListNode head){
        //先创建一个临时链表
        ListNode temp;  //局部变量
        if (head == null || head.next == null){
            return head;
        }else{
            //先逐对逆置第一对
            temp = head;
            head = head.next;
            head.next = temp;
            head.next.next = ReversePairRecursive(head.next.next);
            return head;
        }
    }


    //如何把给定的二叉树转换了双向链表呢？
    //如何对链表进行排序呢？
    //如果把两个链表连接起来，那种算法时间复杂度是O（1）？ 单向链表，双向链表，循环双向链表
    //需要把其中一个链表遍历一遍的。
    //如何把一个链表拆成两个部分，如果是奇数让第一个链表的结点数比第二个多一个
    public void SplitList(ListNode head, ListNode head1, ListNode head2){
        ListNode slow = head, fast = head;
        if (head == null){
            return;
        }
        //如果链表有偶数的结点，那么fast.getNext().getNext()指向head.是奇数结点则可以认为fast.getNext（）指向的是head
        while(fast.getNext() != head && fast.getNext().getNext() != head){
            fast = fast.getNext().getNext();
            slow = slow.getNext();
        }
        //
        if (fast.getNext().getNext() == head){
            fast = fast.getNext();
        }
        //设置前半部分的指针
        head1 = head;
        if (head.getNext() != head){
            head2 = slow.getNext();
        }
        //把后半部分变成环，
        fast.setNext(slow.getNext());
        //把前半部分变成环
        slow.setNext(head);
    }


    /**
     *如何判断链表是回文。既是顺读和倒读的结果都是一样的
     *将后半部分逆置，比较前面的和后面的链表
     *
     */

    //交换链表的结点
    public void ExchangeAdiacentNodes(ListNode head){
        ListNode curNode, temp, nextNode;  //当前指针，临时指针，下一个结点的指针
        curNode = head;
        if (curNode == null || curNode.getNext() != null){   //there is first node or not second node
            return;
        }
        head = curNode.getNext();
        while (curNode != null && curNode.getNext() != null){  //the currentNOde is not equal to null and nextNode is not null
            nextNode = curNode.getNext();
            curNode.setNext(nextNode.getNext());
            temp = curNode.getNext();
            nextNode.setNext(curNode);
            if (temp != null && temp.getNext() != null){
                curNode.setNext(curNode.getNext().getNext());
            }
            curNode = temp;
        }
    }

    //对于给定的K逆置K个节点的块,比如输入:1 2 3 4 5  ;输出: 2; 2 1 4 3 5
    //约瑟夫环:N个人选出来一个零头人，排成一个环，每数到第M个人就从环中排除该人，并从下一个人重新开始，请找到最后一个留下来的人。
    public static void yueSeFu(int totalNum, int countNum){
        List<Integer> start = new ArrayList<>();
        for (int i = 1; i <= totalNum; i++){
            start.add(i);
        }
        int k = 0; //从第K个位置开始计数
        while (start.size() > 0){
            k = k + countNum;
            k =  k % (start.size()) -1;
            //判断是否到队尾
            if ( k <0){
                System.out.println(start.get(start.size() -1));
                start.remove(start.size() -1);
                k =0;
            }else{
                System.out.println(start.get(k));
                start.remove(k);
            }
        }

    }

    //设计一个算法实现链表的复制


    public static void main(String[] args) {
//        String s = Double.toHexString(0.125);
//        System.out.println(s);
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入总人数：");
        int totalNum = sc.nextInt();
        System.out.println("请输入一个报数的大小：");
        int cycleNum = sc.nextInt();
        yueSeFu(totalNum, cycleNum);
        sc.close();
    }

}
