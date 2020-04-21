package HomeworkDay20;

import java.util.Scanner;

import static java.lang.Character.toLowerCase;
import static java.lang.Character.toUpperCase;

public class inputFiled {

    /*
    选做)设计一个方法method(String name) ；当你输入英文字母之外的内容时提示输入错误，当输入英文字母时返回首字母大写，其他字母小写内容。

例如：method(123)，输出：错误

method(ABC)，输出:Abc
     */

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        char a[]=input.toCharArray();
        for (int i=0;i<input.length();i++) {

            if (Character.getType(a[i]) == Character.OTHER_LETTER) {
                System.out.println("输入错误");
                break;
            } else if (Character.isDigit(a[i])) {
                System.out.println("输入错误");
                break;
            } else if (i==1){

                if(Character.isLowerCase(a[i]))
                {
                    a[i]-=32;//出问题的地方
                    toUpperCase(a[i]);
                }
                if(Character.isUpperCase(a[i]))
                {
                    a[i]+=32;
                }
            }
            else if (i>1) {
                if (Character.isLowerCase(a[i]))
                {
                    a[i] -= 32;//出问题的地方

                }
                if (Character.isUpperCase(a[i])) {
                    a[i] += 32;
                    toLowerCase(a[i]);
                }
            }
            String str=new String(a);
            System.out.println(str);
        }

    }




        }



