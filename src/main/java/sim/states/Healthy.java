package sim.states;

import sim.entity.Person;

public class Healthy implements INotImmune{
    private int counter = 0;
    private boolean symptomsPerson = false;
    @Override
    public void handle(Person person) {
        if(this.getCounter() >= 75) {
            if (this.symptomsPerson) {
                this.randomizeState(person);
            }
            else{
                if (Math.random() > .5) {
                    this.randomizeState(person);
                }
            }
        }
        this.counter++;
        this.setSymptomsPerson(false);
    }

    private void randomizeState(Person person){
        if (Math.random() > .5) person.setState(new Symptoms());
        else person.setState(new NoSymptoms());
    }

    public void setSymptomsPerson(boolean symptomsPerson) {
        this.symptomsPerson = symptomsPerson;
    }

    public boolean isSymptomsPerson() {
        return symptomsPerson;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getCounter() {
        return counter;
    }
}
