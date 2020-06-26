import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyDate {
	
	private int year, month, day;
	private static String[] strMonths = {"Jan","Feb","Mar","Apr","May","Jun",
									"Jul","Aug","Sep","Oct","Nov","Dec"};
	private static String[] strDays = {"Sunday","Monday","Tuesday","Wednesday",
								"Thursday","Friday","Saturday"};
	private static int[] daysInMonths = {31,28,31,30,31,30,31,31,30,31,30,31};
	
	public static boolean isLeapYear(int year) {
		boolean isLeapYear = false;
		if (year >= 1 && year <= 9999) {
		if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))
			isLeapYear = true;
		}
		return isLeapYear;
	}
	
	public static boolean isValidDate(int year, int month, int day) {
		boolean isValid = false;
		
		// Check if year is between 1 and 9999
		if (year >= 1 && year <= 9999) {
			// Check if month is between 1 and 12
			if (month >= 1 && month <= 12) {
				// Check if day is between 1 and monthly days
				int days = daysInMonths[month - 1];
				
				// Check for leap year
				if (isLeapYear(year) && month == 2)
					days ++;
				if (day >= 1 && day <= days) {
					isValid = true;
				}
			}
		}
		
		return isValid;
	}
	
	public static int getDayOfWeek(int year, int month, int day) throws ParseException {
		int W = -1;
		if (isValidDate(year, month, day)) {
			Calendar c = Calendar.getInstance();
			Date date = new SimpleDateFormat("dd/M/yyyy").parse(day + "/" + month + "/" + year);
			c.setTime(date);
			W = c.get(Calendar.DAY_OF_WEEK);
		}
		
		return W;
	}
	
	public MyDate(int year, int month, int day) {
		setDate(year,month,day);
	}
	
	public void setDate(int year, int month, int day) {
		if (isValidDate(year, month, day)) {
			this.year = year;
			this.month = month;
			this.day = day;
		} else {
			// Throw exception
			throw new IllegalArgumentException("Invalid year, month, or day!");
		}
	}
	
	public int getYear() {
		return year;
	}
	
	public int getMonth() {
		return month;
	}
	
	public int getDay() {
		return day;
	}
	
	public void SetYear(int year) {
		if (year >= 1 && year <= 9999)
			{ this.year = year; } else {
				// Throw exception
				throw new IllegalArgumentException("Invalid year!");
			}
	}
	
	public void SetMonth(int month) {
		if (month >= 1 && month <= 12)
		{ this.month = month; } else {
			// Throw exception
			throw new IllegalArgumentException("Invalid month!");
		}
	}
	
	public void SetDay(int day) {
		if (day >= 1) { 
			// Check if day is between 1 and monthly days
			int dayMax = daysInMonths[getMonth() - 1];
			// Check for leap year
			if (isLeapYear(getYear()) && month == 2)
				dayMax ++;
			if (day >= 1 && day <= dayMax) {
				this.day = day; 
			} else {
				// Throw exception
				throw new IllegalArgumentException("Invalid day!");
			}
		}
	}
	
	@Override
	public String toString() {
		String value = "default";
		try {
			value = strDays[getDayOfWeek(getYear(), getMonth(), getDay()) - 1] + " " +
					getDay() + " " +
					strMonths[getMonth() - 1] + " " +
					getYear();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return value;
	}
	
	public MyDate nextDay() {
		int dayMax = daysInMonths[getMonth() - 1];
		if (isLeapYear(getYear()) && month == 2)
			dayMax ++;
		
		if (getDay() + 1 <= dayMax) {
				this.SetDay(getDay() + 1); 
			} else {
				this.nextMonth();
			}
		return this;
	}
	
	public MyDate nextMonth() {
		// New Month
		if (getMonth() < 12) {
			this.SetMonth(getMonth() + 1);
			this.SetDay(1);
		}
		else {
			this.nextYear();
		}
		return this;
	}
	
	public MyDate nextYear() {
		// New Year
		this.SetYear(getYear() + 1);
		this.SetMonth(1);
		this.SetDay(1);
		return this;
	}
	
	public MyDate previousDay() {
		if (getDay() > 1) {
			this.SetDay(getDay() - 1);
		} else {
			this.previousMonth();
		}
		return this;
	}
	
	public MyDate previousMonth() {
		if (getMonth() > 1) {
			this.SetMonth(getMonth() - 1);
		} else {
			this.previousYear();
		}
		return this;
	}
	
	public MyDate previousYear() {
		this.SetYear(getYear() - 1);
		this.SetMonth(12);
		this.SetDay(daysInMonths[getMonth() - 1]);
		return this;
	}
	
	public static void main(String[] args) {
		MyDate mydate = new MyDate(2018, 12, 28);
		while (!mydate.toString().equals("Thursday 29 Aug 2019")) {
			System.out.println(mydate.toString());
			mydate.nextDay();
		}
	}
}
