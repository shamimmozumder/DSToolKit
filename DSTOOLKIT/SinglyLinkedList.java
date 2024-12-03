import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Node 
{
    int data;
    Node next;
    int pos;

    public Node(int data) 
    {
        this.data = data;
        this.next = null;
        this.pos = 0;
    }
}

class LinkedList {
    private Node head;
    int pos;

    public LinkedList() 
    {
        head = null;
    }

    public void insertAtTail(int data) 
    {
        Node newNode = new Node(data);
        if (head == null) 
        {
            head = newNode;
        } 
        else 
        {
            Node temp = head;
            while (temp.next != null) 
            {
                temp = temp.next;
            }
            temp.next = newNode;
        }
    }
    
    // Insert at head
    public void insertAtHead(int data)
    {
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;   
    }
    
    // Insert at position
    public void insertPosition(int data, int pos)
    {
        Node newNode = new Node(data);
        Node temp = head;

        for (int i = 1; i <= pos - 1; i++)
        {
            temp = temp.next;
        }
        if (pos == 0)
        {
           newNode.next = head;
           head = newNode; 
        }
        else
        {
            newNode.next = temp.next;
            temp.next = newNode;
        }
    }

    public void deleteHead()
    {
        if (head == null)
        {
            return;
        }
        
        head = head.next;
    }
    
    public Node getHead()
    {
        return head;
    }

    public void deletePosition(int pos)
    {
        Node temp = head;

        for (int i = 1; i <= pos - 1; i++)
        {
            temp = temp.next;
        }
        if (pos == 0)
        {
            head = head.next;
        }
        else
        {
            temp.next = temp.next.next;
        }
    }
}

public class SinglyLinkedList extends JFrame {
    private LinkedList linkedList;
    private JTextField inputField, posField;
    private JButton insertButton, deleteButton, insertHeadButton, insertPositionButton, deleteHeadButton, deletePositionButton,backButton;
    private JPanel drawPanel;
    private Font f;
    private JLabel inputLabel, posLabel;

