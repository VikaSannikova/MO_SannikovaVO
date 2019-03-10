package Algorithms;

import General.Interval;
import net.objecthunter.exp4j.Expression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.pow;
import static java.lang.StrictMath.cos;

public class StronginMethod implements Algorithm{
    ArrayList<Interval> intervals = new ArrayList<Interval>();
    ArrayList<Double> characteristics = new ArrayList<Double>();
    ArrayList<Double> lengths = new ArrayList<Double>();
    double minValue;
    Expression function;

    double M;
    double m;
    double r;

    public double function(double x){
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


    public void setFunction(Expression f){
        this.function = f;
    }

    public void setR(double r) {
        this.r = r;
    }

    public void set_m(double r, double M) {
        if(M>0){
            this.m = r*M;
        }
        if (M==0){
            this.m = 1;
        }
    }

    public void set_M(){
        ArrayList<Double> tmp = new ArrayList<Double>();
        for(int i = 0;i<intervals.size();i++){
            tmp.add(abs(function(intervals.get(i).getRight())-function(intervals.get(i).getLeft()))/(intervals.get(i).getLength()));
        }
        this.M = Collections.max(tmp);
    }

    public int size(){
        return intervals.size();
    }

    public Interval get(int index){
        return intervals.get(index);
    }

    public double getMinValue() {
        return minValue;
    }

    public StronginMethod(double r, Interval interval, Expression expression) {
        setFunction(expression);
        this.intervals.add(interval);
        ArrayList<Double> max = new ArrayList<Double>();
        max.add(abs(function(interval.getRight())-function(interval.getLeft()))/(interval.getRight()-interval.getLeft()));
        this.M = max.get(0);
        setR(r);
        set_m(r, M);
        this.characteristics.add(m*(interval.getRight()-interval.getLeft())+
                pow(function(interval.getRight())-function(interval.getLeft()),2)/(m*(interval.getRight()-interval.getLeft()))-
                2*(function(interval.getRight())+function(interval.getLeft())));
        this.lengths.add(interval.getLength());
        Interval tmp = new Interval(function(interval.getLeft()),function(interval.getRight()));
        minValue = tmp.min();
    }

    public void sort(){
        Collections.sort(intervals, new Comparator<Interval>() {
            public int compare(Interval o1, Interval o2) {
                return o1.getLeft().compareTo(o2.getLeft());
            }
        });
    }

    public void setCharacteristics(){
        characteristics.clear();
        for(int i = 0; i<intervals.size(); i++){
            characteristics.add(m*(intervals.get(i).getRight()-intervals.get(i).getLeft())+
                    pow(function(intervals.get(i).getRight())-function(intervals.get(i).getLeft()),2)/(m*(intervals.get(i).getRight()-intervals.get(i).getLeft()))-
                    2*(function(intervals.get(i).getRight())+function(intervals.get(i).getLeft())));
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

    public void makeTwoIntervals (int index){
        double point = 0.5*(intervals.get(index).getRight()+intervals.get(index).getLeft())-
                (function(intervals.get(index).getRight())-function(intervals.get(index).getLeft()))/(2*m);
        if(point<intervals.get(index).getLeft()||point>intervals.get(index).getRight()){
            point = intervals.get(index).getMiddle();
        }
        Interval first = new Interval(intervals.get(index).getLeft(), point);
        Interval second = new Interval(first.getRight(), intervals.get(index).getRight());

        Interval tmp = new Interval(minValue, function(point));
        minValue = tmp.min();

        intervals.get(index).setRange(first.getLeft(), first.getRight());
        intervals.add(second);
        sort();
        set_M();
        set_m(r,M);
        setCharacteristics();
        setLengths();
    }

    public ArrayList<Double> getCharacteristics() {
        return characteristics;
    }

    public void makeNewIntervals(){
        int index = characteristics.indexOf(Collections.max(characteristics));
        makeTwoIntervals(index);
    }

    public void print(){
        for (int i = 0; i < intervals.size(); i++){
            System.out.print("Интервал: ["+intervals.get(i).getLeft()+", "+intervals.get(i).getRight()+"] ");
            System.out.println("Характеристика: "+characteristics.get(i));
        }

    }
}
