package sim.memento;

import sim.entity.Person;
import sim.entity.Population;
import sim.states.*;

import java.util.ArrayList;
import java.util.List;

public class Memento {
    private List<Person> populationList;

    public Memento(Population population) {
        this.populationList = new ArrayList<>();
        for(Person person: population.getPopulation()){
            IState state= person.getState();
            IState newState = new Immune();
            if(state instanceof Healthy) {
                newState = new Healthy();
                ((Healthy) newState).setSymptomsPerson(((Healthy) state).isSymptomsPerson());
                ((Healthy) newState).setCounter(((Healthy) state).getCounter());
            }else if(state instanceof ISick){
                if(state instanceof Symptoms) {
                    newState = new Symptoms();
                } else {
                    newState = new NoSymptoms();
                }
                ((ISick) newState).setCounter(((ISick) state).getCounter());
            }

            Person newPerson = new Person(newState, person.getX(), person.getY());
            populationList.add(newPerson);
        }
    }

    public List<Person> getPopulationList() {
        return populationList;
    }
}
