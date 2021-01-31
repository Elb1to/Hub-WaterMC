package me.elb1to.watermc.hub.utils.extra;

import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.BlockPosition;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Created by Elb1to
 * Project: FrozedHubDeluxe
 * Date: 1/16/2021 @ 1:05 PM
 */
@Deprecated
public class BlockUtils {

	// List of block that are usable
	public static HashSet<Byte> blockUseSet = new HashSet<>();

	// List of blocks that are always solid and can be stood on
	public static HashSet<Byte> fullSolid = new HashSet<>();

	// List of blocks that are non-solid, but can't be moved through. Eg lily, fence gate, portal
	public static HashSet<Byte> blockPassSet = new HashSet<>();

	// List of blocks that offer zero resistance (long grass, torch, flower)
	public static HashSet<Byte> blockAirFoliageSet = new HashSet<>();

	// All horizontal diections [north, east, south, west]
	public static List<BlockFace> horizontals = Arrays.asList(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);

	static {
		blockAirFoliageSet.add((byte) Material.AIR.getId());
		blockAirFoliageSet.add((byte) Material.SAPLING.getId());
		blockAirFoliageSet.add((byte) Material.LONG_GRASS.getId());
		blockAirFoliageSet.add((byte) Material.DOUBLE_PLANT.getId());
		blockAirFoliageSet.add((byte) Material.DEAD_BUSH.getId());
		blockAirFoliageSet.add((byte) Material.YELLOW_FLOWER.getId());
		blockAirFoliageSet.add((byte) Material.RED_ROSE.getId());
		blockAirFoliageSet.add((byte) Material.BROWN_MUSHROOM.getId());
		blockAirFoliageSet.add((byte) Material.RED_MUSHROOM.getId());
		blockAirFoliageSet.add((byte) Material.FIRE.getId());
		blockAirFoliageSet.add((byte) Material.CROPS.getId());
		blockAirFoliageSet.add((byte) Material.PUMPKIN_STEM.getId());
		blockAirFoliageSet.add((byte) Material.MELON_STEM.getId());
		blockAirFoliageSet.add((byte) Material.NETHER_WARTS.getId());
		blockAirFoliageSet.add((byte) Material.TRIPWIRE_HOOK.getId());
		blockAirFoliageSet.add((byte) Material.TRIPWIRE.getId());
		blockAirFoliageSet.add((byte) Material.CARROT.getId());
		blockAirFoliageSet.add((byte) Material.POTATO.getId());
		blockAirFoliageSet.add((byte) Material.DOUBLE_PLANT.getId());
		blockAirFoliageSet.add((byte) Material.STANDING_BANNER.getId());
		blockAirFoliageSet.add((byte) Material.WALL_BANNER.getId());

		blockPassSet.add((byte) Material.AIR.getId());
		blockPassSet.add((byte) Material.SAPLING.getId());
		blockPassSet.add((byte) Material.WATER.getId());
		blockPassSet.add((byte) Material.STATIONARY_WATER.getId());
		blockPassSet.add((byte) Material.LAVA.getId());
		blockPassSet.add((byte) Material.STATIONARY_LAVA.getId());
		blockPassSet.add((byte) Material.BED_BLOCK.getId());
		blockPassSet.add((byte) Material.POWERED_RAIL.getId());
		blockPassSet.add((byte) Material.DETECTOR_RAIL.getId());
		blockPassSet.add((byte) Material.WEB.getId());
		blockPassSet.add((byte) Material.LONG_GRASS.getId());
		blockPassSet.add((byte) Material.DEAD_BUSH.getId());
		blockPassSet.add((byte) Material.YELLOW_FLOWER.getId());
		blockPassSet.add((byte) Material.RED_ROSE.getId());
		blockPassSet.add((byte) Material.BROWN_MUSHROOM.getId());
		blockPassSet.add((byte) Material.RED_MUSHROOM.getId());
		blockPassSet.add((byte) Material.TORCH.getId());
		blockPassSet.add((byte) Material.FIRE.getId());
		blockPassSet.add((byte) Material.REDSTONE_WIRE.getId());
		blockPassSet.add((byte) Material.CROPS.getId());
		blockPassSet.add((byte) Material.SIGN_POST.getId());
		blockPassSet.add((byte) Material.WOODEN_DOOR.getId());
		blockPassSet.add((byte) Material.LADDER.getId());
		blockPassSet.add((byte) Material.RAILS.getId());
		blockPassSet.add((byte) Material.WALL_SIGN.getId());
		blockPassSet.add((byte) Material.LEVER.getId());
		blockPassSet.add((byte) Material.STONE_PLATE.getId());
		blockPassSet.add((byte) Material.IRON_DOOR_BLOCK.getId());
		blockPassSet.add((byte) Material.WOOD_PLATE.getId());
		blockPassSet.add((byte) Material.REDSTONE_TORCH_OFF.getId());
		blockPassSet.add((byte) Material.REDSTONE_TORCH_ON.getId());
		blockPassSet.add((byte) Material.STONE_BUTTON.getId());
		blockPassSet.add((byte) Material.SNOW.getId());
		blockPassSet.add((byte) Material.SUGAR_CANE_BLOCK.getId());
		blockPassSet.add((byte) Material.FENCE.getId());
		blockPassSet.add((byte) Material.PORTAL.getId());
		blockPassSet.add((byte) Material.CAKE_BLOCK.getId());
		blockPassSet.add((byte) Material.DIODE_BLOCK_OFF.getId());
		blockPassSet.add((byte) Material.DIODE_BLOCK_ON.getId());
		blockPassSet.add((byte) Material.TRAP_DOOR.getId());
		blockPassSet.add((byte) Material.IRON_FENCE.getId());
		blockPassSet.add((byte) Material.THIN_GLASS.getId());
		blockPassSet.add((byte) Material.PUMPKIN_STEM.getId());
		blockPassSet.add((byte) Material.MELON_STEM.getId());
		blockPassSet.add((byte) Material.VINE.getId());
		blockPassSet.add((byte) Material.FENCE_GATE.getId());
		blockPassSet.add((byte) Material.WATER_LILY.getId());
		blockPassSet.add((byte) Material.NETHER_WARTS.getId());
		blockPassSet.add((byte) Material.ENCHANTMENT_TABLE.getId());
		blockPassSet.add((byte) Material.BREWING_STAND.getId());
		blockPassSet.add((byte) Material.CAULDRON.getId());
		blockPassSet.add((byte) Material.ENDER_PORTAL.getId());
		blockPassSet.add((byte) Material.ENDER_PORTAL_FRAME.getId());
		blockPassSet.add((byte) Material.DAYLIGHT_DETECTOR.getId());
		blockPassSet.add((byte) Material.STAINED_GLASS_PANE.getId());
		blockPassSet.add((byte) Material.IRON_TRAPDOOR.getId());
		blockPassSet.add((byte) Material.DAYLIGHT_DETECTOR_INVERTED.getId());
		blockPassSet.add((byte) Material.BARRIER.getId());

		blockPassSet.add((byte) Material.BIRCH_FENCE_GATE.getId());
		blockPassSet.add((byte) Material.JUNGLE_FENCE_GATE.getId());
		blockPassSet.add((byte) Material.DARK_OAK_FENCE_GATE.getId());
		blockPassSet.add((byte) Material.ACACIA_FENCE_GATE.getId());
		blockPassSet.add((byte) Material.SPRUCE_FENCE.getId());
		blockPassSet.add((byte) Material.BIRCH_FENCE.getId());
		blockPassSet.add((byte) Material.JUNGLE_FENCE.getId());
		blockPassSet.add((byte) Material.DARK_OAK_FENCE.getId());
		blockPassSet.add((byte) Material.ACACIA_FENCE.getId());

		blockPassSet.add((byte) Material.SPRUCE_DOOR.getId());
		blockPassSet.add((byte) Material.BIRCH_DOOR.getId());
		blockPassSet.add((byte) Material.JUNGLE_DOOR.getId());
		blockPassSet.add((byte) Material.ACACIA_DOOR.getId());
		blockPassSet.add((byte) Material.DARK_OAK_DOOR.getId());

		fullSolid.add((byte) Material.STONE.getId());
		fullSolid.add((byte) Material.GRASS.getId());
		fullSolid.add((byte) Material.DIRT.getId());
		fullSolid.add((byte) Material.COBBLESTONE.getId());
		fullSolid.add((byte) Material.WOOD.getId());
		fullSolid.add((byte) Material.BEDROCK.getId());
		fullSolid.add((byte) Material.SAND.getId());
		fullSolid.add((byte) Material.GRAVEL.getId());
		fullSolid.add((byte) Material.GOLD_ORE.getId());
		fullSolid.add((byte) Material.IRON_ORE.getId());
		fullSolid.add((byte) Material.COAL_ORE.getId());
		fullSolid.add((byte) Material.LOG.getId());
		fullSolid.add((byte) Material.LEAVES.getId());
		fullSolid.add((byte) Material.SPONGE.getId());
		fullSolid.add((byte) Material.GLASS.getId());
		fullSolid.add((byte) Material.LAPIS_ORE.getId());
		fullSolid.add((byte) Material.LAPIS_BLOCK.getId());
		fullSolid.add((byte) Material.DISPENSER.getId());
		fullSolid.add((byte) Material.SANDSTONE.getId());
		fullSolid.add((byte) Material.NOTE_BLOCK.getId());
		fullSolid.add((byte) Material.PISTON_STICKY_BASE.getId());
		fullSolid.add((byte) Material.PISTON_BASE.getId());
		fullSolid.add((byte) Material.WOOL.getId());
		fullSolid.add((byte) Material.GOLD_BLOCK.getId());
		fullSolid.add((byte) Material.IRON_BLOCK.getId());
		fullSolid.add((byte) Material.DOUBLE_STEP.getId());
		fullSolid.add((byte) Material.STEP.getId());
		fullSolid.add((byte) Material.BRICK.getId());
		fullSolid.add((byte) Material.TNT.getId());
		fullSolid.add((byte) Material.BOOKSHELF.getId());
		fullSolid.add((byte) Material.MOSSY_COBBLESTONE.getId());
		fullSolid.add((byte) Material.OBSIDIAN.getId());
		fullSolid.add((byte) Material.DIAMOND_ORE.getId());
		fullSolid.add((byte) Material.DIAMOND_BLOCK.getId());
		fullSolid.add((byte) Material.WORKBENCH.getId());
		fullSolid.add((byte) Material.SOIL.getId());
		fullSolid.add((byte) Material.FURNACE.getId());
		fullSolid.add((byte) Material.BURNING_FURNACE.getId());
		fullSolid.add((byte) Material.REDSTONE_ORE.getId());
		fullSolid.add((byte) Material.GLOWING_REDSTONE_ORE.getId());
		fullSolid.add((byte) Material.ICE.getId());
		fullSolid.add((byte) Material.SNOW_BLOCK.getId());
		fullSolid.add((byte) Material.CLAY.getId());
		fullSolid.add((byte) Material.JUKEBOX.getId());
		fullSolid.add((byte) Material.PUMPKIN.getId());
		fullSolid.add((byte) Material.NETHERRACK.getId());
		fullSolid.add((byte) Material.SOUL_SAND.getId());
		fullSolid.add((byte) Material.GLOWSTONE.getId());
		fullSolid.add((byte) Material.JACK_O_LANTERN.getId());
		fullSolid.add((byte) Material.STAINED_GLASS.getId());
		fullSolid.add((byte) Material.MONSTER_EGGS.getId());
		fullSolid.add((byte) Material.SMOOTH_BRICK.getId());
		fullSolid.add((byte) Material.HUGE_MUSHROOM_1.getId());
		fullSolid.add((byte) Material.HUGE_MUSHROOM_2.getId());
		fullSolid.add((byte) Material.MELON_BLOCK.getId());
		fullSolid.add((byte) Material.MYCEL.getId());
		fullSolid.add((byte) Material.NETHER_BRICK.getId());
		fullSolid.add((byte) Material.ENDER_STONE.getId());
		fullSolid.add((byte) Material.REDSTONE_LAMP_OFF.getId());
		fullSolid.add((byte) Material.REDSTONE_LAMP_ON.getId());
		fullSolid.add((byte) Material.WOOD_DOUBLE_STEP.getId());
		fullSolid.add((byte) Material.WOOD_STEP.getId());
		fullSolid.add((byte) Material.EMERALD_ORE.getId());
		fullSolid.add((byte) Material.EMERALD_BLOCK.getId());
		fullSolid.add((byte) Material.COMMAND.getId());
		fullSolid.add((byte) Material.BEACON.getId());
		fullSolid.add((byte) Material.REDSTONE_BLOCK.getId());
		fullSolid.add((byte) Material.QUARTZ_ORE.getId());
		fullSolid.add((byte) Material.QUARTZ_BLOCK.getId());
		fullSolid.add((byte) Material.DROPPER.getId());
		fullSolid.add((byte) Material.STAINED_CLAY.getId());
		fullSolid.add((byte) Material.LEAVES_2.getId());
		fullSolid.add((byte) Material.LOG_2.getId());
		fullSolid.add((byte) Material.PRISMARINE.getId());
		fullSolid.add((byte) Material.SEA_LANTERN.getId());
		fullSolid.add((byte) Material.HAY_BLOCK.getId());
		fullSolid.add((byte) Material.HARD_CLAY.getId());
		fullSolid.add((byte) Material.COAL_BLOCK.getId());
		fullSolid.add((byte) Material.PACKED_ICE.getId());
		fullSolid.add((byte) Material.RED_SANDSTONE.getId());
		fullSolid.add((byte) Material.DOUBLE_STONE_SLAB2.getId());

		blockUseSet.add((byte) Material.DISPENSER.getId());
		blockUseSet.add((byte) Material.BED_BLOCK.getId());
		blockUseSet.add((byte) Material.PISTON_BASE.getId());
		blockUseSet.add((byte) Material.BOOKSHELF.getId());
		blockUseSet.add((byte) Material.CHEST.getId());
		blockUseSet.add((byte) Material.WORKBENCH.getId());
		blockUseSet.add((byte) Material.FURNACE.getId());
		blockUseSet.add((byte) Material.BURNING_FURNACE.getId());
		blockUseSet.add((byte) Material.WOODEN_DOOR.getId());
		blockUseSet.add((byte) Material.LEVER.getId());
		blockUseSet.add((byte) Material.IRON_DOOR_BLOCK.getId());
		blockUseSet.add((byte) Material.STONE_BUTTON.getId());
		blockUseSet.add((byte) Material.FENCE.getId());
		blockUseSet.add((byte) Material.DIODE_BLOCK_OFF.getId());
		blockUseSet.add((byte) Material.DIODE_BLOCK_ON.getId());
		blockUseSet.add((byte) Material.TRAP_DOOR.getId());
		blockUseSet.add((byte) Material.FENCE_GATE.getId());
		blockUseSet.add((byte) Material.NETHER_FENCE.getId());
		blockUseSet.add((byte) Material.ENCHANTMENT_TABLE.getId());
		blockUseSet.add((byte) Material.BREWING_STAND.getId());
		blockUseSet.add((byte) Material.ENDER_CHEST.getId());
		blockUseSet.add((byte) Material.ANVIL.getId());
		blockUseSet.add((byte) Material.TRAPPED_CHEST.getId());
		blockUseSet.add((byte) Material.HOPPER.getId());
		blockUseSet.add((byte) Material.DROPPER.getId());

		blockUseSet.add((byte) Material.BIRCH_FENCE_GATE.getId());
		blockUseSet.add((byte) Material.JUNGLE_FENCE_GATE.getId());
		blockUseSet.add((byte) Material.DARK_OAK_FENCE_GATE.getId());
		blockUseSet.add((byte) Material.ACACIA_FENCE_GATE.getId());
		blockUseSet.add((byte) Material.SPRUCE_FENCE_GATE.getId());
		blockUseSet.add((byte) Material.BIRCH_FENCE_GATE.getId());
		blockUseSet.add((byte) Material.JUNGLE_FENCE_GATE.getId());
		blockUseSet.add((byte) Material.DARK_OAK_FENCE_GATE.getId());
		blockUseSet.add((byte) Material.ACACIA_FENCE_GATE.getId());

		blockUseSet.add((byte) Material.SPRUCE_DOOR.getId());
		blockUseSet.add((byte) Material.BIRCH_DOOR.getId());
		blockUseSet.add((byte) Material.JUNGLE_DOOR.getId());
		blockUseSet.add((byte) Material.ACACIA_DOOR.getId());
		blockUseSet.add((byte) Material.DARK_OAK_DOOR.getId());
	}

