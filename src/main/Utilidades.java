/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Random;

/**
 *
 * @author JLHiciano
 */
public class Utilidades {
    public static float floatRand(float leftLimit,float rightLimit) {
            Random rand = new Random();
            return leftLimit + rand.nextFloat() * (rightLimit - leftLimit);
    }

    public static int intRand(int leftLimit,int rightLimit) {
            Random rand = new Random();
            return leftLimit + rand.nextInt() * (rightLimit - leftLimit);
    }
}
