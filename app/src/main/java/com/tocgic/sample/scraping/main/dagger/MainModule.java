package com.tocgic.sample.scraping.main.dagger;

import com.tocgic.sample.scraping.main.adapter.PhotoAdapter;
import com.tocgic.sample.scraping.main.adapter.model.PhotoDataModel;
import com.tocgic.sample.scraping.main.adapter.view.PhotoAdapterView;
import com.tocgic.sample.scraping.main.presenter.MainPresenter;
import com.tocgic.sample.scraping.main.presenter.MainPresenterImpl;
import com.tocgic.sample.scraping.network.dagger.ApiModule;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tocgic on 2016. 8. 6..
 */
@Module(includes = {ApiModule.class})
public class MainModule {
    private PhotoAdapter photoAdapter;
    private MainPresenter.View view;

    public MainModule(MainPresenter.View view, PhotoAdapter photoAdapter) {
        this.view = view;
        this.photoAdapter = photoAdapter;
    }

    @Provides
    PhotoDataModel providePhotoModel() {
        return photoAdapter;
    }

    @Provides
    PhotoAdapterView providePhotoView() {
        return photoAdapter;
    }

    @Provides
    MainPresenter provideMainPresenter(MainPresenterImpl mainPresenter) {
        return mainPresenter;
    }

    @Provides
    MainPresenter.View provideViewOfMainPresenter() {
        return view;
    }
}
