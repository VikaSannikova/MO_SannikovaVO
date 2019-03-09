package Algorithms;

import General.Interval;
import net.objecthunter.exp4j.Expression;


public interface Algorithm {
    public double function(double x);
    public void setFunction(Expression function);
    public Interval get(int index);
    public void setCharacteristics();
    public void setLengths();
    public void sort();
    public void print();
    public void makeTwoIntervals(int index);
    public void makeNewIntervals();
    public int size();

}
