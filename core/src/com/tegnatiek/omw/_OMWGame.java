package com.tegnatiek.omw;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tegnatiek.omw.view.GameUI;

public class _OMWGame extends ApplicationAdapter {

    private GameUI ui;
    private OrthographicCamera camera;
    private Viewport viewport;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewport = new StretchViewport(1080f, 1920f, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        ui = new GameUI(
                camera.viewportWidth,
                camera.viewportHeight
        );
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }

    @Override
    public void dispose() {
        ui.destroy();
        super.dispose();
    }

    @Override
    public void render() {
        camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ui.render(camera);
    }
}
