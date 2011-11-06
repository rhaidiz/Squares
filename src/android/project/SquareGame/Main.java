package android.project.SquareGame;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Main class di avvio del gioco
 * 
 * @author Federico De Meo
 * 
 */
public class Main extends Activity {

    /** Altezza del quadrato */
    private int height = 0;
    /** Larghezza del quadrato */
    private int width = 0;
    /** Controlla se sto premendo il primo quadrato */
    private int first;
    /** Il prossimo valore numerico dei quadrati */
    private int next;
    /** Matrice delle posizioni giˆ usate */
    private int quadrato[][];
    /** Indicatori delle opzioni del menu */
    private final int MENU_NEW_GAME = 1;
    private final int MENU_OPTIONS = 2;
    private final int MENU_PUNTEGGI = 3;
    private final int MENU_QUIT = 4;
    /** Totale dei numeri da riempire */
    private int totale = 0;
    /** Identificatore del DataBase */
    private DataBase db;
    /** Memorizza la TextView attualmente selezionata */
    private TextView attuale;
    /** Identificatore dell'activity del Main per la modifica dei quadrati */
    private Activity activityMain;
    /** Identifica la riga della selezione */
    private int riga;
    /** Identifica la colonna della seleziona */
    private int colonna;
    /** Variabile di controllo per il GameOver */
    private int gameOver = 1;
    /** Dimensioni attuali e showNext attuale, serve per sapere quando cambiano */
    private int dimAttuali = 0;
    private boolean nextAttuale = false;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dimAttuali = Def.DIMENSIONE;
        nextAttuale = Def.showNext;
        setContentView(R.layout.main);
        // Inizzializzo il quadrato di gioco
        initMainTable(Def.DIMENSIONE);
        activityMain = this;
        db = DataBase.getInstances(this);
        db.open();

        // db.dropTable(); // PER DEBUG
        // db.createTable(); 

