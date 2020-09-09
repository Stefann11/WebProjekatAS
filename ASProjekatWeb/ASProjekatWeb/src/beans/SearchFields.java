package beans;

public class SearchFields {
	protected String dateFrom;
	protected String dateTo;
	protected String place;
	protected double priceFrom;
	protected double priceTo;
	protected int numberOfRoomsFrom;
	protected int numberOfRoomsTo;
	protected int numberOfGuestsFrom;
	protected int numberOfGuestsTo;

	public SearchFields() {
		super();
		this.dateFrom = "";
		this.dateTo = "";
		this.place = "";
		this.priceFrom = 0;
		this.priceTo = 0;
		this.numberOfRoomsFrom = 0;
		this.numberOfRoomsTo = 0;
		this.numberOfGuestsFrom = 0;
		this.numberOfGuestsTo = 0;
	}

	public SearchFields(String dateFrom, String dateTo, String place, double priceFrom, double priceTo,
			int numberOfRoomsFrom, int numberOfRoomsTo, int numberOfGuestsFrom, int numberOfGuestsTo) {
		super();
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.place = place;
		this.priceFrom = priceFrom;
		this.priceTo = priceTo;
		this.numberOfRoomsFrom = numberOfRoomsFrom;
		this.numberOfRoomsTo = numberOfRoomsTo;
		this.numberOfGuestsFrom = numberOfGuestsFrom;
		this.numberOfGuestsTo = numberOfGuestsTo;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public double getPriceFrom() {
		return priceFrom;
	}

	public void setPriceFrom(double priceFrom) {
		this.priceFrom = priceFrom;
	}

	public double getPriceTo() {
		return priceTo;
	}

	public void setPriceTo(double priceTo) {
		this.priceTo = priceTo;
	}

	public int getNumberOfRoomsFrom() {
		return numberOfRoomsFrom;
	}

	public void setNumberOfRoomsFrom(int numberOfRoomsFrom) {
		this.numberOfRoomsFrom = numberOfRoomsFrom;
	}

	public int getNumberOfRoomsTo() {
		return numberOfRoomsTo;
	}

	public void setNumberOfRoomsTo(int numberOfRoomsTo) {
		this.numberOfRoomsTo = numberOfRoomsTo;
	}

	public int getNumberOfGuestsFrom() {
		return numberOfGuestsFrom;
	}

	public void setNumberOfGuestsFrom(int numberOfGuestsFrom) {
		this.numberOfGuestsFrom = numberOfGuestsFrom;
	}

	public int getNumberOfGuestsTo() {
		return numberOfGuestsTo;
	}

	public void setNumberOfGuestsTo(int numberOfGuestsTo) {
		this.numberOfGuestsTo = numberOfGuestsTo;
	}

	@Override
	public String toString() {
		return "searchFields [dateFrom=" + dateFrom + ", dateTo=" + dateTo + ", place=" + place + ", priceFrom="
				+ priceFrom + ", priceTo=" + priceTo + ", numberOfRoomsFrom=" + numberOfRoomsFrom + ", numberOfRoomsTo="
				+ numberOfRoomsTo + ", numberOfGuestsFrom=" + numberOfGuestsFrom + ", numberOfGuestsTo="
				+ numberOfGuestsTo + "]";
	}




}