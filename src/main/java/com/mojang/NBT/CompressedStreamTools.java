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
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class CompressedStreamTools {

    public static NBTTagCompound readCompressed(InputStream baseInput) throws IOException {
        NBTTagCompound compound;

        try (DataInputStream input = new DataInputStream(new BufferedInputStream(new GZIPInputStream(baseInput)))) {
            compound = read(input);
        }

        return compound;
    }

    public static void writeCompressed(NBTTagCompound compound, OutputStream baseOutput) throws IOException {

        try (DataOutputStream output = new DataOutputStream(new GZIPOutputStream(baseOutput))) {
            write(compound, output);
        }
    }

    public static NBTTagCompound decompress(byte[] data) throws IOException {
        NBTTagCompound var2;

        try (DataInputStream input = new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(data))))) {
            var2 = read(input);
        }

        return var2;
    }

    public static byte[] compress(NBTTagCompound compound) throws IOException {
        ByteArrayOutputStream baseOutput = new ByteArrayOutputStream();

        try (DataOutputStream output = new DataOutputStream(new GZIPOutputStream(baseOutput))) {
            write(compound, output);
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
        } catch (Exception ex) {
            return null;
        }
    }

    public static void write(NBTTagCompound compound, DataOutput output) throws IOException {
        NBTBase.writeNamedTag(compound, output);
    }

    public static void write(NBTTagCompound compound, File file) throws IOException {

        try (DataOutputStream output = new DataOutputStream(new FileOutputStream(file))) {
            write(compound, output);
        }
    }

    public static NBTTagCompound read(File file) throws IOException {
        if (!file.exists()) {
            return null;
        } else {
            NBTTagCompound compound;

            try (DataInputStream input = new DataInputStream(new FileInputStream(file))) {
                compound = read(input);
            }

            return compound;
        }
    }
}
