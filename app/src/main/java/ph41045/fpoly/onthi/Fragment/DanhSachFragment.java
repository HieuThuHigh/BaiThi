package ph41045.fpoly.onthi.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ph41045.fpoly.onthi.Adapter.DanhSachAdapter;
import ph41045.fpoly.onthi.DB.DatabaseHelper;
import ph41045.fpoly.onthi.Model.ThiSinh;
import ph41045.fpoly.onthi.R;

public class DanhSachFragment extends Fragment {

    RecyclerView rcvThiSinh;
    Button btnThem;
    DanhSachAdapter adapter;
    List<ThiSinh> list;
    DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_danh_sach_fragment, container, false);

        rcvThiSinh = rootView.findViewById(R.id.rcvThiSinh);
        btnThem = rootView.findViewById(R.id.btnThem);
        dbHelper = new DatabaseHelper(getContext());

        // Lấy danh sách từ DB
        list = dbHelper.getAllThiSinh();
        if (list == null) {
            list = new ArrayList<>();
        }

        adapter = new DanhSachAdapter(getContext(), list);
        rcvThiSinh.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvThiSinh.setAdapter(adapter);

        btnThem.setOnClickListener(v -> moDialogThem());

        return rootView;
    }

    private void moDialogThem() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.dialog_them_thisinh, null);

        EditText edtHoTen = dialogView.findViewById(R.id.edtHoTen);
        EditText edtCaThi = dialogView.findViewById(R.id.edtCaThi);
        EditText edtPhongThi = dialogView.findViewById(R.id.edtPhongThi);

        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Thêm thí sinh")
                .setView(dialogView)
                .setPositiveButton("Lưu", null) // override validate sau
                .setNegativeButton("Hủy", null)
                .create();

        dialog.setOnShowListener(dlg -> {
            Button btnLuu = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            btnLuu.setOnClickListener(v -> {
                String hoTen = edtHoTen.getText().toString().trim();
                String caThiStr = edtCaThi.getText().toString().trim();
                String phongThi = edtPhongThi.getText().toString().trim();

                // Validate rỗng
                if (hoTen.isEmpty() || caThiStr.isEmpty() || phongThi.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate ca thi 1-6
                int caThi;
                try {
                    caThi = Integer.parseInt(caThiStr);
                    if (caThi < 1 || caThi > 6) {
                        Toast.makeText(getContext(), "Ca thi chỉ từ 1 đến 6", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Ca thi phải là số", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate phòng thi đúng 3 ký tự
                if (phongThi.length() != 3) {
                    Toast.makeText(getContext(), "Phòng thi phải đúng 3 ký tự", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Lưu DB
                int newId = list.size() + 1;
                ThiSinh thiSinhMoi = new ThiSinh(newId, hoTen, caThi, phongThi);
                boolean kq = dbHelper.themThiSinh(thiSinhMoi);
                if (kq) {
                    list.add(thiSinhMoi);
                    adapter.notifyItemInserted(list.size() - 1);
                    dialog.dismiss(); // đóng dialog sau khi lưu thành công
                } else {
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            });

        });

        dialog.show();
    }
}
