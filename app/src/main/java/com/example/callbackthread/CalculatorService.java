package com.example.callbackthread;

public class CalculatorService {

    /// Sum
    public int calculateSum(int num1, int num2) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return num1 + num2;
    }

    /// Sub
    public int calculateSub(int num1, int num2) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return num1 - num2;
    }

}
