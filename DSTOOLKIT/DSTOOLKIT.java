import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Cursor;

public class DSTOOLKIT extends JFrame {

    private Container c;
    private Font f;
    private JButton linkedlistButton,stackButton,queueButton,bstButton,backButton;
    private Cursor cursor;

    DSTOOLKIT()
    {

        setSize(1550, 900);
        setTitle("DSTOOLKIT");
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Background image set as content pane
        ImageIcon emg = new ImageIcon("pic.jpg");
        Image img1 = emg.getImage();
        Image temp_img1 = img1.getScaledInstance(1550,900,Image.SCALE_SMOOTH);
        emg = new ImageIcon(temp_img1);

        JLabel background = new JLabel(emg);
        background.setLayout(null);

        setContentPane(background);

        initComponents();

        setVisible(true);
    }


    public void initComponents()
    {

        c = this.getContentPane();

        f = new Font("Arial",Font.BOLD + Font.ITALIC,22);

        cursor = new Cursor(Cursor.HAND_CURSOR);


        // Back Button
        backButton = new JButton("Back");
        backButton.setFont(f);
        backButton.setBounds(1200,650,200,50);
        backButton.setForeground(Color.ORANGE);
        backButton.setBackground(Color.RED);
        backButton.setCursor(cursor);
        c.add(backButton);


        // Linkedlist Button
        linkedlistButton = new JButton("LINKED-LIST");
        linkedlistButton.setFont(f);
        linkedlistButton.setBounds(700,200,200,50);
        linkedlistButton.setForeground(Color.RED);
        linkedlistButton.setBackground(Color.WHITE);
        linkedlistButton.setCursor(cursor);
        c.add(linkedlistButton);


        // Stack Button
        stackButton = new JButton("STACK");
        stackButton.setFont(f);
        stackButton.setBounds(700,300,200,50);
        stackButton.setForeground(Color.RED);
        stackButton.setBackground(Color.WHITE);
        stackButton.setCursor(cursor);
        c.add(stackButton);


        // Queue Button
        queueButton = new JButton("QUEUE");
        queueButton.setFont(f);
        queueButton.setBounds(700,400,200,50);
        queueButton.setForeground(Color.RED);
        queueButton.setBackground(Color.WHITE);
        queueButton.setCursor(cursor);
        c.add(queueButton);


        // BST Button
        bstButton = new JButton("BST");
        bstButton.setFont(f);
        bstButton.setBounds(700,500,200,50);
        bstButton.setForeground(Color.RED);
        bstButton.setBackground(Color.WHITE);
        bstButton.setCursor(cursor);
        c.add(bstButton);


        // Action Listeners

        bstButton.addActionListener(e -> {

            dispose();
            BSTVisualization BST = new BSTVisualization();
            BST.setVisible(true);

        });


        stackButton.addActionListener(e -> {

            dispose();
            StackVisualization SV = new StackVisualization();
            SV.setVisible(true);

        });


        queueButton.addActionListener(e -> {

            dispose();
            myQueue mq = new myQueue();
            mq.setVisible(true);

        });


        backButton.addActionListener(e -> {

            dispose();
            frontPage sm = new frontPage();
            sm.setVisible(true);

        });


        linkedlistButton.addActionListener(e -> {

            dispose();
            Llistfrontpage ab = new Llistfrontpage();
            ab.setVisible(true);

        });

    }


    public static void main(String[] args)
    {

        new DSTOOLKIT();

    }

}
