package at.fhhgb.mc.component.utility;

import at.fhhgb.mc.component.sim.model.recorder.network.DataSnapshot;
import at.fhhgb.mc.component.sim.model.recorder.network.WLanData;
import at.fhhgb.mc.interfaces.PlayBackEnum;
import at.fhhgb.mc.interfaces.PlaybackCallbackInterface;

import java.util.List;

public class Player {

    final PlayBackEnum playBackEnum;
    List<DataSnapshot> dataSnapshots;
    PlaybackCallbackInterface callback;
    private long playBackSpeed = 500;
    private int[] wayPointCount;

    public Player(List<DataSnapshot> dataSnapshots, PlaybackCallbackInterface callback, PlayBackEnum playBackEnum){
       this.dataSnapshots = dataSnapshots;
        this.callback = callback;
        this.playBackEnum = playBackEnum;
        setNumberOfAPSteps();
    }

    private void setNumberOfAPSteps(){
        wayPointCount = new int[dataSnapshots.get(dataSnapshots.size()-1).getWayPointID()];
        for(DataSnapshot dataSnapshot : dataSnapshots){
            wayPointCount[dataSnapshot.getWayPointID()-1]+=1;
        }
        callback.wayPointCount(wayPointCount);
    }

    public void setPlayBackSpeed(long playBackSpeed) {
        this.playBackSpeed = playBackSpeed;
    }

    public boolean startPlayback(){
        System.out.println(dataSnapshots.size());
        int i = 1;
        int signallevel = -1000;
        String bssid = "";
        for(DataSnapshot dataSnapshot : dataSnapshots){
            System.out.println("Snapshot "+i);
            i++;
            for(WLanData wLanData : dataSnapshot.getwLanDataList()){
                if(wLanData.getSSID().equals("fhhgb")){
                   /* if(playBackEnum == PlayBackEnum.SIMPLE){
                        List<AccessPoint> accessPoints = wLanData.getAccessPoints();
                        Collections.sort(accessPoints);
                        Collections.reverse(accessPoints);
                        callback.nearestAccessPoint(accessPoints);
                    }else if(playBackEnum == PlayBackEnum.WEIGHTED_DISTANCES){
                        callback.allAccessPoints(wLanData.getAccessPoints());
                    }*/
                    //TODO: REMOVE IF IT WORKS

                    callback.allAccessPoints(wLanData.getAccessPoints());
                }
            }
            try {
                Thread.sleep(10);
//                if(playBackSpeed >1){
//                    Thread.sleep(500/playBackSpeed);
//                }else{
//                    Thread.sleep(500*((playBackSpeed-2)*-1));
//                }
            } catch (InterruptedException e) {
            }
        }
        return false;
    }
}