    public SinglyLinkedList() 
    {
        linkedList = new LinkedList();

        setTitle("Singly Linked List Visualization");
        setSize(1550, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        f = new Font("Arial", Font.BOLD + Font.ITALIC, 17);
        
        // Input and control panel
        JPanel controlPanel = new JPanel();
        inputField = new JTextField(6);
        inputField.setHorizontalAlignment(JTextField.CENTER);
        inputField.setFont(f);

        insertButton = new JButton("Insert Tail");
        insertButton.setFont(f);
        insertButton.setBackground(Color.yellow);
        insertButton.setForeground(Color.RED);

        insertHeadButton = new JButton("Insert Head");
        insertHeadButton.setFont(f);
        insertHeadButton.setBackground(Color.LIGHT_GRAY);
        insertHeadButton.setForeground(Color.BLUE);

        insertPositionButton = new JButton("Insert Position");
        insertPositionButton.setFont(f);
        insertPositionButton.setBackground(Color.orange);
        insertPositionButton.setForeground(Color.BLACK);

        deleteButton = new JButton("Delete Head");
        deleteButton.setFont(f);
        deleteButton.setBackground(Color.BLUE);
        deleteButton.setForeground(Color.GREEN);

        deletePositionButton = new JButton("Delete Position");
        deletePositionButton.setFont(f);
        deletePositionButton.setBackground(Color.MAGENTA);
        deletePositionButton.setForeground(Color.BLACK);

        backButton = new JButton("Back");
        backButton.setFont(f);
        backButton.setBackground(Color.red);
        backButton.setForeground(Color.ORANGE);
        
        posField = new JTextField(6);
        posField.setFont(f);

        inputLabel = new JLabel("Enter Value: ");
        inputLabel.setFont(f);
        controlPanel.add(inputLabel);
       
        controlPanel.add(inputField);
        controlPanel.add(insertButton);
        controlPanel.add(insertHeadButton);
        controlPanel.add(insertPositionButton);
        controlPanel.add(deleteButton);
        controlPanel.add(deletePositionButton);
        controlPanel.add(backButton);

        posLabel = new JLabel("Enter Position: ");
        posLabel.setFont(f);
        controlPanel.add(posLabel);
        controlPanel.add(posField);
        
        add(controlPanel, BorderLayout.NORTH);

        // Drawing panel
        drawPanel = new JPanel() 
        {
            @Override
            protected void paintComponent(Graphics g) 
            {
                super.paintComponent(g);
                drawLinkedList(g);
            }
        };
        drawPanel.setBackground(Color.WHITE);
        add(drawPanel, BorderLayout.CENTER);

        // Insert tail operation
        insertButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    int value = Integer.parseInt(inputField.getText());
                    linkedList.insertAtTail(value);
                    drawPanel.repaint();
                    inputField.setText("");
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(SinglyLinkedList.this, "Enter a valid integer value.");
                }
            }
        });
        
        // Insert head operation
        insertHeadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try
                {
                    int value = Integer.parseInt(inputField.getText());
                    linkedList.insertAtHead(value);
                    drawPanel.repaint();
                    inputField.setText("");
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(SinglyLinkedList.this, " Enter a valid integer value.");
                }
            }
        });

        // Insert at any position
        insertPositionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try
                {
                    int value = Integer.parseInt(inputField.getText());
                    int pos = Integer.parseInt(posField.getText());
                    linkedList.insertPosition(value, pos);
                    drawPanel.repaint();
                    inputField.setText("");
                    posField.setText("");
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(SinglyLinkedList.this, "Enter valid integer value and position.");
                }
            }
        });

        // Delete head operation
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try
                {
                    linkedList.deleteHead();
                    drawPanel.repaint();
                    inputField.setText("");
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(SinglyLinkedList.this, "No Node Found."); 
                }
            }
        });

        // Delete at any position
        deletePositionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try
                {
                    int pos = Integer.parseInt(posField.getText());
                    linkedList.deletePosition(pos);
                    drawPanel.repaint();
                    inputField.setText("");
                    posField.setText("");
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(SinglyLinkedList.this, "Enter Valid Position.");
                }
            }
        });

        //Back Operation
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                if(e.getSource()==backButton)
                {
                    dispose();
                    Llistfrontpage li = new Llistfrontpage();
                    li.setVisible(true);  
                }  
            }
           });

    }

    // Method to draw the linked list on the panel
    private void drawLinkedList(Graphics g) 
    {
        Node temp = linkedList.getHead();
        int x = 100; // starting position
        int y = 250;
        boolean isHead = true;
        Node tail = null;

        // Find the last node (tail) for labeling
        if (temp != null) {
            while (temp.next != null) {
                temp = temp.next;
            }
            tail = temp;
        }

        temp = linkedList.getHead(); // reset temp to head
        while (temp != null) 
        {
            g.setColor(Color.BLUE);
            g.setFont(f);
            g.drawRect(x, y, 52, 32);
            g.drawString(Integer.toString(temp.data), x + 20, y + 20);
            g.setColor(Color.BLACK);
            g.setFont(f);

            // Label the Head
            if (isHead) 
            {
                g.setColor(Color.RED);
                g.drawString("Head", x + 5, y - 10); 
                isHead = false;
            }

            // Label the Tail
            if (temp == tail) 
            {
                g.setColor(Color.RED);
                g.drawString("Tail", x + 5, y + 60); 
            }

            // Draw arrow for the link
            if (temp.next != null) 
            {
                g.drawLine(x + 51, y + 16, x + 101, y + 16); // Draw arrow
                g.drawLine(x + 95, y + 10, x + 100, y + 15);
                g.drawLine(x + 95, y + 20, x + 100, y + 15);
            }

            x += 100; // move to the next position
            temp = temp.next;
        }
    }

    public static void main(String[] args) {    
        SinglyLinkedList sl = new SinglyLinkedList();
        sl.setVisible(true);

        // SwingUtilities.invokeLater(() -> {
        //     SinglyLinkedList sl = new SinglyLinkedList();
        //     sl.setVisible(true);
        // });
    }
}