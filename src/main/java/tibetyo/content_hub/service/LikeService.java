package tibetyo.content_hub.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tibetyo.content_hub.dto.like.LikeRequestDto;
import tibetyo.content_hub.dto.like.LikeResponseDto;
import tibetyo.content_hub.entity.Content;
import tibetyo.content_hub.entity.Like;
import tibetyo.content_hub.entity.User;
import tibetyo.content_hub.exception.CustomException;
import tibetyo.content_hub.exception.ErrorCode;
import tibetyo.content_hub.repository.LikeRepository;
import tibetyo.content_hub.repository.UserRepository;
import tibetyo.content_hub.repository.content.ContentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final ContentRepository contentRepository;

    public LikeService(LikeRepository likeRepository, UserRepository userRepository, ContentRepository contentRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.contentRepository = contentRepository;
    }

    @Transactional
    public void addLike(LikeRequestDto likeRequestDto) {
        validateContentExists(likeRequestDto.getContentId());
        validateUserExists(likeRequestDto.getUserId());
        if (likeRepository.existsByUserIdAndContentId(likeRequestDto.getUserId(), likeRequestDto.getContentId())) {
            throw new CustomException(ErrorCode.LIKE_ALREADY_EXISTS);
        }

        User user = validateUserExists(likeRequestDto.getUserId());
        Content content = validateContentExists(likeRequestDto.getContentId());
        Like like = Like.builder().user(user).content(content).build();
        likeRepository.save(like);
    }

    @Transactional
    public void removeLike(LikeRequestDto likeRequestDto) {
        validateUserExists(likeRequestDto.getUserId());
        validateContentExists(likeRequestDto.getContentId());
        Like like = likeRepository.findByUserIdAndContentId(likeRequestDto.getUserId(), likeRequestDto.getContentId())
                .orElseThrow(() -> new CustomException(ErrorCode.LIKE_NOT_FOUND));
        likeRepository.delete(like);
    }

    public List<LikeResponseDto> findLikesByUser(Long userId) {
        validateUserExists(userId);
        List<Like> likes = likeRepository.findLikesByUserId(userId);
        if (likes.isEmpty()) {
            throw new CustomException(ErrorCode.LIKE_NOT_FOUND);
        }

        return likes.stream()
                .map(LikeResponseDto::of).collect(Collectors.toList());
    }

    public Long countLikesByContent(Long contentId) {
        validateContentExists(contentId);
        return likeRepository.countLikesByContentId(contentId);
    }

    private User validateUserExists(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    private Content validateContentExists(Long contentId) {
        return contentRepository.findById(contentId)
                .orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));
    }

}
