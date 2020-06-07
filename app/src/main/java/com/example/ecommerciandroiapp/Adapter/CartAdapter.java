package com.example.ecommerciandroiapp.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerciandroiapp.Model.CartItemModel;
import com.example.ecommerciandroiapp.Model.CategoryModel;
import com.example.ecommerciandroiapp.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter {

    private List<CartItemModel> cartItemModelList ;

    public CartAdapter(List<CartItemModel> cartItemModelList) {
        this.cartItemModelList = cartItemModelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (cartItemModelList.get(position).getType()){
            case 0:
                return CartItemModel.CART_ITEM;
            case 1:
                return CartItemModel.TOTAL_AMOUNT;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case CartItemModel.CART_ITEM:
                View cartItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                return new CartItemViewHolder(cartItemView);
            case CartItemModel.TOTAL_AMOUNT:
                View cartTotalView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_total_amount_layout, parent, false);
                return new CartTotalAmountViewHolder(cartTotalView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (cartItemModelList.get(position).getType()){
            case CartItemModel.CART_ITEM:
                int resource = cartItemModelList.get(position).getBookImage();
                String title = cartItemModelList.get(position).getBookTitle();
                String bookPrice = cartItemModelList.get(position).getBookPrice();
                String bookPublisher = cartItemModelList.get(position).getBookPublisher();
                String bookCuttedPrice = cartItemModelList.get(position).getCuttedPrice();
                ((CartItemViewHolder)holder).setItemDetails(resource,title,bookPublisher,bookPrice,bookCuttedPrice);
                break;
            case CartItemModel.TOTAL_AMOUNT:
                String totalItem = cartItemModelList.get(position).getTotalItems();
                String totalItemsPrice = cartItemModelList.get(position).getTotalItemsPrice();
                String deliveryPrice = cartItemModelList.get(position).getDeliveryPrices();
                String totalAmount = cartItemModelList.get(position).getTotalAmount();
                String savedAmount = cartItemModelList.get(position).getSavedAmount();
                ((CartTotalAmountViewHolder)holder).setTotalAmount(totalItem,totalItemsPrice,deliveryPrice,totalAmount,savedAmount);
                break;
            default:
                return;
        }

    }

    @Override
    public int getItemCount() {
        return cartItemModelList.size();
    }

    class CartItemViewHolder extends RecyclerView.ViewHolder{

        private ImageView bookImage;
        private TextView bookTitle;
        private TextView bookPublisher;
        private TextView bookPrice;
        private TextView bookCuttedPrice;
        private TextView bookAmount;
        private ImageView up_btn;
        private ImageView down_btn;
        private ImageView del_btn;



        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            bookImage = itemView.findViewById(R.id.book_image);
            bookTitle = itemView.findViewById(R.id.cart_book_title);
            bookPublisher = itemView.findViewById(R.id.book_publisher);
            bookCuttedPrice = itemView.findViewById(R.id.book_cutted_prices);
            bookPrice = itemView.findViewById(R.id.book_prices);
            up_btn = itemView.findViewById(R.id.up_btn);
            down_btn = itemView.findViewById(R.id.down_btn);
            del_btn = itemView.findViewById(R.id.delete_btn);
            bookAmount = itemView.findViewById(R.id.tv_amount);
        }
        private void setItemDetails(int resource, String title, String publisher, String price, String cuttedPrice){
            bookImage.setImageResource(resource);
            bookTitle.setText(title);
            bookPrice.setText(price);
            bookPublisher.setText(publisher);
            bookCuttedPrice.setText(cuttedPrice);
            up_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bookAmount.setText(String.valueOf(Integer.parseInt(bookAmount.getText().toString())+1));
                }
            });
            down_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bookAmount.setText(String.valueOf(Integer.parseInt(bookAmount.getText().toString())-1));
                }
            });
            bookAmount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog quantityDialog = new Dialog(itemView.getContext());
                    quantityDialog.setContentView(R.layout.quantity_dialog);
                    quantityDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    quantityDialog.setCancelable(false);
                    final EditText quantityNumbers = quantityDialog.findViewById(R.id.quantity_number);
                    Button cancelBtn = quantityDialog.findViewById(R.id.cancel_button);
                    Button saveBtn = quantityDialog.findViewById(R.id.save_btn);

                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            quantityDialog.dismiss();
                        }
                    });

                    saveBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bookAmount.setText(quantityNumbers.getText());
                            quantityDialog.dismiss();
                        }
                    });
                    quantityDialog.show();
                }
            });
        }
    }

    class CartTotalAmountViewHolder extends RecyclerView.ViewHolder{

        private TextView totalItems;
        private TextView totalItemsPrice;
        private TextView deliveryPrice;
        private TextView totalAmount;
        private TextView savedAmount;

        public CartTotalAmountViewHolder(@NonNull View itemView) {
            super(itemView);
            totalItems = itemView.findViewById(R.id.total_items);
            totalItemsPrice = itemView.findViewById(R.id.total_items_prices);
            deliveryPrice = itemView.findViewById(R.id.delivery_price);
            totalAmount = itemView.findViewById(R.id.total_price);
            savedAmount = itemView.findViewById(R.id.saved_amount);
        }
        private void setTotalAmount(String totalItemText,String totalItemPriceText,String deliveryPriceText, String totalAmountText,String savedAmountText){
            totalItems.setText(totalItemText);
            totalItemsPrice.setText(totalItemPriceText);
            deliveryPrice.setText(deliveryPriceText);
            totalAmount.setText(totalAmountText);
            savedAmount.setText(savedAmountText);
        }
    }
}
