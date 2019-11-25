package com.pathfinder.app;

public class MapEntity
{
    double x;
    double y;
    int id;
    String name;

    public MapEntity(double xCoord, double yCoord, int thisId)
    {
        this.x = xCoord;
        this.y = yCoord;
        this.id = thisId;
    }

    public MapEntity(double xCoord, double yCoord, int thisId, String thisName)
    {
        this.x = xCoord;
        this.y = yCoord;
        this.id = thisId;
        this.name = thisName;
    }
}
