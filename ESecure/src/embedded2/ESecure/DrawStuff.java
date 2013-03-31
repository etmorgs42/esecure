package embedded2.ESecure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

public class DrawStuff extends View implements OnTouchListener {
	private static final String TAG = "DrawView";
	Random r;
	int numDots = 15;
	List<myPoint> myPoints = new ArrayList<myPoint>();
	Paint paint = new Paint();
	Vibrator v;
	WindowManager wm;
	Display display;
	Point size;
	int width, height;
	float lastx,lasty,x = 0.0f,y = 0.0f;
	char keyChar;
	Attempt newAttempt;
	RectF rect;

	public DrawStuff(Context context, Vibrator v) {
		super(context);
		setFocusable(true);
		setFocusableInTouchMode(true);
		this.v = v;
		r = new Random();
		rect = new RectF();
		this.setOnTouchListener(this);
		wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		size = new Point();

		display = wm.getDefaultDisplay();
		display.getSize(size);
		width = size.x;
		height = size.y;
		paint.setColor(Color.GREEN);
		paint.setStrokeWidth(10);
		paint.setAntiAlias(true);
	}

	@Override
	public void onDraw(Canvas canvas) {
		// paint.setColor(Color.rgb(r.nextInt(4)*64, r.nextInt(4)*64,
		// r.nextInt(4)*64));
		paint.setStrokeWidth(10);

		for (myPoint myPoint : myPoints) {
			// paint.setColor(Color.rgb(myPoint.r, myPoint.g, myPoint.b));
			canvas.drawCircle(myPoint.x, myPoint.y, 5, paint);
			// Log.d(TAG, "Painting: "+myPoint);
		}

		for (int i = 1; i < myPoints.size(); i++) {

			canvas.drawLine(myPoints.get(i - 1).x, myPoints.get(i - 1).y, myPoints.get(i).x, myPoints.get(i).y, paint);
		}
		while (myPoints.size() > numDots) {
			myPoints.remove(0);
		}

		// Vibrate for 500 milliseconds
		// v.vibrate(15);
		// Draw boxes
		canvas.drawLine((float) 0, (float) (height * .5), (float) width, (float) (height * .5), paint);
		canvas.drawLine((float) 0, (float) (height * .6), (float) width, (float) (height * .6), paint);
		canvas.drawLine((float) 0, (float) (height * .7), (float) width, (float) (height * .7), paint);
		canvas.drawLine((float) 0, (float) (height * .8), (float) width, (float) (height * .8), paint);
		canvas.drawLine((float) 0, (float) (height * .9), (float) width, (float) (height * .9), paint);
		canvas.drawLine((float) (width / 3), (float) (height * .5), (float) (width / 3), (float) (height * .9), paint);
		canvas.drawLine((float) (width - 5), (float) (height * .5), (float) (width - 5), (float) (height * .9), paint);
		canvas.drawLine((float) (5), (float) (height * .5), (float) (5), (float) (height * .9), paint);
		canvas.drawLine((float) (width / 3 * 2), (float) (height * .5), (float) (width / 3 * 2), (float) (height * .9), paint);
		paint.setTextSize(100);
		canvas.drawText("1", (float) (width * .13), (float) (height * .58), paint);
		canvas.drawText("2", (float) (width / 3 + .13 * width), (float) (height * .58), paint);
		canvas.drawText("3", (float) (2 * width / 3 + .13 * width), (float) (height * .58), paint);
		canvas.drawText("4", (float) (width * .13), (float) (height * .68), paint);
		canvas.drawText("5", (float) (width / 3 + .13 * width), (float) (height * .68), paint);
		canvas.drawText("6", (float) (2 * width / 3 + .13 * width), (float) (height * .68), paint);
		canvas.drawText("7", (float) (width * .13), (float) (height * .78), paint);
		canvas.drawText("8", (float) (width / 3 + .13 * width), (float) (height * .78), paint);
		canvas.drawText("9", (float) (2 * width / 3 + .13 * width), (float) (height * .78), paint);
		canvas.drawText("0", (float) (width / 3 + .13 * width), (float) (height * .88), paint);
		canvas.drawText("E", (float) (2 * width / 3 + .13 * width), (float) (height * .88), paint);
		canvas.drawOval(rect, paint);
	}

