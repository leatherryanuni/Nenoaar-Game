package com.badlogic.unisim;

abstract class Event {
    int duration;
    int satisfactionEffect;
    String message;

    // Pauses game, displays popup, starts running down duration
    abstract void start();

    // Adds/subtracts value of satisfactionEffect to current satisfaction level ingame
    // Not sure if called at start or end
    abstract void affectSatisfaction();

    // When game paused also pause event timer i.e. stop running down duration
    // Vice versa for resume
    abstract void pause();
    abstract void resume();

    // Event could be started in constructor?
}
