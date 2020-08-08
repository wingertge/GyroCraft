package gyrocraft.item;

import gyrocraft.init.ModBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraftforge.common.*;
import org.jetbrains.annotations.NotNull;

public class CanolaSeedsItem extends Item implements IPlantable {
    public CanolaSeedsItem(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState getPlant(IBlockReader world, BlockPos pos) {
        //return ModBlocks.CANOLA.get().getDefaultState();
        return null;
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.Crop;
    }

    @NotNull
    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        BlockPos pos = context.getPos();
        Direction face = context.getFace();
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        assert player != null;
        ItemStack heldItem = player.getHeldItem(context.getHand());

        BlockState state = world.getBlockState(pos);

        if(face == Direction.UP &&
                player.canPlayerEdit(pos.offset(face), face, heldItem) &&
                state.canSustainPlant(world, pos, Direction.UP, this)
        ) {
            world.setBlockState(pos.up(), getPlant(world, pos));
            if(player instanceof ServerPlayerEntity) {
                CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)player, pos.up(), heldItem);
            }
            heldItem.shrink(1);
            return ActionResultType.SUCCESS;
        } else return ActionResultType.FAIL;
    }
}
