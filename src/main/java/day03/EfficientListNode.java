package day03;

/**
 * Created by YBX on 2017/10/14.
 * 一种高效的存储双向链表。它的指针域是指向后继结点的地址与前驱结点的差（异或运算的结果）
 * IDEA生成get,set方法的快捷键是Alt+insert
 * 其中头结点的ptrdiff与N表尾的null异或结果是表头结点的后继ptrdiff
 */
public class EfficientListNode {
    private int data;
    private EfficientListNode ptrdiff;

    public EfficientListNode(int data) {
        this.data = data;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public EfficientListNode getPtrdiff() {
        return ptrdiff;
    }

    public void setPtrdiff(EfficientListNode ptrdiff) {
        this.ptrdiff = ptrdiff;
    }
}
