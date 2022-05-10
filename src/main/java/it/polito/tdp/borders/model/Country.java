package it.polito.tdp.borders.model;

public class Country {
	
	private String abbrState;
	private int codCountry;
	private String stateName;
	
	public Country(String abbrState, int codCountry, String stateName) {
		super();
		this.abbrState = abbrState;
		this.codCountry = codCountry;
		this.stateName = stateName;
	}

	public String getAbbrState() {
		return abbrState;
	}

	public void setAbbrState(String abbrState) {
		this.abbrState = abbrState;
	}

	public int getCodCountry() {
		return codCountry;
	}

	public void setCodCountry(int codCountry) {
		this.codCountry = codCountry;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codCountry;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (codCountry != other.codCountry)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "" + stateName;
	}
	
	
}
