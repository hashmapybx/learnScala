package day05;

import jdk.jfr.events.ThrowablesEvent;

/**
 * Created by YBX on 2017/10/21.
 * 链表实现栈操作push(),pop(),peak()
 */
public class LStack {
    Node header;  //栈顶元素
    int elementCount;  //栈内的元素的个数
    int size;  //栈的大小

    /**
     * 构造函数 构造一个空栈
     */
    public LStack() {
        header = null;
        elementCount = 0;
        size = 0;
    }

    /**
     * 构造自定义栈的大小
     */
    public LStack(int size) {
        this.size = size;
        header = null;
        elementCount = 0;

    }

    public void setHeader(Node header) {
        this.header = header;
    }

    /**
     * 判断栈是否是空栈
     */
    public boolean isEmpty() {
        if (elementCount == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是满栈
     */
    public boolean isFull() {
        if (elementCount == size) {
            return true;
        }
        return false;
    }

    /**
     * 入栈操作
     */
    public void push(Object value) {
        if (this.isFull()) {
            throw new RuntimeException("stcuk is full");
        }
        header = new Node(value, header);
        elementCount++;
    }

    /**
     * 出栈
     */
    public Object pop() {
        if (this.isEmpty()) {
            throw new RuntimeException("stuck is empty");
        }
        Object object = header.getElement();
        header = header.next;
        elementCount--;
        return object;
    }

    /**
     * 返回栈顶元素
     */
    public Object peak() {

        if (this.isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        return header.getElement();
    }
}
