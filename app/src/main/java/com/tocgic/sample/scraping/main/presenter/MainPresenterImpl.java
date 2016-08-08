package com.tocgic.sample.scraping.main.presenter;

import com.tocgic.sample.scraping.define.Const;
import com.tocgic.sample.scraping.main.adapter.model.PhotoDataModel;
import com.tocgic.sample.scraping.network.ScrapWeb;
import com.tocgic.sample.scraping.network.domain.Photo;
import com.tocgic.sample.scraping.network.domain.ScrapResult;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by tocgic on 2016. 8. 6..
 */
public class MainPresenterImpl implements MainPresenter {
    private View view;
    private ScrapWeb scrapWeb;
    private PhotoDataModel photoDataModel;

    @Inject
    public MainPresenterImpl(View view, ScrapWeb scrapWeb, PhotoDataModel photoDataModel) {
        this.view = view;
        this.scrapWeb = scrapWeb;
        this.photoDataModel = photoDataModel;
    }

    @Override
    public void loadPhotos(int page) {
        Observable<ScrapResult> webPhotoObservable = Observable.defer(() -> scrapWeb.getScrapPhoto(Const.URL));
                webPhotoObservable
                        .subscribeOn(Schedulers.io())
                        .map(ScrapResult::getPhotos)
                        .filter(photos -> photos.getPhotoList() != null && !photos.getPhotoList().isEmpty())
                        .flatMap(photos -> Observable.from(photos.getPhotoList()))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(photoDataModel::add, Throwable::printStackTrace, view::refresh);
    }

    @Override
    public void onPhotoItemClick(int position) {
        Photo photo = photoDataModel.getPhoto(position);
        String url = getUrl(photo);

        view.showItemActionDialog(position, url);
    }

    @Override
    public void removePhoto(int position) {
        photoDataModel.remove(position);
        view.refresh();
    }

    @Override
    public void onPhotoItemLongClick(int position) {
        Photo photo = photoDataModel.getPhoto(position);
        String url = getUrl(photo);

        view.showImageDialog(url);
    }

    private String getUrl(Photo photo) {
        return String.format(Const.URL2+"%s", photo.getPath());
    }
}
