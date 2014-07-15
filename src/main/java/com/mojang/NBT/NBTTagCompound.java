
package com.mojang.NBT;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

import net.specialattack.bukkit.core.SpACore;

public class NBTTagCompound extends NBTBase {
    private Map<String, NBTBase> tagMap = new HashMap<String, NBTBase>();

    public NBTTagCompound() {
        super("");
    }

    public NBTTagCompound(String name) {
        super(name);
    }

    @Override
    void write(DataOutput output) throws IOException {
        Iterator<NBTBase> iterator = this.tagMap.values().iterator();

        while (iterator.hasNext()) {
            NBTBase tag = iterator.next();
            NBTBase.writeNamedTag(tag, output);
        }

        output.writeByte(0);
    }

    @Override
    void load(DataInput input) throws IOException {
        this.tagMap.clear();
        NBTBase tag;

        while ((tag = NBTBase.readNamedTag(input)).getId() != 0) {
            this.tagMap.put(tag.getName(), tag);
        }
    }

    public Collection<NBTBase> getTags() {
        return this.tagMap.values();
    }

    @Override
    public byte getId() {
        return (byte) 10;
    }

    public void setTag(String name, NBTBase base) {
        this.tagMap.put(name, base.setName(name));
    }

    public void setByte(String name, byte data) {
        this.tagMap.put(name, new NBTTagByte(name, data));
    }

    public void setShort(String name, short data) {
        this.tagMap.put(name, new NBTTagShort(name, data));
    }

    public void setInteger(String name, int data) {
        this.tagMap.put(name, new NBTTagInt(name, data));
    }

    public void setLong(String name, long data) {
        this.tagMap.put(name, new NBTTagLong(name, data));
    }

    public void setFloat(String name, float data) {
        this.tagMap.put(name, new NBTTagFloat(name, data));
    }

    public void setDouble(String name, double data) {
        this.tagMap.put(name, new NBTTagDouble(name, data));
    }

    public void setString(String name, String data) {
        this.tagMap.put(name, new NBTTagString(name, data));
    }

    public void setByteArray(String name, byte[] data) {
        this.tagMap.put(name, new NBTTagByteArray(name, data));
    }

    public void setIntArray(String name, int[] data) {
        this.tagMap.put(name, new NBTTagIntArray(name, data));
    }

    public void setCompoundTag(String name, NBTTagCompound data) {
        this.tagMap.put(name, data.setName(name));
    }

    public void setBoolean(String name, boolean data) {
        this.setByte(name, (byte) (data ? 1 : 0));
    }

    public NBTBase getTag(String name) {
        return (NBTBase) this.tagMap.get(name);
    }

    public boolean hasKey(String name) {
        return this.tagMap.containsKey(name);
    }

    public byte getByte(String name) {
        try {
            return !this.tagMap.containsKey(name) ? 0 : ((NBTTagByte) this.tagMap.get(name)).data;
        }
        catch (ClassCastException ex) {
            SpACore.log(Level.WARNING, "Failed getting byte from NBTTagCompound", ex);
            return 0;
        }
    }

    public short getShort(String name) {
        try {
            return !this.tagMap.containsKey(name) ? 0 : ((NBTTagShort) this.tagMap.get(name)).data;
        }
        catch (ClassCastException ex) {
            SpACore.log(Level.WARNING, "Failed getting short from NBTTagCompound", ex);
            return 0;
        }
    }

    public int getInteger(String name) {
        try {
            return !this.tagMap.containsKey(name) ? 0 : ((NBTTagInt) this.tagMap.get(name)).data;
        }
        catch (ClassCastException ex) {
            SpACore.log(Level.WARNING, "Failed getting integer from NBTTagCompound", ex);
            return 0;
        }
    }

    public long getLong(String name) {
        try {
            return !this.tagMap.containsKey(name) ? 0L : ((NBTTagLong) this.tagMap.get(name)).data;
        }
        catch (ClassCastException ex) {
            SpACore.log(Level.WARNING, "Failed getting long from NBTTagCompound", ex);
            return 0;
        }
    }

    public float getFloat(String name) {
        try {
            return !this.tagMap.containsKey(name) ? 0.0F : ((NBTTagFloat) this.tagMap.get(name)).data;
        }
        catch (ClassCastException ex) {
            SpACore.log(Level.WARNING, "Failed getting float from NBTTagCompound", ex);
            return 0;
        }
    }

    public double getDouble(String name) {
        try {
            return !this.tagMap.containsKey(name) ? 0.0D : ((NBTTagDouble) this.tagMap.get(name)).data;
        }
        catch (ClassCastException ex) {
            SpACore.log(Level.WARNING, "Failed getting double from NBTTagCompound", ex);
            return 0;
        }
    }

    public String getString(String name) {
        try {
            return !this.tagMap.containsKey(name) ? "" : ((NBTTagString) this.tagMap.get(name)).data;
        }
        catch (ClassCastException ex) {
            SpACore.log(Level.WARNING, "Failed getting string from NBTTagCompound", ex);
            return null;
        }
    }

    public byte[] getByteArray(String name) {
        try {
            return !this.tagMap.containsKey(name) ? new byte[0] : ((NBTTagByteArray) this.tagMap.get(name)).byteArray;
        }
        catch (ClassCastException ex) {
            SpACore.log(Level.WARNING, "Failed getting byte array from NBTTagCompound", ex);
            return null;
        }
    }

    public int[] getIntArray(String name) {
        try {
            return !this.tagMap.containsKey(name) ? new int[0] : ((NBTTagIntArray) this.tagMap.get(name)).intArray;
        }
        catch (ClassCastException ex) {
            SpACore.log(Level.WARNING, "Failed getting integer array from NBTTagCompound", ex);
            return null;
        }
    }

    public NBTTagCompound getCompoundTag(String name) {
        try {
            return !this.tagMap.containsKey(name) ? new NBTTagCompound(name) : (NBTTagCompound) this.tagMap.get(name);
        }
        catch (ClassCastException ex) {
            SpACore.log(Level.WARNING, "Failed getting tag compound from NBTTagCompound", ex);
            return null;
        }
    }

    public NBTTagList getTagList(String name) {
        try {
            return !this.tagMap.containsKey(name) ? new NBTTagList(name) : (NBTTagList) this.tagMap.get(name);
        }
        catch (ClassCastException ex) {
            SpACore.log(Level.WARNING, "Failed getting tag list from NBTTagCompound", ex);
            return null;
        }
    }

    public boolean getBoolean(String name) {
        return this.getByte(name) != 0;
    }

    public void removeTag(String name) {
        this.tagMap.remove(name);
    }

    @Override
    public String toString() {
        return "" + this.tagMap.size() + " entries";
    }

    public boolean hasNoTags() {
        return this.tagMap.isEmpty();
    }

    @Override
    public NBTBase copy() {
        NBTTagCompound compound = new NBTTagCompound(this.getName());
        Iterator<String> iterator = this.tagMap.keySet().iterator();

        while (iterator.hasNext()) {
            String name = iterator.next();
            compound.setTag(name, ((NBTBase) this.tagMap.get(name)).copy());
        }

        return compound;
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagCompound tag = (NBTTagCompound) object;
            return this.tagMap.entrySet().equals(tag.tagMap.entrySet());
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ this.tagMap.hashCode();
    }

    static Map<String, NBTBase> getTagMap(NBTTagCompound compound) {
        return compound.tagMap;
    }
}
