
package com.mojang.NBT;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagInt extends NBTBase {
    public int data;

    public NBTTagInt(String name) {
        super(name);
    }

    public NBTTagInt(String name, int data) {
        super(name);
        this.data = data;
    }

    void write(DataOutput output) throws IOException {
        output.writeInt(this.data);
    }

    void load(DataInput input) throws IOException {
        this.data = input.readInt();
    }

    public byte getId() {
        return (byte) 3;
    }

    public String toString() {
        return "" + this.data;
    }

    public NBTBase copy() {
        return new NBTTagInt(this.getName(), this.data);
    }

    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagInt tag = (NBTTagInt) object;
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
