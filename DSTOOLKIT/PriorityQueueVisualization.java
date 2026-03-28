import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class PNode {

    int data;
    PNode next;

    public PNode(int data)
    {
        this.data = data;
        this.next = null;
    }

}


public class PriorityQueueVisualization extends JFrame {

    private PNode head;
    private PNode tail;

    private JPanel queuePanel;

    private JTextField input;
    private JTextField capacityField;

    private JLabel statusbar;

    private Font f;

    private int capacity = Integer.MAX_VALUE;
    private int size = 0;

    private boolean smallToLarge = true;


    public PriorityQueueVisualization() {

        head = null;
        tail = null;

        setTitle("Priority Queue Visualization");

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        f = new Font("Arial", Font.BOLD, 26);


        queuePanel = new JPanel(null);

        queuePanel.setBackground(new Color(235,250,255));


        input = new JTextField(6);
        input.setFont(f);

        capacityField = new JTextField(5);
        capacityField.setFont(f);


        // PRIORITY ORDER RADIO BUTTON

        JRadioButton smallFirst = new JRadioButton("Small → Large", true);
        JRadioButton largeFirst = new JRadioButton("Large → Small");

        ButtonGroup group = new ButtonGroup();

        group.add(smallFirst);
        group.add(largeFirst);


        smallFirst.addActionListener(e -> smallToLarge = true);
        largeFirst.addActionListener(e -> smallToLarge = false);


        JButton setCapacity = new JButton("Set Capacity");

        setCapacity.setFont(f);

        setCapacity.addActionListener(e -> {

            try{

                capacity = Integer.parseInt(capacityField.getText());

                statusbar.setText("Capacity set: " + capacity);

            }

            catch(Exception ex){

                JOptionPane.showMessageDialog(this,"Invalid capacity");

            }

        });



        JButton enqueue = new JButton("Enqueue");

        enqueue.setFont(f);

        enqueue.setBackground(new Color(120,255,120));

        enqueue.addActionListener(e -> enqueue());


        JButton dequeue = new JButton("Dequeue");

        dequeue.setFont(f);

        dequeue.setBackground(new Color(255,170,170));

        dequeue.addActionListener(e -> dequeue());


        JButton back = new JButton("Back");

        back.setFont(f);

        back.setBackground(Color.RED);

        back.setForeground(Color.WHITE);

        back.addActionListener(e -> {

            dispose();

            new myQueue().setVisible(true);

        });


        statusbar = new JLabel("Queue Empty");

        statusbar.setFont(f);

        statusbar.setOpaque(true);

        statusbar.setBackground(Color.BLACK);

        statusbar.setForeground(Color.YELLOW);


        JPanel controlPanel = new JPanel();

        controlPanel.add(new JLabel("Capacity"));
        controlPanel.add(capacityField);
        controlPanel.add(setCapacity);

        controlPanel.add(smallFirst);
        controlPanel.add(largeFirst);

        controlPanel.add(new JLabel("Value"));
        controlPanel.add(input);

        controlPanel.add(enqueue);
        controlPanel.add(dequeue);
        controlPanel.add(back);


        add(controlPanel,BorderLayout.NORTH);
        add(queuePanel,BorderLayout.CENTER);
        add(statusbar,BorderLayout.SOUTH);

        setVisible(true);

    }



    private JLabel createNodeLabel(int value)
    {

        JLabel label = new JLabel(String.valueOf(value),SwingConstants.CENTER);

        label.setFont(f);

        label.setOpaque(true);

        label.setBackground(new Color(
                (int)(Math.random()*200),
                (int)(Math.random()*200),
                (int)(Math.random()*200)
        ));

        label.setBorder(BorderFactory.createLineBorder(Color.BLACK,3,true));

        label.setSize(80,80);

        return label;

    }



    private void enqueue()
    {

        try {

            if(size >= capacity)
            {
                statusbar.setText("Queue Full");
                return;
            }

            int value = Integer.parseInt(input.getText());

            PNode newNode = new PNode(value);


            if(head == null ||
                    (smallToLarge && value < head.data) ||
                    (!smallToLarge && value > head.data))

            {

                newNode.next = head;

                head = newNode;

            }

            else

            {

                PNode temp = head;

                while(temp.next != null &&
                        ((smallToLarge && temp.next.data < value) ||
                         (!smallToLarge && temp.next.data > value)))

                {

                    temp = temp.next;

                }

                newNode.next = temp.next;

                temp.next = newNode;

            }


            if(newNode.next == null)

                tail = newNode;


            size++;

            animateInsert(value);

            statusbar.setText("Inserted: " + value);

        }

        catch(Exception e)

        {

            JOptionPane.showMessageDialog(this,"Enter valid integer");

        }


        input.setText("");

    }



    private void dequeue()
    {

        if(head == null)

        {

            JOptionPane.showMessageDialog(this,"Queue Empty");

            return;

        }


        animateDelete();

    }



    private void animateInsert(int value)
    {

        JLabel node = createNodeLabel(value);

        int startX = getWidth();

        int y = 250;

        node.setLocation(startX,y);

        queuePanel.add(node);


        Timer timer = new Timer(10,null);


        timer.addActionListener(new ActionListener(){

            int x = startX;


            public void actionPerformed(ActionEvent e){

                x -= 15;

                node.setLocation(x,y);


                if(x <= 100)

                {

                    timer.stop();

                    updateQueuePanel();

                }

            }

        });


        timer.start();

    }



    private void animateDelete()
    {

        Component comp = queuePanel.getComponent(0);


        Timer timer = new Timer(10,null);


        timer.addActionListener(new ActionListener(){

            int x = comp.getX();


            public void actionPerformed(ActionEvent e){

                x -= 15;

                comp.setLocation(x,comp.getY());


                if(x < -120)

                {

                    timer.stop();

                    int value = head.data;

                    head = head.next;

                    size--;

                    updateQueuePanel();

                    statusbar.setText("Deleted: "+value);

                }

            }

        });


        timer.start();

    }



    private void updateQueuePanel()

    {

        queuePanel.removeAll();

        PNode current = head;

        int x = 100;


        while(current != null)

        {

            JLabel node = createNodeLabel(current.data);

            node.setLocation(x,250);

            queuePanel.add(node);


            if(current == head)

            {

                JLabel frontLabel = new JLabel("↑ Front");

                frontLabel.setFont(new Font("Arial",Font.BOLD,18));

                frontLabel.setBounds(x,210,80,30);

                queuePanel.add(frontLabel);

            }


            if(current.next == null)

            {

                JLabel rearLabel = new JLabel("↑ Rear");

                rearLabel.setFont(new Font("Arial",Font.BOLD,18));

                rearLabel.setBounds(x,340,80,30);

                queuePanel.add(rearLabel);

            }


            JLabel arrow = new JLabel("→");

            arrow.setFont(new Font("Arial",Font.BOLD,40));

            arrow.setLocation(x+80,260);

            arrow.setSize(40,40);

            queuePanel.add(arrow);


            x += 120;

            current = current.next;

        }


        if(head == null)

            statusbar.setText("Queue Empty");


        queuePanel.repaint();

    }



    public static void main(String[] args)

    {

        new PriorityQueueVisualization();

    }

}
