package projectgroep.parkeergarage.logic;

import java.lang.reflect.Field;
import java.util.HashMap;

public class Settings extends MapLike {
    /**
     * All settings fields
     */
    int numberOfFloors = 3;
    int numberOfRows = 6;
    int numberOfPlaces = 30;
    int numberOfPassHolderRows = 2;
    int numberOfResFloors = 1;
    int numberOfResRows = 2;
    double defaultPrice = 0.02;

    int distributedPasses = 60;

    int weekDayArrivals = 80; // average number of arriving AdHoc cars per hour
    int weekendArrivals = 110; // average number of arriving AdHoc cars per hour
    int weekDayPassArrivals = distributedPasses / 3; // average number of arriving Passholder cars per hour
    int weekendPassArrivals = distributedPasses / 3; // average number of arriving Passholder cars per hour
    int weekDayResArrivals = 30; // average number of arriving Reserved cars per hour
    int weekendResArrivals = 10; // average number of arriving Reserved cars per hour

    int maxQueue = 10; // The maximum queue size, after which cars will no longer enter
    double skipChance = 0.5; // 0 to 1

    int maxHistory = 2000;

    int enterSpeed = 8; // number of cars that can enter per minute
    int paymentSpeed = 5; // number of cars that can pay per minute
    int exitSpeed = 7; // number of cars that can leave per minute

    public Settings() {
    }

    public Settings(HashMap<String, Object> map) {
        map.forEach((k, v) -> {
            try {
                Field field = getClass().getDeclaredField(k);
                field.set(this, v);
            } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            }
        });
    }

	public int getNumberOfFloors() {
		return numberOfFloors;
	}

	public void setNumberOfFloors(int numberOfFloors) {
		this.numberOfFloors = numberOfFloors;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	public int getNumberOfPlaces() {
		return numberOfPlaces;
	}

	public void setNumberOfPlaces(int numberOfPlaces) {
		this.numberOfPlaces = numberOfPlaces;
	}

	public int getNumberOfPassHolderRows() {
		return numberOfPassHolderRows;
	}

	public void setNumberOfPassHolderRows(int numberOfPassHolderRows) {
		this.numberOfPassHolderRows = numberOfPassHolderRows;
	}

	public int getNumberOfResFloors() {
		return numberOfResFloors;
	}

	public void setNumberOfResFloors(int numberOfResFloors) {
		this.numberOfResFloors = numberOfResFloors;
	}

	public int getNumberOfResRows() {
		return numberOfResRows;
	}

	public void setNumberOfResRows(int numberOfResRows) {
		this.numberOfResRows = numberOfResRows;
	}

	public double getDefaultPrice() {
		return defaultPrice;
	}

	public void setDefaultPrice(double defaultPrice) {
		this.defaultPrice = defaultPrice;
	}

	public int getWeekDayArrivals() {
		return weekDayArrivals;
	}

	public void setWeekDayArrivals(int weekDayArrivals) {
		this.weekDayArrivals = weekDayArrivals;
	}

	public int getWeekendArrivals() {
		return weekendArrivals;
	}

	public void setWeekendArrivals(int weekendArrivals) {
		this.weekendArrivals = weekendArrivals;
	}

	public int getWeekDayPassArrivals() {
		return weekDayPassArrivals;
	}

	public void setWeekDayPassArrivals(int weekDayPassArrivals) {
		this.weekDayPassArrivals = weekDayPassArrivals;
	}

	public int getWeekendPassArrivals() {
		return weekendPassArrivals;
	}

	public void setWeekendPassArrivals(int weekendPassArrivals) {
		this.weekendPassArrivals = weekendPassArrivals;
	}

	public int getWeekDayResArrivals() {
		return weekDayResArrivals;
	}

	public void setWeekDayResArrivals(int weekDayResArrivals) {
		this.weekDayResArrivals = weekDayResArrivals;
	}

	public int getWeekendResArrivals() {
		return weekendResArrivals;
	}

	public void setWeekendResArrivals(int weekendResArrivals) {
		this.weekendResArrivals = weekendResArrivals;
	}

	public int getMaxQueue() {
		return maxQueue;
	}

	public void setMaxQueue(int maxQueue) {
		this.maxQueue = maxQueue;
	}

	public double getSkipChance() {
		return skipChance;
	}

	public void setSkipChance(double skipChance) {
		this.skipChance = skipChance;
	}

	public int getMaxHistory() {
		return maxHistory;
	}

	public void setMaxHistory(int maxHistory) {
		this.maxHistory = maxHistory;
	}

	public int getEnterSpeed() {
		return enterSpeed;
	}

	public void setEnterSpeed(int enterSpeed) {
		this.enterSpeed = enterSpeed;
	}

	public int getPaymentSpeed() {
		return paymentSpeed;
	}

	public void setPaymentSpeed(int paymentSpeed) {
		this.paymentSpeed = paymentSpeed;
	}

	public int getExitSpeed() {
		return exitSpeed;
	}

	public void setExitSpeed(int exitSpeed) {
		this.exitSpeed = exitSpeed;
	}
}
