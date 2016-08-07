package com.tocgic.sample.scraping.network;

import android.util.Log;

import com.tocgic.sample.scraping.helper.StringUtil;
import com.tocgic.sample.scraping.network.domain.Photo;
import com.tocgic.sample.scraping.network.domain.Photos;
import com.tocgic.sample.scraping.network.domain.WebPhoto;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;
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

    public Observable<WebPhoto> getScrapPhoto(String webUrl) {
        WebPhoto webPhoto = new WebPhoto();

        Photos photos = new Photos();

        photos.setPhotoList(getItems(webUrl));

        webPhoto.setPhotos(photos);


        return Observable.just(webPhoto);
    }

    private List<Photo> getItems(String url) {
        Log.i("ScrapWeb", "url:\n"+url);
        List<Photo> photoList = new ArrayList<>();
        if (url != null && url.length() > 0) {
            try {
                Document doc = Jsoup.parse(new URL(url).openStream(), "euc-kr", url);
                Elements datas = doc.select("img");
                for (Element data : datas) {
                    String src = data.attr("src");
                    String clazz = data.attr("class");
                    if (StringUtil.isNotNull(src)) {
                        if (StringUtil.isNotNull(clazz) && clazz.equalsIgnoreCase("picture")) {
                            Photo photo = new Photo();
                            photo.setPath(src);
                            photoList.add(photo);
                        }
                    }
                }

//                Elements datas = doc.select(".picture");
//                for (Element data : datas) {
//                    String src = data.attr("src");
//                    if (StringUtil.isNotNull(src)) {
//                        Photo photo = new Photo();
//                        photo.setPath(src);
//                        photoList.add(photo);
//                    }
//                }

//                Elements datas = doc.select("div");
//                for (Element data : datas) {
//                    String clazz = data.attr("class");
//                    if (StringUtil.isNotNull(clazz) && clazz.equalsIgnoreCase("gallery-item-group exitemrepeater")) {
//                        Elements subImg = data.select("img");
//                        String src = null;
//                        if (subImg != null && subImg.size() > 0) {
//                            src = subImg.get(0).attr("src");
//                        }
//                        Elements subA = data.select(".gallery-item-caption a");
//                        String text = null;
//                        if (subA != null && subA.size() > 0) {
//                            src = subA.get(0).data();
//                        }
//                        if (StringUtil.isNotNull(src)) {
//                            Photo photo = new Photo();
//                            photo.setPath(src);
//                            if (StringUtil.isNotNull(text)) {
//                                photo.setFileName(text);
//                            }
//                            photoList.add(photo);
//                        }
//                    }
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return photoList;
    }
}
