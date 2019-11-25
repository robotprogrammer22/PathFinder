package com.pathfinder.app;

import java.util.ArrayList;

public class Building extends MapEntity
{
    ArrayList<BuildingFloor> floors;

    public Building(double xCoord, double yCoord, int thisId)
    {
        super(xCoord, yCoord, thisId);
    }

    public Building(double xCoord, double yCoord, int thisId, String thisName)
    {
        super(xCoord, yCoord, thisId, thisName);
    }
}
