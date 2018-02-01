package com.lewis_v.widget.sample;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lewis_v.widget.viewflow.YWFlowViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * auth: lewis-v
 * time: 2018/2/1.
 */
public class MainActivity extends AppCompatActivity {
    private YWFlowViewPager<ImageView> flowview1;
    private YWFlowViewPager<LinearLayout> flowview2;

    private List<ImageView> imageViews = new ArrayList<>();
    private List<LinearLayout> linearLayouts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flowview1 = findViewById(R.id.flowview1);
        flowview2 = findViewById(R.id.flowview2);

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.add);
        ImageView imageView2 = new ImageView(this);
        imageView2.setImageResource(R.drawable.add_circle);
        ImageView imageView3 = new ImageView(this);
        imageView3.setImageResource(R.drawable.add_circle_outline);

        imageViews.add(imageView);
        imageViews.add(imageView2);
        imageViews.add(imageView3);

        flowview1.setFlowView(imageViews);
        flowview1.start(true);


        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setBackgroundColor(Color.BLUE);
        LinearLayout linearLayout1 = new LinearLayout(this);
        linearLayout1.setBackgroundColor(Color.RED);
        LinearLayout linearLayout2 = new LinearLayout(this);
        linearLayout2.setBackgroundColor(Color.GREEN);

        linearLayouts.add(linearLayout);
        linearLayouts.add(linearLayout1);
        linearLayouts.add(linearLayout2);

        flowview2.setFlowView(linearLayouts);
        flowview2.start(true);
    }
}
