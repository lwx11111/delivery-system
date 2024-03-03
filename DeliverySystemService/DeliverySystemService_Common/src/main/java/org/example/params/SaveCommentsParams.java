package org.example.params;

import lombok.Data;
import org.example.domain.comment.Comment;

import javax.validation.constraints.NotNull;

/**
 * @Author 刘文轩
 * @Date 2024/2/29 11:28
 * 前端评论信息传参
 */
@Data
public class SaveCommentsParams {

    private Comment comment;

    /**
     * 店铺评分
     */
//    @NotNull(value = "店铺评分不能为空")
    private Integer score;


}
