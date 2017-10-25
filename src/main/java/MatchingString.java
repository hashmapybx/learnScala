import java.util.Scanner;
import java.util.Stack;

/**
 * Created by YBX on 2017/10/17.
 * 需求：有一行括号序列，检查这行括号是否配对 [9()][][{(}
 * 重复代码抽取方法 的快捷键ctrl + Alt + m
 *
 */
public class MatchingString {
    public static void  main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("输入一个数字n =："); //represent input n arrays
        int n = sc.nextInt();
        System.out.println("请输入一个字符串");
        Stack<Character> stack = null;
        Match(sc, n);

    }

    private static void Match(Scanner sc, int n) {
        Stack<Character> stack;
        while (n!= 0){
            String str = sc.next();
            if (str.length() % 2 == 1){  //the string's length is odd, explain the bracket do not match
                System.out.println("NO");
            }else {
                stack = new Stack<>();
                for (int i = 0; i < str.length(); i++){
                    if (stack.isEmpty()){
                        //if the stack is empty, we will the character push the stack
                        stack.push(str.charAt(i));
                    }else if (stack.peek() == '[' && str.charAt(i) == ']' || stack.peek() == '(' && str.charAt(i) == ')'){
                        stack.pop();  // pop  there happen the pop
                    }else {
                        stack.push(str.charAt(i));
                    }
                }
                if (stack.isEmpty()){  //success
                    System.out.println("Yes");
                }else{                 //to fail for the  matching operation
                    System.out.println("NO");
                }
            }
            n--;
        }
    }



}
