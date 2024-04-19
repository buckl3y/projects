/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Question_2;

import javax.swing.JFrame;

/**
 *
 * @author xhu
 */
//Icons from:
//UIcons by <a href="https://www.flaticon.com/uicons">Flaticon</a>
//<a href="https://www.flaticon.com/free-icons/wrench" title="wrench icons">Wrench icons created by Gregor Cresnar Premium - Flaticon</a>
//.wavs from:
//Ratchet Wrench Fast Multiple by Rudmer_Rotteveel -- https://freesound.org/s/591529/ -- License: Creative Commons 0
//Bubble Pop by YehawSnail -- https://freesound.org/s/683587/ -- License: Creative Commons 0
public class VirusSimulation {

    /**
     * @param args the command line arguments
     */
    //RepairShop synchronized using a Reentrant Lock, to ensure only ONE phone
    //can enter the repair shop at once.
    //
    public static void main(String[] args) {
        JFrame frame = new JFrame("Mobile Phone Virus Simulation");
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Panel panel = new Panel(frame);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

}
