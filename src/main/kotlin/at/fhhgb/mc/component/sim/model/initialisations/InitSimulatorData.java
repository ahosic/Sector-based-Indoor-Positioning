package at.fhhgb.mc.component.sim.model.initialisations;


import at.fhhgb.mc.component.sim.model.Position;
import at.fhhgb.mc.component.sim.model.Room;
import at.fhhgb.mc.component.sim.model.recorder.network.AccessPoint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/*
* Name: Michael Nigl
*
* ID: 1510455019
*/
public class InitSimulatorData {



    private static List<List<AccessPoint>> accessPointListList;

//    public static void initAccessPoints(List<AccessPoint> accessPoints) {
//        AccessPoint accessPoint = new AccessPoint("08:EA:44:37:29:14", new Position(80,-10));
//        accessPoints.add(accessPoint);
//        accessPoint = new AccessPoint("9C:5D:12:5E:13:14", new Position(0, 0));  //new Position(240,-10)
//        accessPoints.add(accessPoint);
//        accessPoint = new AccessPoint("9C:5D:12:5E:1D:14", new Position(80, 0)); //new Position(430,-10)
//        accessPoints.add(accessPoint);
//        accessPoint = new AccessPoint("08:EA:44:37:24:14", new Position(0, 60)); //new Position(590,-10)
//        accessPoints.add(accessPoint);
//        accessPoint = new AccessPoint("00:19:77:4E:5D:94", new Position(80, 60)); //new Position(160,-60)
//        accessPoints.add(accessPoint);
//        accessPoint = new AccessPoint("08:EA:44:37:63:D4", new Position(250,-80));
//        accessPoints.add(accessPoint);
//        accessPoint = new AccessPoint("08:EA:44:37:25:D4", new Position(320,-40));
//        accessPoints.add(accessPoint);
//        accessPoint = new AccessPoint("9C:5D:12:5D:02:94", new Position(450,-80));
//        accessPoints.add(accessPoint);
//        accessPoint = new AccessPoint("08:EA:44:37:62:94", new Position(520,-60));
//        accessPoints.add(accessPoint);
//        accessPoint = new AccessPoint("08:EA:44:37:69:94", new Position(590,-90));
//        accessPoints.add(accessPoint);
//        accessPoint = new AccessPoint("08:EA:44:37:63:54,", new Position(5,-140));
//        accessPoints.add(accessPoint);
//        accessPoint = new AccessPoint("08:EA:44:37:29:94", new Position(130,-160));
//        accessPoints.add(accessPoint);
//        accessPoint = new AccessPoint("08:EA:44:37:64:54", new Position(60,-250));
//        accessPoints.add(accessPoint);
//        accessPoint = new AccessPoint("08:EA:44:37:25:14", new Position(160,-290));
//        accessPoints.add(accessPoint);
//        accessPoint = new AccessPoint("08:EA:44:37:2A:94", new Position(180,-260));
//        accessPoints.add(accessPoint);
//        accessPoint = new AccessPoint("08:EA:44:37:63:94", new Position(385,-280));
//        accessPoints.add(accessPoint);
//        accessPoint = new AccessPoint("08:EA:44:37:64:94", new Position(520,-260));
//        accessPoints.add(accessPoint);
//    }

    /**
     *
     * @param floor
     * @return
     */
    public static List<AccessPoint> getTargetFloorAccessPoints(int floor) {
        if (accessPointListList == null) {
            accessPointListList = new ArrayList<List<AccessPoint>>();
            accessPointListList.add(initAccessPointsFloor0());
            accessPointListList.add(initAccessPointsFloor1());
            accessPointListList.add(initAccessPointsFloor2());
            accessPointListList.add(initAccessPointsFloor3());
            accessPointListList.add(initAccessPointsFloor4());
        }

        return accessPointListList.get(floor);

    }

    private static List<AccessPoint> initAccessPointsFloor0() {

        List<AccessPoint> accessPoints = new ArrayList<>();
        AccessPoint accessPoint = new AccessPoint("", new Position(40, 10), 0);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("", new Position(59, 6), 0);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("", new Position(78, 18), 0);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("", new Position(12, 25), 0);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("", new Position(37, 25), 0);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("", new Position(6, 40), 0);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("", new Position(11, 38), 0);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("", new Position(16, 40), 0);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("", new Position(35, 36), 0);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("", new Position(48, 34), 0);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("", new Position(5, 53), 0);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("", new Position(16, 52), 0);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("", new Position(42, 55), 0);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("", new Position(44, 54), 0);
        accessPoints.add(accessPoint);
        return accessPoints;
    }

    /**
     *
     * @return
     */
    private static List<AccessPoint> initAccessPointsFloor1() {
        List<AccessPoint> accessPoints = new ArrayList<>();
        AccessPoint accessPoint = new AccessPoint("", new Position(15, 7), 1);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("", new Position(7, 21), 1);
        accessPoints.add(accessPoint);
        return accessPoints;
    }

