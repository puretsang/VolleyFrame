package cn.waikey.volleyframe;

import java.io.Serializable;

/**
 * Created by Tsang on 16/4/8.
 * 实体类示例
 */
public class UserLogin implements Serializable{

    private User user;
    private Worker worker;

    public User getUser() {
        return user;
    }

    public Worker getWorker() {
        return worker;
    }

    public static class User implements Serializable {

        private int id;

        public int getId() {
            return id;
        }
    }

    public static class Worker implements Serializable {
        /**
         * 员工的状态:
         * 0:未加入公司
         * 1:待审核
         * 2:未激活,
         * 3:在职
         */
        private int status;

        /**
         * 员工的身份:
         * 0:未加入公司
         * 1:管理员
         * 2:普通员工
         */
        private int identity;

        public int getStatus() {
            return status;
        }

        public int getIdentity() {
            return identity;
        }
    }
}
