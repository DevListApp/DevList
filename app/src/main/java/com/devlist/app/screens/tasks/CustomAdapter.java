package com.devlist.app.screens.tasks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devlist.app.R;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<String> localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.text_list);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    // Inicializa o conjunto de dados do Adapter
    public CustomAdapter(List<String> dataSet){
        this.localDataSet = dataSet;
    }

    @NonNull
    @Override
    //Cria uma nova view
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Cria uma nova view, que define o item da view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    //Substitui o conteúdo de uma visualização(invocando o gerenciador de layout)
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        String item = localDataSet.get(position);
        holder.textView.setText(item);

//        holder.getTextView().setText(localDataSet[position]);
    }

    @Override
    // Retorna o tamanho do seu conjunto de dados
    public int getItemCount() {
        return localDataSet.size();
    }


}

