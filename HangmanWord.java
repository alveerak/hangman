
public class HangmanWord extends Word {
	public boolean isComplete(){
		for (int i = 0; i<letters.length; i++){
			if (presentLetters[i]==false)
				return false;
		}
		return true;
	}
	public boolean isLetter(char a){
		boolean present = false;
		for (int i = 0; i<letters.length; i++){
			if (letters[i]==a){
				present = true;
				presentLetters[i]=true;
			}
		}
	}

}
