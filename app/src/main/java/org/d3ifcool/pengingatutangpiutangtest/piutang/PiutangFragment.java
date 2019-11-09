package org.d3ifcool.pengingatutangpiutangtest.piutang;

import android.content.ContentUris;
import androidx.loader.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.d3ifcool.pengingatutangpiutangtest.R;
import org.d3ifcool.pengingatutangpiutangtest.data.UtangPiutangContract;

public class PiutangFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    FloatingActionButton floatingActionButton;

    View emptyViewPiutang;
    ListView listViewPiutang;
    PiutangCursorAdapter mCursorAdapterPiutang;

    private static final int PIUTANG_LOADER = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_piutang, container, false);


        listViewPiutang = view.findViewById(R.id.list_piutang);
        emptyViewPiutang = view.findViewById(R.id.empty_view_piutang);
        listViewPiutang.setEmptyView(emptyViewPiutang);

        mCursorAdapterPiutang = new PiutangCursorAdapter(getActivity(), null);
        listViewPiutang.setAdapter(mCursorAdapterPiutang);

        listViewPiutang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailPiutang.class);

                Uri currentPiutangkuUri = ContentUris.withAppendedId(UtangPiutangContract.UtangPiutangEntry.CONTENT_URI_PIUTANG, id);

                // Set the URI on the data field of the intent
                intent.setData(currentPiutangkuUri);

                startActivity(intent);
            }
        });

        floatingActionButton = view.findViewById(R.id.fb_add_piutang);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TambahPiutang.class);
                startActivity(i);
            }
        });

        getLoaderManager().initLoader(PIUTANG_LOADER, null, this);

        return view;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {
                UtangPiutangContract.UtangPiutangEntry._ID,
                UtangPiutangContract.UtangPiutangEntry.KEY_NAMA,
                UtangPiutangContract.UtangPiutangEntry.KEY_JUMLAH,
                UtangPiutangContract.UtangPiutangEntry.KEY_DESKRIPSI,
                UtangPiutangContract.UtangPiutangEntry.KEY_DATE,
                UtangPiutangContract.UtangPiutangEntry.KEY_TIME,
                UtangPiutangContract.UtangPiutangEntry.KEY_REPEAT,
                UtangPiutangContract.UtangPiutangEntry.KEY_REPEAT_NO,
                UtangPiutangContract.UtangPiutangEntry.KEY_REPEAT_TYPE,
                UtangPiutangContract.UtangPiutangEntry.KEY_ACTIVE,
                UtangPiutangContract.UtangPiutangEntry.KEY_JENIS,
                UtangPiutangContract.UtangPiutangEntry.KEY_STATUS

        };

        return new CursorLoader(getActivity(),   // Parent activity context
                UtangPiutangContract.UtangPiutangEntry.CONTENT_URI_PIUTANG,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mCursorAdapterPiutang.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mCursorAdapterPiutang.swapCursor(null);
    }
}
