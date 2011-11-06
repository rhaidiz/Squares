package android.project.SquareGame;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

public class Options extends android.app.Activity{
	
	private static Activity main=null;
	private DataBase db;
	
	@SuppressWarnings("unchecked")
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);
        db = DataBase.getInstances(this);
        main = this.getParent();

        final Spinner dimensioniTable = (Spinner)findViewById(R.id.tableDimension);
        final CheckBox ck = (CheckBox)findViewById(R.id.showNext);
        final Button SaveBtn = (Button)findViewById(R.id.SaveBtn);
        
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,		// resource ID del layout da usare per li spinner
                    new String[] { "5", "10" });
        dimensioniTable.setAdapter(spinnerArrayAdapter);

        
        dimensioniTable.setSelection((Def.DIMENSIONE/5)-1);
        ck.setChecked(Def.showNext);
        
        
        SaveBtn.setOnClickListener(new View.OnClickListener(){

			public void onClick(View v) {
				int next = 0;
			    Def.showNext = ck.isChecked();
				Def.DIMENSIONE = Integer.parseInt(dimensioniTable.getSelectedItem().toString());
				// salvataggio opzioni nel DataBase
				if(Def.showNext)
				    next = 1;
				try{
				db.updateOptions(Def.DIMENSIONE, next);
				}catch(Exception e){
				    ((TextView)findViewById(R.id.debug)).setText("ERRORE "+e);
				}
				finish();
			}
        	
        	
        });
        
	}
}
