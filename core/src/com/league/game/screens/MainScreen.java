package com.league.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.league.game.LeagueOfHorrors;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;


public class MainScreen extends ScreenAdapter {
    private Stage stage;
    private LeagueOfHorrors gameManager;
    private TextField usernameField, passwordField;


    public MainScreen(LeagueOfHorrors gameManager)  {
        this.gameManager = gameManager;
    }


    @Override
    public void show(){
            stage = new Stage();
            Gdx.input.setInputProcessor(stage);
            Table table = new Table();
            table.setFillParent(true);
            usernameField = new TextField("", new TextField.TextFieldStyle(new BitmapFont(), Color.BLACK, null, null, null));
            usernameField.setMessageText("Enter username");
            usernameField.setAlignment(Align.center);
            usernameField.setFocusTraversal(false);
            table.add(usernameField).pad(10).colspan(2).width(400).height(50);
            table.row();

            passwordField = new TextField("", new TextField.TextFieldStyle(new BitmapFont(), Color.BLACK, null, null, null));
            passwordField.setMessageText("Enter password");
            passwordField.setPasswordMode(true);
            passwordField.setPasswordCharacter('*');
            passwordField.setAlignment(Align.center);
            passwordField.setFocusTraversal(false);
            table.add(passwordField).pad(10).colspan(2).width(400).height(50);
            table.row();

        usernameField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO: handle change
            }
        });

        passwordField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO: handle change
            }
        });


        TextButton loginButton = new TextButton("Login", new TextButton.TextButtonStyle(null, null, null, new BitmapFont()));
            loginButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    String username = usernameField.getText();
                    String password = passwordField.getText();
                    // TODO: handle login
                }
            });
            table.add(loginButton).pad(10).width(150).height(50);

            TextButton registerButton = new TextButton("Register", new TextButton.TextButtonStyle(null, null, null, new BitmapFont()));
            registerButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    String username = usernameField.getText();
                    String password = passwordField.getText();
                    // TODO: handle registration
                }
            });

            Gdx.input.setOnscreenKeyboardVisible(true);
            stage.setKeyboardFocus(usernameField);
            table.add(registerButton).pad(10).width(150).height(50);
            stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
