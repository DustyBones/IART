import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class DataSet {
    private String inputFile;
    private Instances data;
    private ArrayList<String[]> geoDB;
    private J48 tree;

    public DataSet(String inputFile) throws Exception {
        this.inputFile = inputFile;
        geoDB = new ArrayList<>();
        loadGeoDB();
    }

    public static boolean canParseFloat(String s) {
        try {
            Float.parseFloat(s);
            return true;
        } catch (Exception ignore) {
        }
        return false;
    }

    public void loadGeoDB() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("cities.txt"));
        String line;
        String[] tokens;
        String[] entry;
        while ((line = reader.readLine()) != null) {
            tokens = line.split("\\t");
            for (int i = 1; i < tokens.length; i++) {
                if (canParseFloat(tokens[i])) {
                    entry = new String[3];
                    entry[0] = tokens[i];
                    entry[1] = tokens[i + 1];
                    entry[2] = tokens[i + 4];
                    geoDB.add(entry);
                    break;
                }
            }
        }
    }

    public String findCity(String lat, String lon) {
        float lat_1, lat_2, lon_1, lon_2;
        lat_2 = Float.parseFloat(lat);
        lon_2 = Float.parseFloat(lon);
        for (int i = 0; i < geoDB.size(); i++) {
            lat_1 = Float.parseFloat(geoDB.get(i)[0]);
            lon_1 = Float.parseFloat(geoDB.get(i)[1]);
            if (lat_1 - 1f <= lat_2 && lat_1 + 1f >= lat_2 && lon_1 - 1f <= lon_2 && lon_1 + 1f >= lon_2) {
                return geoDB.get(i)[2];
            }
        }
        return "NOT_FOUND";
    }

    public void setUp() throws Exception {
        ArrayList atts = new ArrayList();
        for (int i = 0; i < 68; i++) {
            atts.add(new Attribute("Attribute_" + (i + 1)));
        }
        atts.add(new Attribute("Location", (List<String>) null));
        data = new Instances("Training Data Set", atts, 0);

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String line;
        String[] attributes;
        double[] val;
        while ((line = reader.readLine()) != null) {
            attributes = line.split(",");
            val = new double[data.numAttributes()];
            for (int i = 0; i < 68; i++) {
                val[i] = Double.parseDouble(attributes[i]);
            }
            val[68] = data.attribute(68).addStringValue(findCity(attributes[68], attributes[69]));
            data.add(new DenseInstance(1.0, val));
        }

        //   System.out.println(data);
        System.out.println("SetUp done.");
    }
}
