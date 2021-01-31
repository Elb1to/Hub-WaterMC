package me.elb1to.watermc.hub.utils.extra;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {

	private ItemStack itemStack;

	public ItemBuilder(Material material) {
		this.itemStack = new ItemStack(material, 1);
	}

	public ItemBuilder(ItemStack itemStack) {
		this.itemStack = itemStack.clone();
	}

	public ItemBuilder(Material material, int damage) {
		this.itemStack = new ItemStack(material, 1, (short) damage);
	}

	public ItemBuilder(Material material, int amount, int damage) {
		this.itemStack = new ItemStack(material, amount, (short) damage);
	}

	public static void registerGlow() {
		try {
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Glow glow = new Glow();
			Enchantment.registerEnchantment(glow);
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ItemBuilder setName(String name) {
		if (name != null) {
			name = ChatColor.translateAlternateColorCodes('&', name);
			ItemMeta meta = this.itemStack.getItemMeta();
			meta.setDisplayName(name);
			this.itemStack.setItemMeta(meta);
		}

		return this;
	}

	public ItemBuilder setLore(List<String> lore) {
		if (lore != null) {
			List<String> list = new ArrayList<>();
			lore.forEach(line -> list.add(ChatColor.translateAlternateColorCodes('&', line)));
			ItemMeta meta = this.itemStack.getItemMeta();
			meta.setLore(list);
			this.itemStack.setItemMeta(meta);
		}

		return this;
	}

	public ItemBuilder setAmount(int amount) {
		this.itemStack.setAmount(amount);
		return this;
	}

	public ItemBuilder addEnchants(List<String> enchants) {
		if (enchants != null)
			enchants.forEach(enchant -> {
				String[] arr = enchant.replace(" ", "").split(",");
				Enchantment enchantment = Enchantment.getByName(arr[0]);
				int level = Integer.parseInt(arr[1]);
				this.itemStack.addUnsafeEnchantment(enchantment, level);
			});

		return this;
	}

	public ItemBuilder setDurability(short dur) {
		this.itemStack.setDurability(dur);

		return this;
	}

	public ItemBuilder setDurability(int dur) {
		this.itemStack.setDurability((short) dur);
		return this;
	}

	public ItemBuilder setHeadUrl(String url) {
		SkullMeta headMeta = (SkullMeta) itemStack.getItemMeta();
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
		profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
		Field profileField;

		try {
			profileField = headMeta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(headMeta, profile);
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
			e1.printStackTrace();
		}

		itemStack.setItemMeta(headMeta);

		return this;
	}

	public ItemBuilder setOwner(String owner) {
		if (this.itemStack.getType() == Material.SKULL_ITEM) {
			SkullMeta meta = (SkullMeta) this.itemStack.getItemMeta();
			meta.setOwner(owner);
			this.itemStack.setItemMeta(meta);
			return this;
		}

		throw new IllegalArgumentException("setOwner() only applicable for Skull Item");
	}

	public ItemBuilder setArmorColor(Color color) {
		try {
			LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) this.itemStack.getItemMeta();
			leatherArmorMeta.setColor(color);
			this.itemStack.setItemMeta(leatherArmorMeta);
		} catch (Exception exception) {
			Bukkit.getConsoleSender().sendMessage("Error set armor color.");
		}

		return this;
	}

	public ItemBuilder glow() {
		ItemMeta meta = this.itemStack.getItemMeta();

		meta.addEnchant(new Glow(), 1, true);
		this.itemStack.setItemMeta(meta);

		return this;
	}

	public ItemStack get() {
		return this.itemStack;
	}

	private static class Glow extends Enchantment {

		public Glow() {
			super(25);
		}

		@Override
		public boolean canEnchantItem(ItemStack arg0) {
			return false;
		}

		@Override
		public boolean conflictsWith(Enchantment arg0) {
			return false;
		}

		@Override
		public EnchantmentTarget getItemTarget() {
			return null;
		}

		@Override
		public int getMaxLevel() {
			return 2;
		}

		@Override
		public String getName() {
			return null;
		}

		@Override
		public int getStartLevel() {
			return 1;
		}
	}
}