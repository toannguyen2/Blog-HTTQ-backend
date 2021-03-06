package com.httq.repository;

import com.httq.model.Post;
import com.httq.model.PostStatusList;
import com.httq.model.Tag;
import com.httq.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	boolean existsBySeo(String seo);

	Optional<Post> findBySeo(String seo);

	Iterable<Post> findTop8ByOrderByIdDesc();

	Iterable<Post> findTop8ByStatusOrderByIdDesc(PostStatusList status);

	@Query(value = "select * from posts where `status` = :status order by (view_trend / (UNIX_TIMESTAMP() - UNIX_TIMESTAMP(created_at))) desc limit :limit", nativeQuery = true)
	Iterable<Post> findTopTrending(@Param("status") Integer status, @Param("limit") Integer limit);

	@Query(value = "select * from posts order by (view_trend / (UNIX_TIMESTAMP() - UNIX_TIMESTAMP(created_at))) desc limit 21", nativeQuery = true)
	Iterable<Post> findTop21Trending();

	Page<Post> findAllByTagsIn(List<Tag> tags, Pageable pageable);


	Page<Post> findAllByUser(User user, Pageable pageable);

	Page<Post> findAllByUserAndContentPlainTextContainsOrTitleContainsOrSubTitleContains(User user, String key1, String key2, String key3, Pageable pageable);
}
