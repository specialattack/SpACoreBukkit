
package com.mojang.NBT;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagEnd extends NBTBase {
    public NBTTagEnd() {
        super((String) null);
    }

    void load(DataInput input) throws IOException {}

    void write(DataOutput output) throws IOException {}

    public byte getId() {
        return (byte) 0;
    }

    public String toString() {
        return "END";
    }

    public NBTBase copy() {
        return new NBTTagEnd();
    }
}
