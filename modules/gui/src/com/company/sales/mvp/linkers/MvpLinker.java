/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.sales.mvp.linkers;


import com.company.sales.gui.Screen;
import com.company.sales.gui.customer.CustomerEdit;
import com.company.sales.mvp.models.Model;
import com.company.sales.mvp.models.impl.CustomerModelImpl;
import com.company.sales.mvp.presentes.impl.CustomerPresenterImpl;
import com.company.sales.mvp.presentes.interfaces.Presenter;
import com.google.common.collect.ImmutableSet;
import com.haulmont.cuba.core.global.DevelopmentException;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

/**
 * Created by DukeKan on 04.11.2017.
 */
public class MvpLinker {

    private static ImmutableSet<MvpLink> mvpLinks = ImmutableSet.<MvpLink>builder()
            .add(MvpLink.of(CustomerEdit.class, CustomerPresenterImpl.class, CustomerModelImpl.class))
            .build();

    private static Optional<MvpLink> getMvpLinkByScreen(Class<? extends Screen> screen) {
        return mvpLinks.stream().filter(link -> link.getScreen().equals(screen)).findFirst();
    }

    public static void create(Screen screen) {
        Class<? extends Screen> screenClass = screen.getClass();
        Optional<MvpLink> mvpLinkOptional = getMvpLinkByScreen(screenClass);
        if (mvpLinkOptional.isPresent()) {
            MvpLink mvpLink = mvpLinkOptional.get();
            Class<? extends Model> modelClass = mvpLink.getModel();
            Class<? extends Presenter> presenterClass = mvpLink.getPresenter();
            instantiatePresenter(screen, modelClass, presenterClass);
        } else {
            throw new DevelopmentException("No MVP classes registered for screen");
        }
    }

    private static void instantiatePresenter(Screen screen, Class<? extends Model> modelClass,
                                             Class<? extends Presenter> presenterClass) {
        Model model = instantiateModel(modelClass);
        Class<? extends Screen> screenClass = screen.getClass();
        try {
            presenterClass.getConstructor(screenClass, modelClass).newInstance(screen, model);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new NullPointerException("Can not create presenter");
        } catch (NoSuchMethodException | InvocationTargetException e) {
            throw new NullPointerException("No suitable constructor found for presenter");
        }
    }

    private static Model instantiateModel(Class<? extends Model> model) {
        try {
            return model.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new NullPointerException("Can not create model");
        } catch (NoSuchMethodException | InvocationTargetException e) {
            throw new NullPointerException("No default constructor found for model");
        }
    }

    private MvpLinker() {
    }
}