    private static List<AccessPoint> initAccessPointsFloor2() {
        List<AccessPoint> accessPoints = new ArrayList<>();
        AccessPoint accessPoint = new AccessPoint("08:EA:44:37:29:14", new Position(16, 14), 2);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("9C:5D:12:5E:13:14", new Position(45, 10), 2);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("9C:5D:12:5E:1D:14", new Position(1, 29), 2);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("08:EA:44:37:24:14", new Position(12, 47), 2);
        accessPoints.add(accessPoint);
        return accessPoints;
    }

    private static List<AccessPoint> initAccessPointsFloor3() {

        List<AccessPoint> accessPoints = new ArrayList<>();
        AccessPoint accessPoint = new AccessPoint("", new Position(1, 1), 3);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("", new Position(28, 1), 3);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("", new Position(51, 1), 3);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("", new Position(73, 1), 3);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("", new Position(39, 9), 3);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("", new Position(19, 12), 3);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("", new Position(33, 14), 3);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("", new Position(48, 19), 3);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("", new Position(65, 13), 3);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("", new Position(1, 26), 3);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("", new Position(14, 32), 3);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("", new Position(79, 20), 3);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("", new Position(23, 47), 3);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("", new Position(63, 45), 3);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("", new Position(41, 54), 3);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("", new Position(57, 52), 3);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("", new Position(73, 50), 3);
        accessPoints.add(accessPoint);
        return accessPoints;
    }

    private static List<AccessPoint> initAccessPointsFloor4() {
        List<AccessPoint> accessPoints = new ArrayList<>();
        AccessPoint accessPoint = new AccessPoint("08:EA:44:37:29:14", new Position(10, 2), 4);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("9C:5D:12:5E:13:14", new Position(30, 2), 4);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("9C:5D:12:5E:1D:14", new Position(53, 2), 4);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("08:EA:44:37:24:14", new Position(73, 2), 4);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("00:19:77:4E:5D:94", new Position(20, 12), 4);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("08:EA:44:37:63:D4", new Position(31, 15), 4);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("08:EA:44:37:25:D4", new Position(39, 8), 4);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("9C:5D:12:5D:02:94", new Position(55, 15), 4);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("08:EA:44:37:62:94", new Position(64, 12), 4);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("08:EA:44:37:69:94", new Position(73, 17), 4);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("08:EA:44:37:63:54,", new Position(1, 27), 4);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("08:EA:44:37:29:94", new Position(16, 31), 4);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("08:EA:44:37:64:54", new Position(7, 48), 4);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("08:EA:44:37:25:14", new Position(20, 56), 4);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("08:EA:44:37:2A:94", new Position(22, 50), 4);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("08:EA:44:37:63:94", new Position(47, 54), 4);
        accessPoints.add(accessPoint);
        accessPoint = new AccessPoint("08:EA:44:37:64:94", new Position(64, 50), 4);
        accessPoints.add(accessPoint);
        return accessPoints;
    }

    public static double[] getFloorBounds(int index) {
        switch (index) {
            case 0:
                return new double[]{5.2, 5.4, 20, 20.5, 80, 60};
            case 1:
                return new double[]{4.4, 70, 24, 8.5, 20, 35};
            case 2:
                return new double[]{4.1, 30.9, 11, 8.1, 60, 60};
            case 3:
                return new double[]{3.9, 6.2, 9.3, 9, 80, 60};
            case 4:
                return new double[]{4.3, 6, 9.3, 9, 80, 60};
            default:
                return new double[]{0, 0, 0, 0, 80, 60};
        }
    }

    public Room generateHallway(){
        int[] xValues = new int[]{
                650, 60, 60,
                260, 300, 340, 380,
                420, 460, 500, 540,
                580, 620, 620, 590, 590,
                570, 570, 540, 500,
                460, 420, 380, 340,
                300, 260, 130, 130,
                650, 650
        };
        int[] yValues = new int[]{
                -40, -40, -270, -270,
                -270,-268,-266, -264,
                -262,-260,-258,-256,
                -254,-244,-244,-60,
                -60, -234, -236, -238,
                -240, -242, -244,- 245,
                -245, -245,-245,-60,-60,
                -40
        };
        return new Room("Raum12", 1101, new Polygon(xValues,yValues,xValues.length));
    }

