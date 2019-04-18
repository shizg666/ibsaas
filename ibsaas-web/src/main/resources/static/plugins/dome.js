/**
 * Created by Administrator on 2017/6/14.
 */
$(function () {
    $('.subnav').click(function () {
        $('.subnav').removeClass('active');
        var index = $(this).index();
        $('.subnav').eq(index).addClass('active');
    });
    $('.subnav .navcontent li').click(function () {
        $('.subnav .navcontent li').removeClass('active');
        var index = $(this).index();
        $('.subnav .navcontent li').eq(index).addClass('active');
        $('.nav-list').eq(index).show();
    });
    $('.nav-list a').click(function () {
        $('.nav-list a').removeClass('active');
        $(this).addClass('active');
    });
    $('.nav-down').click(function () {
        $(this).toggleClass('active');
        $('.nav,subNav,.g-model').toggleClass('active');
    });
    //
    // var innerHeight = document.documentElement.clientHeight;
    // var headHeight = $('.header').height();
    // var menuHeight = $('.ksd-menu').height();
    // var infoHeight = $('.ksd-info').height();
    // var scrollHeight = innerHeight - headHeight - menuHeight - infoHeight -180;
    // var tableHeight = $('.ksd-table').height();
    // if (tableHeight >=scrollHeight ){
    //     $('.ksd-table').css({'height':scrollHeight,'overflow-y':'auto"'});
    // }

    /* 展示数据选择 */
    $('.page-number-list').click(function () {
        $(this).addClass('active');
        $('.page-select').show();
        var oLi=$("ul.page-select li");
        $(oLi).click(function(){
            var index=$(this).index();
            var oLiText=oLi.eq(index).text();
            $('.page-number-list').text(oLiText);
            $('.page-select').hide();
            $('.page-number-list').removeClass('active');
        })
    });

    $('.icon-user').mouseover(function(){
        $('.ksd-user-bg').show();
    }).mouseout(function(){
        $('.ksd-user-bg').hide();
    });
    $('.ksd-user-bg').mouseout(function(){
        $('.ksd-user-bg').hide();
    });
});
