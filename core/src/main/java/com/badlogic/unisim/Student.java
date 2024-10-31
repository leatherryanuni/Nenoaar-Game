package com.badlogic.unisim;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Student {
    Sprite sprite;
    Texture texture;
    int x = 0, y = 0;
    CollisionDetector detector; // Used to check if the student is going to a valid space
    private Vector2 endPoint;
    int moveTimer;
    Student(CollisionDetector detector) {
        texture = new Texture("student.png");
        sprite = new Sprite(texture);
        this.detector = detector;
        sprite.setSize(texture.getWidth(), texture.getHeight());
        setPath();
    }

    private void setPath() {
        do {
            x = MathUtils.random(0, 60);
            y = MathUtils.random(0, 33);
        } while (!detector.isTileBuildable(x, y));
        setPosition(x, y);
        do {
            endPoint = new Vector2(MathUtils.random(0, 60), MathUtils.random(0, 33));
        } while (!detector.isTileBuildable((int) endPoint.x, (int) endPoint.y));
        moveTimer = MathUtils.random(1, 2);
    }

    private void setPosition(int x, int y) {
        sprite.setPosition((float) (x * 13.3), (float) (y * 14.54));
    }

    public boolean move() {
        x = (int) (sprite.getX() / 13.3);
        y = (int) (sprite.getY() / 14.54);
        if (moveTimer > 0) {
            moveTimer--;
            return false;
        }
        moveTimer = MathUtils.random(1, 2);
        if (x == endPoint.x) {
            if (y < endPoint.y && detector.isTileBuildable(x, y + 1)) {
                setPosition(x, y + 1);
                return reachedEnd(x, y + 1);
            }
            else if (y > endPoint.y && detector.isTileBuildable(x, y - 1)) {
                setPosition(x, y - 1);
                return reachedEnd(x, y - 1);
            }
        }
        if (x < endPoint.x && detector.isTileBuildable(x + 1, y)) {
            setPosition(x + 1, y);
            return reachedEnd(x + 1, y);
        }
        if (x > endPoint.x && detector.isTileBuildable(x - 1, y)) {
            setPosition(x - 1, y);
            return reachedEnd(x - 1, y);
        }
        return true;
    }

    private boolean reachedEnd(int x, int y) {
        Vector2 currentPos = new Vector2(x, y);
        return currentPos.equals(endPoint);
    }

    public void resetStudent() {
        setPath();
    }
}
