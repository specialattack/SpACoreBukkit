
package com.mojang.NBT;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagShort extends NBTBase {
    public short data;

    public NBTTagShort(String name) {
        super(name);
    }

    public NBTTagShort(String name, short value) {
        super(name);
        this.data = value;
    }

    void write(DataOutput output) throws IOException {
        output.writeShort(this.data);
    }

    void load(DataInput input) throws IOException {
        this.data = input.readShort();
    }

    public byte getId() {
        return (byte) 2;
    }

    public String toString() {
        return "" + this.data;
    }

    public NBTBase copy() {
        return new NBTTagShort(this.getName(), this.data);
    }

    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagShort tag = (NBTTagShort) object;
            return this.data == tag.data;
        }
        else {
            return false;
        }
    }

    public int hashCode() {
        return super.hashCode() ^ this.data;
    }
}
