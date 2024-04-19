/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Question_2;

import Question_1.LinkedList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Caleb
 */
public class Panel extends JPanel implements KeyListener, ComponentListener {

    LinkedList<Phone> list = new LinkedList<>();
    JFrame frame;
    int shopX;
    int shopY;
    int shopWidth;
    int shopHeight;
    Image healthyImg = new ImageIcon("mobile-notch.png").getImage();
    Image infectedImg = new ImageIcon("virus.png").getImage();
    Image repairingImg = new ImageIcon("repairing50x50.png").getImage();
    Image RepairShopImg = new ImageIcon("garage-open.png").getImage();

    public Panel(JFrame frame) {
        this.frame = frame;
        this.addKeyListener(this);
        this.addComponentListener(this);
        this.setFocusable(true);
        this.shopX = 500;
        this.shopY = 500;
        this.shopHeight = 50;
        this.shopWidth = 50;
        startThread();
    }

    //starts a phone thread. keep moving unless
    //interrupted (life == 0)
    private void startThread() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10);

                    for (int i = 0; i < list.size; i++) {
                        Phone phone = list.getData(i);
                        if (phone != null) {
                            if (phone.phoneHealth()) {
                                list.remove(i);
                                phone.interruptThread();
                            } else {
                                phone.move();
                            }
                        }
                    }
                    repaint();
                    giveVirus();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }).start();
    }

    //Euclidean is used to calculate distance between two points using their X and Y coordinates
    //This method is used to check if a phone is close enough to be infected.
    private synchronized double getEuclidean(Phone phone1, Phone phone2) {
        return Math.sqrt(Math.pow(phone1.getX() - phone2.getX(), 2)
                + Math.pow(phone1.getY() - phone2.getY(), 2));
    }

    //checks if phone is infected and exists.
    private synchronized void giveVirus() {
        for (int i = 0; i < list.size; i++) {
            Phone phone1 = list.getData(i);
            if (phone1 != null && phone1.isPhoneInfected()) {
                infectNearbyPhones(phone1);
            }
        }
    }

    //checks if distance from 1 phone to another is less than 50, if it is
    //infect the other phone
    private synchronized void infectNearbyPhones(Phone infectedPhone) {
        for (int j = 0; j < list.size; j++) {
            Phone phone2 = list.getData(j);
            if (phone2 != null && phone2 != infectedPhone && !phone2.isPhoneInfected() && !phone2.repairing && getEuclidean(infectedPhone, phone2) <= 50) {
                phone2.setPhoneInfected(true);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //draw repair shop
        g.drawRect(shopX, shopY, shopWidth, shopHeight);
        g.drawImage(RepairShopImg, shopX - 39, shopY - 80, frame);
        g.drawString("Repair Shop", shopX - 9, shopY - 8);

        for (int i = 0; i < list.size; i++) {
            Phone phone = list.getData(i);
            if (phone != null) {
                Image img;
                //img determined by phone status
                if (phone.isPhoneInfected() && !phone.repairing) {
                    img = infectedImg;
                } else if (phone.isPhoneRepairing()) {
                    img = repairingImg;
                } else {
                    img = healthyImg;
                }
                g.drawImage(img, phone.getX(), phone.getY(), this);
                g.setColor(Color.BLACK);
                g.drawString("Health: " + Integer.toString(phone.phoneLife), phone.getX(), phone.getY() - 10);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int keyCode = ke.getKeyCode();
        if (keyCode == KeyEvent.VK_UP) {

            Phone newPhone = new Phone(shopX, shopY);
            newPhone.startPhoneThread();
            newPhone.setRange(frame.getWidth(), frame.getHeight());
            list.add(newPhone);

        } else if (keyCode == KeyEvent.VK_V) {
            Phone randomPhone = getRandomPhone();
            if (randomPhone == null) {

            } else {
                while (randomPhone.inRepairShop) {
                    randomPhone = getRandomPhone();
                }
                randomPhone.setPhoneInfected(true); //set phone as infected
                repaint();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {

    }

    @Override
    public void componentResized(ComponentEvent ce) {
    }

    @Override
    public void componentMoved(ComponentEvent ce) {

    }

    @Override
    public void componentShown(ComponentEvent ce) {

    }

    @Override
    public void componentHidden(ComponentEvent ce) {

    }

    private Phone getRandomPhone() {
        if (list.size == 0) {
            return null; // List is empty
        }
        Random rand = new Random();
        int index = rand.nextInt(list.size);
        return list.getData(index);
    }
}
