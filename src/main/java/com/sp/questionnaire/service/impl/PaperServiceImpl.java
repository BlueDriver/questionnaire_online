package com.sp.demo_sb.service.impl;

import com.sp.demo_sb.dao.PaperDao;
import com.sp.demo_sb.entity.Paper;
import com.sp.demo_sb.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * description:
 * Author:Xiaowanwan
 * Date:2018/9/13-14:59
 */
@Service
public class PaperServiceImpl implements PaperService{
    @Autowired
    private PaperDao paperDao;

    @Override
    public List<Paper> queryPaper() {
        return paperDao.queryPaper();
    }

    @Override
    public List<Paper> queryPaperByUserID(String userId) {
        return paperDao.queryPaperByUserID(userId);
    }

    @Override
    public Paper queryPaperByID(String id) {
        return paperDao.queryPaperByID(id);
    }

    @Transactional
    @Override
    public boolean insertPaper(Paper paper) {
        if (paper !=null && !"".equals(paper.getId())){
            try{
                int i = paperDao.insertPaper(paper);
                if (i ==1){
                    return true;
                }else {
                    throw new RuntimeException("a:删除试卷失败！" + paper);
                }
            }catch (Exception e){
                throw new RuntimeException("b:插入试卷失败：" + e.getMessage());
            }
        }else {
            throw new RuntimeException("c:插入试卷失败，Paper的id不能为空！");
        }
    }

    @Transactional
    @Override
    public boolean updatePaper(Paper paper) {
        if (paper !=null && !"".equals(paper.getId())){
            try{
                int i = paperDao.updatePaper(paper);
                if (i ==1){
                    return true;
                }else {
                    throw new RuntimeException("a:修改试卷失败！" + paper);
                }
            }catch (Exception e){
                throw new RuntimeException("b:修改试卷失败：" + e.getMessage());
            }
        }else {
            throw new RuntimeException("c:修改试卷失败，Paper的id不能为空！");
        }
    }

    @Transactional
    @Override
    public boolean deletePaper(String id) {
        if (id !=null && !"".equals(id)){
            try{
                int i = paperDao.deletePaper(id);
                if (i ==1){
                    return true;
                }else {
                    throw new RuntimeException("a:删除试卷失败！" + id);
                }
            }catch (Exception e){
                throw new RuntimeException("b:删除试卷失败：" + e.getMessage());
            }
        }else {
            throw new RuntimeException("c:删除试卷失败，Paper的id不能为空！");
        }
    }

    @Override
    public List<Paper> queryPaperByTitle(String title) {
        return paperDao.queryPaperByTitle(title);
    }
}
