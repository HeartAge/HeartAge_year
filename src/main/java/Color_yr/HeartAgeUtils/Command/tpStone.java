package Color_yr.HeartAgeUtils.Command;

import Color_yr.HeartAgeUtils.API.ICommand;
import Color_yr.HeartAgeUtils.HeartAgeUtils;
import Color_yr.HeartAgeUtils.Obj.LanguageObj;
import Color_yr.HeartAgeUtils.tpStone.tpStoneDo;
import Color_yr.HeartAgeUtils.Utils.IsNumber;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class tpStone implements ICommand {

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        LanguageObj lan = HeartAgeUtils.ConfigMain.lan;
        if (args.length == 1) {
            sender.sendMessage(lan.getTitle() + lan.getUnknown_command_tpStone());
            return true;
        } else if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(lan.getTitle() + lan.getPlayer_only_command());
            return true;
        }
        Player player = Bukkit.getPlayer(sender.getName());
        if (player == null) {
            sender.sendMessage(lan.getTitle() + lan.getNo_player_command());
            return true;
        } else if (args[0].equalsIgnoreCase("help")) {//获取帮助
            for (String a : lan.getHelp_command_tpStone()) {
                sender.sendMessage(lan.getTitle() + a);
            }
            return true;
        } else if (args[0].equalsIgnoreCase("rename")) {//重命名传送石
            ItemStack stack = player.getInventory().getItemInMainHand();
            Material item = stack.getType();
            if (!item.equals(tpStoneDo.item)) {
                sender.sendMessage(lan.getTitle() + lan.getTpStone_hand_command());
                return true;
            }
            if (args.length != 2) {
                sender.sendMessage(lan.getTitle() + lan.getTpStone_new_name_command());
                return true;
            }
            sender.sendMessage(lan.getTitle() + new tpStoneDo().rename_tpStone(stack, args[1]));
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
            return true;
        } else if (args[0].equalsIgnoreCase("uplevel")) {//升级传送石
            ItemStack stack = player.getInventory().getItemInMainHand();
            Material item = stack.getType();
            if (!item.equals(tpStoneDo.item)) {
                sender.sendMessage(lan.getTitle() + lan.getTpStone_hand_command());
                return true;
            }
            stack = player.getInventory().getItemInOffHand();
            item = stack.getType();
            if (!item.equals(tpStoneDo.updateItem)) {
                sender.sendMessage(lan.getTitle() + lan.getTpStone_offhand_command());
                return true;
            }
            sender.sendMessage(lan.getTitle() + new tpStoneDo().up_tpStone(player));
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
            return true;
        } else if (args[0].equalsIgnoreCase("setname")) {
            ItemStack stack = player.getInventory().getItemInMainHand();
            Material item = stack.getType();
            if (!item.equals(tpStoneDo.item)) {
                sender.sendMessage(lan.getTitle() + lan.getTpStone_hand_command());
                return true;
            } else if (args.length == 1) {
                sender.sendMessage(lan.getTitle() + lan.getTpStone_set_name_command());
                return true;
            } else if (args.length == 2) {
                sender.sendMessage(lan.getTitle() + lan.getTpStone_set_name_slot_command());
                return true;
            } else if (!(new IsNumber().isNumber(args[1]))) {
                sender.sendMessage(lan.getTitle() + lan.getTpStone_set_name_number_command());
                return true;
            }
            sender.sendMessage(lan.getTitle() + new tpStoneDo()
                    .set_slot_name(player.getInventory().getItemInMainHand(), Integer.decode(args[1]), args[2]));
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_XYLOPHONE, 1.0f, 1.0f);
            return true;
        } else if (args[0].equalsIgnoreCase("get")) {//给传送石
            if (!sender.hasPermission("tpStone.give")) {
                sender.sendMessage(lan.getTitle() + lan.getNoPermission_command());
                return true;
            }
            UUID uuid;
            do {
                uuid = UUID.randomUUID();
            } while (tpStoneDo.toStoneSave.containsKey(uuid.toString()));
            player.getInventory().addItem(new tpStoneDo().new_tpStone(uuid.toString()));
            sender.sendMessage(lan.getTitle() + lan.getTpStone_get_stone());
            player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_IRON, 1.0f, 1.0f);
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            list.add("help");
            list.add("rename");
            list.add("uplevel");
            list.add("setname");
            return list;
        }
        return null;
    }
}