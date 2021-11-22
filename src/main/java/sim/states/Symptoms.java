package sim.states;

import sim.entity.Person;

public class Symptoms implements ISick{
    private int counter = 0;
    @Override
    public void handle(Person person) {
        if(this.counter >= 500){
            if(Math.random() > .5 || this.counter >=750) person.setState(new Immune());
        }
        this.counter++;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
