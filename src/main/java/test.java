import com.fanyank.dao.UserDao;
import com.fanyank.entity.Reply;
import com.fanyank.entity.User;
import com.fanyank.service.UserService;

import java.lang.*;
import java.util.List;

/**
 * Created by yanfeng-mac on 2017/3/28.
 */
public class test {
    public static void main(String[] args) {
        UserService userService = new UserService();
        UserDao userDao = new UserDao();
        User user = userDao.findByUsername("rose");

        List<Reply> replyList = userService.getUnReadMsg(user);

        for(Reply reply : replyList) {
            System.out.println(reply);
        }

    }
}
