package embedded2.ESecure;

import java.util.ArrayList;

public class Attempt {
	ArrayList<Point> points;
	ArrayList<Long> gaps;
	ArrayList<Character> code;
	int count;
	
	
	Attempt(Point first){
		points = new ArrayList<Point>();
		gaps = new ArrayList<Long>();
		code = new ArrayList<Character>();
		points.add(first);
		code.add(new Character(first.getKey()));
		count = 1;
	}
	
	void addPoint(Point newPoint){
		points.add(newPoint);
		gaps.add(new Long(points.get(count).getStart().getTime() - points.get(count-1).getStart().getTime()));
		if(points.get(count) != points.get(count-1)){
			code.add(newPoint.getKey());
		}
		count++;
	}
}
