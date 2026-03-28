import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Random;

class SNode {
    int data;
    SNode next;
    String address;

    public SNode(int data) {
        this.data = data;
        this.next = null;
        this.address = generateAddress();
    }

    private String generateAddress() {
        Random rand = new Random();
        return "0x" + Integer.toHexString(rand.nextInt(0xFFFF) + 0x1000).toUpperCase();
    }
}

class LinkedList {
    private SNode head;

    public LinkedList() {
        head = null;
    }

    public void insertAtTail(int data) {
        SNode newNode = new SNode(data);
        if (head == null) head = newNode;
        else {
            SNode temp = head;
            while (temp.next != null) temp = temp.next;
            temp.next = newNode;
        }
    }

    public void insertAtHead(int data) {
        SNode newNode = new SNode(data);
        newNode.next = head;
        head = newNode;
    }

    public void insertPosition(int data, int pos) {
        if (pos == 0) { insertAtHead(data); return; }
        SNode newNode = new SNode(data);
        SNode temp = head;
        for (int i = 1; i <= pos - 1; i++) if (temp != null) temp = temp.next;
        if (temp != null) {
            newNode.next = temp.next;
            temp.next = newNode;
        }
    }

    public void deleteHead() { if (head != null) head = head.next; }

    public void deletePosition(int pos) {
        if (head == null) return;
        if (pos == 0) { deleteHead(); return; }
        SNode temp = head;
        for (int i = 1; i <= pos - 1; i++) if (temp != null) temp = temp.next;
        if (temp != null && temp.next != null) temp.next = temp.next.next;
    }

    public SNode search(int value) {
        SNode temp = head;
        while (temp != null) { if (temp.data == value) return temp; temp = temp.next; }
        return null;
    }

    public SNode getHead() { return head; }

    public SNode getTail() {
        SNode temp = head;
        if (temp == null) return null;
        while (temp.next != null) temp = temp.next;
        return temp;
    }
}

public class SinglyLinkedList extends JFrame {
    private LinkedList linkedList;
    private JTextField inputField, posField, searchField;
    private JButton insertButton, insertHeadButton, insertPositionButton, deleteHeadButton, deletePositionButton, searchButton, backButton;
    private JPanel drawPanel, controlPanel;
    private Font f;
    private JLabel inputLabel, posLabel, searchLabel;
    private javax.swing.Timer animationTimer;
    private boolean isAnimating = false;
    private SNode searchNode = null;
    private int animationStep = 0;
    private final int ANIMATION_STEPS = 20;

    public SinglyLinkedList() {
        linkedList = new LinkedList();
        setTitle("Singly Linked List Visualization");
        setSize(1550, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 248, 255));

        f = new Font("Arial", Font.BOLD, 16);

