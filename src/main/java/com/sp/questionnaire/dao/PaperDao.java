package com.sp.demo_sb.dao;

import com.sp.demo_sb.entity.Paper;
import com.sp.demo_sb.entity.User;

import java.util.List;

/**
 * description:
 * Author:Xiaowanwan
 * Date:2018/9/13-14:43
 */
public interface PaperDao {
    /**
     * 查询所有的Paper,返回List<Paper>
     * @return
     */
    List<Paper> queryPaper();

    /**
     * 查询某用户的所有Paper，返回List<Paper>
     * @param userId
     * @return
     */
    List<Paper> queryPaperByUserID(String userId);

    /**
     * 根据Paper对象的id查询Paper，返回Paper
     * @param id
     * @return
     */
    Paper queryPaperByID(String id);

    /**
     * 插入一个Paper对象到数据库中
     * @param paper
     * @return
     */
    int insertPaper(Paper paper);

    /**
     * 更新Paper，根据id更新
     * @param paper
     * @return
     */
    int updatePaper(Paper paper);

    /**
     * 根据id删除Paper对象
     * @param id
     * @return
     */
    int deletePaper(String id);

    /**
     * 根据Paper的title查询Paper,注意：模糊查询
     * @param title
     * @return
     */
    List<Paper> queryPaperByTitle(String title);
}
