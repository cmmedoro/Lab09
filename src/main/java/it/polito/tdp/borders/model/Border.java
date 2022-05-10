package it.polito.tdp.borders.model;

public class Border {
	
	private int state1;
	private int state2;
	private int dyad;
	//private String abbrState1;
	//private String abbrState2;
	private Country s1;
	private Country s2;
	private int year;
	private int conttype;
	private float version;
	
	public Border(/*int state1, int state2,*/ Country s1, Country s2) {
		super();
		//this.state1 = state1;
		//this.state2 = state2;
		this.s1 = s1;
		this.s2 = s2;
		//this.year = year;
	}

	public int getState1() {
		return state1;
	}

	public void setState1(int state1) {
		this.state1 = state1;
	}

	public int getState2() {
		return state2;
	}

	public void setState2(int state2) {
		this.state2 = state2;
	}

	public int getDyad() {
		return dyad;
	}

	public void setDyad(int dyad) {
		this.dyad = dyad;
	}

	public Country getS1() {
		return this.s1;
	}

	public void setAbbrState1(Country s1) {
		this.s1 = s1;
	}

	public Country getS2() {
		return this.s2;
	}

	public void setAbbrState2(Country s2) {
		this.s2 = s2;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getConttype() {
		return conttype;
	}

	public void setConttype(int conttype) {
		this.conttype = conttype;
	}

	public float getVersion() {
		return version;
	}

	public void setVersion(float version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + state1;
		result = prime * result + state2;
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
		Border other = (Border) obj;
		if (state1 != other.state1)
			return false;
		if (state2 != other.state2)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Border [state1=" + state1 + ", state2=" + state2 + ", dyad=" + dyad + ", abbrState1=" + s1
				+ ", abbrState2=" + s2 + ", year=" + year + ", conttype=" + conttype + ", version=" + version
				+ "]";
	}
	
}
