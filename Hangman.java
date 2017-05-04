import.java.util.Arrays;
import.java.util.Random;
import.java.util.Scanner;

public class Hangman {
  public static void main (String[] args) {
    String list[] = {"math", "science", "english"};
    Scanner s = new Scanner(System.in);
    Random rand = new Random();
    
    int wrong = 0;
    String word = (list[rand.nextInt(list.length)]);
    boolean a = true;
    
    while(wrong != 10) {
      
                   
}
