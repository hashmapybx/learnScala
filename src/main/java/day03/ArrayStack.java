package day03;

import java.util.Arrays;

/**
 * Created by YBX on 2017/10/17.
 */
public class ArrayStack<T> implements MyStack<T> {
    /**
     * 栈的实现方式：1、数组
     * 栈的相关属性,栈顶元素出栈，当栈储存满了，在操作入栈则会溢出；对于空栈执行pop操作，则会报下行溢出。
     * 利用数组实现栈的操作，则要让一个变量记录数组的最后一个元素为栈顶位置元素，满栈了执行入栈操作，则会栈满报异常；
     * 对于空的栈数组执行出栈操作的时候会报栈空的异常
     */
    private final int DEFAULT_size = 3;
    private int size = 0;
    private int capacity = 0;
    private int top = 0;
    private Object[] array;

    public ArrayStack() {
        this.capacity = this.DEFAULT_size;
        this.array = new Object[this.capacity];
        this.size = 0;
    }

    public ArrayStack(int capacity) {
        this.capacity = capacity;
        this.array = new Object[this.capacity];
        this.size = 0;
    }

    public int size() {
        return this.size;
    }


    @Override
    public void clear() {
        Arrays.fill(array, null);
        this.top = 0;
        this.size = 0;
        this.capacity = this.DEFAULT_size;   //等于默认的容量
        this.array = new Object[this.capacity];  //新创建数组
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public T peek() {
        return (T) this.array[this.top - 1];
    }

    @Override
    public T pop() {
        T element = (T) this.array[top - 1];
        this.array[top - 1] = null;
        this.size--;
        return element;
    }

    @Override
    public void push(T element) {
        if (this.size < this.capacity) {
            this.array[top] = element;
            this.top++;
            this.size++;
        } else {
            enlarge();   //扩大数组的容量
            push(element);
        }
    }

    public void enlarge() {
        this.capacity = this.capacity + this.DEFAULT_size;
        Object[] newarray = new Object[this.capacity];
        System.arraycopy(array, 0, newarray, 0, array.length);  //把原来的数组copy过来
        Arrays.fill(array, null);
        this.array = newarray;
    }



}
