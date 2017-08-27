package org.thane;

import net.minecraft.server.v1_11_R1.NBTTagCompound;
import net.minecraft.server.v1_11_R1.NBTTagList;
import net.minecraft.server.v1_11_R1.NBTTagString;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ThaneTag {

    public static ItemStack attributeItemStack(Material material, List<String> breakBlocks, List<String> placeBlocks) {

        net.minecraft.server.v1_11_R1.ItemStack stack = CraftItemStack.asNMSCopy(new ItemStack(material, 1));
        if (!breakBlocks.isEmpty()) {

            NBTTagList idsTag = new NBTTagList();
            for (String block : breakBlocks) {
                idsTag.add(new NBTTagString(block));
            }
            NBTTagCompound tag = stack.hasTag() ? stack.getTag() : new NBTTagCompound();
            tag.set("CanDestroy", idsTag);
            stack.setTag(tag);
        }
        if (!placeBlocks.isEmpty()) {

            NBTTagList idsTag = new NBTTagList();
            for (String block : placeBlocks) {
                idsTag.add(new NBTTagString(block));
            }
            NBTTagCompound tag = stack.hasTag() ? stack.getTag() : new NBTTagCompound();
            tag.set("CanDestroy", idsTag);
            stack.setTag(tag);
        }
        return CraftItemStack.asBukkitCopy(stack);
    }
}
