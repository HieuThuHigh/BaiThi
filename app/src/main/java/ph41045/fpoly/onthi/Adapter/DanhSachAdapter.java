package ph41045.fpoly.onthi.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ph41045.fpoly.onthi.Model.ThiSinh;
import ph41045.fpoly.onthi.R;

public class DanhSachAdapter extends RecyclerView.Adapter<DanhSachAdapter.ViewHolder> {

    private Context context;
    private List<ThiSinh> list;

    public DanhSachAdapter(Context context, List<ThiSinh> list) {
        this.context = context;
        this.list = list;
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
        holder.txtPhong.setText(thiSinh.getPhongThi());

        // Sự kiện nút Xóa
        holder.btnXoa.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc chắn muốn xóa thí sinh này?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        list.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, list.size());
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });

        // Nút sửa (bạn có thể làm thêm)
        holder.btnSua.setOnClickListener(v -> {
            // TODO: Code chức năng sửa
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTen, txtPhong;
        ImageButton btnSua, btnXoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txtTen);
            txtPhong = itemView.findViewById(R.id.txtPhong);
            btnSua = itemView.findViewById(R.id.btnSua);
            btnXoa = itemView.findViewById(R.id.btnXoa);
        }
    }
}
