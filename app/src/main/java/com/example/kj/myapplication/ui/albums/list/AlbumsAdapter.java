package com.example.kj.myapplication.ui.albums.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kj.myapplication.R;
import com.example.kj.myapplication.entity.Album;
import com.squareup.picasso.Picasso;

public class AlbumsAdapter extends ArrayAdapter<Album> {
    private Context mContext;

    public AlbumsAdapter(Context ctx) {
        super(ctx, R.layout.contact_item);
        mContext = ctx;
    }

    @SuppressLint("SetTextI18n")
    public View getView(int position, View convertView, ViewGroup parent) {
        Album album = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.album_item, null);
        }

        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivAlbumPhoto);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvAlbumName);

        tvName.setText(album.getName());
        if (!album.getPhoto().isEmpty()) {
            Picasso.with(mContext).load(album.getPhoto()).into(ivPhoto);
        }
        return convertView;
    }
}