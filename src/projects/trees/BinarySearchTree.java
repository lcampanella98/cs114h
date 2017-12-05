package projects.trees;

/*
 *
 *  BinarySearchTree.java
 *
 */

import java.util.Iterator;
import java.util.Stack;

public class BinarySearchTree<E extends Comparable<? super E>> extends BinaryTree<E> {


    public void insert(E data) {
        root = insertR(root, data);
    }

    private Node<E> insertR(Node<E> cur, E data) {
        if (cur == null) return new Node<E>(data);
        int cmp = data.compareTo(cur.data);
        if (cmp < 0) {
            cur.left = insertR(cur.left, data);
        } else if (cmp > 0) {
            cur.right = insertR(cur.right, data);
        }
        return cur;
    }


    public void remove(E data) {
        root = removeR(root, data);
    }

    private Node<E> removeR(Node<E> cur, E data) {
        if (cur == null) return null;
        int cmp = data.compareTo(cur.data);
        if (cmp < 0) {
            cur.left = removeR(cur.left, data);
            return cur;
        }
        else if (cmp > 0) {
            cur.right = removeR(cur.right, data);
            return cur;
        }
        if (cur.left == null || cur.right == null) {
            return cur.left == null ? cur.right : cur.left;
        }
        Node<E> iop = findIOP(cur);
        cur.data = iop.data;
        cur.left = removeR(cur.left, iop.data);
        return cur;
    }

    private Node<E> findIOP(Node<E> cur) {
        Node<E> temp = cur.left;
        while (temp.right != null) temp = temp.right;
        return temp;
    }


    public boolean search(E data) {
        return searchR(root, data);
    }

    private boolean searchR(Node<E> cur, E data) {
        if (cur == null) return false;
        int cmp = data.compareTo(cur.data);
        if (cmp < 0) return searchR(cur.left, data);
        else if (cmp > 0) return searchR(cur.right, data);
        return true;
    }


    public Iterator<E> iterator() {
        Stack<Node<E>> stack = new Stack<>();
        stack.push(root);

        return new Iterator<E>() {
            private Stack<Node<E>> s = stack;

            public boolean hasNext() {
                return !s.empty();
            }


            public E next() {
                Node<E> next = s.pop();
                if (next.right != null) s.push(next.right);
                if (next.left != null) s.push(next.left);
                return next.data;
            }
        };
    }
}