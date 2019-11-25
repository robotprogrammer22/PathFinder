package com.pathfinder.app;

import java.util.ArrayList;

class Room extends NavPoint
{
    int roomNum;

    public Room(double xCoord, double yCoord, int thisId, int thisType, int thisFloorNum, int thisBuildingNum, ArrayList<NavPoint> thisVisiblePoints, int thisRoomNum)
    {
        super(xCoord, yCoord, thisId, thisType, thisFloorNum, thisBuildingNum, thisVisiblePoints);
        this.roomNum = thisRoomNum;
    }

    public Room(double xCoord, double yCoord, int thisId, String thisName, int thisType, int thisFloorNum, int thisBuildingNum, ArrayList<NavPoint> thisVisiblePoints, int thisRoomNum)
    {
        super(xCoord, yCoord, thisId, thisName, thisType, thisFloorNum, thisBuildingNum, thisVisiblePoints);
        this.roomNum = thisRoomNum;
    }
}
