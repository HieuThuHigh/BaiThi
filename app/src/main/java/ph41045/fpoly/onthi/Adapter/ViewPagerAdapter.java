package ph41045.fpoly.onthi.Adapter;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import ph41045.fpoly.onthi.Fragment.DanhSachFragment;
import ph41045.fpoly.onthi.Fragment.ThongTinFragment;
public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new  DanhSachFragment();
        } else {
            return new ThongTinFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2; // Số tab
    }
}
