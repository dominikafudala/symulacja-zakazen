package sim.entity;

import sim.location.IVector;
import sim.location.Vector2D;
import sim.states.IState;


public class Person {
    private IState state;
    private double x;
    private double y;

    public Person(IState state, double x, double y) {
        this.state = state;
        this.x = x;
        this.y = y;
    }

    public IState getState() {
        return state;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setState(IState state) {
        this.state = state;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDistance(Person person){
        IVector vector1 = new Vector2D(this.x, this.y);
        return vector1.abs(person.getX(), person.getY());
    }

    private double getRandomNumber(double min, double max){
        return min + Math.random() * (max - min);
    }


    private IVector generateDisplacementVector(){
        double xa = this.getRandomNumber(-0.1, 0.1);
        double ya = this.getRandomNumber(-0.1, 0.1);
        double newX = this.x + xa;
        double newY = this.y + ya;
        return new Vector2D(newX, newY);
    }

    public void generateNewCoordinates(){
        IVector vector = this.generateDisplacementVector();
        if(vector.abs(this.x, this.y) <= 0.1){
            this.x = vector.getComponents()[0];
            this.y = vector.getComponents()[1];
        }else{
            this.generateNewCoordinates();
        }
    }
}
