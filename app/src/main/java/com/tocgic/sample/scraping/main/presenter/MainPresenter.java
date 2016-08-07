package com.tocgic.sample.scraping.main.presenter;

/**
 * Created by tocgic on 2016. 8. 6..
 */
public interface MainPresenter {

    void loadPhotos(int page);

    void onPhotoItemClick(int position);

    void removePhoto(int position);

    void onPhotoItemLongClick(int position);

    interface View {

        void refresh();

        void showItemActionDialog(int position, String url);

        void showImageDialog(String url);
    }
}