        // Control panel
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2, 7, 10, 10));
        controlPanel.setBackground(new Color(70, 130, 180));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Input fields (bigger width)
        inputField = createStyledTextField();
        posField = createStyledTextField();
        searchField = createStyledTextField();

        // Buttons with uniform width
        insertButton = createStyledButton("Insert Tail", Color.YELLOW, Color.RED);
        insertHeadButton = createStyledButton("Insert Head", Color.LIGHT_GRAY, Color.BLUE);
        insertPositionButton = createStyledButton("Insert Position", Color.ORANGE, Color.BLACK);
        deleteHeadButton = createStyledButton("Delete Head", Color.BLUE, Color.GREEN);
        deletePositionButton = createStyledButton("Delete Position", Color.MAGENTA, Color.BLACK);
        searchButton = createStyledButton("Search", Color.CYAN, Color.DARK_GRAY);
        backButton = createStyledButton("Back", Color.RED, Color.ORANGE);

        // Labels
        inputLabel = createStyledLabel("Value:");
        posLabel = createStyledLabel("Position:");
        searchLabel = createStyledLabel("Search:");

        // Add components to control panel
        controlPanel.add(inputLabel);
        controlPanel.add(inputField);
        controlPanel.add(insertButton);
        controlPanel.add(insertHeadButton);
        controlPanel.add(insertPositionButton);
        controlPanel.add(posLabel);
        controlPanel.add(posField);
        controlPanel.add(deleteHeadButton);
        controlPanel.add(deletePositionButton);
        controlPanel.add(searchLabel);
        controlPanel.add(searchField);
        controlPanel.add(searchButton);
        controlPanel.add(backButton);

        add(controlPanel, BorderLayout.NORTH);

        // Drawing panel
        drawPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gradient = new GradientPaint(0, 0, new Color(135, 206, 250), 0, getHeight(), new Color(255, 255, 255));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                drawLinkedList(g2d);
                g2d.dispose();
            }
        };
        add(drawPanel, BorderLayout.CENTER);

        // Action listeners
        setupActionListeners();

        setVisible(true);
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(10);
        field.setFont(f);
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return field;
    }

    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton button = new JButton(text);
        button.setFont(f);
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setPreferredSize(new Dimension(140, 35)); // uniform width
        return button;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(f);
        label.setForeground(Color.WHITE);
        return label;
    }

    private void setupActionListeners() {
        insertButton.addActionListener(e -> handleInsertTail());
        insertHeadButton.addActionListener(e -> handleInsertHead());
        insertPositionButton.addActionListener(e -> handleInsertPosition());
        deleteHeadButton.addActionListener(e -> handleDeleteHead());
        deletePositionButton.addActionListener(e -> handleDeletePosition());
        searchButton.addActionListener(e -> handleSearch());
        backButton.addActionListener(e -> handleBack());
    }

    private void handleInsertTail() {
        try { int val = Integer.parseInt(inputField.getText()); linkedList.insertAtTail(val); animateInsertion(); inputField.setText(""); }
        catch(Exception ex) { JOptionPane.showMessageDialog(this,"Enter valid integer."); }
    }
    private void handleInsertHead() {
        try { int val = Integer.parseInt(inputField.getText()); linkedList.insertAtHead(val); animateInsertion(); inputField.setText(""); }
        catch(Exception ex) { JOptionPane.showMessageDialog(this,"Enter valid integer."); }
    }
    private void handleInsertPosition() {
        try { int val = Integer.parseInt(inputField.getText()); int pos = Integer.parseInt(posField.getText());
            linkedList.insertPosition(val,pos); animateInsertion(); inputField.setText(""); posField.setText(""); }
        catch(Exception ex) { JOptionPane.showMessageDialog(this,"Enter valid value & position."); }
    }
    private void handleDeleteHead() { linkedList.deleteHead(); animateDeletion(); }
    private void handleDeletePosition() {
        try { int pos = Integer.parseInt(posField.getText()); linkedList.deletePosition(pos); animateDeletion(); posField.setText(""); }
        catch(Exception ex) { JOptionPane.showMessageDialog(this,"Enter valid position."); }
    }
    private void handleSearch() {
        try { int val = Integer.parseInt(searchField.getText()); searchNode = linkedList.search(val);
            if(searchNode != null) animateSearch(); else JOptionPane.showMessageDialog(this,"Value not found."); searchField.setText(""); }
        catch(Exception ex) { JOptionPane.showMessageDialog(this,"Enter valid integer."); }
    }
    private void handleBack() { dispose(); new Llistfrontpage().setVisible(true); }

    private void animateInsertion() { isAnimating=true; animationStep=0; startAnimationTimer(); }
    private void animateDeletion() { isAnimating=true; animationStep=ANIMATION_STEPS; startAnimationTimer(); }
    private void animateSearch() { isAnimating=true; animationStep=0; startAnimationTimer(); }

    private void startAnimationTimer() {
        animationTimer = new javax.swing.Timer(50,e->{
            drawPanel.repaint();
            animationStep++;
            if(animationStep>=ANIMATION_STEPS) { animationTimer.stop(); isAnimating=false; searchNode=null; }
        });
        animationTimer.start();
    }

    private void drawLinkedList(Graphics2D g2d) {
        SNode temp = linkedList.getHead();
        int x = 100, y = 300;
        SNode tail = linkedList.getTail();
        boolean isHead = true;
        while(temp != null){
            // Node rectangle
            RoundRectangle2D rect = new RoundRectangle2D.Float(x,y,80,60,20,20);
            g2d.setColor(Color.ORANGE); g2d.fill(rect);
            g2d.setColor(Color.BLACK); g2d.draw(rect);

            // Data
            g2d.setFont(new Font("Arial",Font.BOLD,16));
            g2d.drawString(String.valueOf(temp.data), x+30, y+35);

            // Address
            g2d.setFont(new Font("Arial",Font.PLAIN,10));
            g2d.drawString(temp.address, x+15, y+50);

            // Head label
            if(isHead) { g2d.setColor(Color.RED); g2d.setFont(new Font("Arial",Font.BOLD,14)); g2d.drawString("HEAD", x+20, y-10); isHead=false; }

            // Tail label
            if(temp==tail) { g2d.setColor(Color.RED); g2d.setFont(new Font("Arial",Font.BOLD,14)); g2d.drawString("TAIL", x+20, y+80); }

            // Arrow
            if(temp.next != null) {
                int startX = x+80, startY = y+30, endX = x+120;
                g2d.setStroke(new BasicStroke(4));
                g2d.drawLine(startX, startY, endX, startY);
                g2d.drawLine(endX-10, startY-8, endX, startY);
                g2d.drawLine(endX-10, startY+8, endX, startY);
            }

            temp = temp.next;
            x+=140;
        }
    }

    public static void main(String[] args) { new SinglyLinkedList().setVisible(true); }
}
