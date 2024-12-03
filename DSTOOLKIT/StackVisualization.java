import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

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
class LinkedListStack {
    private Node top;

    public LinkedListStack() {
        this.top = null;
    }
    public void push(int value) 
    {
        Node newNode = new Node(value);
        // if (head == null) 
        // {
        //     head = newNode;
        // } 
        //else 
       // {
       newNode.val=value;
            newNode.next = top;
           top = newNode;
       // }
    }
    public int pop() 
    {
        if (top == null) 
        {
            throw new IllegalStateException("Stack is Empty");
        }
        int value = top.val;
        top = top.next;
        return value;
    }
    public boolean Empty() 
    {
        return top == null;
    }
}

public class StackVisualization extends JFrame {
    private Stack<Integer> stack;
    private JPanel stackPanel;
    private JTextField input;
    private JLabel statusbar;
    private JButton push,pop,back;
    private Font f;
    private BoxLayout Box;

    public StackVisualization() 
    {
        stack = new Stack<>();

        // Setup the main frame
        setTitle("Stack Visualization");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

       f = new Font("Arial",Font.BOLD + Font.ITALIC,24);

        stackPanel = new JPanel();
        Box = new BoxLayout(stackPanel,BoxLayout.Y_AXIS);
        stackPanel.setLayout(Box);
        stackPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        
        input = new JTextField(6);
        input.setHorizontalAlignment(JTextField.CENTER);
        input.setFont(f);

          //Push Operation
         push = new JButton("Push");
        push.setFont(f);
        push.setBackground(Color.cyan);
        push.setForeground(Color.black);

        push.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                push();
            }
        });

         //pop operation
         pop = new JButton("Pop");
         pop.setFont(f);
         pop.setBackground(Color.pink);
         pop.setForeground(Color.black);

         pop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                pop();
            }
        });
        
        //Back operation
        back = new JButton("Back");
        back.setFont(f);
        back.setBackground(Color.RED);
        back.setForeground(Color.ORANGE);

        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                if(e.getSource()==back)
                {
                    dispose();
                    DSTOOLKIT frame = new DSTOOLKIT();
                    frame.setVisible(true);  
                }  
            }
           });

        statusbar = new JLabel("Stack is Empty");
        statusbar.setOpaque(true);
        statusbar.setFont(f);
        statusbar.setBackground(Color.ORANGE);

        JPanel controlPanel = new JPanel();
        JLabel value = new JLabel("Value:");
        value.setFont(f);
        
        controlPanel.add(value);
        controlPanel.add(input);
        controlPanel.add(push);
        controlPanel.add(pop);
        controlPanel.add(back);
        
        add(controlPanel, BorderLayout.NORTH);
        JScrollPane js = new JScrollPane(stackPanel);
        add(js,BorderLayout.CENTER);
        add(statusbar, BorderLayout.SOUTH);
        setVisible(true);
    }
    private void push() 
    {
        try 
        {
            int value = Integer.parseInt(input.getText());
            stack.push(value);
            updateStackPanel();
            statusbar.setText("Pushed: " + value);
            
        }
         catch (NumberFormatException e) 
        {
            JOptionPane.showMessageDialog(this, " Enter a valid integer", "Error", JOptionPane.ERROR_MESSAGE);
        }
        input.setText("");
    }
    private void pop() 
    {
        if (stack.isEmpty()) 
        {
            statusbar.setText("Stack is Empty");
            JOptionPane.showMessageDialog(this, "Stack is Empty", "Error", JOptionPane.ERROR_MESSAGE);
        } 
        else
         {
            int value = stack.pop();
            updateStackPanel();
            statusbar.setText("Popped: " + value);
        }
    }

    private void updateStackPanel() 
    {
        stackPanel.removeAll();

        for (int i = stack.size() - 1; i >= 0; i--)
         {
            JLabel label = new JLabel(String.valueOf(stack.get(i)));
            label.setBorder(BorderFactory.createLineBorder(Color.MAGENTA,13,true));
            label.setFont(f);
            label.setOpaque(true);
            label.setBackground(Color.PINK);
            
            stackPanel.add(label);
            stackPanel.add(javax.swing.Box.createVerticalStrut(15)); 
        }
        stackPanel.revalidate();
        stackPanel.repaint();
    }

    public static void main(String[] args) {
        
         StackVisualization sv = new StackVisualization();
    }
}