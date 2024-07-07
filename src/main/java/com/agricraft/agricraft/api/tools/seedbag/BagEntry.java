package com.agricraft.agricraft.api.tools.seedbag;

import com.agricraft.agricraft.api.genetic.AgriGenome;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record BagEntry(int count, AgriGenome genome) {

	public static final Codec<BagEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.INT.fieldOf("count").forGetter(data -> data.count),
			AgriGenome.CODEC.fieldOf("genome").forGetter(data -> data.genome)
	).apply(instance, BagEntry::new));
	public static StreamCodec<RegistryFriendlyByteBuf, BagEntry> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.INT, data -> data.count,
			AgriGenome.STREAM_CODEC, data -> data.genome,
			BagEntry::new
	);

}
