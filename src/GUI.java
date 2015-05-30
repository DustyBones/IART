import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class GUI extends JFrame {
	
	JFileChooser inputChooser, outputChooser;
	private JButton openFileButton, saveFileButton;

    public GUI() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {

        initGUI(this);
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        //FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
        //chooser.setFileFilter(filter);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                GUI ex;
                try {
                    ex = new GUI();
                    ex.setVisible(true);
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
    }

    private void initGUI(final GUI gui) {

    	JPanel panel = new JPanel();

    	setTitle("Simple example"); //SET TITLE
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        inputChooser = new JFileChooser();
    	outputChooser = new JFileChooser();
    	openFileButton = new JButton("Input File/DataSet File");
    	openFileButton.setBounds(127, 101, 167, 23);
    	openFileButton.setVerticalAlignment(SwingConstants.TOP);
    	saveFileButton = new JButton("Output File/Analysis Results");
    	saveFileButton.setBounds(127, 160, 167, 23);

        openFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	int returnVal = inputChooser.showOpenDialog(gui);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                   System.out.println("You chose to open this file: " +
                		   inputChooser.getSelectedFile().getName());
                }
            }
        });

        saveFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	int returnVal = outputChooser.showOpenDialog(gui);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                   System.out.println("You chose to save the results in this file: " +
                		  outputChooser.getSelectedFile().getName());
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

        JTextPane textPane = new JTextPane();
    	textPane.setEnabled(false);
    	textPane.setEditable(false);
    	textPane.setBounds(307, 101, 262, 23);
    	panel.add(textPane);

        JTextPane textPane_1 = new JTextPane();
    	textPane_1.setEnabled(false);
    	textPane_1.setEditable(false);
    	textPane_1.setBounds(310, 160, 259, 23);
    	panel.add(textPane_1);

        JButton btnRunTest = new JButton("Run Test");
    	btnRunTest.setBounds(285, 358, 89, 23);
    	panel.add(btnRunTest);

        JRadioButton rdbtnTrainingFile = new JRadioButton("Training Set");
    	rdbtnTrainingFile.setBounds(127, 275, 109, 23);
    	panel.add(rdbtnTrainingFile);

        JRadioButton rdbtnCrossTest = new JRadioButton("Cross Validation");
    	rdbtnCrossTest.setBounds(285, 275, 134, 23);
    	panel.add(rdbtnCrossTest);

        JRadioButton rdbtnPercentage = new JRadioButton("Percentage Split");
    	rdbtnPercentage.setBounds(448, 275, 121, 23);
    	panel.add(rdbtnPercentage);

        JSpinner spinner = new JSpinner();
    	spinner.setModel(new SpinnerNumberModel(30, 10, 90, 5));
    	spinner.setEnabled(false);
    	spinner.setBounds(587, 305, 39, 23);
    	panel.add(spinner);

        JTextPane txtpnTestingPercentage = new JTextPane();
    	txtpnTestingPercentage.setBackground(SystemColor.controlLtHighlight);
    	txtpnTestingPercentage.setEditable(false);
    	txtpnTestingPercentage.setEnabled(false);
    	txtpnTestingPercentage.setText("Testing Percentage");
    	txtpnTestingPercentage.setBounds(468, 305, 109, 20);
    	panel.add(txtpnTestingPercentage);

    }
}