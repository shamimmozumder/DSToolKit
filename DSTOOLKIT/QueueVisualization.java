import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class QNode
{
    int val;
    QNode next;

    public QNode(int val)
    {
        this.val = val;
        this.next = null;
    }
}


class LinkedListQueue
{

    private int size = 0;
    private int capacity = Integer.MAX_VALUE;

    private QNode head, tail;


    public void setCapacity(int cap)
    {
        capacity = cap;
    }


    public boolean isFull()
    {
        return size >= capacity;
    }


    public boolean isEmpty()
    {
        return size == 0;
    }


    public int getSize()
    {
        return size;
    }


    public void enqueue(int value)
    {

        if(isFull())
            throw new IllegalStateException("Queue Full");


        QNode node = new QNode(value);


        if(head == null)
            head = tail = node;

        else
        {
            tail.next = node;
            tail = node;
        }


        size++;

    }



    public int dequeue()
    {

        if(isEmpty())
            throw new IllegalStateException("Queue Empty");


        int val = head.val;


        head = head.next;


        if(head == null)
            tail = null;


        size--;


        return val;

    }



    public QNode getFront()
    {
        return head;
    }


    public QNode getRear()
    {
        return tail;
    }


}



public class QueueVisualization extends JFrame
{

    private LinkedListQueue queue;

    private JPanel queuePanel;

    private JTextField input;

    private JTextField sizeInput;

    private JLabel statusbar;

    private Font f;


    public QueueVisualization()
    {

        queue = new LinkedListQueue();

        setTitle("Queue Visualization");

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        f = new Font("Arial", Font.BOLD, 26);


        queuePanel = new JPanel(null);

        queuePanel.setBackground(new Color(235,250,255));


        input = new JTextField(6);
        input.setFont(f);

        sizeInput = new JTextField(5);
        sizeInput.setFont(f);


        JButton setSize = new JButton("Set Size");

        setSize.setBackground(Color.YELLOW);

        setSize.setFont(f);


        setSize.addActionListener(e -> {

            try
            {
                int cap = Integer.parseInt(sizeInput.getText());

                queue.setCapacity(cap);

                statusbar.setText("Capacity set to: " + cap);
            }

            catch(Exception ex)
            {
                JOptionPane.showMessageDialog(this,"Invalid size");
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


        // BACK BUTTON (same logic as your previous code)

        JButton back = new JButton("Back");

        back.setFont(f);

        back.setBackground(Color.RED);

        back.setForeground(Color.WHITE);


        back.addActionListener(e -> {

            dispose();

            new myQueue().setVisible(true);

        });



        statusbar = new JLabel("Queue Empty");

        statusbar.setOpaque(true);

        statusbar.setBackground(Color.BLACK);

        statusbar.setForeground(Color.YELLOW);

        statusbar.setFont(f);


        JPanel controlPanel = new JPanel();

        controlPanel.add(new JLabel("Capacity"));

        controlPanel.add(sizeInput);

        controlPanel.add(setSize);

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

        try
        {

            int value = Integer.parseInt(input.getText());

            queue.enqueue(value);

            animateInsert(value);

        }

        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this,e.getMessage());
        }

        input.setText("");

    }



    private void dequeue()
    {

        if(queue.isEmpty())
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


        timer.addActionListener(new ActionListener()
        {

            int x = startX;


            public void actionPerformed(ActionEvent e)
            {

                x -= 15;

                node.setLocation(x,y);


                if(x <= 100 + queue.getSize()*90)
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


        timer.addActionListener(new ActionListener()
        {

            int x = comp.getX();


            public void actionPerformed(ActionEvent e)
            {

                x -= 15;

                comp.setLocation(x,comp.getY());


                if(x < -120)
                {

                    timer.stop();

                    queue.dequeue();

                    updateQueuePanel();

                }

            }

        });


        timer.start();

    }



    private void updateQueuePanel()
    {

        queuePanel.removeAll();

        QNode current = queue.getFront();

        int x = 100;


        while(current != null)
        {

            JLabel node = createNodeLabel(current.val);

            node.setLocation(x,250);

            queuePanel.add(node);


            // FRONT POINTER

            if(current == queue.getFront())
            {

                JLabel frontLabel = new JLabel("↑ Front");

                frontLabel.setFont(new Font("Arial",Font.BOLD,18));

                frontLabel.setBounds(x,210,80,30);

                queuePanel.add(frontLabel);

            }


            // REAR POINTER

            if(current == queue.getRear())
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


        if(queue.isEmpty())
            statusbar.setText("Queue Empty");

        else if(queue.isFull())
            statusbar.setText("Queue Full");


        queuePanel.repaint();

    }



    public static void main(String[] args)
    {

        new QueueVisualization();

    }

}
