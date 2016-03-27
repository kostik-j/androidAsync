package com.example.kj.myapplication.ui.albums.create;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.kj.myapplication.R;

public class NewAlbumDialogFragment extends DialogFragment
        implements DialogInterface.OnClickListener{

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        return builder.setTitle(R.string.new_album)
                .setView(inflater.inflate(R.layout.fragment_new_album, null))
                .setPositiveButton(R.string.add, this)
                .setNegativeButton(R.string.cancel, this)
                .create();
    }
}