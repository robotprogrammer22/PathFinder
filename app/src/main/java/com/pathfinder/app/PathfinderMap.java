package com.pathfinder.app;

import java.util.ArrayList;

public class PathfinderMap
{
    ArrayList<MapEntity> entList;

    public PathfinderMap()
    {
        entList = new ArrayList<MapEntity>();
    }

    public MapEntity getEntity(int index)
    {
        return entList.get(index);
    }

    public void addEntity(MapEntity newEnt)
    {
        entList.add(newEnt);
    }
}
