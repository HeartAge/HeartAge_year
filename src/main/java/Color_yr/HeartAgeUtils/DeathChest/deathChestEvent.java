package Color_yr.HeartAgeUtils.DeathChest;

import Color_yr.HeartAgeUtils.DeathChest.deathChestConfigObj;
import Color_yr.HeartAgeUtils.DeathChest.deathChestDo;
import Color_yr.HeartAgeUtils.DeathChest.playSetObj;
import Color_yr.HeartAgeUtils.HeartAgeUtils;
import Color_yr.HeartAgeUtils.Hook.Hook;
import Color_yr.HeartAgeUtils.Obj.languageObj;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public class deathChestEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onplayerDeath(PlayerDeathEvent e) {
        if (e.getDrops().size() == 0)
            return;
        Player player = e.getEntity();
        playSetObj set = deathChestDo.getPlayerSet(player.getUniqueId(), true);
        deathChestConfigObj config = HeartAgeUtils.configMain.Config.getDeathChest();
        languageObj lan = HeartAgeUtils.configMain.lan;
        switch (set.getMode()) {
            case 1: {
                if (Hook.vaultCheck(player,
                        config.getCost().getSaveInLocal())) {
                    deathChestDo.RE re = deathChestDo.genDeathChest(e);
                    if (!re.ok) {
                        player.sendMessage(lan.getDeathChestCantGen());
                    } else {
                        deathChestDo.GenChest(player, re.location);
                    }
                } else {
                    player.sendMessage(lan.getDeathChestNoMoney());
                }
                break;
            }
            case 2:
                if (Hook.vaultCheck(player,
                        config.getCost().getSaveInChest())) {
                    if (!deathChestDo.setChest(e)) {
                        player.sendMessage(lan.getDeathChestError());
                    } else {
                        deathChestDo.GenLocalChest(player);
                    }
                } else {
                    player.sendMessage(lan.getDeathChestNoMoney1());
                }
                break;
            case 3: {
                if (Hook.vaultCheck(player,
                        config.getCost().getNoDrop())) {
                    e.setKeepInventory(true);
                    e.getDrops().clear();
                    e.setKeepLevel(true);
                    e.setDroppedExp(0);

                } else {
                    player.sendMessage(lan.getDeathChestNoMoney2());
                }
                break;
            }
            case 4: {
                if (Hook.vaultCheck(player,
                        config.getCost().getNoDrop())) {
                    e.setKeepInventory(true);
                    e.getDrops().clear();
                    deathChestDo.NoDrop(player);
                    return;
                } else if (Hook.vaultCheck(player,
                        config.getCost().getSaveInChest())) {
                    if (deathChestDo.setChest(e)) {
                        deathChestDo.GenLocalChest(player);
                        return;
                    }
                } else if (Hook.vaultCheck(player,
                        config.getCost().getSaveInLocal())) {
                    deathChestDo.RE re = deathChestDo.genDeathChest(e);
                    if (!re.ok) {
                        player.sendMessage(lan.getDeathChestCantGen());
                    } else {
                        deathChestDo.GenChest(player, re.location);
                    }
                } else {
                    player.sendMessage(lan.getDeathChestNoMoney());
                }
                break;
            }
            case 5: {
                if (Hook.vaultCheck(player,
                        config.getCost().getNoDrop())) {
                    e.setKeepInventory(true);
                    e.getDrops().clear();
                    deathChestDo.NoDrop(player);
                    return;
                } else if (Hook.vaultCheck(player,
                        config.getCost().getSaveInChest())) {
                    if (!deathChestDo.setChest(e)) {
                        player.sendMessage(lan.getDeathChestError());
                    } else {
                        deathChestDo.GenLocalChest(player);
                    }
                } else {
                    player.sendMessage(lan.getDeathChestNoMoney1());
                }
                break;
            }
            case 6: {
                if (Hook.vaultCheck(player,
                        config.getCost().getNoDrop())) {
                    e.setKeepInventory(true);
                    e.getDrops().clear();
                    deathChestDo.NoDrop(player);
                    return;
                } else if (Hook.vaultCheck(player,
                        config.getCost().getSaveInLocal())) {
                    deathChestDo.RE re = deathChestDo.genDeathChest(e);
                    if (!re.ok) {
                        player.sendMessage(lan.getDeathChestCantGen());
                    } else {
                        deathChestDo.GenChest(player, re.location);
                    }
                } else {
                    player.sendMessage(lan.getDeathChestNoMoney());
                }
                break;
            }
            case 7: {
                if (Hook.vaultCheck(player,
                        config.getCost().getSaveInChest())) {
                    if (deathChestDo.setChest(e)) {
                        deathChestDo.GenLocalChest(player);
                        return;
                    }
                } else if (Hook.vaultCheck(player,
                        config.getCost().getSaveInLocal())) {
                    deathChestDo.RE re = deathChestDo.genDeathChest(e);
                    if (!re.ok) {
                        player.sendMessage(lan.getDeathChestCantGen());
                    } else {
                        deathChestDo.GenChest(player, re.location);
                    }
                } else {
                    player.sendMessage(lan.getDeathChestNoMoney());
                }
                break;
            }
        }
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent e) {
        deathChestConfigObj config = HeartAgeUtils.configMain.Config.getDeathChest();
        languageObj lan = HeartAgeUtils.configMain.lan;
        Player player = e.getPlayer();
        playSetObj save = deathChestDo.getPlayerSet(player.getUniqueId(), true);
        String temp = lan.getDeathChestMode();
        int b = 0;
        int mode = save.getMode();
        if (config.getDisable().contains(mode)) {
            for (int a : config.getDisable()) {
                if (a == b)
                    b++;
                else {
                    save.setMode(b);
                    deathChestDo.setPlayerSet(player.getUniqueId(), save);
                    break;
                }
            }
        }
        temp = temp.replace("%Mode%", "" + mode);
        player.sendMessage(temp);
    }

    @EventHandler
    public void de(BlockBreakEvent e) {
        Block block = e.getBlock();
        List<MetadataValue> list = block.getMetadata("NoDrop");
        if (list.size() > 0 && list.get(0).asBoolean()) {
            e.setDropItems(false);
        }
    }

    @EventHandler
    public void selGui(InventoryCloseEvent e) {
        if (e.getPlayer() instanceof Player) {
            Player player = (Player) e.getPlayer();
            Inventory inventory = e.getInventory();
            Location location = inventory.getLocation();
            if (location == null)
                return;
            Block block = player.getWorld().getBlockAt(location);
            if (block.getType().equals(Material.CHEST)) {
                List<MetadataValue> list = block.getMetadata("NoDrop");
                if (list.size() > 0 && list.get(0).asBoolean()) {
                    for (ItemStack item : inventory) {
                        if (item != null &&
                                !item.getType().equals(Material.AIR)) {
                            return;
                        }
                    }
                    block.setType(Material.AIR);
                    player.sendMessage(HeartAgeUtils.configMain.lan.getDeathChestNull());
                }
            }
        }
    }

}
