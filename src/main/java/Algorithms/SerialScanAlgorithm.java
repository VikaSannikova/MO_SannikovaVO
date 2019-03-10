package Algorithms;

import General.Interval;
import net.objecthunter.exp4j.Expression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SerialScanAlgorithm implements Algorithm {
    ArrayList<Interval> intervals = new ArrayList<Interval>();
    ArrayList<Double> characteristics = new ArrayList<Double>();
    ArrayList<Double> lengths = new ArrayList<Double>();
    double minValue;
    Expression function;

    public SerialScanAlgorithm(Interval interval, Expression expression) {
        setFunction(expression);
        intervals.add(interval);
        characteristics.add(interval.getLength());
        lengths.add(interval.getLength());

        Interval tmp = new Interval(function(interval.getLeft()), function(interval.getRight()));
        minValue = tmp.min();

    }

    public double function(double x) {
        try{
            return function.setVariable("x",x).evaluate();
        }catch (Throwable cause){
            if(cause instanceof ArithmeticException && "Division by zero!".equals(cause.getMessage()))
                return Double.POSITIVE_INFINITY;
            else{
                return Double.NaN;
            }
        }
    }

    public void setFunction(Expression function) {
        this.function = function;
    }

    public Interval get(int index){
        return intervals.get(index);
    }

    public void setCharacteristics(){
       characteristics.clear();
        for(int i = 0; i<intervals.size(); i++){
            characteristics.add(i,intervals.get(i).getLength());
        }
    }

    public void setLengths(){
        lengths.clear();
        for(int i = 0; i<intervals.size(); i++){
            lengths.add(i,intervals.get(i).getLength());
        }
    }

    public ArrayList<Double> getLengths() {
        return lengths;
    }

    public void sort(){
        Collections.sort(intervals, new Comparator<Interval>() {
            public int compare(Interval o1, Interval o2) {
                return o1.getLeft().compareTo(o2.getLeft());
            }
        });
    }

    public void print(){
        for (int i = 0; i < intervals.size(); i++){
            System.out.print("Интервал: ["+intervals.get(i).getLeft()+", "+intervals.get(i).getRight()+"] ");
            System.out.println("Характеристика: "+characteristics.get(i));
        }

    }

    public double getMinValue() {
        return minValue;
    }

    public void makeTwoIntervals (int index){
        Interval first = new Interval(intervals.get(index).getLeft(), intervals.get(index).getMiddle());
        Interval second = new Interval(first.getRight(), intervals.get(index).getRight());

        Interval tmp = new Interval(minValue, function(intervals.get(index).getMiddle()));
        minValue = tmp.min();


        intervals.get(index).setRange(first.getLeft(), first.getRight());
        intervals.add(second);
        sort();
        setCharacteristics();
        setLengths();
    }
    public void makeNewIntervals(){
        int index = characteristics.indexOf(Collections.max(characteristics));
        makeTwoIntervals(index);
    }

    public int size(){
        return intervals.size();
    }

//    public static void main(String[] args) {
//        Interval interval = new Interval(0.0,9.0);
//        SerialScanAlgorithm intervals = new SerialScanAlgorithm(interval);
//        intervals.print();
//        intervals.makeNewIntervals();
//        intervals.makeNewIntervals();
//        intervals.print();
//        intervals.makeNewIntervals();
//        intervals.print();
//    }
}
