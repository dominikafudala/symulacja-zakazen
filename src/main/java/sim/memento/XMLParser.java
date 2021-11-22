package sim.memento;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import sim.entity.Person;
import sim.entity.Population;
import sim.entity.Simulation;
import sim.states.*;

public class XMLParser {
    private Document data;

    public XMLParser(Document data) {
        this.data = data;
    }
    public void createPopulation() throws Exception {
        if (data == null) {
            throw new Exception("Data is null");
        }
        // get all <person>
        NodeList entries = data.getElementsByTagName("person");
        if(entries.getLength() == 0){
            throw new Exception("No tag <person>, can't create Population");
        }

        Population population = Simulation.getPopulation();

        for(int i = 0; i < entries.getLength(); i++){
            Node entry = entries.item(i);
            Element element = (Element) entry;
            try{
                double x = Double.parseDouble(element.getElementsByTagName("x").item(0).getTextContent());
                double y = Double.parseDouble(element.getElementsByTagName("y").item(0).getTextContent());
                String state = element.getElementsByTagName("state").item(0).getTextContent();
                IState statePerson = new Immune();
                if(state.contains("Symptoms")){
                    statePerson = new Symptoms();
                    int counter = Integer.parseInt(element.getElementsByTagName("counter").item(0).getTextContent());
                    ((Symptoms) statePerson).setCounter(counter);
                } else if (state.contains("NoSymptoms")){
                    statePerson = new NoSymptoms();
                    int counter = Integer.parseInt(element.getElementsByTagName("counter").item(0).getTextContent());
                    ((NoSymptoms) statePerson).setCounter(counter);
                } else if (state.contains("Healthy")){
                    statePerson = new Healthy();
                    int counter = Integer.parseInt(element.getElementsByTagName("counter").item(0).getTextContent());
                    ((Healthy) statePerson).setCounter(counter);
                }

                Person person = new Person(statePerson, x, y);
                population.addPerson(person);
            }catch(NullPointerException e){
                System.out.println("Dodawanie osoby nie powiodło się");
            }
        }
    }

}