	public static void main(String[] args) {
		for (Material m : Material.values()) {
			boolean thisSolid = fullSolid(m.getId());
			boolean solid = m.isSolid();
			if (thisSolid != solid) {
				StringBuilder sb = new StringBuilder();
				sb.append("Failed: ");
				sb.append(m.name());
				int amount = 40 - sb.length();
				for (int i = 0; i < amount; i++) {
					sb.append(" ");
				}
				sb.append(thisSolid);
				System.out.println(sb);
			}
		}
		System.out.println("done!");
	}

	public static boolean solid(Block block) {
		if (block == null) {
			return false;
		}

		return solid(block.getTypeId());
	}

	public static boolean solid(int block) {
		return solid((byte) block);
	}

	public static boolean solid(byte block) {
		return !blockPassSet.contains(block);
	}

	public static boolean airFoliage(Block block) {
		if (block == null) {
			return false;
		}

		return airFoliage(block.getTypeId());
	}

	public static boolean airFoliage(int block) {
		return airFoliage((byte) block);
	}

	public static boolean airFoliage(byte block) {
		return blockAirFoliageSet.contains(block);
	}

	public static boolean fullSolid(Block block) {
		if (block == null) {
			return false;
		}

		return fullSolid(block.getTypeId());
	}

	public static boolean fullSolid(int block) {
		return fullSolid((byte) block);
	}

