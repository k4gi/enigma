/*
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.JComboBox;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import java.util.Hashtable;



public class Cipher extends JFrame {
	boolean debugging = true;
	
	public Cipher() {
		initUI();
	}
	
	private String dummyOutput(String in) {
		return in;
	}
	
	private void debug(String in) {
		if(debugging) System.out.println(in);
	}
	
	private String enigmaCipher(String in, String in_wheel1, int in_start1, String in_wheel2, int in_start2, String in_wheel3, int in_start3, String in_reflect, String plugboard) {
		debug("==========STARTING enigmaCipher()==========");
		in = in.toUpperCase();
		String out = "";
		String keyboard = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String[] wheel_i = { // Q-R
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ",
			"EKMFLGDQVZNTOWYHXUSPAIBRCJ",
			"R "};
		String[] wheel_ii = { // E-F
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ",
			"AJDKSIRUXBLHWTMCQGZNPYFVOE",
			"F "};
		String[] wheel_iii = { // V-W
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ",
			"BDFHJLCPRTXVZNYEIWGAKMUSQO",
			"W "};
		String[] wheel_iv = { // J-K
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ",
			"ESOVPZJAYQUIRHXLNFTGKDCMWB",
			"K ",};
		String[] wheel_v = { // Z-A
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ",
			"VZBRGITYUPSDNHLXAWMJQOFECK",
			"A "};
		String[] wheel_vi = { // Z-A, M-N
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ",
			"JPGVOUMFYQBENHZRDKASXLICTW",
			"AN"};
		String[] wheel_vii = { // Z-A, M-N
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ",
			"NZJHGRCXMYSWBOUFAIVLPEKQDT",
			"AN"};
		String[] wheel_viii = { // Z-A, M-N
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ",
			"FKQHTLXOCBJSPDZRAMEWNIUYGV",
			"AN"};
		String reflector_b = "YRUHQSLDPXNGOKMIEBFZCWVJAT";
		String reflector_c = "FVPJIAOYEDRZXWGCTKUQSBNMHL";
		
		debug("Input: " + in);
		debug("Slot 1: " + in_wheel1);
		debug("Setting 1: " + in_start1);
		debug("Slot 2: " + in_wheel2);
		debug("Setting 2: " + in_start2);
		debug("Slot 3: " + in_wheel3);
		debug("Setting 3: " + in_start3);
		
		String[] working_wheel_i;
		String[] working_wheel_ii;
		String[] working_wheel_iii;
		String working_reflector;
		
		switch(in_wheel1) {
			case "Wheel I" :
				working_wheel_i = wheel_i.clone();
				break;
			case "Wheel II" :
				working_wheel_i = wheel_ii.clone();
				break;
			case "Wheel III" :
				working_wheel_i = wheel_iii.clone();
				break;
			case "Wheel IV" :
				working_wheel_i = wheel_iv.clone();
				break;
			case "Wheel V" :
				working_wheel_i = wheel_v.clone();
				break;
			case "Wheel VI" :
				working_wheel_i = wheel_vi.clone();
				break;
			case "Wheel VII" :
				working_wheel_i = wheel_vii.clone();
				break;
			case "Wheel VIII" :
				working_wheel_i = wheel_viii.clone();
				break;
			default:
				working_wheel_i = wheel_i.clone();
		}
		switch(in_wheel2) {
			case "Wheel I" :
				working_wheel_ii = wheel_i.clone();
				break;
			case "Wheel II" :
				working_wheel_ii = wheel_ii.clone();
				break;
			case "Wheel III" :
				working_wheel_ii = wheel_iii.clone();
				break;
			case "Wheel IV" :
				working_wheel_ii = wheel_iv.clone();
				break;
			case "Wheel V" :
				working_wheel_ii = wheel_v.clone();
				break;
			case "Wheel VI" :
				working_wheel_ii = wheel_vi.clone();
				break;
			case "Wheel VII" :
				working_wheel_ii = wheel_vii.clone();
				break;
			case "Wheel VIII" :
				working_wheel_ii = wheel_viii.clone();
				break;
			default:
				working_wheel_ii = wheel_ii.clone();
		}
		switch(in_wheel3) {
			case "Wheel I" :
				working_wheel_iii = wheel_i.clone();
				break;
			case "Wheel II" :
				working_wheel_iii = wheel_ii.clone();
				break;
			case "Wheel III" :
				working_wheel_iii = wheel_iii.clone();
				break;
			case "Wheel IV" :
				working_wheel_iii = wheel_iv.clone();
				break;
			case "Wheel V" :
				working_wheel_iii = wheel_v.clone();
				break;
			case "Wheel VI" :
				working_wheel_iii = wheel_vi.clone();
				break;
			case "Wheel VII" :
				working_wheel_iii = wheel_vii.clone();
				break;
			case "Wheel VIII" :
				working_wheel_iii = wheel_viii.clone();
				break;
			default:
				working_wheel_iii = wheel_iii.clone();
		}
		switch(in_reflect) {
			case "Reflector B" :
				working_reflector = reflector_b;
				break;
			case "Reflector C" :
				working_reflector = reflector_c;
				break;
			default:
				working_reflector = reflector_b;
		}

		// now the initial rotor/wheel setting
		for(int i = 1; i < in_start1; i ++) { //wheel i
			working_wheel_i[0] = working_wheel_i[0].substring(1, working_wheel_i[0].length()) + working_wheel_i[0].charAt(0);
			working_wheel_i[1] = working_wheel_i[1].substring(1, working_wheel_i[1].length()) + working_wheel_i[1].charAt(0);
		}
		for(int i = 1; i < in_start2; i ++) { //wheel ii
			working_wheel_ii[0] = working_wheel_ii[0].substring(1, working_wheel_ii[0].length()) + working_wheel_ii[0].charAt(0);
			working_wheel_ii[1] = working_wheel_ii[1].substring(1, working_wheel_ii[1].length()) + working_wheel_ii[1].charAt(0);
		}
		for(int i = 1; i < in_start3; i ++) { //wheel iii
			working_wheel_iii[0] = working_wheel_iii[0].substring(1, working_wheel_iii[0].length()) + working_wheel_iii[0].charAt(0);
			working_wheel_iii[1] = working_wheel_iii[1].substring(1, working_wheel_iii[1].length()) + working_wheel_iii[1].charAt(0);
		}
		debug( "Initial W-I:   " + working_wheel_i[0] + " / " + working_wheel_i[1] + " / " + working_wheel_i[2] );
		debug( "Initial W-II:  " + working_wheel_ii[0] + " / " + working_wheel_ii[1] + " / " + working_wheel_ii[2] );
		debug( "Initial W-III: " + working_wheel_iii[0] + " / " + working_wheel_iii[1] + " / " + working_wheel_iii[2] );
		debug( "Reflector: " + working_reflector );
		debug( "Plugboard: " + plugboard );

		//start loop here
		//for each character in the input string
		for(int i = 0; i < in.length(); i++) {
			debug("==========START OF LOOP==========");

			//Step 1: get input
			char code_letter = in.charAt(i);
			debug("code_letter: " + code_letter);
			if(keyboard.indexOf(code_letter) == -1) { //skip character if it's not a letter, don't move wheels
				continue;
			}

			//Step 2: shift wheels
			debug("Shifting rotors...");
		
			working_wheel_iii[0] = working_wheel_iii[0].substring(1, working_wheel_iii[0].length()) + working_wheel_iii[0].charAt(0);
			working_wheel_iii[1] = working_wheel_iii[1].substring(1, working_wheel_iii[1].length()) + working_wheel_iii[1].charAt(0);
			if( working_wheel_iii[0].charAt(0) == working_wheel_iii[2].charAt(0) || working_wheel_iii[0].charAt(0) == working_wheel_iii[2].charAt(1) ) {
				
				working_wheel_ii[0] = working_wheel_ii[0].substring(1, working_wheel_ii[0].length()) + working_wheel_ii[0].charAt(0);
				working_wheel_ii[1] = working_wheel_ii[1].substring(1, working_wheel_ii[1].length()) + working_wheel_ii[1].charAt(0);
				
				if( working_wheel_ii[0].charAt(0) == working_wheel_ii[2].charAt(0) || working_wheel_ii[0].charAt(0) == working_wheel_ii[2].charAt(1) ) {
					
					working_wheel_i[0] = working_wheel_i[0].substring(1, working_wheel_i[0].length()) + working_wheel_i[0].charAt(0);
					working_wheel_i[1] = working_wheel_i[1].substring(1, working_wheel_i[1].length()) + working_wheel_i[1].charAt(0);
					//since we're only doing three wheels (for now) we can stop here. The reflector doesn't move.
				}
			}
			
			debug( "W-I:   " + working_wheel_i[0] + " / " + working_wheel_i[1] + " / " + working_wheel_i[2] );
			debug( "W-II:  " + working_wheel_ii[0] + " / " + working_wheel_ii[1] + " / " + working_wheel_ii[2] );
			debug( "W-III: " + working_wheel_iii[0] + " / " + working_wheel_iii[1] + " / " + working_wheel_iii[2] );
			
			//Step 3: plugboard
			
			code_letter = plugboard.charAt( keyboard.indexOf(code_letter) );
			debug("Plugboard... " + code_letter);
			
			
			
			//Step 4: Wheel III
			
			//entry
			code_letter = working_wheel_iii[0].charAt( keyboard.indexOf(code_letter) );
			//encode
			code_letter = working_wheel_iii[1].charAt( working_wheel_iii[0].indexOf(code_letter) );
			//exit
			code_letter = keyboard.charAt( working_wheel_iii[0].indexOf(code_letter) );
			
			debug("Wheel III... " + code_letter);
			
			//Step 5: Wheel II
			
			//entry
			code_letter = working_wheel_ii[0].charAt( keyboard.indexOf(code_letter) );
			//encode
			code_letter = working_wheel_ii[1].charAt( working_wheel_ii[0].indexOf(code_letter) );
			//exit
			code_letter = keyboard.charAt( working_wheel_ii[0].indexOf(code_letter) );
			
			debug("Wheel II...  " + code_letter);
			
			//Step 6: Wheel I
			
			//entry
			code_letter = working_wheel_i[0].charAt( keyboard.indexOf(code_letter) );
			//encode
			code_letter = working_wheel_i[1].charAt( working_wheel_i[0].indexOf(code_letter) );
			//exit
			code_letter = keyboard.charAt( working_wheel_i[0].indexOf(code_letter) );
			
			debug("Wheel I...   " + code_letter);
			
			//Step 7: Reflector
			
			code_letter = working_reflector.charAt( keyboard.indexOf(code_letter) );
			
			debug("Reflector... " + code_letter);
			
			//Step 8: Wheel I
			
			//entry
			code_letter = working_wheel_i[0].charAt( keyboard.indexOf(code_letter) ); //ready to go through wheel
			//encode (but backwards!}
			code_letter = working_wheel_i[0].charAt( working_wheel_i[1].indexOf(code_letter) ); //passing backwards through wheel
			//exit
			code_letter = keyboard.charAt( working_wheel_i[0].indexOf(code_letter) );
			
			debug("Wheel I...   " + code_letter);
			
			//Step 9: Wheel II
			
			//entry
			code_letter = working_wheel_ii[0].charAt( keyboard.indexOf(code_letter) );
			//encode
			code_letter = working_wheel_ii[0].charAt( working_wheel_ii[1].indexOf(code_letter) );
			//exit
			code_letter = keyboard.charAt( working_wheel_ii[0].indexOf(code_letter) );
			
			debug("Wheel II...  " + code_letter);
			
			//Step 10: Wheel III
			
			//entry
			code_letter = working_wheel_iii[0].charAt( keyboard.indexOf(code_letter) );
			//encode
			code_letter = working_wheel_iii[0].charAt( working_wheel_iii[1].indexOf(code_letter) );
			//exit
			code_letter = keyboard.charAt( working_wheel_iii[0].indexOf(code_letter) );
			
			debug("Wheel III... " + code_letter);
			
			//Step 11: Plugboard
			
			code_letter = plugboard.charAt( keyboard.indexOf(code_letter) );
			debug("Plugboard... " + code_letter);
			
			//Step 12: Output
			
			out += code_letter;
			
			
		}
		
		return out;
	}
	
	private void initUI() {
		var in_field = new JTextArea("Type here",5,50);
		in_field.setLineWrap(true);
		var out_field = new JTextArea("Read here",5,50);
		out_field.setLineWrap(true);
		
		
		
		
		var label0 = new JLabel("Change wheel settings here...");
		var label1 = new JLabel("Slot I..");
		var label2 = new JLabel("Slot II.");
		var label3 = new JLabel("Slot III");
		var labelp = new JLabel("Change plugboard settings here...");
		
		var slide1 = new JSlider(1,26,1);
		var slide2 = new JSlider(1,26,1);
		var slide3 = new JSlider(1,26,1);

		slide1.setMinorTickSpacing(1);
		slide2.setMinorTickSpacing(1);
		slide3.setMinorTickSpacing(1);
		slide1.setPaintTicks(true);
		slide2.setPaintTicks(true);
		slide3.setPaintTicks(true);
		slide1.setPaintLabels(true);
		slide2.setPaintLabels(true);
		slide3.setPaintLabels(true);
		
		JLabel[] plugboard_labels = new JLabel[26];
		JComboBox[] plugboard_menu = new JComboBox[26];
		for(int i = 0; i < 26; i ++) {
			plugboard_menu[i] = new JComboBox();
			plugboard_menu[i].addItem("---");
		}
		String keyboard_copy = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //duplicate
		Hashtable markers = new Hashtable();
		for(int i = 0; i < 26; i ++) {
			markers.put(i+1, new JLabel( Character.toString(keyboard_copy.charAt(i)) ));
			
			plugboard_labels[i] = new JLabel( keyboard_copy.charAt(i) + " = ");
			
			for(int j = 0; j < 26; j ++) {
				if(i != j) plugboard_menu[j].addItem( Character.toString(keyboard_copy.charAt(i)) );
			} 
		}
		
		slide1.setLabelTable(markers);
		slide2.setLabelTable(markers);
		slide3.setLabelTable(markers);

		String[] wheel_list = {"Wheel I", "Wheel II", "Wheel III", "Wheel IV", "Wheel V", "Wheel VI", "Wheel VII", "Wheel VIII"};
		String[] reflector_list = {"Reflector B", "Reflector C"};

		var select1 = new JComboBox(wheel_list);
		var select2 = new JComboBox(wheel_list);
		var select3 = new JComboBox(wheel_list);
		
		var selectr = new JComboBox(reflector_list);

		var press_this = new JButton("Press to encode text!");
		press_this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String plugboard_made = "";
				for(int i = 0; i < 26; i ++) { //grabbing plugboard
					if( String.valueOf( plugboard_menu[i].getSelectedItem() ) != "---" ) {
						plugboard_made += String.valueOf( plugboard_menu[i].getSelectedItem() );
					} else {
						plugboard_made += Character.toString( keyboard_copy.charAt(i) );
					}
				}
				boolean verified = true;
				for(int i = 0; i < 26; i ++) { //checking plugboard
					if( !plugboard_made.contains( Character.toString(keyboard_copy.charAt(i)) ) ) {
						verified = false;
						break;
					}
					if( plugboard_made.charAt(i) != keyboard_copy.charAt(i) ) {
						if( plugboard_made.charAt( keyboard_copy.indexOf( plugboard_made.charAt(i) ) ) != keyboard_copy.charAt(i) ) {
							verified = false;
							break;
						}
					}
				}
				if(verified) {
					out_field.setText( enigmaCipher( in_field.getText(), String.valueOf(select1.getSelectedItem()), slide1.getValue(), String.valueOf(select2.getSelectedItem()), slide2.getValue(), String.valueOf(select3.getSelectedItem()), slide3.getValue(), String.valueOf(selectr.getSelectedItem()), plugboard_made ) );
				} else out_field.setText( "ERROR: Plugboard Load Letter" );
			}
		});

		//the GUI hellbeast needs taming
		createLayout(
			plugboard_labels[0], plugboard_labels[1], plugboard_labels[2], plugboard_labels[3], plugboard_labels[4], plugboard_labels[5], plugboard_labels[6], plugboard_labels[7], plugboard_labels[8], plugboard_labels[9], plugboard_labels[10], plugboard_labels[11], plugboard_labels[12], plugboard_labels[13], plugboard_labels[14], plugboard_labels[15], plugboard_labels[16], plugboard_labels[17], plugboard_labels[18], plugboard_labels[19], plugboard_labels[20], plugboard_labels[21], plugboard_labels[22], plugboard_labels[23], plugboard_labels[24], plugboard_labels[25], 
			plugboard_menu[0], plugboard_menu[1], plugboard_menu[2], plugboard_menu[3], plugboard_menu[4], plugboard_menu[5], plugboard_menu[6], plugboard_menu[7], plugboard_menu[8], plugboard_menu[9], plugboard_menu[10], plugboard_menu[11], plugboard_menu[12], plugboard_menu[13], plugboard_menu[14], plugboard_menu[15], plugboard_menu[16], plugboard_menu[17], plugboard_menu[18], plugboard_menu[19], plugboard_menu[20], plugboard_menu[21], plugboard_menu[22], plugboard_menu[23], plugboard_menu[24], plugboard_menu[25], 
			in_field, press_this, out_field, label0, label1, select1, slide1, label2, select2, slide2, label3, select3, slide3, selectr, labelp);
		setTitle("It's cipher tiem");
		setLocationRelativeTo(null); //centre window
		setDefaultCloseOperation(EXIT_ON_CLOSE); //make X button work
	}
	
	private void createLayout(JComponent... arg) {
		var pane = getContentPane();
        var gl = new GroupLayout(pane);
        pane.setLayout(gl);
		
		gl.setHorizontalGroup(gl.createParallelGroup()
			.addComponent(arg[52])
			.addComponent(arg[53])
			.addComponent(arg[54])
			.addComponent(arg[55])
			.addGroup(gl.createSequentialGroup()
				.addComponent(arg[56])
				.addComponent(arg[57])
				.addComponent(arg[58])
			)
			.addGroup(gl.createSequentialGroup()
				.addComponent(arg[59])
				.addComponent(arg[60])
				.addComponent(arg[61])
			)
			.addGroup(gl.createSequentialGroup()
				.addComponent(arg[62])
				.addComponent(arg[63])
				.addComponent(arg[64])
			)
			.addComponent(arg[65])
			.addComponent(arg[66])
			.addGroup(gl.createSequentialGroup()
					.addComponent(arg[0])
					.addComponent(arg[26])
				)
			.addGroup(gl.createSequentialGroup()
					.addComponent(arg[1])
					.addComponent(arg[27])
				)
			.addGroup(gl.createSequentialGroup()
					.addComponent(arg[2])
					.addComponent(arg[28])
				)
			.addGroup(gl.createSequentialGroup()
					.addComponent(arg[3])
					.addComponent(arg[29])
				)
			.addGroup(gl.createSequentialGroup()
					.addComponent(arg[4])
					.addComponent(arg[30])
				)
			.addGroup(gl.createSequentialGroup()
					.addComponent(arg[5])
					.addComponent(arg[31])
				)
			.addGroup(gl.createSequentialGroup()
					.addComponent(arg[6])
					.addComponent(arg[32])
				)
			.addGroup(gl.createSequentialGroup()
					.addComponent(arg[7])
					.addComponent(arg[33])
				)
			.addGroup(gl.createSequentialGroup()
					.addComponent(arg[8])
					.addComponent(arg[34])
				)
			.addGroup(gl.createSequentialGroup()
					.addComponent(arg[9])
					.addComponent(arg[35])
				)
			.addGroup(gl.createSequentialGroup()
					.addComponent(arg[10])
					.addComponent(arg[36])
				)
			.addGroup(gl.createSequentialGroup()
					.addComponent(arg[11])
					.addComponent(arg[37])
				)
			.addGroup(gl.createSequentialGroup()
					.addComponent(arg[12])
					.addComponent(arg[38])
				)
			.addGroup(gl.createSequentialGroup()
					.addComponent(arg[13])
					.addComponent(arg[39])
				)
			.addGroup(gl.createSequentialGroup()
					.addComponent(arg[14])
					.addComponent(arg[40])
				)
			.addGroup(gl.createSequentialGroup()
					.addComponent(arg[15])
					.addComponent(arg[41])
				)
			.addGroup(gl.createSequentialGroup()
					.addComponent(arg[16])
					.addComponent(arg[42])
				)
			.addGroup(gl.createSequentialGroup()
					.addComponent(arg[17])
					.addComponent(arg[43])
				)
			.addGroup(gl.createSequentialGroup()
					.addComponent(arg[18])
					.addComponent(arg[44])
				)
			.addGroup(gl.createSequentialGroup()
					.addComponent(arg[19])
					.addComponent(arg[45])
				)
			.addGroup(gl.createSequentialGroup()
					.addComponent(arg[20])
					.addComponent(arg[46])
				)
			.addGroup(gl.createSequentialGroup()
					.addComponent(arg[21])
					.addComponent(arg[47])
				)
			.addGroup(gl.createSequentialGroup()
					.addComponent(arg[22])
					.addComponent(arg[48])
				)
			.addGroup(gl.createSequentialGroup()
					.addComponent(arg[23])
					.addComponent(arg[49])
				)
			.addGroup(gl.createSequentialGroup()
					.addComponent(arg[24])
					.addComponent(arg[50])
				)
			.addGroup(gl.createSequentialGroup()
					.addComponent(arg[25])
					.addComponent(arg[51])
				)
			
		);
		gl.setVerticalGroup(gl.createSequentialGroup()
			.addComponent(arg[52])
			.addComponent(arg[53])
			.addComponent(arg[54])
			.addComponent(arg[55])
			.addGroup(gl.createParallelGroup()
				.addComponent(arg[56])
				.addComponent(arg[57])
				.addComponent(arg[58])
			)
			.addGroup(gl.createParallelGroup()
				.addComponent(arg[59])
				.addComponent(arg[60])
				.addComponent(arg[61])
			)
			.addGroup(gl.createParallelGroup()
				.addComponent(arg[62])
				.addComponent(arg[63])
				.addComponent(arg[64])
			)
			.addComponent(arg[65])
			.addComponent(arg[66])
			.addGroup(gl.createParallelGroup()
					.addComponent(arg[0])
					.addComponent(arg[26])
				)
			.addGroup(gl.createParallelGroup()
					.addComponent(arg[1])
					.addComponent(arg[27])
				)
			.addGroup(gl.createParallelGroup()
					.addComponent(arg[2])
					.addComponent(arg[28])
				)
			.addGroup(gl.createParallelGroup()
					.addComponent(arg[3])
					.addComponent(arg[29])
				)
			.addGroup(gl.createParallelGroup()
					.addComponent(arg[4])
					.addComponent(arg[30])
				)
			.addGroup(gl.createParallelGroup()
					.addComponent(arg[5])
					.addComponent(arg[31])
				)
			.addGroup(gl.createParallelGroup()
					.addComponent(arg[6])
					.addComponent(arg[32])
				)
			.addGroup(gl.createParallelGroup()
					.addComponent(arg[7])
					.addComponent(arg[33])
				)
			.addGroup(gl.createParallelGroup()
					.addComponent(arg[8])
					.addComponent(arg[34])
				)
			.addGroup(gl.createParallelGroup()
					.addComponent(arg[9])
					.addComponent(arg[35])
				)
			.addGroup(gl.createParallelGroup()
					.addComponent(arg[10])
					.addComponent(arg[36])
				)
			.addGroup(gl.createParallelGroup()
					.addComponent(arg[11])
					.addComponent(arg[37])
				)
			.addGroup(gl.createParallelGroup()
					.addComponent(arg[12])
					.addComponent(arg[38])
				)
			.addGroup(gl.createParallelGroup()
					.addComponent(arg[13])
					.addComponent(arg[39])
				)
			.addGroup(gl.createParallelGroup()
					.addComponent(arg[14])
					.addComponent(arg[40])
				)
			.addGroup(gl.createParallelGroup()
					.addComponent(arg[15])
					.addComponent(arg[41])
				)
			.addGroup(gl.createParallelGroup()
					.addComponent(arg[16])
					.addComponent(arg[42])
				)
			.addGroup(gl.createParallelGroup()
					.addComponent(arg[17])
					.addComponent(arg[43])
				)
			.addGroup(gl.createParallelGroup()
					.addComponent(arg[18])
					.addComponent(arg[44])
				)
			.addGroup(gl.createParallelGroup()
					.addComponent(arg[19])
					.addComponent(arg[45])
				)
			.addGroup(gl.createParallelGroup()
					.addComponent(arg[20])
					.addComponent(arg[46])
				)
			.addGroup(gl.createParallelGroup()
					.addComponent(arg[21])
					.addComponent(arg[47])
				)
			.addGroup(gl.createParallelGroup()
					.addComponent(arg[22])
					.addComponent(arg[48])
				)
			.addGroup(gl.createParallelGroup()
					.addComponent(arg[23])
					.addComponent(arg[49])
				)
			.addGroup(gl.createParallelGroup()
					.addComponent(arg[24])
					.addComponent(arg[50])
				)
			.addGroup(gl.createParallelGroup()
					.addComponent(arg[25])
					.addComponent(arg[51])
				)
		);
		
		pack(); //auto-size
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
            var ex = new Cipher();
            ex.setVisible(true);
        });
	}
}
