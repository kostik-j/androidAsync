package com.example.kj.myapplication.ui.contacts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kj.myapplication.R;
import com.example.kj.myapplication.entity.Contact;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    private Context mContext;
    private List<Contact> mContacts = new ArrayList<>();
    private Listener mListener;

    public interface Listener {
        /**
         * Событие когда докрытили до конца списка
         */
        void onReachedFloor();

        /**
         * Хотим отобразить подробную информацию о контакте
         */
        void onShowDetailContact(Contact contact);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = ViewHolder.class.getSimpleName();
        private ImageView mPhoto;
        private TextView mName;
        private TextView mMessages;
        private ViewGroup mContainer;
        private Contact mContact;
        private Listener mListener;

        public ViewHolder(View itemView, Listener listener) {
            super(itemView);
            mListener = listener;
            mPhoto = (ImageView) itemView.findViewById(R.id.ivSmallPhoto);
            mName = (TextView) itemView.findViewById(R.id.tvName);
            mMessages = (TextView) itemView.findViewById(R.id.tvMessages);
            mContainer = (ViewGroup) itemView.findViewById(R.id.contact_item);
        }

        public void setItem(Context context, Contact item) {
            mContact = item;
            String text = item.getName();
            if (item.getAge() > 0) {
                text += ", " + String.valueOf(item.getAge());
            }
            mName.setText(text);
            mMessages.setText(context.getResources().getQuantityString
                    (R.plurals.messages, item.getMessageCount(), item.getMessageCount()));

            if (!item.getPhoto().isEmpty()) {
                Picasso.with(context).load(item.getPhoto()).into(mPhoto);
            } else {
                mPhoto.setImageResource(R.drawable.no_photo_man_small);
            }

            if (mListener != null) {
                mContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onShowDetailContact(mContact);
                    }
                });
            }
        }
    }

    public ContactsAdapter(Context context, Listener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);
        return new ViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contact contact = mContacts.get(position);
        holder.setItem(mContext, contact);
        if (position == mContacts.size() - 1 && mListener != null) {
            mListener.onReachedFloor();
        }
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public void replaceItems(List<Contact> newContacts) {
        mContacts = newContacts;
        notifyDataSetChanged();
    }

    public void addItems(@NonNull List<Contact> newContacts) {
        if (!newContacts.isEmpty()) {
            mContacts.addAll(newContacts);
            notifyDataSetChanged();
        }
    }
}