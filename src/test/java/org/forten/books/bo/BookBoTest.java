package org.forten.books.bo;

import org.forten.BaseTest;
import org.forten.books.dto.vo.BorrowedBooksVo;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class BookBoTest extends BaseTest{
    @Resource
    private BookBo bo;

    @Test
    public void testQueryBorrowedBooksForReader(){
        List<BorrowedBooksVo> list = bo.queryBorrowedBooksForReader(4);
        Assert.assertEquals(3,list.size());
        list.stream().forEach(System.out::println);
    }
}
