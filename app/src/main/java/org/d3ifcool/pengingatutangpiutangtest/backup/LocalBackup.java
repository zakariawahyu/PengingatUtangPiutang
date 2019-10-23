package org.d3ifcool.pengingatutangpiutangtest.backup;

import android.app.AlertDialog;
import android.os.Environment;
import android.text.InputType;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import org.d3ifcool.pengingatutangpiutangtest.Aktivitas;
import org.d3ifcool.pengingatutangpiutangtest.R;
import org.d3ifcool.pengingatutangpiutangtest.backup.Permission;
import org.d3ifcool.pengingatutangpiutangtest.MainActivity;
import org.d3ifcool.pengingatutangpiutangtest.data.UtangPiutangDbHelper;
import org.d3ifcool.pengingatutangpiutangtest.data.UtangPiutangProvider;

import java.io.File;

public class LocalBackup {
    private Aktivitas aktivitas;

    private Permission permission;

    public LocalBackup(Aktivitas aktivitas) {
        this.aktivitas = aktivitas;
    }

    public void performBackup(final UtangPiutangDbHelper db, final String outFileName) {
        permission = new Permission();

        permission.verifyStoragePermissions(aktivitas);

        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + aktivitas.getResources().getString(R.string.db));

        boolean success = true;
        if (!folder.exists())
            success = folder.mkdirs();
        if (success) {

            AlertDialog.Builder builder = new AlertDialog.Builder(aktivitas);
            builder.setTitle("Backup Name");
            final EditText input = new EditText(aktivitas);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);
            builder.setPositiveButton("Save", (dialog, which) -> {
                String m_Text = input.getText().toString();
                String out = outFileName + m_Text + ".db";
                db.backup(out);
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        } else
            Toast.makeText(aktivitas, "Unable to create directory. Retry", Toast.LENGTH_SHORT).show();
    }

    //ask to the user what backup to restore
    public void performRestore(final UtangPiutangDbHelper db) {
        permission = new Permission();
        permission.verifyStoragePermissions(aktivitas);

        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + aktivitas.getResources().getString(R.string.db));
        if (folder.exists()) {

            final File[] files = folder.listFiles();

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(aktivitas, android.R.layout.select_dialog_item);
            for (File file : files)
                arrayAdapter.add(file.getName());

            AlertDialog.Builder builderSingle = new AlertDialog.Builder(aktivitas);
            builderSingle.setTitle("Restore:");
            builderSingle.setNegativeButton(
                    "cancel",
                    (dialog, which) -> dialog.dismiss());
            builderSingle.setAdapter(
                    arrayAdapter,
                    (dialog, which) -> {
                        try {
                            db.importDB(files[which].getPath());
                        } catch (Exception e) {
                            Toast.makeText(aktivitas, "Unable to restore. Retry", Toast.LENGTH_SHORT).show();
                        }
                    });
            builderSingle.show();
        } else
            Toast.makeText(aktivitas, "Backup folder not present.\nDo a backup before a restore!", Toast.LENGTH_SHORT).show();
    }
}
