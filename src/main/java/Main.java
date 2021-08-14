import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class Main {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";

        List<Employee> list = parseCSV(columnMapping, fileName);

        String json = listToJson(list);

        writeString(json, "data.json");

        List<Employee> listFromXML = parseXML("data.xml");

        String json2 = listToJson(listFromXML);

        writeString(json2, "data2.json");
    }

    public static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        List<Employee> stuff = null;

        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);
            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(reader)
                    .withMappingStrategy(strategy)
                    .build();
            stuff = csv.parse();
            stuff.forEach(System.out :: println);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return stuff;


    }

    public static String listToJson(List list) {

        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        GsonBuilder rustem = new GsonBuilder();
        Gson gson = rustem.create();
        String json = gson.toJson(list, listType);
        return json;
    }

    public static void writeString(String json, String jsonFile) {

        try (FileWriter file = new FileWriter(jsonFile)) {
            file.write(json);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Employee> parseXML(String xmlFilename) throws ParserConfigurationException, IOException, SAXException {

        List<Employee> stuff1 = null;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(xmlFilename));

        NodeList employeeElements = (document).getDocumentElement().getElementsByTagName("employee");

        for (int i = 0; i < employeeElements.getLength(); i++) {
            Node employee = employeeElements.item(i);
            NamedNodeMap attributes = employee.getAttributes();

            stuff1.add(new Employee(Long.parseLong(attributes.getNamedItem("id").getNodeValue()), attributes.getNamedItem("firstName").getNodeValue(), attributes.getNamedItem("lastName").getNodeValue(), attributes.getNamedItem("country").getNodeValue(), Integer.parseInt(attributes.getNamedItem("age").getNodeValue())));
            System.out.println(stuff1);
        }

        return stuff1;
    }
}
