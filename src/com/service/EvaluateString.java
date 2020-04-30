package com.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class EvaluateString {

    public static void main(String[] args) {
    	//int a = 30 - 12 / (2 * 5 ) + 1;
    	try{
    	String foo = 	"((3+3) / 2 * 3) / ((8+16) * 4)";
    	ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        //String foo = "40+2";
        System.out.println(engine.eval(foo));
//    	af = "'" + af + "'";
//    	System.out.println(af);
    	//double ab = (double)af;
//    	double a = (((double) 3 +(double) 3) / (double)2 * (double)3) / (((double)8+(double)16) * (double)4);
//    	System.out.println(a);
    	}catch(Exception e){
    	System.out.println(e.getMessage());	
    	}
//    	String orgString = "(3+3) / 2 * 3 / ((8+6) * 4)";
//        System.out.println(findValueInBraces(orgString));

    }

    public static String findValueInBraces(String finalStr) {

        while (finalStr.contains("(") && finalStr.contains(")")) {
            int fIndex = finalStr.indexOf("(");
            int nIndex = finalStr.indexOf(")");
            String subString = finalStr.substring(fIndex + 1, nIndex);
            finalStr = finalStr.substring(0, fIndex)
                    + calculate(subString)
                    + finalStr.substring(nIndex + 1,
                            finalStr.length());
        }
        return calculate(finalStr);

    }

    public static String calculate(String finalString) {

        while (finalString.contains("(") && finalString.contains(")")) {
            findValueInBraces(finalString);
        }
        while (!isNum(finalString)) {
            List<Integer> positions = getOperandPosition(finalString);
            int pos = positions.get(0);
            if (positions.size() >= 2 && positions.get(1) != null) {
                int nxtPos = positions.get(1);
                finalString = getValue(finalString.substring(0, nxtPos), pos)
                        + finalString.substring(nxtPos, finalString.length());
            } else {
                finalString = getValue(
                        finalString.substring(0, finalString.length()), pos);
            }
        }
        return finalString;

    }

    public static boolean isNum(String str) {
        if (str.contains("+") || str.contains("-") || str.contains("*")
                || str.contains("/")) {
            return false;
        }
        return true;
    }

    public static List<Integer> getOperandPosition(String str) {

        List<Integer> integers = new ArrayList<Integer>();

        if (str.contains("+")) {
            integers.add(str.indexOf("+"));
        }

        if (str.contains("-")) {
            integers.add(str.indexOf("-"));
        }

        if (str.contains("*")) {
            integers.add(str.indexOf("*"));
        }

        if (str.contains("/")) {
            integers.add(str.indexOf("/"));
        }

        Collections.sort(integers);
        return integers;
    }

    public static String getValue(String str, int pos) {
        double finalVal = 0;
        double a = Double.parseDouble(str.substring(0, pos));
        double b = Double.parseDouble(str.substring(pos + 1, str.length()));
        char c = str.charAt(pos);

        if (c == '*') {
            finalVal = a * b;
        } else if (c == '/') {
            finalVal = a / b;
        } else if (c == '+') {
            finalVal = a + b;
        } else if (c == '-') {
            finalVal = a - b;
        }
        return String.valueOf(finalVal);
    }
}