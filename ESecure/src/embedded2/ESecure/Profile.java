package embedded2.ESecure;

import java.io.Serializable;
import java.util.ArrayList;

public class Profile implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5720841490748053586L;
	private ArrayList<Attempt> history;
	private int count;
	private float avgDuration;
	private float avgPressure;
	private float avgMajorAxis, avgMinorAxis; // major and minor axes of touch
	private float avgOrientation; // orientation of touch ellipse
	
	private float stdDuration;
	private float stdPressure;
	private float stdMajorAxis, stdMinorAxis; // major and minor axes of touch
	private float stdOrientation; // orientation of touch ellipse
	
	private float weightDuration;
	private float weightPressure;
	private float weightMajorAxis, weightMinorAxis;
	private float weightOrientation;

	public Profile() {
		history = new ArrayList<Attempt>();
		//history.add(first);
		count = 0;
	}

	public boolean add(Attempt newAttempt) {
	if(count == 0){
		history.add(newAttempt);
		count++;
		this.updateStats();
		return true;
	}else if (newAttempt.getCode().equals(history.get(count - 1).getCode())) {
			if (count < 10) {
				history.add(newAttempt);
				count++;
				this.updateStats();
				return true;
			} else {
				float score = 0;
				score += weightDuration*((Math.abs(newAttempt.getAvgDuration() - avgDuration) > (2 * stdDuration))?0:1);
				score += weightPressure*((Math.abs(newAttempt.getAvgPressure() - avgPressure) > (2 * stdPressure))?0:1);
				score += weightMajorAxis*((Math.abs(newAttempt.getAvgMajorAxis() - avgMajorAxis) > (2 * stdMajorAxis))?0:1);
				score += weightMinorAxis*((Math.abs(newAttempt.getAvgMinorAxis() - avgMinorAxis) > (2 * stdMinorAxis))?0:1);
				score += weightOrientation*((Math.abs(newAttempt.getAvgOrientation() - avgOrientation) > (2 * stdOrientation))?0:1);
				
				if(score > 0.6){
					history.add(newAttempt);
					history.remove(0);
					updateStats();
					return true;
				}else{
					return false;
				}
			}
		} else {
			return false;
		}
	}
	
	private void updateStats(){
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
		
		float normDuration, normPressure, normMajorAxis, normMinorAxis, normOrientation, sumnorm = 0.0f;
		sumnorm += (normDuration = stdDuration/stdDuration);
		sumnorm += (normPressure = avgPressure/stdPressure);
		sumnorm += (normMajorAxis = avgMajorAxis/stdMajorAxis);
		sumnorm += (normMinorAxis = avgMinorAxis/stdMinorAxis);
		sumnorm += (normOrientation = avgOrientation/stdOrientation);
		
		weightDuration = normDuration/sumnorm;
		weightPressure = normPressure/sumnorm;
		weightMajorAxis = normMajorAxis/sumnorm;
		weightMinorAxis = normMinorAxis/sumnorm;
		weightOrientation = normOrientation/sumnorm;		
	}
}
