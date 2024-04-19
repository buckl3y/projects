package Question_2;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

/**
 *
 * @author Caleb
 */
//Question: “Which object(s) have you chosen for the synchronize? Why?”
//RepairShop synchronized using a Reentrant Lock, to ensure only ONE phone
//can enter the repair shop at once.
//Also using "private static final Object repairLock = new Object();" as a monitor
//to further synchronize access to RepairShop. allowing only ONE phone
//to enter the repair shop at a time.
public class Phone implements Comparable<Phone>, Runnable {

    int x;
    int y;
    int destY;
    int destX;
    int shopX;
    int shopY;
    int width;
    int height;
    int phoneLife = -1;
    int delay = 10;
    boolean infected;
    boolean repairing;
    boolean inRepairShop;
    String threadName;
    Random rand = new Random();
    RepairShop repairShop = new RepairShop();
    Image image;
    File file = new File("683587__yehawsnail__bubble-pop.wav");
    private static final Object repairLock = new Object();
    private Thread phoneThread;

    public Phone(int shopX, int shopY) {
        x = rand.nextInt(1000);
        y = rand.nextInt(1000);
        this.shopX = shopX;
        this.shopY = shopY;
        infected = false;
        repairing = false;
        inRepairShop = false;
        image = new ImageIcon("smartphone50x50.png").getImage();
        destX = rand.nextInt(1000);
        destY = rand.nextInt(1000);

    }

    public boolean isPhoneRepairing() {
        return this.repairing;
    }

    public boolean isPhoneInfected() {
        return this.infected;
    }

    public void setPhoneRepairing(boolean repairing) {
        this.repairing = repairing;
    }

    public synchronized void setPhoneInfected(boolean infected) {
        try {
            if (infected && !this.infected) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
                this.phoneLife = 500;
            }
            this.infected = infected;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(RepairShop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized boolean phoneHealth() {
        if (isPhoneInfected()) {
            this.phoneLife -= 1;
            if (this.phoneLife == 0 || this.phoneLife < -1) {
                this.phoneThread.interrupt();
                return true;
            }
        }
        return false;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setRange(int width, int height) {
        this.width = width;
        this.height = height;
        x = rand.nextInt(width);
        y = rand.nextInt(height);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (this.isPhoneInfected()) {
                    if (!this.isPhoneInRepairShop()) {

                        move();
                    } else {
                        synchronized (repairLock) {
                            repairShop.repairPhone(this);
                        }
                    }
                } else {
                    move(); // Move randomly
                }
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                break;
            }
        }
    }

    public void move() {
        if (isPhoneInRepairShop() && isPhoneRepairing()) {
            // If the phone is in the repair shop and already repairing, stop moving
            x = shopX;
            y = shopY;
        } else {
            if (isPhoneInfected() && !repairShop.isRepairShopLocked()) {
                // If infected and repair shop is not occupied, move towards the repair shop
                if (x < shopX) {
                    x++;
                } else if (x > shopX) {
                    x--;
                }
                if (y < shopY) {
                    y++;
                } else if (y > shopY) {
                    y--;
                }
            } else {
                if (x == destX && y == destY) {
                    // If the phone reaches its destination, generate a new random destination
                    destX = rand.nextInt(width);
                    destY = rand.nextInt(height);
                }

                // Calculate direction towards the destination
                int dx = Integer.compare(destX, x);
                int dy = Integer.compare(destY, y);

                // Update position based on direction
                x += dx;
                y += dy;
            }
        }
    }

    @Override
    public int compareTo(Phone o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getThreadId() {
        return threadName;
    }

    public boolean isPhoneInRepairShop() {
        return (this.x == this.shopX && this.y == this.shopY);
    }

    public void interruptThread() {
        phoneThread.interrupt();
    }

    void startPhoneThread() {
        phoneThread = new Thread(this);
        phoneThread.start();
    }
}
