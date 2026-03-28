import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

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
    private javax.swing.Timer animationTimer;
    private boolean isAnimating = false;

    // Node Structure
    private static class Node {
        static int TEXT_WIDTH = 40;
        static int TEXT_HEIGHT = 40;

        NodePanel data;
        Node left;
        Node right;
        Points p;
        boolean isRoot = false;
        Color nodeColor;

        Node(int info, boolean isRoot) {
            this.isRoot = isRoot;
            nodeColor = isRoot ? new Color(255, 100, 100) : new Color(100, 200, 255);
            data = new NodePanel(info + "", nodeColor);
            p = null;
        }
    }

    // Custom panel for nodes with rounded corners and gradient
    private static class NodePanel extends JPanel {
        private String text;
        private Color bgColor;

        public NodePanel(String text, Color bgColor) {
            this.text = text;
            this.bgColor = bgColor;
            setOpaque(false);
            setFont(new Font("Arial", Font.BOLD, 20));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, width, height, 20, 20);

            // Gradient background
            GradientPaint gradient = new GradientPaint(0, 0, bgColor.brighter(), 0, height, bgColor.darker());
            g2d.setPaint(gradient);
            g2d.fill(roundedRectangle);

            // Border
            g2d.setColor(bgColor.darker());
            g2d.setStroke(new BasicStroke(2));
            g2d.draw(roundedRectangle);

            // Text
            g2d.setColor(Color.WHITE);
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getHeight();
            int x = (width - textWidth) / 2;
            int y = (height + textHeight / 2) / 2;
            g2d.drawString(text, x, y);

            g2d.dispose();
        }

        public void setText(String text) {
            this.text = text;
            repaint();
        }

        public String getText() {
            return text;
        }

        public void setBgColor(Color bgColor) {
            this.bgColor = bgColor;
            repaint();
        }

        public Color getBgColor() {
            return bgColor;
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
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

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
            if (pts != null && pts.x1 != 0 && pts.y1 != 0) {
                // Draw gradient line
                GradientPaint gradient = new GradientPaint(pts.x1 + 7, pts.y1 + 30 + offset, Color.BLUE,
                        pts.x2 + 3, pts.y2 + 10 + offset, Color.CYAN);
                g2.setPaint(gradient);
                g2.drawLine(pts.x1 + 7, pts.y1 + 30 + offset, pts.x2 + 3, pts.y2 + 10 + offset);
            }
            curr = curr.right;
        }

        // Draw root indicator
        if (root != null) {
            g2.setColor(Color.RED);
            g2.setFont(new Font("Arial", Font.BOLD, 16));
            FontMetrics fm = g2.getFontMetrics();
            String rootText = "ROOT";
            int textWidth = fm.stringWidth(rootText);
            int x = root.data.getX() + root.data.getWidth() / 2 - textWidth / 2;
            int y = root.data.getY() - 10;
            g2.drawString(rootText, x, y);
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
        if (isAnimating) return; // Prevent adding during animation

        Node newNode = new Node(info, false);
        int width = getWidth(newNode);

        if (root == null) {
            root = newNode;
            newNode.isRoot = true;
            newNode.nodeColor = new Color(255, 100, 100);
            newNode.data.setBgColor(newNode.nodeColor);
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
            int finalX, finalY;
            if (currData > info) {
                pre.left = newNode;
                finalX = x - X;
                finalY = y + Y;
                newNode.p = new Points(x, y + preDimension.height / 2, finalX + currDimension.width / 2,
                        finalY + currDimension.height / 2);
            } else {
                pre.right = newNode;
                finalX = x + X;
                finalY = y + Y;
                newNode.p = new Points(x + preDimension.width, y + preDimension.height / 2,
                        finalX + currDimension.width / 2, finalY + currDimension.height / 2);
            }

            // Start animation from parent position
            newNode.data.setBounds(x + preDimension.width / 2 - width / 2, y + preDimension.height / 2 - 20, width, 40);
            animateNode(newNode, finalX, finalY);
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

    private void animateNode(Node node, int finalX, int finalY) {
        isAnimating = true;
        int startX = node.data.getX();
        int startY = node.data.getY();
        int steps = 20;
        int delay = 50; // milliseconds

        animationTimer = new javax.swing.Timer(delay, new ActionListener() {
            int step = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                step++;
                double progress = (double) step / steps;
                int currentX = (int) (startX + (finalX - startX) * progress);
                int currentY = (int) (startY + (finalY - startY) * progress);

                node.data.setBounds(currentX, currentY, node.data.getWidth(), node.data.getHeight());
                treePanel.repaint();
                repaint();

                if (step >= steps) {
                    animationTimer.stop();
                    isAnimating = false;
                    node.data.setBounds(finalX, finalY, node.data.getWidth(), node.data.getHeight());
                    treePanel.repaint();
                    repaint();
                }
            }
        });
        animationTimer.start();
    }

    public void search(int info) {
        // Reset all node colors first
        resetNodeColors(root);

        Node curr = root;
        boolean found = false;

        // Traverse the tree to find the node
        while (curr != null) {
            int currData = Integer.parseInt(curr.data.getText());
            if (currData == info) {
                // Node found, highlight it with animation
                animateHighlight(curr, Color.GREEN);
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

    private void resetNodeColors(Node node) {
        if (node == null) return;
        node.data.setBgColor(node.nodeColor);
        resetNodeColors(node.left);
        resetNodeColors(node.right);
    }

    private void animateHighlight(Node node, Color highlightColor) {
        Color originalColor = node.data.getBgColor();
        javax.swing.Timer highlightTimer = new javax.swing.Timer(100, new ActionListener() {
            int count = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                count++;
                if (count % 2 == 1) {
                    node.data.setBgColor(highlightColor);
                } else {
                    node.data.setBgColor(originalColor);
                }
                if (count >= 6) { // Blink 3 times
                    ((javax.swing.Timer) e.getSource()).stop();
                    node.data.setBgColor(highlightColor);
                }
            }
        });
        highlightTimer.start();
    }

    private int getWidth(Node node) {
        Font font = node.data.getFont();
        fontMatrix = getFontMetrics(font);
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
