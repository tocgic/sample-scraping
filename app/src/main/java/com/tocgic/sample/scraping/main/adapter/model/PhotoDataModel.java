package com.tocgic.sample.scraping.main.adapter.model;

import com.tocgic.sample.scraping.network.domain.Photo;

/**
 * Created by tocgic on 2016. 8. 6..
 */
public interface PhotoDataModel {
    void add(Photo photo);
    Photo getPhoto(int position);
    void remove(int position);
}
