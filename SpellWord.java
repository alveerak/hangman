/**
 * class represents a HangmanWord that is a spell
 *
 */
public class SpellWord extends HangmanWord {

	private String description; 
	
	/**
	 * Parameterized constructor that initializes the word (spell) and its description
	 * @param a word
	 * @param d description
	 */
	public SpellWord(String a, String d) {
		super(a);
		description = d;
	}
	
	/**
	 * returns hint (description of spell)
	 * @return hint
	 */
	public String getHint(){
		return description;
	}
	
	/**
	 * returns won message with the name of spell
	 * @return won message
	 */
	public String getWonMessage() {
		return "YOU WON!!!! The spell "+this.getWord(this.getLength()-1)+" was cast.";
	
	}
	
	/**
	 * returns lost message with name of spell
	 * @return lost message
	 */
	public String getLostMessage() {
		return "YOU LOST!!!! The spell was "+ this.getWord(this.getLength()-1)+".";
	}

}
