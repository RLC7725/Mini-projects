import java.io.*;
import java.util.*;

class Crisscross
{
public static void main(String args[]) throws IOException
{
Scanner sc=new Scanner(System.in);
int i,j;
System.out.println("Enter the string");
 char[] str = sc.next().toCharArray();
for(i=0;i<str.length;i++)
{
System.out.print(str[i]);
}
System.out.println();
System.out.println("The pattern is");
for(i=0;i<str.length;i++)
{
for(j=0;j<str.length;j++)
{
if(i==j || i+j==str.length-1)
{
System.out.print(" "+str[j]);
}
else
{
System.out.print(" ");
}
}
System.out.println();
}
}
}