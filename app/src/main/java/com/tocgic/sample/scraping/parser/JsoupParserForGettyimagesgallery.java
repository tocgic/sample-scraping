package com.tocgic.sample.scraping.parser;

import android.util.Log;

import com.tocgic.sample.scraping.helper.StringUtil;
import com.tocgic.sample.scraping.network.domain.Photo;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tocgic on 2016. 8. 8..
 * for gettyimagesgallery (http://www.gettyimagesgallery.com/collections/archive/slim-aarons.aspx)
 */
public class JsoupParserForGettyimagesgallery implements JsoupHtmImgParser {
    @Override
    public List<Photo> getPhotoList(Document document) {
        List<Photo> photoList = new ArrayList<>();

//        Elements elements = document.select("img");
//        for (Element data : elements) {
//            String src = data.attr("src");
//            String clazz = data.attr("class");
//            if (StringUtil.isNotNull(src)) {
//                if (StringUtil.isNotNull(clazz) && clazz.equalsIgnoreCase("picture")) {
//                    Photo photo = new Photo();
//                    photo.setPath(src);
//                    photoList.add(photo);
//                }
//            }
//        }

//        Elements elements = document.select("img[class=picture]");
//        for (Element data : elements) {
//            String src = data.attr("src");
//            if (StringUtil.isNotNull(src)) {
//                Photo photo = new Photo();
//                photo.setPath(src);
//                photoList.add(photo);
//            }
//        }

        Elements elements = document.select("div[class^=gallery-item-group");
        for (Element element : elements) {
            String src, text = null;
            Element elementImg = element.select("img").first();
            src = elementImg.attr("src");


            Element elementDivCaption = element.select("div[class=gallery-item-caption").first();
            if (elementDivCaption != null) {
                Element elementACaption = elementDivCaption.select("a[href]").first();
                text = elementACaption.text();
            }

            if (StringUtil.isNotNull(src)) {
                Log.d("ScrapWeb", "Photo path:"+src+", fileName:"+text);
                Photo photo = new Photo();
                photo.setPath(src);
                if (StringUtil.isNotNull(text)) {
                    photo.setFileName(text);
                }
                photoList.add(photo);
            }
        }

        return photoList;
    }
}
