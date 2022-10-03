package org.example;


public interface Tree<T extends Comparable<? super T>> {
    enum TraversMode {
        IN_ORDER, PRE_ORDER, POST_ORDER
    }

    boolean contains(T value);

    boolean add(T value);

    boolean remove(T value);

    boolean isEmpty();

    int size();

    void display();

    void traverse(Tree.TraversMode mode);

    boolean isBalanced();

    boolean isBalanced(Node node);

    int height(Node node);
}
