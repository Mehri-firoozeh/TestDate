package sample;

import org.joda.time.DateTime;

import java.text.DecimalFormat;

public class ParsedDate {

    ParsedDate(String todayDate, String format) {
        if (format.length() != todayDate.length()) {
            day = defaultDay;
            mon = defaultMonth;
            year = defaultYear;
        } else {
            SetDate(todayDate, format);
        }

        if (day == 0) day = defaultDay;
        if (mon == 0) mon = defaultMonth;
        if (year == 0) year = defaultYear;

    }

    public ParsedDate(DateTime date) {
        day = date.getDayOfMonth();
        mon = date.getMonthOfYear();
        year = date.getYear();
    }

    public int GetDay() {
        return day;
    }

    public int GetMon() {
        return mon;
    }

    public int GetYear() {
        return year;
    }

    public String ToString(String format) {
        StringBuilder toString = new StringBuilder(format);
        for (int i = 0; i < format.length(); i++) {
            if (format.charAt(i) == 'D' && toString.charAt(i) == format.charAt(i)) {
                ReplaceDay(toString, format, i);
            } else if (format.charAt(i) == 'M' && toString.charAt(i) == format.charAt(i)) {
                ReplaceMonth(toString, format, i);
            } else if (format.charAt(i) == 'Y' && toString.charAt(i) == format.charAt(i)) {
                ReplaceYear(toString, format, i);
            }
        }
        return toString.toString();


    }

    private void SetDate(String todayDate, String format) {
        for (int i = 0; i < format.length() - 1; i++) {
            if (format.charAt(i) == 'D' && day == 0) {
                int parsedDay = GetDayFrom(format, todayDate, i);
                day = parsedDay == -1 ? defaultDay : parsedDay;
            } else if (format.charAt(i) == 'M' && mon == 0) {
                int parsedMonth = GetMonthFrom(format, todayDate, i);
                mon = parsedMonth == -1 ? defaultMonth : parsedMonth;

            } else if (format.charAt(i) == 'Y' & year == 0) {
                int parsedYear = GetYearFrom(format, todayDate, i);
                year = parsedYear == -1 ? defaultYear : parsedYear;
            }
        }
    }

    private int GetYearFrom(String format, String todayDate, int start) {
        //TODO:get rid of consts
        int yearOutput = -1;
        try {
            if (start + yyyySize <= format.length()) {
                String yearString = GetyyyyFormat(format, todayDate, start);
                int yyyyDateInt = Integer.parseInt(yearString);
                yearOutput = yyyyDateInt > 9999 ? -1 : yyyyDateInt;
            }
            if (start + twoSizedString <= format.length() && yearOutput == -1) {
                String yearString = GetyyForamt(format, todayDate, start);
                yearOutput = Integer.parseInt(yearString);
            }
        } catch (Exception e) {
            return yearOutput;
        }
        return yearOutput;
    }

    private String GetyyForamt(String format, String todayDate, int start) {
        String yySubFormat = format.substring(start, start + twoSizedString);
        if (yySubFormat.equals("YY"))
            return todayDate.substring(start, start + twoSizedString);

        return "";
    }

    private String GetyyyyFormat(String format, String todayDate, int start) {
        String yyyySubFormat = format.substring(start, start + yyyySize);
        if (yyyySubFormat.equals("YYYY"))
            return todayDate.substring(start, start + yyyySize);

        return "";

    }

    private int GetMonthFrom(String format, String todayDate, int start) {
        //TODO:get rid of consts specially the char o
        int outputMonth = -1;

        if (start + twoSizedString <= format.length() && format.charAt(start + 1) == 'M') {
            String mmMonthString = GetMMFormat(format, todayDate, start);
            int mmDateInt = Integer.parseInt(mmMonthString);
            outputMonth = mmDateInt > 12 ? -1 : mmDateInt;
        }
        if (start + monSize <= format.length() && format.charAt(start + 1) == 'O' && outputMonth == -1) {
            String monthString = GetMONFormat(format, todayDate, start);
            outputMonth = GetMonth(monthString);
        }
        return outputMonth;

    }

