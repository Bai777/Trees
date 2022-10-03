package org.example;

public class FillTrees {
    static int countOfNums = 30;
    public static void main(String[] args) {

        Tree[] trees = new Tree[countOfNums];
        fillTrees(trees);
        displayAll(trees);
    }

    private static void displayAll(Tree[] trees) {
        for (int i = 0; i < trees.length; i++) {
            trees[i].display();
        }
    }

    static void fillTrees(Tree<Integer>[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new TreeImpl();
            fillTree(arr[i]);
        }
    }

    static void fillTree(Tree<Integer> tree) {
        int[] ints = new int[countOfNums];
        for (int i = 0; i < ints.length; i++) {
            int a;
            do {
                a = (int) (Math.random() * 26);
                if (Math.random() * 100 > 50) a = -a;
            } while (isHas(a, ints));
            ints[i] = a;
            tree.add(ints[i]);
        }
    }

    static boolean isHas(int a, int[] ints) {
        for (int i = 0; i < ints.length; i++) {
            if (a == ints[i]) return true;
        }
        return false;
    }
}
