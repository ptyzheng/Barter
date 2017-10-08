/**
 * Slightly modified version of ArrayHeap for purposes of this project.
 */
public class MaxPQ<T> {
    private Node[] contents;
    private int size;

    public MaxPQ() {
        contents = new MaxPQ.Node[16];

        /* Add a dummy item at the front of the ArrayHeap so that the getLeft,
         * getRight, and parent methods are nicer. */
        contents[0] = null;

        /* Even though there is an empty spot at the front, we still consider
         * the size to be 0 since nothing has been inserted yet. */
        size = 0;
    }

    /**
     * Returns the index of the node to the left of the node at i.
     */
    private static int leftIndex(int i) {
        return 2 * i;
    }

    /**
     * Returns the index of the node to the right of the node at i.
     */
    private static int rightIndex(int i) {
        return 2 * i + 1;
    }

    /**
     * Returns the index of the node that is the parent of the node at i.
     */
    private static int parentIndex(int i) {
        return i / 2;
    }

    /**
     * Gets the node at the ith index, or returns null if the index is out of
     * bounds.
     */
    private Node getNode(int index) {
        if (!inBounds(index)) {
            return null;
        }
        return contents[index];
    }

    /**
     * Returns true if the index corresponds to a valid item. For example, if
     * we have 5 items, then the valid indices are 1, 2, 3, 4, 5. Index 0 is
     * invalid because we leave the 0th entry blank.
     */
    private boolean inBounds(int index) {
        if ((index > size) || (index < 1)) {
            return false;
        }
        return true;
    }

    /**
     * Swap the nodes at the two indices.
     */
    private void swap(int index1, int index2) {
        Node node1 = getNode(index1);
        Node node2 = getNode(index2);
        contents[index1] = node2;
        contents[index2] = node1;
    }


    /**
     * Returns the index of the node with smaller priority. Precondition: not
     * both nodes are null.
     */
    private int max(int index1, int index2) {
        Node node1 = getNode(index1);
        Node node2 = getNode(index2);
        if (node1 == null) {
            return index2;
        } else if (node2 == null) {
            return index1;
        } else {
            int[] priority1 = node1.priority();
            int[] priority2 = node2.priority();
            for (int i = 0; i < node1.priority().length; i++) {
                if (priority1[i] > priority2[i]) {
                    return index1;
                } else if (priority1[i] < priority2[i]) {
                    return index2;
                } else {
                    if (i == priority1.length - 1) {
                        return index1;
                    }
                }
            }
        }
        return index1; //this line shouldn't be executed ever
    }

    /**
     * Bubbles up the node currently at the given index.
     */
    private void swim(int index) {
        // Throws an exception if index is invalid. DON'T CHANGE THIS LINE.
        try {
            validateSinkSwimArg(index);
            if (max(index, parentIndex(index)) == index) {
                this.swap(index, parentIndex(index));
                this.swim(parentIndex(index));
            }
        } catch (RuntimeException e) {
            return;
        }
    }

    /**
     * Bubbles down the node currently at the given index.
     */
    private void sink(int index) {
        // Throws an exception if index is invalid. DON'T CHANGE THIS LINE.
        try {
            validateSinkSwimArg(index);
            if (max(index, leftIndex(index)) != index || max(index, rightIndex(index)) != index) {
                int maxT = this.max(leftIndex(index), rightIndex(index));
                this.swap(index, maxT);
                this.sink(maxT);
            }
        } catch (RuntimeException e) {
            return;
        }
    }

    /**
     * Inserts an item with the given priority value. This is enqueue, or offer.
     * To implement this method, add it to the end of the ArrayList, then swim it.
     */
    public void insert(T item, int[] priority) {
        /* If the array is totally full, resize. */
        if (size + 1 == contents.length) {
            resize(contents.length * 2);
        }
        size = size + 1;
        this.contents[size] = new Node(item, priority);
        this.swim(size);
    }

    /**
     * Returns the Node with the smallest priority value, but does not remove it
     * from the heap. To implement this, return the item in the 1st position of the ArrayList.
     */
    public T peek() {
        return this.contents[1].item();
    }

    /**
     * Returns the Node with the smallest priority value, and removes it from
     * the heap. This is dequeue, or poll. To implement this, swap the last
     * item from the heap into the root position, then sink the root. This is
     * equivalent to firing the president of the company, taking the last
     * person on the list on payroll, making them president, and then demoting
     * them repeatedly. Make sure to avoid loitering by nulling out the dead
     * item.
     */
    public T removeMax() {
        T temp = this.contents[1].item();
        this.contents[1] = this.contents[size];
        this.contents[size] = null;
        this.sink(1);
        size = size - 1;
        return temp;
    }

    /**
     * Returns the number of items in the PQ. This is one less than the size
     * of the backing ArrayList because we leave the 0th element empty. This
     * method has been implemented for you.
     */
    public int size() {
        return size;
    }

    /**
     * Change the node in this heap with the given item to have the given
     * priority. You can assume the heap will not have two nodes with the same
     * item. Check item equality with .equals(), not ==. This is a challenging
     * bonus problem, but shouldn't be too hard if you really understand heaps
     * and think about the algorithm before you start to code.
     */
    public boolean changePriority(T item, int index) {
        for (int i = 1; i <= size; i++) {
            if (this.contents[i].item().equals(item)) {
                this.contents[i].setPriority(index, this.contents[i].priority()[index] + 1);
                this.swim(i);
                if (this.contents[i] != null) {
                    this.sink(i);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Prints out the heap sideways. Provided for you.
     */
    @Override
    public String toString() {
        return toStringHelper(1, "");
    }

    /* Recursive helper method for toString. */
    private String toStringHelper(int index, String soFar) {
        if (getNode(index) == null) {
            return "";
        } else {
            String toReturn = "";
            int rightChild = rightIndex(index);
            toReturn += toStringHelper(rightChild, "        " + soFar);
            if (getNode(rightChild) != null) {
                toReturn += soFar + "    /";
            }
            toReturn += "\n" + soFar + getNode(index) + "\n";
            int leftChild = leftIndex(index);
            if (getNode(leftChild) != null) {
                toReturn += soFar + "    \\";
            }
            toReturn += toStringHelper(leftChild, "        " + soFar);
            return toReturn;
        }
    }


    /**
     * Throws an exception if the index is invalid for sinking or swimming.
     */
    private void validateSinkSwimArg(int index) {
        if (index < 1) {
            throw new IllegalArgumentException("Cannot sink or swim nodes with index 0 or less");
        }
        if (index > size) {
            throw new IllegalArgumentException("Cannot sink or swim nodes with index greater than current size.");
        }
        if (contents[index] == null) {
            throw new IllegalArgumentException("Cannot sink or swim a null node.");
        }
    }

    private class Node {
        private T myItem;
        private int[] myPriority;

        private Node(T item, int[] priority) {
            myItem = item;
            myPriority = priority;
        }

        public T item() {
            return myItem;
        }

        public int[] priority() {
            return myPriority;
        }

        public void setPriority(int index, int priority) {
            myPriority[index] = priority;
        }

        @Override
        public String toString() {
            return myItem.toString() + ", " + myPriority;
        }
    }


    /**
     * Helper function to resize the backing array when necessary.
     */
    private void resize(int capacity) {
        Node[] temp = new MaxPQ.Node[capacity];
        for (int i = 1; i <= temp.length; i++) {
            temp[i] = contents[i];
        }
        contents = temp;
    }
}
