package sim.memento;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import sim.entity.Person;
import sim.states.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class XML {
    private int xml_counter = 0;
    public XML() {
    }

    public void saveXML(Memento memento){
        try {
            File f = new File("./state" + xml_counter + ".xml");
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element population = doc.createElement("population");
            doc.appendChild(population);

            for(Person person : memento.getPopulation()) {

                Element personElement = doc.createElement("person");
                population.appendChild(personElement);
                Element x = doc.createElement("x");
                x.appendChild(doc.createTextNode(String.valueOf(person.getX())));
                personElement.appendChild(x);
                Element y = doc.createElement("y");
                y.appendChild(doc.createTextNode(String.valueOf(person.getY())));
                personElement.appendChild(y);
                Element state = doc.createElement("state");
                state.appendChild(doc.createTextNode(person.getState().getClass().getName()));
                personElement.appendChild(state);
                if(person.getState() instanceof ISick || person.getState() instanceof Healthy){
                    Element counter = doc.createElement("counter");
                    if(person.getState() instanceof ISick){
                        int counterInt = ((ISick) person.getState()).getCounter();
                        counter.appendChild(doc.createTextNode(String.valueOf(counterInt)));
                    } else{
                        int counterInt = ((Healthy) person.getState()).getCounter();
                        counter.appendChild(doc.createTextNode(String.valueOf(counterInt)));
                    }
                    state.appendChild(counter);
                }
            }

            StreamResult result = new StreamResult(f);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, result);
        }
        catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();}
    xml_counter++;
    }

    public void fromXML(String path){
        path = "./" + path;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(path);
            doc.getDocumentElement().normalize();
            new XMLParser(doc).createPopulation();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Nie znaleziono pliku xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
