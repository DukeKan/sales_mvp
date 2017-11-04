/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.mvp.linkers;

import com.company.sales.gui.Screen;
import com.company.sales.mvp.models.Model;
import com.company.sales.mvp.presentes.interfaces.Presenter;

/**
 * Created by DukeKan on 04.11.2017.
 */
public class MvpLink {
    private Class<? extends Screen> screen;
    private Class<? extends Presenter> presenter;
    private Class<? extends Model> model;

    public MvpLink(Class<? extends Screen> screen, Class<? extends Presenter> presenter, Class model) {
        this.screen = screen;
        this.presenter = presenter;
        this.model = model;
    }

    public Class<? extends Screen> getScreen() {
        return screen;
    }

    public void setScreen(Class<? extends Screen> screen) {
        this.screen = screen;
    }

    public Class<? extends Presenter> getPresenter() {
        return presenter;
    }

    public void setPresenter(Class<? extends Presenter> presenter) {
        this.presenter = presenter;
    }

    public Class<? extends Model> getModel() {
        return model;
    }

    public void setModel(Class<? extends Model> model) {
        this.model = model;
    }

    public static MvpLink of(Class<? extends Screen> screen, Class<? extends Presenter> presenter, Class model){
        return new MvpLink(screen, presenter, model);
    }
}
