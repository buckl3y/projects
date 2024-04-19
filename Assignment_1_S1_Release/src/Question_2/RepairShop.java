/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Question_2;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;

/**
 *
 * @author Caleb
 */
public class RepairShop {

    File file = new File("591529__rudmer_rotteveel__ratchet-wrench-fast-multiple.wav");
    Lock lock = new ReentrantLock();
    private static boolean repairShopLocked = false;

    public synchronized boolean isRepairShopLocked() {
        return repairShopLocked;
    }

    public synchronized void lockRepairShop() {
        repairShopLocked = true;
    }

    public synchronized void unlockRepairShop() {
        repairShopLocked = false;
    }

    private void waitRepairCompletion(Phone phone) throws InterruptedException {
        //synchronized to avoid race conditions
        synchronized (phone) {
            phone.wait(2500); // Wait for 2.5 seconds
        }
    }

    public boolean repairPhone(Phone phone) {
        //Reentrant lock to only allow one phone to be in repair shop
        lock.lock();
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            try {
                if (!phone.isPhoneRepairing() && phone.isPhoneInfected() && !isRepairShopLocked()) {
                    lockRepairShop();
                    if (phone.isPhoneInRepairShop()) {
                        System.out.println("Phone Repairing");
                        phone.setPhoneRepairing(true);
                        phone.setPhoneInfected(false);
                        try {
                            waitRepairCompletion(phone);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(RepairShop.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        phone.setPhoneRepairing(false);
                        phone.y = 150;
                        phone.phoneLife = -1;
                        System.out.println("Repair Complete");
                        return true;
                    }
                }
            } finally {
                lock.unlock();
                unlockRepairShop();
                clip.start();
                //unlock reentrant lock
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(RepairShop.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
