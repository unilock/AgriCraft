package com.agricraft.agricraft.api.adapter;

import com.agricraft.agricraft.api.AgriApi;
import com.agricraft.agricraft.api.fertilizer.AgriFertilizer;
import com.agricraft.agricraft.api.genetic.AgriGenome;

import java.util.Optional;

/**
 * Convert an object to a specific type. Namely convert to an {@link AgriGenome} and an {@link AgriFertilizer}.
 * You can register your own via {@link AgriApi}
 * @param <T> the type to convert to
 */
public interface AgriAdapter<T> {

	boolean accepts(Object obj);

	Optional<T> valueOf(Object obj);

}
