package com.ypb.canal.redis.mapper;

import com.ypb.canal.redis.entry.StudentEntry;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentEntryMapper {

	int deleteByPrimaryKey(Integer id);

    int insert(StudentEntry record);

    int insertSelective(StudentEntry record);

    StudentEntry selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StudentEntry record);

    int updateByPrimaryKey(StudentEntry record);
}