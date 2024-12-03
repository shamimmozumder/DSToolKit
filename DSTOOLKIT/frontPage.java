import java.awt.*;
import javax.swing.*;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Cursor;

public class frontPage extends JFrame {
    private Container c;
    private Font f;
    private JButton DSTOOLKIT;
    private Cursor cursor;
    
    frontPage()
    {
        //freme
       setSize(1550, 900);
        setTitle("DSTOOLKIT");
       setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        ImageIcon background_image = new ImageIcon("DS.jpg");
        Image img = background_image.getImage();
        Image temp_img = img.getScaledInstance(1550,900,Image.SCALE_SMOOTH);
        background_image = new ImageIcon(temp_img);
       JLabel background = new JLabel("", background_image,JLabel.CENTER);
        background.setBounds(0,0,1550,900);
        add(background);

        setVisible(true);

        initComponents();
    }
    public void initComponents()
    {
        c = this.getContentPane();
       c.setLayout(null);

        f = new Font("Arial",Font.BOLD + Font.ITALIC,35);
        cursor = new Cursor(Cursor.HAND_CURSOR);
        
     DSTOOLKIT = new JButton("DSToolKit");
        DSTOOLKIT.setFont(f);
        DSTOOLKIT.setBounds(660,385,250,90);
        DSTOOLKIT.setForeground(Color.BLACK);
        DSTOOLKIT.setBackground(Color.GREEN);
       DSTOOLKIT.setCursor(cursor);
        c.add( DSTOOLKIT);

       DSTOOLKIT.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e)
            {
                if(e.getSource()==DSTOOLKIT)
                {
                    dispose();
                    DSTOOLKIT dst = new DSTOOLKIT();
                    dst.setVisible(true);   
                }  
            }
           });  
    }
    public static void main(String[] args) {
       
        frontPage fp = new frontPage();
        fp.setVisible(true);
       
        
    } 
}
