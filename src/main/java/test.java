import com.fanyank.dao.UserDao;
import com.fanyank.entity.User;
import java.lang.*;

/**
 * Created by yanfeng-mac on 2017/3/28.
 */
public class test {
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        User user = userDao.findByUsername("tom");
        System.out.println(user);
    }
}
