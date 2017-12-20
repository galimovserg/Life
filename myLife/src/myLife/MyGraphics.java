package myLife;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;



public  class MyGraphics extends JPanel implements Runnable{
	
	private int delay=30;
	private int countw=0;
	private int counth=0;
	private int w=0;
	private int h=0;
	
	private int widthcell=0;
	private int left=0;
	private int top=0;
	private int map[][];
	private int mapbuffer[][];
	
	private boolean flag=false;
	private boolean correct=false;
	private boolean isshow=false;
	private boolean ismsg=false;
	
	private static final int deathCell=0;
	private static final int lifeCell=1;
	private static final Color deathColor=new Color(255,255,255);
	private static final Color lifeColor=new Color(0,0,0);
	protected int xllast=0;
	protected int yllast=0;
	protected Graphics2D g2d;
	
	private JLabel msg;
	
	MyGraphics(int left,int top,int countw,int counth,int widthcell){
		setLayout(null);
		msg=new JLabel("Game over");
		Font font = new Font("Verdana", Font.BOLD, 24);
		msg.setBounds(countw*widthcell/2-100, counth*widthcell/2-20, 200, 40);
		msg.setFont(font);
		msg.setVisible(false);
		add(msg);
		if(counth>10&&countw>10&&widthcell>4){
			
			this.widthcell=widthcell;
			this.counth=counth;
			this.countw=countw;
			this.w=countw*widthcell;
			this.h=counth*widthcell;
			this.left=left;
			this.top=top;
			this.correct=true;
			this.initialMap();
			
			setBounds(left, top, w, h);
			
			addMouseMotionListener(new MouseAdapter(){
				
				public void mouseDragged(MouseEvent event) {
					
					int xl=event.getX()/widthcell;
					int yl=event.getY()/widthcell;
					if(xl!=xllast||yl!=yllast){
						if(!flag){
							if(correct){
								setXY(xl,yl);
								repaint();
							}
						}else{
							if(isshow){
								setXY(xl,yl);
								repaint();
							}
						}
						
					}
					xllast=xl;
					yllast=yl;
					
				}
			
			});
			
			addMouseListener(new MouseAdapter(){
				
				public void mousePressed(MouseEvent event) {
					
						
						int xl=event.getX()/widthcell;
						int yl=event.getY()/widthcell;
						if(!flag){
							if(correct){
								setXY(xl,yl);
								repaint();
							}
						}else{
							if(isshow){
								setXY(xl,yl);
								repaint();
							}
						}
						xllast=xl;
						yllast=yl;
					
				}
			
			
			});	
			
		}
		
		

	}
	
	void step(){
		int cnt=0;
		for(int i=0;i<counth;i++){
			for(int j=0;j<countw;j++){
				int sum=0;
				
				int im=i-1;
				if(im<0){im=counth-1;}
				
				int ip=i+1;
				if(ip>counth-1){ip=0;}
				
				int jm=j-1;
				if(jm<0){jm=countw-1;}
				
				int jp=j+1;
				if(jp>countw-1){jp=0;}
				
				sum+=map[ip][jp];
				sum+=map[i][jp];
				sum+=map[im][jp];
				sum+=map[i][jm];
				sum+=map[ip][jm];
				sum+=map[im][jm];
				sum+=map[ip][j];
				sum+=map[im][j];
				
				if(map[i][j]==deathCell){
					if(sum==3){
						cnt++;
						mapbuffer[i][j]=lifeCell;
					}else{
						mapbuffer[i][j]=deathCell;
					}
					
				}
				else if(map[i][j]==lifeCell){
					if(sum<2||sum>3){
						cnt++;
						mapbuffer[i][j]=deathCell;
					}else{
						mapbuffer[i][j]=lifeCell;
					}
					
				}
				
			}
		}
		
		for(int i=0;i<counth;i++){
			for(int j=0;j<countw;j++){
				map[i][j]=mapbuffer[i][j];
			}
		}
		if(cnt==0){
			showmsg("Game over!");
			setRun(false);
		}
	}
	void setXY(int xl,int yl){
		
		if(yl>=0&&yl<counth&&xl>=0&&xl<countw){
			if(map[yl][xl]==deathCell){
				map[yl][xl]=lifeCell;
			}else
			{
				map[yl][xl]=deathCell;
			}
		}
		
	}
	void showmsg(String text){
		msg.setVisible(true);
		msg.setText(text);
		ismsg=true;
	}
	void clmsg(){
		if(ismsg){
			msg.setVisible(false);
			ismsg=false;
		}
		
	}
	private void initialMap(){
		if(correct){
			isshow=false;
			map=new int[counth][countw];
			mapbuffer=new int[counth][countw];
			
			for(int i=0;i<counth;i++){
				for(int j=0;j<countw;j++){
					map[i][j]=deathCell;
					mapbuffer[i][j]=deathCell;
				}
			}
		}
	}
	void setDelay(int mm){
		if(mm>20)
			this.delay=mm;
	}
	void clear() throws InterruptedException{
		
		
		setRun(false);
		
		initialMap();
		repaint();
		
		
	}
	
	
	public void setRun(boolean fl){
		this.flag=fl;
	}
	
	
	public void run() {
		if(correct){
			while(true){
				try {
					//если запущено то выполняем действия
					if(flag){
						Thread.sleep(delay);
						isshow=false;
						step();
						repaint();
						isshow=true;
						
					//иначе ничего не делаем
					}else{
						Thread.sleep(1);
					}
			
				} catch (InterruptedException e) {
				// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
			}
		}
		
	}

	 
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
       
        g2d= (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for(int i=0;i<counth;i++){
	    	for(int j=0;j<countw;j++){
	    		if(map[i][j]==deathCell){
	    			 g2d.setColor(deathColor);
	    			 g2d.fillRect(j*widthcell, i*widthcell, j*widthcell+widthcell, i*widthcell+widthcell);	
	    		}else if(map[i][j]==lifeCell){
	    			 g2d.setColor(lifeColor);
	    			 g2d.fillRect(j*widthcell, i*widthcell, j*widthcell+widthcell, i*widthcell+widthcell);	
	    		}
	    	}
	    }
        
       
	    
	    
	
	}
}
