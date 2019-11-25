package com.pathfinder.app;

import java.util.ArrayList;
import java.util.ListIterator;

class BuildingFloor
{
    ArrayList<NavPoint> navPoints;
    ArrayList<Room> rooms;
    int buildingNum;
    int floorNum;

    public BuildingFloor()
    {

    }

    public Room getRoom(int searchRoomNum)
    {
        Room currRoom;
        ListIterator<Room> iter = rooms.listIterator();

        while(iter.hasNext())
        {
            currRoom = iter.next();
            if(currRoom.roomNum == searchRoomNum)
            {
                return currRoom;
            }
        }

        return null;
    }

    public NavPoint getNavPoint(int searchPointNum)
    {
        Room currPoint;
        ListIterator<Room> iter = rooms.listIterator();

        while(iter.hasNext())
        {
            currPoint = iter.next();
            if(currPoint.id == searchPointNum)
            {
                return currPoint;
            }
        }

        return null;
    }
}
