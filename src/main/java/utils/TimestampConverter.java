package utils;

import java.sql.Timestamp;
import java.util.Date;

public class TimestampConverter {

	public static Date timestampToDate(Timestamp time) {
		return new Date(time.getTime());
	}
	
	public static Timestamp dateToTimestamp(Date time) {
		return new Timestamp(time.getTime());
	}
}
