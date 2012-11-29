
package com.mojang.NBT;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagByte extends NBTBase {
    public byte data;

    public NBTTagByte(String name) {
        super(name);
    }

    public NBTTagByte(String name, byte data) {
        super(name);
        this.data = data;
    }

    void write(DataOutput otput) throws IOException {
        otput.writeByte(this.data);
    }

    void load(DataInput input) throws IOException {
        this.data = input.readByte();
    }

    public byte getId() {
        return (byte) 1;
    }

    public String toString() {
        return "" + this.data;
    }

    public NBTBase copy() {
        return new NBTTagByte(this.getName(), this.data);
    }

    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagByte tag = (NBTTagByte) object;
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
