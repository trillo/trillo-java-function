package com.collager.trillo.util;

import org.joda.time.DateTime;

public class Test {

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    
    DateTime date = new DateTime();
    System.out.println(date.toString());
    System.out.println(date.getMillis());
    date = date.minusDays(100);
    System.out.println(date.toString());
    System.out.println(date.getMillis());

  }

}
