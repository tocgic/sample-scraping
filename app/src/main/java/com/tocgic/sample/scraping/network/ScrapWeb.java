package com.tocgic.sample.scraping.network;

import com.tocgic.sample.scraping.network.domain.Photo;
import com.tocgic.sample.scraping.network.domain.Photos;
import com.tocgic.sample.scraping.network.domain.ScrapResult;
import com.tocgic.sample.scraping.parser.JsoupParserForGettyimagesgallery;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URL;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by tocgic on 2016. 8. 7..
 */
@Singleton
public class ScrapWeb {

    @Inject
    public ScrapWeb() {
    }

    public Observable<ScrapResult> getScrapPhoto(String webUrl) {
        ScrapResult scrapResult = new ScrapResult();

        Photos photos = new Photos();

        photos.setPhotoList(getItems(webUrl));

        scrapResult.setPhotos(photos);


        return Observable.just(scrapResult);
    }

    /**
     * parsing Photo from web url.
     * jsoup : https://jsoup.org/cookbook/extracting-data/selector-syntax
     * @param url
     * @return
     */
    private List<Photo> getItems(String url) {
        List<Photo> photoList = null;
        if (url != null && url.length() > 0) {
            try {
                Document document = Jsoup.parse(new URL(url).openStream(), "euc-kr", url);
                return new JsoupParserForGettyimagesgallery().getPhotoList(document);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return photoList;
    }
}
