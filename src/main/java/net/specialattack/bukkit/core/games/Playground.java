package net.specialattack.bukkit.core.games;

import java.util.UUID;

import net.specialattack.bukkit.core.block.Cuboid;

import org.bukkit.Bukkit;
import org.bukkit.World;

import com.mojang.NBT.NBTTagCompound;
import com.mojang.NBT.NBTTagInt;
import com.mojang.NBT.NBTTagList;

public abstract class Playground{

    protected Cuboid cuboid;
    private UUID id;
    public boolean errored = false; // Use me to check if the playground has had any errors. If true then the playground should attempt to finish its current game and refuse to start a new one
    private NBTTagCompound backup;

  //First commit of 2015!
    protected Playground(Cuboid cuboid) {
        this.cuboid = cuboid;
        this.id = UUID.randomUUID();
    }
    
    /**
     * 
     * This constructor should be called when the user wishes to load a playground.
     * 
     */
    protected Playground(UUID id, NBTTagCompound compound){
    	this.id = id;
    	this.loadPlayground(id, compound);
    }
    
    protected abstract NBTTagCompound savePlaygroundAdditionalData();

    protected abstract void loadPlaygroundAdditionalData(NBTTagCompound compound);

    public abstract String getTypeName();

    public UUID getUniqueId() {
        return this.id;
    }
    
    public Cuboid getCuboid() {
		return cuboid;
	}

    public NBTTagCompound savePlayground() {
        if (this.errored) {
            this.backup.setName(this.getUniqueId().toString());

            return this.backup;
        }

        NBTTagCompound compound = new NBTTagCompound(this.getUniqueId().toString());

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

    public void loadPlayground(UUID uuid, NBTTagCompound compound) {
        this.backup = compound;

        String worldName = compound.getString("world");

        World world = Bukkit.getWorld(worldName);

        if (world == null) {
            this.errored = true;

            return;
        }

        NBTTagList start = compound.getTagList("start");
        int x1 = ((NBTTagInt) start.tagAt(0)).data;
        int y1 = ((NBTTagInt) start.tagAt(1)).data;
        int z1 = ((NBTTagInt) start.tagAt(2)).data;

        NBTTagList end = compound.getTagList("end");
        int x2 = ((NBTTagInt) end.tagAt(0)).data;
        int y2 = ((NBTTagInt) end.tagAt(1)).data;
        int z2 = ((NBTTagInt) end.tagAt(2)).data;

        this.cuboid = new Cuboid(world, x1, y1, z1, x2, y2, z2);

        if (compound.hasKey("additional")) {
            this.loadPlaygroundAdditionalData(compound.getCompoundTag("additional"));
        }
    }
    
    @Override
    public boolean equals(Object obj) {
    	
    	if (obj == null) return false;
    	if (!(obj instanceof Playground)) return false;
    	
    	return ((Playground)obj).getUniqueId().equals(this.getUniqueId());
    }
    
    @Override
    public int hashCode() {
    	return (int) (this.getUniqueId().getMostSignificantBits() * this.getUniqueId().getLeastSignificantBits());
    }
    
}
