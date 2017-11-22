package com.fu.tantancard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView mRecyclerView;
    private List<User> mData;
    private CardAdapter mAdapter;
    private TextView tv_del_count;
    private TextView tv_love_count;
    private int loveCount;  //喜欢的数量
    private int delCount;   //删除的数量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        mData = initData();
        //初始化卡片的基本配置参数
        CardConfig.initConfig(this);
        mRecyclerView.setLayoutManager(new SwipeCardLayoutManager());
        mAdapter = new CardAdapter();
        mRecyclerView.setAdapter(mAdapter);

        //三步
        //1.创建ItemTuchCallBack
        CardItemTouchCallBack callBack = new CardItemTouchCallBack(mRecyclerView, mAdapter, mData);
        //2.创建ItemTouchHelper并把callBack传进去
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callBack);
        //3.与RecyclerView关联起来
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void initUI() {
        loveCount = 0;
        delCount = 0;
        tv_love_count = findViewById(R.id.tv_love_count);
        tv_love_count.setText("喜欢:" + loveCount);
        tv_del_count = findViewById(R.id.tv_del_count);
        tv_del_count.setText("删除:" + delCount);
        this.mRecyclerView = findViewById(R.id.recycler_view);
    }

    private List<User> initData() {
        List<User> datas = new ArrayList<>();
        datas.add(new User(R.mipmap.pic1, "名字1", "其他1"));
        datas.add(new User(R.mipmap.pic2, "名字2", "其他2"));
        datas.add(new User(R.mipmap.pic3, "名字3", "其他3"));
        datas.add(new User(R.mipmap.pic4, "名字4", "其他4"));
        datas.add(new User(R.mipmap.pic5, "名字5", "其他5"));
        datas.add(new User(R.mipmap.pic6, "名字6", "其他6"));
        datas.add(new User(R.mipmap.pic7, "名字7", "其他7"));
        datas.add(new User(R.mipmap.pic8, "名字8", "其他8"));
        return datas;
    }

    class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

        @Override
        public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_card, parent, false);
            CardViewHolder holder = new CardViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(CardViewHolder holder, int position) {
            holder.tv_name.setText(mData.get(position).getName());
            holder.tv_sign.setText(mData.get(position).getSign());
            //用Glide来加载图片
            Glide.with(getApplicationContext())
                    .load(mData.get(position).getPhotoResId())
                    .apply(new RequestOptions().transform(new CenterCrop()))
                    .into(holder.iv_photo);
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        //右滑的时候调用
        public void addLoveCount() {
            loveCount++;
            tv_love_count.setText("喜欢:" + loveCount);
        }

        //左滑的时候调用
        public void addDelCount(){
            delCount++;
            tv_del_count.setText("删除:" + delCount);
        }

        class CardViewHolder extends RecyclerView.ViewHolder {

            ImageView iv_photo;
            TextView tv_name;
            TextView tv_sign;
            ImageView iv_love;
            ImageView iv_del;

            public CardViewHolder(View itemView) {
                super(itemView);
                iv_photo = itemView.findViewById(R.id.iv_photo);
                tv_name = itemView.findViewById(R.id.tv_name);
                tv_sign = itemView.findViewById(R.id.tv_sign);
                iv_love = itemView.findViewById(R.id.iv_love);
                iv_del = itemView.findViewById(R.id.iv_del);
            }
        }
    }
}
