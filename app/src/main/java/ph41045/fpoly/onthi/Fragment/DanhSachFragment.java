package ph41045.fpoly.onthi.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ph41045.fpoly.onthi.Model.ThiSinh;
import ph41045.fpoly.onthi.R;
import ph41045.fpoly.onthi.Adapter.DanhSachAdapter;

public class DanhSachFragment extends Fragment {

    RecyclerView rcvThiSinh;
    Button btnThem;
    DanhSachAdapter adapter;
    List<ThiSinh> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_danh_sach_fragment, container, false);

        rcvThiSinh = view.findViewById(R.id.rcvThiSinh);
        btnThem = view.findViewById(R.id.btnThem);

        // Giả lập dữ liệu ban đầu (sau này sẽ lấy từ DB)
        list = new ArrayList<>();
        list.add(new ThiSinh(1, "Nguyễn Văn A", 1, "P101"));
        list.add(new ThiSinh(2, "Trần Thị B", 2, "P102"));

        adapter = new DanhSachAdapter(getContext(), list);
        rcvThiSinh.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvThiSinh.setAdapter(adapter);

        // Sự kiện nút Thêm
        btnThem.setOnClickListener(v -> {
            // TODO: Mở dialog thêm thí sinh mới
        });

        return view;
    }
}

