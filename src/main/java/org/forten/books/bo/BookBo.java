package org.forten.books.bo;

import org.forten.books.dto.qo.BookQo;
import org.forten.books.dto.vo.BookForShow;
import org.forten.books.dto.vo.BookForUpdate;
import org.forten.books.dto.vo.BorrowedBooksVo;
import org.forten.books.entity.Book;
import org.forten.books.entity.BorrowInfo;
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
    public Message doSave(Book book) {
        try {
            dao.save(book);
            return Message.info("书籍信息添加完成");
        } catch (Exception e) {
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

        PageInfo page = PageInfo.getInstance(qo.getPageNo(), qo.getPageSize(), (int) count);

        List<BookForShow> list = dao.findBy(hql, params, page.getFirst(), page.getPageSize());

        return new PagedRo<>(list, page);
    }

    @Transactional
    public Message doUpdate(BookForUpdate vo) {
        try {
            Book book = dao.loadById(Book.class, vo.getId());
            BeanUtils.copyProperties(vo, book);
            dao.update(book);
            return Message.info("修改书籍信息完成");
        } catch (Exception e) {
            e.printStackTrace();
            return Message.error("修改书籍信息失败");
        }
    }

    @Transactional
    public Message doPreBorrow(int userId, int bookId) {
        // 查询书是否还有库存
        String hql = "SELECT amount FROM Book WHERE id=:id";
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", bookId);
        int amount = dao.findOneBy(hql, params);
        if (amount <= 0) {
            return Message.warn("书籍已无库存，暂时不能借阅。");
        }

        // 读者是否已经借阅过此书
        hql = "SELECT count(id) FROM BorrowInfo WHERE uId=:userId AND bId=:bookId";
        params.clear();
        params.put("userId", userId);
        params.put("bookId", bookId);
        long count = dao.findOneBy(hql, params);
        if (count > 0) {
            return Message.warn("相同书籍每位读者只能借阅一本。");
        }

        // 预借操作
        BorrowInfo bi = new BorrowInfo(userId, bookId);
        dao.save(bi);
        return Message.info("图书已经预借成功，请到管理员处领取图书。");
    }

    @Transactional(readOnly = true)
    public List<BorrowedBooksVo> queryBorrowedBooksForReader(int userId) {
        String hql = "SELECT new org.forten.books.dto.vo.BorrowedBooksVo(bi.id,u.name,b.name,bi.borrowStatus,bi.borrowTime) " +
                "FROM User u JOIN BorrowInfo bi ON (u.id=bi.uId) JOIN Book b ON (b.id=bi.bId) " +
                "WHERE u.id=:uId " +
                "ORDER BY bi.borrowTime DESC";
        Map<String, Object> params = new HashMap<>(1);
        params.put("uId", userId);

        return dao.findBy(hql, params);
    }
}
