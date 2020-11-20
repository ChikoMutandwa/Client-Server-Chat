package xyzindustries;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.time.*;
import java.time.format.*;
import java.util.logging.*;
import javax.swing.*;

/**
 *
 * @author chiko
 */
public class Server extends JFrame {

    JLabel laServer = new JLabel("Server");
    JLabel laServer2 = new JLabel("");

    JTextField tfSendMessage = new JTextField();
    JTextArea taDisplayMessage = new JTextArea();

    JButton btnSend = new JButton("Send");
    JButton btnExit = new JButton("Exit");

    ServerSocket serveSoc;
    Socket soc;

    DataInputStream input;
    DataOutputStream out;

    LocalDateTime currentTime = LocalDateTime.now();
    DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm:ss");
    String time = currentTime.format(formatTime);

    public Server() {
        super("Server Chat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);

        JPanel panel = new JPanel(new GridLayout(3, 3));

        panel.add(laServer);
        panel.add(laServer2);
        panel.add(tfSendMessage);
        panel.add(btnSend);
        panel.add(taDisplayMessage);
        panel.add(btnExit);

        getContentPane().add(panel);
        setVisible(true);

        btnExit.addActionListener((e) -> {
            try {
                out.close();
                System.exit(0);
            } catch (IOException ex) {
                System.out.println("See you later");
                System.out.println("connection closed");
            }
        });

        btnSend.addActionListener((e) -> {
            try {
                out.writeUTF(tfSendMessage.getText());
                taDisplayMessage.append(time + " Server: " + tfSendMessage.getText() + "\n");
                tfSendMessage.setText("");
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        try {
            serveSoc = new ServerSocket(9000);
            System.out.println("Server is starting....");
            soc = serveSoc.accept();

            input = new DataInputStream(soc.getInputStream());
            out = new DataOutputStream(soc.getOutputStream());

            while (true) {
                taDisplayMessage.append(time + " Client: " + input.readUTF() + "\n");
            }

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }

}
