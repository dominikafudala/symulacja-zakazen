package sim.memento;

import sim.entity.Person;
import sim.entity.Population;

import java.util.ArrayList;
import java.util.List;

public class Memento {
    private List<Person> population;

    public Memento(Population population) {
        this.population = new ArrayList<>(population.getPopulation());
    }

    public List<Person> getPopulation() {
        return population;
    }
}
