package twitterapp.twitter;

import lombok.Data;

@Data
public class TwitterSearchRequestDto {
    private String queryString;
    private String action;
}
