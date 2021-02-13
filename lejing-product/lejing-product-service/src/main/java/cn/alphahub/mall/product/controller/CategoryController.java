package cn.alphahub.mall.product.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.Category;
import cn.alphahub.mall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 商品三级分类Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:46:24
 */
@RestController
@RequestMapping("product/category")
public class CategoryController extends BaseController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 查询商品三级分类列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param category    商品三级分类,字段选择性传入,默认为等值查询
     * @return 商品三级分类分页数据
     */
    @GetMapping("/list")
    @SuppressWarnings("unchecked")
    public BaseResult<PageResult<Category>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            Category category
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<Category> pageResult = categoryService.queryPage(pageDomain, category);
        return (BaseResult<PageResult<Category>>) toPageableResult(pageResult);
    }

    /**
     * 查出所有分类及其子分类， 树形结构组装
     *
     * @return 分类及其子分类树形数据
     */
    @GetMapping("/list/tree")
    public BaseResult<List<Category>> listCategoryTree() {
        List<Category> categories = categoryService.listWithTree();
        return BaseResult.ok(categories);
    }

    /**
     * 获取商品三级分类详情
     *
     * @param catId 商品三级分类主键id
     * @return 商品三级分类详细信息
     */
    @GetMapping("/{catId}")
    @SuppressWarnings("unchecked")
    public BaseResult<Category> info(@PathVariable("catId") Long catId) {
        Category category = categoryService.getById(catId);
        return (BaseResult<Category>) toResponseResult(category);
    }

    /**
     * 新增商品三级分类
     *
     * @param category 商品三级分类元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public BaseResult<Boolean> save(@RequestBody Category category) {
        boolean save = categoryService.save(category);
        return toOperationResult(save);
    }

    /**
     * 修改商品三级分类
     *
     * @param category 商品三级分类,根据主键id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(@RequestBody Category category) {
        boolean update = categoryService.updateById(category);
        return toOperationResult(update);
    }

    /**
     * 批量修改三级分类排序
     *
     * @param categories 商品三级分类集合 ,根据id批量更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update/sort")
    public BaseResult<Boolean> updateSort(@RequestBody Category[] categories) {
        boolean update = categoryService.updateBatchById(Arrays.asList(categories));
        return toOperationResult(update);
    }

    /**
     * 批量删除商品三级分类
     *
     * @param catIds 商品三级分类id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/{catIds}")
    public BaseResult<Boolean> delete(@PathVariable Long[] catIds) {
        //boolean delete = categoryService.removeByIds(Arrays.asList(catIds));
        boolean delete = categoryService.removeMenusByIds(Arrays.asList(catIds));
        return toOperationResult(delete);
    }
}
