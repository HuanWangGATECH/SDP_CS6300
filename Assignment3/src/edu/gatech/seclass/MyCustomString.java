package edu.gatech.seclass;

public class MyCustomString implements MyCustomStringInterface{

    private String string;

    public void setString(String string){
        this.string = string;
    }

    public String getString(){
        return string;
    }

    public int countDuplicates() {
        String str = getString();
        int count = 0;
        char [] str_array = str.toCharArray();
        for (int i = 0; i < str.length(); i++)
        {
            for (int j = 0; j < str.length(); j++)
            {
                if (str_array[i] == str_array[j])
                {
                    if (j-i == 1)
                    {
                        count ++;
                    }
                    break;
                }
            }
        }
        return count;
    }

    public String addDigits(int n, boolean positive) throws NullPointerException,IllegalArgumentException {
        String str = getString();
        if (str == null)
        {
            throw new NullPointerException();
        }
        if (n > 9 || n <= 0)
        {
            throw new IllegalArgumentException();
        }

        char [] str_array = str.toCharArray();
        for (int i = 0; i < str.length(); i ++)
        {
            if (Character.isDigit(str_array[i]))
            {
                if(positive)
                {
                    int a = Character.getNumericValue(str_array[i]);
                    a = (a + n) % 10;
                    //inp[i] = ((inp[i] + n) % 10);
                    str_array[i] = Character.forDigit(a,10);
                }
                else
                {
                    int b = Character.getNumericValue(str_array[i]);
                    if(b - n >= 0)
                    {
                        b = (b-n);
                        str_array[i] = Character.forDigit(b,10);
                    }
                    else
                    {
                        b = 10 + b - n;
                        str_array[i] = Character.forDigit(b,10);
                    }
                }
            }

        }
        String k = new String(str_array);
        return k;
    }

    public void flipLetttersInSubstring(int startPosition, int endPosition) throws NullPointerException,MyIndexOutOfBoundsException,IllegalArgumentException {

        String str = getString();
        if (str == null)
        {
            throw new NullPointerException();
        }
        if ((startPosition <=0 || endPosition > str.length()) && str != null)
        {
            throw new MyIndexOutOfBoundsException();
        }
        if (startPosition > endPosition && str != null && startPosition >0 && endPosition <= str.length())
        {
            throw new IllegalArgumentException();
        }
        if (startPosition == 1)
        {
            String str2 = str.substring(startPosition,endPosition + 1);
            String str3 = str.substring(endPosition + 1, str.length());
            //System.out.println(str1);
            //System.out.println(str2);
            //System.out.println(str3);
            //System.out.println(str);
            char [] str_array = str2.toCharArray();
            int r = str_array.length - 1;
            int l = 0;
            while(l<r)
            {
                if (!Character.isAlphabetic(str_array[l]))
                {
                    l++;
                }
                else if (!Character.isAlphabetic(str_array[r]))
                {
                    r--;
                }
                else
                {
                    char tmp = str_array[l];
                    str_array[l] = str_array[r];
                    str_array[r] = tmp;
                    l++;
                    r--;
                }
            }
            String newstr2 = new String(str_array);
            //System.out.println(str1);
            //System.out.println(newstr2);
            //System.out.println(str3);
            String finalstr = newstr2 + str3;
            System.out.println(finalstr);
        }
        else if (endPosition == str.length())
        {
            String str1 = str.substring(0,startPosition);
            String str2 = str.substring(startPosition,endPosition);
            //System.out.println(str1);
            //System.out.println(str2);
            //System.out.println(str3);
            //System.out.println(str);
            char [] str_array = str2.toCharArray();
            int r = str_array.length - 1;
            int l = 0;
            while(l<r)
            {
                if (!Character.isAlphabetic(str_array[l]))
                {
                    l++;
                }
                else if (!Character.isAlphabetic(str_array[r]))
                {
                    r--;
                }
                else
                {
                    char tmp = str_array[l];
                    str_array[l] = str_array[r];
                    str_array[r] = tmp;
                    l++;
                    r--;
                }
            }
            String newstr2 = new String(str_array);
            //System.out.println(str1);
            //System.out.println(newstr2);
            //System.out.println(str3);
            String finalstr = str1 + newstr2;
            System.out.println(finalstr);

        }
        else if (startPosition == 0 && endPosition == str.length())
        {
            String str1 = str.substring(0,startPosition);
            String str2 = str.substring(startPosition,endPosition);
            String str3 = str.substring(endPosition + 1, str.length());
            //System.out.println(str1);
            //System.out.println(str2);
            //System.out.println(str3);
            //System.out.println(str);

            char [] str_array = str2.toCharArray();
            int r = str_array.length - 1;
            int l = 0;
            while(l<r)
            {
                if (!Character.isAlphabetic(str_array[l]))
                {
                    l++;
                }
                else if (!Character.isAlphabetic(str_array[r]))
                {
                    r--;
                }
                else
                {
                    char tmp = str_array[l];
                    str_array[l] = str_array[r];
                    str_array[r] = tmp;
                    l++;
                    r--;
                }
            }
            String newstr2 = new String(str_array);
            //System.out.println(str1);
            //System.out.println(newstr2);
            //System.out.println(str3);
            String finalstr = str1 + newstr2 + str3;
            System.out.println(finalstr);
            //REFERENCES
            //https://www.geeksforgeeks.org/reverse-an-array-without-affecting-special-characters/
            //https://www.javatpoint.com/java-int-to-char
            //https://stackoverflow.com/questions/7655127/how-to-convert-a-char-array-back-to-a-string
        }

    }



}
