
package com.mojang.NBT;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagByteArray extends NBTBase {
    public byte[] byteArray;

    public NBTTagByteArray(String name) {
        super(name);
    }

    public NBTTagByteArray(String name, byte[] data) {
        super(name);
        this.byteArray = data;
    }

    @Override
    void write(DataOutput output) throws IOException {
        output.writeInt(this.byteArray.length);
        output.write(this.byteArray);
    }

    @Override
    void load(DataInput input) throws IOException {
        int var2 = input.readInt();
        this.byteArray = new byte[var2];
        input.readFully(this.byteArray);
    }

    @Override
    public byte getId() {
        return (byte) 7;
    }

    @Override
    public String toString() {
        return "[" + this.byteArray.length + " bytes]";
    }

    @Override
    public NBTBase copy() {
        byte[] data = new byte[this.byteArray.length];
        System.arraycopy(this.byteArray, 0, data, 0, this.byteArray.length);
        return new NBTTagByteArray(this.getName(), data);
    }

    @Override
    public boolean equals(Object object) {
        return super.equals(object) ? Arrays.equals(this.byteArray, ((NBTTagByteArray) object).byteArray) : false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.byteArray);
    }
}
