package org.forten.books.bo;

import org.forten.books.dto.qo.BookQo;
import org.forten.books.dto.vo.BookForShow;
import org.forten.dao.HibernateDao;
import org.forten.dto.PageInfo;
import org.forten.dto.PagedRo;
import org.forten.util.StringUtil;
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
}