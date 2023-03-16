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
import com.league.game.Handlers.HttpHandler;
import com.league.game.LeagueOfHorrors;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import java.net.HttpURLConnection;


public class MainScreen extends ScreenAdapter {
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 50;
    private Stage stage;
    private final LeagueOfHorrors gameManager;
    private TextField usernameField, passwordField;


    public MainScreen(LeagueOfHorrors gameManager)  {
        this.gameManager = gameManager;
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table table = createTable();
        stage.addActor(table);

        Gdx.input.setOnscreenKeyboardVisible(true);
        stage.setKeyboardFocus(usernameField);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        if (gameManager.playerAuthenticated) {
            gameManager.setScreen(new HeroSelectionScreen(gameManager));
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private static class TextFieldChangeListener extends ChangeListener {
        @Override
        public void changed(ChangeEvent event, Actor actor) {
            // TODO: handle change
        }
    }

    private TextField createTextField(String messageText, boolean password) {
        TextField.TextFieldStyle style = new TextField.TextFieldStyle(new BitmapFont(), Color.BLACK, null, null, null);
        TextField textField = new TextField("", style);
        textField.setMessageText(messageText);
        if (password) {
            textField.setPasswordCharacter('*');
            textField.setPasswordMode(true);
        }
        textField.setAlignment(Align.center);
        textField.setFocusTraversal(false);

        textField.addListener(new TextFieldChangeListener());
        return textField;
    }

    private Table createTable() {
        Table table = new Table();
        table.setFillParent(true);
        usernameField = createTextField("Enter username", false);
        passwordField = createTextField("Enter password", true);
        table.add(usernameField).pad(10).colspan(2).width(400).height(50);
        table.row();
        table.add(passwordField).pad(10).colspan(2).width(400).height(50);
        table.row();
        table.add(createButton("Login")).pad(10).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);
        table.add(createButton("Register")).pad(10).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);
        return table;
    }

    private TextButton createButton(final String buttonText) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(null, null, null, new BitmapFont());
        TextButton button = new TextButton(buttonText, style);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (buttonText.equals("Login")) {
                    handleLogin();
                } else if (buttonText.equals("Register")) {
                    handleRegistration();
                }
            }
        });
        return button;
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (HttpHandler.requestUserData(username, password, "login") == HttpURLConnection.HTTP_OK) {
            gameManager.playerAuthenticated = true;
        }
    }

    private void handleRegistration() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        // TODO: handle registration
        HttpHandler.requestUserData(username, password, "register");
    }
}
