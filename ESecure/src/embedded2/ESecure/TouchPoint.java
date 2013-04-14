package embedded2.ESecure;

import java.io.Serializable;
import java.util.Date;

public class TouchPoint implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5295750412613428842L;
	private float x,y;
	private float dist;
	private long duration;
	private float pressure;
	private float majorAxis,minorAxis; //major and minor axes of touch ellipse
	private long start,end;
	private char keyPress;
	
	public TouchPoint(float x, float y, long start){
		this.x = x;
		this.y = y;
		this.start = start;
	}
	
	void setShape(float majorAxis, float minorAxis){
		this.majorAxis = majorAxis;
		this.minorAxis = minorAxis;
	}
	
	void setKey(char keyPress){
		this.keyPress = keyPress;
	}
	
	void setPressure(float pressure){
		this.pressure = pressure;
	}
	
	void setEnd(long end){
		this.end = end;
		duration = end - start;
	}
	
	void setDist(float dist){
		this.dist = dist;
	}
	
	float getX(){ return x; }
	float getY(){ return y; }
	float getMajorAxis(){ return majorAxis; }
	float getMinorAxis(){ return minorAxis; }
	float getPressure(){ return pressure; }
	float getDist(){ return dist; }
	char getKey(){ return keyPress; }
	long getStart(){ return start; }
	long getEnd(){ return end; }
	long getDuration(){ return duration; }
	
	
	

}
