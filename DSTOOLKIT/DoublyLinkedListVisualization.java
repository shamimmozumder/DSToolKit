import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

 class Node 
{
    int data;
    Node next;
    Node prev;

     Node(int data) 
    {
        this.data = data;
        this.next = null;
        this.prev = null;
        
    }
}

 class DLinkedList 
{
    private Node head;
    private Node tail;

    public DLinkedList() 
    {
        head = null;
        tail = null;
    }

    // Insert at Tail
    public void insertAtTail(int data) 
    {
        Node newNode = new Node(data);
        if (head == null) 
        {
            head = newNode;
            tail = newNode;
        } 
        else 
        {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    // Insert at Head
    public void insertAtHead(int data) 
    {
        Node newNode = new Node(data);
        if (head == null) 
        {
            head = newNode;
            tail = newNode;
        } 
        else 
        {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
    }

    // Insert at Position
    public void insertPosition(int data, int pos) 
    {
        Node newNode = new Node(data);
        Node current = head;

        if (pos == 0) 
        {
            insertAtHead(data);
            return;
        }

        for (int i = 1; i <= pos - 1; i++) {
            if (current != null) 
            {
                current = current.next;
            } 
            else 
            {
                return; // Position out of bounds
            }
        }

        if (current == null || current.next == null) 
        {
            insertAtTail(data);
        } 
        else 
        {
            newNode.next = current.next;
            newNode.prev = current;
            current.next.prev = newNode;
            current.next = newNode;
        }
    }

    // Delete Head
    public void deleteHead() 
    {
        if (head == null) 
        {
            return;
        }
        if (head == tail) 
        {
            head = tail = null;
        } 
        else 
        {
            head = head.next;
            head.prev = null;
        }
    }

    // Delete at Position
    public void deletePosition(int pos) 
    {
        if (head == null) 
        {
            return;
        }

        if (pos == 0) 
        {
            deleteHead();
            return;
        }

        Node current = head;
        for (int i = 1; i <= pos - 1; i++) 
        {
            if (current != null) 
            {
                current = current.next;
            } 
            else 
            {
                return; // Position out of bounds
            }
        }

        if (current != null && current.next != null) 
        {
            if (current.next == tail) 
            {
                deleteTail();
            } 
            else 
            {
                current.next = current.next.next;
                current.next.prev=current; 

                // if (current.next != null) 
                // {
                //     current.next.prev = current;
                // }
            }
        }
    }

    // Delete Tail
    public void deleteTail() 
    {
        if (tail == null) 
        {
            return;
        }
        if (head == tail) 
        {
            head = tail = null;
        } 
        else 
        {
            tail = tail.prev;

            if (tail != null) 
            {
                tail.next = null;
            }
        }
    }

    public Node getHead() 
    {
        return head;
    }

    public Node getTail() 
    {
        return tail;
    }
}

public class DoublyLinkedListVisualization extends JFrame {
    private DLinkedList linkedList;
    private JTextField inputField, posField;
    private JButton insertButton, deleteButton, insertHeadButton, insertPositionButton, deleteHeadButton, deletePositionButton, deleteTailButton,backButton;
    private JPanel drawPanel;
    private Font f;
    private JLabel inputLabel, posLabel;

    public DoublyLinkedListVisualization() 
    {
        linkedList = new DLinkedList();

        setTitle("Doubly Linked List Visualization");
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
        insertButton.setBackground(Color.WHITE);
        insertButton.setForeground(Color.RED);

        insertHeadButton = new JButton("Insert Head");
        insertHeadButton.setFont(f);
        insertHeadButton.setBackground(Color.LIGHT_GRAY);
        insertHeadButton.setForeground(Color.BLUE);

        insertPositionButton = new JButton("Insert Position");
        insertPositionButton.setFont(f);
        insertPositionButton.setBackground(Color.ORANGE);
        insertPositionButton.setForeground(Color.BLACK);

        deleteButton = new JButton("Delete Head");
        deleteButton.setFont(f);
        deleteButton.setBackground(Color.BLUE);
        deleteButton.setForeground(Color.GREEN);

        deletePositionButton = new JButton("Delete Position");
        deletePositionButton.setFont(f);
        deletePositionButton.setBackground(Color.MAGENTA);
        deletePositionButton.setForeground(Color.BLACK);

        deleteTailButton = new JButton("Delete Tail");
        deleteTailButton.setFont(f);
        deleteTailButton.setBackground(Color.YELLOW);
        deleteTailButton.setForeground(Color.BLACK);

        backButton = new JButton("Back");
        backButton.setFont(f);
        backButton.setBackground(Color.RED);
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
        controlPanel.add(deleteTailButton);
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
                drawList(g);
            }
        };
        drawPanel.setBackground(Color.WHITE);
        add(drawPanel, BorderLayout.CENTER);

        // Add action listeners
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try
                {
                    int value = Integer.parseInt(inputField.getText());
                    linkedList.insertAtTail(value);
                    drawPanel.repaint();
                    inputField.setText("");
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(DoublyLinkedListVisualization.this, "Enter a valid integer value.");
                }
            }
        });

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
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(DoublyLinkedListVisualization.this, "Enter a valid integer value.");
                }
            }
        });

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
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(DoublyLinkedListVisualization.this, "Enter valid integer value and position.");
                }
            }
        });

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
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(DoublyLinkedListVisualization.this, "No Node Found.");
                }
            }
        });

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
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(DoublyLinkedListVisualization.this, "Enter Valid Position.");
                }
            }
        });

        deleteTailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try
                {
                    linkedList.deleteTail();
                    drawPanel.repaint();
                    inputField.setText("");
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(DoublyLinkedListVisualization.this, "No Node Found.");
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
                    Llistfrontpage ls = new Llistfrontpage();
                    ls.setVisible(true);  
                }  
            }
           });
    }

    // Draw the doubly linked list with forward and backward arrows and show Head and Tail labels
    private void drawList(Graphics g) 
    {
        Node current = linkedList.getHead();
        Node tail = linkedList.getTail();
        int x = 100;
        int y = 300;

        while (current != null) 
        {
            // Draw node
            g.setColor(Color.WHITE);
            g.setFont(f);
            g.fillRect(x, y - 20, 50, 40);
            g.setColor(Color.BLUE);
            g.drawRect(x, y - 20, 50, 40);
            g.drawString(String.valueOf(current.data), x + 20, y);

            // Draw Head label if this node is the head
           
            if (current == linkedList.getHead()) 
            {
                g.setColor(Color.RED);
                g.drawString("Head", x + 10, y - 30);
            }

            // Draw Tail label if this node is the tail
            if (current == linkedList.getTail()) 
            {
                g.setColor(Color.red);
                g.drawString("Tail", x + 10, y + 40);
            }

            // Draw forward arrow
            if (current.next != null) 
            {
                g.drawLine(x + 50, y, x + 70, y);  // Forward line
                g.drawLine(x + 65, y - 5, x + 70, y);  // Forward arrow head
                g.drawLine(x + 65, y + 5, x + 70, y);
            }


            // Move to the next position
            x += 70;
            current = current.next;
            
        }
    }

    public static void main(String[] args) {
         DoublyLinkedListVisualization dl = new DoublyLinkedListVisualization();
         dl.setVisible(true);

        
    }
}