        // Recupero le opzioni dal DB e se setto
        try {
            Cursor cur = db.getOptions();
            startManagingCursor(cur);
            cur.moveToFirst();
            Def.DIMENSIONE = cur.getInt(0);
            if (cur.getInt(1) == 1)
                Def.showNext = true;

        } catch (Exception e) {

        }
    }

    /**
     * Inizializza la tabella grafica
     * 
     * @param dim, lato del quadrato
     */
    public void initMainTable(int dim) {

        final TableLayout mainTable = (TableLayout) findViewById(R.id.MainTable);
        TableRow row = null;
        TextView text = null;
        // Layout delle righe
        TableRow.LayoutParams layou = new TableRow.LayoutParams();
        int cella = 0;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        height = dm.heightPixels;
        width = (dm.widthPixels - (Def.DIMENSIONE * 2));
        width = width / Def.DIMENSIONE;
        height = (height - 130) / Def.DIMENSIONE;
        quadrato = new int[Def.DIMENSIONE][Def.DIMENSIONE];

        // elimino tutte le view nella tabella
        mainTable.removeAllViews();
        // settiamo il layout con margini scostati di 1
        layou.setMargins(1, 1, 1, 1);

        totale = dim * dim;
        next = 1;
        first = 0;

        mainTable.setBackgroundColor(Color.TRANSPARENT);
        for (int i = 0; i < dim; i++) {
            row = new TableRow(this);
            row.setBackgroundColor(Color.TRANSPARENT);
            for (int j = 0; j < dim; j++) {
                text = new TextView(this);
                text.setId(cella);
                quadrato[i][j] = cella;
                text.setTag(false);
                text.setText("?");
                cella++;
                text.setWidth(width);
                text.setBackgroundColor(Color.BLACK);
                // to set a different background
                //text.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon ));
                text.setHeight(height);
                text.setClickable(true);
                text.setGravity(Gravity.CENTER);
                text.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View view) {
                        int id = view.getId();
                        clickControl(id, view);
                    }
                });
                row.addView(text, layou);
            }
            mainTable.setGravity(Gravity.CENTER);
            mainTable.addView(row);

        }

    }

    /**
     * Controlla che il quadrato premuto sia valido, che non si sia gameover e
     * mostra i prossimi quadrati validi.
     * 
     * @param id del quadrato premuto
     * @param v, View che identifica il quadrato premuto
     */
    private void clickControl(int id, View v) {
        // Caso in cui ho fatto la prima pressione
        if (first == 0) {
            riga = (id / Def.DIMENSIONE);
            colonna = (id % Def.DIMENSIONE);
            // memorizzo la textview attuale
            attuale = (TextView) v;
            ((TextView) v).setText("" + next);
            ((TextView) v).setBackgroundColor(Def.curColor);
            ((TextView) v).setTextColor(Color.BLACK);
            ((TextView) v).setWidth(width);
            // Setto che  stata fatta la prima pressione
            first++;
            // Aumento con il prossimo numero
            next++;
            // Diminuisco il totale che manda alla fine
            totale--;
            // Segno la posizione appena presa
            quadrato[riga][colonna] = -1;
            // Mostro i prossimi quadrati validi
            Util.next(riga, colonna, quadrato, activityMain, 1);
        } else {
            // Per prima cosa controllo di cliccare un quadrato valido, poi
            // penser˜ alla colorazione
            Boolean check = (Boolean) ((TextView) v).getTag();
            if (check) {
                // Nasconto i vecchi quadrati validi
                Util.next(riga, colonna, quadrato, this, 0);
                // Aggiorno il valore di riga e colonna
                riga = (id / Def.DIMENSIONE);
                colonna = (id % Def.DIMENSIONE);
                // cancello la selezione della vecchia textview
                attuale.setBackgroundColor(Color.TRANSPARENT);
                attuale.setTextColor(Color.WHITE);
                // memorizzo la nuova attuale e le do il colore di selezione
                attuale = (TextView) v;
                attuale.setBackgroundColor(Def.curColor);
                attuale.setTextColor(Color.BLACK);
                attuale.setText("" + next);
                // Segno la posizione appena presa
                quadrato[riga][colonna] = -1;
                // Mostro i prossimi quadrati validi
                gameOver = Util.next(riga, colonna, quadrato, activityMain, 1);
                next++;
                totale--;
            }
        }

        if (totale == 0) {
            // WINNET
            showSaveScore(1);
        } else {
            // Controllo GameOver
            if (gameOver == 0) {
                // GAME OVER
                showSaveScore(0);
                gameOver = 1;
            }
        }
    }

    /** Aggiungo il menu */
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_NEW_GAME, 0, "New Game");
        menu.add(0, MENU_OPTIONS, 0, "Options");
        menu.add(0, MENU_PUNTEGGI, 0, "Score");
        menu.add(0, MENU_QUIT, 0, "Exit");
        return true;
    }

    /** Metodo per la selezione delle voci del menu */
    public boolean onOptionsItemSelected(MenuItem M) {
        switch (M.getItemId()) {
            case 1:
                // Nuova partita
                initMainTable(Def.DIMENSIONE);
                next = 1;
                first = 0;
                break;
            case 2:
                // Intent OPZIONI
                Intent i = new Intent(this, Options.class);
                startActivity(i);
                break;
            case 3:
                // Intent RECORD
                Intent j = new Intent(this, ScoreList.class);
                startActivity(j);
                break;
            case 4:
                // Chiusura programma
                this.finish();
                break;
        }
        return true;
    }

    /**
     * Chiamato al resume dell'Activity
     */
    public void onResume() {
        super.onResume();
        if (dimAttuali != Def.DIMENSIONE) {
            dimAttuali = Def.DIMENSIONE;
            initMainTable(Def.DIMENSIONE);
        } else if (nextAttuale != Def.showNext) {
            nextAttuale = Def.showNext;
            if (first != 0)
                Util.next(riga, colonna, quadrato, activityMain, 1);
        }
    }

    /**
     * Mostra il salvataggio del punteggio.
     * 
     * @param i, punteggio ottenuto
     */
    public void showSaveScore(int i) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.save_record);

        TextView score = (TextView) dialog.findViewById(R.id.score);
        Button saveBtn = (Button) dialog.findViewById(R.id.saveBtn);
        final EditText name = (EditText) dialog.findViewById(R.id.saveName);

        saveBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try{
                db.insertRecord(name.getText().toString(),
                        ((next - 1) * 100) / (Def.DIMENSIONE * Def.DIMENSIONE));
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), "Errore "+e, Toast.LENGTH_LONG+Toast.LENGTH_LONG).show();
                    ((TextView)findViewById(R.id.debug)).setText("Error: "+e);
                }
                initMainTable(Def.DIMENSIONE);
                dialog.dismiss();
            }

        });

        score.setText("" + (next - 1));

        dialog.setCancelable(false);
        if (i == 0)
            dialog.setTitle("GAME OVER!!!");
        else
            dialog.setTitle("WINNER!!!");
        dialog.show();
    }
}