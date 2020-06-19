package sample;

import org.joda.time.DateTime;
import org.joda.time.Days;

//TODO:Add JavaDoc
public class TestDate {
    DateTime date;

    public TestDate() {
        date = new DateTime(1900, 1, 1, 0, 0);

    }

    public TestDate(int month, int day, int year) {
        try {
            date = new DateTime(year, month, day, 0, 0);
        } catch (Exception e) {
            date = new DateTime(1900, 1, 1, 0, 0);
        }
    }

    public TestDate(String dat, String format) {
        try {
            ParsedDate parsed = new ParsedDate(dat, format);
            date = new DateTime(parsed.GetYear(), parsed.GetMon(), parsed.GetDay(), 0, 0);
        } catch (Exception e) {
            date = new DateTime(1900, 1, 1, 0, 0);
        }
    }

    public TestDate AddMonths(int months) {
        try {
            DateTime newDate = date.plusMonths(months);
            return new TestDate(newDate.getMonthOfYear(), newDate.getDayOfMonth(), newDate.getYear());
        }
        catch (Exception e)
        {
            return new TestDate();//TODO:Make sure this meets the requrirements
        }
    }

    public int DaysUntil(TestDate dat) {
        try {
            return Days.daysBetween(date, dat.date).getDays();
        }
        catch (Exception e)
        {
            return -1; //TODO:Make sure this meets the requrirements
        }
    }

    public String ToString(String format) {
        try {
            ParsedDate parsed = new ParsedDate(date);
            return parsed.ToString(format);
        } catch (Exception e) {
            return StandardToString("YYYYddMM"); //TODO:Make sure this meets the requrirements, it's already in assumption part of doc
        }
    }

    public String StandardToString(String format) {
      /*  DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern(format)
                .toFormatter();
    */

        return date.toLocalDate().toString(format);
    }

    public static void main(String[] args) {

    }
}

