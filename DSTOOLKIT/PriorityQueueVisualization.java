import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Node {
    int data;
    Node next;

    public Node(int data) {
        this.data = data;
        this.next = null;
    }
}

public class PriorityQueueVisualization extends JFrame {
    private Node head;  // Front of the queue
    private Node tail;  // Rear of the queue
    private JPanel queuePanel;
    private JTextField input;
    private JLabel statusbar;
    private BoxLayout boxLayout;

    public PriorityQueueVisualization() {
        head = null;
        tail = null;

        setTitle("Priority Queue Visualization ");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

       Font f = new Font("Arial", Font.BOLD, 24);

        queuePanel = new JPanel();
        boxLayout = new BoxLayout(queuePanel, BoxLayout.X_AXIS);
        queuePanel.setLayout(boxLayout);
        queuePanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));

        input = new JTextField(6);
        input.setHorizontalAlignment(JTextField.CENTER);
        input.setFont(f);

        // Enqueue operation
        JButton enqueue = new JButton("Enqueue");
        enqueue.setFont(f);
        enqueue.setBackground(Color.CYAN);
        enqueue.setForeground(Color.BLACK);

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
        dequeue.setBackground(Color.ORANGE);
        dequeue.setForeground(Color.BLACK);

        dequeue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                dequeue();
            }
        });
        
        //Back Button
        JButton back= new JButton("Back");
        back.setFont(f);
        back.setBackground(Color.red);
        back.setForeground(Color.ORANGE);

        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                if(e.getSource()==back)
                {
                    dispose();
                    myQueue mq = new myQueue();
                    mq.setVisible(true);  
                }  
            }
           });

        // Status bar to show messages
        statusbar = new JLabel("Queue is empty");
        statusbar.setOpaque(true);
        statusbar.setBackground(Color.LIGHT_GRAY);
        statusbar.setForeground(Color.BLACK);
        statusbar.setFont(f);

        // Control panel setup
        JPanel controlPanel = new JPanel();
        JLabel valueLabel = new JLabel("Value:");
        valueLabel.setFont(f);
        controlPanel.add(valueLabel);

        controlPanel.add(input);
        controlPanel.add(enqueue);
        controlPanel.add(dequeue);
        controlPanel.add(back);

        add(controlPanel, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(queuePanel);
        add(scrollPane, BorderLayout.CENTER);
        add(statusbar, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void enqueue() 
    {
        try {
            int value = Integer.parseInt(input.getText());
            Node newNode = new Node(value);

            // Inserting in sorted order based on priority (lower values have higher priority)
            if (head == null || value < head.data) 
            {
                newNode.next = head;
                head = newNode;
            } 
            else 
            {
                Node temp = head;
                while (temp.next != null && temp.next.data < value) //maze valuee add krbo(insert position)
                {
                    temp = temp.next;
                }
                newNode.next = temp.next;
                temp.next = newNode;
            }

            // Update the tail reference
            if (newNode.next == null) 
            {
                tail = newNode;
            }

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
        if (head == null) 
        {
            statusbar.setText("Queue is empty");
            JOptionPane.showMessageDialog(this, "Queue is empty", "Error", JOptionPane.ERROR_MESSAGE);
        } else 
        {
            int value = head.data;
            head = head.next;
            
            updateQueuePanel();
            statusbar.setText("Dequeued: " + value);
        }
    }

    // Update the visualization panel with the current state of the queue
    private void updateQueuePanel()
    {
        queuePanel.removeAll();

        if (head == null) {
            queuePanel.revalidate();
            queuePanel.repaint();
            return;
        }

        Node temp = head;

        while (temp != null) 
        {
            Font f = new Font("Arial", Font.BOLD, 24);
            // Add queue elements
            JLabel label = new JLabel(String.valueOf(temp.data));
            label.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 10, true));
            label.setFont(f);
            label.setOpaque(true);

            if (temp == head && temp == tail) 
            {
                // If the node is both front and rear (only one element)
                label.setBackground(Color.WHITE);
                label.setText("Front&Rear: " + temp.data);
            } 
            else if (temp == head) 
            {
                // If the node is the front of the queue
                label.setBackground(Color.WHITE);
                label.setText("Front: " + temp.data);
            } 
            else if (temp == tail) 
            {
                // If the node is the rear of the queue
                label.setBackground(Color.WHITE);
                label.setText("Rear: " + temp.data);
            } 
            else 
            {
                label.setBackground(Color.WHITE);
            }

            queuePanel.add(label);
            queuePanel.add(Box.createHorizontalStrut(20));

            temp = temp.next;
        }

        queuePanel.revalidate();
        queuePanel.repaint();
    }

    public static void main(String[] args) {
        PriorityQueueVisualization pv= new PriorityQueueVisualization();
        pv.setVisible(true);
       
        
    }
}
