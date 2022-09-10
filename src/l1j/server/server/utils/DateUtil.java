package l1j.server.server.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
 
/**
 * Calendar ��ü ���� ��ɵ��� ��Ƴ��� ��ƿ��Ƽ Ŭ����
 *
 * @author croute
 * @since 2011.02.10
 */
public class DateUtil
{
 
    /**
     * Ķ���� ��ü�� yyyy-MM-dd HH:mm:ss ������ ���ڿ��� ��ȯ�մϴ�.
     *
     * @param cal Ķ���� ��ü
     * @return ��ȯ�� ���ڿ�
     */
    public static String StringFromCalendar(Calendar cal)
    {
        // ��¥�� ��ſ� ���ڿ��� ����
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(cal.getTime());
    }
     
    /**
     * Ķ���� ��ü�� yyyy-MM-dd������ ���ڿ��� ��ȯ�մϴ�.
     *
     * @param cal Ķ���� ��ü
     * @return ��ȯ�� ���ڿ�
     */
    public static String StringSimpleFromCalendar(Calendar cal)
    {
        // ��¥�� ��ſ� ���ڿ��� ����
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(cal.getTime());
    }
     
    /**
     * yyyy-MM-dd HH:mm:ss ������ ���ڿ��� Ķ���� ��ü�� ��ȯ�մϴ�.
     * ���� ��ȯ�� ������ ��� ���� ��¥�� ��ȯ�մϴ�.
     *
     * @param date ��¥�� ��Ÿ���� ���ڿ�
     * @return ��ȯ�� Ķ���� ��ü
     */
    public static Calendar CalendarFromString(String date)
    {
        Calendar cal = Calendar.getInstance();
         
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cal.setTime(formatter.parse(date));
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }
        return cal;
    }
     
    /**
     * yyyy-MM-dd ������ ���ڿ��� Ķ���� ��ü�� ��ȯ�մϴ�.
     * ���� ��ȯ�� ������ ��� ���� ��¥�� ��ȯ�մϴ�.
     *
     * @param date ��¥�� ��Ÿ���� ���ڿ�
     * @return ��ȯ�� Ķ���� ��ü
     */
    public static Calendar CalendarFromStringSimple(String date)
    {
        Calendar cal = Calendar.getInstance();
         
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            cal.setTime(formatter.parse(date));
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }
        return cal;
    }
}