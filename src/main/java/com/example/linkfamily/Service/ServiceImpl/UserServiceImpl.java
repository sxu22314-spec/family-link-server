package com.example.linkfamily.Service.ServiceImpl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.linkfamily.Mapper.UserMapper;
import com.example.linkfamily.Model.Entity.User;
import com.example.linkfamily.Service.UserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{
}
