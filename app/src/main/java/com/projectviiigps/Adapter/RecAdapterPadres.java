package com.projectviiigps.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.projectviiigps.Data.dataPadre;
import com.projectviiigps.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class RecAdapterPadres extends RecyclerView.Adapter<RecAdapterPadres.RecViewHolder> {
    private List<dataPadre> list;
    Context context;

    public RecAdapterPadres(List<dataPadre> list,Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_padre, parent, false);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecViewHolder holder, int position) {
        dataPadre dt= list.get(position);

        String imageLink="https://img2.freepng.es/20180331/ixq/kisspng-father-parent-clip-art-fathers-day-5abffc2ef3d114.8442283415225313749987.jpg";

        Picasso.with(context).load(imageLink).into(holder.linkimagen);

        holder.bind(dt);

        holder.itemView.setOnClickListener(v -> {
            boolean expanded = dt.isExpanded();
            dt.setExpanded(!expanded);
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class RecViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre,telefono,direccion,correo;
        private ImageView linkimagen;
        private View subItem;

        public RecViewHolder(View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.nombrePadre);
            telefono = itemView.findViewById(R.id.telefono);
            direccion = itemView.findViewById(R.id.direccion);
            correo = itemView.findViewById(R.id.correo);
            linkimagen = itemView.findViewById(R.id.linkimagen);
            subItem = itemView.findViewById(R.id.sub_item);
        }

        private void bind(dataPadre dt) {
            boolean expanded = dt.isExpanded();

            //subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);
            nombre.setText(dt.getNombre());
            telefono.setText("Telefono: "+dt.getTelefono());
            direccion.setText("Direccion: "+dt.getDireccion());
            correo.setText(dt.getCorreo());
        }
    }
}
