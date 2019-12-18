package com.landleaf.ibsaas.client.meeting.dal.dao.meeting.mbg;

import com.landleaf.ibsaas.client.meeting.model.po.meeting.mbg.BookCategories;
import com.landleaf.ibsaas.client.meeting.model.po.meeting.mbg.BookCategoriesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BookCategoriesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_categories
     *
     * @mbggenerated Wed Dec 18 14:12:28 CST 2019
     */
    int countByExample(BookCategoriesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_categories
     *
     * @mbggenerated Wed Dec 18 14:12:28 CST 2019
     */
    int deleteByExample(BookCategoriesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_categories
     *
     * @mbggenerated Wed Dec 18 14:12:28 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_categories
     *
     * @mbggenerated Wed Dec 18 14:12:28 CST 2019
     */
    int insert(BookCategories record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_categories
     *
     * @mbggenerated Wed Dec 18 14:12:28 CST 2019
     */
    int insertSelective(BookCategories record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_categories
     *
     * @mbggenerated Wed Dec 18 14:12:28 CST 2019
     */
    List<BookCategories> selectByExample(BookCategoriesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_categories
     *
     * @mbggenerated Wed Dec 18 14:12:28 CST 2019
     */
    BookCategories selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_categories
     *
     * @mbggenerated Wed Dec 18 14:12:28 CST 2019
     */
    int updateByExampleSelective(@Param("record") BookCategories record, @Param("example") BookCategoriesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_categories
     *
     * @mbggenerated Wed Dec 18 14:12:28 CST 2019
     */
    int updateByExample(@Param("record") BookCategories record, @Param("example") BookCategoriesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_categories
     *
     * @mbggenerated Wed Dec 18 14:12:28 CST 2019
     */
    int updateByPrimaryKeySelective(BookCategories record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table book_categories
     *
     * @mbggenerated Wed Dec 18 14:12:28 CST 2019
     */
    int updateByPrimaryKey(BookCategories record);
}