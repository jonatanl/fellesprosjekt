package util;

public class Time {
	
	public static int getHours(String time){
		return Integer.parseInt(time.substring(0, 2));
	}
	
	public static int getMinutes(String time){
		return Integer.parseInt(time.substring(3, 5));
	}
	
	public static String toTimeString(int hours, int minutes){
		String h = "";
		String m = "";
		if (hours < 10){
			h = "0" + Integer.toString(hours); 
		}
		else{
			h = Integer.toString(hours);
		}
		
		if (minutes < 10){
			m = "0" + Integer.toString(minutes);
		}
		else{
			m = Integer.toString(minutes);
		}
		
		return h + ":" + m;
			
	}
	
	public static String subtractTimes(String time1, String time2){
    	int h1 = getHours(time1);
    	int m1 = getMinutes(time1);
    	int h2 = getHours(time2);
    	int m2 = getMinutes(time2);
    	
    	int mResult = m1 - m2;
    	int hResult = h1 - h2;
    	
    	if (mResult < 0){
    		mResult += 60;
    		hResult -= 1;
    	}
    	
    	return toTimeString(hResult, mResult);
    }
	
	public static String addTimes(String time1, String time2){
		int h1 = getHours(time1);
    	int m1 = getMinutes(time1);
    	int h2 = getHours(time2);
    	int m2 = getMinutes(time2);
    	
    	int mResult = m1 + m2;
    	int hResult = h1 + h2;
    	
    	if (mResult > 59){
    		mResult -= 60;
    		hResult +=1;
    	}
    	
    	return toTimeString(hResult, mResult);
	}
}
