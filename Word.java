
public abstract class Word {
	protected boolean [] presentLetters;
	protected char [] letters;
	public abstract boolean isComplete();
	public abstract boolean isLetter(char a);
	public String getWord(){
		String a = "";
		for (int i = 0; i<letters.length; i++)
			a+=letters[i];
		return a;
	}
	public char getLetter(int index){
		return letters[index];
	}
}
