package edu.gatech.seclass.encode;
import java.io.*;
import java.lang.management.BufferPoolMXBean;
import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.nio.file.Paths;
import java.nio.file.Path;

public class Main {

/*
Empty Main class for Individual Project 1 in GT CS6300
 */

    public static void main(String[] args) {
        //System.out.println("TESTING");
        // Empty Skeleton Method
        int args_length = args.length;
        String file_content = getFileContent(args[args_length - 1]);
        String filename = args[args_length - 1];
        String orig_file_content = "";
        String new_string = "";
        FileWriter writer = null;

        try {
            File f = new File(filename);
            BufferedReader br = new BufferedReader(new FileReader(f));
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            String read_line = br.readLine();
            while(read_line != null) {

                if(args_length == 0)
                {
                    new_string = encode(read_line,13);
                }
                else
                {
                    for(int i = 0; i < args_length ; i ++)
                    {
                        if(args[i] == "-d")
                        {
                            if(isInt(args[i + 1]))
                            {
                                new_string = remove_dups(read_line, Integer.parseInt(args[i+1]));
                            }
                        }
                        if(args[i] == "-r")
                        {
                            if(isInt(args[i + 1]))
                            {
                                new_string = right_rotation(read_line, Integer.parseInt(args[i+1]));
                            }
                        }
                        else if(args[i] == "-l")
                        {
                            if(isInt(args[i + 1]))
                            {
                                new_string = left_rotation(read_line, Integer.parseInt(args[i+1]));
                            }
                        }

                        if(args[i] == "-c")
                        {
                            if(isString(args[i+1]))
                            {
                                new_string = reverse_capitalization(read_line, args[i+1]);
                            }
                        }

                        if(args[i] == "-e")
                        {
                            if(isString(args[i+1]))
                            {
                                new_string = encode(read_line,Integer.parseInt(args[i+1]));
                            }

                        }

                    }
                }

            }

        }
        catch (IOException e)
        {
            System.out.println(e);
        }

        System.out.println(new_string);

    }

    public static boolean isInt(String str)
    {
        try{
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    public static boolean isString(String str)
    {
        try{
            String test = (String) str;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static String getFileContent(String filename) {
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    private static String remove_dups(String str, int val)
    {
        int count = 1;
        for(int i = 0; i < str.length(); i++)
        {
            for(int j = i + 1; j < str.length(); j++)
            {
                if(str.charAt(i) == str.charAt(j))
                {
                    count ++;
                    if(count == val)
                    {
                        char c = str.charAt(i);
                        str = str.replace(String.valueOf(c),"");
                    }
                }
            }
        }
        return str;
    }

    private static String encode(String str, int val)
    {
        int [] rot = new int[str.length()];
        int ascii = 1;
        for(int i = 0;i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isLetter(c)) {
                ascii = (int) c;
            } else
                ascii = Integer.parseInt(String.valueOf(c));

            if (ascii > 64 && ascii < 91) {
                ascii += val;
                if (ascii > 90) {
                    ascii += -90 + 64;
                }
                if (ascii < 65) {
                    ascii += -64 + 90;
                }
                ascii += 27;

            } else if (ascii > 96 && ascii < 123) {
                ascii += val;
                if (ascii > 122) {
                    ascii += -122 + 96;
                }
                if (ascii < 97) {
                    ascii += -96 + 122;
                }

            }
            rot[i] = ascii;
        }
        String result = Arrays.toString(rot);
        return result;
    }
    private static String reverse_capitalization(String str, String sub_string)
    {
        char[] test = sub_string.toCharArray();
        for(int i = 0; i < test.length; i ++)
        {
            if (Character.isLowerCase(test[i]))
            {
                test[i] = Character.toUpperCase(test[i]);
            }
            else if (Character.isUpperCase(test[i]))
            {
                test[i] = Character.toLowerCase(test[i]);
            }
        }
        String final_str = new String(test);
        return final_str;
    }

    private static String right_rotation(String str, int val)
    {
        char[] test = str.toCharArray();
        int str_len = test.length;
        val = val % str_len;
        reverse(test,0,str_len-val-1);
        reverse(test,str_len-val,str_len-1);
        reverse(test,0,str_len-1);
        String final_str = new String(test);
        return final_str;
    }

    private static String left_rotation(String str, int val)
    {
        int len = str.length();
        int left_val = len - val;
        String final_str = right_rotation(str, left_val);
        return final_str;
    }

    private static void reverse(char[] str, int start, int end)
    {
        while (start < end) {
            char temp = str[start];
            str[start] = str[end];
            str[end] = temp;
            start++;
            end--;
        }
    }

    private static void usage() {
        System.err.println("Usage: encode [-n [int]] [-r int | -l int] [-c string] <filename>");
    }

}

