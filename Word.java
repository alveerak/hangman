/**
* Word is an abstract class that represents a word made up of letters and possibly spaces
* 
*/
public abstract class Word {
	protected boolean [] presentLetters;
	protected char [] letters;
	/**
	* abstract method checks if .. is complete
	*/
	public abstract boolean isComplete();
	/**
	* abstract method adds a certain letter
	* @param a - letter to be added
	*/
	public abstract void addLetter(char a);
	/**
	* method returns the word from the letters
	* @param n - index of last letter desired
	*/
	public String getWord(int n){
		if(n == 0) {
			return ""+letters[0];
		} else {
			return getWord(n-1) + letters[n];
		}
	}
	/** 
	* method returns a letter at a certain index
	* @param index - index of wanted letter
	*/
	public char getLetter(int index){
		return letters[index];
	}
	
	/**
	 * method returns length of word
	 * @return length of word
	 */
	public int getLength() {
		return letters.length;
	}
}
