package com.gdglc.hzqmes.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.lang.reflect.Array;
import java.util.StringTokenizer;


/**
 * Created by Leyenda on 2019/2/14.
 */
@Slf4j
public class StringHelper {
    public final static String BR = "<BR>";
    public final static String BR_escape = "&lt;BR&gt;";
    public final static String LINE_BREAK = System.getProperty("line.separator");

    public static String pad(String aString, int aLength)
    {
        return fill(aString, ' ', aLength, "R");
    }

    public static String fill(String aString, char aReplace, int aLength)
    {
        return fill(aString, aReplace, aLength, "R");
    }

    public static String fill(String aString, char aReplace, int aLength, String aLeftRight)
    {
        if (aString == null) return aString;

        int remaining = aLength - aString.length();
        if (remaining <= 0) {
            return aString;
        }

        for (int i = 0; i < remaining; i ++)
        {
            if (aLeftRight.equals("L"))
            {
                aString = String.valueOf(aReplace) + aString;
            }
            else
            {
                aString = aString + String.valueOf(aReplace);
            }
        }

        return aString;
    }

    public static String replaceStr(String aString, String aFind, String aReplace)
    {
        if (aString==null) return aString;  //PL 12 Oct 2004

        StringBuffer input = null;

        try
        {
            input = new StringBuffer(aString.trim());

            int index = input.toString().indexOf(aFind);
            int lengthOfFind = aFind.length();
            while (index != -1)
            {
                input = input.replace(index, index + lengthOfFind, aReplace);
                index = input.toString().indexOf(aFind);
            }
        }
        finally
        {
        }

        return input.toString();
    }

    public static String breakLine(String aString, int aLength)
    {
        String result = "";
        String temp = "";

        if (aString == null) return "";  //PL 12 Oct 2004
        aString = aString.trim();

        aString = replaceStr(aString, "  ", " ");

        int i = -1;

        while (aString.length() > 0)
        {
            for (i = aLength; i >= 1; i--)
            {
                try
                {
                    if (aString.substring(i, i + 1).trim().equals(""))
                    {
                        break;
                    }
                }
                catch (StringIndexOutOfBoundsException e)
                {
                    break;
                }
            }

            if (i <= 0)
            {
                i = aLength - 1;
            }

            if (aString.length() <= aLength)
            {
                result += fill(aString, ' ' , aLength);
                aString = "";
            }
            else
            {

                if (i==aLength)
                {
                    result += fill(aString.substring(0, i), ' ', aLength);
                }
                else
                {
                    result += fill(aString.substring(0, i + 1), ' ', aLength);
                }

                aString = aString.substring(i + 1).trim();
            }
        }

        return result;
    }

    //Prudence
    /**
     * @return String with specified delimitor included
     */
    public static String breakLine(String aString, int aLength, String delimitor)
    {
        String result = "";
        String temp = "";

        if (aString == null) {
            return "";
        }
        if (delimitor == null) {
            delimitor = "";
        }

        if (aString.trim().indexOf(" ")<=0) {
            return aString;
        }

        int i = -1;

        if (aString.length() < aLength)
        {
            return aString;
        }

        while (aString.length() > 0)
        {
            for (i = aLength; i >= 1; i--)
            {
                try
                {
                    if (aString.substring(i, i + 1).trim().equals(""))
                    {
                        String line = aString.substring(0, i);
                        result += (line + delimitor);
                        aString = aString.substring(i + 1);
                        break;
                    }
                }
                catch (StringIndexOutOfBoundsException e)
                {
                    break;
                }
            }

            if (aString.length() <= aLength)
            {
                result += (aString + delimitor);
                break;
            }
        }

        return result;
    }

    /**Added by Gorlone Su on 18 Jan 2005, copied from Eric's workspace
     * To split the string into String[]
     */
    public static String[] splitString(String stringList, String delimiter)
    {
        if (stringList == null){
            return null;
        }
        if (stringList.length() == 0){
            return null;
        }

        StringTokenizer st = new StringTokenizer(stringList, delimiter);
        String[] result = new String[st.countTokens()];
        int i = 0;
        while (st.hasMoreTokens())
        {
            result[i] = st.nextToken();
            i++;
        }
        return result;
    }
    /** End - Added by Gorlone Su on 18 Jan 2005 **/

    public static boolean isEqual(String value1, String value2)
    {
        if (value1 == null)
        {
            if (value2 == null)
            {
                return true ;
            }
            else
            {
                return false ;
            }
        }
        if (value2 == null)
        {
            if (value1 == null)
            {
                return true ;
            }
            else
            {
                return false ;
            }
        }
        return value1.equals(value2) ;
    }

    /**
     * check if string value is empty
     *
     * Created By: Jerry Huang
     * Created On: Aug 19, 2011
     */
    public static boolean isEmpty(String valueStr)
    {
        if(valueStr == null){
            return true;
        }
        if("".equals(valueStr)){
            return true;
        }
        return false;
    }

    public static String replaceCharWithString(String aSrcStr, char aChar, String aString) {

        StringBuffer buffer = new StringBuffer(aSrcStr);
        for (int i = (buffer.length()-1); i >= 0; i--) {
            if (buffer.charAt(i) == aChar) {
                buffer.replace(i, i + 1, aString);
            }
        }

        return buffer.toString();
    }

