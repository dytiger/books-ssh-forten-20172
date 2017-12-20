package org.forten.books.action;

import org.forten.books.bo.BookBo;
import org.forten.books.dto.qo.BookQo;
import org.forten.books.dto.vo.BookForShow;
import org.forten.books.dto.vo.BookForUpdate;
import org.forten.books.entity.Book;
import org.forten.dto.Message;
import org.forten.dto.PagedRo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class BookAction {
    @Resource
    private BookBo bo;

    @RequestMapping("/book/list")
    public @ResponseBody
    PagedRo<BookForShow> list(@RequestBody BookQo qo) {
        return bo.queryBy(qo);
    }

    @RequestMapping("/book/save")
    public @ResponseBody
    Message save(@RequestBody Book book) {
        return bo.doSave(book);
    }

    @RequestMapping("/book/update")
    public @ResponseBody
    Message update(@RequestBody BookForUpdate vo) {
        return bo.doUpdate(vo);
    }
}
