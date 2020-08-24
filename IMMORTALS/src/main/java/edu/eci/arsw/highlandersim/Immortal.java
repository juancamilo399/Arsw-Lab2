package edu.eci.arsw.highlandersim;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Immortal extends Thread {

    private ImmortalUpdateReportCallback updateCallback = null;

    private AtomicInteger health;

    private int defaultDamageValue;

    private final CopyOnWriteArrayList<Immortal> immortalsPopulation;

    private final String name;

    private final Random r = new Random(System.currentTimeMillis());

    private boolean pausar;

    private boolean isDeath;

    private boolean terminar;


    public Immortal(String name, CopyOnWriteArrayList<Immortal> immortalsPopulation, int health, int defaultDamageValue, ImmortalUpdateReportCallback ucb) {
        super(name);
        this.updateCallback = ucb;
        this.name = name;
        this.immortalsPopulation = immortalsPopulation;
        this.health = new AtomicInteger(health);
        this.defaultDamageValue = defaultDamageValue;
    }

    public void run() {
        while (!isDeath) {
            synchronized (this) {
                while (pausar) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            Immortal im;
            int myIndex = immortalsPopulation.indexOf(this);

            int nextFighterIndex = r.nextInt(immortalsPopulation.size());

                //avoid self-fight
            if (nextFighterIndex == myIndex) {
                nextFighterIndex = ((nextFighterIndex + 1) % immortalsPopulation.size());
            }

            im = immortalsPopulation.get(nextFighterIndex);

            this.fight(im);

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void fight(Immortal i2) {
        boolean attack = false;
        if (getHealth()<= 0) {
            immortalsPopulation.remove(this);
            isDeath = true;
        }
        else {
            synchronized (i2) {
                attack = i2.getHealth() > 0;
                if (attack) {
                    i2.changeHealth(-defaultDamageValue);
                }
            }
            synchronized (this) {
                if (attack) {
                    this.changeHealth(defaultDamageValue);
                }
            }
            updateCallback.processReport("Fight: " + this + " vs " + i2 + "\n");
        }
    }

    public void changeHealth(int v) {
        health.addAndGet(v);
    }

    public int getHealth() {
        return health.intValue();
    }

    @Override
    public String toString() {

        return name + "[" + health + "]";
    }

    synchronized void pausarhilo(){
        pausar = true;
        notifyAll();
    }

    synchronized void renaudarhilo(){
        pausar = false;
        notifyAll();
    }


    public synchronized void finish() {
        isDeath=true;
    }
}