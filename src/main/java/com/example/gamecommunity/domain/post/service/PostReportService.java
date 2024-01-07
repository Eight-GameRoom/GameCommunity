package com.example.gamecommunity.domain.post.service;

import com.example.gamecommunity.domain.post.repository.PostReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostReportService {

  private final PostReportRepository postReportRepository;

}
