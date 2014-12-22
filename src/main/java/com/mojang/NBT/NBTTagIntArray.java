package com.mojang.NBT;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagIntArray extends NBTBase {

    public int[] intArray;

    public NBTTagIntArray(String name) {
        super(name);
    }

    public NBTTagIntArray(String name, int[] data) {
        super(name);
        this.intArray = data;
    }

    @Override
    void write(DataOutput output) throws IOException {
        output.writeInt(this.intArray.length);

        for (int anIntArray : this.intArray) {
            output.writeInt(anIntArray);
        }
    }

    @Override
    void load(DataInput input) throws IOException {
        int length = input.readInt();
        this.intArray = new int[length];

        for (int i = 0; i < length; i++) {
            this.intArray[i] = input.readInt();
        }
    }

    @Override
    public byte getId() {
        return (byte) 11;
    }

    @Override
    public String toString() {
        return "[" + this.intArray.length + " bytes]";
    }

    @Override
    public NBTBase copy() {
        int[] data = new int[this.intArray.length];
        System.arraycopy(this.intArray, 0, data, 0, this.intArray.length);
        return new NBTTagIntArray(this.getName(), data);
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagIntArray tag = (NBTTagIntArray) object;
            return this.intArray == null && tag.intArray == null || this.intArray != null && Arrays.equals(this.intArray, tag.intArray);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.intArray);
    }
}
