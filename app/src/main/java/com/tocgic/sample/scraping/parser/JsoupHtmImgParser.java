package com.tocgic.sample.scraping.parser;

import com.tocgic.sample.scraping.network.domain.Photo;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * Created by tocgic on 2016. 8. 8..
 */
public interface JsoupHtmImgParser {
    List<Photo> getPhotoList(Document document);
}
