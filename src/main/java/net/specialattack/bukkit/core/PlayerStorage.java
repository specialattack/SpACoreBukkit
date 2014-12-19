package net.specialattack.bukkit.core;

import com.mojang.NBT.*;
import java.io.*;
import java.util.Map;
import java.util.logging.Level;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

/**
 * Utility class for storing and restoring player states.
 *
 * @author heldplayer
 */
@SuppressWarnings("deprecation")
public class PlayerStorage {

    /**
     * Function to restore a player to a previous state.
     *
     * @param player
     *         The player to be restored.
     *
     * @throws IOException
     *         Thrown if something goes wrong, why would it do that though?
     */
    public static void apply(Player player) throws IOException {
        File backupFolder = new File(SpACore.instance.getDataFolder(), "players");

        File playerFile = new File(backupFolder, player.getName() + ".dat");

        if (!playerFile.exists()) {
            playerFile = new File(backupFolder, player.getName() + ".dat_old");

            if (!playerFile.exists()) {
                SpACore.log(Level.WARNING, "Player '" + player.getName() + "' has no backup file and no old backup file. This is a bug");
                return;
            }
        }

        FileInputStream FIS = new FileInputStream(playerFile);

        NBTTagCompound compound = CompressedStreamTools.readCompressed(FIS);

        Util.clearEverything(player);

        player.setHealth(compound.getShort("health"));
        player.setFoodLevel(compound.getInteger("foodlevel"));
        player.setGameMode(GameMode.getByValue(compound.getInteger("playerGameType")));
        player.setLevel(compound.getInteger("XpLevel"));
        player.setTotalExperience(compound.getInteger("XpTotal"));
        player.setExhaustion(compound.getFloat("foodExhaustionLevel"));
        player.setSaturation(compound.getFloat("foodSaturationLevel"));
        player.setExp(compound.getFloat("XpP"));

        PlayerInventory inv = player.getInventory();
        ItemStack[] contents = new ItemStack[inv.getSize()];

        NBTTagList inventory = compound.getTagList("Inventory");
        for (int i = 0; i < inventory.tagCount(); i++) {
            NBTTagCompound stackComp = (NBTTagCompound) inventory.tagAt(i);

            ItemStack stack = new ItemStack(stackComp.getShort("id"), stackComp.getByte("Count"), stackComp.getShort("Damage"));

            NBTTagCompound tag = stackComp.getCompoundTag("tag");

            NBTTagList ench = tag.getTagList("ench");

            for (int num = 0; num < ench.tagCount(); num++) {
                NBTTagCompound enchComp = (NBTTagCompound) ench.tagAt(num);

                stack.addEnchantment(Enchantment.getById(enchComp.getShort("id")), enchComp.getShort("lvl"));
            }

            contents[stackComp.getByte("Slot")] = stack;
        }
        inv.setContents(contents);

        NBTTagList motion = compound.getTagList("Motion");
        player.setVelocity(new Vector(((NBTTagDouble) motion.tagAt(0)).data, ((NBTTagDouble) motion.tagAt(1)).data, ((NBTTagDouble) motion.tagAt(2)).data));

        NBTTagList pos = compound.getTagList("Pos");
        Location loc = new Location(SpACore.instance.getServer().getWorld(compound.getString("world")), ((NBTTagDouble) pos.tagAt(0)).data, ((NBTTagDouble) pos.tagAt(1)).data, ((NBTTagDouble) pos.tagAt(2)).data);
        NBTTagList rotation = compound.getTagList("Rotation");
        loc.setYaw(((NBTTagFloat) rotation.tagAt(0)).data);
        loc.setPitch(((NBTTagFloat) rotation.tagAt(1)).data);
        player.teleport(loc);

        File backupFile = new File(backupFolder, player.getName() + ".dat_old");

        if (backupFile.exists() && !backupFile.equals(playerFile)) {
            backupFile.delete();
        }

        playerFile.renameTo(backupFile);

        FIS.close();
    }

