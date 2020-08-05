package dev.hevav.royaleevent.helpers;

import dev.hevav.royaleevent.RoyaleEvent;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldBorder;

import java.util.HashMap;
import java.util.List;

public class PeriodsHelper {

    private static Thread borderThread;
    private static Thread dropThread;

    public static void doPeriod(World world, Location middleLocation){
        List<HashMap<Object, Object>> borderPeriod = (List<HashMap<Object, Object>>) RoyaleEvent.config.get("periods.worldborder");
        List<HashMap<Object, Object>> dropPeriod = (List<HashMap<Object, Object>>) RoyaleEvent.config.get("periods.loot");
        WorldBorder worldBorder = world.getWorldBorder();
        worldBorder.setCenter(middleLocation);

        borderThread = new Thread(() -> {
            for (HashMap<Object, Object> border : borderPeriod){
                switch ((String) border.get("type")){
                    case "radius":
                        worldBorder.setSize((Double) border.get("radius"), (Long) border.get("time"));
                        break;
                    case "wait":
                        try {
                            Thread.sleep((Long) border.get("time"));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        });

        dropThread = new Thread(() -> {
            for (HashMap<Object, Object> drop : dropPeriod){
                switch ((String) drop.get("type")){
                    case "airdrop":
                        //TODO: random drop
                        break;
                    case "wait":
                        try {
                            Thread.sleep((Long) drop.get("time"));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        });

        borderThread.start();
        dropThread.start();
    }

    public static void stopPeriod(){
        borderThread.interrupt();
        dropThread.interrupt();
    }
}