	public static boolean fullSolid(byte block) {
		return fullSolid.contains(block);
	}

	/**
	 * Determines whether a block is a bottom slab.
	 *
	 * @param block The block object.
	 * @return <code>true</code> if block is a bottom slab.
	 */
	public static boolean bottomSlab(Block block) {
		return bottomSlab(block.getType().getId(), block.getData());
	}

	/**
	 * Determines whether a block is a bottom slab.
	 *
	 * @param block The block id
	 * @param data  The block data
	 * @return <code>true</code> if block is a bottom slab.
	 */
	public static boolean bottomSlab(int block, byte data) {
		switch (block) {
			case 44:
				if (data >= 0 && data <= 7) {
					return true;
				}
				break;
			case 182:
				if (data == 0) {
					return true;
				}
				break;
			case 126:
				if (data >= 0 && data <= 5) {
					return true;
				}
				break;
		}

		return false;
	}

	public static boolean usable(Block block) {
		if (block == null) {
			return false;
		}

		return usable(block.getTypeId());
	}

	public static boolean usable(int block) {
		return usable((byte) block);
	}

	public static boolean usable(byte block) {
		return blockUseSet.contains(block);
	}

	public static HashMap<Block, Double> getInRadius(Block block, double dR) {
		return getInRadius(block, dR, false);
	}

