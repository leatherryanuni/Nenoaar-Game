package com.badlogic.unisim;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

public class Student {
    Sprite sprite;
    Texture texture;
    Student(CollisionDetector detector) {
        texture = new Texture("student.png");
        sprite = new Sprite(texture);
        sprite.setSize(texture.getWidth(), texture.getHeight());
        int x = 0, y = 0;
        do {
            x = MathUtils.random(0, 60);
            y = MathUtils.random(0, 33);
        } while (!detector.isTileBuildable(x, y));
        sprite.setPosition((float) (x * 13.3), (float) (y * 14.54));
    }
}
