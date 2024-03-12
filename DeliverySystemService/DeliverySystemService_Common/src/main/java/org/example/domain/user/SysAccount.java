package org.example.domain.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class SysAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ACCOUNT_ID", type = IdType.ASSIGN_ID)
    private String accountId;

    private String userId;

    private String appId;

    private String accountName;

    private String loginPass;

    private String loginName;

    private String avatar;

    private String status;

    private String description;

    private String createdBy;

    private LocalDateTime createTime;

    private String updatedBy;

    private LocalDateTime updatedTime;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String customAccountId;

    private Integer loginFailTimes;

    private LocalDateTime lastLoginTime;

    private String initialPasswordFlag;


}
