package day02;


import java.util.Arrays;

/**
 * Created by YBX on 2017/10/3.
 * 堆排序 复杂度O（nlogn）
 * 思想就是构建大根堆，就是根节点的值不小于子节点的值
 * ① 初始化操作：将R[1..n]构造为初始堆；
   ②每一趟排序的基本操作：将当前无序区的堆顶记录R[1]和该区间的最后一个记录交换，然后将新的无序区调整为堆(亦称重建堆)。
   注意：
    ①只需做n-1趟排序，选出较大的n-1个关键字即可以使得文件递增有序。
   ②用小根堆排序与利用大根堆类似，只不过其排序结果是递减有序的。堆排序和直接选择排序相反：在任何时刻堆排序中无序区总是在有序区之前，
   且有序区是在原向量的尾部由后往前逐步扩大至整个向量为止。
   重点就是adjustHeap(int[] a， int parent, int length) 方法
 */
public class HeapSort {
    public static void sort(int[] a){
        //循环建立堆，若父节点索引为i,那么左节点索引为i*2+1;右节点的索引为2*i+2; 子节点的长度为length,则父节点的长度小于length/2;
        for(int i = a.length /2; i>=0;i--){ //循环遍历子节点的父节点
           adjustHeap(a, i, a.length -1);
        }

        //进行n-1次循环完成排序

        for (int i = a.length-1; i > 0; i--){
            //交换最后一个元素和第一个元素
            int temp = a[i];
            a[i] = a[0];
            a[0] = temp;

            adjustHeap(a, 0, i);
        }
    }

    //将数组调整为大根堆,大根堆的父节点为数组中最大的元素
    private static void adjustHeap(int[] a, int parent, int length) {
        int temp = a[parent]; //父节点
        int child = 2*parent +1;  //子节点的索引
        while (child < length){   //判断子节点是否为最大的索引
            if (child +1 < length && a[child] < a[child+1]){ //存在右节点，并且左节点的值小于右节点的值
                child++; //将左节点转移为右节点
            }
            if (temp > a[child]){  //父节点大于子节点，则退出当前的情况
                break;
            }

            //将子节点的值赋给父节点
            a[parent] = a[child];
            //选取左子节点继续往下帅选
            parent =child;
            child = 2 * parent +1;
        }
        a[parent] = temp;
    }


    public static void main(String[] args){
        int[] array = { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 };
        HeapSort.sort(array);
        System.out.println("排序后数组：" + Arrays.toString(array));
    }
}
