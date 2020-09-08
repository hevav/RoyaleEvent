package dev.hevav.royaleevent.helpers;

import dev.hevav.royaleevent.RoyaleEvent;
import dev.hevav.royaleevent.listeners.BlockListener;
import dev.hevav.royaleevent.listeners.InventoryListener;
import dev.hevav.royaleevent.listeners.WeaponListener;
import dev.hevav.royaleevent.types.Inventorable;
import dev.hevav.royaleevent.types.OtherItems;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;

import java.util.*;
import java.util.stream.Collectors;

public class RoyaleHelper {
    private static final HashMap<String, Integer> killerStats = new HashMap<>();

    public static void initRoyale(Server server){
        stopRoyale(server);

        server.broadcastMessage(String.format("%s[RE] %s", ChatColor.GOLD, RoyaleEvent.config.getString("strings.gameStarted")));
        Location middleLocation = (Location) RoyaleEvent.config.get("middleLocation");
        for(Player player : server.getOnlinePlayers()){
            PlayerInventory inventory = player.getInventory();
            inventory.clear();
            inventory.setMaxStackSize(128);

            ItemStack pickaxe = new ItemStack(Material.STONE_PICKAXE);
            ItemMeta pickaxeMeta = pickaxe.getItemMeta();
            pickaxeMeta.setUnbreakable(true);
            pickaxeMeta.setDisplayName("Ломалка");
            pickaxe.setItemMeta(pickaxeMeta);

            Inventorable wood = OtherItems.Wood;
            ItemStack woodItem = new ItemStack(wood.material);
            ItemMeta woodMeta = woodItem.getItemMeta();
            woodMeta.setDisplayName(wood.name);
            woodItem.setItemMeta(woodMeta);

            Inventorable brick = OtherItems.Bricks;
            ItemStack brickItem = new ItemStack(brick.material);
            ItemMeta brickMeta = brickItem.getItemMeta();
            brickMeta.setDisplayName(brick.name);
            brickItem.setItemMeta(brickMeta);

            Inventorable iron = OtherItems.Iron;
            ItemStack ironItem = new ItemStack(iron.material);
            ItemMeta ironMeta = ironItem.getItemMeta();
            ironMeta.setDisplayName(iron.name);
            ironItem.setItemMeta(ironMeta);

            Inventorable patron = OtherItems.Patron;
            ItemStack patronItem = new ItemStack(patron.material);
            ItemMeta patronMeta = patronItem.getItemMeta();
            patronMeta.setDisplayName(patron.name);

            inventory.setItem(0, pickaxe);
            inventory.setItem(6, woodItem);
            inventory.setItem(7, brickItem);
            inventory.setItem(8, ironItem);
            inventory.setItemInOffHand(patronItem);

            inventory.setChestplate(new ItemStack(Material.ELYTRA));

            player.teleport(middleLocation);

            player.sendTitle(ChatColor.GOLD+RoyaleEvent.config.getString("strings.gameStarted"), "RoyaleEvent by hevav", 5, 80, 5);
        }
        PluginManager pluginManager = Bukkit.getPluginManager();
        RoyaleEvent plugin = RoyaleEvent.getInstance();
        pluginManager.registerEvents(new BlockListener(), plugin);
        pluginManager.registerEvents(new InventoryListener(), plugin);
        pluginManager.registerEvents(new WeaponListener(), plugin);
        PeriodsHelper.doPeriod(server, middleLocation);
    }

    public static void stopRoyale(Server server){
        server.broadcastMessage(String.format("%s[RE] %s", ChatColor.GOLD, RoyaleEvent.config.getString("strings.gameOver")));
        PeriodsHelper.stopPeriod();
        killerStats.clear();
        HandlerList.unregisterAll(RoyaleEvent.getInstance());
    }

    public static HashMap<String, Integer> getKillerStats(){
        return killerStats;
    }

    public static void addKillToStats(String nick){
        int currentKills = 0;
        if(killerStats.containsKey(nick)){
            currentKills = killerStats.get(nick);
            killerStats.remove(nick);
        }
        killerStats.put(nick, ++currentKills);
    }

    public static void proceedKill(Player killed){
        Location playerLocation = killed.getLocation();
        World world = playerLocation.getWorld();
        killed.getInventory().forEach(stack -> { if(stack != null) world.dropItem(killed.getLocation(), stack); });
        world.strikeLightningEffect(playerLocation);
        List<Player> survived = Bukkit.getServer().getOnlinePlayers().stream().filter(player -> player.getGameMode() == GameMode.SURVIVAL && player.getHealth() > 0).collect(Collectors.toList());
        if (survived.size() == 1) {
            RoyaleHelper.proceedWinner(survived.get(0));
        }
    }

    public static void proceedWinner(Player winner){
        winner.sendTitle(ChatColor.GOLD+RoyaleEvent.config.getString("strings.playerIsWinner"), "RoyaleEvent by hevav", 5, 80, 5);
        Server server = Bukkit.getServer();
        server.broadcastMessage(String.format("%s[RE] %s %s!!!", ChatColor.GOLD, RoyaleEvent.config.getString("strings.winnerIs"), winner.getName()));
        server.broadcastMessage(String.format("%s[RE] %s", ChatColor.GOLD, RoyaleEvent.config.getString("strings.sortStarted")));
        HashMap<String, Integer> killerStats = RoyaleHelper.getKillerStats();
        Set<Integer> killsSet = new LinkedHashSet<>(killerStats.values());
        List<Integer> killsTop = new ArrayList<>(killsSet);
        Collections.sort(killsTop);
        Collections.reverse(killsTop);
        killsTop.clear();
        killsTop.addAll(killsSet);

        for(int i = 0; i < Math.min(10, killsTop.size()); i++){
            StringBuilder nicks = new StringBuilder();
            int needNum = killsTop.get(i);
            killerStats.forEach((String nick, Integer num)->{
                if(num == needNum){
                    nicks.append(nick).append(" ");
                }
            });

            server.broadcastMessage(String.format("%s[RE] %d %s %sс %d %s", ChatColor.GOLD, i+1, RoyaleEvent.config.getString("strings.place"), nicks, needNum, RoyaleEvent.config.getString("strings.withKills")));
        }

        RoyaleHelper.stopRoyale(server);
    }
}
