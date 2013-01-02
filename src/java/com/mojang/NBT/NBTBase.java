
package com.mojang.NBT;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class NBTBase {
    private String name;

    abstract void write(DataOutput output) throws IOException;

    abstract void load(DataInput input) throws IOException;

    public abstract byte getId();

    protected NBTBase(String name) {
        if (name == null) {
            this.name = "";
        }
        else {
            this.name = name;
        }
    }

    public NBTBase setName(String name) {
        if (name == null) {
            this.name = "";
        }
        else {
            this.name = name;
        }

        return this;
    }

    public String getName() {
        return this.name == null ? "" : this.name;
    }

    public static NBTBase readNamedTag(DataInput input) throws IOException {
        byte id = input.readByte();

        if (id == 0) {
            return new NBTTagEnd();
        }
        else {
            String name = input.readUTF();
            NBTBase tag = newTag(id, name);

            try {
                tag.load(input);
                return tag;
            }
            catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }

    public static void writeNamedTag(NBTBase tag, DataOutput output) throws IOException {
        output.writeByte(tag.getId());

        if (tag.getId() != 0) {
            output.writeUTF(tag.getName());
            tag.write(output);
        }
    }

    public static NBTBase newTag(byte id, String name) {
        switch (id) {
        case 0:
            return new NBTTagEnd();
        case 1:
            return new NBTTagByte(name);
        case 2:
            return new NBTTagShort(name);
        case 3:
            return new NBTTagInt(name);
        case 4:
            return new NBTTagLong(name);
        case 5:
            return new NBTTagFloat(name);
        case 6:
            return new NBTTagDouble(name);
        case 7:
            return new NBTTagByteArray(name);
        case 8:
            return new NBTTagString(name);
        case 9:
            return new NBTTagList(name);
        case 10:
            return new NBTTagCompound(name);
        case 11:
            return new NBTTagIntArray(name);
        default:
            return null;
        }
    }

    public static String getTagName(byte id) {
        switch (id) {
        case 0:
            return "TAG_End";
        case 1:
            return "TAG_Byte";
        case 2:
            return "TAG_Short";
        case 3:
            return "TAG_Int";
        case 4:
            return "TAG_Long";
        case 5:
            return "TAG_Float";
        case 6:
            return "TAG_Double";
        case 7:
            return "TAG_Byte_Array";
        case 8:
            return "TAG_String";
        case 9:
            return "TAG_List";
        case 10:
            return "TAG_Compound";
        case 11:
            return "TAG_Int_Array";
        default:
            return "UNKNOWN";
        }
    }

    public abstract NBTBase copy();

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof NBTBase)) {
            return false;
        }
        else {
            NBTBase tag = (NBTBase) object;
            return this.getId() != tag.getId() ? false : ((this.name != null || tag.name == null) && (this.name == null || tag.name != null) ? this.name == null || this.name.equals(tag.name) : false);
        }
    }

    @Override
    public int hashCode() {
        return this.name.hashCode() ^ this.getId();
    }
}
