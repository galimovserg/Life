package myLife;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class Life extends JFrame {
	
	JPanel panel0;
	JButton bt1;
	JButton bt2;
	JButton bt3;
	JButton bt4;
	Thread t1;
	Life(){
		super("myLife");
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		//главная панель
		panel0=new JPanel();
		panel0.setLayout(null);
		
		
		MyGraphics Map=new MyGraphics(20,20,200,92,5);
		Map.setDelay(50);
		
		panel0.add(Map);
		t1= new Thread(Map);
		t1.start();
		
		bt1=new JButton("start");
		bt1.setBounds(20, 500, 80, 25);
		bt2=new JButton("stop");
		bt2.setBounds(100, 500, 80, 25);
		bt3=new JButton("clear");
		bt3.setBounds(180, 500, 80, 25);
		bt4=new JButton("step");
		bt4.setBounds(260, 500, 80, 25);
		
		bt1.addMouseListener(new MouseAdapter(){
			
			public void mousePressed(MouseEvent event) {
					Map.clmsg();
					Map.setRun(true);
					
				
			}
		});
		bt2.addMouseListener(new MouseAdapter(){
			
			public void mousePressed(MouseEvent event) {
					
					Map.setRun(false);
					Map.clmsg();
				
			}
		});
		bt3.addMouseListener(new MouseAdapter(){
			
			public void mousePressed(MouseEvent event) {
					
					try {
						Map.clmsg();
						Map.clear();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				
			}
		});
		bt4.addMouseListener(new MouseAdapter(){
			
			public void mousePressed(MouseEvent event) {
					Map.clmsg();
					Map.step();
					Map.repaint();
				
				
			}
		});
		panel0.add(bt1);
		panel0.add(bt2);
		panel0.add(bt3);
		panel0.add(bt4);
		setContentPane(panel0);
	 	setSize(1040,575);
	 	setResizable(false);
	 	setVisible(true);
	 	
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Life();
	}

}
