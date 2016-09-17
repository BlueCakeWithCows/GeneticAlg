package components;

public class BreedingSummary {

	public int elite;

	// For simple mixer
	public int pairsToSelect, numberPerPairToSelect;

	// For simple mutator
	public int singleMutationsToSelect;

	// Overall percents
	public double percentMixBreed, percentMutateOnly;

	public int population;

	public void recalc() {
		double total = percentMixBreed + percentMutateOnly;
		percentMixBreed = percentMixBreed/total;
		percentMutateOnly = percentMutateOnly/total;
		int popMinusElite = this.population - elite;
	
		pairsToSelect = (int) Math.round((popMinusElite * percentMixBreed));
		singleMutationsToSelect = (int) Math.round(popMinusElite * percentMutateOnly);
	}
}
