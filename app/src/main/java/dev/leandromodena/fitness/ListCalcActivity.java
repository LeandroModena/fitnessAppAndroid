package dev.leandromodena.fitness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListCalcActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_calc);

        recyclerView = findViewById(R.id.recyclerViewListResult);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String type = extras.getString("type");

            new Thread(() -> {
                List<Register> register = SqlHelper.getInstance(this).getRegisterBy(type);
                runOnUiThread(() -> {
                    ListCalcAdapter adapter = new ListCalcAdapter(register);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.setAdapter(adapter);

                });
            }).start();
        }
    }

    public class ListCalcAdapter extends RecyclerView.Adapter<ListCalcAdapter.ListCalcViewHolder> {

        private List<Register> datas;
        private OnItemClickListener listener;

        public ListCalcAdapter(List<Register> datas) {
            this.datas = datas;
        }

        @NonNull
        @Override
        public ListCalcViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ListCalcViewHolder(getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ListCalcViewHolder holder, int position) {
            Register data = datas.get(position);
            holder.bind(data);

        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        public class ListCalcViewHolder extends RecyclerView.ViewHolder {

            public ListCalcViewHolder(@NonNull View itemView) {
                super(itemView);

            }

            public void bind(Register data) {
                String formatted = "";

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", new Locale("pt", "br"));
                    Date dataSaved = sdf.parse(data.createDate);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", new Locale("pt", "br"));
                    formatted = dateFormat.format(dataSaved);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                ((TextView) itemView).setText(getString(R.string.list_response, data.response, formatted));

            }
        }
    }
}