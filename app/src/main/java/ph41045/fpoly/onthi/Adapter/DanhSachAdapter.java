package ph41045.fpoly.onthi.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ph41045.fpoly.onthi.DB.DatabaseHelper;
import ph41045.fpoly.onthi.Model.ThiSinh;
import ph41045.fpoly.onthi.R;

public class DanhSachAdapter extends RecyclerView.Adapter<DanhSachAdapter.ViewHolder> {

    private Context context;
    private List<ThiSinh> list;
    private DatabaseHelper dbHelper;

    public DanhSachAdapter(Context context, List<ThiSinh> list) {
        this.context = context;
        this.list = list;
        dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thisinh, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThiSinh thiSinh = list.get(position);

        holder.txtTen.setText(thiSinh.getHoTen());
        holder.txtCaThi.setText("Tuổi: " + thiSinh.getCaThi());
        holder.txtPhong.setText("Phòng: " + thiSinh.getPhongThi());

        // Xóa
        holder.btnXoa.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa thí sinh này?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        dbHelper.xoaThiSinh(thiSinh.getId()); // xóa trong DB
                        list.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, list.size());
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });

        // Sửa
        holder.btnSua.setOnClickListener(v -> {
            LayoutInflater inflater = LayoutInflater.from(context);
            View dialogView = inflater.inflate(R.layout.dialog_them_thisinh, null);

            EditText edtHoTen = dialogView.findViewById(R.id.edtHoTen);
            EditText edtCaThi = dialogView.findViewById(R.id.edtCaThi);
            EditText edtPhongThi = dialogView.findViewById(R.id.edtPhongThi);

            // Điền dữ liệu cũ
            edtHoTen.setText(thiSinh.getHoTen());
            edtCaThi.setText(String.valueOf(thiSinh.getCaThi()));
            edtPhongThi.setText(thiSinh.getPhongThi());

            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setTitle("Cập nhật thí sinh")
                    .setView(dialogView)
                    .setPositiveButton("Update", null) // override sau
                    .setNegativeButton("Hủy", null)
                    .create();

            dialog.setOnShowListener(dlg -> {
                Button btnUpdate = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                btnUpdate.setOnClickListener(v1 -> {
                    String hoTen = edtHoTen.getText().toString().trim();
                    String caThiStr = edtCaThi.getText().toString().trim();
                    String phongThi = edtPhongThi.getText().toString().trim();

                    if (hoTen.isEmpty() || caThiStr.isEmpty() || phongThi.isEmpty()) {
                        Toast.makeText(context, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int caThi;
                    try {
                        caThi = Integer.parseInt(caThiStr);
                        if (caThi < 1 || caThi > 6) {
                            Toast.makeText(context, "Ca thi chỉ từ 1 đến 6", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(context, "Ca thi phải là số", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (phongThi.length() != 3) {
                        Toast.makeText(context, "Phòng thi phải đúng 3 ký tự", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Cập nhật object trong list
                    thiSinh.setHoTen(hoTen);
                    thiSinh.setCaThi(caThi);
                    thiSinh.setPhongThi(phongThi);

                    notifyItemChanged(position);

                    // Cập nhật vào DB
                    dbHelper.capNhatThiSinh(thiSinh);

                    dialog.dismiss();
                });
            });

            dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTen, txtCaThi, txtPhong;
        ImageButton btnSua, btnXoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txtTen);
            txtCaThi = itemView.findViewById(R.id.txtCaThi);
            txtPhong = itemView.findViewById(R.id.txtPhong);
            btnSua = itemView.findViewById(R.id.btnSua);
            btnXoa = itemView.findViewById(R.id.btnXoa);
        }
    }
}
