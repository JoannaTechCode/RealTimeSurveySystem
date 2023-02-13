
/****************************************************************
 PROGRAM:   Survey
 AUTHOR:    Qian Xie (Joanna)


 FUNCTION:  The code in this file create and locate components to the MainForm (manager's form).
            Connect Mainform to the network, also implemented fuctions that Mainframe receives, sends data, and disconnects from the network.

 INPUT:     MyTableModel


 OUTPUT:    .\Survey\HashBinaryTree.txt

 ****************************************************************/
package ClassDemos;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.net.*;
import java.io.*;
import java.util.HashSet;
import org.apache.commons.lang3.StringUtils;

/**
 * The code in this file create and locate components to the MainForm (manager's form).
 * Connect MainForm to the network, also implemented fuctions that MainForm receives, sends data, and disconnects from the network.
 * @author Qian Xie(Joanna)
 * @version 25 October,2022
 */
public class MainForm extends JFrame implements ActionListener {
    String header[]={"#","Topic","Question"};
    ArrayList<Object[]> tableData = new ArrayList<>();
    BinaryTree tree=new BinaryTree();
    DList dList=new DList();
    FileManager fileManager=new FileManager();
    int totalScore=0;
    int i=0;
    float averageScore=0;




    private Socket socket = null;
    private DataInputStream console = null;
    private DataOutputStream streamOut = null;
    private ChatClientThread1 client = null;
    private String serverName = "localhost";
    private int serverPort = 4444;




    MyTableModel tbQuestionModel;
    // Declaration of UI Components for form
    JButton btnQn, btnTopic, btnQuestion, btnExit, btnConnect, btnSend,btnPreDisplay, btnInDisplay,
            btnPostDisplay, btnDisplay,btnSave;
    JTextField txtSearch, txtTopic, txtQnNumber, txtA, txtB, txtC, txtD, txtE;
    JLabel lbTitle, lbSearch, lbSurveyQuestions, lbSortBy, lbLinkedList, lbBinaryTree, lbPreOrder,
            lbTopic, lbQn, lbA, lbB, lbC, lbD, lbE,lblMessage;
    JTextArea txtLinkList, txtBinaryTree, txtQuestion;
    JTable tbQuestions;
    JScrollPane spLinkList, spBinaryTree, spQn;
    SpringLayout myLayout = new SpringLayout();


