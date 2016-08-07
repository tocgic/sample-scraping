package com.tocgic.sample.scraping.network.dagger;

import com.tocgic.sample.scraping.network.ScrapWeb;

import dagger.Module;
import dagger.Provides;

/**
 * Created by tocgic on 2016. 8. 6..
 */
@Module
public class ApiModule {
    @Provides
    ScrapWeb providerScrapWeb() {
        return new ScrapWeb();
    }
}
