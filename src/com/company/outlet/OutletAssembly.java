package com.company.outlet;

import com.company.inventory.Beverage;

import java.util.PriorityQueue;

/*
    Class to represent the set of outlets on the machine
    and manage all the outlets in a single place.
 */
public class OutletAssembly  {


    private PriorityQueue<Outlet> assemblyQueue;  // Queue of available outlets

    private int numOutlets;

    private static OutletAssembly instance;

    private OutletAssembly(){
        assemblyQueue = new PriorityQueue<>();
    }

    public static OutletAssembly getInstance() {
        if (instance == null) {
            synchronized (OutletAssembly.class) {
                if(instance==null) {
                    instance = new OutletAssembly();
                }
            }
        }
        return instance;
    }

    /*
        Create the outlets in the assembly
     */
    public synchronized void initializeAssembly(int size){
        for(int i=0;i<size;i++){
            assemblyQueue.add(new Outlet(i));
        }
        this.numOutlets = size;
    }

    /*
        Add outlet to the assemblyQueue
     */
    public synchronized void addOutlet(Outlet outlet){
        assemblyQueue.add(outlet);
    }

    /*
        Get an outlet and serve the beverage
     */
    public boolean serve(Beverage beverage){
        Outlet outlet = assemblyQueue.poll();
        if(outlet == null){
            return false;
        }
        outlet.serveBeverage(beverage);
        return true;
    }

    public PriorityQueue<Outlet> getAssemblyQueue() {
        return assemblyQueue;
    }

    public int getNumOutlets() {
        return numOutlets;
    }
}
