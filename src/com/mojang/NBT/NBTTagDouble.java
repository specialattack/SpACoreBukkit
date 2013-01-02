
package com.mojang.NBT;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagDouble extends NBTBase {
    public double data;

    public NBTTagDouble(String name) {
        super(name);
    }

    public NBTTagDouble(String par1Str, double data) {
        super(par1Str);
        this.data = data;
    }

    @Override
    void write(DataOutput output) throws IOException {
        output.writeDouble(this.data);
    }

    @Override
    void load(DataInput input) throws IOException {
        this.data = input.readDouble();
    }

    @Override
    public byte getId() {
        return (byte) 6;
    }

    @Override
    public String toString() {
        return "" + this.data;
    }

    @Override
    public NBTBase copy() {
        return new NBTTagDouble(this.getName(), this.data);
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagDouble tag = (NBTTagDouble) object;
            return this.data == tag.data;
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        long bytes = Double.doubleToLongBits(this.data);
        return super.hashCode() ^ (int) (bytes ^ bytes >>> 32);
    }
}
