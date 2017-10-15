import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ParkingArea extends JPanel implements Runnable{
	private Image backgroundImage;
	public static int h;
	public static int w;
	private Thread animator;
	private final int DELAY = 9;
	static ArrayList<Car> cars;
	static Place places[];
	long startTime;
	int indexc,indexp;
	int car2leave;
	TollBooth booth = new TollBooth();
	
	public ParkingArea(){
		 initParkingArea();
	 }
	
	 private void initParkingArea() {
	        
			loadBackgroundImage();
	        w = backgroundImage.getWidth(this);
	        h =  backgroundImage.getHeight(this);
	        setPreferredSize(new Dimension(w, h));  
	        setFocusable(true);
	        cars = new ArrayList<Car>();
	        places = new Place[]{
	        		new Place(h/2 - 10,true),
	        		new Place(h/2 - 80,true),
	        		new Place(h /2 - 150,true)};
	        
	        addCar();
	        startTime = System.currentTimeMillis();
	        
	        
	        booth.setY(places[0].getY() + 120);
	        booth.setX(w/2 - 60);
	        booth.setHeight(100);
	        booth.setWidth(220);

	        
	        
	 }
	 
	 private void loadBackgroundImage(){
		 ImageIcon ii = new ImageIcon("assets/background11.png");
		 backgroundImage = ii.getImage();
	}
	 
	private void drawBackgroundImage(Graphics g){
	     g.drawImage(backgroundImage, 0, 0, null);
	}
	
	private void drawBooth(Graphics g){
		 booth.loodBoothImage();
	     g.drawImage(booth.boothImage, booth.getX(),booth.getY(),booth.getWidth(), booth.getHeight(), null);
	}
	
	private void drawCar(Graphics g, Car car){
		 Graphics2D g2d = (Graphics2D) g;
		 if(car.animated){
			 if(car.carDirection == "RIGHT"){
				 g2d.drawImage(car.turningRightAnimation.getSprite(), car.getX(),car.getY(), car.getWidth(), car.getHeight(),this); 
			 }else if(car.carDirection == "LEFT"){
				 g2d.drawImage(car.turningLeftAnimation.getSprite(), car.getX(),car.getY(), car.getWidth(), car.getHeight(),this);
			 }
			 
		 }else{
			 g2d.drawImage(car.carImage, car.getX(),car.getY(), car.getWidth(), car.getHeight(),this);
		 }		 
	}
	
	@Override 
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackgroundImage(g);
        drawBooth(g);
        for(int i=0; i < cars.size() ;i++){
        	Car car = cars.get(i);
        	drawCar(g,car);
        }
        Toolkit.getDefaultToolkit().sync();
    }
	
	
	public void addCar(){
		 int num = ThreadLocalRandom.current().nextInt(1,4);	
		 Car car = new Car("car"+num);
		 cars.add(car);
	}
	
	public void park(){	
		for(int i = 0;i< cars.size() ;i++){
			Car car  = cars.get(i);
			int num = getEmptyPlace();
			
			//System.out.println(" car size = " + cars.size());
			//System.out.println(" car " + i + " place " + car.getNumeroP() + " empty ? " + places[car.getNumeroP()].isEmpty() + " parked ? " + car.parked);
			if(i == cars.size() - 1 && cars.size() == 4){					
				System.out.println(" car waiting " + i);
				car.waitThere(places[0]);
				booth.boothState = "ON";
			}
			else if(num != -1 && !car.parked){
				System.out.println(" car  parking " + i);
				car.setNumeroP(num); 
				car.gopark(places[num],num);				
			}
			
			if(cars.size() == 3){
				booth.boothState = "OFF";
			}

			
			

		}
		

	}
	
	public boolean onlyOnePlaceLeft(){
		boolean oneplace = false;
		if(places[0].isEmpty() && !places[1].isEmpty() && !places[2].isEmpty()){
			oneplace = true;
		}else if(!places[0].isEmpty() && places[1].isEmpty() && !places[2].isEmpty()){
			oneplace = true;
		}else if(!places[0].isEmpty() && !places[1].isEmpty() && places[2].isEmpty()){
			oneplace = true;
		}
		return oneplace;
	}
	
	public int getEmptyPlace(){
		
		//int num = ThreadLocalRandom.current().nextInt(0,3);	
		for(int i = 0; i< places.length ; i++){
			if(places[i].isEmpty()){
				return i;
			}
		}
		return -1;
	}
	
	
	public boolean allCarsParked(){
		boolean allparked = false;
		for(int i = 0; i< cars.size() -1; i++){
			if(cars.get(i).parked && cars.get(i+1).parked){
				allparked = true;
			}
		}
		return allparked;
	}

	
	public int getleavingCar(){
		for(int i = 0; i< cars.size() ; i++){
			if(cars.get(i).parked){
				car2leave =  i;
				return i;
			}
		}
		return -1;
	}
	
	public void leave(){
		//cars.get(car2leave).leave(places[cars.get(car2leave).getNumeroP()]);
		cars.get(0).leave(places[cars.get(0).getNumeroP()]);
	}
	
	 @Override
	    public void addNotify() {
	        super.addNotify();
	        animator = new Thread(this);
	        animator.start();
	    }

	@Override
	public void run() {
		 long beforeTime, timeDiff, sleep;

	     beforeTime = System.currentTimeMillis();
	        while (true) {	    		
	        	long stopTime = System.currentTimeMillis();
	    	    if(stopTime - startTime > 4000){
	    	    	if(cars.size() < 4){
	    	    		addCar();
	    	    	}
	    	    		    	    	

	    	    	startTime = System.currentTimeMillis();
	    	    }
	    	    
	    	    
	    
	    	    park();
	    	    
	    	    
	    	    if(getEmptyPlace() == -1){
	    	    	//car2leave = ThreadLocalRandom.current().nextInt(0,cars.size());
	    	    	//System.out.println(" i'm in car2leave " + car2leave);
	    	    	leave();
	    	    }

    	    	
	    	    
	    		repaint();

	    		timeDiff = System.currentTimeMillis() - beforeTime;
	            sleep = DELAY - timeDiff;

	            if (sleep < 0) {
	                sleep = 2;
	            }

	            try {
	                Thread.sleep(sleep);
	            } catch (InterruptedException e) {
	                System.out.println("Interrupted: " + e.getMessage());
	            }

	            beforeTime = System.currentTimeMillis();
	        }
		
	}
}
