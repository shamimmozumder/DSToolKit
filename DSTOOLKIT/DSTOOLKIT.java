import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
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
        
        ImageIcon emg = new ImageIcon("pic.jpg");
        Image img1 = emg.getImage();
        Image temp_img1 = img1.getScaledInstance(1550,900,Image.SCALE_SMOOTH);
        emg = new ImageIcon(temp_img1);
       JLabel background = new JLabel("", emg,JLabel.CENTER);
        background.setBounds(0,0,1550,900);
        add(background);

        setVisible(true);

        initComponents();
    }
    public void initComponents()
    {
        c = this.getContentPane();
        c.setLayout(null);

        f = new Font("Arial",Font.BOLD + Font.ITALIC,22);
         //Cursor making
         cursor = new Cursor(Cursor.HAND_CURSOR);

         //0-Back Button
         backButton = new JButton("Back");
         backButton.setFont(f);
         backButton.setBounds(1200,650,200,50);
         backButton.setForeground(Color.ORANGE);
         backButton.setBackground(Color.RED);
         backButton.setCursor(cursor);
         c.add(backButton);

        //1-Linkedlist
        linkedlistButton = new JButton("LINKED-LIST");
        linkedlistButton.setFont(f);
        linkedlistButton.setBounds(700,200,200,50);
        linkedlistButton.setForeground(Color.RED);
        linkedlistButton.setBackground(Color.WHITE);
        linkedlistButton.setCursor(cursor);
        c.add(linkedlistButton);

         //2-STACK
         stackButton = new JButton("STACK");
         stackButton.setFont(f);
         stackButton.setBounds(700,300,200,50);
         stackButton.setForeground(Color.RED);
         stackButton.setBackground(Color.WHITE);
         stackButton.setCursor(cursor);
         c.add(stackButton);


          //3-QUEUE
        queueButton = new JButton("QUEUE");
        queueButton.setFont(f);
        queueButton.setBounds(700,400,200,50);
        queueButton.setForeground(Color.RED);
        queueButton.setBackground(Color.WHITE);
        queueButton.setCursor(cursor);
        c.add(queueButton);

         //4-BINARY SEARCH TREE
         bstButton = new JButton("BST");
         bstButton.setFont(f);
         bstButton.setBounds(700,500,200,50);
         bstButton.setForeground(Color.RED);
         bstButton.setBackground(Color.WHITE);
         bstButton.setCursor(cursor);
         c.add(bstButton);

       bstButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource()==bstButton)
            {
                dispose();
                BSTVisualization BST = new BSTVisualization();
                BST.setVisible(true);
                
            }
            
        }
       });

       stackButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        {
            if(evt.getSource()== stackButton)
            {
                dispose();
                StackVisualization SV = new StackVisualization();
                SV.setVisible(true);
            }
        }
       });

       queueButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        {
            if(evt.getSource()== queueButton)
            {
                dispose();
                myQueue mq = new myQueue();
                mq.setVisible(true);
            }
        }
       });

       backButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        {
            if(evt.getSource()== backButton)
            {
                dispose();
                frontPage sm = new frontPage();
                sm.setVisible(true);
            }
        }
       });

       linkedlistButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        {
            if(evt.getSource()== linkedlistButton)
            {
                dispose();
                Llistfrontpage ab = new Llistfrontpage();
                ab.setVisible(true);
            }
        }
       });

       
    }
   
    public static void main(String[] args) {
         DSTOOLKIT frame =  new DSTOOLKIT();
        
    }
    
}
