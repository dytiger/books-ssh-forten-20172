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
import javax.swing.text.Style;
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

        // 图书库存减1
        hql = "UPDATE Book SET amount=amount-1 WHERE id=:id";
        params.clear();
        params.put("id",bookId);
        dao.executeUpdate(hql,params);

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

    @Transactional(readOnly = true)
    public List<BorrowedBooksVo> queryBorrowedBooksForAdmin(String card) {
        String hql = "SELECT new org.forten.books.dto.vo.BorrowedBooksVo(bi.id,u.name,b.name,bi.borrowStatus,bi.borrowTime) " +
                "FROM User u JOIN BorrowInfo bi ON (u.id=bi.uId) JOIN Book b ON (b.id=bi.bId) " +
                "WHERE u.libraryCard LIKE :card " +
                "ORDER BY bi.borrowTime DESC";
        Map<String, Object> params = new HashMap<>(1);
        params.put("card", card + "%");

        return dao.findBy(hql, params);
    }

    @Transactional
    public Message doChangePB2BD(int biId){
        String hql = "UPDATE BorrowInfo SET borrowStatus='BD' WHERE id=:id";
        Map<String,Object> params = new HashMap<>(1);
        params.put("id",biId);
        try {
            dao.executeUpdate(hql, params);
            return Message.info("已经成功借出图书");
        }catch (Exception e){
            return Message.error("预借状态切换错误");
        }
    }

    @Transactional
    public Message doReturnBook(int biId){
        try {
            // 得到书籍的id
            String hql = "SELECT bId FROM BorrowInfo WHERE id=:id";
            Map<String, Object> params = new HashMap<>();
            params.put("id", biId);

            int bookId = dao.findOneBy(hql, params);

            // 对书籍信息中的库存加1
            hql = "UPDATE Book SET amount=amount+1 WHERE id=:id";
            params.put("id", bookId);
            dao.executeUpdate(hql, params);

            // 删除借书的信息
            dao.delete(BorrowInfo.class, biId);

            return Message.info("图书归还成功");
        }catch(Exception e){
            return Message.error("图书归还失败");
        }
    }

    @Transactional
    public void doCleanPB(){
        String hql = "SELECT bId FROM BorrowInfo WHERE borrowStatus='PB'";
        List<Integer> list = dao.findBy(hql);

        // 恢复书籍的库存量
        hql = "UPDATE Book SET amount=amount+1 WHERE id=:id";
        Map<String,Object> params = new HashMap<>();
        for(int bookId : list){
            params.put("id",bookId);
            dao.executeUpdate(hql,params);
        }

        // 删除BorrowInfo数据
        hql = "DELETE FROM BorrowInfo WHERE borrowStatus='PB'";
        dao.executeUpdate(hql);
    }

    @Transactional(readOnly = true)
    public int queryBorrowInfoMaxId(){
        String hql = "SELECT max(id) FROM BorrowInfo";
        return dao.findOneBy(hql);
    }

    @Transactional(readOnly = true)
    public List<BorrowedBooksVo> queryPBBi(){
        String hql = "SELECT new org.forten.books.dto.vo.BorrowedBooksVo(bi.id,u.name,b.name,bi.borrowStatus,bi.borrowTime) " +
                "FROM User u JOIN BorrowInfo bi ON (u.id=bi.uId) JOIN Book b ON (b.id=bi.bId) " +
                "WHERE bi.borrowStatus='PB' " +
                "ORDER BY bi.borrowTime DESC";

        return dao.findBy(hql);
    }
}