	public static HashMap<Block, Double> getInRadius(Block block, double dR, boolean hollow) {
		HashMap<Block, Double> blockList = new HashMap<>();
		int iR = (int) dR + 1;

		for (int x = -iR; x <= iR; x++)
			for (int z = -iR; z <= iR; z++)
				for (int y = -iR; y <= iR; y++) {
					Block curBlock = block.getRelative(x, y, z);
					double offset = MathUtils.offset(block.getLocation(), curBlock.getLocation());
					if (offset <= dR && !(hollow && offset < dR - 1)) {
						blockList.put(curBlock, 1 - (offset / dR));
					}
				}

		return blockList;
	}

	public static ArrayList<Block> getInSquare(Block block, double dR) {
		ArrayList<Block> blockList = new ArrayList<>();
		int iR = (int) dR + 1;

		for (int x = -iR; x <= iR; x++)
			for (int z = -iR; z <= iR; z++)
				for (int y = -iR; y <= iR; y++) {
					blockList.add(block.getRelative(x, y, z));
				}

		return blockList;
	}

	public static boolean isBlock(ItemStack item) {
		if (item == null) {
			return false;
		}

		return item.getTypeId() > 0 && item.getTypeId() < 256;
	}

	public static Block getHighest(World world, int x, int z) {
		return getHighest(world, x, z, null);
	}

