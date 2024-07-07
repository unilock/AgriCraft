package com.agricraft.agricraft.api.fertilizer;

import com.agricraft.agricraft.api.TagUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

// TODO: @Ketheroth convert nbt to component ?
public record AgriFertilizerVariant(ExtraCodecs.TagOrElementLocation item/*, CompoundTag nbt*/) {

	public static final Codec<AgriFertilizerVariant> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			ExtraCodecs.TAG_OR_ELEMENT_ID.fieldOf("item").forGetter(variant -> variant.item)/*,
			CompoundTag.CODEC.optionalFieldOf("nbt").forGetter(variant -> variant.nbt.isEmpty() ? Optional.empty() : Optional.of(variant.nbt))*/
	).apply(instance, AgriFertilizerVariant::new));

//	public AgriFertilizerVariant(ExtraCodecs.TagOrElementLocation item, Optional<CompoundTag> nbt) {
		// codec use
//		this(item, nbt.orElse(new CompoundTag()));
//	}

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
//		CompoundTag nbt = new CompoundTag();

		public AgriFertilizerVariant build() {
			return new AgriFertilizerVariant(item/*, nbt*/);
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

	}
}
