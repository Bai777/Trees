package org.example;

import java.util.Stack;

public class TreeImpl<T extends Comparable<? super T>> implements Tree<T>  {
    private Node<T> root;
    private int size;

    private class NodeAndParent {
        private Node<T> current;
        private Node<T> parent;

        public NodeAndParent(Node<T> current, Node<T> parent) {
            this.current = current;
            this.parent = parent;
        }
    }

    @Override
    public boolean contains(T value) {
        NodeAndParent nodeAndParent = doFind(value);
        return nodeAndParent.current != null;
    }

    private TreeImpl.NodeAndParent doFind(T value) {
        Node<T> current = root;
        Node<T> parent = null;

        while (current != null) {
            if (current.getValue().equals(value)) {
                return new TreeImpl.NodeAndParent(current, parent);
            }

            parent = current;

            if (current.isLeftChild(value)) {
                current = current.getLeftChild();
            } else {
                current = current.getRightChild();
            }
        }

        return new NodeAndParent(null, parent);
    }

    @Override
    public boolean add(T value) {
        NodeAndParent nodeAndParent = doFind(value);

        if (nodeAndParent.current != null) {
            //nodeAndp.current.incRepeat();
            return false;
        }

        Node<T> parent = nodeAndParent.parent;
        Node<T> node = new Node<>(value);

        if (isEmpty()) {
            root = node;
        } else if (parent.isLeftChild(value)) {
            parent.setLeftChild(node);
        } else {
            parent.setRightChild(node);
        }

        size++;

        return true;
    }

    @Override
    public boolean remove(T value) {
        NodeAndParent nodeAndParent = doFind(value);
        Node<T> parent = nodeAndParent.parent;
        Node<T> removed = nodeAndParent.current;

        if (removed == null) {
            return false;
        }

        if (removed.isLeaf()) {
            removeLeafNode(removed, parent);
        } else if (removed.hasOnlyOneChild()) {
            removeNodeWithOneChild(removed, parent);
        } else {
            removedNodeWithAllChildren(removed, parent);
        }


        size--;
        return true;
    }


    private void removeLeafNode(Node<T> removed, Node<T> parent) {
        if (removed == root) {
            root = null;
        } else if (parent.isLeftChild(removed.getValue())) {
            parent.setLeftChild(null);
        } else {
            parent.setRightChild(null);
        }
    }

    private void removeNodeWithOneChild(Node<T> removed, Node<T> parent) {

        Node<T> child = removed.getLeftChild() != null ? removed.getLeftChild() : removed.getRightChild();

        if (removed == root) {
            root = null;
        } else if (parent.isLeftChild(removed.getValue())) {
            parent.setLeftChild(child);
        } else {
            parent.setRightChild(child);
        }
    }

    private void removedNodeWithAllChildren(Node<T> removed, Node<T> parent) {
        Node<T> successor = getSuccessor(removed);

        if (removed == root) {
            root = successor;
        } else if (parent.isLeftChild(removed.getValue())) {
            parent.setLeftChild(successor);
        } else {
            parent.setRightChild(successor);
        }
    }
    private Node<T> getSuccessor(Node<T> removed) {
        Node<T> successor = removed;
        Node<T> parent = null;
        Node<T> current = removed.getRightChild();

        while (current != null) {
            parent = successor;
            successor = current;
            current = current.getLeftChild();
        }

        if (successor != removed.getRightChild() && parent != null) {
            parent.setLeftChild(successor.getRightChild());
            successor.setRightChild(removed.getRightChild());
        }
        successor.setLeftChild(removed.getLeftChild());

        return successor;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void traverse(TraversMode mode) {
        switch (mode) {
            case PRE_ORDER -> preOrder(root); //прямой
            case IN_ORDER -> inOrder(root); //центированный
            case POST_ORDER -> postOrder(root); //обратный
        }
        System.out.println();
    }

    @Override
    public boolean isBalanced() {
        return isBalanced(root);
    }

    @Override
    public boolean isBalanced(Node node) {
        return (node == null) ||
                isBalanced(node.getLeftChild()) &&
                        isBalanced(node.getRightChild()) &&
                        Math.abs(height(node.getLeftChild()) - height(node.getRightChild())) <= 1;
    }

    @Override
    public int height(Node node) {
        return node == null ? 0 : 1 + Math.max(height(node.getLeftChild()), height(node.getRightChild()));
    }

    private void preOrder(Node<T> current) {
        if (current == null) {
            return;
        }

        System.out.print(current.getValue() + " ");
        preOrder(current.getLeftChild());
        preOrder(current.getRightChild());
    }

    private void inOrder(Node<T> current) {
        if (current == null) {
            return;
        }

        inOrder(current.getLeftChild());
        System.out.print(current.getValue() + " ");
        inOrder(current.getRightChild());
    }

    private void postOrder(Node<T> current) {
        if (current == null) {
            return;
        }

        postOrder(current.getLeftChild());
        postOrder(current.getRightChild());
        System.out.print(current.getValue() + " ");
    }

    public void display() {
        Stack<Node<T>> globalStack = new Stack<>();
        globalStack.push(root);
        int nBlanks = 64;

        boolean isRowEmpty = false;
        System.out.println("................................................................");

        while (!isRowEmpty) {
            Stack<Node<T>> localStack = new Stack<>();

            isRowEmpty = true;
            for (int i = 0; i < nBlanks; i++) {
                System.out.print(" ");
            }

            while (!globalStack.isEmpty()) {
                Node<T> tempNode = globalStack.pop();
                if (tempNode != null) {
                    System.out.print(tempNode.getValue());
                    localStack.push(tempNode.getLeftChild());
                    localStack.push(tempNode.getRightChild());
                    if (tempNode.getLeftChild() != null || tempNode.getRightChild() != null) {
                        isRowEmpty = false;
                    }
                } else {
                    System.out.print("--");
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int j = 0; j < nBlanks * 2 - 2; j++) {
                    System.out.print(" ");
                }
            }

            System.out.println();

            while (!localStack.isEmpty()) {
                globalStack.push(localStack.pop());
            }

            nBlanks /= 2;
        }
        System.out.println("................................................................");
    }
}
