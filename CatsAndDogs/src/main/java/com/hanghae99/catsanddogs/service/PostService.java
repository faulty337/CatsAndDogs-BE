package com.hanghae99.catsanddogs.service;

import com.hanghae99.catsanddogs.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;



}