    public static String replace(String aSrcStr, String anOldStr, String aNewStr) {

        String tempStr = "", returnStr = "";
        int index = 0;

        tempStr = aSrcStr;

        index = tempStr.indexOf(anOldStr);
        while (index != -1) {
            returnStr = returnStr + tempStr.substring(0, index) + aNewStr;
            tempStr = tempStr.substring(index + anOldStr.length());
            index = tempStr.indexOf(anOldStr);
        }

        returnStr = returnStr + tempStr;

        return returnStr;
    }

    /**
     *
     * @param prefix
     * @param num
     * @param maxLen
     * @return
     */
    public static String transformKey(String prefix, String num, int maxLen)
    {
        if (maxLen <1) {
            maxLen = 15;
        }

        StringBuffer outputKey = new StringBuffer(prefix);
        int numLen = num.length();
        int replaceLen = maxLen - numLen;
        for (int i = 0; i < replaceLen; i++)
        {
            outputKey.append("0");
        }
        outputKey.append(num);

        return outputKey.toString();
    }

    /**
     * Trim spaces off the left side of this string.
     *
     * @param s The string to trim.
     *
     * @return  The trimmed string.
     */
    // Jeremy Cheung, 2004-02-04, create method
    public static String ltrim(String s) {
        StringBuffer buf = new StringBuffer(s) ;
        for (int n = 0; n < buf.length() && buf.charAt( n ) == ' ';) {
            buf.delete(0, 1) ;
        }
        return buf.toString();
    }

    /**
     * Trim spaces off the right side of this string.
     *
     * @param s The string to trim.
     *
     * @return  The trimmed string.
     */
    // Jeremy Cheung, 2004-02-04, create method
    public static String rtrim( String s ) {
        StringBuffer buf = new StringBuffer(s) ;
        for (int k = buf.length() - 1;
             k >= 0 && buf.charAt( k ) == ' ';
             k = buf.length() - 1) {
            buf.delete(buf.length() - 1, buf.length()) ;
        }

        return buf.toString() ;
    }

    // Jeremy Cheung, 2004-02-04, consolidate method created by SG's Joshua Lee (JL)
    // JL - Pass in colValue and call ConverttoHTML function and call convertString
    public static String ConvertToHTML(String aSrcString){
        return convertString (aSrcString, "HTML","en");
    }

    // JL - Pass in colValue and call ConverttoFOP function and call convertString
    public static String ConvertToFOP(String aSrcString){
        return convertString(aSrcString, "FOP","en");
    }

    /**
     * Method convertString.
     * @param aSrcString
     * @param aMode
     * @param aLocale
     */
    public static String convertString(String aSrcString, String aMode, String aLocale) {
        StringBuffer filtered = new StringBuffer();
        String aResultString = aSrcString;

        if (aMode.equals("HTML")) {
            if (aSrcString.indexOf('<') != -1 |
                    aSrcString.indexOf('>') != -1 |
                    aSrcString.indexOf('"') != -1 |
                    aSrcString.indexOf('&') != -1 |
                    aSrcString.indexOf('\\') != -1
                    ) {

                for (int i=0; i < aSrcString.length(); i++) {
                    char character = aSrcString.charAt(i);
                    if (character =='<') {
                        filtered.append("&lt;");
                    } else if (character == '>') {
                        filtered.append("&gt;");
                    } else if (character == '"') {
                        filtered.append("&quot;");
                    } else if (character == '&') {
                        filtered.append("&amp;");
                    } else if (character == '\'') {
                        filtered.append("&#39;");
                    } else {
                        filtered.append(character);
                    }
                }
                aResultString = filtered.toString();
            }
        } else if (aMode.equals("FOP")) {

            if (aSrcString.indexOf('<') != -1 |
                    aSrcString.indexOf('>') != -1
//    aSrcString.indexOf('"') != -1
                    ) {

                if (aLocale.startsWith("zh") | aLocale.startsWith("ja")) {
                    aSrcString =  utf8ToUnicode(aSrcString);
                }

                for (int i=0; i < aSrcString.length(); i++) {
                    char character = aSrcString.charAt(i);
                    if (character =='<') {
                        filtered.append("&lt;");
                    } else if (character == '>') {
                        filtered.append("&gt;");
//              } else if (character == '"') {
//               filtered.append("&quot;");
                    } else {
                        filtered.append(character);
                    }
                }
                aResultString = filtered.toString();
            }
        }

        return aResultString;
    }


    /**
     * Method convertString.
     * @param utf8Str
     */
    public static String utf8ToUnicode(String utf8Str) {
        String unicodeStr = "";
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeBytes(utf8Str);
            dos.close();
            byte [] b1 = baos.toByteArray();
            byte [] b2 = new byte[Array.getLength(b1) + 2];
            Integer i1 = new Integer(0);
            Integer i2 = new Integer(Array.getLength(b1));
            b2[0] = i1.byteValue();
            b2[1] = i2.byteValue();
            for (int i = 0; i<Array.getLength(b1); i++)
                b2[i+2] = b1[i];

            ByteArrayInputStream bais = new ByteArrayInputStream(b2);
            DataInputStream dis = new DataInputStream(bais);
            unicodeStr = dis.readUTF();
            dis.close();
        } catch (IOException e) {
            log.error("error",e);
            System.out.println("error: " + e.getMessage());
        }
        return unicodeStr;
    }
    // Jeremy Cheung, 2004-02-03, End of consolidate
}
