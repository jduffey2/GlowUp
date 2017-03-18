package com.example.jduff.glowup;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Set;

/**
 * Ring_selection_activity - the main activity that allows a user to select a ring to modify
 * @author Jason Duffey
 * @version 1.0 - 01/2017
 */
public class Ring_selection_activity extends AppCompatActivity {

    private Base lightBase;

    public final static String BASE = "com.example.jduff.glowup.BASE";
    public final static String RING = "com.example.jduff.glowup.RING";
    private String m_text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring_selection_activity);

        //Get the Base that is passed from the previous activity
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        //Check to see if anything has been passed
        if(extras != null && extras.containsKey(BASE)) {
            lightBase = (Base)intent.getSerializableExtra(BASE);
        }
        //If not create a new base and add three rings to it
        else {
            lightBase = new Base();
            LightGroup outer = new LightGroup(BaseRingEnum.OUTER);
            LightGroup mid = new LightGroup(BaseRingEnum.MIDDLE);
            LightGroup inner = new LightGroup(BaseRingEnum.INNER);
            lightBase.addGroup(outer);
            lightBase.addGroup(mid);
            lightBase.addGroup(inner);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.patternactionmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.saveMenu:
                saveDialog();
                return true;
            case R.id.openMenu:
                openDialog();
                return true;
            case R.id.deleteMenu:
                deleteDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /****************************************************************************************/
    // These methods are the event handlers for the buttons for editing a ring
    /****************************************************************************************/
    public void selectRingOuter(View view) {
        openLightGroupActivity(BaseRingEnum.OUTER);
    }

    public void selectRingMiddle(View view) {
        openLightGroupActivity(BaseRingEnum.MIDDLE);
    }

    public void selectRingInner(View view) {
        openLightGroupActivity(BaseRingEnum.INNER);
    }

    /*****************************************************************************************/

    //Start the Element_selection activity with the selected ring
    private void openLightGroupActivity(BaseRingEnum ring) {
        Intent intent = new Intent(this, Element_selection.class);
        intent.putExtra(BASE, lightBase);
        intent.putExtra(RING, ring);
        startActivity(intent);
    }

    //Send the data to the base
    public void uploadData(View view) {
        Log.d("TEST",lightBase.toJSON());
    }

    //Functionality for saving a pattern, creates a dialog to select the name, then saves to db
    private void saveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pattern Name");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_text = input.getText().toString();
                ((TextView)findViewById(R.id.nameLbl)).setText(m_text);
                save();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void save() {
        PatternDBHelper helper = new PatternDBHelper(this.getBaseContext());
        SQLiteDatabase db = helper.getWritableDatabase();

        //Save the new pattern to the patterns table
        ContentValues values = new ContentValues();
        values.put(PatternDBContract.PatternsTable.COLUMN_NAME, m_text);
        long newID = db.insert(PatternDBContract.PatternsTable.TABLE_NAME, null, values);
        lightBase.setPatternID(newID);

        //Save the all the elements to the elements table
        for(int i = 0; i < 3; i++) {
            LightGroup group = lightBase.getGroup(i);
            int j = 0;
            for (SequenceElement element: group.getPattern() ) {
                ContentValues val = new ContentValues();
                val.put(PatternDBContract.ElementTable.COLUMN_PATTERN_ID, newID);
                val.put(PatternDBContract.ElementTable.COLUMN_INDEX_ID, j);
                val.put(PatternDBContract.ElementTable.COLUMN_RING_ID, i);
                val.put(PatternDBContract.ElementTable.COLUMN_RED, element.getRedComponent());
                val.put(PatternDBContract.ElementTable.COLUMN_GREEN, element.getGreenComponent());
                val.put(PatternDBContract.ElementTable.COLUMN_BLUE, element.getBlueComponent());
                val.put(PatternDBContract.ElementTable.COLUMN_LENGTH, element.getLength());

                long rowID = db.insert(PatternDBContract.ElementTable.TABLE_NAME, null, val);
                j++;
            }
        }

        helper.close();
        db.close();
    }

    //Functionality for opening a previous pattern, shows the dialog to select which pattern then opens the pattern
    private void openDialog() {
        PatternDBHelper helper = new PatternDBHelper(this.getBaseContext());
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] patternProj = {
                PatternDBContract.PatternsTable._ID,
                PatternDBContract.PatternsTable.COLUMN_NAME
        };

        Cursor cursor = db.query(PatternDBContract.PatternsTable.TABLE_NAME, //Table
                patternProj, //Columns
                null,        //WHERE Columns
                null,        //WHERE Values
                null,        //GROUP
                null,        //FILTER
                null);       //SORT

        final CharSequence[] items = new CharSequence[cursor.getCount()];
        final int[] patternID = new int[cursor.getCount()];
        int i = 0;
        while(cursor.moveToNext()) {
            items[i] = cursor.getString(1);
            patternID[i] = cursor.getInt(0);
            i++;
        }
        cursor.close();
        cursor = null;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Pattern").setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                open(patternID[which]);
                ((TextView)findViewById(R.id.nameLbl)).setText(items[which]);
            }
        });

        helper.close();
        db.close();
        builder.show();
    }

    private void open(int pattern) {
        PatternDBHelper helper = new PatternDBHelper(this.getBaseContext());
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] elementProj = {
                PatternDBContract.ElementTable.COLUMN_PATTERN_ID,
                PatternDBContract.ElementTable.COLUMN_RING_ID,
                PatternDBContract.ElementTable.COLUMN_INDEX_ID,
                PatternDBContract.ElementTable.COLUMN_LENGTH,
                PatternDBContract.ElementTable.COLUMN_RED,
                PatternDBContract.ElementTable.COLUMN_GREEN,
                PatternDBContract.ElementTable.COLUMN_BLUE
        };
        
        String selection = PatternDBContract.ElementTable.COLUMN_PATTERN_ID + " = ?";
        String[] selectionArgs = {Integer.toString(pattern)};

        Cursor cursor = db.query(PatternDBContract.ElementTable.TABLE_NAME, //Table
                elementProj, //Columns
                selection,        //WHERE Columns
                selectionArgs,        //WHERE Values
                null,        //GROUP
                null,        //FILTER
                null);       //SORT

        while( cursor.moveToNext()) {
            int col = cursor.getColumnCount();
            Log.d("Element", "NEW ELEMENT");

            for (int i = 0; i < col; i++) {
                Log.d("Element", cursor.getColumnName(i) + ", " + cursor.getInt(i));
            }
        }

        helper.close();
        db.close();
        cursor.close();
    }

    //Functionality for deleting a pattern, opens a dialog for confiming deleting current pattern, then
    //deletes all data from db, and creates a new pattern to work with
    private void deleteDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Pattern").setMessage("Are you sure you want to delete this pattern?");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delete();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void delete() {
        //Check if previously saved,( patternID != -1)
        //if so delete all records from ElementTable and record from PatternTable
        //if not do nothing
        if(lightBase.getPatternID() != -1) {
            PatternDBHelper helper = new PatternDBHelper(this.getBaseContext());
            SQLiteDatabase db = helper.getReadableDatabase();

            //Delete from the Element Table
            String selection = PatternDBContract.ElementTable.COLUMN_PATTERN_ID + " LIKE ?";
            String[] selectionArgs = { Long.toString(lightBase.getPatternID())};

            db.delete(PatternDBContract.ElementTable.TABLE_NAME,selection,selectionArgs);

            //Delete from the Pattern Table
            String pSel = PatternDBContract.PatternsTable._ID;
            String[] pSelArgs = { Long.toString(lightBase.getPatternID())};

            db.delete(PatternDBContract.PatternsTable.TABLE_NAME, pSel, pSelArgs);

            helper.close();
            db.close();
        }

        //Get a new Base Object and reset everything
        lightBase = new Base();
        LightGroup outer = new LightGroup(BaseRingEnum.OUTER);
        LightGroup mid = new LightGroup(BaseRingEnum.MIDDLE);
        LightGroup inner = new LightGroup(BaseRingEnum.INNER);
        lightBase.addGroup(outer);
        lightBase.addGroup(mid);
        lightBase.addGroup(inner);
        ((TextView)findViewById(R.id.nameLbl)).setText(R.string.patternNameLbl);


    }


}
