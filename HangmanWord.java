public class HangmanWord extends Word {
	public HangmanWord(String a){
		letters = new char [a.length()];
		presentLetters = new boolean [a.length()];
		for (int i = 0; i<a.length(); i++){
			letters[i]=a.charAt(i);
		}
	}
	public boolean isComplete(){
		for (int i = 0; i<letters.length; i++){
			if (presentLetters[i]==false)
				return false;
		}
		return true;
	}
	public void addLetter (char a){
		for (int i = 0; i<letters.length; i++){
			if (letters[i]==a){
				presentLetters[i]=true;
			}
		}
	}
	public boolean isLetter(int index){
		return presentLetters[index];
	}
	public String makeLabel(){
		String filling = "";
		for (int i = 0; i<getWord().length(); i++){
			if(presentLetters[i]==true)
				filling+=letters[i]+" ";
			else
				filling+="_ ";
		}
		return filling;
	}

}