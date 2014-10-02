package com.ies.izv.practicalistview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;


public class Main extends Activity {

    /**********************************************************************************************/

    private Adaptador ad;
    private ArrayList<Alumno> listaAlumnos = new ArrayList<Alumno>();
    private ListView lv;
    private OperacionDialogo opAdd, opEdit;

    /**********************************************************************************************/

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        switch(item.getItemId()) {
            case R.id.action_delete:
                alumnoDelete(index);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciar();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            mostrarDialogo(null,R.string.dialogo_titulo_add,opAdd);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**********************************************************************************************/

    private void alumnoAdd(Alumno a){
        listaAlumnos.add(a);
        ordenar();
    }

    private void alumnoDelete(int index){
        listaAlumnos.remove(index);
        ordenar();
    }

    private void alumnoEdit(Alumno original, Alumno editado){
        original.set(editado);
        ordenar();
    }

    private void asignarOperaciones(){
        opAdd = new OperacionDialogo() {
            @Override
            public void operacion(View v, Alumno a) {
                EditText et1, et2;
                et1 = (EditText) v.findViewById(R.id.etGrupo);
                et2 = (EditText) v.findViewById(R.id.etNombre);
                RadioGroup rg;
                rg = (RadioGroup) v.findViewById(R.id.rgSexo);
                int checked = rg.getCheckedRadioButtonId();
                Alumno alumno;
                if(checked==R.id.rbFemenino){
                    alumno = new Alumno(et2.getText().toString(), et1.getText().toString(),true);
                    alumnoAdd(alumno);
                } else if(checked==R.id.rbMasculino){
                    alumno = new Alumno(et2.getText().toString(), et1.getText().toString(),false);
                    alumnoAdd(alumno);
                }
            }
        };
        opEdit = new OperacionDialogo() {
            @Override
            public void operacion(View v, Alumno a) {
                EditText et1, et2;
                et1 = (EditText) v.findViewById(R.id.etGrupo);
                et2 = (EditText) v.findViewById(R.id.etNombre);
                RadioGroup rg;
                rg = (RadioGroup) v.findViewById(R.id.rgSexo);
                int checked = rg.getCheckedRadioButtonId();
                Alumno alumno;
                if(checked==R.id.rbFemenino){
                    alumno = new Alumno(et2.getText().toString(), et1.getText().toString(),true);
                    alumnoEdit(a,alumno);
                } else if(checked==R.id.rbMasculino){
                    alumno = new Alumno(et2.getText().toString(), et1.getText().toString(),true);
                    alumnoEdit(a,alumno);
                }
            }
        };
    }

    private void iniciar(){
        lv = (ListView) findViewById(R.id.lvLista);
        iniciarAlumnos();
        ad = new Adaptador(this,R.layout.listview_item,listaAlumnos);
        lv.setAdapter(ad);
        registerForContextMenu(lv);
        asignarOperaciones();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Alumno alumno = (Alumno)lv.getItemAtPosition(i);
                mostrarDialogo(alumno, R.string.dialogo_titulo_edit, opEdit);
            }
        });
    }

    private void iniciarAlumnos(){
        Alumno a;
        a = new Alumno("maría","2 dam", true);
        listaAlumnos.add(a);
        a = new Alumno("juana","2 dam", true);
        listaAlumnos.add(a);
        a = new Alumno("pepe","2 dam", false);
        listaAlumnos.add(a);
        a = new Alumno("luisa","2 daw", true);
        listaAlumnos.add(a);
        a = new Alumno("paco","2 daw", false);
        listaAlumnos.add(a);
        a = new Alumno("juan","2 daw", false);
        listaAlumnos.add(a);
        Collections.sort(listaAlumnos);
    }

    private void mostrarControles(View vista, Alumno alumno){
        if(alumno!=null){
            EditText et1, et2;
            et1 = (EditText) vista.findViewById(R.id.etGrupo);
            et2 = (EditText) vista.findViewById(R.id.etNombre);
            RadioGroup rg;
            rg = (RadioGroup) vista.findViewById(R.id.rgSexo);
            et1.setText(alumno.getGrupo());
            et2.setText(alumno.getNombre());
            if(alumno.isSexoFemenino()){
                rg.check(R.id.rbFemenino);
            } else {
                rg.check(R.id.rbMasculino);
            }
        }
    }

    private void mostrarDialogo(final Alumno alumno, int titulo, final OperacionDialogo od){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(titulo);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View vista = inflater.inflate(R.layout.menu_altaedicion, null);
        mostrarControles(vista, alumno);
        alert.setView(vista);
        alert.setPositiveButton(R.string.dialogo_boton_aceptar,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        od.operacion(vista, alumno);
                    }
                });
        alert.setNegativeButton(R.string.dialogo_boton_cancelar, null);
        alert.show();
    }

    private void ordenar(){
        Collections.sort(listaAlumnos);
        ad.notifyDataSetChanged();
    }

    private void tostada(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    /**********************************************************************************************/

    static interface OperacionDialogo{
        public void operacion(View v, Alumno a);
    }
}