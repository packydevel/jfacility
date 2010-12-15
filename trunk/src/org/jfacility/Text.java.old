package org.jfacility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author luca
 */
public class Text {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);

    public static Date convertStringToDate(String d) throws ParseException{
        return sdf.parse(d);
    }

    public static String convertDateToString(Date d) throws ParseException{
        return sdf.format(d);
    }
}