    /**
     * Function to save a player state. Can only store up to 2 states (latest
     * one is used when calling {@link net.specialattack.bukkit.core.PlayerStorage#apply(org.bukkit.entity.Player)})
     *
     * @param player
     *         The player to save.
     *
     * @throws IOException
     *         Thrown if something goes wrong, why would it do that though?
     * @throws FileNotFoundException
     *         Shouldn't ever be thrown.
     */
    public static void store(Player player) throws IOException, FileNotFoundException {
        File backupFolder = new File(SpACore.instance.getDataFolder(), "players");

        File playerFile = new File(backupFolder, player.getName() + ".dat");

        if (playerFile.exists()) {
            File backupFile = new File(backupFolder, player.getName() + ".dat_old");

            if (backupFile.exists()) {
                backupFile.delete();
            }

            playerFile.renameTo(backupFile);

            playerFile = new File(backupFolder, player.getName() + ".dat");

            playerFile.createNewFile();
        } else {
            backupFolder.mkdirs();

            playerFile.createNewFile();
        }

        FileOutputStream FOS = new FileOutputStream(playerFile);

        Location loc = player.getLocation();
        Vector velocity = player.getVelocity();
        PlayerInventory inv = player.getInventory();

        NBTTagCompound compound = new NBTTagCompound();

        compound.setShort("health", (short) player.getHealth());
        compound.setInteger("foodlevel", player.getFoodLevel());
        compound.setInteger("playerGameType", player.getGameMode().getValue());
        compound.setInteger("XpLevel", player.getLevel());
        compound.setInteger("XpTotal", player.getTotalExperience());
        compound.setFloat("foodExhaustionLevel", player.getExhaustion());
        compound.setFloat("foodSaturationLevel", player.getSaturation());
        compound.setFloat("XpP", player.getExp());

        NBTTagList inventory = new NBTTagList();
        for (int slot = 0; slot < inv.getSize(); slot++) {
            ItemStack stack = inv.getItem(slot);

            if ((stack != null) && (stack.getTypeId() != 0) && (stack.getAmount() != 0)) {
                NBTTagCompound stackComp = new NBTTagCompound();

                stackComp.setByte("Count", (byte) stack.getAmount());
                stackComp.setByte("Slot", (byte) slot);
                stackComp.setShort("Damage", stack.getDurability());
                stackComp.setShort("id", (short) stack.getTypeId());

                Map<Enchantment, Integer> enchants = stack.getEnchantments();

                if (enchants.size() > 0) {
                    NBTTagCompound tag = new NBTTagCompound();
                    NBTTagList ench = new NBTTagList();

                    for (Enchantment enchantment : enchants.keySet()) {
                        NBTTagCompound enchComp = new NBTTagCompound();

                        enchComp.setShort("id", (short) enchantment.getId());
                        enchComp.setShort("lvl", ((Integer) enchants.get(enchantment)).shortValue());

                        ench.appendTag(enchComp);
                    }
                    tag.setTag("ench", ench);
                    stackComp.setCompoundTag("tag", tag);
                }

                inventory.appendTag(stackComp);
            }
        }
        compound.setTag("Inventory", inventory);

        NBTTagList motion = new NBTTagList();
        motion.appendTag(new NBTTagDouble("x", velocity.getX()));
        motion.appendTag(new NBTTagDouble("y", velocity.getY()));
        motion.appendTag(new NBTTagDouble("z", velocity.getZ()));
        compound.setTag("Motion", motion);

        NBTTagList pos = new NBTTagList();
        pos.appendTag(new NBTTagDouble("x", loc.getX()));
        pos.appendTag(new NBTTagDouble("y", loc.getY()));
        pos.appendTag(new NBTTagDouble("z", loc.getZ()));
        compound.setTag("Pos", pos);
        compound.setString("world", loc.getWorld().getName());

        NBTTagList rotation = new NBTTagList();
        rotation.appendTag(new NBTTagFloat("yaw", loc.getYaw()));
        rotation.appendTag(new NBTTagFloat("pitch", loc.getPitch()));
        compound.setTag("Rotation", rotation);

        CompressedStreamTools.writeCompressed(compound, FOS);

        Util.clearEverything(player);

        FOS.close();
    }

}
