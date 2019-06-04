package com.ypb.canal.redis.mapper;

import com.ypb.canal.redis.entry.CourseEntry;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseEntryMapper {
	
	int deleteByPrimaryKey(Integer cId);

    int insert(CourseEntry record);

    int insertSelective(CourseEntry record);

    CourseEntry selectByPrimaryKey(Integer cId);

    int updateByPrimaryKeySelective(CourseEntry record);

    int updateByPrimaryKey(CourseEntry record);
}