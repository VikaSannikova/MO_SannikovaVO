package General;

import static java.lang.Math.abs;

public class Interval {
    Double left;
    Double right;

    public Interval(Double left, Double right) {
        this.left = left;
        this.right = right;
    }

    public Double getLeft() {
        return left;
    }

    public void setLeft(Double left) {
        this.left = left;
    }

    public Double getRight() {
        return right;
    }

    public void setRight(Double right) {
        this.right = right;
    }

    public void setRange(Double left, Double right){
        this.left = left;
        this.right = right;
    }

    public Double getLength(){
        return abs(this.getRight()-this.getLeft());
    }

    public Double getMiddle(){
        return 0.5*(this.getRight()+this.getLeft());
    }

    public double min(){
        return (this.getLeft() < this.getRight() ? this.getLeft() : this.getRight());
    }
}
