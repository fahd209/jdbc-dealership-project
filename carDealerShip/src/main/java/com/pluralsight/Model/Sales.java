package com.pluralsight.Model;

public class Sales extends Contract
{
    private final double SALES_TAX;
    private final double RECORDING_FEE;
    private double processingFee;
    private boolean financed;

    public Sales(String date, String customerName, String customerEmail, Vehicle vehicleSold , boolean financed)
    {
        super(date, customerName, customerEmail, vehicleSold);
        this.SALES_TAX = vehicleSold.getPrice() * .05;
        this.RECORDING_FEE = 100;
        this.processingFee = vehicleSold.getPrice() < 10000 ? 295 : 495;
        this.financed = financed;
    }

    public double getSALES_TAX() {
        return SALES_TAX;
    }

    public double getRECORDING_FEE() {
        return RECORDING_FEE;
    }

    public double getProcessingFee() {
        return processingFee;
    }

    public void setProcessingFee(double processingFee) {
        this.processingFee = processingFee;
    }

    public boolean isFinanced() {
        return financed;
    }

    public void setFinanced(boolean financed) {
        this.financed = financed;
    }

    @Override
    public double getTotalPrice()
    {
        return getSalePrice() + getSALES_TAX() + getRECORDING_FEE() + getProcessingFee();
    }

    @Override
    public double getMonthlyPayment()
    {
        double monthlyInterestRate = 0.0425 / 12;
        double monthlyPayment = 0;

        if(isFinanced())
        {
            if(getTotalPrice() >= 10000)
            {
                monthlyPayment = monthlyInterestRate * getTotalPrice() / (1 - Math.pow(1 + monthlyInterestRate, -48));
            }
            else
            {
                double monthlyInterestRateBelow10000 = 0.0525 / 12;
                monthlyPayment = monthlyInterestRateBelow10000 * getTotalPrice() / (1 - Math.pow(1 + monthlyInterestRateBelow10000, -24));
            }
        }
        return monthlyPayment;
    }
}
