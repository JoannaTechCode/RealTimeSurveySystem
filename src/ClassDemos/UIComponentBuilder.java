package ClassDemos;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public  class UIComponentBuilder {

        // region Methods

        /**
         * This method takes the parameters given within the method call to generate a JButton
         * component and locate it using a SpringLayout. The button will be given text to display, a size  location and actionlistener
         * before being returned to the caller in working fashion.
         * @param text the text to be printed on the label
         * @param width the width in pixels of the button.
         * @param height height the height in pixels of the button.
         * @param xPos the distance in pixels from the top left corner along the X-axis of the form.
         * @param yPos the distance in pixels from the top left corner along the Y-axis of the form.
         * @param layout layout the SpringLayout component used for positioning the button.
         * @param frame  the JFrame the button is being added to.
         * @param listener the action listener of the window.
         * @return the completed button is returned to the caller once configured.
         */
        public static JButton createAButton(String text, int width, int height, int xPos, int yPos,
                                            SpringLayout layout, JFrame frame, ActionListener listener)
        {

            JButton myNewButton = new JButton(text);
            myNewButton.setPreferredSize(new Dimension(width,height));
            myNewButton.setBackground(new Color(211,211,211));

            myNewButton.addActionListener(listener);


            myNewButton.setBorder(new BasicBorders.ButtonBorder(Color.GRAY,Color.DARK_GRAY,Color.WHITE,Color.WHITE));

            layout.putConstraint(SpringLayout.WEST,myNewButton,xPos,SpringLayout.WEST,frame);
            layout.putConstraint(SpringLayout.NORTH,myNewButton,yPos,SpringLayout.NORTH,frame);

            //Adds the button to the specified frame/form for use.
            frame.add(myNewButton);

            //Returns the finished button back to the caller.
            return myNewButton;
        }
        public static JTable createATable(int xPos, int yPos, SpringLayout layout, JFrame frame,
                                          int width, int height, MyTableModel questions)
        {
            ListSelectionModel listSelectionModel;
            JTable myNewTable=new JTable(questions);
            myNewTable.isForegroundSet();
            myNewTable.setShowHorizontalLines(true);
            myNewTable.setShowVerticalLines(true);
            myNewTable.setRowSelectionAllowed(true);
            myNewTable.setPreferredScrollableViewportSize(new Dimension(width,height));
            myNewTable.setCellSelectionEnabled(false);
            myNewTable.setRowSelectionAllowed(true);

            // Change the text and background colours
            myNewTable.setSelectionForeground(Color.BLUE);
            myNewTable.setSelectionBackground(new Color(255,165,44));


            JScrollPane myScrollPane=new JScrollPane(myNewTable);
            layout.putConstraint(SpringLayout.WEST,myScrollPane,xPos,SpringLayout.WEST,frame);
            layout.putConstraint(SpringLayout.NORTH,myScrollPane,yPos,SpringLayout.NORTH,frame);
            frame.add(myScrollPane);

            return myNewTable;
        }

        /**
         * This method takes the parameters given within the method call to generate a JTextField
         * component and locate it using a SpringLayout. The text field will be given a size and location,
         * before being returned to the caller in working fashion.
         * @param xPos the distance in pixels from the top left corner along the X-axis of the form
         * @param yPos the distance in pixels from the top left corner along the Y-axis of the form
         * @param layout the SpringLayout component used for positioning the text field
         * @param frame the JFrame the text field is being added to.
         * @param width the width in pixels of the text field.
         * @param height the height in pixels of the text field.
         * @return the completed text field is returned to the caller once configured.
         */
        public static  JTextField createATextField(int xPos, int yPos, SpringLayout layout, JFrame frame, int width, int height)
        {
            JTextField myTextField = new JTextField();
            myTextField.setPreferredSize(new Dimension(width,height));
            myTextField.setFont(new Font("Arial", Font.BOLD, 16));
            layout.putConstraint(SpringLayout.WEST,myTextField,xPos,SpringLayout.WEST,frame);
            layout.putConstraint(SpringLayout.NORTH,myTextField,yPos,SpringLayout.NORTH,frame);
            frame.add(myTextField);
            return myTextField;
        }

        /**
         * This method takes the parameters given within the method call to generate a JTextArea.
         * component and locate it using a SpringLayout. The text area will be given a size and location,
         * before being returned to the caller in working fashion.
         * @param xPos the distance in pixels from the top left corner along the X-axis of the form.
         * @param yPos the distance in pixels from the top left corner along the Y-axis of the form.
         * @param layout the SpringLayout component used for positioning the text area.
         * @param frame the JFrame the text area is being added to.
         * @param width the width in pixels of the text area.
         * @param height the height in pixels of the text area.
         * @return the completed text area is returned to the caller once configured.
         */
        public static JTextArea createATextArea(int xPos, int yPos, SpringLayout layout, JFrame frame,int width, int height)
        {
            JTextArea myTextArea=new JTextArea();
            // JScrollPane sp = new JScrollPane(myTextArea);
            myTextArea.setPreferredSize(new Dimension(width, height));
            myTextArea.setLineWrap(true);
            //sp.setVerticalScrollBarPolicy(
            //JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            //sp.setPreferredSize(new Dimension(width,height));
            myTextArea.setFont(new Font("Arial", Font.BOLD, 16));
            myTextArea.setBorder(BorderFactory.createLineBorder(Color.gray));
            layout.putConstraint(SpringLayout.WEST,myTextArea,xPos,SpringLayout.WEST,frame);
            layout.putConstraint(SpringLayout.NORTH,myTextArea,yPos,SpringLayout.NORTH,frame);
            frame.add(myTextArea);
            //frame.add(sp);

            return myTextArea;
        }

        /**
         * This method takes the parameters given within the method call to generate a scroll pane
         * component and locate it using a SpringLayout. The scroll pane will be given a location and dimension on the screen.
         * The scroll pane is fit in a text area.
         * @param xPos distance in pixels from the top left corner along the X-axis of the form.
         * @param yPos the distance in pixels from the top left corner along the Y-axis of the form.
         * @param width the width in pixels of the scroll pane.
         * @param height height the height in pixels of the scroll pane.
         * @param frame the JFrame the scroll pane is being added to.
         * @param layout the SpringLayout component used for positioning the scroll pane.
         * @param myTextAreaWithScrollbar the JTextArea the scroll pane is being added to.
         * @return the completed scroll pane is returned to the caller once configured.
         */
        public static JScrollPane createAScrollBar(int xPos, int yPos,int width, int height, JFrame frame,SpringLayout layout,JTextArea myTextAreaWithScrollbar) {

            // JTextArea myTextAreaWithScrollbar=new J   TextArea();
            JScrollPane scrollPane = new JScrollPane(myTextAreaWithScrollbar);
            scrollPane.setPreferredSize(new Dimension(width,height));
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            layout.putConstraint(SpringLayout.WEST,scrollPane,xPos,SpringLayout.WEST,frame);
            layout.putConstraint(SpringLayout.NORTH,scrollPane,yPos,SpringLayout.NORTH,frame);
            frame.add(scrollPane);
            return scrollPane;
        }

        /**
         * This method takes the parameters given within the method call to generate a JLabel with background color
         * component and locate it using a SpringLayout. The label will be given text to display and a location on screen,
         * before being returned to the caller in working fashion. The size of the label will be set to whatever size is needed
         * to display the given text in the current font setting.
         *
         * @param text      the text to be printed on the label
         * @param xPos      the distance in pixels from the top left corner along the X-axis of the form
         * @param yPos      the distance in pixels from the top left corner along the Y-axis of the form
         * @param width     the width in pixels of the label.
         * @param height    the height in pixels of the label.
         * @param layout    the SpringLayout component used for positioning label
         * @param frame     the JFrame the label is being added to.
         *
         * @return          the completed label with background color is returned to the caller once configured.
         */
        public static JLabel createALabel(String text, int xPos, int yPos, int width, int height, SpringLayout layout, JFrame frame)
        {
            JLabel myLabel = new JLabel(text);
            myLabel.setForeground(Color.WHITE);
            myLabel.setOpaque(true);
            myLabel.setBackground(new Color(0,79,152));
            myLabel.setPreferredSize(new Dimension(width,height));
            myLabel.setFont(new Font("Arial", Font.BOLD, 16));
            myLabel.setSize(100,30);
            layout.putConstraint(SpringLayout.WEST,myLabel,xPos,SpringLayout.WEST,frame);
            layout.putConstraint(SpringLayout.NORTH,myLabel,yPos,SpringLayout.NORTH,frame);
            frame.add(myLabel);
            return myLabel;
        }


    public static  JTextField createAColorTextField(int xPos, int yPos, SpringLayout layout, JFrame frame, int width, int height, Color color)
    {
        JTextField myTextField = new JTextField();
        myTextField.setPreferredSize(new Dimension(width,height));
        myTextField.setFont(new Font("Arial", Font.BOLD, 16));
        myTextField.setEditable(false);
        myTextField.setBackground(color);
        layout.putConstraint(SpringLayout.WEST,myTextField,xPos,SpringLayout.WEST,frame);
        layout.putConstraint(SpringLayout.NORTH,myTextField,yPos,SpringLayout.NORTH,frame);
        frame.add(myTextField);
        return myTextField;
    }

    public static JButton createAColorButton(String text, int width, int height, int xPos, int yPos,
                                        SpringLayout layout, JFrame frame, ActionListener listener, Color color)
    {

        JButton myNewButton = new JButton(text);
        myNewButton.setPreferredSize(new Dimension(width,height));
        myNewButton.setBackground(color);

        myNewButton.addActionListener(listener);


        myNewButton.setBorder(new BasicBorders.ButtonBorder(Color.GRAY,Color.DARK_GRAY,Color.WHITE,Color.WHITE));
        myNewButton.setBorderPainted(false);
        layout.putConstraint(SpringLayout.WEST,myNewButton,xPos,SpringLayout.WEST,frame);
        layout.putConstraint(SpringLayout.NORTH,myNewButton,yPos,SpringLayout.NORTH,frame);

        //Adds the button to the specified frame/form for use.
        frame.add(myNewButton);

        //Returns the finished button back to the caller.
        return myNewButton;
    }



    //endregion

    }
