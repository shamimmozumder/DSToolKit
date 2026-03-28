import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

class StackElement {
    int value;
    double x, y;
    double targetX, targetY;
    boolean isAnimating;
    boolean isSettled;
    long startTime;
    final int ANIMATION_DURATION = 1000;
}

public class StackVisualization extends JFrame {

    private List<StackElement> elements = new ArrayList<>();
    private List<StackElement> animatingOut = new ArrayList<>();

    private JPanel drawPanel;
    private JTextField input;
    private JLabel statusbar;

    private JButton push, pop, back;

    private Font f;
    private javax.swing.Timer animationTimer;

    private boolean arrowVisible = true;
    private int arrowBlinkCounter = 0;

    private final int MAX_STACK_SIZE = 8; // Max stack size

    public StackVisualization() {
        // Setup frame
        setTitle("Stack Visualization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 248, 255));

        f = new Font("Arial", Font.BOLD, 20);

        input = new JTextField(6);
        input.setHorizontalAlignment(JTextField.CENTER);
        input.setFont(f);

        // Buttons
        push = createButton("Push", new Color(34, 139, 34));
        push.addActionListener(e -> push());

        pop = createButton("Pop", new Color(220, 20, 60));
        pop.addActionListener(e -> pop());

        back = createButton("Back", new Color(70, 130, 180));
        back.addActionListener(e -> dispose());

        statusbar = new JLabel("Stack is Empty");
        statusbar.setOpaque(true);
        statusbar.setFont(f);
        statusbar.setBackground(new Color(255, 165, 0));
        statusbar.setForeground(Color.WHITE);
        statusbar.setHorizontalAlignment(JLabel.CENTER);

        // Control panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        controlPanel.setBackground(new Color(70, 130, 180));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel valueLabel = new JLabel("Value:");
        valueLabel.setFont(f);
        valueLabel.setForeground(Color.WHITE);

        controlPanel.add(valueLabel);
        controlPanel.add(input);
        controlPanel.add(push);
        controlPanel.add(pop);
        controlPanel.add(back);

        add(controlPanel, BorderLayout.NORTH);

        // Draw panel
        drawPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                FontMetrics fm = g2d.getFontMetrics();

                // Background
                GradientPaint bg = new GradientPaint(0, 0, new Color(220, 220, 220),
                        0, getHeight(), new Color(255, 255, 255));
                g2d.setPaint(bg);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Bucket
                int bucketWidth = 200, bucketHeight = 500;
                int bucketX = (getWidth() - bucketWidth) / 2;
                int bucketY = (getHeight() - bucketHeight) / 2;

                GradientPaint bucketGrad = new GradientPaint(bucketX, bucketY,
                        new Color(255, 253, 230), bucketX, bucketY + bucketHeight, Color.WHITE);
                g2d.setPaint(bucketGrad);
                g2d.fillRoundRect(bucketX, bucketY, bucketWidth, bucketHeight, 30, 30);
                g2d.setColor(new Color(200, 200, 180, 150));
                g2d.setStroke(new BasicStroke(4));
                g2d.drawRoundRect(bucketX, bucketY, bucketWidth, bucketHeight, 30, 30);

                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("Arial", Font.BOLD, 20));
                String bucketLabel = "Stack";
                int labelX = bucketX + (bucketWidth - fm.stringWidth(bucketLabel)) / 2;
                int labelY = bucketY - 10;
                g2d.drawString(bucketLabel, labelX, labelY);

                // Draw elements
                Color[] elementColors = {new Color(34, 139, 34), new Color(255, 215, 0),
                        new Color(255, 140, 0), new Color(255, 20, 147), new Color(0, 191, 255)};
                int elementHeight = 50;
                int bottomY = bucketY + bucketHeight - elementHeight;

                for (int i = 0; i < elements.size(); i++) {
                    StackElement elem = elements.get(i);
                    int elemY = elem.isAnimating ? (int) elem.y : bottomY - i * elementHeight;
                    int elemX = (int) elem.x;

                    Color baseColor = elementColors[i % elementColors.length];
                    Color lightColor = baseColor.brighter();
                    GradientPaint grad = new GradientPaint(elemX, elemY, baseColor,
                            elemX, elemY + elementHeight, lightColor);
                    g2d.setPaint(grad);
                    g2d.fillRoundRect(elemX, elemY, 180, elementHeight, 15, 15);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRoundRect(elemX, elemY, 180, elementHeight, 15, 15);

                    g2d.setColor(Color.WHITE);
                    g2d.setFont(new Font("Arial", Font.BOLD, 18));
                    String text = String.valueOf(elem.value);
                    int textX = elemX + (180 - fm.stringWidth(text)) / 2;
                    int textY = elemY + elementHeight / 2 + fm.getAscent() / 2 - 2;
                    g2d.drawString(text, textX, textY);

                    if (elem.isSettled && i < elements.size() - 1) {
                        g2d.setColor(new Color(255, 69, 0));
                        g2d.setStroke(new BasicStroke(3));
                        g2d.drawLine(elemX + 10, elemY, elemX + 170, elemY);
                    }
                }

                // Animating out elements (pop animation)
                for (StackElement elem : animatingOut) {
                    int elemX = (int) elem.x;
                    int elemY = (int) elem.y;
                    GradientPaint grad = new GradientPaint(elemX, elemY, new Color(220, 20, 60),
                            elemX, elemY + elementHeight, new Color(255, 99, 71));
                    g2d.setPaint(grad);
                    g2d.fillRoundRect(elemX, elemY, 180, elementHeight, 15, 15);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRoundRect(elemX, elemY, 180, elementHeight, 15, 15);
                    g2d.setColor(Color.WHITE);
                    g2d.drawString(String.valueOf(elem.value), elemX + (180 - fm.stringWidth(String.valueOf(elem.value))) / 2,
                            elemY + elementHeight / 2 + fm.getAscent() / 2 - 2);
                }

                // TOP arrow
                if (!elements.isEmpty() && arrowVisible) {
                    StackElement topElem = elements.get(elements.size() - 1);
                    int arrowX = (int) topElem.x + 90;
                    int arrowY = (int) topElem.y - 20;
                    g2d.setColor(new Color(255, 69, 0));
                    g2d.setStroke(new BasicStroke(5));
                    g2d.drawLine(arrowX, arrowY, arrowX, arrowY + 20);
                    g2d.drawLine(arrowX - 8, arrowY + 12, arrowX, arrowY + 20);
                    g2d.drawLine(arrowX + 8, arrowY + 12, arrowX, arrowY + 20);

                    g2d.setFont(new Font("Arial", Font.BOLD, 16));
                    g2d.setColor(Color.BLACK);
                    g2d.drawString("TOP", (int) topElem.x + 200, (int) topElem.y + 25);
                }
            }
        };

        add(drawPanel, BorderLayout.CENTER);
        add(statusbar, BorderLayout.SOUTH);

        // Animation Timer (for push and pop)
        animationTimer = new javax.swing.Timer(50, e -> updateAnimations());
        animationTimer.start();

        setVisible(true);
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(f);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createRaisedBevelBorder());
        return btn;
    }

    private void push() {
        if (elements.size() >= MAX_STACK_SIZE) {
            statusbar.setText("Stack Overflow! Max size: " + MAX_STACK_SIZE);
            JOptionPane.showMessageDialog(this, "Stack Overflow!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int value = Integer.parseInt(input.getText());
            StackElement elem = new StackElement();
            elem.value = value;
            elem.x = 10;
            elem.y = 350;
            int bucketX = (getWidth() - 200) / 2;
            elem.targetX = bucketX + 10;
            elem.targetY = 600 - (elements.size()) * 50;
            elem.isAnimating = true;
            elem.isSettled = false;
            elem.startTime = System.currentTimeMillis();
            elements.add(elem);
            statusbar.setText("Value placed in stack: " + value);
            input.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter a valid integer", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pop() {
        if (elements.isEmpty()) {
            statusbar.setText("Stack Underflow! Stack is empty");
            JOptionPane.showMessageDialog(this, "Stack Underflow!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Animate pop
        StackElement elem = elements.remove(elements.size() - 1);
        elem.targetX = getWidth() - 200;
        elem.targetY = 350;
        elem.isAnimating = true;
        elem.startTime = System.currentTimeMillis();
        animatingOut.add(elem);
        statusbar.setText("Value removed from stack: " + elem.value);
    }

    private void updateAnimations() {
        boolean needRepaint = false;
        // Push animation
        for (StackElement elem : elements) {
            if (elem.isAnimating) {
                long elapsed = System.currentTimeMillis() - elem.startTime;
                double progress = Math.min(elapsed / (double) elem.ANIMATION_DURATION, 1.0);
                elem.x += (elem.targetX - elem.x) * 0.1;
                elem.y += (elem.targetY - elem.y) * 0.1;
                if (progress >= 1.0) {
                    elem.x = elem.targetX;
                    elem.y = elem.targetY;
                    elem.isAnimating = false;
                    elem.isSettled = true;
                }
                needRepaint = true;
            }
        }

        // Pop animation
        Iterator<StackElement> it = animatingOut.iterator();
        while (it.hasNext()) {
            StackElement elem = it.next();
            long elapsed = System.currentTimeMillis() - elem.startTime;
            double progress = Math.min(elapsed / (double) elem.ANIMATION_DURATION, 1.0);
            elem.x += (elem.targetX - elem.x) * 0.1;
            elem.y += (elem.targetY - elem.y) * 0.1;
            if (progress >= 1.0) it.remove();
            needRepaint = true;
        }

        arrowBlinkCounter++;
        if (arrowBlinkCounter >= 10) {
            arrowVisible = !arrowVisible;
            arrowBlinkCounter = 0;
            needRepaint = true;
        }

        if (needRepaint) drawPanel.repaint();
    }

    public static void main(String[] args) {
        new StackVisualization();
    }
}
