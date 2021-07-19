package com.example.appbanhangnew.action.Admin.XemSanPham;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.controller.Admin.ChiTietSPAdminController;

import com.example.appbanhangnew.model.SanPham.ChitietSPs;
import com.example.appbanhangnew.model.SanPham.SanPham;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class ChiTietSPAdminActivity extends AppCompatActivity {

    //ImageView imageView_Listimage;
    ViewFlipper viewFlipper_ChitietSP;
    ArrayList<String> mangimage;
    SanPham getSP;
    ChitietSPs getChitietSP;
    int position_image = 0;
    //biến nhận biết cử chỉ của người dùng
    GestureDetector gestureDetector;
    //các biến xác định khoảng cách và tốc độ vuốt.
    int SWIPE_THRESHOLD = 10;
    int SWIPE_VECLOCITY_THRESHOLD = 10;
    Animation in, out;
    androidx.appcompat.widget.Toolbar toolbarVanChuyen;
    Toolbar toolbarChonChiTiet;

    TextView txtTenSP,txtGia,txtMotasp;
    public static TextView txtPhiVC,txtVanChuyen;
    ChiTietSPAdminController chiTietSPController;
    RecyclerView recyclerViewChonChiTietSP, recyclerViewSpTuongTu;

    SanPham sanPham;
    ChitietSPs chitietSPs;
    ImageButton btnPreVious, btnNext;



    //List<>

    // Dùng pop up

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_s_p_admin);
        Anhxa();
        DataSP();
        SetArrImage();
        ActionViewFlipper();
        chiTietSPController.ActionBar(toolbarChonChiTiet,getSP,getChitietSP,txtGia.getText().toString());
        getData();


    }



    @SuppressLint("WrongViewCast")
    public void Anhxa() {
        //imageView_Listimage=(ImageView) findViewById(R.id.img_Listimage);
        viewFlipper_ChitietSP = (ViewFlipper) findViewById(R.id.viewFlipperChitietSP);
        in = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        out = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);

        mangimage = new ArrayList<>();

        toolbarChonChiTiet= (Toolbar) findViewById(R.id.toolbarChonHang);

        txtGia=(TextView) findViewById(R.id.txt_GiaCTSP);
        txtMotasp=(TextView)findViewById(R.id.txtMotaChiTietSP);
        txtTenSP=(TextView) findViewById(R.id.txt_TenSPChiTiet);

        //txtTile=(TextView) findViewById(R.id.txtTiltle);

        btnPreVious=(ImageButton) findViewById(R.id.btnPreviuos);
        btnNext=(ImageButton) findViewById(R.id.btnNext);
        chiTietSPController=new ChiTietSPAdminController(SWIPE_THRESHOLD,SWIPE_VECLOCITY_THRESHOLD,recyclerViewChonChiTietSP,recyclerViewSpTuongTu,viewFlipper_ChitietSP, ChiTietSPAdminActivity.this);


    }
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.btnNext:
                viewFlipper_ChitietSP.showNext();
                break;
            case  R.id.btnPreviuos:
                viewFlipper_ChitietSP.showPrevious();
                break;
        }
    }
    public void DataSP() {
        getSP = (SanPham) getIntent().getSerializableExtra("thongtinsp");
        getChitietSP = (ChitietSPs) getIntent().getSerializableExtra("ThongtinChiTietSP");

    }

    public void SetArrImage() {
        mangimage.add(0, getSP.getHinhanhsp());
        for (int i = 1; i < getChitietSP.CountChitietSPs(); i++) {
            mangimage.add(i, getChitietSP.getChitiet(i).getHinhanhChiTietsp());
        }
    }

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //e1,e2 là position 1 và position 2
            //vectocityX, veclocityY : là kéo theo trục nào.
            //kéo từ trái sang phải
            if (e2.getX() - e1.getX() > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VECLOCITY_THRESHOLD && e1.getAction()==MotionEvent.ACTION_DOWN) {
                Toast.makeText(getApplicationContext(), "Từ trái sang phải", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),"velocity" + velocityX,Toast.LENGTH_SHORT).show();

                viewFlipper_ChitietSP.showPrevious();

            }
            if (e1.getX() - e2.getX() > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VECLOCITY_THRESHOLD && e1.getAction()==MotionEvent.ACTION_DOWN) {
                Toast.makeText(getApplicationContext(), "Từ phải sang trái", Toast.LENGTH_SHORT).show();

                viewFlipper_ChitietSP.showNext();


            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    public void ActionViewFlipper(){

        for(int i=0;i<mangimage.size();i++){
            ImageView imageView= new ImageView(getApplicationContext());

            //thực hiện load ảnh quảng cáo từ link vào inmageView.
            Picasso.get().load(mangimage.get(i)).error(R.drawable.error).into(imageView);
            //Fuit hình cho vừa vs inmage
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper_ChitietSP.addView(imageView);

        }
        // tạo hiệu ứng cb động
        viewFlipper_ChitietSP.setInAnimation(in);
        viewFlipper_ChitietSP.setOutAnimation(out);
        viewFlipper_ChitietSP.setFlipInterval(3000);
        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        viewFlipper_ChitietSP.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });


    }
    public void getData(){

        sanPham=(SanPham) getIntent().getSerializableExtra("thongtinsp");
        txtTenSP.setText(sanPham.getTensp());
        txtGia.setText(getIntent().getStringExtra("Gianhonhat"));
        txtMotasp.setText(sanPham.getMota());



    }

}