    /**
     *Initialise, instance and locate a MainForm and swing components.
     * Implemented clicking the item on the Jtable, the content of the item will
     * be displayed in the specified text box
     */
    public MainForm() {
        setSize(1000, 780);
        setLocationRelativeTo(null);
        setLayout(myLayout);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                Runtime.getRuntime().addShutdownHook(new Thread(){
                    public  void run(){
                        try{
                            System.out.println("Shutdown Hook is running");
                            Thread.sleep(6000);
                            saveBiaryTree();
                            System.out.println("Saving Binary tree");
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            e.printStackTrace();
                        }
                    }
                });
                System.out.println("System.exit(0)");
                System.exit(0);
            }
        });

        lbTitle=UIComponentBuilder.createALabel("Survey by NetWork",0,0,1000,35,myLayout,this);
        lbTitle.setHorizontalAlignment(0);
        lbTitle.setFont(new Font("Arial", Font.PLAIN, 25));
        lbSearch=UIComponentBuilder.createALabel("Search Question:",5,38,150,25,myLayout,this);



        txtSearch=UIComponentBuilder.createATextField(160,38,myLayout,this,390,25);
        lbSurveyQuestions=UIComponentBuilder.createALabel("Servey Questions",5,70,560,20,myLayout,this);
        lbSurveyQuestions.setHorizontalAlignment(0);
        lbSurveyQuestions.setBackground(Color.WHITE);

        tableData = fileManager.readDataFromFile();
        tbQuestionModel =new MyTableModel(tableData,header);
        tbQuestions=UIComponentBuilder.createATable(5,91,myLayout,this,544,220,tbQuestionModel);
        tbQuestions.getColumnModel().getColumn(0).setPreferredWidth(25);
        tbQuestions.getColumnModel().getColumn(1).setPreferredWidth(80);
        tbQuestions.getColumnModel().getColumn(2).setPreferredWidth(444);
        /*tbQuestions.getColumnModel().getColumn(3).setPreferredWidth(0);
        tbQuestions.getColumnModel().getColumn(4).setPreferredWidth(0);
        tbQuestions.getColumnModel().getColumn(5).setPreferredWidth(0);
        tbQuestions.getColumnModel().getColumn(6).setPreferredWidth(0);
        tbQuestions.getColumnModel().getColumn(7).setPreferredWidth(0);*/


        lbTopic=UIComponentBuilder.createALabel("Topic:",570,38,60,20,myLayout,this);
        txtTopic=UIComponentBuilder.createATextField(632,38,myLayout,this,350,25);
        lbQn=UIComponentBuilder.createALabel("Qn:", 570,65,60,20,myLayout,this);

        txtQuestion=new JTextArea();
        spQn=UIComponentBuilder.createAScrollBar(632,65,350,70,this,myLayout,txtQuestion);
        txtQuestion=UIComponentBuilder.createATextArea(632,65,myLayout,this,350,70);

        txtQnNumber=UIComponentBuilder.createATextField(570,87,myLayout,this,35,25);

        lbA=UIComponentBuilder.createALabel("A:", 570,135,60,30,myLayout,this);
        lbB=UIComponentBuilder.createALabel("B:", 570,175,60,30,myLayout,this);
        lbC=UIComponentBuilder.createALabel("C:", 570,215,60,30,myLayout,this);
        lbD=UIComponentBuilder.createALabel("D:", 570,250,60,30,myLayout,this);
        lbE=UIComponentBuilder.createALabel("E:", 570,285,60,30,myLayout,this);

        txtA=UIComponentBuilder.createATextField(632,135,myLayout,this,350,30);
        txtB=UIComponentBuilder.createATextField(632,175,myLayout,this,350,30);
        txtC=UIComponentBuilder.createATextField(632,215,myLayout,this,350,30);
        txtD=UIComponentBuilder.createATextField(632,250,myLayout,this,350,30);
        txtE=UIComponentBuilder.createATextField(632,285,myLayout,this,350,30);

        lbSortBy=UIComponentBuilder.createALabel("Sort by:",5,330,80,25,myLayout,this);
        btnQn=UIComponentBuilder.createAButton("Qn#",100,25,115,333,myLayout,this,this);
        btnTopic=UIComponentBuilder.createAButton("Topic",100,25,260,333,myLayout,this,this);
        btnQuestion =UIComponentBuilder.createAButton("Question",100,25,400,333,myLayout,this,this);

        btnExit=UIComponentBuilder.createAButton("Exit",250,25,5,360,myLayout,this,this);
        btnConnect=UIComponentBuilder.createAButton("Connect",250,25,380,360,myLayout,this,this);
        btnSend=UIComponentBuilder.createAButton("Send",250,25,730,360,myLayout,this,this);

        lbLinkedList=UIComponentBuilder.createALabel("Linked List:",5,385,110,25,myLayout,this);

        txtLinkList=new JTextArea();
        spLinkList=UIComponentBuilder.createAScrollBar(5,415,975,70,this,myLayout,txtLinkList);
        txtLinkList=UIComponentBuilder.createATextArea(5,415,myLayout,this,975,70);

        lbBinaryTree=UIComponentBuilder.createALabel("Binary Tree:",5,490,110,25,myLayout,this);
        txtBinaryTree=new JTextArea();
        spBinaryTree=UIComponentBuilder.createAScrollBar(5,520,975,70,this,myLayout,txtBinaryTree);
        txtBinaryTree=UIComponentBuilder.createATextArea(5,520,myLayout,this,975,70);

        lbPreOrder=UIComponentBuilder.createALabel("Pre-Order",20,600,80,25,myLayout,this);
        lbPreOrder=UIComponentBuilder.createALabel("In-Order",200,600,80,25,myLayout,this);
        lbPreOrder=UIComponentBuilder.createALabel("Post-Order",380,600,90,25,myLayout,this);

        btnPreDisplay=UIComponentBuilder.createAButton("Display",80,30,20,627,myLayout,this,this);
        btnInDisplay=UIComponentBuilder.createAButton("Display",80,30,200,627,myLayout,this,this);
        btnPostDisplay=UIComponentBuilder.createAButton("Display",90,30,380,627,myLayout,this,this);

        btnDisplay=UIComponentBuilder.createAButton("Display",150,40,550,617,myLayout,this,this);
        btnSave=UIComponentBuilder.createAButton("Save",150,40,800,617,myLayout,this,this);
        lblMessage=UIComponentBuilder.createALabel("lblMessage",5,680,975,30,myLayout,this);

        // Below is my code
        tbQuestions.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row=tbQuestions.getSelectedRow();
                txtTopic.setText(tbQuestions.getValueAt(row,1).toString());
                txtQnNumber.setText(tbQuestions.getValueAt(row,0).toString());
                txtQuestion.setText(tbQuestions.getValueAt(row,2).toString());
                txtA.setText(tbQuestions.getModel().getValueAt(row, 3).toString());
                txtB.setText(tbQuestions.getModel().getValueAt(row, 4).toString());
                txtC.setText(tbQuestions.getModel().getValueAt(row, 5).toString());
                txtD.setText(tbQuestions.getModel().getValueAt(row, 6).toString());
                txtE.setText(tbQuestions.getModel().getValueAt(row, 7).toString());

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });


        txtSearch.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                TableRowSorter<MyTableModel> search=new TableRowSorter<MyTableModel>(tbQuestionModel);
                tbQuestions.setRowSorter(search);
                search.setRowFilter(RowFilter.regexFilter(txtSearch.getText()));

            }
        });
        setVisible(true);

    }

    /**
     *Implements the actions when events happen on correspondence components.
     * @param e A semantic event which indicates that a component-defined action occurred.
     */

   public void actionPerformed(ActionEvent e) {
        if (e.getSource() ==btnQn) {
            //Bubble sorting
            for (int i = 0; i < tableData.size()-1; i++) {
                for (int j = 0; j <tableData.size()-1-i ; j++) {
                    if (Integer.parseInt(tableData.get(j)[0].toString())>Integer.parseInt(tableData.get(j+1)[0].toString()))
                    {
                        Object[] temp=tableData.get(j);
                        tableData.set(j,tableData.get(j+1));
                        tableData.set(j+1,temp);
                    }

                }
            }
            updateTable();

        }

        if (e.getSource() == btnTopic) {
            //Insert sorting
            for (int i = 1; i < tableData.size(); i++) {
                String currentValue=tableData.get(i)[1].toString();
                int j=i-1;
                while (j>=0 && tableData.get(j)[1].toString().compareToIgnoreCase(currentValue)>0){

                    Object[] temp=tableData.get(j);
                    tableData.set(j,tableData.get(j+1));
                    tableData.set(j+1,temp);
                    j--;
                }
            }
            updateTable();

        }
        if (e.getSource() == btnQuestion) {
            // Selection Sort
            String  minValue;
            int minIndex;
            for (int i = 0; i < tableData.size(); i++) {
                minValue=tableData.get(i)[2].toString();
                minIndex=i;
                for (int j = i; j <tableData.size() ; j++) {
                    if(tableData.get(j)[2].toString().compareToIgnoreCase(minValue)<0)
                    {
                        minValue=tableData.get(j)[2].toString();
                        minIndex=j;
                    }

                }
                if(minValue.compareToIgnoreCase(tableData.get(i)[2].toString())<0)
                {
                    Object[] temp=tableData.get(i);
                    tableData.set(i,tableData.get(minIndex));
                    tableData.set(minIndex, temp);
                }
            }

            updateTable();
        }
       if (e.getSource() ==btnSend ) {
            tree.addNode(Integer.parseInt(tableData.get(tbQuestions.getSelectedRow())[0].toString()),txtTopic.getText());
           String topic=txtTopic.getText();
           String qNumber=txtQnNumber.getText();
           String avgScore=Float.toString(averageScore);

            dList.head.append(new DNode(topic+", Qn"+qNumber +"<-->"));
            txtLinkList.setText(dList.toString());
           send();
           totalScore=0;
           averageScore=0;
           i=0;
        }
       if (e.getSource() == btnPreDisplay) {
            String displayContent="PRE-ORDER: "+ tree.preorderTraverseTree(tree.root);
          displayContent= displayContent.substring(0,displayContent.length()-2);
           txtBinaryTree.setText(displayContent);
        }
        if (e.getSource() == btnInDisplay) {
            String displayContent="IN-ORDER: "+tree.inOrderTraverseTree(tree.root);
            displayContent= displayContent.substring(0,displayContent.length()-2);
            txtBinaryTree.setText(displayContent);

        }
        if (e.getSource() == btnPostDisplay) {
            String displayContent="POST-ORDER: "+ tree.postOrderTraverseTree(tree.root);
            displayContent= displayContent.substring(0,displayContent.length()-2);
            txtBinaryTree.setText(displayContent);
        }
        if (e.getSource()==btnConnect)
        {

            connect(serverName, serverPort);

        }

       if (e.getSource() == btnSave) {
           saveBiaryTree();
       }
         if (e.getSource() == btnExit) {
             Runtime.getRuntime().addShutdownHook(new Thread(){
                 public  void run(){
                     try{
                         System.out.println("Shutdown Hook is running");
                         Thread.sleep(6000);
                         saveBiaryTree();
                         System.out.println("Saving Binary tree");
                     } catch (InterruptedException e) {
                         Thread.currentThread().interrupt();
                         e.printStackTrace();
                     }
                 }
             });
             System.out.println("System.exit(0)");

             System.out.println("ManagerForm Terminating ");
                System.exit(0);
        }

    }

    /**
     * Apply hashset to the content of binary tree and save it to the disk.
     */
    private void saveBiaryTree() {
        HashSet<String> hashedFile = new HashSet<String>();
        String tempfile=tree.inOrderTraverseTree(tree.root);
        String[] nodes= StringUtils.split(tempfile,",");
        for (i=0; i<nodes.length-1; i++)
        {
            hashedFile.add(nodes[i]);
        }
        fileManager.writeDataToFile(hashedFile);
        lblMessage.setText("The content of BinaryTree has beed saved on the disk. ");
    }

    /**
     * Repaint and reset Jtable
     */
    private void updateTable() {
        this.tbQuestions.repaint();
        this.tbQuestions.updateUI();
        tbQuestions.getSelectionModel().clearSelection();
    }

    /**
     *
     *Connect the MainForm to the chat server
     *
     * @param serverName ChatServer name
     * @param serverPort ChatServer port
     */
    //close method is copied from mark
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
     * send the content of the selected item to the ChatServer
     */
    private void send()
    {
        try
        {
            streamOut.writeUTF("MainForm"+": "+txtTopic.getText()+": "+txtQnNumber.getText()+": "+txtQuestion.getText()+": "+txtA.getText()+": "+txtB.getText()+": "+txtC.getText()+": "+txtD.getText()+": "+txtE.getText());
            streamOut.flush();

        }
        catch (IOException ioe)
        {
            println("Sending error: " + ioe.getMessage());
            close();
        }
    }

    /**
     *
     *Process data received from the WorkerForm.
     * Split the message into pieces, get the score, calculate the average score,
     * update double link list with the average score.
     * @param msg  The message from WorkerForm
     */
    public void handle(String msg)
    {
        String[] temp;
        String delimeter = ": ";
        temp = msg.split(delimeter);

            totalScore=totalScore+Integer.parseInt(temp[1]);
            i=i+1;
            averageScore=((float)totalScore)/i;
           dList.get(1).myWords=txtTopic.getText()+", Qn"+txtQnNumber.getText()+", "+Float.toString(averageScore) +"<-->";
           txtLinkList.setText(dList.toString());

        if (msg.equals(".bye"))
        {
            println("Good bye. Press EXIT button to exit ...");
            close();
        }
        else
        {
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
            client = new ChatClientThread1(this, socket);
        }
        catch (IOException ioe)
        {
            println("Error opening output stream: " + ioe);
        }
    }

    /**
     *Close the input and output of the Mainform (Disconnect MainForm from ChatServer).
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
        client.close();
        client.stop();
    }

    /**
     * Displayed the message received by the Mainform on the lable named lbMessage
     * @param msg the message from the network
     */
    void println(String msg)
    {

        lblMessage.setText(msg);
    }

    /**
     * Get the name and the port of ChatServer
     */
    public void getParameters()
    {
//        serverName = getParameter("host");
//        serverPort = Integer.parseInt(getParameter("port"));

        serverName = "localhost";
        serverPort = 4444;
    }

}
