package dk.jnie.dragunzip.view;

import java.util.Random;

import javax.swing.JOptionPane;

public class MessagePopup {

	private String[] messages = {	"What the hell kindof a filetype a you trying to throw at me :(",
									"Are you nuts, I don't know that filetype.",
									"Come on! did you expect me to know that filetype",
									"Jeez, have you ever seen any other files with that extension?",
									"Now I'm going to have the Police arrest you, bringin' on filetypes like that.",
									"If you keep up this workload, I'll quit!!",
									"I think I just poop'd.",
									"Somewhere there has got to be some kind of intelligence, it's not here.",
									"So now you think I've got all the answers?",
									"Ok, that's it I will stop immediately"};
	
	public String getMessage(int someNumber) {
		if (someNumber < messages.length) {
			return messages[someNumber];
		} else {
			return "I'm out of words.";
		}
	}
	
	public String getRandomMessage() {
		Random generator = new Random();
		int randomNumber = generator.nextInt(10);
		return getMessage(randomNumber);
	}

	public static void pop(String message) {
		JOptionPane.showMessageDialog(null,message,
			    "I just had this pop into my mind",JOptionPane.PLAIN_MESSAGE);
	}

	public static int popQuestion(String message) {
		return JOptionPane.showConfirmDialog(null,message,
			    "I just had this pop into my mind",JOptionPane.YES_NO_OPTION);
	}
	
	public static void main(String[] args) {

		MessagePopup mp = new MessagePopup();
		
		mp.pop(mp.getRandomMessage());
		
		mp.popQuestion(mp.getRandomMessage());
		
	}
}
