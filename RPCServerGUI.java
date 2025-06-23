import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class RPCServerGUI {
    private final ServerSocket serverSocket;
    private final JTextArea textArea;
    private JButton startButton;
    private List<Socket> clientSockets = new ArrayList<>(); // Declare and initialize the clientSockets variable here

    public RPCServerGUI(int port) throws IOException {
        // List<Socket> clientSockets = new ArrayList<>();

        serverSocket = new ServerSocket(port);
        textArea = new JTextArea();
        startButton = new JButton("Start Server");
        JButton stopButton = new JButton("Stop Server"); // Declare the stopButton variable

        JFrame frame = new JFrame("RPC Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(); // Add this line to create the buttonPanel
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton); // Use the stopButton variable

        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                textArea.append("Server is running on 10.10.10.1:5000\n");
                new Thread(() -> {
                    while (!serverSocket.isClosed()) {
                        try {
                            Socket rpcClient = serverSocket.accept();
                            String address = rpcClient.getRemoteSocketAddress().toString();
                            textArea.append("New client connected : " + address + "\n");
                            new Thread(() -> {
                                try {
                                    addHook(rpcClient);
                                } catch (IOException ex) {
                                    textArea.append("Client disconnected " + address + "\n");
                                }
                            }).start();
                        } catch (IOException ex) {
                            if (!serverSocket.isClosed()) {
                                textArea.append("Error accepting client connection\n");
                            }
                        }
                    }
                }).start();
            }
        });

        // JButton stopButton = new JButton("Stop Server");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopButton.setEnabled(false);
                startButton.setEnabled(true);
                try {
                    for (Socket clientSocket : clientSockets) {
                        if (!clientSocket.isClosed()) {
                            clientSocket.close();
                        }
                    }
                    serverSocket.close();
                    textArea.append("Server stopped\n");
                } catch (IOException ex) {
                    textArea.append("Error stopping server\n");
                }
            }
        });
    }
    private void addHook(Socket rpcClient) throws IOException {
        // List<Socket> clientSockets = new ArrayList<>(); // Declare and initialize the clientSockets variable
        clientSockets.add(rpcClient);
        BufferedReader reader = new BufferedReader(new InputStreamReader(rpcClient.getInputStream()));
        String line;
        // continue your code here
        while ((line = reader.readLine()) != null) { 
            if ("exit".equals(line)) {
                break;
            }
            System.out.println("Client request : " + line);
            String[] commands = line.split(":", 3);
            int result;
            int operand1 = Integer.parseInt(commands[1]);
            int operand2 = Integer.parseInt(commands[2]);
            String message = "";

            switch (commands[0]) {
                case "add":
                    result = (operand1 + operand2);
                    message = operand1 + " + " + operand2 + " = " + result;
                    break;
                case "sub":
                    result = (operand1 - operand2);
                    message = operand1 + " - " + operand2 + " = " + result;
                    break;
                case "mul":
                    result = (operand1 * operand2);
                    message = operand1 + " * " + operand2 + " = " + result;
                    break;
                case "div":
                    result = (operand1 / operand2);
                    message = operand1 + " / " + operand2 + " = " + result;
                    break;
                case "mod":
                    result = (operand1 % operand2);
                    message = operand1 + " % " + operand2 + " = " + result;
                    break;
                default:
                    message = "Invalid operation";
                    break;
            }
            PrintStream printStream = new PrintStream(rpcClient.getOutputStream(), true);
            printStream.println(message);
            textArea.append("Client request : " + line + "\n");
            textArea.append("Response : " + message + "\n");
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new RPCServerGUI(5000);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}