	public static Block getHighest(World world, Location location) {
		return getHighest(world, location.getBlockX(), location.getBlockZ());
	}

	public static Block getHighest(World world, Block block) {
		return getHighest(world, block.getLocation());
	}

	public static Block getHighest(World world, int x, int z, HashSet<Material> ignore) {
		Block block = world.getHighestBlockAt(x, z);

		// Shuffle Down
		while (block.getY() > 0 && (airFoliage(block) || block.getType() == Material.LEAVES || block.getType() == Material.LEAVES_2 || (ignore != null && ignore.contains(block.getType())))) {
			block = block.getRelative(BlockFace.DOWN);
		}

		return block.getRelative(BlockFace.UP);
	}

	public static List<Block> getInBoundingBox(World world, AxisAlignedBB box) {
		Location l1 = new Location(world, box.a, box.b, box.c);
		Location l2 = new Location(world, box.d, box.e, box.f);

		return getInBoundingBox(l1, l2);
	}

	public static ArrayList<Block> getInBoundingBox(Location a, Location b) {
		return getInBoundingBox(a, b, true);
	}

	public static ArrayList<Block> getInBoundingBox(Location a, Location b, boolean ignoreAir) {
		return getInBoundingBox(a, b, ignoreAir, false, false, false);
	}

	public static ArrayList<Block> getInBoundingBox(Location a, Location b, boolean ignoreAir, boolean hollow, boolean wallsOnly, boolean ceilfloorOnly) {
		ArrayList<Block> blocks = new ArrayList<>();

		int xmin = Math.min(a.getBlockX(), b.getBlockX());
		int xmax = Math.max(a.getBlockX(), b.getBlockX());

		int ymin = Math.min(a.getBlockY(), b.getBlockY());
		int ymax = Math.max(a.getBlockY(), b.getBlockY());

		int zmin = Math.min(a.getBlockZ(), b.getBlockZ());
		int zmax = Math.max(a.getBlockZ(), b.getBlockZ());

		for (int x = xmin; x <= xmax; x++)
			for (int y = ymin; y <= ymax; y++)
				for (int z = zmin; z <= zmax; z++) {
					if (hollow) {
						if (!(x == xmin || x == xmax || y == ymin || y == ymax || z == zmin || z == zmax)) {
							continue;
						}
					}

					if (wallsOnly) {
						if ((x != xmin && x != xmax) && (z != zmin && z != zmax)) {
							continue;
						}
					}

					if (ceilfloorOnly) {
						if (y != ymin && y != ymax) {
							continue;
						}
					}

					Block block = a.getWorld().getBlockAt(x, y, z);
					if (ignoreAir) {
						if (block.getType() != Material.AIR) blocks.add(block);
					} else {
						blocks.add(block);
					}
				}

		return blocks;
	}

