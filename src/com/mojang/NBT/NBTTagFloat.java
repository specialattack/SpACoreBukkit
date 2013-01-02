
package com.mojang.NBT;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagFloat extends NBTBase {
    public float data;

    public NBTTagFloat(String name) {
        super(name);
    }

    public NBTTagFloat(String name, float data) {
        super(name);
        this.data = data;
    }

    @Override
    void write(DataOutput output) throws IOException {
        output.writeFloat(this.data);
    }

    @Override
    void load(DataInput input) throws IOException {
        this.data = input.readFloat();
    }

    @Override
    public byte getId() {
        return (byte) 5;
    }

    @Override
    public String toString() {
        return "" + this.data;
    }

    @Override
    public NBTBase copy() {
        return new NBTTagFloat(this.getName(), this.data);
    }

    @Override
    public boolean equals(Object obbject) {
        if (super.equals(obbject)) {
            NBTTagFloat tag = (NBTTagFloat) obbject;
            return this.data == tag.data;
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ Float.floatToIntBits(this.data);
    }
}
