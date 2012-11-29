
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

    void write(DataOutput output) throws IOException {
        output.writeFloat(this.data);
    }

    void load(DataInput input) throws IOException {
        this.data = input.readFloat();
    }

    public byte getId() {
        return (byte) 5;
    }

    public String toString() {
        return "" + this.data;
    }

    public NBTBase copy() {
        return new NBTTagFloat(this.getName(), this.data);
    }

    public boolean equals(Object obbject) {
        if (super.equals(obbject)) {
            NBTTagFloat tag = (NBTTagFloat) obbject;
            return this.data == tag.data;
        }
        else {
            return false;
        }
    }

    public int hashCode() {
        return super.hashCode() ^ Float.floatToIntBits(this.data);
    }
}
