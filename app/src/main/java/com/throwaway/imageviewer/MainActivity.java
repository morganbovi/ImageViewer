package com.throwaway.imageviewer;

import com.bumptech.glide.Glide;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.add_url_edittext)
    EditText mAddUrlEditText;
    @BindView(R.id.add_button)
    Button mAddButton;
    private ImageAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new ImageAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.addImage(mAddUrlEditText.getText().toString());
                mAddUrlEditText.setText("");
            }
        });


    }

    class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final Context mContext;
        private List<String> urlList = new ArrayList<>();

        public ImageAdapter(Context context) {
            mContext = context;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_view, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ImageViewHolder) holder).bindImage(urlList.get(position));
        }

        void addImage(String url) {
            if (!TextUtils.isEmpty(url)) {
                urlList.add(url);
                notifyItemInserted(urlList.size());
            }
        }

        @Override
        public int getItemCount() {
            return urlList.size();
        }

        class ImageViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.image)
            ImageView imageView;
            @BindView(R.id.url)
            TextView urlTextView;

            public ImageViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            void bindImage(String url) {
                urlTextView.setText(url);
                Glide.with(mContext).load(url).fitCenter().into(imageView);
            }
        }
    }
}
