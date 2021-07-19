package com.example.appbanhangnew.action.User.SanPham;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.appbanhangnew.R;
import com.example.appbanhangnew.action.User.GioHang.GioHangActivity;
import com.example.appbanhangnew.controller.User_Admin.ChiTietSPController;

import com.example.appbanhangnew.model.SanPham.ChitietSPs;
import com.example.appbanhangnew.model.SanPham.SanPham;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChitietSanPhamActivity extends AppCompatActivity {
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

    TextView txtTenSP,txtGia,txtMotasp, txtdanhgia;
    public static TextView txtPhiVC,txtVanChuyen;
    ChiTietSPController chiTietSPController;
    RecyclerView recyclerViewChonChiTietSP, recyclerViewSpTuongTu;

    SanPham sanPham;
    ChitietSPs chitietSPs;
    ImageButton btnPreVious, btnNext;
    Toolbar toolbarChiTiet;




    //List<>

    // Dùng pop up

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_san_pham);
        Anhxa();
        ActionToolBar();
        DataSP();
        SetArrImage();
        ActionViewFlipper();

        chiTietSPController.ActionBar(toolbarVanChuyen,toolbarChonChiTiet,getSP,getChitietSP,txtGia.getText().toString());

        getData();
        chiTietSPController.DanhGia(getSP.getIdsp(),txtdanhgia);



    }



    @SuppressLint("WrongViewCast")
    public void Anhxa() {
        //imageView_Listimage=(ImageView) findViewById(R.id.img_Listimage);
        viewFlipper_ChitietSP = (ViewFlipper) findViewById(R.id.viewFlipperChitietSP);
        in = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        out = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);

        mangimage = new ArrayList<>();
        toolbarVanChuyen= (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbarVanChuyen);
        toolbarChonChiTiet= (Toolbar) findViewById(R.id.toolbarChonHang);
        toolbarChiTiet=(Toolbar) findViewById(R.id.toolbarChiTiet );
        setSupportActionBar(toolbarChiTiet);

        txtGia=(TextView) findViewById(R.id.txt_GiaCTSP);
        txtMotasp=(TextView)findViewById(R.id.txtMotaChiTietSP);
        txtPhiVC=(TextView) findViewById(R.id.txtPhiVC);
        txtTenSP=(TextView) findViewById(R.id.txt_TenSPChiTiet);
        txtVanChuyen=(TextView) findViewById(R.id.txtTiltle);
        txtdanhgia=(TextView) findViewById(R.id.txt_DanhGia);
        //txtTile=(TextView) findViewById(R.id.txtTiltle);

        btnPreVious=(ImageButton) findViewById(R.id.btnPreviuos);
        btnNext=(ImageButton) findViewById(R.id.btnNext);
        chiTietSPController=new ChiTietSPController(SWIPE_THRESHOLD,SWIPE_VECLOCITY_THRESHOLD,recyclerViewChonChiTietSP,recyclerViewSpTuongTu,viewFlipper_ChitietSP,ChitietSanPhamActivity.this);


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

    private void ActionToolBar() {
        //toolbarDanhMuc.setTitle("Hoa");
        setSupportActionBar(toolbarChiTiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChiTiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_giohang:
                Intent intent= new Intent(ChitietSanPhamActivity.this, GioHangActivity.class);
                startActivity(intent);
                //Toast.makeText(getApplicationContext(),"Click",Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_main, menu);
        return true;
    }

}