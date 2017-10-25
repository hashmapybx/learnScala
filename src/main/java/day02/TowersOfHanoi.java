package day02;


import akka.dispatch.Foreach;

/**
 * 这里包括了递归的经典案例
 * Created by YBX on 2017/10/2.
 * 汉诺塔问题。
 * 将第一根柱子上的碟子移动到另一个柱子上
 * 规则：每次只能移动一个；每次只能移动最上面的碟子到另一根柱子上；任何时候不能出现大圆盘在小圆盘上面
 */
public class TowersOfHanoi {

    public static void removeDiskOfTower(int n, char from, char inner, char to) {
        //如果只有一个disk,则直接移动，返回就ok
        if (n == 1) {
            System.out.println("从" + from + " 移动盘子" + n + " 号到" + to);
        } else {
            //利用C柱作辅助，将A的n-1移动到圆盘B柱
            removeDiskOfTower(n - 1, from, to, inner);
            //余下的DIsk从A柱移动到C柱
            System.out.println("从" + from + " 移动盘子" + n + " 号到" + to);
            //利用A柱作为辅助，将B柱上的n-1个圆盘移到C柱
            removeDiskOfTower(n - 1, inner, to, from);
        }
    }

    //给定一个数组,用递归判断数组中的元素是否有序
    public static int isArraySorted(int[] arr, int index) {
        if (arr.length == 1) {
            return 1;
        }
        return (arr[index - 1] <= arr[index - 2]) ? 0 : isArraySorted(arr, index - 1);
    }

    //归并排序。快速排序

    /**
     * 归并排序的原理：时间复杂度O（nlogn）
     * 对于一个数组，分成两个子序列，进行排序。将两个有序的子序列进行合并
     * 设置两个指针，最初时指向已经排好序的起始位置
     * 比较指针所指的两个元素，将小的放入合并的空间
     */
    public static void sort(int[] data, int left, int right) {
        if (left >= right) {
            return;
        }
        int center = (left + right) / 2;
        //对左边的子序列进行递归
        sort(data, left, center);
        //对右边的子序列进行递归
        sort(data, center + 1, right);
        //合并操作
        merge(data, left, center, right);
    }

    private static void merge(int[] data, int left, int center, int right) {
        int[] temArr = new int[data.length];
        //右边数组第一个元素的索引为
        int mid = center + 1;
        //左边数组的第一个索引
        int tmp = left;
        //third记录临时数组的索引
        int third = left;
        //左边第一个元素索引小于左边最后一个元素索引；右边第一个元素索引小于右边最后一个元素索引。
        while (left <= center && mid <= right) {
            //从两个数组中取出来最小的放入临时数组
            if (data[left] <= data[mid]) {   //左边第一个元素<=右边第一个元素
                temArr[third++] = data[left++];
            } else {
                temArr[third++] = data[mid++];
            }
        }
        //剩余部分依次放入临时数组
        while (mid <= right) {
            temArr[third++] = data[mid++];
        }
        while (left <= center) {
            temArr[third++] = data[left++];
        }

        //将临时数组的的内容拷贝到原数组
        while (tmp <= right) {
            data[tmp] = temArr[tmp++];
        }
    }

    //打印数组
    public static void print(int[] data) {
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + "\t");
        }
        System.out.println();
    }

    public static void mergeSort(int[] data) {
        sort(data, 0, data.length - 1);
    }

    //快速排序
    public static void quickSort(int[] arr, int low, int high) {
        int i, j, temp, t;
        //i,j代表用左右两边同时开工
        if(low > high){
            return;
        }
        i = low;
        j = high;
        temp = arr[low];
        //选择第一个元素为基准值
        while (i < j) {
            //先从右边出发哨兵
            while (temp < arr[j] && i < j) {
                j--;
            }
            while (temp > arr[i] && i < j) {
                i++;
            }
            if (i < j) {
                t = arr[j];
                arr[j] = arr[i];
                arr[i] = t;
            }
        }
        //当i和j相遇的时候停止。即i = j时候。
        arr[low] = arr[i];
        arr[i] = temp;

        //递归调用处理左边的序列
        quickSort(arr, low, j - 1);
        //递归调用处理右边的序列
        quickSort(arr, j + 1, high);
    }

    public static void main(String[] args) {
//        int nDisks = 3;
//        removeDiskOfTower(3, 'A', 'B', 'C');
        //归并排序测试
//        int[] data = {4, 3, 5, 2, 6, 7, 9, 1};
//        print(data);
//        mergeSort(data);
//        System.out.println("排序后的数组： ");
//        print(data);

        //快速排序测试
        int[] arr = {10, 7, 2, 4, 7, 62, 3, 4, 2, 1, 8, 9, 19};
        quickSort(arr, 0, arr.length - 1);

        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
