import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

public class Main extends JFrame {
    String[] driverURL = { "com.mysql.jdbc.Driver", "" };
    String[] databaseURL = { "jdbc:mysql://localhost:3306/project3", "" };
    public boolean areWeConnectedToTheDatabase = false;   // Connection value
    private Connection connection;
    // initially not connected to a database
    private boolean databaseConnection = false;

    private JLabel Top = new JLabel("Enter database information");

    private JLabel filler = new JLabel(" ");
    private JLabel Driver = new JLabel("JDBC Driver");
    private JLabel DataBase = new JLabel("Database URL");
    private JLabel Username = new JLabel("Username");
    private JLabel Password = new JLabel("Password");
    private JLabel ConnectionStatus = new JLabel("No Connection Now");
    private JLabel SQLCommand = new JLabel("Enter a SQL Command");

    // Drop down arrows
    private JComboBox driver_URL = new JComboBox(driverURL);
    private JComboBox database_URL = new JComboBox(databaseURL);

    private JTextField usernameField = new JTextField();
    private JPasswordField passwordField = new JPasswordField();
    private JButton Connect = new JButton("Connect to Database");
    private JLabel Connected = new JLabel("Not Connected");
    private JButton Clear = new JButton("Clear SQL Command");
    private JButton Execute = new JButton("Execute SQL Command");
    private JButton ClearResult = new JButton("Clear Result Window");

    private JLabel executionResultWindow = new JLabel("SQL Execution Result Window");
    private JTextArea commandLine = new JTextArea();
    private JTable executionOutput = new JTable();


    public void checkifDataBaseIsConnected() throws SQLException {
        if (areWeConnectedToTheDatabase){
            connection.close();
            Connected.setForeground(Color.RED);
            Connected.setText("Not Connected");
        }
        areWeConnectedToTheDatabase = false;
    }



    public Main() throws FileNotFoundException {
        JPanel labelsAndInput = new JPanel(new GridLayout(4, 2));

        // North
        labelsAndInput.add(Driver);
        Driver.setOpaque(true);
        Driver.setBackground(Color.lightGray);
        labelsAndInput.add(driver_URL);
        labelsAndInput.add(DataBase);
        DataBase.setOpaque(true);
        DataBase.setBackground(Color.lightGray);
        labelsAndInput.add(database_URL);
        labelsAndInput.add(Username);
        Username.setOpaque(true);
        Username.setBackground(Color.lightGray);
        labelsAndInput.add(usernameField);
        labelsAndInput.add(Password);
        Password.setOpaque(true);
        Password.setBackground(Color.lightGray);
        labelsAndInput.add(passwordField);
        SQLCommand.setPreferredSize(new Dimension(10,10));
//        commandWindow.add(SQLCommand, FlowLayout.LEFT);
//        commandWindow.add(commandLine);

        JPanel north = new JPanel(new GridLayout(1, 2));
        north.add(labelsAndInput);
        commandLine.setBackground(Color.lightGray);
        north.add(commandLine);

        JPanel center = new JPanel(new GridLayout(1, 4));
        center.add(Connected);
        Connected.setForeground(Color.RED);
        Connected.setOpaque(true);
        Connected.setBackground(Color.BLACK);

        center.add(Connect);
        Connect.setForeground(Color.yellow);
        Connect.setOpaque(true);
        Connect.setBackground(Color.BLUE);
        center.add(Clear);
        Clear.setForeground(Color.RED);
        Clear.setOpaque(true);
        Clear.setBackground(Color.WHITE);
        Clear.setEnabled(false);
        center.add(Execute);
        Execute.setForeground(Color.BLACK);
        Execute.setOpaque(true);
        Execute.setBackground(Color.GREEN);
        Execute.setEnabled(false);


        JPanel south = new JPanel(new BorderLayout(20,0));
        executionResultWindow.setForeground(Color.BLUE);
        south.add(executionResultWindow, BorderLayout.NORTH);
        south.add(new JScrollPane(executionOutput), BorderLayout.CENTER);
        ClearResult.setBackground(Color.YELLOW);
        south.add(ClearResult, BorderLayout.SOUTH, FlowLayout.LEFT);

        add(north, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);



        // Connects to the database
        Connect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String userName= usernameField.getText();
                String password = passwordField.getText();


                System.out.println("Username: " + userName + "\nPassword: " + password);



                try {
                    checkifDataBaseIsConnected();
                    connection = DriverManager.getConnection(String.valueOf(database_URL.getSelectedItem()), userName, password);
                    Connected.setForeground(Color.GREEN);
                    Connected.setText("Connected to " + database_URL.getSelectedItem());
                    commandLine.setText(" ");
                    Execute.setEnabled(true);
                    Clear.setEnabled(true);
                } catch (SQLException throwables) {
                    commandLine.setText("Username or Password Incorrect\nTry Again");
                    Connected.setForeground(Color.RED);
                    Connected.setText("Not Connected");
                }


            }
//
        });
        // Clears the output
        ClearResult.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executionOutput.setModel(new DefaultTableModel());
//                tableModel = null;
            }
        });
        // Processes Input and executes it in the database
        Execute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = commandLine.getText();
                System.out.println(s);

            }
//
        });
        // Clears the command line
        Clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commandLine.setText(" ");
            }
        });



    }

    public static void main(String[] args) throws FileNotFoundException {
        // Set up the frames
        Main frame = new Main();

        frame.pack(); // fit windows for screen
        frame.setLayout(new BorderLayout(2,0));
        frame.setTitle("SQL Client App - (MJL - CNT 4714 - Spring 2021");
        frame.setLocationRelativeTo(null); // center windows
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // close window
        frame.setVisible(true); // display window
    }
}