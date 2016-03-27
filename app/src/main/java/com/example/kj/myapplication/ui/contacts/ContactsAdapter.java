package com.example.kj.myapplication.ui.contacts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kj.myapplication.R;
import com.example.kj.myapplication.entity.Contact;
import com.squareup.picasso.Picasso;

public class ContactsAdapter extends ArrayAdapter<Contact> {
    private Context mContext;

    public ContactsAdapter(Context ctx) {
        super(ctx, R.layout.contact_item);
        mContext = ctx;
    }

    @SuppressLint("SetTextI18n")
    public View getView(int position, View convertView, ViewGroup parent) {
        Contact contact = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.contact_item, null);
        }

        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivSmallPhoto);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvMessages = (TextView) convertView.findViewById(R.id.tvMessages);
        tvName.setText(contact.getName() + ", " + contact.getAge());

        tvMessages.setText(mContext.getResources().getQuantityString
                (R.plurals.messages, contact.getMessageCount(), contact.getMessageCount()));
        if (!contact.getPhoto().isEmpty()) {
            Picasso.with(mContext).load(contact.getPhoto()).into(ivPhoto);
        } else {
            ivPhoto.setImageResource(R.drawable.no_photo_man_small);
        }
        return convertView;
    }
}