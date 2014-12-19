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

    @Override
    void write(DataOutput otput) throws IOException {
        otput.writeByte(this.data);
    }

    @Override
    void load(DataInput input) throws IOException {
        this.data = input.readByte();
    }

    @Override
    public byte getId() {
        return (byte) 1;
    }

    @Override
    public String toString() {
        return "" + this.data;
    }

    @Override
    public NBTBase copy() {
        return new NBTTagByte(this.getName(), this.data);
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagByte tag = (NBTTagByte) object;
            return this.data == tag.data;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ this.data;
    }
}
