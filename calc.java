import java.util.Scanner;
import java.util.Arrays;

public class calc {

    public static boolean checkRoman (String input){
        char romanSet[] = {'X','I','V'};
        char currentSymbol;
        boolean flag;

        for (int i=0; i < input.length(); i++){
            currentSymbol = input.charAt(i);
            flag = false;
            for (char ch:romanSet){
                if (ch == currentSymbol) flag = true;
            }
            if (!(flag)) return false;

            /*System.out.print(currentSymbol + " - ");
            System.out.println(Arrays.binarySearch(romanSet, currentSymbol));
            if (Arrays.binarySearch(romanSet, currentSymbol)<0) return false;*/
        }
        return true;
    }

    public static boolean checkArabic (String input){
        char arabicSet[] = {'0','1','2','3','4','5','6','7','8','9'};
        char currentSymbol;

        for (int i=0; i < input.length(); i++){
            currentSymbol = input.charAt(i);
            if (Arrays.binarySearch(arabicSet,currentSymbol)<0) return false;
        }
        return true;
    }

    public static boolean validateString (String input){
        char allowedSymbols[] = {'0','1','2','3','4','5','6','7','8','9','X','I','V','+','-','*','/',' '};
        char currentSymbol;
        boolean symbolExist;

        for (int i=0; i < input.length(); i++){
            currentSymbol = input.charAt(i);
            symbolExist = false;
            for (char ch:allowedSymbols){
                if (ch == currentSymbol) symbolExist = true;
            }
            if (!(symbolExist)) return false;
        }
        return true;
    }

    public static String getLine() throws Exception{

        Scanner input = new Scanner(System.in);
        String receivedLine = "";
        boolean validation = false;

        while (receivedLine == "" || validation == false) {
            System.out.print("Введите выражение для вычисления или exit, чтобы выйти: ");
            receivedLine = input.nextLine();
            if (receivedLine == "exit") break;
            validation = validateString(receivedLine);
            if (receivedLine == "") System.out.println("Вы ничего не ввели. Попробуйте еще раз.");
            if (!(validation)) throw new Exception ("Ошибка корректности ввода выражения.");
        }
        return receivedLine;

    }

    public static int romanToArabic(String input) {
        int result;
        switch (input) {
            case "I" -> result = 1;
            case "II" -> result = 2;
            case "III" -> result = 3;
            case "IV" -> result = 4;
            case "V" -> result = 5;
            case "VI" -> result = 6;
            case "VII" -> result = 7;
            case "VIII" -> result = 8;
            case "IX" -> result = 9;
            case "X" -> result = 10;
            default -> result = 0;
        }
        return result;
    }

    public static String arabicToRoman (int input){
        String result = "";
        switch (input/10*10){
            case 100 -> result = "C";
            case 90 -> result = "XC";
            case 80 -> result = "LXXX";
            case 70 -> result = "LXX";
            case 60 -> result = "LX";
            case 50 -> result = "L";
            case 40 -> result = "XL";
            case 30 -> result = "XXX";
            case 20 -> result = "XX";
            case 10 -> result = "X";
        }

        switch (input%10){
            case 1 -> result = result + "I";
            case 2 -> result = result + "II";
            case 3 -> result = result + "III";
            case 4 -> result = result + "IV";
            case 5 -> result = result + "V";
            case 6 -> result = result + "VI";
            case 7 -> result = result + "VII";
            case 8 -> result = result + "VIII";
            case 9 -> result = result + "IX";
        }
        return result;

    }

    public static String calc (String input) throws Exception {
        int result = 0;
        boolean roman = false;
        boolean arabic = false;
        String operand1 = "";
        String operand2 = "";
        char operationSet[] = {'+','-','*','/'};
        char operation = '\u0000';
        int index = 0;
        int mathCount = 0;

        for (char ch:operationSet){

            if (input.indexOf(ch) != -1) {
                 index = input.indexOf(ch);
                 operation = ch;
                 mathCount++;
             }
        }

        //if (operation=='\u0000') return ("Ошибка! Не указана операция");
        if (mathCount != 1) throw new Exception("Ошибка! Формат математической операции не удовлятворяет заданию.");

        operand1 = input.substring(0,index).trim();
        operand2 = input.substring(index+1, input.length()).trim();

        int operand1int = 0;
        int operand2int = 0;

        if (checkRoman(operand1) && checkRoman(operand2)){
            roman = true;
            operand1int = romanToArabic(operand1);
            operand2int = romanToArabic(operand2);
        }

        else if (checkArabic(operand1) && checkArabic(operand2)) {
            arabic = true;
            operand1int = Integer.parseInt(operand1);
            operand2int = Integer.parseInt(operand2);
        }

        else throw new Exception ("Ошибка! Оба числа должны принадлежать одной системе счисления.");

        if (operand1int > 10 || operand2int >10
            || operand1int == 0 || operand2int == 0) throw new Exception ("Ошибка! Введено непоходящее число.");

        switch (operation) {
            case '+' -> result = operand1int + operand2int;
            case '-' -> result = operand1int - operand2int;
            case '*' -> result = operand1int * operand2int;
            case '/' -> result = operand1int / operand2int;
        }

        if (roman && result <= 0) throw new Exception ("Ошибка! В римской системе счисления не может существовать нулевых или отрицательных чисел.");
        if (arabic) return (operand1 + Character.toString(operation) + operand2 + "=" + Integer.toString(result));
        if (roman) return (operand1 + Character.toString(operation) + operand2 + "=" + arabicToRoman(result));
        return ("Error!");
    }

    public static void main(String[] args) throws Exception{

        String expression = "";
        while (expression != "exit") {
            expression = getLine();
            System.out.println(calc(expression));
        }

    }
}
