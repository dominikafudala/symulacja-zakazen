package sim.states;

import sim.entity.Person;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public interface IState {
    void handle(Person person);
}
