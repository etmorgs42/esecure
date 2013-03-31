package embedded2.ESecure;

import java.io.Serializable;
import java.util.Date;

public class Point implements Serializable{
	private float x,y;
	private long duration;
	private float pressure;
	private float majorAxis,minorAxis; //major and minor axes of touch ellipse
	private float orientation; //orientation of touch ellipse
	private Date start,end;
	private char keyPress;
	
	Point(float x, float y, Date start){
		this.x = x;
		this.y = y;
		this.start = start;
	}
	
	void setShape(float majorAxis, float minorAxis){
		this.majorAxis = majorAxis;
		this.minorAxis = minorAxis;
	}
	
	void setOrientation(float orientation){
		this.orientation = orientation;
	}
	
	void setKey(char keyPress){
		this.keyPress = keyPress;
	}
	
	void setPressure(float pressure){
		this.pressure = pressure;
	}
	
	void setEnd(Date end){
		this.end = end;
		duration = end.getTime() - start.getTime();
	}
	
	float getX(){ return x; }
	float getY(){ return y; }
	float getMajorAxis(){ return majorAxis; }
	float getMinorAxis(){ return minorAxis; }
	float getOrientation(){ return orientation; }
	float getPressure(){ return pressure; }
	char getKey(){ return keyPress; }
	Date getStart(){ return start; }
	Date getEnd(){ return end; }
	long getDuration(){ return duration; }
	
	
	

}
