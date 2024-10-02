package net.fullstackjones.bigbrainsmpmod.block.custom;


import net.fullstackjones.bigbrainsmpmod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.LocalDateTime;

import static net.fullstackjones.bigbrainsmpmod.data.ModAttachmentTypes.UBI;

public class PiggyBankBlock extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public PiggyBankBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(!level.isClientSide){
            LocalDateTime lastUbiAllowance = LocalDateTime.parse(player.getData(UBI));
            LocalDateTime currentTime = LocalDateTime.now();
            Duration duration =  Duration.between(lastUbiAllowance, currentTime);

            if(duration.toMinutes() >= 0.5)
            {
                ItemStack Coin = ModItems.COPPERCOIN.get().getDefaultInstance();
                givePlayerAllowance(player, Coin);
                player.playSound(SoundEvents.AMETHYST_BLOCK_PLACE);
                player.setData(UBI, currentTime.toString());
            }
        }
        return ItemInteractionResult.SUCCESS;
    }

    private boolean canAddToPlayerInventory(Player player, ItemStack itemStack){
        return player.getInventory().getFreeSlot() > 0
                || player.getInventory().getSlotWithRemainingSpace(itemStack) > 0;
    }

    private void givePlayerAllowance(Player player, ItemStack itemStack){
        if(canAddToPlayerInventory(player, itemStack)){
            player.addItem(itemStack);
        } else {
            player.drop(itemStack, false);
        }
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        switch (direction) {
            case NORTH:
                return makeShape();
            case SOUTH:
                return rotateShape(makeShape(), Direction.SOUTH);
            case WEST:
                return rotateShape(makeShape(), Direction.WEST);
            case EAST:
                return rotateShape(makeShape(), Direction.EAST);
            default:
                return makeShape();
        }
    }

    private VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.1875, 0, 0.0625, 0.8125, 0.0625, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.0625, 0.375, 0.625, 0.25, 0.5), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.4375, 0.25, 0.25, 0.5625, 0.5625, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0.1875, 0.25, 0.75, 0.25, 0.5625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.25, 0.25, 0.875, 0.5625, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5625, 0.25, 0.1875, 0.8125, 0.625, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.25, 0.25, 0.1875, 0.5625, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.25, 0.1875, 0.4375, 0.625, 0.6875), BooleanOp.OR);
        return shape;
    }

    private VoxelShape rotateShape(VoxelShape shape, Direction direction) {
        VoxelShape[] buffer = new VoxelShape[]{shape, Shapes.empty()};

        int times = (direction.get2DDataValue() + 2) % 4;
        for (int i = 0; i < times; i++) {
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
                buffer[1] = Shapes.or(buffer[1], Shapes.box(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX));
            });
            buffer[0] = buffer[1];
            buffer[1] = Shapes.empty();
        }

        return buffer[0];
    }

}
