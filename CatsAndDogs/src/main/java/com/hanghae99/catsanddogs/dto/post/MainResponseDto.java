package com.hanghae99.catsanddogs.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@Getter
public class MainResponseDto {
    private PostResponseListDto postList;

    private TodaysPetsDto todaysPets;

    public MainResponseDto(PostResponseListDto postList, List<PostResponseDto> todaysPets) {
        this.postList = postList;
        this.todaysPets = new TodaysPetsDto(todaysPets);
    }

    @Getter
    class TodaysPetsDto{
        private List<PostResponseDto> rank;

        public TodaysPetsDto(List<PostResponseDto> rank) {
            this.rank = rank;
        }

    }
}
