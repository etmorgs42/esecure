package embedded2.ESecure;

import java.io.Serializable;
import java.util.ArrayList;

public class Attempt implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7564729164140437181L;
	private ArrayList<TouchPoint> points;
	private ArrayList<Long> gaps;
	private StringBuilder code;
	private int count,mode;
	private float avgGaps;
	private float avgDuration;
	private float avgPressure;
	private float avgMajorAxis, avgMinorAxis; // major and minor axes of touch
												// ellipse
	private float avgOrientation; // orientation of touch ellipse
	private float stdDuration;
	private float stdPressure;
	private float stdMajorAxis, stdMinorAxis; // major and minor axes of touch
												// ellipse
	private float stdOrientation; // orientation of touch ellipse

	Attempt(int mode, TouchPoint first) {
		points = new ArrayList<TouchPoint>();
		gaps = new ArrayList<Long>();
		code = new StringBuilder();
		points.add(first);
		code.append(first.getKey());
		count = 1;
		this.mode = mode;
	}

	void addPoint(TouchPoint newPoint) {
		points.add(newPoint);
		gaps.add(new Long(points.get(count).getStart()- points.get(count - 1).getStart()));
		if(mode == 1){
			code.append(newPoint.getKey());
		}else if(points.get(count).getKey() != points.get(count - 1).getKey()){
			code.append(newPoint.getKey());
		}
		count++;

		float sumDuration = 0.0f, sumPressure = 0.0f, sumMajorAxis = 0.0f, sumMinorAxis = 0.0f, sumOrientation = 0.0f;
		for (int i = 0; i < count; i++) {
			sumDuration += (float) points.get(i).getDuration();
			sumPressure += points.get(i).getPressure();
			sumMajorAxis += points.get(i).getMajorAxis();
			sumMinorAxis += points.get(i).getMinorAxis();
			sumOrientation += points.get(i).getOrientation();
		}
		avgDuration = sumDuration / count;
		avgPressure = sumPressure / count;
		avgMajorAxis = sumMajorAxis / count;
		avgMinorAxis = sumMinorAxis / count;
		avgOrientation = sumOrientation / count;
	}

	public float getAvgDuration() {
		return avgDuration;
	}

	public float getAvgPressure() {
		return avgPressure;
	}

	public float getAvgMajorAxis() {
		return avgMajorAxis;
	}

	public float getAvgMinorAxis() {
		return avgMinorAxis;
	}

	public float getAvgOrientation() {
		return avgOrientation;
	}
	
	public float getStdDuration() {
		return stdDuration;
	}

	public float getStdPressure() {
		return stdPressure;
	}

	public float getStdMajorAxis() {
		return stdMajorAxis;
	}

	public float getStdMinorAxis() {
		return stdMinorAxis;
	}

	public float getStdOrientation() {
		return stdOrientation;
	}

	public StringBuilder getCode() {
		return code;
	}
}
