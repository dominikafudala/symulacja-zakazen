package sim.memento;

import sim.entity.Population;

import java.util.ArrayList;
import java.util.List;

public class Caretaker {
    private List<Memento> mementos = new ArrayList<>();
    public Caretaker() {
    }

    public List<Memento> getMementos() {
        return mementos;
    }

    public void addMemento(Population population){
        mementos.add(new Memento(population));
    }

    public Memento getMemento(int index){
        return mementos.get(index);
    }
}
