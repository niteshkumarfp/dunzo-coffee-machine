package com.company.io.output;

/*
    Class to handle output operations
 */
public class Output {

    public static void threadSafePrintln(String s) {
        synchronized (System.out) {
            System.out.println(s);
        }
    }
}
