package com.debuggor.mockinterview.common;

public class LoginTests {
    public static void main(String[] args) throws InterruptedException {
        Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        for (int i = 0; i < 10; i++) {
            int j = i;
            for (; j < i + 5 && j < 10; j++) {
                System.out.print(arr[j] + "-");
            }
            if (j >= 9) {
                for (int k = 0; k < (i + 5 - j); k++) {
                    System.out.print(arr[k] + "-");
                }
            }
            if (i == 9) i = -1;
            System.out.println();
        }
    }
}