	public static int getStepSoundId(Block block) {
		if (block.getTypeId() != 35 && block.getTypeId() != 159 && block.getTypeId() != 160) return block.getTypeId();

		switch (block.getData()) {
			case 0:
				return block.getTypeId();
			case 1:
				return 172;
			case 2:
				return 87;
			case 3:
				return 79;
			case 4:
				return 41;
			case 5:
				return 133;
			case 6:
				return 45;
			case 7:
				return 16;
			case 8:
				return 13;
			case 9:
				return 56;
			case 10:
				return 110;
			case 11:
				return 22;
			case 12:
				return 3;
			case 13:
				return 31;
			case 14:
				return 152;
			case 15:
				return 173;

			default:
				return block.getTypeId();
		}
	}

	/**
	 * Gets the max distance this blocks bounding box extends in the given block face. E.g. stone have a max:min of 1:0 in all direction.
	 * Slabs have 0:1 in horizontal directions, but 0:0.5 or 0.5:1 depending on if it is top or bottom.
	 *
	 * @param block     The block to test
	 * @param blockFace The direction to test in
	 * @return
	 */
	public static double getSize(Block block, BlockFace blockFace) {
		BlockPosition bpos = new BlockPosition(block.getX(), block.getY(), block.getZ());
		net.minecraft.server.v1_8_R3.Block b = ((CraftWorld) block.getWorld()).getHandle().c(bpos);

		switch (blockFace) {
			default:
			case WEST:
				return b.B();    //min-x
			case EAST:
				return b.C();    //max-x
			case DOWN:
				return b.D();    //min-y
			case UP:
				return b.E();    //max-y
			case NORTH:
				return b.F();    //min-z
			case SOUTH:
				return b.G();    //max-z
		}
	}

