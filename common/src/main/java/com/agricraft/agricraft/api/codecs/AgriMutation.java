package com.agricraft.agricraft.api.codecs;

import com.agricraft.agricraft.api.AgriApi;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public record AgriMutation(boolean enabled, List<String> mods, ResourceLocation child, ResourceLocation parent1,
                           ResourceLocation parent2, double chance) {

	public static final Codec<AgriMutation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.BOOL.fieldOf("enabled").forGetter(mutation -> mutation.enabled),
			Codec.STRING.listOf().fieldOf("mods").forGetter(mutation -> mutation.mods),
			ResourceLocation.CODEC.fieldOf("child").forGetter(mutation -> mutation.child),
			ResourceLocation.CODEC.fieldOf("parent1").forGetter(mutation -> mutation.parent1),
			ResourceLocation.CODEC.fieldOf("parent2").forGetter(mutation -> mutation.parent2),
			Codec.DOUBLE.fieldOf("chance").forGetter(mutation -> mutation.chance)
	).apply(instance, AgriMutation::new));

	public Optional<AgriPlant> getParent1() {
		return AgriApi.getPlant(this.parent1);
	}

	public Optional<AgriPlant> getParent2() {
		return AgriApi.getPlant(this.parent2);
	}

	public Optional<AgriPlant> getChild() {
		return AgriApi.getPlant(this.child);
	}

	public boolean isValid() {
		return getChild().isPresent() && getParent1().isPresent() && getParent2().isPresent();
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		List<String> mods = new ArrayList<>();
		double chance = 0;
		boolean enabled = true;
		ResourceLocation child;
		ResourceLocation parent1;
		ResourceLocation parent2;

		public AgriMutation build() {
			return new AgriMutation(enabled, mods, child, parent1, parent2, chance);
		}

		public Builder mods(String... mods) {
			Collections.addAll(this.mods, mods);
			return this;
		}

		public Builder chance(double chance) {
			this.chance = chance;
			return this;
		}

		public Builder child(String child) {
			this.child = new ResourceLocation(child);
			return this;
		}

		public Builder child(ResourceLocation child) {
			this.child = child;
			return this;
		}

		public Builder parent1(String parent1) {
			this.parent1 = new ResourceLocation(parent1);
			return this;
		}

		public Builder parent1(ResourceLocation parent1) {
			this.parent1 = parent1;
			return this;
		}

		public Builder parent2(String parent2) {
			this.parent2 = new ResourceLocation(parent2);
			return this;
		}

		public Builder parent2(ResourceLocation parent2) {
			this.parent2 = parent2;
			return this;
		}

		public Builder parents(String parent1, String parent2) {
			this.parent1 = new ResourceLocation(parent1);
			this.parent2 = new ResourceLocation(parent2);
			return this;
		}

		public Builder parents(ResourceLocation parent1, ResourceLocation parent2) {
			this.parent1 = parent1;
			this.parent2 = parent2;
			return this;
		}

	}

}
