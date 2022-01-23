package com.techmaker.calcengine;

@CommandKeyword("multiply")
public class Multiplier implements MathProcessing {
    @Override
    public double doCalculation(double leftVal, double rightVal) {
        return leftVal * rightVal;
    }
}
