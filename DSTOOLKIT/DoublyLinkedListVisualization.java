import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.Map;

class DNode {
    int data;
    DNode next;
    DNode prev;
    String address; // Simulated memory address

    DNode(int data) {
        this.data = data;
        this.next = null;
        this.prev = null;
        this.address = "0x" + Integer.toHexString(hashCode()).toUpperCase();
    }
}

class DLinkedList {
    private DNode head;
    private DNode tail;

    public DLinkedList() {
        head = null;
        tail = null;
    }

    // Insert at Tail
    public void insertAtTail(int data) {
        DNode newNode = new DNode(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    // Insert at Head
    public void insertAtHead(int data) {
        DNode newNode = new DNode(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
    }

    // Insert at Position
    public void insertPosition(int data, int pos) {
        DNode newNode = new DNode(data);
        DNode current = head;

        if (pos == 0) {
            insertAtHead(data);
            return;
        }

        for (int i = 1; i <= pos - 1; i++) {
            if (current != null) {
                current = current.next;
            } else {
                return; // Position out of bounds
            }
        }

        if (current == null || current.next == null) {
            insertAtTail(data);
        } else {
            newNode.next = current.next;
            newNode.prev = current;
            current.next.prev = newNode;
            current.next = newNode;
        }
    }

    // Delete Head
    public void deleteHead() {
        if (head == null) {
            return;
        }
        if (head == tail) {
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
    }

    // Delete at Position
    public void deletePosition(int pos) {
        if (head == null) {
            return;
        }

        if (pos == 0) {
            deleteHead();
            return;
        }

        DNode current = head;
        for (int i = 1; i <= pos - 1; i++) {
            if (current != null) {
                current = current.next;
            } else {
                return; // Position out of bounds
            }
        }

        if (current != null && current.next != null) {
            if (current.next == tail) {
                deleteTail();
            } else {
                current.next = current.next.next;
                if (current.next != null) {
                    current.next.prev = current;
                }
            }
        }
    }

    // Delete Tail
    public void deleteTail() {
        if (tail == null) {
            return;
        }
        if (head == tail) {
            head = tail = null;
        } else {
            tail = tail.prev;
            if (tail != null) {
                tail.next = null;
            }
        }
    }

    // Search for a value
    public DNode search(int value) {
        DNode current = head;
        while (current != null) {
            if (current.data == value) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    public DNode getHead() {
        return head;
    }

    public DNode getTail() {
        return tail;
    }
}

public class DoublyLinkedListVisualization extends JFrame implements ActionListener {
    private DLinkedList linkedList;
    private JTextField inputField, posField, searchField;
    private JButton insertButton, deleteButton, insertHeadButton, insertPositionButton,
                    deleteHeadButton, deletePositionButton, deleteTailButton, backButton, searchButton;
    private JPanel drawPanel, controlPanel;
    private Font f;
    private JLabel inputLabel, posLabel, searchLabel;
    private javax.swing.Timer animationTimer;
    private boolean isAnimating = false;
    private DNode highlightedNode = null;
    private Map<DNode, Point> nodePositions = new HashMap<>();
    private int animationStep = 0;
    private final int ANIMATION_STEPS = 20;

    public DoublyLinkedListVisualization() {
        linkedList = new DLinkedList();

        setTitle("Doubly Linked List Visualization");
        setSize(1550, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 248, 255)); // Alice blue background

        f = new Font("Arial", Font.BOLD, 16);

        // Control panel with beautiful design
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2, 7, 10, 10));
        controlPanel.setBackground(new Color(70, 130, 180)); // Steel blue
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Input fields with styling
        inputField = createStyledTextField();
        posField = createStyledTextField();
        searchField = createStyledTextField();

        // Beautiful buttons
        insertButton = createStyledButton("Insert Tail", new Color(34, 139, 34), Color.WHITE);
        insertHeadButton = createStyledButton("Insert Head", new Color(25, 25, 112), Color.WHITE);
        insertPositionButton = createStyledButton("Insert Position", new Color(255, 140, 0), Color.BLACK);
        deleteHeadButton = createStyledButton("Delete Head", new Color(220, 20, 60), Color.WHITE);
        deletePositionButton = createStyledButton("Delete Position", new Color(138, 43, 226), Color.WHITE);
        deleteTailButton = createStyledButton("Delete Tail", new Color(255, 215, 0), Color.BLACK);
        searchButton = createStyledButton("Search", new Color(0, 191, 255), Color.WHITE);
        backButton = createStyledButton("Back", new Color(105, 105, 105), Color.WHITE);

        // Labels
        inputLabel = new JLabel("Value:");
        inputLabel.setFont(f);
        inputLabel.setForeground(Color.WHITE);

        posLabel = new JLabel("Position:");
        posLabel.setFont(f);
        posLabel.setForeground(Color.WHITE);

        searchLabel = new JLabel("Search:");
        searchLabel.setFont(f);
        searchLabel.setForeground(Color.WHITE);

        // Add components to control panel
        controlPanel.add(inputLabel);
        controlPanel.add(inputField);
        controlPanel.add(insertButton);
        controlPanel.add(insertHeadButton);
        controlPanel.add(insertPositionButton);
        controlPanel.add(posLabel);
        controlPanel.add(posField);
        controlPanel.add(deleteHeadButton);
        controlPanel.add(deleteTailButton);
        controlPanel.add(deletePositionButton);
        controlPanel.add(searchLabel);
        controlPanel.add(searchField);
        controlPanel.add(searchButton);
        controlPanel.add(backButton);

        add(controlPanel, BorderLayout.NORTH);

        // Drawing panel with gradient background
        drawPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradient background
                GradientPaint gradient = new GradientPaint(0, 0, new Color(135, 206, 235), 0, getHeight(), new Color(255, 255, 255));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                drawList(g2d);
                g2d.dispose();
            }
        };
        drawPanel.setBackground(Color.WHITE);
        add(drawPanel, BorderLayout.CENTER);

        // Add action listeners
        insertButton.addActionListener(this);
        insertHeadButton.addActionListener(this);
        insertPositionButton.addActionListener(this);
        deleteHeadButton.addActionListener(this);
        deletePositionButton.addActionListener(this);
        deleteTailButton.addActionListener(this);
        searchButton.addActionListener(this);
        backButton.addActionListener(this);

        setVisible(true);
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(8);
        field.setFont(f);
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return field;
    }

    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(f);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(140, 35));
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isAnimating) return;

        try {
            if (e.getSource() == insertButton) {
                int value = Integer.parseInt(inputField.getText());
                linkedList.insertAtTail(value);
                animateInsertion();
                inputField.setText("");
            } else if (e.getSource() == insertHeadButton) {
                int value = Integer.parseInt(inputField.getText());
                linkedList.insertAtHead(value);
                animateInsertion();
                inputField.setText("");
            } else if (e.getSource() == insertPositionButton) {
                int value = Integer.parseInt(inputField.getText());
                int pos = Integer.parseInt(posField.getText());
                linkedList.insertPosition(value, pos);
                animateInsertion();
                inputField.setText("");
                posField.setText("");
            } else if (e.getSource() == deleteHeadButton) {
                linkedList.deleteHead();
                animateDeletion();
            } else if (e.getSource() == deletePositionButton) {
                int pos = Integer.parseInt(posField.getText());
                linkedList.deletePosition(pos);
                animateDeletion();
                posField.setText("");
            } else if (e.getSource() == deleteTailButton) {
                linkedList.deleteTail();
                animateDeletion();
            } else if (e.getSource() == searchButton) {
                int value = Integer.parseInt(searchField.getText());
                highlightedNode = linkedList.search(value);
                if (highlightedNode == null) {
                    JOptionPane.showMessageDialog(this, "Value not found in the list.");
                } else {
                    animateSearch();
                }
                searchField.setText("");
            } else if (e.getSource() == backButton) {
                dispose();
                Llistfrontpage ls = new Llistfrontpage();
                ls.setVisible(true);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid integer values.");
        }
    }

    private void animateInsertion() {
        isAnimating = true;
        animationStep = 0;
        animationTimer = new javax.swing.Timer(50, e -> {
            animationStep++;
            drawPanel.repaint();
            if (animationStep >= ANIMATION_STEPS) {
                animationTimer.stop();
                isAnimating = false;
                highlightedNode = null;
            }
        });
        animationTimer.start();
    }

    private void animateDeletion() {
        isAnimating = true;
        animationStep = ANIMATION_STEPS;
        animationTimer = new javax.swing.Timer(50, e -> {
            animationStep--;
            drawPanel.repaint();
            if (animationStep <= 0) {
                animationTimer.stop();
                isAnimating = false;
                highlightedNode = null;
            }
        });
        animationTimer.start();
    }

    private void animateSearch() {
        isAnimating = true;
        animationStep = 0;
        animationTimer = new javax.swing.Timer(100, e -> {
            animationStep++;
            drawPanel.repaint();
            if (animationStep >= 10) {
                animationTimer.stop();
                isAnimating = false;
            }
        });
        animationTimer.start();
    }

    // Draw the doubly linked list with forward and backward arrows and show Head and Tail labels
    private void drawList(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        DNode current = linkedList.getHead();
        int x = 150;
        int y = 300;
        int nodeIndex = 0;

        nodePositions.clear();

        while (current != null) {
            nodePositions.put(current, new Point(x, y));

            // Calculate animation scale
            float scale = 1.0f;
            if (isAnimating && animationStep < ANIMATION_STEPS) {
                if (nodeIndex == 0) { // First node animation
                    scale = (float) animationStep / ANIMATION_STEPS;
                }
            }

            int nodeWidth = (int) (80 * scale);
            int nodeHeight = (int) (50 * scale);

            // Draw node with rounded rectangle
            RoundRectangle2D nodeRect = new RoundRectangle2D.Float(x - nodeWidth/2, y - nodeHeight/2, nodeWidth, nodeHeight, 15, 15);

            // Node color
            Color nodeColor = new Color(100, 149, 237); // Cornflower blue
            if (current == highlightedNode) {
                nodeColor = Color.YELLOW;
            } else if (current == linkedList.getHead()) {
                nodeColor = new Color(255, 69, 0); // Red orange for head
            } else if (current == linkedList.getTail()) {
                nodeColor = new Color(0, 128, 0); // Green for tail
            }

            g2d.setColor(nodeColor);
            g2d.fill(nodeRect);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.draw(nodeRect);

            // Draw data
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            FontMetrics fm = g2d.getFontMetrics();
            String dataStr = String.valueOf(current.data);
            int dataWidth = fm.stringWidth(dataStr);
            g2d.drawString(dataStr, x - dataWidth/2, y + fm.getAscent()/2 - 2);

            // Draw address below node
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.PLAIN, 10));
            String addrStr = current.address;
            int addrWidth = g2d.getFontMetrics().stringWidth(addrStr);
            g2d.drawString(addrStr, x - addrWidth/2, y + nodeHeight/2 + 15);

            // Draw Head/Tail labels
            if (current == linkedList.getHead()) {
                g2d.setColor(Color.RED);
                g2d.setFont(new Font("Arial", Font.BOLD, 14));
                g2d.drawString("HEAD", x - 20, y - nodeHeight/2 - 10);
            }
            if (current == linkedList.getTail()) {
                g2d.setColor(Color.GREEN);
                g2d.setFont(new Font("Arial", Font.BOLD, 14));
                g2d.drawString("TAIL", x - 20, y + nodeHeight/2 + 35);
            }

            // Draw forward arrow (next) - positioned in the middle between nodes
            if (current.next != null) {
                int midX = x + 60; // Middle point between current node and next node
                int arrowY = y - 10; // Position above the node
                int arrowLength = 20; // Short arrow length
                g2d.setColor(Color.BLUE);
                g2d.setStroke(new BasicStroke(5)); // Bold arrow
                // Draw arrow line
                g2d.drawLine(midX - arrowLength/2, arrowY, midX + arrowLength/2, arrowY);
                // Arrow head pointing right
                g2d.drawLine(midX + arrowLength/2 - 8, arrowY - 5, midX + arrowLength/2, arrowY);
                g2d.drawLine(midX + arrowLength/2 - 8, arrowY + 5, midX + arrowLength/2, arrowY);
                // Label
                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("Arial", Font.BOLD, 12));
                g2d.drawString("next", midX - 15, arrowY - 8);
            }

            // Draw backward arrow (prev) - positioned in the middle between nodes
            if (current.prev != null) {
                int midX = x - 60; // Middle point between current node and previous node
                int arrowY = y + 30; // Position below the node
                int arrowLength = 20; // Short arrow length
                g2d.setColor(Color.RED);
                g2d.setStroke(new BasicStroke(5)); // Bold arrow
                // Draw arrow line
                g2d.drawLine(midX + arrowLength/2, arrowY, midX - arrowLength/2, arrowY);
                // Arrow head pointing left
                g2d.drawLine(midX - arrowLength/2 + 8, arrowY - 5, midX - arrowLength/2, arrowY);
                g2d.drawLine(midX - arrowLength/2 + 8, arrowY + 5, midX - arrowLength/2, arrowY);
                // Label
                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("Arial", Font.BOLD, 12));
                g2d.drawString("prev", midX - 15, arrowY - 8);
            }

            x += 120;
            current = current.next;
            nodeIndex++;
        }
    }

    public static void main(String[] args) {
         DoublyLinkedListVisualization dl = new DoublyLinkedListVisualization();
         dl.setVisible(true);

        
    }
}
