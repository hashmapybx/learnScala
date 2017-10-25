package day03;


import java.util.Arrays;

/**
 * Created by YBX on 2017/10/17.
 */
public class StackTest {
    public static void main(String[] args){
        ArrayStacks arr = new ArrayStacks(5);
        arr.push(2);
        arr.push(3);
        arr.push(4);
        arr.push(5);
        System.out.println(arr.isEmpty());

    }
}

class ArrayStacks{
    private int[] array;
    private int top; //栈顶指针
    public ArrayStacks(int size){
        this.array = new int[size];
        top = -1;   //让栈顶元素指向-1表示空栈
    }

    /**
     * 判断是否为空栈
     * */
    public boolean isEmpty(){
        return -1 == top;
    }

    /**
     * 判断是否满栈
     * */
    public boolean isFull(){
        return array.length -1 == top;
    }

    /**
     * 向栈顶插人一个元素
     * */
    public void push(int iterm){
        if (isFull()){
            throw new RuntimeException("栈已满");
        }
        array[++top] = iterm;
    }

    /**
     * 弹出元素
     * */
    public int pop(){
        if (isEmpty()){
            throw new RuntimeException("栈为空");
        }
        return  array[top--] ;
    }

    /**
     * 清空栈
     * */

}