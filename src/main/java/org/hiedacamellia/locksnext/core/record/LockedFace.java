package org.hiedacamellia.locksnext.core.record;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Direction;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.block.state.properties.AttachFace;

public record LockedFace(Direction direction, AttachFace face) {
    public static final LockedFace NORTH_UP = new LockedFace(Direction.NORTH, AttachFace.CEILING);
    public static final LockedFace SOUTH_UP = new LockedFace(Direction.SOUTH, AttachFace.CEILING);
    public static final LockedFace WEST_UP = new LockedFace(Direction.WEST, AttachFace.CEILING);
    public static final LockedFace EAST_UP = new LockedFace(Direction.EAST, AttachFace.CEILING);
    public static final LockedFace NORTH_MID = new LockedFace(Direction.NORTH, AttachFace.WALL);
    public static final LockedFace SOUTH_MID = new LockedFace(Direction.SOUTH, AttachFace.WALL);
    public static final LockedFace WEST_MID = new LockedFace(Direction.WEST, AttachFace.WALL);
    public static final LockedFace EAST_MID = new LockedFace(Direction.EAST, AttachFace.WALL);
    public static final LockedFace NORTH_DOWN = new LockedFace(Direction.NORTH, AttachFace.FLOOR);
    public static final LockedFace SOUTH_DOWN = new LockedFace(Direction.SOUTH, AttachFace.FLOOR);
    public static final LockedFace WEST_DOWN = new LockedFace(Direction.WEST, AttachFace.FLOOR);
    public static final LockedFace EAST_DOWN = new LockedFace(Direction.EAST, AttachFace.FLOOR);

    public static final Codec<LockedFace> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Direction.CODEC.fieldOf("direction").forGetter(LockedFace::direction),
                    Codec.STRING.fieldOf("face").forGetter(LockedFace::toString)
            ).apply(instance, LockedFace::new)
    );

    public static final StreamCodec<ByteBuf,LockedFace> STREAM_CODEC = StreamCodec.composite(
            Direction.STREAM_CODEC, LockedFace::direction,
            ByteBufCodecs.STRING_UTF8, LockedFace::toString,
            LockedFace::new
    );

    public LockedFace(Direction direction, String face) {
        this(direction, fromString(face));
    }
    public LockedFace() {
        this(Direction.NORTH, AttachFace.CEILING);
    }
    public LockedFace(Direction direction) {
        this(direction,faceFromDir(direction));
    }

    public static AttachFace fromString(String face) {
        return AttachFace.valueOf(face);
    }

    public String toString(AttachFace face) {
        return face.name();
    }

    public static final LockedFace[] VALUES = new LockedFace[]{
            NORTH_UP, SOUTH_UP, WEST_UP, EAST_UP,
            NORTH_MID, SOUTH_MID, WEST_MID, EAST_MID,
            NORTH_DOWN, SOUTH_DOWN, WEST_DOWN, EAST_DOWN
    };

    public static LockedFace fromDirectionAndFace(Direction direction, AttachFace face, Direction def) {
        for (LockedFace lockedFace : VALUES) {
            if (lockedFace.direction() == (direction.getAxis() == Direction.Axis.Y ? def : direction) && lockedFace.face() == face) {
                return lockedFace;
            }
        }
        return null;
    }

    public static LockedFace fromDirection(Direction direction, Direction def) {
        return fromDirectionAndFace(direction, faceFromDir(direction), def);
    }

    public static AttachFace faceFromDir(Direction direction) {
        return switch (direction) {
            case UP -> AttachFace.CEILING;
            case DOWN -> AttachFace.FLOOR;
            default -> AttachFace.WALL;
        };
    }
}
