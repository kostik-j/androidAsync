package com.example.kj.myapplication.ui.albums;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.kj.myapplication.R;

public class NewAlbumDialogFragment extends DialogFragment
        implements DialogInterface.OnClickListener {

    public interface Listener {
        void onCreateAlbum(String name);
    }

    private Listener mListener;
    private View mView;

    public NewAlbumDialogFragment setListener(Listener listener) {
        mListener = listener;

        return this;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (
            which == DialogInterface.BUTTON_POSITIVE
            && mListener != null
            && mView != null
        ) {
            final EditText tvAlbumName = (EditText)mView.findViewById(R.id.album_name);
            mListener.onCreateAlbum(tvAlbumName.getText().toString());
        }
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mView = inflater.inflate(R.layout.fragment_new_album, null);

        return builder.setTitle(R.string.new_album)
                .setCancelable(false)
                .setView(mView)
                .setPositiveButton(R.string.add, this)
                .setNegativeButton(R.string.cancel, this)
                .create();
    }
}