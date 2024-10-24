package com.badlogic.unisim;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Student {
    Texture studentFront = new Texture("studentFront.png");
    Sprite sprite = new Sprite(studentFront);

    Student() {
        sprite.setSize(studentFront.getWidth(), studentFront.getHeight());
        sprite.setX(MathUtils.random(0f, 60 - sprite.getWidth()));
        sprite.setY(MathUtils.random(0f, 33 - sprite.getHeight()));
    }

}
