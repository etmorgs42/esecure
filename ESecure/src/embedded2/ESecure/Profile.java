package embedded2.ESecure;

import java.io.Serializable;
import java.util.ArrayList;

public class Profile implements Serializable{
	private ArrayList<Attempt> history;
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

	public Profile(Attempt first) {
		history.add(first);
		count = 1;
	}

	public boolean add(Attempt newAttempt) {
		if (newAttempt.getCode().equals(history.get(count - 1).getCode())) {
			if (count < 10) {
				float sumDuration = 0, sumPressure = 0.0f, sumMajorAxis = 0.0f, sumMinorAxis = 0.0f, sumOrientation = 0.0f;
				for (int i = 0; i < count; i++) {
					sumDuration += history.get(i).getAvgDuration();
					sumPressure += history.get(i).getAvgPressure();
					sumMajorAxis += history.get(i).getAvgMajorAxis();
					sumMinorAxis += history.get(i).getAvgMinorAxis();
					sumOrientation += history.get(i).getAvgOrientation();
				}
				avgDuration = sumDuration / count;
				avgPressure = sumPressure / count;
				avgMajorAxis = sumMajorAxis / count;
				avgMinorAxis = sumMinorAxis / count;
				avgOrientation = sumOrientation / count;
				sumDuration = sumPressure = sumMajorAxis = sumMinorAxis = sumOrientation = 0.0f;
				for (int i = 0; i < count; i++) {
					sumDuration += ((float) history.get(i).getAvgDuration() - avgDuration)
							* ((float) history.get(i).getAvgDuration() - avgDuration);
					sumPressure += (history.get(i).getAvgPressure() - avgPressure)
							* (history.get(i).getAvgPressure() - avgPressure);
					sumMajorAxis += (history.get(i).getAvgMajorAxis() - avgMajorAxis)
							* (history.get(i).getAvgMajorAxis() - avgMajorAxis);
					sumMinorAxis += (history.get(i).getAvgMinorAxis() - avgMinorAxis)
							* (history.get(i).getAvgMinorAxis() - avgMinorAxis);
					sumOrientation += (history.get(i).getAvgOrientation() - avgOrientation)
							* (history.get(i).getAvgOrientation() - avgOrientation);
				}

				stdDuration = (float) Math.sqrt(sumDuration / count);
				stdPressure = (float) Math.sqrt(sumPressure / count);
				stdMajorAxis = (float) Math.sqrt(sumMajorAxis / count);
				stdMinorAxis = (float) Math.sqrt(sumMinorAxis / count);
				stdOrientation = (float) Math.sqrt(sumOrientation / count);
				return true;
			} else {
			}
		} else {
			return false;
		}
	}
}
