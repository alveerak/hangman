public class SpellWord extends HangmanWord {

	private String description; 
	
	public SpellWord(String a, String d) {
		super(a);
		description = d;
	}
	
	public String getHint(){
		return description;
	}
	
	public String getWonMessage() {
		return "YOU WON!!!! The spell "+this.getWord(this.getLength()-1)+" was cast.";
	
	}
	
	public String getLostMessage() {
		return "YOU LOST!!!! The spell was "+ this.getWord(this.getLength()-1)+".";
	}

}