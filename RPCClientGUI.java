import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class RPCClientGUI {
    private JFrame frame;
    private JTextField ipField;
    private JTextField portField;
    private JTextField num1Field;
    private JTextField num2Field;
    private JTextArea responseArea;
    private JComboBox<String> commandBox;
    private Socket rpcClient;
    private PrintWriter writer;

    public RPCClientGUI() {
        frame = new JFrame("RPC Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 430);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel ipLabel = new JLabel("Server IP:");
        ipLabel.setBounds(10, 20, 80, 25);
        panel.add(ipLabel);

        ipField = new JTextField(20);
        ipField.setBounds(100, 20, 370, 25); // Adjust the size and position of the components
        panel.add(ipField);

        JLabel portLabel = new JLabel("Port:");
        portLabel.setBounds(10, 50, 80, 25);
        panel.add(portLabel);

        portField = new JTextField(20);
        portField.setBounds(100, 50, 370, 25);
        panel.add(portField);

        JButton connectButton = new JButton("Connect");
        connectButton.setBounds(100, 80, 370, 25);
        panel.add(connectButton);

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectToServer();
            }
        });

        // Add more components for commands and numbers here

        JLabel commandLabel = new JLabel("Command:");
        commandLabel.setBounds(10, 130, 80, 25);
        panel.add(commandLabel);

        String[] commands = {"add", "sub", "mul", "div", "mod"};
        commandBox = new JComboBox<>(commands);
        commandBox.setBounds(100, 130, 100, 25);
        panel.add(commandBox);

        JLabel num1Label = new JLabel("Number 1:");
        num1Label.setBounds(220, 130, 80, 25);
        panel.add(num1Label);

        num1Field = new JTextField(20);
        num1Field.setBounds(300, 130, 60, 25);
        panel.add(num1Field);

        JLabel num2Label = new JLabel("Number 2:");
        num2Label.setBounds(220, 160, 80, 25);
        panel.add(num2Label);

        num2Field = new JTextField(20);
        num2Field.setBounds(300, 160, 60, 25);
        panel.add(num2Field);

        responseArea = new JTextArea();
        responseArea.setBounds(10, 200, 460, 150); // Adjust the size of the responseArea
        responseArea.setEditable(false);
        panel.add(responseArea);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(10, 360, 460, 20); // Set the coordinates and size of the exit button
        panel.add(exitButton);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JButton sendButton = new JButton("Send");
        sendButton.setBounds(370, 130, 100, 55); // Adjust the size and position of the sendButton
        panel.add(sendButton);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendCommand();
            }
            private void sendCommand() {
                String command = (String) commandBox.getSelectedItem();
                String num1 = num1Field.getText();
                String num2 = num2Field.getText();
    
                writer.println(command + ":" + num1 + ":" + num2);
    
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(rpcClient.getInputStream()));
                    String response = reader.readLine();
                    responseArea.append("Server response: " + response + "\n");
                } catch (IOException e) {
                    responseArea.append("Error reading response from server\n");
                }
            }
        });

    }

    private void connectToServer() {
        String serverIp = ipField.getText();
        int port = Integer.parseInt(portField.getText());

        try {
            rpcClient = new Socket(serverIp, port);
            writer = new PrintWriter(rpcClient.getOutputStream(), true);
            responseArea.append("Connected to server\n");
        } catch (IOException e) {
            responseArea.append("Error connecting to server\n");
        }
    }

    public static void main(String[] args) {
        new RPCClientGUI();
    }
}