/**
* The abstract Word class 
*
* @author 
*/
public abstract class Word {
	protected boolean [] presentLetters;
	protected char [] letters;
	public abstract boolean isComplete();
	public abstract void addLetter(char a);
	/**
	* This method returns the word from the letters 
	* array of characters
	*/
	public String getWord(){
		String a = "";
		for (int i = 0; i<letters.length; i++)
			a+=letters[i];
		return a;
	}
	/**
	* This method returns the letter at 
	* a certain index
	* @param index - the index of the desired letter
	*/
	public char getLetter(int index){
		return letters[index];
	}
}
