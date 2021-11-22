package sim.entity;

import sim.states.*;

import javax.swing.text.Document;
import java.util.ArrayList;
import java.util.List;

public class Population {
    private final List<Person> population;
    private final Area area;

    public Population(Area area) {
        this.population = new ArrayList<>();
        this.area=area;
    }

    public List<Person> getPopulation() {
        return population;
    }

    public void addPerson(Person person){
        this.population.add(person);
    }

    public void maintainPopulation(boolean flagImmunity){
        double x;
        double y;
        double randomize = Math.random();
        if(randomize < 0.25){
            x = 0.0;
            y = Math.random() * (area.getHeight() *.01);
        } else if(randomize < .5){
            x = Math.random() * (area.getWidth() *.01);
            y = 0.0;
        }else if (randomize < .75){
            x = area.getWidth() * .01;
            y = Math.random() * (area.getHeight() *.01);
        } else{
            x = Math.random() * (area.getWidth() *.01);
            y = area.getHeight() * .01;
        }
        IState state = new Healthy();
        if(Math.random() < 0.1) {
            if(Math.random() < .5) state = new Symptoms();
            else state = new NoSymptoms();
        }else if(flagImmunity) if(Math.random() > .5) state = new Immune();
        this.addPerson(new Person(state, x, y));
    }

    public void removePerson(Person person){
        this.population.remove(person);
    }

    public void handleReturn(Person person){
        double x = person.getX();
        double y = person.getY();
        if(x > this.area.getWidth() * .01) person.setX(x - .2);
        else person.setX(x + .2);

        if(y > this.area.getHeight() *.01) person.setY(y - .2);
        else person.setY(y + .2);

    }

    public Person generateNewCoordinatesPopulation(){
        for(Person person : population){
            person.generateNewCoordinates();
            if(!this.area.inArea(person)){
                if(Math.random() < 0.5) {
                    return person;
                }
                this.handleReturn(person);
            }
        }
        return null;
    }

    public void handleSickness(){
        List<Person> sickPeople = new ArrayList<>();
        for(Person person : this.population){
            if(person.getState() instanceof ISick) {
                sickPeople.add(person);
            }
        }
        for(Person person: this.population){
            if(person.getState() instanceof Healthy){
                boolean access = false;
                for(Person sickPerson : sickPeople){
                    if(person.getDistance(sickPerson) <= 2.0) {
                        access = true;
                        if(sickPerson.getState() instanceof Symptoms) ((Healthy) person.getState()).setSymptomsPerson(true);
                        person.getState().handle(person);
                        break;
                    }
                }
                if(!access){
                    ((Healthy) person.getState()).setCounter(0);
                }
            }
        }
    }


    public void handleImmunity(){
        for(Person person : this.population){
            if(person.getState() instanceof ISick) {
                person.getState().handle(person);
            }
        }
    }
}
