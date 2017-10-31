package com.dfocus;

import java.time.LocalDate;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        String s="192.168.0.*";
        String[] a = s.split(",");
        System.out.println(a.length);
        System.out.println(s.substring(0,s.lastIndexOf(".")));
        System.out.println(s.substring(s.lastIndexOf(".")+1,s.length()));
        System.out.println(!false);
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate.toString());
    }
}
