package togedog.server.domain.feed.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import togedog.server.domain.feed.controller.dto.FeedCreateApiRequest;
import togedog.server.domain.feed.controller.dto.FeedReportApiRequest;
import togedog.server.domain.feed.controller.dto.FeedUpdateApiRequest;
import togedog.server.domain.feed.service.dto.response.FeedDetailResponse;
import togedog.server.domain.feedreport.service.FeedReportService;
import togedog.server.domain.feedreport.service.dto.response.FeedReportResponse;
import togedog.server.domain.reply.service.dto.response.ReplyResponse;
import togedog.server.global.image.ImageNameDTO;
import togedog.server.domain.feed.service.FeedService;
import togedog.server.domain.feed.service.dto.response.FeedResponse;
import togedog.server.domain.feedbookmark.service.FeedBookmarkService;
import togedog.server.domain.feedlike.service.FeedLikeService;
import togedog.server.domain.reply.controller.dto.ReplyCreateApiRequest;
import togedog.server.domain.reply.service.ReplyService;
import togedog.server.global.image.PresignedUrlService;
import togedog.server.global.response.ApiPageResponse;
import togedog.server.global.response.ApiSingleResponse;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/feed")
@Validated
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;
    private final FeedLikeService feedLikeService;
    private final FeedBookmarkService feedBookmarkService;
    private final ReplyService replyService;
    private final FeedReportService feedReportService;


    @GetMapping //전체 피드 반환 이미지 처리 다시하자
    public ResponseEntity<ApiPageResponse<FeedResponse>> getFeeds(@RequestParam(defaultValue = "1") int page,
                                                                  @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdDateTime").descending());
        Page<FeedResponse> feedsPage = feedService.getFeedsPaged(pageable);

        return ResponseEntity.ok(ApiPageResponse.ok(feedsPage));
    }

    @GetMapping("/{feed-id}") //한 피드에 대한 feply 페이징 조회
    public ResponseEntity<ApiSingleResponse<FeedDetailResponse>> getRepliesByFeedId(@PathVariable("feed-id")
                                                                                    Long feedId) {

//        Pageable pageable = PageRequest.of(page - 1, size);
//        Page<ReplyResponse> repliesPage = replyService.getRepliesPaged(feedId, pageable);
//        FeedDetailResponse repliesPageOneFeed = feedService.getFeed(feedId, pageable);
        FeedDetailResponse feedDetailResponse = feedService.getFeedWithReplies(feedId);

        return ResponseEntity.ok(ApiSingleResponse.ok(feedDetailResponse));
    }


    @PostMapping
    public ResponseEntity<Void> postFeed(@Valid @RequestBody FeedCreateApiRequest request) {

        //로그인 된 사용자 확인 로직

        Long feedId = feedService.createFeed(request.toFeedCreateServiceRequest());

        URI uri = URI.create("/feed/" + feedId); // + feedId

        return ResponseEntity.created(uri).build();
    }


    @PatchMapping("/{feed-id}")
    public ResponseEntity<Void> updateFeed(@PathVariable("feed-id") Long feedId,
                                           @RequestBody @Valid FeedUpdateApiRequest request) {


        feedService.updateFeed(feedId, request.toFeedUpdateServiceRequest());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{feed-id}")
    public ResponseEntity<Void> deleteFeed(@PathVariable("feed-id") Long feedId) {

        feedService.deleteFeed(feedId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{feed-id}/report")
    public ResponseEntity<Void> deleteFeedByReport(@PathVariable("feed-id") Long feedId) {

        feedService.deleteFeedByReport(feedId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{feed-id}/like")
    public ResponseEntity<Void> likeFeed(@PathVariable("feed-id") Long feedId) {

        feedLikeService.likeFeed(feedId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{feed-id}/bookmark")
    public ResponseEntity<Void> bookmarkFeed(@PathVariable("feed-id") Long feedId) {

        feedBookmarkService.bookmarkFeed(feedId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{feed-id}/replies")
    public ResponseEntity<Void> postReply(@PathVariable("feed-id") Long feedId,
                                          @Valid @RequestBody ReplyCreateApiRequest request) {

        //로그인 된 사용자 확인 로직

        Long replyId = replyService.createReply(request.toCreateServiceApiRequest(), feedId);

        URI uri = URI.create("/replies/" + replyId);

        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/{feed-id}/report")
    public ResponseEntity<Void> reportFeed(@PathVariable("feed-id") Long feedId,
                                           @Valid @RequestBody FeedReportApiRequest request) {


        Long feedReportId = feedReportService.reportFeed(request.feedReportApiToService(), feedId);

        URI uri = URI.create("/feed/report/" + feedReportId);
        // uri 반환이 필요한가 ?

        return ResponseEntity.created(uri).build();

    }

    @GetMapping("/report")
    public ResponseEntity<ApiPageResponse<FeedReportResponse>> reportFeedGet(@RequestParam(defaultValue = "1") int page,
                                                                             @RequestParam(defaultValue = "5") int size) {


        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdDateTime").descending());
        Page<FeedReportResponse> feedReportsPage = feedReportService.getFeedReportPaged(pageable);

        return ResponseEntity.ok(ApiPageResponse.ok(feedReportsPage));
    }

    @PatchMapping("/report/{feed-report-id}")
    public ResponseEntity<Void> reportUpdate(@PathVariable("feed-report-id") Long feedReportId) {

        feedReportService.updateReportState(feedReportId);

        return ResponseEntity.noContent().build();
    }
}

