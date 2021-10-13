package com.example.test;

public class DataProvider
{
   @org.testng.annotations.DataProvider(name = "data-provider")
   public Object[][] dpMethod(){
     return new Object[][] {{"Value Passed by DataProvider class"}};
   }
}
