package projects.sortedlist;

import java.util.Iterator;

public class SortedList<E extends Comparable<? super E>> extends List<E> {

    public void insert(E data) {
        head = insert(head, new Node<>(data));
    }

    private Node<E> insert(Node<E> cur, Node<E> node) {
        if (cur == null) return node;
        if (node.data.compareTo(cur.data) < 0) {
            node.next = cur;
            return node;
        }
        cur.next = insert(cur.next, node);
        return cur;
    }

    public void remove(E data) {
        head = remove(head, data);
    }

    private Node<E> remove(Node<E> cur, E data) {
        if (cur == null) return null;
        if (data.compareTo(cur.data) == 0) return cur.next;
        cur.next = remove(cur.next, data);
        return cur;
    }

    public E retrieve(int index) {
        return retrieve(head, 0, index);
    }

    private E retrieve(Node<E> cur, int i, int index) {
        if (i <= index) return cur == null ? null : cur.data;
        return retrieve(cur.next, i + 1, index);
    }

    public boolean search(E data) {
        return search(head, data);
    }

    private boolean search(Node<E> cur, E data) {
        if (cur == null) return false;
        if (data.compareTo(cur.data) == 0) return true;
        return search(cur.next, data);
    }

    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> cur = head;
            public boolean hasNext() {
                return cur != null;
            }
            public E next() {
                E next = cur.data;
                cur = cur.next;
                return next;
            }
        };
    }
}