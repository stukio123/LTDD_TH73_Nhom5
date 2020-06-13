package com.example.ecommerciandroiapp.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ecommerciandroiapp.BookDetailActivity;
import com.example.ecommerciandroiapp.Database.DataBaseQueries;
import com.example.ecommerciandroiapp.Model.CartItemModel;
import com.example.ecommerciandroiapp.Model.CategoryModel;
import com.example.ecommerciandroiapp.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter {

    private List<CartItemModel> cartItemModelList ;
    private int lastPosition = -1;
    private TextView cartTotalAmount;
    private boolean showDeleteBtn;

    public CartAdapter(List<CartItemModel> cartItemModelList,TextView cartTotalAmount, boolean showDeleteBtn) {
        this.cartItemModelList = cartItemModelList;
        this.cartTotalAmount = cartTotalAmount;
        this.showDeleteBtn = showDeleteBtn;
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
                String id = cartItemModelList.get(position).getBookID();
                String resource = cartItemModelList.get(position).getBookImage();
                String title = cartItemModelList.get(position).getBookTitle();
                String bookPrice = cartItemModelList.get(position).getBookPrice();
                String bookPublisher = cartItemModelList.get(position).getBookPublisher();
                String bookCuttedPrice = cartItemModelList.get(position).getCuttedPrice();
                ((CartItemViewHolder)holder).setItemDetails(id,resource,title,bookPublisher,bookPrice,bookCuttedPrice,position);
                break;
            case CartItemModel.TOTAL_AMOUNT:
                int totalItems = 0;
                int totalItemPrices = 0;
                String deliveryPrice;
                int totalAmount;
                int savedAmount = 0;
                for(int i = 0; i < cartItemModelList.size(); i++){
                    if(cartItemModelList.get(i).getType() == CartItemModel.CART_ITEM){
                        totalItems++;
                        totalItemPrices = totalItemPrices + Integer.parseInt(cartItemModelList.get(i).getBookPrice());
                    }
                }
                if(totalItemPrices > 500000){
                    deliveryPrice = "Free";
                    totalAmount = totalItemPrices;
                }else{
                    deliveryPrice = "30000";
                    totalAmount = totalItemPrices + 30000;
                }


                ((CartTotalAmountViewHolder)holder).setTotalAmount(totalItems,totalItemPrices,deliveryPrice,totalAmount,savedAmount);
                break;
            default:
                return;
        }
        if(lastPosition < position){
            Context context;
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fragment_fade_enter);
            holder.itemView.setAnimation(animation);
            lastPosition = position;
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
        private void setItemDetails(String bookId, String resource, String title, String publisher, String price, String cuttedPrice, final int position){
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.mipmap.sachtienganh)).into(bookImage);
            bookTitle.setText(title);
            bookPrice.setText(formatPrice(price));
            bookPublisher.setText(publisher);
            bookCuttedPrice.setText(formatPrice(cuttedPrice));
            up_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bookAmount.setText(String.valueOf(Integer.parseInt(bookAmount.getText().toString())+1));
                }
            });
            down_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Integer.parseInt(bookAmount.getText().toString())>=1){
                        bookAmount.setText(String.valueOf(Integer.parseInt(bookAmount.getText().toString())-1));
                    }
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
            if(showDeleteBtn){
                del_btn.setVisibility(View.VISIBLE);
            }else{
                up_btn.setEnabled(false);
                down_btn.setEnabled(false);
                bookAmount.setEnabled(false);
                del_btn.setVisibility(View.GONE);
            }
            del_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!BookDetailActivity.RUNNING_CART_QUERY){
                        BookDetailActivity.RUNNING_CART_QUERY = true;
                        DataBaseQueries.removeCartList(position, itemView.getContext());
                    }
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
        private void setTotalAmount(int totalItemText,int totalItemPriceText,String deliveryPriceText, int totalAmountText,int savedAmountText){
            totalItems.setText(String.format("Giá (%s món)",totalItemText));
            totalItemsPrice.setText(formatPrice(String.valueOf(totalItemPriceText)));
            if(deliveryPriceText.equals("Free")){
                deliveryPrice.setText(deliveryPriceText);
            }else{
                deliveryPrice.setText(formatPrice(deliveryPriceText));
            }
            totalAmount.setText(formatPrice(String.valueOf(totalAmountText)));
            cartTotalAmount.setText(formatPrice(String.valueOf(totalAmountText)));
            savedAmount.setText(String.format("Tiết kiệm +%s",formatPrice(String.valueOf(savedAmountText))));
        }
    }
    private String formatPrice(String price){
        int prices = 0;
        if(price != null)
            prices = Integer.parseInt(price);
        String Prices = String.format("%,d đ",prices);
        return Prices;
    }
}
