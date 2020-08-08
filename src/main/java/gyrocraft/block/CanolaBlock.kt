/*
package gyrocraft.block

import gyrocraft.init.ModItems
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.IGrowable
import net.minecraft.inventory.InventoryHelper
import net.minecraft.item.ItemStack
import net.minecraft.state.IntegerProperty
import net.minecraft.util.Direction
import net.minecraft.util.NonNullList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorldReader
import net.minecraft.world.World
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.common.IPlantable
import java.util.*

class CanolaBlock(props: Properties): Block(props.tickRandomly()), IPlantable, IGrowable {
    companion object {
        val GROWN = 9
        val GROWTH = IntegerProperty.create("growth", 0, GROWN)
    }

    init {
        System.out.println("test")
    }

    override fun getPlant(world: IBlockReader, pos: BlockPos): BlockState = defaultState
    override fun canUseBonemeal(worldIn: World, rand: Random, pos: BlockPos, state: BlockState) = true

    override fun tick(state: BlockState, world: ServerWorld, pos: BlockPos, rand: Random) {
        if(!canSurvive(world, pos)) die(world, pos)
        else if(canGrow(world, pos) && rand.nextInt(3) == 0) grow(world, rand, pos, state)
    }

    override fun onBlockAdded(state: BlockState, world: World, pos: BlockPos, oldState: BlockState, isMoving: Boolean) {
        if(!canSurvive(world, pos)) die(world, pos)
    }

    override fun onNeighborChange(state: BlockState, world: IWorldReader, pos: BlockPos, neighbor: BlockPos) {
        if(world is World) {
            if(!canSurvive(world, pos)) die(world, pos)
        }
    }

    private fun die(world: World, pos: BlockPos) {
        world.removeBlock(pos, false)
        val drops = NonNullList.create<ItemStack>()
        drops.add(ItemStack(ModItems.EXAMPLE_CRYSTAL.get()))
        InventoryHelper.dropItems(world, pos, drops)
    }

    override fun grow(world: ServerWorld, rand: Random, pos: BlockPos, state: BlockState) {
        val growth = state[GROWTH]
        if(growth < GROWN) {
            world.setBlockState(pos, state.with(GROWTH, growth + 1), 3)
        }
    }

    override fun canGrow(world: IBlockReader, pos: BlockPos, state: BlockState, isClient: Boolean) = canGrow(world, pos)

    private fun canGrow(world: IBlockReader, pos: BlockPos) =
            world.getLightValue(pos) >= 9 && isValidFarmBlock(world, pos.down()) && !isBlockedUpwards(world, pos)

    private fun isValidFarmBlock(world: IBlockReader, pos: BlockPos): Boolean {
        val state = world.getBlockState(pos)
        if(state.isAir(world, pos)) return false
        return state.isFertile(world, pos) && state.canSustainPlant(world, pos, Direction.UP, this)
    }

    private fun isBlockedUpwards(world: IBlockReader, pos: BlockPos): Boolean {
        val block = world.getBlockState(pos)
        return !(block.isOpaqueCube(world, pos) &&
                block.isNormalCube(world, pos) &&
                block.getOpacity(world, pos) > 25 &&
                block.material.isSolid)
    }

    private fun canSurvive(world: World, pos: BlockPos) =
            world.getLightValue(pos) >= 6 || world.canBlockSeeSky(pos.up()) && isValidFarmBlock(world, pos.down())
}
*/
