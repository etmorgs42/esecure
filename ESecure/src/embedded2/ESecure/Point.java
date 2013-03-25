package embedded2.ESecure;

import java.util.Date;

public class Point {
	int x,y;
	long duration;
	float pressure;
	float majorAxis,minorAxis; //major and minor axes of touch ellipse
	float orientation; //orientation of touch ellipse
	Date start,end;
	char keyPress;
	
	Point(int x, int y, Date start){
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
	
	int getX(){ return x; }
	int getY(){ return y; }
	float getMajorAxis(){ return majorAxis; }
	float getMinorAxis(){ return minorAxis; }
	float getOrientation(){ return orientation; }
	float getPressure(){ return pressure; }
	char getKey(){ return keyPress; }
	Date getStart(){ return start; }
	Date getEnd(){ return end; }
	long getDuration(){ return duration; }
	
	
	

}
