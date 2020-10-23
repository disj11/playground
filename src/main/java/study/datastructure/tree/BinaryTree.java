package study.datastructure.tree;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree<T> {
    private Node<T> root;

    public static <T> BinaryTree<T> generate(T... values) {
        BinaryTree<T> tree = new BinaryTree<>();
        for (T value : values) {
            tree.add(value);
        }
        return tree;
    }

    public void add(T value) {
        root = addRecursive(root, value);
    }

    private Node<T> addRecursive(Node<T> current, T value) {
        if (current == null) {
            return new Node<>(value);
        }

        if (current.getLeft() == null) {
            current.setLeft(addRecursive(current.getLeft(), value));
        } else if (current.getRight() == null) {
            current.setRight(addRecursive(current.getRight(), value));
        } else {
            addRecursive(current.getLeft(), value);
        }

        return current;
    }

    public void printUsingBfs() {
        printUsingBfs(root);
    }

    private void printUsingBfs(Node<?> node) {
        if (node == null) {
            return;
        }

        Queue<Node<?>> queue = new LinkedList<>(Collections.singletonList(node));
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node<?> current = queue.poll();
                if (current == null) {
                    continue;
                }

                System.out.println(current.getValue());
                queue.addAll(Arrays.asList(current.getLeft(), current.getRight()));
            }
        }
    }

    public static void main(String[] args) {
        BinaryTree<Integer> tree = BinaryTree.generate(1, 2, 3, 4, 5);
        tree.printUsingBfs();
    }
}

class Node<T> {
    private T value;
    private Node<T> left;
    private Node<T> right;

    public Node(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }
}