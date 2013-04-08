package embedded2.ESecure;

import java.io.Serializable;
import java.util.ArrayList;

import android.util.Log;

public class Profile implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5720841490748053586L;
	TTest tester;
	private ArrayList<Attempt> history;
	private ArrayList<Double> durations;
	private ArrayList<Double> pressures;
	private ArrayList<Double> majors;
	private ArrayList<Double> minors;
	private ArrayList<Double> orientations;
	private ArrayList<Double> gaps;
	boolean testDuration, testPressure, testMajor,testMinor,testOrientation,testGaps;
	private int count;
	private float avgDuration;
	private float avgPressure;
	private float avgMajorAxis, avgMinorAxis; // major and minor axes of touch
	private float avgOrientation,avgGap; // orientation of touch ellipse
	
	private float stdDuration;
	private float stdPressure;
	private float stdMajorAxis, stdMinorAxis; // major and minor axes of touch
	private float stdOrientation, stdGap; // orientation of touch ellipse
	
	private float cvDuration;
	private float cvPressure;
	private float cvMajorAxis, cvMinorAxis;
	private float cvOrientation;
	private float cvGaps;
	
	private float weightDuration;
	private float weightPressure;
	private float weightMajorAxis, weightMinorAxis;
	private float weightOrientation;
	private float weightGaps;
	
	private float maxScore;

	public Profile() {
		history = new ArrayList<Attempt>();
		durations = new ArrayList<Double>();
		pressures = new ArrayList<Double>();
		majors = new ArrayList<Double>();
		minors = new ArrayList<Double>();
		orientations = new ArrayList<Double>();
		gaps = new ArrayList<Double>();
		tester = new TTest();
		//history.add(first);
		count = 0;
	}

	public boolean add(Attempt newAttempt) {
	if(count == 0){
		history.add(newAttempt);
		count++;
		durations.addAll(newAttempt.getDurations());
		pressures.addAll(newAttempt.getPressures());
		majors.addAll(newAttempt.getMajors());
		minors.addAll(newAttempt.getMinors());
		orientations.addAll(newAttempt.getOrientations());
		gaps.addAll(newAttempt.getGaps());
		//this.updateStats();
		return true;
	}else if (newAttempt.getCode().toString().equals(history.get(count - 1).getCode().toString())) {
			if (count < 10) {
				Log.v("CODE","new");
				history.add(newAttempt);
				count++;
				//this.updateStats();
				return true;
			} else {
				Log.v("CODE","attempt");
				float score = 0;
				
				
				
				double [] sample1 = new double[durations.size()];
				double [] sample2 = new double[newAttempt.getPoints().size()];
				for(int i = 0; i < sample1.length; i++){
					sample1[i] = durations.get(i).doubleValue();
				}
				for(int i = 0; i < sample2.length; i++){
					sample2[i] = newAttempt.getDurations().get(i).doubleValue();
				}
				testDuration = tester.tTest(sample1,sample2,0.1);
				
				for(int i = 0; i < sample1.length; i++){
					sample1[i] = pressures.get(i).doubleValue();
				}
				for(int i = 0; i < sample2.length; i++){
					sample2[i] = newAttempt.getPressures().get(i).doubleValue();
				}
				testPressure = tester.tTest(sample1,sample2,0.1);
				
				for(int i = 0; i < sample1.length; i++){
					sample1[i] = majors.get(i).doubleValue();
				}
				for(int i = 0; i < sample2.length; i++){
					sample2[i] = newAttempt.getMajors().get(i).doubleValue();
				}
				testMajor = tester.tTest(sample1,sample2,0.1);
				
				for(int i = 0; i < sample1.length; i++){
					sample1[i] = minors.get(i).doubleValue();
				}
				for(int i = 0; i < sample2.length; i++){
					sample2[i] = newAttempt.getMinors().get(i).doubleValue();
				}
				testMinor = tester.tTest(sample1,sample2,0.1);
				
				for(int i = 0; i < sample1.length; i++){
					sample1[i] = orientations.get(i).doubleValue();
				}
				for(int i = 0; i < sample2.length; i++){
					sample2[i] = newAttempt.getOrientations().get(i).doubleValue();
				}
				testOrientation = tester.tTest(sample1,sample2,0.1);
				
				sample1 = new double[gaps.size()];
				sample2 = new double[newAttempt.getGaps().size()];
				for(int i = 0; i < sample1.length; i++){
					sample1[i] = gaps.get(i).doubleValue();
				}
				for(int i = 0; i < sample2.length; i++){
					sample2[i] = newAttempt.getGaps().get(i).doubleValue();
				}
				testGaps = tester.tTest(sample1,sample2,0.1);
				
				
				score += weightDuration*(testDuration?0:1);
				score += weightPressure*(testPressure?0:1);
				score += weightMajorAxis*(testMajor?0:1);
				score += weightMinorAxis*(testMinor?0:1);
				score += weightOrientation*(testOrientation?0:1);
				score += weightGaps*(testGaps?0:1);
				
				if(score > 0.6*maxScore){
					history.add(newAttempt);
					durations.removeAll(history.get(0).getDurations());
					pressures.removeAll(history.get(0).getPressures());
					majors.removeAll(history.get(0).getMajors());
					minors.removeAll(history.get(0).getMinors());
					orientations.removeAll(history.get(0).getOrientations());
					gaps.removeAll(history.get(0).getGaps());
					history.remove(0);
					//updateStats();
					return true;
				}else{
					return false;
				}
			}
		} else {
			Log.v("CODE","mismatch");
			return false;
		}
	}
	
	private void updateStats(){
		float sumDuration = 0, sumPressure = 0, sumMajorAxis = 0, sumMinorAxis = 0, sumOrientation = 0,sumGaps = 0;
		int size = durations.size();
		for (int i = 0; i < size; i++) {
			sumDuration += durations.get(i);
			sumPressure += pressures.get(i);
			sumMajorAxis += majors.get(i);
			sumMinorAxis += minors.get(i);
			sumOrientation += orientations.get(i);
		}
		
		for(int i = 0; i < gaps.size(); i++){
			sumGaps += gaps.get(i);
		}
		avgDuration = sumDuration / size;
		avgPressure = sumPressure / size;
		avgMajorAxis = sumMajorAxis / size;
		avgMinorAxis = sumMinorAxis / size;
		avgOrientation = sumOrientation / size;
		avgGap = sumGaps/gaps.size();
		sumDuration = sumPressure = sumMajorAxis = sumMinorAxis = sumOrientation = 0.0f;
		for (int i = 0; i < size; i++) {
			sumDuration += (durations.get(i) - avgDuration)
					* (durations.get(i) - avgDuration);
			sumPressure += (pressures.get(i) - avgPressure)
					* (pressures.get(i) - avgPressure);
			sumMajorAxis += (majors.get(i) - avgMajorAxis)
					* (majors.get(i) - avgMajorAxis);
			sumMinorAxis += (minors.get(i) - avgMinorAxis)
					* (minors.get(i) - avgMinorAxis);
			sumOrientation += (orientations.get(i) - avgOrientation)
					* (orientations.get(i) - avgOrientation);
		}

		for(int i = 0; i < gaps.size(); i++){
			sumGaps += (gaps.get(i) - avgGap)*(gaps.get(i) - avgGap);
		}

		stdDuration = (float) Math.sqrt(sumDuration / size);
		stdPressure = (float) Math.sqrt(sumPressure / size);
		stdMajorAxis = (float) Math.sqrt(sumMajorAxis / size);
		stdMinorAxis = (float) Math.sqrt(sumMinorAxis / size);
		stdOrientation = (float) Math.sqrt(sumOrientation / size);
		stdGap = (float)Math.sqrt(sumGaps/gaps.size());
		
		
		cvDuration = stdDuration/avgDuration;
		cvPressure = stdPressure/avgPressure;
		cvMajorAxis = stdMajorAxis/avgMajorAxis;
		cvMinorAxis = stdMinorAxis/avgMinorAxis;
		cvOrientation = stdOrientation/avgOrientation;
		cvGaps = stdGap/avgGap;
		
		float sumCv = cvDuration + cvPressure + cvMajorAxis + cvMinorAxis + cvOrientation + cvGaps;
		
		weightDuration = cvDuration/sumCv;
		weightPressure = cvPressure/sumCv;
		weightMajorAxis = cvMajorAxis/sumCv;
		weightMinorAxis = cvMinorAxis/sumCv;
		weightOrientation = cvOrientation/sumCv;
		weightGaps = cvGaps/sumCv;
		
		weightDuration = 1/weightDuration;
		weightPressure = 1/weightPressure;
		weightMajorAxis = 1/weightMajorAxis;
		weightMinorAxis = 1/weightMinorAxis;
		weightOrientation = 1/weightOrientation;
		weightGaps = 1/weightGaps;
		
		maxScore = weightDuration + weightPressure + weightMajorAxis + 
				weightMinorAxis + weightOrientation + weightGaps;
		
		
	}
}
