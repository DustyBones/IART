import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

@SuppressWarnings("serial")
public class IartGUI extends JFrame {

    JFileChooser inputChooser, outputChooser;
    int testingMode = 0, percentage = 66;
    boolean focus = true;
    File inputFile, outputFile;
    private JButton openFileButton, saveFileButton;
    private DataSet dataSet;
    private boolean boost;

    public IartGUI() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {

        initGUI(this);
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        //FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
        //chooser.setFileFilter(filter);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                IartGUI ex;
                try {
                    ex = new IartGUI();
                    ex.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initGUI(final IartGUI iartGui) {

        JPanel panel = new JPanel();

        setTitle("Music Data Location"); //SET TITLE
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        inputChooser = new JFileChooser();
        outputChooser = new JFileChooser();
        openFileButton = new JButton("Input File/DataSet File");
        openFileButton.setBounds(127, 101, 167, 23);
        openFileButton.setVerticalAlignment(SwingConstants.TOP);
        openFileButton.setFocusable(false);
        FileFilter filter = new FileNameExtensionFilter("CSV File", "csv", "txt");
        inputChooser.addChoosableFileFilter(filter);
        inputChooser.setFileFilter(filter);
        outputChooser.addChoosableFileFilter(filter);
        outputChooser.setFileFilter(filter);
        saveFileButton = new JButton("Output File/Analysis Results");
        saveFileButton.setBounds(127, 160, 167, 23);
        saveFileButton.setFocusable(false);

        final JTextPane textPane = new JTextPane();
        textPane.setEnabled(false);
        textPane.setEditable(false);
        textPane.setBounds(307, 101, 262, 23);
        panel.add(textPane);

        final JTextPane textPane_1 = new JTextPane();
        textPane_1.setEnabled(false);
        textPane_1.setEditable(false);
        textPane_1.setBounds(310, 160, 259, 23);
        panel.add(textPane_1);

        openFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                int returnVal = inputChooser.showOpenDialog(iartGui);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    inputFile = inputChooser.getSelectedFile();
                    textPane.setText(inputFile.getAbsolutePath());
                    textPane.setEnabled(true);
                }
            }
        });

        saveFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                int returnVal = outputChooser.showSaveDialog(iartGui);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    outputFile = outputChooser.getSelectedFile();
                    textPane_1.setText(outputFile.getAbsolutePath());
                    textPane_1.setEnabled(true);
                }
            }
        });

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(openFileButton);
        buttonGroup.add(saveFileButton);
        panel.setLayout(null);

        panel.add(openFileButton);
        panel.add(saveFileButton);

        getContentPane().add(panel);

        JButton btnRunTest = new JButton("Run Test");
        btnRunTest.setBounds(285, 358, 89, 23);
        panel.add(btnRunTest);

        final JRadioButton rdbtnTrainingFile = new JRadioButton("Training Set");
        rdbtnTrainingFile.setBounds(127, 275, 109, 23);
        panel.add(rdbtnTrainingFile);

        final JRadioButton rdbtnCrossTest = new JRadioButton("Cross Validation");
        rdbtnCrossTest.setBounds(285, 275, 134, 23);
        panel.add(rdbtnCrossTest);

        final JRadioButton rdbtnPercentage = new JRadioButton("Percentage Split");
        rdbtnPercentage.setBounds(448, 275, 121, 23);
        panel.add(rdbtnPercentage);

        ButtonGroup radioBtnGroup = new ButtonGroup();
        radioBtnGroup.add(rdbtnTrainingFile);
        radioBtnGroup.add(rdbtnCrossTest);
        radioBtnGroup.add(rdbtnPercentage);

        final JSpinner percentSpinner = new JSpinner();
        percentSpinner.setModel(new SpinnerNumberModel(66, 10, 90, 1));
        percentSpinner.setEnabled(false);
        percentSpinner.setBounds(587, 305, 39, 23);
        panel.add(percentSpinner);

        final JTextPane txtpnTestingPercentage = new JTextPane();
        txtpnTestingPercentage.setBackground(SystemColor.controlLtHighlight);
        txtpnTestingPercentage.setEditable(false);
        txtpnTestingPercentage.setEnabled(false);
        txtpnTestingPercentage.setText("Train Percentage");
        txtpnTestingPercentage.setBounds(468, 305, 109, 20);
        panel.add(txtpnTestingPercentage);

        rdbtnPercentage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                percentSpinner.setEnabled(true);
                percentSpinner.setFocusable(true);
                txtpnTestingPercentage.setEnabled(true);
                testingMode = 2;
            }
        });

        percentSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                percentage = (int) percentSpinner.getValue();
            }
        });

        rdbtnCrossTest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                percentSpinner.setEnabled(false);
                percentSpinner.setFocusable(false);
                txtpnTestingPercentage.setEnabled(false);
                testingMode = 1;
            }
        });

        rdbtnTrainingFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                percentSpinner.setEnabled(false);
                percentSpinner.setFocusable(false);
                txtpnTestingPercentage.setEnabled(false);
                testingMode = 0;
            }
        });

        btnRunTest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    dataSet = new DataSet(inputFile, outputFile, testingMode, percentage, true);
                    dataSet.loadData();
                    dataSet.test();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}