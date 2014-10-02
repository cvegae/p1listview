package com.ies.izv.practicalistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adaptador extends ArrayAdapter<Alumno> {

    private static LayoutInflater inflater;
    private ArrayList<Alumno> lista;
    private Context contexto;
    private int recursoLayoutDelItem;

    public Adaptador(Context context, int resource, ArrayList<Alumno> objects) {
        super(context, resource, objects);
        this.contexto = context;
        this.lista = objects;
        this.recursoLayoutDelItem = resource;
        inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position,View convertView,ViewGroup parent) {
        View v = null;
        if (convertView == null) {
            v = inflater.inflate(this.recursoLayoutDelItem, null);
        } else {
            v = convertView;
        }
        TextView tv1, tv2, tv3;
        tv1 = (TextView) v.findViewById(R.id.tv1);
        tv2 = (TextView) v.findViewById(R.id.tv2);
        tv3 = (TextView) v.findViewById(R.id.tv3);
        ImageView iv;
        iv = (ImageView) v.findViewById(R.id.ivImagen);
        Alumno a;
        a = lista.get(position);
        tv1.setText(a.getGrupo());
        tv2.setText(a.getNombre());
        tv3.setText(a.getSexo());
        if(a.isSexoFemenino()){
            iv.setImageResource(R.drawable.girl);
        } else{
            iv.setImageResource(R.drawable.boy);
        }
        return v;
    }
}