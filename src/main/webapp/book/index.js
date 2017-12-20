let lastPage = 0;
let bookForUpdate = null;

let update = function () {
    // bookForUpdate.id = $("#edit-id").val();
    bookForUpdate.name = $("#edit-bookName").val();
    bookForUpdate.author = $("#edit-author").val();
    bookForUpdate.press = $("#edit-press").val();
    bookForUpdate.pubDate = $("#edit-pubDate").val();
    bookForUpdate.price = $("#edit-price").val();
    bookForUpdate.amount = $("#edit-bookAmount").val();

    let jsonStr = JSON.stringify(bookForUpdate);

    $.ajax({
        url: "update.do",
        type: "POST",
        data: jsonStr,
        dataType: "json",
        contentType: "application/json"
    }).then(function (ro) {
        editDialog.dialog('close');

        let msgDialog = $("#msg-dialog").dialog({
            autoOpen: false,
            title: ro.typeChineseDes
        });

        $("#msg-text").html(ro.msg);
        msgDialog.dialog('open');

        var tds = $("button[data-id='" + bookForUpdate.id + "']").parent().parent().children("td");

        $(tds[1]).text(bookForUpdate.name);
        $(tds[2]).text(bookForUpdate.author);
        $(tds[3]).text(bookForUpdate.press);
        $(tds[4]).text(bookForUpdate.pubDate);
        $(tds[5]).text(bookForUpdate.price);
        $(tds[6]).text(bookForUpdate.amount);
    }, function (xhr) {
        editDialog.dialog('close');

        let msgDialog = $("#msg-dialog").dialog({
            autoOpen: false,
            title: ro.typeChineseDes
        });
        $("#msg-text").html(ro.msg);
        msgDialog.dialog('open');
    });
};

let editDialog = $("#edit-dialog").dialog({
    autoOpen: false,
    modal: true,
    width: 550,
    height: 460,
    buttons: [{
        text: "修改",
        click: update
    }, {
        text: "重置",
        click: function () {
            //$("#edit-form").get(0).reset();
            $("#edit-id").val(bookForUpdate.id);
            $("#edit-bookName").val(bookForUpdate.name);
            $("#edit-author").val(bookForUpdate.author);
            $("#edit-press").val(bookForUpdate.press);
            $("#edit-pubDate").val(bookForUpdate.pubDate);
            $("#edit-price").val(bookForUpdate.price);
            $("#edit-bookAmount").val(bookForUpdate.amount);
        }
    }]
});

$(function () {
    list();
    $('#list-btn').click(list);
    $("#pageSize").change(list);
    let pageBtns = $(".page-btn");
    $(pageBtns[0]).click(toFirstPage);
    $(pageBtns[1]).click(prePage);
    $(pageBtns[2]).click(nextPage);
    $(pageBtns[3]).click(toLastPage);

    $("#save-btn").click(save);

    $('#data-body').on('click', '.open-edit-dialog-btn', function () {
        let btn = $(this);
        let id = btn.data("id");
        let tr = btn.parent().parent();
        let tds = tr.children();

        bookForUpdate = {
            "id": id,
            "name": $(tds[1]).text(),
            "author": $(tds[2]).text(),
            "press": $(tds[3]).text(),
            "pubDate": $(tds[4]).text(),
            "price": $(tds[5]).text(),
            "amount": $(tds[6]).text()
        };

        bookForUpdate.pubDate = bookForUpdate.pubDate.replace("年", "-").replace("月", "-") + "01";

        $("#edit-id").val(bookForUpdate.id);
        $("#edit-bookName").val(bookForUpdate.name);
        $("#edit-author").val(bookForUpdate.author);
        $("#edit-press").val(bookForUpdate.press);
        $("#edit-pubDate").val(bookForUpdate.pubDate);
        $("#edit-price").val(bookForUpdate.price);
        $("#edit-bookAmount").val(bookForUpdate.amount);

        editDialog.dialog('open');
    });
});

