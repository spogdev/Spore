package dev.spog.spore.minecraft.gui;

import dev.spog.spore.format.text.Message;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.block.Container;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntPredicate;

public class SporeGui {
    // vanilla attributes
    private Inventory inventory;

    // custom attributes
    private boolean hasBorder;
    private GuiType type;

    public enum DisplayItem {
        PLACEHOLDER(Material.BLACK_STAINED_GLASS_PANE, " ", null),;

        private Material material;
        private String name;
        private List<String> lore;
        int defaultAmount;

        DisplayItem(Material material, String name, @Nullable List<String> lore) {
            this.material = material;
            this.name = name;
            this.lore = lore;
            this.defaultAmount = 1;
        }

        DisplayItem(Material material, String name, @Nullable List<String> lore, int defaultAmount) {
            this.material = material;
            this.name = name;
            this.lore = lore;
            this.defaultAmount = defaultAmount;
        }

        public ItemStack get() {
            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.displayName(Message.of(this.name).asComponent());

            if (lore != null) {
                List<TextComponent> lore = new ArrayList<>();
                for (String line : this.lore) {
                    lore.add(Message.of(line).asComponent());
                }
                meta.lore(lore);
            }

            item.setItemMeta(meta);
            return item;
        }
    }

    public SporeGui(Inventory inventory, GuiType type) {
        this.inventory = inventory;
        this.type = type;
        this.hasBorder = false;
    }

    public static SporeGui of(Container container) {
        return new SporeGui(container.getInventory(), GuiType.STORAGE);
    }

    public static SporeGui of(Inventory inventory, GuiType type) {
        return new SporeGui(inventory, type);
    }

    public void setBorder(@Nullable ItemStack border) {
        int size = this.inventory.getSize();
        int indexLim = size - 1;
        if (size < 27) return;
        this.hasBorder = border != null;

        List<Integer> topRow = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> bottomRow = List.of(indexLim, indexLim - 1, indexLim - 2, indexLim - 3, indexLim - 4, indexLim - 5, indexLim - 6, indexLim - 7, indexLim - 8);

        for (int i = 0; i <= indexLim; i++) {
            // top and bottom
            if (topRow.contains(i) || bottomRow.contains(i)) {
                this.inventory.setItem(i, border);
                continue;
            }

            // sides
            if (i % 9 == 0) {
                this.inventory.setItem(i, border);
                this.inventory.setItem(i - 1, border);
            }
        }
    }

    public void setBorder(DisplayItem item) {
        this.setBorder(item.get());
    }

    public int getExclusiveBorderSize() {
        int size = this.inventory.getSize();
        if (!hasBorder) return size;

        return size - 18 - (2 * inventory.getSize() / 9);
    }

    public void setSlot(int index, ItemStack item) {
        if (!hasBorder) inventory.setItem(index, item);
        this.inventory.setItem(index + 9 + (2 * inventory.getSize() / 9), item);
    }
}
