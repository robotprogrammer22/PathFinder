package com.pathfinder.app;

import java.util.ArrayList;
import java.util.Queue;
import java.lang.Math;

class PathfinderDataHandler
{
    static int INTERSECTION = 1;
    static int ROOM = 2;
    static int BUILDINGENTRANCE = 3;
    static int STAIRUP = 4;
    static int STAIRDOWN = 5;

    private PathfinderMap bMap;
    private PathfinderMap bpMap;
    private PathfinderMap bsMap;

    public ArrayList<NavPoint> buildInsidePath(NavPoint start, Room goal, ArrayList<NavPoint>[] floors)
    {
        return translateArrayList(buildInsidePathHelper(start,goal,floors));
    }


    public ArrayList<navpointWrap> buildInsidePathHelper(NavPoint start, Room goal, ArrayList<NavPoint>[] floors)
    {
        ArrayList<navpointWrap> closedList;
        ArrayList<navpointWrap> openList;
        ArrayList<navpointWrap> recursiveList;
        NavPoint localGoal;
        int localGoalType;
        NavPoint globalStart = start;
        NavPoint globalGoal = goal;
        int globalGoalType = globalGoal.type;
        int floorIndex = globalStart.floorNum;

        navpointWrap startPoint = new navpointWrap(globalStart, 0);

        closedList = new ArrayList<>();
        openList = new ArrayList<>();
        openList.add(startPoint);

        // first, figure out if we're on the right floor
        // if we are not, navigate to the nearest set of stairs leading up/down
        if(start.floorNum != goal.floorNum)
        {
            ArrayList<NavPoint> stairList = new ArrayList<NavPoint>();
            NavPoint currPoint;

            if(floorIndex < goal.floorNum)
            {
                localGoalType = STAIRUP;
            }
            else
            {
                localGoalType = STAIRDOWN;
            }

            for(int arrLoop = 0; arrLoop <= stairList.size(); arrLoop++)
            {
                currPoint = floors[floorIndex].get(arrLoop);
                if(currPoint.type == localGoalType)
                {
                    stairList.add(currPoint);
                }
            }

            localGoal = findClosestPoint(stairList, start);
        }
        // else, we are on the right floor, navigate to the final goal.
        else
        {
            localGoal = globalGoal;
            localGoalType = globalGoalType;
        }

        //next, run A* algorithm to find shortest path to the localGoal
        // while the openList is not empty

        NavPoint visiblePoint;
        NavPoint localPoint;
        while(openList.size() > 0)
        {
            // pop the first item out of the List
            // note: I didn't use a queue here because I wanted flexibility with finding
            // items without removing the entire queue. It's also just easier.
            localPoint = openList.get(0).localNav;
            // for each point visible to that point
            for(int i = 0; i < localPoint.visiblePoints.size(); i++)
            {
                visiblePoint = localPoint.visiblePoints.get(i);
                if(visiblePoint.type == localGoal.type)
                {
                    // if the visible point is a room, check to see if it is the room we're looking for
                    if(visiblePoint == globalGoal)
                    {
                        // if it is the room we're looking for, add the current and visible point
                        // to the closed list, and end the loop.
                        closedList.add(new navpointWrap(localPoint, findF(localPoint, visiblePoint, localGoal)));
                        closedList.add(new navpointWrap(visiblePoint, 0));
                        break;
                    }
                }
                else
                {
                    // else, if it's an intersection, cal
                    // TODO: Implement the rest of this method.
                }
            }
        }

        // NEED NAVPOINT CLASS TO HAVE A LINKED PROPERTY THAT POINTS TO ANOTHER NAVPOINT OBJECT FOR THIS TO WORK
        // or just have another stair object on the next floor in the "visible" list

        // if the current floor is not the same as the floor for the the final goal, recursively call the method and append the results to the closedList
        if(start.floorNum != goal.floorNum)
        {
            recursiveList = buildInsidePathHelper(getNextStair(localGoal, localGoalType), goal, floors);
            closedList.addAll(recursiveList);
            return closedList;
        }
        // else, the final goal has been found, return the closedList
        else
        {
            return closedList;
        }
    }

    public NavPoint findClosestPoint(ArrayList<NavPoint> list, MapEntity curr)
    {
        // TODO: Implement this. Finds the point in list that has the shortest linear distance to curr.
        return null;
    }

    public NavPoint getCurrLocationAsNavpoint(double x, double y)
    {
        return new NavPoint(x, y, 0);
    }

    private double findF(NavPoint curr, NavPoint next, NavPoint goal)
    {
        double g = findDiff(curr, next); //not correct
        double h = findDiff(curr, goal); //correct

        return g + h;
    }

    private double findDiff(NavPoint curr, NavPoint goal)
    {
        double diffX = curr.x - goal.x;
        double diffY = curr.y - goal.y;

        return Math.hypot(diffX, diffY);
    }

    private NavPoint findSmallestF(ArrayList<navpointWrap> list)
    {
        // TODO: Implement this. Takes an arraylist of type navpointWrap and finds the object with the smallest field f.
        /*
            first, compare all

         */

        return null;
    }

    private ArrayList<NavPoint> translateArrayList(ArrayList<navpointWrap> oldList)
    {
        ArrayList<NavPoint> newList = new ArrayList<>();
        int oldListSize = oldList.size();

        for(int newListIter = 0; newListIter < oldListSize; newListIter++)
        {
            newList.add(oldList.get(newListIter).localNav);
        }

        return newList;
    }

    private NavPoint getNextStair(NavPoint curr, int direction)
    {
        int visiblePointSize = curr.visiblePoints.size();
        NavPoint visiblePoint;

        for(int iter = 0; iter<=visiblePointSize; iter++)
        {
            visiblePoint = curr.visiblePoints.get(iter);
            if(visiblePoint.floorNum != curr.floorNum)
            {
                return visiblePoint;
            }
        }

        return null;
    }

    private class navpointWrap
    {
        NavPoint localNav;
        double f;

        public navpointWrap(NavPoint thisNavPoint, double thisF)
        {
            this.localNav = thisNavPoint;
            this.f = thisF;
        }
    }

}
