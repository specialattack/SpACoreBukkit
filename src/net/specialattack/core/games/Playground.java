
package net.specialattack.core.games;

import net.specialattack.core.SpACore;
import net.specialattack.core.block.Cuboid;

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
        return this.id;
    }

    public NBTTagCompound savePlayground() {
        NBTTagCompound compound = new NBTTagCompound(this.getId() + "-" + this.getTypeName());

        compound.setString("world", this.cuboid.getWorld().getName());

        NBTTagList start = new NBTTagList("start");
        start.appendTag(new NBTTagInt("x", this.cuboid.getStartX()));
        start.appendTag(new NBTTagInt("y", this.cuboid.getStartY()));
        start.appendTag(new NBTTagInt("z", this.cuboid.getStartZ()));
        compound.setTag("start", start);

        NBTTagList end = new NBTTagList("end");
        end.appendTag(new NBTTagInt("x", this.cuboid.getEndX()));
        end.appendTag(new NBTTagInt("y", this.cuboid.getEndY()));
        end.appendTag(new NBTTagInt("z", this.cuboid.getEndZ()));
        compound.setTag("end", end);

        NBTTagCompound data = this.savePlaygroundAdditionalData();

        if (data != null) {
            compound.setCompoundTag("additional", data);
        }

        return compound;
    }

    public void loadPlayground(NBTTagCompound compound) {
        if (compound.hasKey("additional")) {
            this.loadPlaygroundAdditionalData(compound.getCompoundTag("additional"));
        }
    }
}
