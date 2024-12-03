import java.awt.*;
import javax.swing.*;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Cursor;


public class Llistfrontpage extends JFrame {
    private Container c;
    private Font f;
    private JButton singlyButton,doublyButton,backButton;
    private Cursor cursor;

    Llistfrontpage()
    {
        setSize(1550, 900);
        setTitle("Linked-List");
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        ImageIcon background_image2 = new ImageIcon("pic.jpg");
        Image img2 = background_image2.getImage();
        Image temp_img2 = img2.getScaledInstance(1550,900,Image.SCALE_SMOOTH);
        background_image2 = new ImageIcon(temp_img2);
       JLabel background = new JLabel("", background_image2,JLabel.CENTER);
        background.setBounds(0,0,1550,900);
        add(background);

        setVisible(true);

        initComponents();
    }

    public void initComponents()
    {
        c = this.getContentPane();
        c.setLayout(null);
 
         f = new Font("Arial",Font.BOLD + Font.ITALIC,35);
          //Cursor making
         cursor = new Cursor(Cursor.HAND_CURSOR);

         //Back Button
         //0-Back Button
         backButton = new JButton("Back");
         backButton.setFont(f);
         backButton.setBounds(1250,650,200,50);
         backButton.setForeground(Color.ORANGE);
         backButton.setBackground(Color.RED);
         backButton.setCursor(cursor);
         c.add(backButton);


         //Singly button
         singlyButton = new JButton("Singly LinkedList");
         singlyButton.setFont(f);
         singlyButton.setBounds(630, 370, 350, 50);
         singlyButton.setForeground(Color.BLACK);
         singlyButton.setBackground(Color.GREEN);
         singlyButton.setCursor(cursor);
         c.add(singlyButton);

         //Doubley LinkedList
         doublyButton = new JButton("Doubly LinkedList");
         doublyButton.setFont(f);
         doublyButton.setBounds(630, 450, 350, 50);
         doublyButton.setForeground(Color.BLACK);
         doublyButton.setBackground(Color.GREEN);
         doublyButton.setCursor(cursor);
         c.add(doublyButton);

         singlyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
             {
                 if(e.getSource()==singlyButton)
                 {
                     dispose();
                     SinglyLinkedList sll= new SinglyLinkedList();
                     sll.setVisible(true);
                     
                 }  
             }
            }); 

            doublyButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                 {
                     if(e.getSource()==doublyButton)
                     {
                         dispose();
                         DoublyLinkedListVisualization DL = new DoublyLinkedListVisualization();
                        DL.setVisible(true);
                         
                     }  
                 }
                }); 
            
            backButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt)
                {
                    if(evt.getSource()== backButton)
                    {
                        dispose();
                        DSTOOLKIT xy =  new DSTOOLKIT();
                        xy.setVisible(true);
                    }
                }
               });
    }

    public static void main(String[] args) {
        Llistfrontpage lf = new Llistfrontpage();
        lf.setVisible(true);
       
    }
    
}
