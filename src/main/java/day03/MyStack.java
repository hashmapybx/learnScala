package day03;


/**
 * Created by YBX on 2017/10/17.
 */
public interface MyStack<T> {

    public T pop();         //弹栈操作

    public void push(T element);  //入栈操作

    public T peek();  //获取栈顶元素
    public boolean isEmpty();

    public void clear();
}
