package org.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.domain.user.RiderOrder;
import org.example.domain.user.SysAccount;

/**
 * @Author 刘文轩
 * @Date 2023/12/29 19:02
 */
@Mapper
public interface RiderOrderMapper extends BaseMapper<RiderOrder> {
    SysAccount getRiderByNum();
}
