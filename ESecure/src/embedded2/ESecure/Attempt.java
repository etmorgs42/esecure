package embedded2.ESecure;

import java.io.Serializable;
import java.util.ArrayList;

public class Attempt implements Serializable{
	private ArrayList<Point> points;
	private ArrayList<Long> gaps;
	private ArrayList<Character> code;
	private int count;
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

	Attempt(Point first) {
		points = new ArrayList<Point>();
		gaps = new ArrayList<Long>();
		code = new ArrayList<Character>();
		points.add(first);
		code.add(Character.valueOf(first.getKey()));
		count = 1;
	}

	void addPoint(Point newPoint) {
		points.add(newPoint);
		gaps.add(new Long(points.get(count).getStart().getTime()
				- points.get(count - 1).getStart().getTime()));
		if (points.get(count) != points.get(count - 1)
				|| gaps.get(count - 1) > 100) {
			code.add(newPoint.getKey());
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

	public ArrayList<Character> getCode() {
		return code;
	}
}
