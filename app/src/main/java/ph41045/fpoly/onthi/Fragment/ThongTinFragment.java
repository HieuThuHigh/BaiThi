package ph41045.fpoly.onthi.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ph41045.fpoly.onthi.R;

public class ThongTinFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_thong_tin_fragment, container, false);

        TextView tvInfo = view.findViewById(R.id.tvInfo);
        tvInfo.setText("Họ tên: Nguyễn Văn A\nMã SV: 123456\nLớp: CNTT1");

        return view;
    }
}



