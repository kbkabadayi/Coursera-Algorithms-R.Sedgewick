import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private final int CAPACITY = 10;
    private Item[] arr;
    private int size;

    public RandomizedQueue() {
        arr = (Item[]) new Object[CAPACITY];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (size == arr.length) {
            growArr();
        }
        arr[size] = item;
        size++;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int random = StdRandom.uniform(0, size);
        if (size == arr.length / 4) {
            shrinkArr();
        }
        Item poppedItem = arr[random];
        for (int i = random; i < arr.length - 1; i++) {
            arr[i] = arr[i + 1];
        }
        size--;
        return poppedItem;
    }

    private void growArr() {
        Item[] newArr = (Item[]) new Object[arr.length * 2];
        for (int i = 0; i < arr.length; i++) {
            newArr[i] = arr[i];
        }
        arr = newArr;
    }

    private void shrinkArr() {
        Item[] newArr = (Item[]) new Object[arr.length / 2];
        for (int i = 0; i < newArr.length; i++) {
            newArr[i] = arr[i];
        }
        arr = newArr;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int random = StdRandom.uniform(0, size);
        Item peekedItem = arr[random];
        return peekedItem;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private int cursor = 0;
        private int[] randomIndex;

        public ListIterator() {
            randomIndex = new int[size];
            for (int i = 0; i < size; i++) {
                randomIndex[i] = i;
            }
            StdRandom.shuffle(randomIndex);
        }

        public boolean hasNext() {
            return cursor < size;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return arr[randomIndex[cursor++]];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        for (int i = 0; i < 15; i++) {
            int random = StdRandom.uniform(0, 100);
            rq.enqueue(random);
        }
        rq.dequeue();
        rq.dequeue();
        System.out.println(rq.dequeue());
        Iterator<Integer> iterator = rq.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
