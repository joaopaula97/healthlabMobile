package br.unibh.sdm.healthlabexames.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.unibh.sdm.healthlabexames.entidades.Exame;

public class ListaExamesAdapter extends BaseAdapter {

    private final List<Exame> lista1;
    private final Context context;

    public ListaExamesAdapter(Context context, List<Exame> lista1) {
        this.context = context;
        this.lista1 = lista1;

    }

    @Override
    public int getCount() {
        return lista1.size();


    }

    @Override
    public Object getItem(int position) {
        return lista1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lista1.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context)
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        Exame objeto = lista1.get(position);
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(objeto.getMatricula());
        return view;
    }

}
