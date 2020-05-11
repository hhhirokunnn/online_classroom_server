package com.hhhirokunnn.classroom_community_server.domain.services.favorite_article

import com.hhhirokunnn.classroom_community_server.app.models.parameters.FavoriteArticleRegisterParameter
import com.hhhirokunnn.classroom_community_server.domain.entities.article.ArticleEntity
import com.hhhirokunnn.classroom_community_server.domain.entities.favorite_article.FavoriteArticleEntity
import com.hhhirokunnn.classroom_community_server.domain.entities.user.UserEntity
import com.hhhirokunnn.classroom_community_server.domain.repositories.favorite_article.FavoriteArticleRepository
import org.springframework.stereotype.Service

@Service
class FavoriteArticleService(
    private val favoriteArticleRepository: FavoriteArticleRepository) {

//    fun save(param: FavoriteArticleRegisterParameter) =
//        favoriteArticleRepository.save(FavoriteArticleEntity(
//        userId =  param.userId,
//        articleId =  param.articleId))

    fun save(user: UserEntity, article: ArticleEntity): FavoriteArticleEntity {
        return favoriteArticleRepository.save(
                FavoriteArticleEntity(
                    user = user, article = article))
    }

    fun countAmountByArticleId(id: Long): Int = favoriteArticleRepository.countByArticleId(id)

    fun findByUserId(userId: Long): List<FavoriteArticleEntity> = favoriteArticleRepository.findByUserId(userId)
}