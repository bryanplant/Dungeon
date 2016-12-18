package com.plant;

/**
 * Created by Bryan on 8/12/2016.
 */
public class Gun {
    int fireRate;
    int fireCounter;
    boolean ready;

    public Gun(int fireRate)
    {
        this.fireRate = fireRate;
        ready = true;
    }

    public void update(float dt)
    {
        if(!ready) {
            fireCounter += dt;
            if (fireCounter >= fireRate) {
                ready = true;
                fireCounter = 0;
            }
        }
    }
}