	public static boolean water(Material type) {
		return type == Material.WATER || type == Material.STATIONARY_WATER;
	}

	public static boolean lava(Material type) {
		return type == Material.LAVA || type == Material.STATIONARY_LAVA;
	}

	public static boolean liquid(Material type) {
		return water(type) || lava(type);
	}

	public static boolean water(Block block) {
		return water(block.getType());
	}

	public static boolean lava(Block block) {
		return lava(block.getType());
	}

	public static boolean liquid(Block block) {
		return liquid(block.getType());
	}

	public static BlockFace getFace(float yaw) {
		return horizontals.get(Math.round(yaw / 90F) & 0x3);
	}

	public static boolean isFence(Block block) {
		return isFence(block.getType());
	}

	public static boolean isFence(Material type) {
		switch (type) {
			case FENCE:
			case FENCE_GATE:
			case ACACIA_FENCE:
			case BIRCH_FENCE:
			case DARK_OAK_FENCE:
			case IRON_FENCE:
			case JUNGLE_FENCE:
			case NETHER_FENCE:
			case SPRUCE_FENCE:
			case ACACIA_FENCE_GATE:
			case BIRCH_FENCE_GATE:
			case DARK_OAK_FENCE_GATE:
			case JUNGLE_FENCE_GATE:
			case SPRUCE_FENCE_GATE:
				return true;
		}

		return false;
	}

	public static boolean isSlab(Block block) {
		return isSlab(block.getType());
	}

	public static boolean isSlab(Material type) {
		switch (type) {
			case STEP:
			case WOOD_STEP:
			case STONE_SLAB2:
				return true;
		}

		return false;
	}
}
