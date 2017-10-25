package day01;

import scala.sys.SystemProperties;

import java.util.*;

/**
 * Created by YBX on 2017/9/25.
 * 求列表的并集,遍历list1，把list1里面的元素拿去和list2里面的做比较，把不想同的元素添加到list2
 */
public class Union {


    public static ArrayList<Integer> union(ArrayList<Integer> list1, ArrayList<Integer> list2){

        if (list1.isEmpty() || list2.isEmpty()){
            return null;
        }
        for(Integer number : list1){
            if (!list2.contains(number)){
                list2.add(number);
            }
        }
        return list2;
    }

    public static  void getArray(){
        int[] arr = new int[12];
        int[] arr1 = {1, 2, 4, 5};
        System.out.println(arr.length);
    }

    public static void main(String[] args){
        ArrayList<Integer> list1 = new ArrayList<Integer>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        list1.add(6);
        list1.add(8);
        ArrayList<Integer> list2 = new ArrayList<Integer>();
        list2.add(2);
        list2.add(4);
        list2.add(5);
        list2.add(6);
        list2.add(9);
        ArrayList<Integer> list3 = union(list1, list2);

        Iterator<Integer> it = list3.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }


        /**那么哦想对list进行排序呢
         * 方法一：Collections。sort(list)
         * 方法二：用comparator
         *
         * */
        Collections.sort(list3);
        System.out.println(list3.toString());

        list3.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                //return ((o1 > o2) ? 1 : -1);
                return new Integer(o1).compareTo(new Integer(o2));
            }
        });
        System.out.println(list3.toString());
    }
}
