package net.specialattack.bukkit.core;

import com.mojang.NBT.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import net.specialattack.bukkit.core.util.Util;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

/**
 * Utility class for storing and restoring player states.
 */
@SuppressWarnings("deprecation")
public class PlayerStorage {

    public static final String DEFAULT_STASH = "default";

    /**
     * Function to restore a player to a previous state.
     *
     * @param player
     *         The player to be restored.
     * @param stash
     *         The stash to store player data in. Different stashes allow for
     *         many different player states to be saved at once and accessed
     *         seperately.
     *
     * @throws IOException
     *         Thrown if something goes wrong, why would it do that though?
     */
    @SuppressWarnings("deprecation")
    public static void apply(Player player, String stash) throws IOException {
        File backupFolder = new File(SpACore.instance.getDataFolder(), "players/" + stash);

        File playerFile = new File(backupFolder, player.getUniqueId() + ".dat");

        if (!playerFile.exists()) {
            playerFile = new File(backupFolder, player.getUniqueId() + ".dat_old");

            if (!playerFile.exists()) {
                SpACore.log(Level.WARNING, "Player '" + player.getName() + "' has no backup file and no old backup file. This is a bug");
                return;
            }
        }

        FileInputStream FIS = new FileInputStream(playerFile);

        NBTTagCompound compound = CompressedStreamTools.readCompressed(FIS);

        Util.clearEverything(player);

        if (compound.hasKey("health")) {
            player.setHealth(compound.getShort("health"));
        } else {
            player.setHealth(compound.getDouble("healthDouble"));
        }
        player.setFoodLevel(compound.getInteger("foodlevel"));

        if (compound.hasKey("playerGameType")) {
            player.setGameMode(GameMode.getByValue(compound.getInteger("playerGameType")));
        } else {
            player.setGameMode(GameMode.valueOf(compound.getString("playerGameTypeString")));
        }
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

            ItemStack stack;

            if (stackComp.hasKey("id")) {
                stack = new ItemStack(stackComp.getShort("id"), stackComp.getByte("Count"), stackComp.getShort("Damage"));
            } else {
                stack = new ItemStack(Material.valueOf(stackComp.getString("type")), stackComp.getByte("Count"), stackComp.getShort("Damage"));
            }

            NBTTagCompound tag = stackComp.getCompoundTag("tag");

            NBTTagCompound display = tag.getCompoundTag("display");

            if (display != null) {

                ItemMeta meta = stack.getItemMeta();

                if (display.hasKey("Name")) {
                    meta.setDisplayName(display.getString("Name"));
                }

                if (display.hasKey("Lore")) {
                    NBTTagList lore = display.getTagList("Lore");
                    List<String> loreList = new ArrayList<String>();
                    for (int j = 0; j < lore.tagCount(); j++) {
                        loreList.add(lore.tagAt(j).toString());
                    }
                    meta.setLore(loreList);
                }

                stack.setItemMeta(meta);

            }

            NBTTagList ench = tag.getTagList("ench");

            for (int num = 0; num < ench.tagCount(); num++) {
                NBTTagCompound enchComp = (NBTTagCompound) ench.tagAt(num);

                if (enchComp.hasKey("id")) {
                    stack.addEnchantment(Enchantment.getById(enchComp.getShort("id")), enchComp.getShort("lvl"));
                } else {
                    stack.addEnchantment(Enchantment.getByName(enchComp.getString("name")), enchComp.getShort("lvl"));
                }

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
     * one is used when calling
     * {@link net.specialattack.bukkit.core.PlayerStorage#apply(org.bukkit.entity.Player, String)}
     * )
     *
     * @param player
     *         The player to save.
     *
     * @throws IOException
     *         Thrown if something goes wrong, why would it do that though?
     * @throws FileNotFoundException
     *         Shouldn't ever be thrown.
     */
    public static void store(Player player, String stash) throws IOException {
        File backupFolder = new File(SpACore.instance.getDataFolder(), "players/" + stash);

        File playerFile = new File(backupFolder, player.getUniqueId() + ".dat");

        if (playerFile.exists()) {
            File backupFile = new File(backupFolder, player.getUniqueId() + ".dat_old");

            if (backupFile.exists()) {
                backupFile.delete();
            }

            playerFile.renameTo(backupFile);

            playerFile = new File(backupFolder, player.getUniqueId() + ".dat");

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

        compound.setDouble("healthDouble", player.getHealth());
        compound.setInteger("foodlevel", player.getFoodLevel());
        compound.setString("playerGameTypeString", player.getGameMode().toString());
        compound.setInteger("XpLevel", player.getLevel());
        compound.setInteger("XpTotal", player.getTotalExperience());
        compound.setFloat("foodExhaustionLevel", player.getExhaustion());
        compound.setFloat("foodSaturationLevel", player.getSaturation());
        compound.setFloat("XpP", player.getExp());

        NBTTagList inventory = new NBTTagList();
        for (int slot = 0; slot < inv.getSize(); slot++) {
            ItemStack stack = inv.getItem(slot);

            if ((stack != null) && (stack.getType() != Material.AIR) && (stack.getAmount() != 0)) {
                NBTTagCompound stackComp = new NBTTagCompound();

                stackComp.setByte("Count", (byte) stack.getAmount());
                stackComp.setByte("Slot", (byte) slot);
                stackComp.setShort("Damage", stack.getDurability());
                stackComp.setString("type", stack.getType().toString());

                NBTTagCompound tag = new NBTTagCompound();

                ItemMeta metaData = stack.getItemMeta();

                if (metaData != null) {
                    String displayName = metaData.getDisplayName();
                    List<String> lore = metaData.getLore();

                    NBTTagCompound displayComp = new NBTTagCompound();
                    boolean hasDisplay = false;

                    if (displayName != null) {
                        hasDisplay = true;
                        displayComp.setString("Name", displayName);
                    }

                    if (lore != null && lore.size() > 0) {
                        NBTTagList loreList = new NBTTagList();

                        for (String line : lore) {
                            loreList.appendTag(new NBTTagString("", line));
                        }


                        displayComp.setTag("Lore", loreList);
                        hasDisplay = true;
                    }

                    if (hasDisplay) {
                        tag.setCompoundTag("display", displayComp);
                    }

                    Map<Enchantment, Integer> enchants = metaData.getEnchants();

                    if (enchants.size() > 0) {
                        NBTTagList ench = new NBTTagList();

                        for (Enchantment enchantment : enchants.keySet()) {
                            NBTTagCompound enchComp = new NBTTagCompound();

                            enchComp.setString("name", enchantment.getName());
                            enchComp.setShort("lvl", enchants.get(enchantment).shortValue());

                            ench.appendTag(enchComp);
                        }
                        tag.setTag("ench", ench);
                    }
                }

                stackComp.setCompoundTag("tag", tag);
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

        NBTTagCompound custom = new NBTTagCompound();
        custom.setString("lastKnownName", player.getName());
        custom.setLong("saveTime", System.currentTimeMillis());
        compound.setTag("SpACore", custom);

        CompressedStreamTools.writeCompressed(compound, FOS);

        Util.clearEverything(player);

        FOS.close();
    }

}
