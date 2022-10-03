package org.example;

public class Balance {
    static int t = 0;
    static int f = 0;
    static float percent = 0;
    static int countOfNumbers = 5;
    static int countOfTrees = 1000000;
    public static void main(String[] args) {
        Tree tree;
        for (int i = 0; i < countOfTrees; i++) {
            tree = new TreeImpl();
            fillTree(tree);
        }
        percent = ((float) t / (float) countOfTrees) * 100f;
        System.out.println("If 5 numbers, Balanced quintity = " + t + ", Not Balanced quantity = " + f + "; ---> Its " + percent + "%");
        countOfNumbers = 20;
        t = 0;
        f = 0;
        for (int i = 0; i < countOfTrees; i++) {
            tree = new TreeImpl();
            fillTree(tree);
        }
        percent = ((float) t / (float) countOfTrees) * 100f;
        System.out.println("If 20 numbers, Balanced quintity = " + t + ", Not Balanced quantity = " + f + "; ---> Its " + percent + "%");
        countOfNumbers = 50;
        t = 0;
        f = 0;
        for (int i = 0; i < countOfTrees; i++) {
            tree = new TreeImpl();
            fillTree(tree);
        }
        percent = ((float) t / (float) countOfTrees) * 100f;
        System.out.println("If 50 numbers, Balanced quintity = " + t + ", Not Balanced quantity = " + f + "; ---> Its " + percent + "%");
    }

    static void fillTree(Tree<Integer> tree) {
        int[] ints = new int[countOfNumbers];
        for (int i = 0; i < ints.length; i++) {
            int a;
            do {
                a = (int) (Math.random() * 26);
                if (Math.random() * 100 > 50) a = -a;
            } while (isHas(a, ints));
            ints[i] = a;
            tree.add(ints[i]);
        }
//        tree.display();
//        System.out.println(tree.isBalanced());
        if (tree.isBalanced()) t++;
        else f++;
    }

    static boolean isHas(int a, int[] ints) {
        for (int i = 0; i < ints.length; i++) {
            if (a == ints[i]) return true;
        }
        return false;
    }
}
