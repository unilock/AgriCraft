package com.agricraft.agricraft.api.codecs;

import com.agricraft.agricraft.api.TagUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

// TODO: @Ketheroth convert nbt to component ?
public record AgriSeed(ExtraCodecs.TagOrElementLocation item, boolean overridePlanting/*, CompoundTag nbt*/) {

	public static final Codec<AgriSeed> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			ExtraCodecs.TAG_OR_ELEMENT_ID.fieldOf("item").forGetter(seed -> seed.item),
			Codec.BOOL.fieldOf("override_planting").forGetter(seed -> seed.overridePlanting)
//			CompoundTag.CODEC.optionalFieldOf("nbt").forGetter(seed -> seed.nbt.isEmpty() ? Optional.empty() : Optional.of(seed.nbt))
	).apply(instance, AgriSeed::new));

//	public AgriSeed(ExtraCodecs.TagOrElementLocation item, boolean overridePlanting, Optional<CompoundTag> nbt) {
		// codec use
//		this(item, overridePlanting, nbt.orElse(new CompoundTag()));
//	}

	public static Builder builder() {
		return new Builder();
	}

	public boolean isVariant(ItemStack itemStack) {
		List<Item> items = TagUtils.items(this.item());
		if (items.contains(itemStack.getItem())) {
//			if (this.nbt.isEmpty()) {
//				return true;
//			}
//			CompoundTag tag = itemStack.getOrCreateTag();
//			for (String key : this.nbt.getAllKeys()) {
//				if (!tag.contains(key) || !tag.get(key).equals(this.nbt.get(key))) {
//					return false;
//				}
//			}
			return true;
		}
		return false;
	}

	public static class Builder {

		ExtraCodecs.TagOrElementLocation item;
		boolean overridePlanting = true;
//		CompoundTag nbt = new CompoundTag();

		public AgriSeed build() {
			return new AgriSeed(item, overridePlanting/*, nbt*/);
		}

		public Builder item(String location) {
			this.item = new ExtraCodecs.TagOrElementLocation(ResourceLocation.parse(location), false);
			return this;
		}

		public Builder item(String namespace, String path) {
			this.item = new ExtraCodecs.TagOrElementLocation(ResourceLocation.fromNamespaceAndPath(namespace, path), false);
			return this;
		}

		public Builder tag(String location) {
			this.item = new ExtraCodecs.TagOrElementLocation(ResourceLocation.parse(location), true);
			return this;
		}

		public Builder tag(String namespace, String path) {
			this.item = new ExtraCodecs.TagOrElementLocation(ResourceLocation.fromNamespaceAndPath(namespace, path), true);
			return this;
		}

//		public Builder nbt(CompoundTag nbt) {
//			this.nbt = nbt;
//			return this;
//		}

		public Builder overridePlanting(boolean overridePlanting) {
			this.overridePlanting = overridePlanting;
			return this;
		}

	}

}
