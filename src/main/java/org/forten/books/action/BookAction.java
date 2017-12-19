package org.forten.books.action;

import org.forten.books.bo.BookBo;
import org.forten.books.dto.qo.BookQo;
import org.forten.books.dto.vo.BookForShow;
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
    public @ResponseBody PagedRo<BookForShow> list(@RequestBody BookQo qo){
            return bo.queryBy(qo);
    }

}
