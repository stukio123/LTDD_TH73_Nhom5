package com.example.ecommerciandroiapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerciandroiapp.Model.BookSpecificationModel;
import com.example.ecommerciandroiapp.R;

import java.util.List;

public class BookSpecificationAdapter extends RecyclerView.Adapter<BookSpecificationAdapter.ViewHolder> {

    private List<BookSpecificationModel> bookSpecificationModelList;

    public BookSpecificationAdapter(List<BookSpecificationModel> bookSpecificationModelList) {
        this.bookSpecificationModelList = bookSpecificationModelList;
    }


    @NonNull
    @Override
    public BookSpecificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_specification_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookSpecificationAdapter.ViewHolder holder, int position) {
        String title = bookSpecificationModelList.get(position).getTitle();
        String value = bookSpecificationModelList.get(position).getValue();
        holder.setSpecification(title,value);
    }

    @Override
    public int getItemCount() {
        return bookSpecificationModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title,value;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            value = itemView.findViewById(R.id.tv_value);
        }
        private void setSpecification(String specificationTitle,String specificationValue){
            title.setText(specificationTitle);
            value.setText(specificationValue);
        }
    }
}
