
package com.mojang.NBT;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagString extends NBTBase {
    public String data;

    public NBTTagString(String name) {
        super(name);
    }

    public NBTTagString(String name, String data) {
        super(name);
        this.data = data;

        if (data == null) {
            throw new IllegalArgumentException("Empty string not allowed");
        }
    }

    void write(DataOutput output) throws IOException {
        output.writeUTF(this.data);
    }

    void load(DataInput input) throws IOException {
        this.data = input.readUTF();
    }

    public byte getId() {
        return (byte) 8;
    }

    public String toString() {
        return "" + this.data;
    }

    public NBTBase copy() {
        return new NBTTagString(this.getName(), this.data);
    }

    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagString tag = (NBTTagString) object;
            return this.data == null && tag.data == null || this.data != null && this.data.equals(tag.data);
        }
        else {
            return false;
        }
    }

    public int hashCode() {
        return super.hashCode() ^ this.data.hashCode();
    }
}
