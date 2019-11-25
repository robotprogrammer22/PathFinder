package com.pathfinder.app;

import java.util.ArrayList;

class NavPoint extends MapEntity
{
    int type;
    int floorNum;
    int buildingNum;
    ArrayList<NavPoint> visiblePoints;

    public NavPoint(double xCoord, double yCoord, int thisId, int thisType, int thisFloorNum, int thisBuildingNum, ArrayList<NavPoint> thisVisiblePoints)
    {
        super(xCoord, yCoord, thisId);
        this.type = thisType;
        this.floorNum = thisFloorNum;
        this.buildingNum = thisBuildingNum;
        visiblePoints = new ArrayList<NavPoint>(thisVisiblePoints);
    }

    public NavPoint(double xCoord, double yCoord, int thisId, String thisName, int thisType, int thisFloorNum, int thisBuildingNum, ArrayList<NavPoint> thisVisiblePoints)
    {
        super(xCoord, yCoord, thisId, thisName);
        this.type = thisType;
        this.floorNum = thisFloorNum;
        this.buildingNum = thisBuildingNum;
        visiblePoints = new ArrayList<NavPoint>(thisVisiblePoints);
    }
}
