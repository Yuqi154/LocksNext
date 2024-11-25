package org.hiedacamellia.locksnext.core.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.hiedacamellia.locksnext.core.record.Cuboid6i;
import org.hiedacamellia.locksnext.core.record.Lock;
import org.hiedacamellia.locksnext.core.record.LockInfo;
import org.hiedacamellia.locksnext.core.record.LockedFace;

import javax.annotation.Nullable;
import java.util.ArrayList;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.*;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.CHEST_TYPE;
import static net.minecraft.world.level.block.state.properties.DoorHingeSide.LEFT;
import static net.minecraft.world.level.block.state.properties.DoubleBlockHalf.LOWER;

public class LockInfoUtil {

    public static @Nullable LockInfo create(Level level, BlockPos pos){
        return create(level, pos, new Lock(0L,false,"hard"));
    }
    public static @Nullable LockInfo create(Level level, BlockPos pos, Lock lock){
        BlockState blockState = level.getBlockState(pos);
        Direction direction = getDirection(blockState);
        if(direction==null) return null;
        BlockPos posNext = pos;
        BlockPos pos1 = checkChest(pos, blockState);
        if(pos1!=null){
            posNext = pos1;
        }
        BlockPos pos2 = checkDoor(level, pos, blockState, direction);
        if(pos2!=null){
            posNext = pos2;
            direction = direction.getOpposite();
        }
        return new LockInfo(new Cuboid6i(pos,posNext), new LockedFace(direction), lock, ItemStack.EMPTY);
    }

    private static @Nullable BlockPos checkDoor(Level level, BlockPos pos, BlockState blockState, Direction direction) {
        if (!blockState.hasProperty(DOUBLE_BLOCK_HALF)) return null;
        if (blockState.getValue(DOUBLE_BLOCK_HALF) == LOWER) return null;
        BlockPos pos1 = pos.below();
        if (!blockState.hasProperty(DOOR_HINGE)) return pos1;
        if (blockState.hasProperty(HORIZONTAL_FACING)) {
            BlockPos pos2 = pos1.relative(blockState.getValue(DOOR_HINGE) == LEFT ? direction.getClockWise() : direction.getCounterClockWise());
            if (level.getBlockState(pos2).is(blockState.getBlock())) {
                if (blockState.getValue(DOOR_HINGE) == LEFT) {
                    return null;
                }
                pos1 = pos2;
            }
        }
        return pos1;
    }

    public static @Nullable BlockPos checkChest(BlockPos pos, BlockState blockState) {
        if (!blockState.hasProperty(CHEST_TYPE)) return null;
        return switch (blockState.getValue(CHEST_TYPE)) {
            case SINGLE -> pos;
            case LEFT -> pos.relative(ChestBlock.getConnectedDirection(blockState));
            case RIGHT -> null;
        };
    }

    public static @Nullable Direction getDirection(BlockState blockState){
        if(blockState.hasProperty(FACING)) {
            return blockState.getValue(FACING);
        }
        if (blockState.hasProperty(HORIZONTAL_FACING)) {
            return blockState.getValue(HORIZONTAL_FACING);
        }
        return null;
    }
}
