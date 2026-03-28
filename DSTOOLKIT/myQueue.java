import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Cursor;

public class myQueue extends JFrame {

    private Container c;
    private Font f;
    private JButton btn1,btn2,btn4;
    private Cursor cursor;

    myQueue()
    {

        setSize(1550, 900);
        setTitle("Queue");
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        // Background image set as content pane
        ImageIcon background_image3 = new ImageIcon("qu.jpg");
        Image img3 = background_image3.getImage();
        Image temp_img3 = img3.getScaledInstance(1550,900,Image.SCALE_SMOOTH);
        background_image3 = new ImageIcon(temp_img3);

        JLabel background = new JLabel(background_image3);
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


        // Linear Queue Button
        btn1 = new JButton("LINEAR QUEUE");
        btn1.setFont(f);
        btn1.setBounds(630,300,270,50);
        btn1.setForeground(Color.black);
        btn1.setBackground(Color.YELLOW);
        btn1.setCursor(cursor);
        c.add(btn1);


        // Priority Queue Button
        btn2 = new JButton("PRIORITY QUEUE");
        btn2.setFont(f);
        btn2.setBounds(630,390,270,50);
        btn2.setForeground(Color.black);
        btn2.setBackground(Color.YELLOW);
        btn2.setCursor(cursor);
        c.add(btn2);


        // Back Button
        btn4 = new JButton("BACK");
        btn4.setFont(f);
        btn4.setBounds(1200,620,200,50);
        btn4.setForeground(Color.ORANGE);
        btn4.setBackground(Color.RED);
        btn4.setCursor(cursor);
        c.add(btn4);


        // Action Listeners

        btn1.addActionListener(e -> {

            dispose();
            QueueVisualization qv = new QueueVisualization();
            qv.setVisible(true);

        });


        btn2.addActionListener(e -> {

            dispose();
            PriorityQueueVisualization pq = new PriorityQueueVisualization();
            pq.setVisible(true);

        });


        btn4.addActionListener(e -> {

            dispose();
            DSTOOLKIT frame = new DSTOOLKIT();
            frame.setVisible(true);

        });

    }


    public static void main(String[] args)
    {

        new myQueue();

    }

}
