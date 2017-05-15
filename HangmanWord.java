/**
* class extends the abstract class Word and provides Hangman game specifications
*/
public class HangmanWord extends Word {
	/**
	* HangmanWord constructor adds letters of a given string to the letters array
	* @param a - String of which's characters are added to letter array
	*/
	public HangmanWord(String a){
		letters = new char [a.length()];
		presentLetters = new boolean [a.length()];
		for (int i = 0; i<a.length(); i++){
			letters[i]=a.charAt(i);
		}
	}
	/**
	* method that checks if presentLetters array contains any false values
	*/
	public boolean isComplete(){
		for (int i = 0; i<letters.length; i++){
			if (presentLetters[i]==false)
				return false;
		}
		return true;
	}
	/**
	* method that sets value of presentLetters to true if letters contains param a
	* @param a - letter character to be checked
	*/
	public void addLetter (char a){
		for (int i = 0; i<letters.length; i++){
			if (letters[i]==a){
				presentLetters[i]=true;
			}
		}
	}
	/**
	* method checks if a character at a certain index has been guessed already
	* @param index - index of character to be checked
	*/
	public boolean isLetter(int index){
		return presentLetters[index];
	}
	/**
	* method adds the guessed letter to String filling if it is in the correct word
	* else it adds a dash
	*/
	public String makeLabel(){
		String filling = "";
		for (int i = 0; i<getWord(letters.length -1).length(); i++){
			if(letters[i] == ' ') 
				presentLetters[i] = true; 
			if(presentLetters[i]==true)
				filling+=letters[i]+" ";
			else
				filling+="_ ";
		}
		return filling; 
	}
	/**
	 * returns default hint (no hint)
	 * @return hint
	 */
	public String getHint() {
		return "No hint for this one! Keep trying.";
	}
	/**
	 * returns default win message
	 * @return win
	 */
	public String getWonMessage() {
		return "YOU WON!";
	}
	
	/**
	 * returns default lose message
	 * @return lose
	 */
	public String getLostMessage() {
		return "YOU LOST!";
	}

}
