package org.forten.books.bo;

import org.forten.books.dto.qo.BookQo;
import org.forten.books.dto.vo.BookForShow;
import org.forten.books.dto.vo.BookForUpdate;
import org.forten.books.entity.Book;
import org.forten.dao.HibernateDao;
import org.forten.dto.Message;
import org.forten.dto.PageInfo;
import org.forten.dto.PagedRo;
import org.forten.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("bookBo")
public class BookBo {
    @Resource
    private HibernateDao dao;

    @Transactional
    public Message doSave(Book book){
        try{
            dao.save(book);
            return Message.info("书籍信息添加完成");
        }catch (Exception e){
            e.printStackTrace();
            return Message.error("书籍信息添加失败");
        }
    }

    @Transactional(readOnly = true)
    public PagedRo<BookForShow> queryBy(BookQo qo) {
        String name = qo.getName();
        Date begin = qo.getBegin();
        Date end = qo.getEnd();

        String countHql = "SELECT count(id) FROM Book WHERE 1=1 ";
        String hql = "SELECT new org.forten.books.dto.vo.BookForShow(id,name,author,press,pubDate,price,amount) FROM Book WHERE 1=1 ";
        Map<String, Object> params = new HashMap<>();
        if (StringUtil.hasText(name)) {
            countHql = countHql + "AND name LIKE :n ";
            hql = hql + "AND name LIKE :n ";
            params.put("n", "%" + name + "%");
        }

        if (begin != null && end != null) {
            countHql = countHql + "AND pubDate BETWEEN :b AND :e ";
            hql = hql + "AND pubDate BETWEEN :b AND :e ";
            params.put("b", begin);
            params.put("e", end);
        }

        long count = dao.findOneBy(countHql, params);
        if (count == 0) {
            return new PagedRo<>();
        }

        hql = hql + "ORDER BY id DESC";

        PageInfo page = PageInfo.getInstance(qo.getPageNo(),qo.getPageSize(),(int)count);

        List<BookForShow> list = dao.findBy(hql,params,page.getFirst(),page.getPageSize());

        return new PagedRo<>(list,page);
    }

    @Transactional
    public Message doUpdate(BookForUpdate vo){
        try {
            Book book = dao.loadById(Book.class, vo.getId());
            BeanUtils.copyProperties(vo, book);
            dao.update(book);
            return Message.info("修改书籍信息完成");
        }catch (Exception e){
            e.printStackTrace();
            return Message.error("修改书籍信息失败");
        }
    }
}
