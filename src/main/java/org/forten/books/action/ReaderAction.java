package org.forten.books.action;

import org.forten.books.bo.BookBo;
import org.forten.books.dto.qo.BookQo;
import org.forten.books.dto.vo.BookForShow;
import org.forten.books.dto.vo.BorrowedBooksVo;
import org.forten.dto.Message;
import org.forten.dto.PagedRo;
import org.forten.util.NumberUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ReaderAction {
    @Resource
    private BookBo bo;

    @RequestMapping("/search")
    public @ResponseBody
    PagedRo<BookForShow> list(@RequestBody BookQo qo) {
        return bo.queryBy(qo);
    }

    @RequestMapping("/preBorrow")
    public @ResponseBody
    Message preBorrow(int bId,HttpSession session){
        // TODO uId应该从session中获取
        int uId = 2;

        return bo.doPreBorrow(uId,bId);
    }

    @RequestMapping("/queryBorrowedBooks")
    public @ResponseBody
    List<BorrowedBooksVo> queryBorrowedBooks(HttpSession session){
        // TODO uId应该从session中获取
        int userId = 2;
        return bo.queryBorrowedBooksForReader(userId);
    }
}
