
package com.mojang.NBT;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import net.specialattack.core.SpACore;

public class CompressedStreamTools {

    public static NBTTagCompound readCompressed(InputStream baseInput) throws IOException {
        DataInputStream input = new DataInputStream(new BufferedInputStream(new GZIPInputStream(baseInput)));
        NBTTagCompound compound;

        try {
            compound = read(input);
        }
        finally {
            input.close();
        }

        return compound;
    }

    public static void writeCompressed(NBTTagCompound compound, OutputStream baseOutput) throws IOException {
        DataOutputStream output = new DataOutputStream(new GZIPOutputStream(baseOutput));

        try {
            write(compound, output);
        }
        finally {
            output.close();
        }
    }

    public static NBTTagCompound decompress(byte[] data) throws IOException {
        DataInputStream input = new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(data))));
        NBTTagCompound var2;

        try {
            var2 = read(input);
        }
        finally {
            input.close();
        }

        return var2;
    }

    public static byte[] compress(NBTTagCompound compound) throws IOException {
        ByteArrayOutputStream baseOutput = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(new GZIPOutputStream(baseOutput));

        try {
            write(compound, output);
        }
        finally {
            output.close();
        }

        return baseOutput.toByteArray();
    }

    public static NBTTagCompound read(DataInput input) {
        try {
            NBTBase Base = NBTBase.readNamedTag(input);
            if ((Base instanceof NBTTagCompound)) {
                return (NBTTagCompound) Base;
            }
            throw new IOException("Root tag must be a named compound tag");
        }
        catch (Exception ex) {
            SpACore.log(Level.WARNING, "Failed reading NBT save file", ex);
            return null;
        }
    }

    public static void write(NBTTagCompound compound, DataOutput output) throws IOException {
        NBTBase.writeNamedTag(compound, output);
    }

    public static void write(NBTTagCompound compound, File file) throws IOException {
        DataOutputStream output = new DataOutputStream(new FileOutputStream(file));

        try {
            write(compound, output);
        }
        finally {
            output.close();
        }
    }

    public static NBTTagCompound read(File file) throws IOException {
        if (!file.exists()) {
            return null;
        }
        else {
            DataInputStream input = new DataInputStream(new FileInputStream(file));
            NBTTagCompound compound;

            try {
                compound = read(input);
            }
            finally {
                input.close();
            }

            return compound;
        }
    }
}
