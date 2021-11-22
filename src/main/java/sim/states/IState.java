package sim.states;

import sim.entity.Person;

public interface IState {
    void handle(Person person);
}
