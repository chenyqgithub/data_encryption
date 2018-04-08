package com.data.encryption.respository;

import com.data.encryption.entity.Address;
import com.data.encryption.entity.History;
import com.data.encryption.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/4/2.
 */
@Repository
public class UserRepository {
    //模仿数据
    private static List<User> users = new ArrayList<User>();

    static {
        //初始化User数据
        for (int i=0;i<10;i++){
            User user = new User();
            user.setId(""+i);
            user.setEmail("email" + i);
            user.setPassword("password" + i);
            Address address=new Address();
            address.setAddress("address-"+i);
            address.setUserId(i);
            History history=new History();
            history.setTitle("出现");
            address.setHistory(history);
            user.setAddress(address);
            Map<String,Object>  map=new HashMap<>();
            map.put("ee",1);
//            user.setMap(map);
            users.add(user);
        }
    }
    public User getUserById(String id){
        for (User user : users){
            if(user.getId().equals(id) ){
                return user;
            }
        }
        return  null;
    }


    public List<User> getAllUser(){
        return users;
    }
}