	public boolean onTouch(View view, MotionEvent event) {
		// if(event.getAction() != MotionEvent.ACTION_DOWN)
		// return super.onTouchEvent(event);
		Log.v("PRESSURE", "" + event.getSize());

		// Log.v
		myPoint myPoint = new myPoint();
		x = myPoint.x = event.getX();
		y = myPoint.y = event.getY();
		myPoint.r = r.nextInt(255);
		myPoint.g = r.nextInt(255);
		myPoint.b = r.nextInt(255);
		myPoints.add(myPoint);
		Log.d("POINT LOCATION", "" + myPoint);
		float major, minor;
		major = event.getTouchMajor(0);
		minor = event.getTouchMinor(0);
		rect = myOval(minor, major, event.getX(), event.getY());
		Log.v("MAJOR/MINOR", "" + major + ":::" + minor);
		invalidate();
		keyChar = calcButtons(event.getX(), event.getY());
		
		if(keyChar == 'E'){
			if(newAttempt != null){
				
			}
		}else if(keyChar == 'x'){
			
		}
		
		return true;
	}

	private char calcButtons(float x, float y) {
		// TODO Auto-generated method stub
		int a = (int) x * 3 / width;

		if (a == 0) {
			if (y >= 0.5 * height && y < 0.6 * height) {
				Log.v("BUTTONPRESSED", "1");
				return '1';
			}
			if (y >= 0.6 * height && y < 0.7 * height) {
				Log.v("BUTTONPRESSED", "4");
				return '4';
			}
			if (y >= 0.7 * height && y < 0.8 * height) {
				Log.v("BUTTONPRESSED", "7");
				return '7';
			}
			if (y >= 0.8 * height && y < 0.9 * height) {
				Log.v("BUTTONPRESSED", "BlankThing");
				return 'x';
			}
		} else if (a == 1) {
			if (y >= 0.5 * height && y < 0.6 * height) {
				Log.v("BUTTONPRESSED", "2");
				return '2';
			}
			if (y >= 0.6 * height && y < 0.7 * height) {
				Log.v("BUTTONPRESSED", "5");
				return '5';
			}
			if (y >= 0.7 * height && y < 0.8 * height) {
				Log.v("BUTTONPRESSED", "8");
				return '8';
			}
			if (y >= 0.8 * height && y < 0.9 * height) {
				Log.v("BUTTONPRESSED", "0");
				return '0';
			}
		} else if (a == 2) {
			if (y >= 0.5 * height && y < 0.6 * height) {
				Log.v("BUTTONPRESSED", "3");
				return '3';
			}
			if (y >= 0.6 * height && y < 0.7 * height) {
				Log.v("BUTTONPRESSED", "6");
				return '6';
			}
			if (y >= 0.7 * height && y < 0.8 * height) {
				Log.v("BUTTONPRESSED", "9");
				return '9';
			}
			if (y >= 0.8 * height && y < 0.9 * height) {
				Log.v("BUTTONPRESSED", "E");
				return 'E';
			}
		}
		return 'x';
	}

	class myPoint {
		float x, y;
		int r, g, b;

		@Override
		public String toString() {
			return x + ", " + y;
		}
	}

	public RectF myOval(float width, float height, float x, float y) {
		float halfW = width / 2;
		float halfH = height / 2;
		return new RectF(x - halfW, y - halfH, x + halfW, y + halfH);
	}

	public void setColor(int i) {
		// TODO Auto-generated method stub
		switch (i) {
		case 1:
			paint.setColor(Color.RED);
			break;
		case 2:
			paint.setColor(Color.rgb(255, 102, 0));
			break;
		case 3:
			paint.setColor(Color.YELLOW);
			break;
		case 4:
			paint.setColor(Color.GREEN);
			break;
		case 5:
			paint.setColor(Color.BLUE);
			break;
		case 6:
			paint.setColor(Color.rgb(204, 0, 255));
			break;
		}
		invalidate();
	}

}