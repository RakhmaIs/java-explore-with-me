package ru.practicum.main.comment.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.main.comment.dto.CommentCountDto;
import ru.practicum.main.comment.model.Comment;

import java.util.List;


public interface CommentMainServiceRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByEventId(Long eventId, Pageable pageable);

    @Query("select new ru.practicum.main.comment.dto.CommentCountDto(c.event.id, count(c.id)) " +
            "from Comment as c " +
            "where c.event.id in ?1 " +
            "group by c.event.id")
    List<CommentCountDto> findAllCommentCount(List<Long> listEventId);

    @Query("select c " +
            "from Comment as c " +
            "where lower(c.text) like concat('%', lower(?1), '%') ")
    List<Comment> findAllByText(String text);

    List<Comment> findAllByAuthorId(Long userId);
}
