package com.mojang.NBT;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NBTTagList extends NBTBase {

    private List<NBTBase> tagList = new ArrayList<NBTBase>();

    private byte tagType;

    public NBTTagList() {
        super("");
    }

    public NBTTagList(String name) {
        super(name);
    }

    @Override
    void write(DataOutput output) throws IOException {
        if (!this.tagList.isEmpty()) {
            this.tagType = this.tagList.get(0).getId();
        } else {
            this.tagType = 1;
        }

        output.writeByte(this.tagType);
        output.writeInt(this.tagList.size());

        for (NBTBase aTagList : this.tagList) {
            aTagList.write(output);
        }
    }

    @Override
    void load(DataInput input) throws IOException {
        this.tagType = input.readByte();
        int length = input.readInt();
        this.tagList = new ArrayList<NBTBase>();

        for (int i = 0; i < length; i++) {
            NBTBase tag = NBTBase.newTag(this.tagType, null);
            tag.load(input);
            this.tagList.add(tag);
        }
    }

    @Override
    public byte getId() {
        return (byte) 9;
    }

    @Override
    public String toString() {
        return "" + this.tagList.size() + " entries of type " + NBTBase.getTagName(this.tagType);
    }

    public void appendTag(NBTBase tag) {
        this.tagType = tag.getId();
        this.tagList.add(tag);
    }

    public NBTBase removeTag(int index) {
        return this.tagList.remove(index);
    }

    public NBTBase tagAt(int index) {
        return this.tagList.get(index);
    }

    public int tagCount() {
        return this.tagList.size();
    }

    @Override
    public NBTBase copy() {
        NBTTagList tag = new NBTTagList(this.getName());
        tag.tagType = this.tagType;

        for (NBTBase original : this.tagList) {
            NBTBase copy = original.copy();
            tag.tagList.add(copy);
        }

        return tag;
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            NBTTagList tag = (NBTTagList) object;

            if (this.tagType == tag.tagType) {
                return this.tagList.equals(tag.tagList);
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ this.tagList.hashCode();
    }
}
