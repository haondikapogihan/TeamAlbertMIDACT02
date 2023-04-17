package model;

public class Metrics {
    public double divide(double a, double b, String s) {
        double ave = a/b;
        if (s == "Percentage") {
            return Math.round(ave*100);
        } else
            return Math.round(ave);
    }
}
