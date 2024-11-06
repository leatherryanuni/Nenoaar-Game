package com.badlogic.unisim;

/**
 * This class is responsible for running a timer in the game upon entering
 * the game screen and updating the time of year display.
 */
public class GameTimer {
    private float remainingTime;
    private boolean isPaused;
    private final int durationInMinutes;
    private final String[] seasonsOfTheYear;
    private final String[] years;

    public GameTimer(int setMinutes) {
        this.durationInMinutes = setMinutes;
        this.remainingTime = setMinutes * 60f;
        this.isPaused = true;// Game starts paused
        this.seasonsOfTheYear = new String[]{"Spring", "Summer", "Autumn", "Winter"};
        this.years = new String[]{"2062", "2063", "2064", "2065", "2066"};
    }

    /**
     * Updates the timer by reducing by delta time if not paused.
     * @param deltaTime the time since the last render in seconds.
     */
    public void updateTime(float deltaTime) {
        if (!isPaused && remainingTime > 0) {
            remainingTime -= deltaTime;
        }
        if (remainingTime <= 0) {
            isPaused = true;
        }
    }

    public void pauseTime() { isPaused = true; }

    public void resumeTime() { isPaused = false; }

    /**
     * Checks if the timer has run out.
     * @return true if the time has reached or passed 0, otherwise false.
     */
    public boolean isTimeEnded() {
        return remainingTime <= 0;
    }

    /**
     * Checks if the game is paused or not.
     * @return true if the game is paused, otherwise false.
     */
    public boolean getIsPaused() {
        return isPaused;
    }

    /**
     * Converts a 5-minute time frame into a 5-year period, with each year
     * split into 4 seasons.
     * @return the season of the year and the year.
     */
    public String getFormattedDate() {
        int SECONDS_PER_MIN = 60;
        int SECONDS_PER_SEASON = 15;
        // Carry out some calculations to iterate through array of years every
        // minute.
        int remainingMinutes = (int) (remainingTime / SECONDS_PER_MIN);
        int yearsIndex = durationInMinutes - remainingMinutes;
        // Carry out some calculations to iterate through array of seasons every
        // 15 seconds, starting from 0 again every minute.
        int remainingSeconds = (int) (remainingTime % SECONDS_PER_MIN);
        int elapsedIntervals = (SECONDS_PER_MIN - remainingSeconds) / SECONDS_PER_SEASON;
        int seasonIndex = elapsedIntervals % seasonsOfTheYear.length;
        // return is of the format e.g Spring + " " + 2066
        return seasonsOfTheYear[seasonIndex] + " " + years[yearsIndex];
    }
}
