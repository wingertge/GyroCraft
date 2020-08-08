package gyrocraft.block;

import gyrocraft.init.ModItems;
import net.minecraft.block.*;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.state.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.*;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class CanolaBlock extends Block implements IPlantable, IGrowable, CustomItem {
    public static final int GROWN = 9;
    public static final IntegerProperty GROWTH = IntegerProperty.create("growth", 0, GROWN);

    public CanolaBlock(Properties properties) {
        super(properties.tickRandomly());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(GROWTH);
    }

    @Override
    public boolean isFoliage(BlockState state, IWorldReader world, BlockPos pos) {
        return true;
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.Crop;
    }

    @Override
    public void tick(@NotNull BlockState state, @NotNull ServerWorld world, @NotNull BlockPos pos, @NotNull Random rand) {
        if(!canSurvive(world, pos)) die(world, pos);
        else if(canGrow(world, pos) && rand.nextInt(3) == 0) grow(world, rand, pos, state);
    }

    @Override
    public boolean canGrow(@NotNull IBlockReader worldIn, @NotNull BlockPos pos, @NotNull BlockState state, boolean isClient) {
        return canGrow(worldIn, pos);
    }

    private boolean canGrow(IBlockReader world, BlockPos pos) {
        return world.getLightValue(pos) >= 9 &&
                isValidFarmBlock(world, pos.down()) &&
                !isBlockedUpwards(world, pos);
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(@NotNull ServerWorld world, @NotNull Random rand, @NotNull BlockPos pos, BlockState state) {
        int growth = state.get(GROWTH);
        if(growth < GROWN) {
            world.setBlockState(pos, state.with(GROWTH, growth + 1), 3);
        }
    }

    private void die(World world, BlockPos pos) {
        world.removeBlock(pos, false);
        NonNullList<ItemStack> drops = NonNullList.create();
        drops.add(new ItemStack(ModItems.EXAMPLE_CRYSTAL.get()));
        InventoryHelper.dropItems(world, pos, drops);
    }

    @Override
    public BlockState getPlant(IBlockReader world, BlockPos pos) {
        return getDefaultState();
    }

    private boolean isValidFarmBlock(IBlockReader world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if(state.isAir(world, pos)) return false;
        return state.isFertile(world, pos) && state.canSustainPlant(world, pos, Direction.UP, this);
    }

    private boolean isBlockedUpwards(IBlockReader world, BlockPos pos) {
        BlockState block = world.getBlockState(pos);
        return  !block.isOpaqueCube(world, pos) ||
                !block.isNormalCube(world, pos) ||
                block.getOpacity(world, pos) <= 25 ||
                !block.getMaterial().isSolid();
    }

    private boolean canSurvive(World world, BlockPos pos) {
        return world.getLightValue(pos) >= 6 ||
                world.canBlockSeeSky(pos.up()) && isValidFarmBlock(world, pos.down());
    }
}
