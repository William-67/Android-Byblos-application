package com.example.myapplicationprojectpart1;

import java.util.List;
import com.example.myapplicationprojectpart1.UserAccount;

public class ListRecheck {
    public static boolean isDuplicate(List<UserAccount> accounts, String userName) {
        for (UserAccount account : accounts) {
            if (account.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;

    }

    public static boolean match(List<UserAccount> accounts, String userName, String password) {
        for (UserAccount account : accounts) {
            if (account.getUserName().equals(userName) && account.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}