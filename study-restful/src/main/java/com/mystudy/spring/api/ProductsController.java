package com.mystudy.spring.api;

//import com.fengwenyi.javalib.result.Result;
import com.mystudy.spring.domain.Book;
import com.mystudy.spring.enums.ResponseEnum;
import com.mystudy.spring.service.ProductsService;
import com.mystudy.spring.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @GetMapping("/")
    public ResponseVo getProducts(@RequestParam(required = false) String categoryId,
                              @RequestParam(required = false) String keyword,
                              @RequestParam(defaultValue = "1") String pageNum,
                              @RequestParam(defaultValue = "10") String pageSize,
                              @RequestParam(defaultValue = "price_asc") String orderBy) {

        Integer cId = null;
        Integer pNum = 1;
        Integer pSize = 10;
        try {
            if (!categoryId.isEmpty()){
                cId = Integer.valueOf(categoryId);
                if (cId < 1) {
//                    return Result.error(1, "参数错误，categoryId不能小于1");
                    return ResponseVo.error(ResponseEnum.PARAM_ERROR);
                }
            }
            pNum = Integer.valueOf(pageNum);
            pSize = Integer.valueOf(pageSize);
            if (pNum < 1 || pSize < 1) {
//                return Result.error(1, "参数错误，pageNum和pageSize不能小于1");
                return ResponseVo.error(ResponseEnum.PARAM_ERROR);
            }
        } catch (NumberFormatException e) {
//            return Result.error(1, "参数错误，categoryId、pageNum和pageSize必须为正整数");
            return ResponseVo.error(ResponseEnum.PARAM_ERROR);
        }
        String[] temp = orderBy.split("_");
        if (temp.length != 2) {
//            return Result.error(1, "orderBy参数错误，正确：升序：price_asc，降序：price_desc，暂支持price、auther排序！");
            return ResponseVo.error(ResponseEnum.PARAM_ERROR);
        }
        String orderBy_field = temp[0];
        String orderBy_rule = temp[1];
        if ((!orderBy_field.equals("price") && !orderBy_field.equals("auther")) ||
                (!orderBy_rule.equalsIgnoreCase("ASC") && !orderBy_rule.equalsIgnoreCase("DESC"))) {
//            return Result.error(1, "orderBy参数错误，正确：升序：price_asc，降序：price_desc，暂支持price、auther排序！");
            return ResponseVo.error(ResponseEnum.PARAM_ERROR);
        }
        Page<Book> page = productsService.getAllBooks(cId, keyword, pNum, pSize, orderBy_field, orderBy_rule);
        List<Book> list = page.getContent();
        Map<String, Object> data = new HashMap<>();
        data.put("pageNum", page.getNumber()+1);
        data.put("pageSize", page.getSize());
        data.put("size", page.getNumberOfElements());
        data.put("orderBy", page.getSort().toString());
        data.put("total", page.getTotalElements());
        data.put("pages", page.getTotalPages());
        data.put("list", list);
        data.put("firstPage", 1);
        Integer prePage = page.hasPrevious() ? page.getNumber() : null;
        data.put("prePage", prePage);
        Integer nextPage = page.hasNext() ? page.getNumber()+2 : null;
        data.put("nextPage", nextPage);
        data.put("lastPage", page.getTotalPages());
        data.put("isFirstPage", page.isFirst());
        data.put("isLastPage", page.isLast());
        data.put("hasPreviousPage", page.hasPrevious());
        data.put("hasNextPage", page.hasNext());
//        return Result.success(data);
        return ResponseVo.success(data);
    }

    @GetMapping("/{productId}")
    public ResponseVo getProductsById(@PathVariable("productId") String id){
        Integer pid = null;
        try {
            pid = Integer.valueOf(id);
            if (pid < 1) {
//                return Result.error(1, "参数错误，productId不能小于1");
                return ResponseVo.error(ResponseEnum.PARAM_ERROR);
            }
        } catch (NumberFormatException e) {
//            return Result.error(1, "参数错误，productId必须为正整数");
            return ResponseVo.error(ResponseEnum.PARAM_ERROR);
        }
        Book book = productsService.getBookById(pid);
        if (book == null){
//            return Result.error(1,"该商品已下架或删除");
            return ResponseVo.error(ResponseEnum.PARAM_ERROR);
        }
        return ResponseVo.success(book);
    }
}
