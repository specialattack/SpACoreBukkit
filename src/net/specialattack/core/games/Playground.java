
package net.specialattack.core.games;

import java.io.FileNotFoundException;
import java.io.IOException;

import net.specialattack.core.PlayerStorage;
import net.specialattack.core.SpACore;
import net.specialattack.core.block.Cuboid;

import org.bukkit.entity.Player;

import com.mojang.NBT.NBTTagCompound;
import com.mojang.NBT.NBTTagInt;
import com.mojang.NBT.NBTTagList;

public abstract class Playground {
    protected Cuboid cuboid;
    private final int id;

    protected Playground(Cuboid cuboid) {
        this.cuboid = cuboid;
        this.id = SpACore.getNextAvailablePlaygroundId();
    }

    protected abstract NBTTagCompound savePlaygroundAdditionalData();

    protected abstract void loadPlaygroundAdditionalData(NBTTagCompound compound);

    public abstract String getTypeName();

    public int getId() {
        return id;
    }

    public NBTTagCompound savePlayground() {
        NBTTagCompound compound = new NBTTagCompound(getId() + "-" + getTypeName());

        compound.setString("world", cuboid.getWorld().getName());

        NBTTagList start = new NBTTagList("start");
        start.appendTag(new NBTTagInt("x", cuboid.getStartX()));
        start.appendTag(new NBTTagInt("y", cuboid.getStartY()));
        start.appendTag(new NBTTagInt("z", cuboid.getStartZ()));
        compound.setTag("start", start);

        NBTTagList end = new NBTTagList("end");
        end.appendTag(new NBTTagInt("x", cuboid.getEndX()));
        end.appendTag(new NBTTagInt("y", cuboid.getEndY()));
        end.appendTag(new NBTTagInt("z", cuboid.getEndZ()));
        compound.setTag("end", end);

        NBTTagCompound data = savePlaygroundAdditionalData();

        if (data != null) {
            compound.setCompoundTag("additional", data);
        }

        return compound;
    }
    
    public void addPlayerAsIs(Player player){
    	addPlayer(player, false);
    }
    
    public void addPlayer(Player player, boolean saveAndClearPlayer){
    	//TODO
    }
    
    public void loadPlayground(NBTTagCompound compound) {
        if (compound.hasKey("additional")) {
            loadPlaygroundAdditionalData(compound.getCompoundTag("additional"));
        }
    }
}