    public void generate4thFloor(List<Room> m_rooms){

        //rooms top row
        int[] xValues = {0, 80, 80, 0};
        int [] yValues = new int[]{0, 0, -40, -40};
        Room room = new Room("Raum1", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{80, 160, 160, 80};
        yValues = new int[]{0, 0, -40, -40};
        room = new Room("Raum2", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{160, 240, 240, 160};
        yValues = new int[]{0, 0, -40, -40};
        room = new Room("Raum3", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);


        xValues = new int[]{240, 320, 320, 240};
        yValues = new int[]{0, 0, -40, -40};
        room = new Room("Raum4", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{320, 350, 350, 320};
        yValues = new int[]{0, 0, -40, -40};
        room = new Room("Staircase", 666, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{350, 430, 430, 350};
        yValues = new int[]{0, 0, -40, -40};
        room = new Room("Raum5", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{430, 510, 510, 430};
        yValues = new int[]{0, 0, -40, -40};
        room = new Room("Raum6", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{510, 590, 590, 510};
        yValues = new int[]{0, 0, -40, -40};
        room = new Room("Raum7", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{590, 650, 650, 590};
        yValues = new int[]{0, 0, -40, -40};
        room = new Room("Raum8", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        //rooms left row
        xValues = new int[]{0, 60, 60, 0};
        yValues = new int[]{-40, -40, -70, -70};
        room = new Room("Raum9", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{0, 60, 60, 0};
        yValues = new int[]{-70, -70, -150, -150};
        room = new Room("Raum10", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{0, 60, 60, 0};
        yValues = new int[]{-150, -150, -230, -230};
        room = new Room("Raum11", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{0, 60, 60, 0};
        yValues = new int[]{-230, -230, -240, -240};
        room = new Room("Lift", 666, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{0, 60, 60, 0};
        yValues = new int[]{-240, -240, -270, -270};
        room = new Room("Raum12", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{0, 80, 80, 0};
        yValues = new int[]{-270, -270, -307, -310};
        room = new Room("Raum13", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        //rooms opposite left row
        xValues = new int[]{130, 160, 160, 130};
        yValues = new int[]{-60, -60, -75, -75};
        room = new Room("Toilet male", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{130, 160, 160, 130};
        yValues = new int[]{-75, -75, -90, -90};
        room = new Room("Toilet female", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{130, 160, 160, 130};
        yValues = new int[]{-90, -90, -135, -135};
        room = new Room("Raum14", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{130, 160, 160, 130};
        yValues = new int[]{-135, -135, -190, -190};
        room = new Room("Raum15", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{130, 160, 160, 130};
        yValues = new int[]{-190, -190, -245, -245};
        room = new Room("Raum16", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        //rooms below top roew
        xValues = new int[]{160, 220, 220, 160};
        yValues = new int[]{-60, -60, -90, -90};
        room = new Room("Raum17", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{220, 280, 280, 220};
        yValues = new int[]{-60, -60, -90, -90};
        room = new Room("Raum18", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{280, 340, 340, 280};
        yValues = new int[]{-60, -60, -90, -90};
        room = new Room("Raum19", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{340, 400, 400, 340};
        yValues = new int[]{-60, -60, -90, -90};
        room = new Room("Raum20", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{400, 460, 460, 400};
        yValues = new int[]{-60, -60, -90, -90};
        room = new Room("Raum21", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{460, 520, 520, 460};
        yValues = new int[]{-60, -60, -90, -90};
        room = new Room("Raum22", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{520, 570, 570, 520};
        yValues = new int[]{-60, -60, -90, -90};
        room = new Room("Raum23", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        //bottom row
        xValues = new int[]{80, 160, 160, 80};
        yValues = new int[]{-270, -270, -300, -307};
        room = new Room("Raum24", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{160, 180, 180, 160};
        yValues = new int[]{-265, -265, -298, -300};
        room = new Room("Staircase", 666, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{180, 260, 260, 180};
        yValues = new int[]{-265, -265, -295, -298};
        room = new Room("Raum25", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{260, 300, 300, 260};
        yValues = new int[]{-265, -265, -293, -295};
        room = new Room("Raum26", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{300, 340, 340, 300};
        yValues = new int[]{-265, -263, -291, -293};
        room = new Room("Raum26", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{340, 380, 380, 340};
        yValues = new int[]{-263, -261, -289, -291};
        room = new Room("Raum27", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{380, 420, 420, 380};
        yValues = new int[]{-261, -259, -287, -289};
        room = new Room("Raum28", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{420, 460, 460, 420};
        yValues = new int[]{-259, -257, -285, -287};
        room = new Room("Raum29", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{460, 500, 500, 460};
        yValues = new int[]{-257, -255, -283, -285};
        room = new Room("Raum30", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{500, 540, 540, 500};
        yValues = new int[]{-255, -253, -281, -283};
        room = new Room("Raum31", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{540, 580, 580, 540};
        yValues = new int[]{-253, -251, -279, -281};
        room = new Room("Raum32", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{580, 620, 620, 580};
        yValues = new int[]{-251, -249, -277, -279};
        room = new Room("Raum33", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        //rooms right row
        xValues = new int[]{590, 650, 650, 590};
        yValues = new int[]{-60, -60, -90, -90};
        room = new Room("Raum34", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{590, 650, 650, 590};
        yValues = new int[]{-90, -90, -170, -170};
        room = new Room("Raum35", 932, new Polygon(xValues,yValues,4));
        m_rooms.add(room);

        xValues = new int[]{590, 650, 650, 590};
        yValues = new int[]{-170, -170, -190, -190};
        room = new Room("Staircase", 666, new Polygon(xValues,yValues,4));
        m_rooms.add(room);
    }
}
