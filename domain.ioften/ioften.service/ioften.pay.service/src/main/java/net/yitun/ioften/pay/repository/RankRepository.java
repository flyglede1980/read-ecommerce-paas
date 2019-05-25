package net.yitun.ioften.pay.repository;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;

import net.yitun.ioften.pay.entity.Rank;
import net.yitun.ioften.pay.entity.vo.rank.MyRankVo;
import net.yitun.ioften.pay.entity.vo.rank.RankQueryVo;

@Repository("pay.RankRepository")
public interface RankRepository {

    // 表名
    static final String TABLE = "pay_rank";

    // 查询字段
    static final String COLUMNS = "t1.id, t1.no, t1.uid, t1.amount, t1.ctime, t1.mtime";

    @Select("select count(1) as total," //
            + " ( select no from " + TABLE + " where valid=1 and uid=#{uid} order by id desc limit 1 ) as no" //
            + " from " + TABLE + " where  valid=1 and ctime between #{stime} and #{etime}")
    MyRankVo my(@Param("uid") Long uid, @Param("stime") Date stime, @Param("etime") Date etime);

    @ResultType(Rank.class)
    @Select("select " + COLUMNS + " from " + TABLE + " t1 where t1.valid=1 and t1.ctime between #{stime} and #{etime}")
    Page<Rank> query(RankQueryVo queryVo);

    @ResultType(Integer.class)
    @Insert("<script>" //
            + "insert into " + TABLE + " ( id, no, uid, amount, ctime, mtime, valid )" //
            + " values <foreach collection='ranks' item='item' separator=', '> ( #{item.id}, #{item.no}, #{item.uid}, #{item.amount}, #{item.ctime}, #{item.mtime}, 1 )</foreach></script>")
    int imports(@Param("ranks") List<Rank> ranks);
}
