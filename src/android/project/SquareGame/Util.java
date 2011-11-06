package android.project.SquareGame;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.widget.TextView;
import android.widget.Toast;

public class Util {
    
    /**
     * Metodo che verifica i prossimi quadrati validi
     * 
     * @param riga, identifica la riga del quadrato cliccato
     * @param colonna, identifica la colonna del quadrato cliccato
     * @param quadrato, matrice delle selezioni
     * @param a, riferimento all'activity per le modifiche video
     * @param show, 1 se devo visualizzare i next 0 altrimenti
     * @return 0 se c' gameOver, >0 altrimenti
     */
    public static int next(int riga, int colonna, int[][] quadrato,
            Activity a,int show) {
        
        int colore=Color.BLACK;
        int result=0;
        Boolean check = true;
        if(show == 1 && Def.showNext){
            colore = Color.argb(75, 75, 0,130);
        }
        if(show == 0)
            check=false;
        
        /* Diagonale Alto a Sinistra */
        if ((riga - 2) >= 0 && (colonna - 2) >= 0 && quadrato[riga - 2][colonna - 2] != -1){
            ((TextView) a.findViewById(quadrato[riga - 2][colonna - 2])).setBackgroundColor(colore);
            ((TextView) a.findViewById(quadrato[riga - 2][colonna - 2])).setWidth(10);
            ((TextView) a.findViewById(quadrato[riga - 2][colonna - 2])).setTag(check);
            result++;
        }
        /* Diagonale Basso a Destra */
        if ((riga + 2) < Def.DIMENSIONE && (colonna + 2) < Def.DIMENSIONE && quadrato[riga + 2][colonna + 2] != -1){
            ((TextView) a.findViewById(quadrato[riga + 2][colonna + 2])).setBackgroundColor(colore);
            ((TextView) a.findViewById(quadrato[riga + 2][colonna + 2])).setTag(check);
            result++;
        }
        /* Diagonale Alto a Destra */
        if ((riga - 2) >= 0 && (colonna + 2) < Def.DIMENSIONE && quadrato[riga - 2][colonna + 2] != -1){
            ((TextView) a.findViewById(quadrato[riga - 2][colonna + 2])).setBackgroundColor(colore);
            ((TextView) a.findViewById(quadrato[riga - 2][colonna + 2])).setTag(check);
            result++;
        }
        /* Diagonale Basso a Sinistra */
        if ((riga + 2) < Def.DIMENSIONE && (colonna - 2) >= 0 && quadrato[riga + 2][colonna - 2] != -1){
            ((TextView) a.findViewById(quadrato[riga + 2][colonna - 2])).setBackgroundColor(colore);
            ((TextView) a.findViewById(quadrato[riga + 2][colonna - 2])).setTag(check);
            result++;
        }
        /* Dritto a Destra */
        if ((colonna + 3) < Def.DIMENSIONE && quadrato[riga][colonna + 3] != -1){
            ((TextView) a.findViewById(quadrato[riga][colonna + 3])).setBackgroundColor(colore);
            ((TextView) a.findViewById(quadrato[riga][colonna + 3])).setTag(check);
            result++;
        }
        /* Dritto a Sinistra */
        if ((colonna - 3) >= 0 && quadrato[riga][colonna - 3] != -1){
            ((TextView) a.findViewById(quadrato[riga][colonna - 3])).setBackgroundColor(colore);
            ((TextView) a.findViewById(quadrato[riga][colonna - 3])).setTag(check);
            result++;
        }
        /* Dritto in basso */
        if ((riga - 3) >= 0 && quadrato[riga - 3][colonna] != -1){
            ((TextView) a.findViewById(quadrato[riga - 3][colonna])).setBackgroundColor(colore);
            ((TextView) a.findViewById(quadrato[riga - 3][colonna])).setTag(check);
            result++;
        }
        /* Dritto in alto */
        if ((riga + 3) < Def.DIMENSIONE && quadrato[riga + 3][colonna] != -1){
            ((TextView) a.findViewById(quadrato[riga + 3][colonna])).setBackgroundColor(colore);
            ((TextView) a.findViewById(quadrato[riga + 3][colonna])).setTag(check);
            result++;
        }
        return result;

    }
    
}
