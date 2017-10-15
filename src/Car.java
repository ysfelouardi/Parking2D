import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import animationBase.Animation;
import animationBase.Sprite;

public class Car{
	private int dx;
    private int dy;
    private int x;
    private int y;
    private int width;
    private int height;
    private String type;
    boolean animated = false;
    String carDirection = "UP";
    boolean parked = false;
    Image carImage;
    BufferedImage[] turningRight;
    BufferedImage[] turningLeft;
    Animation turningRightAnimation;
    Animation turningLeftAnimation;
    private int numeroP;
    
    
  public int getNumeroP() {
		return numeroP;
	}

	public void setNumeroP(int numeroP) {
		this.numeroP = numeroP;
	}

	public Car(String type) {
        
        initCar(type);
    }
    
    public void initCar(String type) {
//        width= 100;
//        height = 134;
    	this.type = type;
        width= 90;
        height = 128;
        dy = 1;
        dx = 1;
        x = ParkingArea.w/2 - width/2 - 2;
        y = ParkingArea.h - 12;
        loadAcceleratingImage();
        loadSprites();
        setAnimation();
    }
    
    public void loadAcceleratingImage(){
    	ImageIcon ii = null;
    	if(carDirection == "UP"){
        	ii = new ImageIcon("assets/"+type+"/1.png");
    	}else if(carDirection == "DOWN"){
        	ii = new ImageIcon("assets/"+type+"/6.png");   		
    	}else if(carDirection == "RIGHT"){
    		ii = new ImageIcon("assets/"+type+"/5.png");
    	}
    	carImage = ii.getImage();
    }
    
    public void loadSprites(){
    	  turningRight = new BufferedImage[]{
      	        Sprite.loadSprite("assets/"+type+"/2.png"),
      	        Sprite.loadSprite("assets/"+type+"/3.png"),
      	        Sprite.loadSprite("assets/"+type+"/4.png"),
      	        Sprite.loadSprite("assets/"+type+"/5.png")
          };
    	  
    	  turningLeft = new BufferedImage[]{
        	        Sprite.loadSprite("assets/"+type+"/left/4.png"),
        	        Sprite.loadSprite("assets/"+type+"/left/3.png"),
        	        Sprite.loadSprite("assets/"+type+"/left/2.png"),
        	        Sprite.loadSprite("assets/"+type+"/left/1.png"),
            }; 
    	
    }
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setAnimation(){
    	turningRightAnimation = new Animation(turningRight,13);
    	turningRightAnimation.start();
    	
    	turningLeftAnimation = new Animation(turningLeft,13);
    	turningLeftAnimation.start();
    }
    
    
    public void moveBackward(){
    		 y+= dy;
    }
        
    public void moveBackwardRight(){
        	x-= dx;
    }
    
    public void moveForwardRight(){
        	x+= dx;
    }
    
    public void moveForward(){
        	y-= dy;
    }
    
    public void stop(){
    	dx = 0;
    	dy = 0;
    }
    
    public void start(){
    	dx = 1;
    	dy = 1;
    }

	public int getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public int getDy() {
		return dy;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	
	public void gopark(Place p,int num){
		//System.out.println("  car y " + y);
		if( y < ParkingArea.h && y > -height){
			start();
			if( y == p.getY()){
				animated = true;//animate the car
				turningRightAnimation.update();
				carDirection = "RIGHT";
				if(x < ParkingArea.w - 210){
					int currentframe = turningRightAnimation.getCurrentFrame();
		    		int totalframes =  turningRightAnimation.getTotalFrames() - 1;
					if(currentframe > totalframes - 1){
						turningRightAnimation.stop();
						animated = false;
						//carDirection = "RIGHT";
						loadAcceleratingImage();
						//moveForwardRight();
					}
		    		moveForwardRight();

				}
				else if ( x == ParkingArea.w - 210 ){
					//System.out.println("x = " + x);
					p.setEmpty(false);
					parked = true;
					stop();
				}
			}else{
				moveForward();
			}
			
		}else{
			ParkingArea.cars.remove(this);
			p.setEmpty(true);
		}
	}
	
	public void waitThere(Place p){
		if( y < ParkingArea.h && y > -height){
			moveForward();
			if( y == p.getY() + 170 ){
				System.out.println(" i'm waiting place " + getNumeroP());
				stop();
			}
		}
	}
	
	public void leave(Place p){
		if(parked){
			//System.out.println(" car x "+ x + " y "+ y );
			if( y < ParkingArea.h && y > -height){
				start();
				if(x > ParkingArea.w/2 ){
					moveBackwardRight();
				}else if( x == ParkingArea.w/2){
					animated = true;
					turningLeftAnimation.update();
					carDirection = "LEFT";
					int currentframe = turningLeftAnimation.getCurrentFrame();
		    		int totalframes =  turningLeftAnimation.getTotalFrames() - 1;
					if(currentframe > totalframes - 1){
						turningLeftAnimation.stop();
						animated = false;
						carDirection = "UP";
						loadAcceleratingImage();
						moveForward();
						//System.out.println("i'm moving y = " + y);
					}			
				}
				
				if(y == p.getY() - 60){
					parked = false;
					p.setEmpty(true);
				}
			}else{
				//parked = false;
				ParkingArea.cars.remove(this);
				//p.setEmpty(true);
				//y = ParkingArea.h - 12;
			}

		}

	}
    
   
        
}
