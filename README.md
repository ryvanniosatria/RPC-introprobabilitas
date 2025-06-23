# Java RPC Arithmetic Application

This project demonstrates the implementation of **Remote Procedure Call (RPC)** using Java Sockets to perform basic arithmetic operations (Add, Subtract, Multiply, Divide, Modulo) between a client and a server. It includes both **CLI (Command Line Interface)** and **GUI (Graphical User Interface)** versions of the RPC client-server system.

---

## Features

- Bidirectional communication using TCP sockets
- Arithmetic operations: `add`, `sub`, `mul`, `div`, `mod`
- Client-Server communication model with message parsing
- Real-time interaction with multi-threaded server
- GUI version with Swing (for both client and server)

## echnologies Used

- Java SE
- Java Socket Programming
- Java Swing (for GUI)
- NetBeans / Eclipse / VS Code (for development)

## Project Structure
```vbnet
/rpc-arithmetic-app/
├── CLI/
│ ├── RPCServer.java
│ └── RPCClient.java
├── GUI/
│ ├── RPCServerGUI.java
│ └── RPCClientGUI.java
└── README.md
```
## How It Works

### Server Logic (`RPCServer`)
- Listens on a specified port.
- Accepts multiple client connections using threads.
- Parses client messages like `add:10:20`, performs the operation, and sends back the result.

### Client Logic (`RPCClient`)
- Connects to the server using IP and port.
- Reads user input for the operation and operands.
- Sends formatted message to the server and displays the response.

## GUI Version

### Server GUI (`RPCServerGUI`)
- Start/Stop server button
- Real-time console log in a `JTextArea`
- Handles multiple client connections

### Client GUI (`RPCClientGUI`)
- Input fields for:
  - Server IP
  - Port
  - Operation (dropdown)
  - Two operands
- Displays server response in a log area

## Usage Instructions

### Run CLI Version
1.Compile the server:
```bash
javac RPCServer.java
java RPCServer
```
2.Compile the client:
```bash
javac RPCClient.java
java RPCClient
```

### Run CLI Version
1.Compile and run server GUI:
```bash
javac RPCServerGUI.java
java RPCServerGUI
```
2.Compile and run client GUI:
```bash
javac RPCClientGUI.java
java RPCClientGUI
```

### Conclusion
This Java-based RPC application demonstrates how remote procedure calls can be implemented using raw socket programming. The application shows how to send structured data between machines over a network in both console and graphical environments. With GUI support, the app provides a more user-friendly experience while maintaining the core functionality of client-server arithmetic operations.

