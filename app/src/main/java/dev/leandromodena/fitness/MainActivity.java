package dev.leandromodena.fitness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<MainItem> mainItem = new ArrayList<>();
        mainItem.add(new MainItem(1, R.drawable.image_imc, R.string.imc_label, Color.WHITE));
        mainItem.add(new MainItem(2, R.drawable.image_tmb, R.string.imc_tmb, Color.WHITE));



        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        MainAdapter mainAdapter = new MainAdapter(mainItem);
        mainAdapter.setListener(id ->{
            switch (id){
                case 1:
                    startActivity(new Intent(MainActivity.this, ImcActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(MainActivity.this, TmbActivity.class));
                    break;
            }});
        recyclerView.setAdapter(mainAdapter);

    }

    public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{

        private List<MainItem> mainItems;
        private OnItemClickListener listener;

        public MainAdapter(List<MainItem> mainItems){
            this.mainItems = mainItems;

        }

        public void setListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        @NonNull
        @Override
        public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MainViewHolder(getLayoutInflater().inflate(R.layout.main_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
            MainItem mainItemCurrent =  mainItems.get(position);
            holder.bind(mainItemCurrent);

        }

        @Override
        public int getItemCount() {
            return mainItems.size();
        }

        public class MainViewHolder extends RecyclerView.ViewHolder {

            public MainViewHolder(@NonNull View itemView) {
                super(itemView);

            }

            public void bind(MainItem mainItem) {
                TextView textView = itemView.findViewById(R.id.textViewItemMain);
                ImageView imageView = itemView.findViewById(R.id.imageViewItemMain);
                LinearLayout container = (LinearLayout) itemView.findViewById(R.id.linearLayout);

                container.setOnClickListener(v -> {
                    listener.onClick(mainItem.getId());

                });

                textView.setText(mainItem.getTextStringId());
                imageView.setImageResource(mainItem.getDrawable());
                container.setBackgroundColor(mainItem.getColor());

            }

        }
    }

}