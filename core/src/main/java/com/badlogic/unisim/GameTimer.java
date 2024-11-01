package com.badlogic.unisim;

/**
 * This class is responsible for running a timer in the game upon entering
 * the game screen.
 */
public class GameTimer {
    private float remainingTime;
    private boolean isPaused;

    public GameTimer(int minutes) {
        this.remainingTime = minutes * 60f;
        this.isPaused = true; // Game starts paused
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
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * Converts the remaining time in seconds into minutes and seconds,
     * and prints it.
     * @return the time in minutes and seconds as a string.
     */
    public String getFormattedTime() {
        int minutes = (int) (remainingTime / 60);
        int seconds = (int) (remainingTime % 60);
        return String.format("%02d:%02d", minutes, seconds);
    }
}
