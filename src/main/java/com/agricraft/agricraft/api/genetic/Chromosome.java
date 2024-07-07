package com.agricraft.agricraft.api.genetic;

/**
 * A chromosome contains two alleles of a specific gene.
 * @param <T> the type of the gene
 */
public class Chromosome<T> {

	private final AgriGene<T> gene;
	private final T dominant;
	private final T recessive;

	public Chromosome(AgriGene<T> gene, T first, T second) {
		this.gene = gene;
		if (gene.isAlleleDominant(first, second)) {
			this.dominant = first;
			this.recessive = second;
		} else {
			this.dominant = second;
			this.recessive = first;
		}
	}
	public Chromosome(AgriGene<T> gene, T both) {
		this.gene = gene;
		this.dominant = both;
		this.recessive = both;
	}

	/**
	 * @return the gene for this gene pair
	 */
	public AgriGene<T> gene() {
		return this.gene;
	}

	public AgriGene<T> castGene(AgriGene<?> gene) {
		return (AgriGene<T>) gene;
	}

	/**
	 * @return The apparent trait resulting form the two alleles (by default this is the dominant allele)
	 */
	public T trait() {
		return this.dominant();
	}

	/**
	 * @return the dominant allele
	 */
	public final T dominant() {
		return this.dominant;
	}

	/**
	 * @return the recessive allele
	 */
	public final T recessive() {
		return this.recessive;
	}

	public Chromosome<T> copy() {
		return new Chromosome<>(this.gene(), this.dominant(), this.recessive());
	}

	@Override
	public String toString() {
		return "Chromosome{" +
				"gene=" + gene +
				", dominant=" + dominant +
				", recessive=" + recessive +
				'}';
	}

	@Override
	public final boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Chromosome<?> that)) {
			return false;
		}

		return gene.equals(that.gene) && dominant.equals(that.dominant) && recessive.equals(that.recessive);
	}

	@Override
	public int hashCode() {
		int result = gene.hashCode();
		result = 31 * result + dominant.hashCode();
		result = 31 * result + recessive.hashCode();
		return result;
	}

}
