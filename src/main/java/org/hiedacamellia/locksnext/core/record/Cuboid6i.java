package org.hiedacamellia.locksnext.core.record;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.phys.Vec3;

public record Cuboid6i(int x1, int y1, int z1, int x2, int y2, int z2){

    public static final Codec<Cuboid6i> CODEC = RecordCodecBuilder.create(cuboid6iInstance ->
            cuboid6iInstance.group(
                    Codec.INT.fieldOf("x1").forGetter(Cuboid6i::x1),
                    Codec.INT.fieldOf("y1").forGetter(Cuboid6i::y1),
                    Codec.INT.fieldOf("z1").forGetter(Cuboid6i::z1),
                    Codec.INT.fieldOf("x2").forGetter(Cuboid6i::x2),
                    Codec.INT.fieldOf("y2").forGetter(Cuboid6i::y2),
                    Codec.INT.fieldOf("z2").forGetter(Cuboid6i::z2)
            ).apply(cuboid6iInstance, Cuboid6i::new)
    );

    public static final StreamCodec<ByteBuf,Cuboid6i> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,Cuboid6i::x1,
            ByteBufCodecs.INT,Cuboid6i::y1,
            ByteBufCodecs.INT,Cuboid6i::z1,
            ByteBufCodecs.INT,Cuboid6i::x2,
            ByteBufCodecs.INT,Cuboid6i::y2,
            ByteBufCodecs.INT,Cuboid6i::z2,
            Cuboid6i::new
    );
    public Cuboid6i(int x1, int y1, int z1, int x2, int y2, int z2)
    {
        this.x1= Math.min(x1, x2);
        this.y1= Math.min(y1, y2);
        this.z1= Math.min(z1, z2);
        this.x2= Math.max(x1, x2);
        this.y2= Math.max(y1, y2);
        this.z2= Math.max(z1, z2);
    }

    public Cuboid6i(BlockPos pos1, BlockPos pos2)
    {
        this(Math.min(pos1.getX(), pos2.getX()),Math.min(pos1.getY(), pos2.getY()),
                Math.min(pos1.getZ(), pos2.getZ()),Math.max(pos1.getX(), pos2.getX()) + 1,
                Math.max(pos1.getY(), pos2.getY()) + 1,Math.max(pos1.getZ(), pos2.getZ()) + 1);
    }

    public Cuboid6i(BlockPos pos) {
        this(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
    }

    public Cuboid6i(){
        this(0,0,0,0,0,0);
    }

    public int lengthX() {
        return x2 - x1;
    }

    public int lengthY() {
        return y2 - y1;
    }

    public int lengthZ() {
        return z2 - z1;
    }

    public int volume() {
        return lengthX() * lengthY() * lengthZ();
    }

    public boolean contains(BlockPos pos) {
        return pos.getX() >= x1 && pos.getX() < x2 && pos.getY() >= y1 && pos.getY() < y2 && pos.getZ() >= z1 && pos.getZ() < z2;
    }

    public boolean intersects(Cuboid6i other) {
        return x1 < other.x2 && x2 > other.x1 && y1 < other.y2 && y2 > other.y1 && z1 < other.z2 && z2 > other.z1;
    }

    public Cuboid6i intersection(Cuboid6i other) {
        return new Cuboid6i(Math.max(x1, other.x1), Math.max(y1, other.y1), Math.max(z1, other.z1), Math.min(x2, other.x2), Math.min(y2, other.y2), Math.min(z2, other.z2));
    }

    public Cuboid6i offset(int x, int y, int z) {
        return new Cuboid6i(x1 + x, y1 + y, z1 + z, x2 + x, y2 + y, z2 + z);
    }

    public Vec3 center() {
        return new Vec3((x1 + x2) / 2.0, (y1 + y2) / 2.0, (z1 + z2) / 2.0);
    }

    public Vec3 sideCenter(Direction direction){
        Vec3i vec3i = direction.getNormal();
        return new Vec3((x1 + x2 + vec3i.getX()) / 2.0, (y1 + y2 + vec3i.getY()) / 2.0, (z1 + z2 + vec3i.getZ()) / 2.0);
    }

    public BlockPos centerPos() {
        return new BlockPos((x1 + x2) / 2, (y1 + y2) / 2, (z1 + z2) / 2);
    }

    @Override
    public String toString() {
        return "Cuboid6i{" + "x1=" + x1 + ", y1=" + y1 + ", z1=" + z1 + ", x2=" + x2 + ", y2=" + y2 + ", z2=" + z2 + '}';
    }
}
