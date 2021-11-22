package sim.entity;

import sim.memento.Caretaker;
import sim.memento.XML;
import sim.states.Healthy;
import sim.states.IState;
import sim.states.Immune;

public class Simulation {
    private int n;
    private int m;
    private int startPopulationNum;
    private final DrawWindow window;
    private final Area area;
    private static Population population;
    private final static Caretaker caretaker = new Caretaker();
    private final static XML xml = new XML();

    public Simulation(int n, int m, int startPopulationNum) {
        this.n = n;
        this.m = m;
        this.startPopulationNum = startPopulationNum;
        this.window = new DrawWindow(n , m);
        this.area = new Area(m, n);
    }

    public Simulation(int n, int m, String path){
        this.n = n;
        this.m = m;
        this.window = new DrawWindow(n , m);
        this.area = new Area(m, n);
        this.population = new Population(area);
        new XML().fromXML(path);

    }

    public static Population getPopulation() {
        return population;
    }

    public void startSimulation(boolean flagImmunity){

        if(population == null) {
            population = new Population(area);
            for (int i = 0; i < startPopulationNum; i++) {
                double x = Math.random() * (m * .01);
                double y = Math.random() * (n * .01);
                IState state = new Healthy();
                if (flagImmunity == true) {
                    if (Math.random() > .5) state = new Immune();
                }
                population.addPerson(new Person(state, x, y));
            }
        }

        while(true) {
            window.displayPopulation(population);
            try {
                Thread.sleep( 40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Person personToRemove = population.generateNewCoordinatesPopulation();
            if(personToRemove != null) {
                population.removePerson(personToRemove);
                population.maintainPopulation(flagImmunity);
            }

            population.handleImmunity();
            population.handleSickness();

        }
    }

    public static void saveSimulation(){
        caretaker.addMemento(population);
        xml.saveXML(caretaker.getMemento(caretaker.getMementos().size() - 1));
    }
}
