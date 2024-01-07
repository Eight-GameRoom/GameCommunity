package com.example.gamecommunity.domain.post.controller;

import com.example.gamecommunity.domain.post.service.PostReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts/{postId}/report")
@RequiredArgsConstructor
public class PostReportController {

  private final PostReportService postReportService;

}
