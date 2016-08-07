package com.tocgic.sample.scraping.main.dagger;

import com.tocgic.sample.scraping.main.view.MainActivity;

import dagger.Component;

/**
 * Created by tocgic on 2016. 8. 6..
 */

@Component(modules = {MainModule.class})
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
