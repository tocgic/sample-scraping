package com.tocgic.sample.scraping.main.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tocgic.sample.scraping.R;
import com.tocgic.sample.scraping.main.adapter.PhotoAdapter;
import com.tocgic.sample.scraping.main.adapter.view.PhotoAdapterView;
import com.tocgic.sample.scraping.main.dagger.DaggerMainComponent;
import com.tocgic.sample.scraping.main.dagger.MainModule;
import com.tocgic.sample.scraping.main.presenter.MainPresenter;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainPresenter.View {
    private static final int FIRST_PAGE = 1;

    @Bind(R.id.lv_recycler)
    RecyclerView lvRecycler;

    @Inject
    PhotoAdapterView photoAdapterView;

    @Inject
    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        PhotoAdapter photoAdapter = new PhotoAdapter(MainActivity.this);
        DaggerMainComponent.builder()
                .mainModule(new MainModule(this, photoAdapter))
                .build()
                .inject(this);

        lvRecycler.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
        lvRecycler.setAdapter(photoAdapter);
        photoAdapter.setOnRecyclerItemClickListener((adapter1, position) -> mainPresenter.onPhotoItemClick(position));
        photoAdapter.setOnRecyclerItemLongClickListener((adapter1, position) -> {
            mainPresenter.onPhotoItemLongClick(position);
            return true;
        });

        mainPresenter.loadPhotos(FIRST_PAGE);
    }

    @Override
    public void refresh() {
        photoAdapterView.refresh();
    }

    @Override
    public void showItemActionDialog(int position, String url) {
        String[] menus = {"Copy", "Show Browser", "Delete"};
        new AlertDialog.Builder(MainActivity.this)
                .setItems(menus, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                            clipboardManager.setPrimaryClip(ClipData.newPlainText("", url));
                            break;
                        case 1:
                            Intent contentIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            Intent chooserIntent = Intent.createChooser(contentIntent, "Choose WebBrowser");
                            startActivity(chooserIntent);
                            break;
                        case 2:
                            mainPresenter.removePhoto(position);
                            break;

                    }
                })
                .create()
                .show();
    }

    @Override
    public void showImageDialog(String url) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_photo_detail, null);

        new AlertDialog.Builder(MainActivity.this)
                .setView(view)
                .create().show();

        ImageView imageView = (ImageView) view.findViewById(R.id.iv_photo_detail);
        Glide.with(MainActivity.this)
                .load(url)
                .crossFade()
                .into(imageView);
    }

}
