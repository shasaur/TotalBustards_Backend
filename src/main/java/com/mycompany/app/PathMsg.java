package com.mycompany.app;

import java.util.ArrayList;

/**
 * Created by User on 11/03/2018.
 */

public class PathMsg {
    private ArrayList<BusStop> locs;

    public PathMsg(ArrayList<BusStop> locs){
        this.locs = locs;
    }

    public ArrayList<BusStop> getLocations(){
        return locs;
    }
}
