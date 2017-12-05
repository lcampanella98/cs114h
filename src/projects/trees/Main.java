package projects.trees;

import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.insert(3);
        bst.insert(1);
        bst.insert(4);
        bst.insert(9);
        bst.insert(2);
        bst.insert(7);
        bst.insert(6);
        bst.insert(8);
        bst.insert(5);
        bst.insert(15);
        bst.insert(12);
        bst.insert(11);
        bst.insert(13);
        bst.insert(16);
        bst.insert(18);
        bst.insert(0);
        printBST(bst);
        bst.remove(9);
        System.out.println("\n\n");
        printBST(bst);
    }

    private static <E extends Comparable<? super E>> void printBST(BinarySearchTree<E> b) {
        Iterator<E> it = b.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
