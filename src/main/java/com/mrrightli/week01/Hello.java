package com.mrrightli.week01;

public class Hello {
    static {
        System.out.println("Hello Class Initialized!");
    }
    public static void main(String[] args) {
        System.out.println("Hello");
        int[] arr1 = {5, 6, 7, 8};
        String[] arr2 = {"+", "-", "*", "/"};
        int[] arr3 = {1, 2, 3, 4};

        for (int i = 0; i < 4; i++) {
            if (arr2[i].equals("+")) {
                System.out.println(add(arr1[i], arr3[i]));
            }
            if (arr2[i].equals("-")) {
                System.out.println(subtract(arr1[i], arr3[i]));
            }
            if (arr2[i].equals("*")) {
                System.out.println(multiply(arr1[i], arr3[i]));
            }
            if (arr2[i].equals("/")) {
                System.out.println(divide(arr1[i], arr3[i]));
            }
        }
    }

    //
    public static int add(int a, int b) {
        return a + b;
    }

    public static int subtract(int a, int b) {
        return a - b;
    }

    public static int multiply(int a, int b) {
        return a * b;
    }

    public static int divide(int a, int b) {
        return a / b;
    }

}