    private String GetMONFormat(String format, String todayDate, int start) {
        String monSubstring = format.substring(start, start + monSize);
        if (monSubstring.equals("MON"))
            return todayDate.substring(start, start + monSize);

        return "";
    }

    private String GetMMFormat(String format, String todayDate, int start) {
        String mmsubstring = format.substring(start, start + twoSizedString);
        if (mmsubstring.equals("MM")) {
            return todayDate.substring(start, start + twoSizedString);
        }
        return "";
    }

    private int GetDayFrom(String format, String todayDate, int start) {
        if (start + twoSizedString > format.length()) {
            return -1;
        }

        try {
            String subStringOfFormat = format.substring(start, start + twoSizedString);
            if (subStringOfFormat.equals("DD")) {
                String subStringOfDay = todayDate.substring(start, start + twoSizedString);
                return Integer.parseInt(subStringOfDay);
            }

        } catch (Exception e) {
            return -1;
        }

        return -1;
    }

    private String GetMonth(int val) {
        switch (val) {
            case 1:
                return "JAN";
            case 2:
                return "Feb";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "SEP";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DEC";

            default:
                return "JAN";
        }
    }

    private int GetMonth(String s) {
        switch (s) {
            case "JAN":
                return 1;
            case "Feb":
                return 2;
            case "MAR":
                return 3;
            case "APR":
                return 4;
            case "MAY":
                return 5;
            case "JUN":
                return 6;
            case "JUL":
                return 7;
            case "AUG":
                return 8;
            case "SEP":
                return 9;
            case "OCT":
                return 10;
            case "NOV":
                return 11;
            case "DEC":
                return 12;

            default:
                return 0;
        }

    }

    private void ReplaceYear(StringBuilder toString, String format, int start) {
        if (start + yyyySize <= format.length() && GetyyyyFormat(toString.toString(), format, start) != "") {
            toString.replace(start, start + yyyySize, String.valueOf(year));
        } else if (start + twoSizedString <= format.length() && GetyyForamt(toString.toString(), format, start) != "") {
            toString.replace(start, start + twoSizedString, MakeTwoDigitYear(year));
        }
    }

    private boolean ValidTwoDigitYear(int year) {

        if (year < leftBoundryOfTwoDigitYear || year > rightBoundryOfTwoDigitYear)
            return false;
        return true;
    }

    private String MakeTwoDigitYear(int year) {
        if (!ValidTwoDigitYear(year))
            throw new IllegalArgumentException();
        int indexOfnumber = 2;
        return String.valueOf(year).substring(indexOfnumber);

    }

    private void ReplaceMonth(StringBuilder toString, String format, int start) {

        if (start + twoSizedString <= format.length() && GetMMFormat(toString.toString(), format, start) != "") {
            toString.replace(start, start + 2, String.valueOf(GetTwoDigit(mon)));
        } else if (start + monSize <= format.length() && GetMONFormat(toString.toString(), format, start) != "") {
            toString.replace(start, start + monSize, GetMonth(mon));
        }

    }

    private String GetTwoDigit(int month) {
        DecimalFormat formatter = new DecimalFormat("00");
        return formatter.format(month);

    }

    private void ReplaceDay(StringBuilder toString, String format, int start) {
        if (start + 2 > format.length())
            return;

        String sub = format.substring(start, start + 2);
        if (sub.equals("DD"))
            toString.replace(start, start + 2, String.valueOf(GetTwoDigit(day)));
    }

    int day;
    int mon;
    int year;
    int defaultDay = 1;
    int defaultMonth = 1;
    int defaultYear = 1900;
    int leftBoundryOfTwoDigitYear = 1975;
    int rightBoundryOfTwoDigitYear = 2074;
    int monSize = 3;
    int twoSizedString = 2;
    int yyyySize = 4;
}
