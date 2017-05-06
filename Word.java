/**
* Word is an abstract class which 
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
	* 
	*/
	public String getWord(){
		String a = "";
		for (int i = 0; i<letters.length; i++)
			a+=letters[i];
		return a;
	}
	/** 
	* method returns a letter at a certain index
	* @param index - index of wanted letter
	*/
	public char getLetter(int index){
		return letters[index];
	}
}
