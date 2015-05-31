import weka.classifiers.Evaluation;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class DataSet {
    private String inputFile;
    private Instances data;
    private ArrayList<String> areaDB;
    private J48 tree;
    private Evaluation eval;

    public DataSet(String inputFile) throws Exception {
        this.inputFile = inputFile;

        areaDB = new ArrayList<>();
        areaDB.add("West Europe");
        areaDB.add("East Europe");
        areaDB.add("North America");
        areaDB.add("Central America");
        areaDB.add("South America");
        areaDB.add("North/West Africa");
        areaDB.add("East Africa");
        areaDB.add("West Asia");
        areaDB.add("SouthEast Asia");
        areaDB.add("Oceania");
    }

    public String findArea(String lat, String lon) {
        float latF, lonF;
        latF = Float.parseFloat(lat);
        lonF = Float.parseFloat(lon);
        if (latF == -15.75f && lonF == -47.95f) {
            return areaDB.get(4);
        } else if (latF == 14.91f && lonF == -23.51f) {
            return areaDB.get(5);
        } else if (latF == 12.65f && lonF == -8.0f) {
            return areaDB.get(5);
        } else if (latF == 9.03f && lonF == 38.74f) {
            return areaDB.get(6);
        } else if (latF == 34.03f && lonF == -6.85f) {
            return areaDB.get(5);
        } else if (latF == 14.66f && lonF == -17.41f) {
            return areaDB.get(5);
        } else if (latF == 52.5f && lonF == -0.12f) {
            return areaDB.get(0);
        } else if (latF == 41.26f && lonF == 69.21f) {
            return areaDB.get(7);
        } else if (latF == 41.9f && lonF == 12.48f) {
            return areaDB.get(0);
        } else if (latF == 28.61f && lonF == 77.2f) {
            return areaDB.get(8);
        } else if (latF == 33.66f && lonF == 73.16f) {
            return areaDB.get(7);
        } else if (latF == 54.68f && lonF == 25.31f) {
            return areaDB.get(1);
        } else if (latF == 44.41f && lonF == 26.1f) {
            return areaDB.get(1);
        } else if (latF == 36.7f && lonF == 3.21f) {
            return areaDB.get(5);
        } else if (latF == 39.91f && lonF == 32.83f) {
            return areaDB.get(1);
        } else if (latF == 19.75f && lonF == 96.1f) {
            return areaDB.get(8);
        } else if (latF == 13.75f && lonF == 100.48f) {
            return areaDB.get(8);
        } else if (latF == 39.91f && lonF == 116.38f) {
            return areaDB.get(8);
        } else if (latF == 23.76f && lonF == 121.0f) {
            return areaDB.get(8);
        } else if (latF == -6.17f && lonF == 106.82f) {
            return areaDB.get(8);
        } else if (latF == 17.98f && lonF == -76.8f) {
            return areaDB.get(3);
        } else if (latF == 35.68f && lonF == 51.41f) {
            return areaDB.get(7);
        } else if (latF == 30.03f && lonF == 31.21f) {
            return areaDB.get(6);
        } else if (latF == 42.86f && lonF == 74.6f) {
            return areaDB.get(7);
        } else if (latF == -1.26f && lonF == 36.8f) {
            return areaDB.get(6);
        } else if (latF == 17.25f && lonF == -88.76f) {
            return areaDB.get(3);
        } else if (latF == 38.0f && lonF == 23.71f) {
            return areaDB.get(1);
        } else if (latF == -35.3f && lonF == 149.12f) {
            return areaDB.get(9);
        } else if (latF == 35.7f && lonF == 139.71f) {
            return areaDB.get(8);
        } else if (latF == -6.17f && lonF == 35.74f) {
            return areaDB.get(6);
        } else if (latF == 41.71f && lonF == 44.78f) {
            return areaDB.get(1);
        } else if (latF == 11.55f && lonF == 104.91f) {
            return areaDB.get(8);
        } else if (latF == 41.33f && lonF == 19.8f) {
            return areaDB.get(1);
        }
        System.out.println(lat + " " + lon + "NOT FOUND!");
        return "NOT_FOUND";
    }

    public void loadData() throws Exception {
        ArrayList atts = new ArrayList();
        for (int i = 0; i < 68; i++) {
            atts.add(new Attribute("Attribute_" + (i + 1)));
        }
        atts.add(new Attribute("Location", areaDB));
        data = new Instances("Training Data Set", atts, 0);
        data.setClassIndex(68);

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String line;
        String[] attributes;
        double[] val;
        while ((line = reader.readLine()) != null) {
            attributes = line.split(",");
            val = new double[data.numAttributes()];
            for (int i = 0; i < data.numAttributes(); i++) {
                val[i] = Double.parseDouble(attributes[i]);
            }
            val[68] = data.attribute(68).indexOfValue(findArea(attributes[68], attributes[69]));
            data.add(new DenseInstance(1.0, val));
        }

        //System.out.println(data);
        //System.out.println("SetUp done.");
    }

    public void test() throws Exception {
        tree = new J48();
        String[] options = new String[4];
        options[0] = "-C";
        options[1] = "0.2";
        options[2] = "-M";
        options[3] = "2";
        tree.setOptions(options);
        tree.buildClassifier(data);

        AdaBoostM1 boost = new AdaBoostM1();
        boost.setClassifier(tree);

        eval = new Evaluation(data);
        eval.crossValidateModel(boost, data, 10, new Random(123));

        System.out.println(eval.pctCorrect());

        TreeVisualizer tv = new TreeVisualizer(null, tree.graph(), new PlaceNode2());
        JFrame jf = new JFrame("Weka Classifier Tree Visualizer: J48");
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setSize(800, 600);
        jf.getContentPane().setLayout(new BorderLayout());
        jf.getContentPane().add(tv, BorderLayout.CENTER);
        jf.setVisible(true);
// adjust tree
        tv.fitToScreen();

        //System.out.println(eval.toSummaryString("Results: \n\n", false));
    }
}
