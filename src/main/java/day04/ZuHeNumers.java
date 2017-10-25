package day04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Created by YBX on 2017/10/18.
 * 设有n个正整数，将他们连接成一排，组成一个最大的多位整数。
   如:n=3时，3个整数13,312,343,连成的最大整数为34331213。
   如:n=4时,4个整数7,13,4,246连接成的最大整数为7424613。
 */
public class ZuHeNumers {
    static List totalNum = new ArrayList();
    static int kk = 0;   //list的指针
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入两个整数用,隔开：");
        String inputs = sc.next();
        String[] numbers = inputs.split(",");
        new ZuHeNumers().zuHe(numbers, numbers.length, 0);

        Collections.sort(totalNum);
        System.out.println("总组成了" +kk+ "条组合");
        System.out.println("所有的组合为"+ totalNum.toString());
        System.out.println("最大组合数为：" + totalNum.get(kk - 1));
    }
    public void zuHe(String[] array, int n, int k){
        if (n == k){
            String s = "";
            for (int i = 0; i<n; i++){
                s += array[i];
            }
            totalNum.add(s);
            ++kk;
        }else {
            for (int i = k; i <n;i++){
                swap(array, k, i);
                zuHe(array, n, k+1);
                swap(array, i, k);
            }
        }
    }
    private void swap(String[] array, int a, int b){
        String temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }
}

