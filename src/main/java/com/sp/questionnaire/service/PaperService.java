package com.sp.questionnaire.service;

import com.sp.questionnaire.entity.Paper;

import java.util.List;

/**
 * description:
 * Author:Xiaowanwan
 * Date:2018/9/13-14:55
 */
public interface PaperService {
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
    boolean insertPaper(Paper paper);

    /**
     * 更新Paper，根据id更新
     * @param paper
     * @return
     */
    boolean updatePaper(Paper paper);

    /**
     * 根据id删除Paper对象
     * @param id
     * @return
     */
    boolean deletePaper(String id);

    /**
     * 根据Paper的title查询Paper,注意：模糊查询
     * @param title
     * @return
     */
    List<Paper> queryPaperByTitle(String title);
}
