package com.ypb.canal.redis.mapper;

import com.ypb.canal.redis.entry.ScoreEntry;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScoreEntryMapper {

	int deleteByPrimaryKey(Integer scId);

    int insert(ScoreEntry record);

    int insertSelective(ScoreEntry record);

    ScoreEntry selectByPrimaryKey(Integer scId);

    int updateByPrimaryKeySelective(ScoreEntry record);

    int updateByPrimaryKey(ScoreEntry record);
}