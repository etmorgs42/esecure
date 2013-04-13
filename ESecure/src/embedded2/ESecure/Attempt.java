package embedded2.ESecure;

import java.io.Serializable;
import java.util.ArrayList;

public class Attempt implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7564729164140437181L;
	private ArrayList<TouchPoint> points;
	private ArrayList<Double> durations;
	private ArrayList<Double> pressures;
	private ArrayList<Double> majors;
	private ArrayList<Double> minors;
	private ArrayList<Double> gaps;
	private StringBuilder code;
	private int count,mode;
//	private float avgGaps;
//	private float avgDuration;
//	private float avgPressure;
//	private float avgMajorAxis, avgMinorAxis; // major and minor axes of touch
//												// ellipse
//	private float stdDuration;
//	private float stdPressure;
//	private float stdMajorAxis, stdMinorAxis; // major and minor axes of touch
//												// ellipse

	public Attempt(int mode, TouchPoint first) {
		points = new ArrayList<TouchPoint>();
		durations = new ArrayList<Double>();
		pressures = new ArrayList<Double>();
		majors = new ArrayList<Double>();
		minors = new ArrayList<Double>();
		gaps = new ArrayList<Double>();
		durations.add(Double.valueOf(first.getDuration()));
		pressures.add(Double.valueOf(first.getPressure()));
		majors.add(Double.valueOf(first.getMajorAxis()));
		minors.add(Double.valueOf(first.getMinorAxis()));
		code = new StringBuilder();
		points.add(first);
		code.append(first.getKey());
		count = 1;
		this.mode = mode;
	}

	void addPoint(TouchPoint newPoint) {
		points.add(newPoint);
		durations.add(Double.valueOf(newPoint.getDuration()));
		pressures.add(Double.valueOf(newPoint.getPressure()));
		majors.add(Double.valueOf(newPoint.getMajorAxis()));
		minors.add(Double.valueOf(newPoint.getMinorAxis()));
		gaps.add(Double.valueOf((double)(points.get(count).getStart()- points.get(count - 1).getStart())));
		if(mode == 1){
			code.append(newPoint.getKey());
		}else if(points.get(count).getKey() != points.get(count - 1).getKey()){
			code.append(newPoint.getKey());
		}
		count++;
	}

	public StringBuilder getCode() {
		return code;
	}
	
	public ArrayList<TouchPoint> getPoints(){
		return points;
	}
	
	public ArrayList<Double> getDurations(){
		return durations;
	}
	
	public ArrayList<Double> getPressures(){
		return pressures;
	}
	
	public ArrayList<Double> getMajors(){
		return majors;
	}
	
	public ArrayList<Double> getMinors(){
		return minors;
	}
	
	public ArrayList<Double> getGaps(){
		return gaps;
	}
	
	
}
