package Model;


import java.util.Scanner;

public class test {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String test=scanner.nextLine();

            int sum1=1;

            for (int i=0;i<test.length()-3;i++){
                if (test.charAt(i)==test.charAt(i+1) && test.charAt(i+2)==test.charAt(i+3)){
                    sum1++;
                }else
                {
                    sum1=1;
                }
                if (sum1==2){
                    String s1=test.substring(0,i+2);
                    String s2=test.substring(i+3);
                    test=s1+s2;
                }
            }
            int total=1;
            for (int i=0;i<test.length()-1;i++){
                if(test.charAt(i)==test.charAt(i+1)){
                    total++;
                }else{
                    total=1;
                }
                if (total>=3){
                    //delete a char
                    String s1=test.substring(0,i);
                    String s2=test.substring(i+1);
                    test=s1+s2;
                    i=i-1;
                }
            }
            System.out.println(test);


    }
}
