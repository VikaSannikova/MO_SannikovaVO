package Algorithms;

import General.Interval;
import net.objecthunter.exp4j.Expression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PiyavskiMethod implements Algorithm {
    ArrayList<Interval> intervals = new ArrayList<Interval>();
    ArrayList<Double> characteristics = new ArrayList<Double>();
    ArrayList<Double> lengths = new ArrayList<Double>();
    double minValue;
    Expression function;
    double m;

    public PiyavskiMethod(double m, Interval interval, Expression expression) {
        setFunction(expression);
        intervals.add(interval);
        set_m(m);
        characteristics.add(0.5*m*(intervals.get(0).getRight()-intervals.get(0).getLeft())- //ошибка гдето здесь
                (function(intervals.get(0).getRight())+function(intervals.get(0).getLeft()))/2);
        lengths.add(interval.getLength());
        Interval tmp =new Interval(function(interval.getLeft()),function(interval.getRight()));
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

    public void set_m(double m) {
        this.m = m;
    }

    public void setFunction(Expression function) {
        this.function = function;
    }

    public Interval get(int index) {
        return intervals.get(index);
    }

    public void setCharacteristics() {
        characteristics.clear();
        for(int i = 0; i<intervals.size(); i++){
            characteristics.add(i,0.5*m*(intervals.get(i).getRight()-intervals.get(i).getLeft())- //или тут
                    (function(intervals.get(i).getRight())+function(intervals.get(i).getLeft()))/2);
        }
    }

    public void setLengths(){
        lengths.clear();
        for(int i = 0; i<intervals.size(); i++){
            lengths.add(i,intervals.get(i).getLength());
        }
    }

    public ArrayList<Double> getCharacteristics() {
        return characteristics;
    }

    public ArrayList<Double> getLengths() {

        return lengths;
    }



    public void sort() {
        Collections.sort(intervals, new Comparator<Interval>() {
            public int compare(Interval o1, Interval o2) {
                return o1.getLeft().compareTo(o2.getLeft());
            }
        });
    }

    public void print() {
        for (int i = 0; i < intervals.size(); i++){
            System.out.print("Интервал: ["+intervals.get(i).getLeft()+", "+intervals.get(i).getRight()+"] ");
            System.out.println("Характеристика: "+characteristics.get(i));
        }
    }

    public double getMinValue() {
        return minValue;
    }

    public void makeTwoIntervals(int index) {
        double point = 0.5*(intervals.get(index).getRight()+intervals.get(index).getLeft())-
                (function(intervals.get(index).getRight())-function(intervals.get(index).getLeft()))/(2*m);
        //System.out.println(point);
        if(point<intervals.get(index).getLeft()||point>intervals.get(index).getRight()){
            point = intervals.get(index).getMiddle();
        }

        Interval first = new Interval(intervals.get(index).getLeft(), point);

        Interval tmp = new Interval(minValue, function(point));
        minValue = tmp.min();


        Interval second = new Interval(first.getRight(), intervals.get(index).getRight());
        intervals.get(index).setRange(first.getLeft(), first.getRight());
        intervals.add(second);
        sort();
        setCharacteristics();
        setLengths();
    }

    public void makeNewIntervals() {
        int index = characteristics.indexOf(Collections.max(characteristics));
//        for(int i = 0; i<characteristics.size();i++){
//            System.out.println(intervals.get(i).getLeft() + ", " + intervals.get(i).getRight() + ": " + characteristics.get(i));
//        }
//        System.out.println("Index: " + index);
        makeTwoIntervals(index);
    }

    public int size() {
        return intervals.size();
    }
}
