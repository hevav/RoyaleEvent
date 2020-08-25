package dev.hevav.royaleevent.helpers;

import dev.hevav.royaleevent.RoyaleEvent;
import dev.hevav.royaleevent.types.Inventorable;
import dev.hevav.royaleevent.types.OtherItems;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

public class RoyaleHelper {

    private static boolean started = false;
    private static HashMap<String, Integer> killerStats = new HashMap<>();

    public static void initRoyale(Server server, World initWorld){
        server.broadcastMessage(ChatColor.GOLD + "[RE] Ивент запущен");
        server.broadcastMessage(ChatColor.GOLD + "[RE] Плагин написал hevav");
        server.broadcastMessage(ChatColor.GOLD + "[RE] Летите на элитрах с автобуса!");
        server.broadcastMessage(ChatColor.GOLD + "[RE] Посмотрите наверх для экстренной посадки");
        Location middleLocation = new Location(initWorld, (Integer) RoyaleEvent.config.get("midXcord"), (Integer) RoyaleEvent.config.get("midYcord"), (Integer) RoyaleEvent.config.get("midZcord"));
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

            player.sendTitle(ChatColor.GOLD+"Игра начата!", "RoyaleEvent by hevav", 5, 80, 5);
        }
        started = true;
        PeriodsHelper.doPeriod(server, initWorld, middleLocation);
    }

    public static void stopRoyale(Server server){
        server.broadcastMessage(ChatColor.GOLD + "[RE] Ивент закончен");
        PeriodsHelper.stopPeriod();
        killerStats.clear();
        started = false;
    }

    public static boolean isStarted(){
        return started;
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
        winner.sendTitle(ChatColor.GOLD+"Вы победитель!!!", "RoyaleEvent by hevav", 5, 80, 5);
        Server server = Bukkit.getServer();
        server.broadcastMessage(ChatColor.GOLD+"[RE] И у нас есть победитель! Это... " + winner.getName() + "!!!!");
        server.broadcastMessage(ChatColor.GOLD+"[RE] Подсчитываю топ по киллам....");
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

            server.broadcastMessage(String.format("%s[RE] %d Место %sс %d убийствами", ChatColor.GOLD, i+1, nicks, needNum));
        }

        RoyaleHelper.stopRoyale(server);
    }
}
