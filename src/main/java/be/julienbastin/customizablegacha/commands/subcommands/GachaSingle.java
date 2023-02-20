package be.julienbastin.customizablegacha.commands.subcommands;

import be.julienbastin.customizablegacha.config.models.Pack;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GachaSingle extends SubCommand<Player> {

    public GachaSingle(JavaPlugin javaPlugin) {
        super("/czgacha", javaPlugin);
    }

    @Override
    public @NotNull String getValue() {
        return "single";
    }

    @Override
    public @NotNull String description() {
        return "Single Pack Draw";
    }

    @Override
    public void perform(@NotNull Player sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ItemStack test = new ItemStack(Material.DIAMOND_ORE, 10);
        sender.getInventory().addItem(test);
        List<Pack> packs = this.plugin
                .getConfig()
                .getMapList("packs")
                .stream()
                .map(Pack::new)
                .toList();
        sender.sendMessage("List of packs : " + packs);
    }
}
