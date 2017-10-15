import java.awt.Image;

import javax.swing.ImageIcon;

public class TollBooth {
	private int x;
    private int y;
    private int width;
    private int height;
    Image boothImage;
    String boothState = "OFF";
    
    
    public void loodBoothImage(){
    	ImageIcon ii = null;
    	if(boothState == "OFF"){
        	ii = new ImageIcon("assets/booth/1.png");
    	}else if(boothState == "ON"){
        	ii = new ImageIcon("assets/booth/2.png");   		
    	}
    	boothImage = ii.getImage();
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
    
    
}
