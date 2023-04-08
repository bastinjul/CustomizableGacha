package be.julienbastin.customizablegacha.commands.subcommands.draw;

import be.julienbastin.customizablegacha.CustomizableGacha;
import be.julienbastin.customizablegacha.commands.subcommands.SubCommand;
import be.julienbastin.customizablegacha.config.Pack;
import be.julienbastin.customizablegacha.config.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract sealed class DrawSC extends SubCommand permits SingleSC, MultiSC {

    public DrawSC(String parentCommand, CustomizableGacha plugin, String permission) {
        super(parentCommand, plugin, permission);
    }

    @Override
    public boolean perform(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 0 && sender instanceof Player player) {
            return this.draw(player);
        } else if(
                (args.length == 1 || args.length == 2) && this.hasSubcommandPermission().test(sender)) {
            super.perform(sender, command, label, args);
            return true;
        }
        if(this.hasSubcommandPermission().test(sender)) {
            sender.sendMessage(subCommandUsage());
        } else {
            sender.sendMessage(drawUsage());
        }
        return false;
    }

    @Override
    public @Nullable List<String> autoComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(this.hasSubcommandPermission().test(sender)) {
            return super.autoComplete(sender, command, label, args);
        }
        return null;
    }

    protected boolean draw(Player player) {
        if(!CustomizableGacha.getEcon().has(player, this.getPrice())) {
            player.sendMessage("You don't have enough money. You need " + ChatColor.RED + this.getPrice() + "$");
            return false;
        }
        if(this.getNeededFreeSpace() > (int) Arrays.stream(player.getInventory().getStorageContents()).filter(Objects::isNull).count()) {
            player.sendMessage("You don't have enough space in your inventory. You need " + ChatColor.RED + this.getNeededFreeSpace() + " free space");
            return false;
        }
        this.performDraw(player);
        return true;
    }

    protected abstract Integer getPrice();

    protected abstract Integer getNeededFreeSpace();

    protected abstract int getDrawQuantity();

    private void performDraw(Player player) {
        for (int i = 0; i < this.getDrawQuantity(); i++) {
            performSingleDraw(player, i+1);
        }
        CustomizableGacha.getEcon().withdrawPlayer(player, this.getPrice());
        Bukkit.dispatchCommand(player, "balance");
    }

    private void performSingleDraw(Player player, int drawNbr) {
        Rarity rarity = this.getRandomRarityBasedOnProbability();
        List<Pack> packList = new ArrayList<>();
        for(Pack pack : rarity.getPacks()) {
            packList.addAll(Collections.nCopies(pack.getQuantity(), pack));
        }
        Pack randomPack = packList.get(new Random().nextInt(packList.size()));
        for(ItemStack itemStack : randomPack.getItemStackList()) {
            player.getInventory().addItem(itemStack);
        }
        player.sendMessage("============= Draw " + ChatColor.BLUE + drawNbr + "/" + this.getDrawQuantity() + ChatColor.WHITE + " =============");
        player.sendMessage("You received a pack from rarity " + ChatColor.GOLD + rarity.getName());
        player.sendMessage("You draw the following items : " + ChatColor.GOLD + randomPack.getItemStackList());
    }

    protected Rarity getRandomRarityBasedOnProbability() {
        int totalProbability = 100;
        int random = new Random().nextInt(totalProbability);
        for(Rarity rarity : this.plugin.getRarities()
                .values()
                .stream()
                .sorted(Comparator.comparing(Rarity::getProbability).reversed())
                .toList()) {
            random -= rarity.getProbability();
            if(random <= 0) {
                return rarity;
            }
        }
        throw new IllegalStateException("Impossible state");
    }

    protected abstract String subCommandUsage();

    protected abstract String drawUsage();
}
