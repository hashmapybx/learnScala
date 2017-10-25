package day03;

/**
 * Created by YBX on 2017/10/17.
 */
public class MyArrayStackClient {
    public static void main(String[] args) {
//        ArrayStack<Integer> stack = new ArrayStack<Integer>();
        ArrayStack<Integer> stack = new ArrayStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        System.out.println(stack.isEmpty());
        System.out.println(stack.peek());
//      System.out.println(stack.pop());
        System.out.println(stack.size());
        stack.clear();
        System.out.println(stack.size());
    }
}
