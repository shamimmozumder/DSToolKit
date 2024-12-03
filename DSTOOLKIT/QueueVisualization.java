import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Node 
{
    int val;
    Node next;

    public Node(int val) 
    {
        this.val = val;
        this.next = null;
    }
}

class LinkedListQueue 
{
    private int sz = 0;
    private Node head, tail;

    public LinkedListQueue() 
    {
        head = null;
        tail = null;
    }

    public void enqueue(int value) 
    {
        sz++;
        Node newNode = new Node(value);
        if (tail == null) 
        {
            head = newNode;
            tail = newNode;
        } 
        else 
        {
            tail.next = newNode;
            tail = newNode;
        }
    }

    public int dequeue() 
    {
        if (head == null) 
        {
            throw new IllegalStateException("Queue is Empty");
        }
        sz--;
        int value = head.val;
        head = head.next;
        if (head == null) 
        {
            tail = null;
        }
        return value;
    }

    public int getSize() 
    {
        return sz;
    }

    public boolean isEmpty() 
    {
        return head == null;
    }

    public Node getFront() 
    {
        return head;
    }

    public Node getRear() 
    {
        return tail;
    }
}

public class QueueVisualization extends JFrame 
{
    private LinkedListQueue queue;
    private JPanel queuePanel;
    private JTextField input;
    private JLabel statusbar;
    private Font f;
    private BoxLayout Box;

    public QueueVisualization() 
    {
        queue = new LinkedListQueue();

        setTitle("Queue Visualization");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        f = new Font("Arial", Font.BOLD + Font.ITALIC, 24);

        queuePanel = new JPanel();
        //Box = new BoxLayout(queuePanel, BoxLayout.X_AXIS);
        //queuePanel.setLayout(Box);
        queuePanel.setLayout(new BoxLayout(queuePanel, BoxLayout.X_AXIS));
        queuePanel.setBorder(BorderFactory.createLineBorder(Color.blue));

        input = new JTextField(6);
        input.setHorizontalAlignment(JTextField.CENTER);
        input.setFont(f);

        // Enqueue operation
        JButton enqueue = new JButton("Enqueue");
        enqueue.setFont(f);
        enqueue.setForeground(Color.BLACK);
        enqueue.setBackground(Color.CYAN);

        enqueue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                enqueue();
            }
        });

        // Dequeue operation
        JButton dequeue = new JButton("Dequeue");
        dequeue.setFont(f);
        dequeue.setForeground(Color.BLACK);
        dequeue.setBackground(Color.PINK);

        dequeue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                dequeue();
            }
        });

        // Back Operation
        JButton back = new JButton("Back");
        back.setFont(f);
        back.setForeground(Color.ORANGE);
        back.setBackground(Color.RED);

        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                if(e.getSource() == back)
                {
                    dispose();
                    
                     myQueue MQ = new myQueue();
                     MQ.setVisible(true);
                }   
            }
        });

        statusbar = new JLabel("Queue is empty");
        statusbar.setOpaque(true);
        statusbar.setBackground(Color.DARK_GRAY);
        statusbar.setForeground(Color.RED);
        statusbar.setFont(f);

        JPanel controlPanel = new JPanel();
        JLabel value = new JLabel("Value:");
        value.setFont(f);
        controlPanel.add(value);

        controlPanel.add(input);
        controlPanel.add(enqueue);
        controlPanel.add(dequeue);
        controlPanel.add(back);

        add(controlPanel, BorderLayout.NORTH);
        JScrollPane jp = new JScrollPane(queuePanel);
        add(jp, BorderLayout.CENTER);
        add(statusbar, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void enqueue() 
    {
        try 
        {
            int value = Integer.parseInt(input.getText());
            queue.enqueue(value);
            updateQueuePanel();
            statusbar.setText("Enqueued: " + value);
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(this, "Enter a valid integer", "Error", JOptionPane.ERROR_MESSAGE);
        }
        input.setText("");
    }

    private void dequeue() 
    {
        if (queue.isEmpty()) 
        {
            statusbar.setText("Queue is empty");
            JOptionPane.showMessageDialog(this, "Queue is empty", "Error", JOptionPane.ERROR_MESSAGE);
        } 
        else 
        {
            int value = queue.dequeue();
            updateQueuePanel();
            statusbar.setText("Dequeued: " + value);
        }
    }

    private void updateQueuePanel() 
    {
        queuePanel.removeAll();
        Node current = queue.getFront();
        while (current != null) 
        {
            JLabel label = new JLabel(String.valueOf(current.val));
            label.setFont(f);
            label.setOpaque(true);
            label.setBackground(Color.WHITE);

            if (current == queue.getFront() && current==queue.getRear()) 
           
            {
                label.setText("Front&Rear: " + current.val);
                label.setBackground(Color.WHITE);
            } 
            else if(current== queue.getFront())
            {
                label.setText("Front: " + current.val);
                label.setBackground(Color.WHITE);
            }
            else if (current == queue.getRear()) 
            {
                label.setText("Rear: " + current.val);
                label.setBackground(Color.WHITE);
            }
            else 
            {
                label.setBackground(Color.WHITE);
            }

            label.setBorder(BorderFactory.createLineBorder(Color.MAGENTA, 13, true));
            queuePanel.add(label);
            queuePanel.add(javax.swing.Box.createHorizontalStrut(20));

            current = current.next;
        }

        queuePanel.revalidate();
        queuePanel.repaint();
    }

    public static void main(String[] args) 
    {
        QueueVisualization qv = new QueueVisualization();
        qv.setVisible(true);

        
    }
}
