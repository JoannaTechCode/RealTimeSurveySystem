/****************************************************************
 PROGRAM:   program name
 AUTHOR:    your name
 LOGON ID:  Z??????  (your Z number here)
 DUE DATE:  due date of program

 FUNCTION:  a short paragraph stating the purpose of the
 program.

 INPUT:     location of input, i.e.  standard input, a file on
 disk

 OUTPUT:    location and type of output, i.e.  a report
 containing a detail record for each city processed
 containing city id, Celsius temperature, Fahrenheit
 temperature and wind chill temperature.

 NOTES:     any relevant information that would be of
 additional help to someone looking at the program.
 ****************************************************************/

package ClassDemos;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * The code in this file create and locate components to the WorkerForm (worker's form).
 * Connect WorkerForm to the network, also implemented fuctions that WorkerForm receives, sends data, and disconnects from the network.
 *  @author Qian Xie(Joanna)
 *  @version 25 October,2022
 */
public class WorkerForm extends JFrame implements ActionListener {
    //CHAT RELATED ---------------------------
    private Socket socket = null;
    private DataInputStream console = null;
    private DataOutputStream streamOut = null;
    private ChatClientThread2 client2 = null;
    private String serverName = "localhost";
    private int serverPort = 4444;


    SpringLayout myLayout=new SpringLayout();
    JLabel lbTitle, lbInstruction, lbTopic, lbQuestion, lb1,lb2,lb3,lb4,lb5,lbYourAnswer,lblMessage;
    JPanel pnDisplay;
    JTextField txtTopic, txt1,txt2,txt3,txt4,txt5,txtAnswer;
    JTextArea txtQuestion;
    JButton btnSubmit, btnConnect, btnExit;

    /**
     *Program entry. Instantiate WorkForm
     * @param args
     */
    public static void main(String[] args)
    {
        new WorkerForm();


    }

    /**
     *Initialise, instance and locate a WorkerForm and swing components.
     *
     */
    public WorkerForm(){



        setSize(500, 600);
        setLocationRelativeTo(null);
        setLayout(myLayout);



        lbTitle = UIComponentBuilder.createALabel("Survey Questions", 0, 0, 500, 25, myLayout, this);
        lbTitle.setHorizontalAlignment(0);
        lbTitle.setFont(new Font("Arial", Font.PLAIN, 20));
        lbInstruction =UIComponentBuilder.createALabel("Enter your answer and click Submit",8,50,472,25,myLayout,this);
        lbTopic =UIComponentBuilder.createALabel("Topic:",8,76,80,25,myLayout,this);
        lbQuestion =UIComponentBuilder.createALabel("Question:",8,107,80,25,myLayout,this);
        lb1 =UIComponentBuilder.createALabel("1:",8,183,80,25,myLayout,this);
        lb2 =UIComponentBuilder.createALabel("2:",8,213,80,25,myLayout,this);
        lb3 =UIComponentBuilder.createALabel("3:",8,243,80,25,myLayout,this);
        lb4 =UIComponentBuilder.createALabel("4:",8,273,80,25,myLayout,this);
        lb5 =UIComponentBuilder.createALabel("5",8,303,80,25,myLayout,this);
        lbYourAnswer =UIComponentBuilder.createALabel("Your Answer:",8,383,110,25,myLayout,this);
        txtTopic=UIComponentBuilder.createATextField(100,76,myLayout,this,381,25);
        txtQuestion=UIComponentBuilder.createATextArea(100,107,myLayout,this,381,70);
        txt1=UIComponentBuilder.createATextField(100,183,myLayout,this,381,25);
        txt2=UIComponentBuilder.createATextField(100,213,myLayout,this,381,25);
        txt3=UIComponentBuilder.createATextField(100,243,myLayout,this,381,25);
        txt4=UIComponentBuilder.createATextField(100,273,myLayout,this,381,25);
        txt5=UIComponentBuilder.createATextField(100,303,myLayout,this,381,25);
        txtAnswer=UIComponentBuilder.createATextField(128,383,myLayout,this,80,25);

        btnSubmit=UIComponentBuilder.createAButton("Submit",100,35,8,420,myLayout,this,this);
        btnConnect=UIComponentBuilder.createAButton("Connect",100,35,193,420,myLayout,this,this);
        btnExit=UIComponentBuilder.createAButton("Exit",100,35,378,420,myLayout,this,this);
        lblMessage=UIComponentBuilder.createALabel("lblMessage",5,470,475,30,myLayout,this);


        setVisible(true);
    }


    /**
     *Implements the actions when events happen on correspondence components.
     * @param e A semantic event which indicates that a component-defined action occurred.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() ==btnSubmit ) {

            send();
//send question number and answer to the main form


        }
        if (e.getSource() ==btnConnect ) {

            connect(serverName, serverPort);



        }
        if (e.getSource() ==btnExit ) {

           System.exit(0);



        }


    }

    /**
     *Connect the WorkerForm to the chat server
     * @param serverName ChatServer name
     * @param serverPort ChatServer port
     */
    public void connect(String serverName, int serverPort)
    {
        println("Establishing connection. Please wait ...");
        try
        {
            socket = new Socket(serverName, serverPort);
            println("Connected: " + socket);
            open();
        }
        catch (UnknownHostException uhe)
        {
            println("Host unknown: " + uhe.getMessage());
        }
        catch (IOException ioe)
        {
            println("Unexpected exception: " + ioe.getMessage());
        }
    }

    /**
     *send the content of the selected item to the ChatServer
     */
    private void send()
    {
        try
        {
            streamOut.writeUTF(txtAnswer.getText());
            streamOut.flush();
            txtAnswer.setText("");
        }
        catch (IOException ioe)
        {
            println("Sending error: " + ioe.getMessage());
            close();
        }
    }

    /**
     *Process data received from the MainForm.
     *Split the message into pieces, and display them in the correspondence text fields.
     * @param msg The message from MainForm
     */
    public void handle(String msg)
    {
        String[] temp;
        String delimeter = ": ";
        temp = msg.split(delimeter);
        if (Integer.parseInt(temp[0])!=socket.getLocalPort()&&temp[1].equals("MainForm"))
        {
            txtTopic.setText(temp[2]);
            txtQuestion.setText(temp[3]);
            txt1.setText(temp[4]);
            txt2.setText(temp[5]);
            txt3.setText(temp[6]);
            txt4.setText(temp[7]);
            txt5.setText(temp[8]);
        }

        if (msg.equals(".bye"))
        {
            println("Good bye. Press EXIT button to exit ...");
            close();
        }
        else
        {
            System.out.println("Handle: " + msg);
            println(msg);
        }
    }

    /**
     *Creates a new data output stream to write data to the specified underlying output stream.
     *Create a listener to receive messages from the ChatServer.
     */
    public void open()
    {
        try
        {
            streamOut = new DataOutputStream(socket.getOutputStream());
            client2 = new ChatClientThread2(this, socket);
        }
        catch (IOException ioe)
        {
            println("Error opening output stream: " + ioe);
        }
    }

    /**
     *Close the input and output of the WorkerForm (Disconnect WorkerForm from ChatServer).
     */
    public void close()
    {
        try
        {
            if (streamOut != null)
            {
                streamOut.close();
            }
            if (socket != null)
            {
                socket.close();
            }
        }
        catch (IOException ioe)
        {
            println("Error closing ...");
        }
        client2.close();
        client2.stop();
    }

    /**
     * Displayed the message received by the WorkerForm on the lable named lbMessage
     * @param msg the message from the network
     */
    void println(String msg)
    {

        lblMessage.setText(msg);
    }

    /**
     * Get the name and the port of ChatServer
     *
     */
    public void getParameters()
    {
        serverName = "localhost";
        serverPort = 4444;
    }

}
