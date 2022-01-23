package com.techmaker.calcengine;

@CommandKeyword("power")
public class PowerOf implements MathProcessing {
    @Override
    public double doCalculation(double leftVal, double rightVal) {
        //formattedOutput = leftVal + " to the power of " + rightVal + " is " + product;
        return Math.pow(leftVal, rightVal);
    }
}
