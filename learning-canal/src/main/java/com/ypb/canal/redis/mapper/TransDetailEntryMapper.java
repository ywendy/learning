package com.ypb.canal.redis.mapper;

import com.ypb.canal.redis.entry.TransDetailEntry;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TransDetailEntryMapper {
    int deleteByPrimaryKey(Long transID);

    int insert(TransDetailEntry record);

    int insertSelective(TransDetailEntry record);

    TransDetailEntry selectByPrimaryKey(Long transID);

    int updateByPrimaryKeySelective(TransDetailEntry record);

    int updateByPrimaryKey(TransDetailEntry record);

	List<TransDetailEntry> getTransDetailByCardID(@Param("groupID") Long groupID, @Param("cardID") Long cardID);

	TransDetailEntry getTransDetailByLinkedID(@Param("groupID") Long groupID, @Param("linkTransID") Long linkTransID);

	List<TransDetailEntry> getTransDetails(@Param("groupID") Long groupID);
}