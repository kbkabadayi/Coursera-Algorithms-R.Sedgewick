import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node head;
    private Node last;
    private int size;

    public Deque() {
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node newHead = new Node();
        newHead.item = item;
        if (isEmpty()) {
            head = newHead;
            last = newHead;
        }
        else {
            newHead.next = head;
            head.prev = newHead;
            head = newHead;
        }
        size++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node newLast = new Node();
        newLast.item = item;
        if (isEmpty()) {
            head = newLast;
            last = newLast;
        }
        else {
            last.next = newLast;
            newLast.prev = last;
            last = newLast;
        }
        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node poppedNode = head;
        if (size == 1) {
            head = null;
            last = null;
        }
        else {
            head.next.prev = null;
            head = head.next;
        }
        poppedNode.next = null;
        size--;
        return poppedNode.item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node poppedNode = last;
        if (size == 1) {
            head = null;
            last = null;
        }
        else {
            last = last.prev;
            last.next = null;
        }

        poppedNode.prev = null;
        size--;
        return poppedNode.item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = head;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item thisItem = current.item;
            current = current.next;
            return thisItem;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        System.out.println(deque.removeFirst());
        deque.addFirst(2);
        System.out.println(deque.removeFirst());
        deque.addLast(0);
        System.out.println(deque.removeFirst());
        deque.addLast(3);
        deque.addLast(4);
        deque.addFirst(8);
        for (Integer integer : deque) {
            System.out.println(integer);
        }
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeLast());
        System.out.println(deque.size);
        System.out.println(deque.isEmpty());
    }
}
