import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BSTVisualization extends JFrame implements ActionListener {
    // Tree Root Node.
    private Node root;
    private JPanel topPanel, treePanel, infoPanel;
    private JPanel topLeftPanel, topRightPanel;
    private JButton btnAdd, btnSearch;
    private JTextField tf;
    private int X = 300, Y = 75;
    private Graphics2D g2;
    private Rectangle size;
    private JLabel labelInorder, labelPreorder, labelPostorder;
    private JLabel ansInorder, ansPreorder, ansPostorder;
    private FontMetrics fontMatrix;

    // Node Structure
    private static class Node {
        static int TEXT_WIDTH = 40;
        static int TEXT_HEIGHT = 40;

        JLabel data;
        Node left;
        Node right;
        Points p;

        Node(int info) {
            data = new JLabel(info + "", SwingConstants.CENTER);
            data.setFont(new Font("Arial", Font.BOLD, 20));
            data.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
            data.setOpaque(true);
            data.setBackground(Color.ORANGE);
            p = null;
        }
    }

    // Points structure
    private static class Points {
        int x1 = 0, x2 = 0, y2 = 0, y1 = 0;

        Points(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.x2 = x2;
            this.y2 = y2;
            this.y1 = y1;
        }

        public String toString() {
            return "x1 = " + x1 + ", y1 = " + y1 + ", x2 = " + x2 + ", y2 = " + y2;
        }
    }

    public void paint(Graphics g) {
        super.paintComponents(g);
        g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3.0f));

        Stack<Node> s = new Stack<>();
        Node curr = root;
        Points pts;
        int offset = topPanel.getBounds().height;
        while (!s.isEmpty() || curr != null) {
            while (curr != null) {
                s.push(curr);
                curr = curr.left;
            }
            if (!s.isEmpty())
                curr = s.pop();
            pts = curr.p;
            g2.drawLine(pts.x1 + 7, pts.y1 + 30 + offset, pts.x2 + 3, pts.y2 + 10 + offset);
            curr = curr.right;
        }
    }

    public BSTVisualization() {
        // Initialize the frame.
        initialize();
    }

    private void initialize() {
        setSize(1300, 700); // frame size

        size = getBounds();
        X = size.width / 2;

        topPanel = new JPanel(new BorderLayout());
        Rectangle top = topPanel.getBounds();

        topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.add(topLeftPanel, BorderLayout.WEST);

        topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(topRightPanel, BorderLayout.EAST);

        treePanel = new JPanel(null);
        treePanel.setPreferredSize(new Dimension(size.width, size.height - 300));

        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setPreferredSize(new Dimension(size.width, 200));

        // For getting data.
        tf = new JTextField("");
        tf.setFont(new Font("Arial", Font.BOLD, 20));
        tf.setPreferredSize(new Dimension(150, 30));
        tf.setHorizontalAlignment(JTextField.CENTER);

        topRightPanel.add(tf);

        // Add Button
        btnAdd = new JButton("Insert");
        btnAdd.setFont(new Font("Arial", Font.BOLD, 20));
        btnAdd.setBackground(Color.YELLOW);
        btnAdd.setForeground(Color.RED);
        btnAdd.addActionListener(this);
        topRightPanel.add(btnAdd);

        // Search Button
        btnSearch = new JButton("Search");
        btnSearch.setFont(new Font("Arial", Font.BOLD, 20));
        btnSearch.setBackground(Color.BLACK);
        btnSearch.setForeground(Color.RED);
        btnSearch.addActionListener(this);
        topRightPanel.add(btnSearch);

        // Inorder label
        labelInorder = new JLabel("Inorder :");
        labelInorder.setFont(new Font("Times New Roman", Font.BOLD, 20));
        infoPanel.add(labelInorder);
        infoPanel.add(Box.createRigidArea(new Dimension(7, 5)));

        // Inorder traversal answer
        ansInorder = new JLabel("BST is empty.");
        ansInorder.setFont(new Font("Arial", Font.PLAIN, 18));
        infoPanel.add(ansInorder);

        infoPanel.add(Box.createRigidArea(new Dimension(7, 15)));

        // Preorder label
        labelPreorder = new JLabel("Preorder :");
        labelPreorder.setFont(new Font("Times New Roman", Font.BOLD, 20));
        infoPanel.add(labelPreorder);

        infoPanel.add(Box.createRigidArea(new Dimension(7, 5)));

        // Preorder traversal answer
        ansPreorder = new JLabel("BST is empty.");
        ansPreorder.setFont(new Font("Arial", Font.PLAIN, 18));
        infoPanel.add(ansPreorder);

        infoPanel.add(Box.createRigidArea(new Dimension(7, 15)));

        // Postorder label
        labelPostorder = new JLabel("Postorder :");
        labelPostorder.setFont(new Font("Times New Roman", Font.BOLD, 20));
        infoPanel.add(labelPostorder);

        // Postorder traversal answer
        ansPostorder = new JLabel("BST is empty.");
        ansPostorder.setFont(new Font("Arial", Font.PLAIN, 18));
        infoPanel.add(ansPostorder);

        tf.requestFocusInWindow();

        add(topPanel, BorderLayout.NORTH);
        add(treePanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);

        setTitle("Binary Search Tree Visualization"); // Title Frame
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (tf.isEnabled()) {
            try {
                int data = Integer.parseInt(tf.getText());
                if (evt.getSource() == btnAdd) {
                    add(data);
                } else if (evt.getSource() == btnSearch) {
                    search(data);
                }

                tf.setText("");
                tf.requestFocusInWindow();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Please Enter Integer.");
            }
        }
    }

    // Add element in BST.
    public void add(int info) {
        Node newNode = new Node(info);
        int width = getWidth(newNode);

        if (root == null) {
            root = newNode;
            newNode.data.setBounds(treePanel.getBounds().width / 2, 10, width, 40);
            newNode.p = new Points(0, 0, 0, 0);
        } else {
            Node curr = root, pre = root;
            int currData;
            X = treePanel.getBounds().width / 2;
            while (curr != null) {
                pre = curr;
                currData = Integer.parseInt(curr.data.getText());
                if (info == currData) {
                    JOptionPane.showMessageDialog(null, info + " already exists.");
                    return;
                } else if (currData > info) {
                    curr = curr.left; 
                } else {
                    curr = curr.right;
                }
                X /= 2;
            }

            currData = Integer.parseInt(pre.data.getText());
            int x = pre.data.getX();
            int y = pre.data.getY();
            Dimension preDimension = pre.data.getSize();
            Dimension currDimension = new Dimension(width, Node.TEXT_HEIGHT);
            //Node connection
            if (currData > info) {
                pre.left = newNode;
                newNode.data.setBounds(x - X, y + Y, width, 40);
                newNode.p = new Points(x, y + preDimension.height / 2, x - X + currDimension.width / 2,
                        y + Y + currDimension.height / 2);
            } else {
                pre.right = newNode;
                newNode.data.setBounds(x + X, y + Y, width, 40);
                newNode.p = new Points(x + preDimension.width, y + preDimension.height / 2,
                        x + X + currDimension.width / 2, y + Y + currDimension.height / 2);
            }
        }

        // Add the new node to the tree panel
        treePanel.add(newNode.data);
        treePanel.validate();
        treePanel.repaint();
        validate();
        repaint();

        // Update the traversal displays
        updateTraversalDisplays();
    }

    public void search(int info) {
        Node curr = root;
        boolean found = false;

        // Traverse the tree to find the node
        while (curr != null) {
            int currData = Integer.parseInt(curr.data.getText());
            if (currData == info) {
                // Node found, highlight it
                curr.data.setBackground(Color.GREEN);
                found = true;
                break;
            } else if (currData > info) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(null, info + " not found in BST.");
        }
    }

    private int getWidth(Node node) {
        fontMatrix = node.data.getFontMetrics(node.data.getFont());
        return Math.max(Node.TEXT_WIDTH, fontMatrix.stringWidth(node.data.getText()) + 20);
    }

    private void updateTraversalDisplays() {
        if (root == null) {
            ansInorder.setText("BST is empty.");
            ansPreorder.setText("BST is empty.");
            ansPostorder.setText("BST is empty.");
        } else {
            ansInorder.setText(inorder(root).trim());
            ansPreorder.setText(preorder(root).trim());
            ansPostorder.setText(postorder(root).trim());
        }
    }

    // Inorder logic
    public String inorder(Node root) {
        if (root == null)
            return "";
        return inorder(root.left) + root.data.getText() + " " + inorder(root.right);
    }

    // Preorder logic
    public String preorder(Node root) {
        if (root == null)
            return "";
        return root.data.getText() + " " + preorder(root.left) + preorder(root.right);
    }

    // Postorder logic
    public String postorder(Node root) {
        if (root == null)
            return "";
        return postorder(root.left) + postorder(root.right) + root.data.getText() + " ";
    }

    public static void main(String[] args) {
        BSTVisualization bv= new BSTVisualization();
    }
}
