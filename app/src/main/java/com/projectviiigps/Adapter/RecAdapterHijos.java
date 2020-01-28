package com.projectviiigps.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.projectviiigps.Data.dataHijos;
import com.projectviiigps.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecAdapterHijos extends RecyclerView.Adapter<RecAdapterHijos.RecViewHolder>{
    private List<dataHijos> list;
    Context context;
    private ItemClickListener clickListener;

    public RecAdapterHijos(List<dataHijos> list,Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecAdapterHijos.RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.itemhijos, parent, false);
        return new RecAdapterHijos.RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecAdapterHijos.RecViewHolder holder, int position) {
        dataHijos dt= list.get(position);

        String imageLink="https://i.pinimg.com/originals/00/d2/f3/00d2f37716030112ff0e14e64c7f90e4.png";

        Picasso.with(context).load(imageLink).into(holder.linkimagen);

        holder.bind(dt);
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class RecViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nombre,edad, direccion,tiposangre,enfermedad,alergia;
        private ImageView linkimagen;
        private View subItem;

        public RecViewHolder(View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.nombreHijo);
            edad = itemView.findViewById(R.id.edadHijo);
            direccion = itemView.findViewById(R.id.direccion);
            tiposangre = itemView.findViewById(R.id.tiposangrehijo);
            enfermedad = itemView.findViewById(R.id.enfermedadhijo);
            alergia = itemView.findViewById(R.id.alergiahijo);
            linkimagen = itemView.findViewById(R.id.linkimagenHijo);
            subItem = itemView.findViewById(R.id.sub_itemHijo);
        }

        private void bind(dataHijos dt) {
            nombre.setText(dt.getNombre());
            edad.setText("Edad: "+dt.getEdad());
            direccion.setText("Direccion: "+dt.getDireccion());
            tiposangre.setText("Tipo de sangre: "+dt.getTiposangre());
            enfermedad.setText("Enfermedad: "+dt.getEnfermedad());
            alergia.setText("Alergia: "+dt.getAlergia());
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onClick(v, getAdapterPosition());
        }
    }
}