let save = function () {
    let book = {
        "name": $("#bookName").val(),
        "press": $("#press").val(),
        "author": $("#author").val(),
        "price": $("#price").val(),
        "pubDate": $("#pubDate").val(),
        "amount": $("#bookAmount").val()
    };

    let bookJSON = JSON.stringify(book);

    $.ajax({
        url: "save.do",
        type: "POST",
        data: bookJSON,
        dataType: "json",
        contentType: "application/json"
    }).then(function (ro) {
        let msgDialog = $("#msg-dialog").dialog({
            autoOpen: false,
            title: ro.typeChineseDes
        });

        $("#msg-text").html(ro.msg);
        msgDialog.dialog('open');

        let dateStrArr = book.pubDate.split("-");
        book.pubDate = dateStrArr[0] + "年" + dateStrArr[1] + "月";

        let newTr = "<tr>" +
            "<td>1</td>" +
            "<td>" + book.name + "</td>" +
            "<td>" + book.author + "</td>" +
            "<td>" + book.press + "</td>" +
            "<td>" + book.pubDate + "</td>" +
            "<td>" + book.price + "</td>" +
            "<td>" + book.amount + "</td>" +
            "<td><button title='修改' class='btn btn-link text-danger'><span class='glyphicon glyphicon-edit'></span></button></td>" +
            "</tr>";

        let oldTrs = $("tr", "#data-body");
        oldTrs.remove("tr:last");
        $("#data-body").prepend(newTr);

        for (let i = 0; i < oldTrs.length; i++) {
            oldTrs[i].children[0].innerText = (i + 2);
        }

        $("#save-form").get(0).reset();
    }, function (xhr) {
        let msgDialog = $("#msg-dialog").dialog({
            autoOpen: false,
            title: ro.typeChineseDes
        });
        $("#msg-text").html(ro.msg);
        msgDialog.dialog('open');
    });
}

let list = function () {
    let tbody = $("#data-body");
    // 封装表单数据为JSON
    let qo = {
        "name": $("#name").val(),
        "begin": $("#begin").val(),
        "end": $("#end").val(),
        "pageNo": $("#pageNo").val(),
        "pageSize": $("#pageSize").val()
    };

    let jsonStr = JSON.stringify(qo);

    // 调用AJAX请求得到RO的JSON
    $.ajax({
        url: "list.do",
        type: "POST",
        data: jsonStr,
        dataType: "json",
        contentType: "application/json"
    }).then(function (ro) {
        tbody.empty();
        if (ro.emptyData) {
            tbody.html("<tr><td colspan='8' class='text-danger text-center'>未查询到符合条件的书籍信息</td></tr>");
        } else {
            let trs = "";
            $.each(ro.data, function (i, b) {
                trs = trs + "<tr>" +
                    "<td>" + (i + 1) + "</td>" +
                    "<td>" + b.name + "</td>" +
                    "<td>" + b.author + "</td>" +
                    "<td>" + b.press + "</td>" +
                    "<td>" + b.pubDateStr + "</td>" +
                    "<td>" + b.price + "</td>" +
                    "<td>" + b.amount + "</td>" +
                    "<td><button title='修改' class='btn btn-link text-danger open-edit-dialog-btn' data-id='" + b.id + "'><span class='glyphicon glyphicon-edit'></span></button></td>" +
                    "</tr>";
            });
            tbody.html(trs);
            let page = ro.page;
            $("#first").text(page.first + 1);
            $("#last").text(page.last);
            $("#amount").text(page.amount);
            $("#pageNum").text(page.pageNo);
            $("#pageAmount").text(page.pageAmount);
            lastPage = page.pageAmount;
        }
    }, function (xhr) {
        alert(xhr.responseText);
    });

};

let toFirstPage = function () {
    $("#pageNo").val(1);
    list();
}
let toLastPage = function () {
    $("#pageNo").val(lastPage);
    list();
}
let nextPage = function () {
    let pageNoInput = $("#pageNo");
    let n = parseInt(pageNoInput.val()) + 1;
    if (n > lastPage) {
        pageNoInput.val(lastPage);
    } else {
        pageNoInput.val(n);
    }
    list();
}
let prePage = function () {
    let pageNoInput = $("#pageNo");
    let n = parseInt(pageNoInput.val()) - 1;
    if (n < 1) {
        pageNoInput.val(1);
    } else {
        pageNoInput.val(n);
    }
    list();
}