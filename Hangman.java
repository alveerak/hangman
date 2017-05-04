import.java.util.Arrays;
import.java.util.Random;
import.java.util.Scanner;

public class Hangman {
  public static void main (String[] args) {
    String list[] = {"math", "science", "english"};
    //Scanner s = new Scanner(System.in);
    Random rand = new Random();
    
    int wrong = 0;
    String word = (list[rand.nextInt(list.length)]);
    boolean a = false;
    String guess = "";
    
    while( != a) {
      System.out.print("Enter your guess: ");
      Scanner s = new Scanner(System.in);
      guess = s.next
}
