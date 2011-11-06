package android.project.SquareGame;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

/**
 * Classe che gestisce e visualizza la lista dei record
 * 
 * @author Federico De Meo
 * 
 */
public class ScoreList extends ListActivity {

    /** Riferimento al DataBase */
    private DataBase db = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = DataBase.getInstances(this);
        db.open();
        loadValue();
        
    }
    /**
     * Caricatore dei valori
     */
    private void loadValue(){
        try {
            // Prendo la lista dei record
            Cursor cursor = db.getAllRecord();
            // Inizio gestione del cursore con i record
            startManagingCursor(cursor);
            // Vettore di stringhe con i record
            String[] VISUAL = new String[cursor.getCount()];
            // Estraggo i recordo dal Cursor
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                VISUAL[i] = cursor.getString(0) + " - " + cursor.getString(1)
                        + "%";

            }
            setListAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.test_list_item, VISUAL));
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Errore "+e, Toast.LENGTH_LONG).show();
        }
    }
    /**
     * @see android.app.Activity#onCreateOptionsMenu(Menu)
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Reset Score");
        return true;
    }

    /**
     * @see android.app.Activity#onOptionsItemSelected(MenuItem)
     */
    public boolean onOptionsItemSelected(MenuItem M) {
        switch (M.getItemId()) {
            case 1:
                // Eliminazione dei record
                try{
                db.svutaRecord();
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), "Errore "+e, Toast.LENGTH_LONG).show();
                }
                loadValue();
        }
        return true;
    }
}
