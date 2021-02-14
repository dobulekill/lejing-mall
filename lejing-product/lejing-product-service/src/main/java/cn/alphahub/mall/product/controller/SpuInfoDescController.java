package cn.alphahub.mall.product.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.SpuInfoDesc;
import cn.alphahub.mall.product.service.SpuInfoDescService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * spu信息介绍Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-14 19:02:16
 */
@RestController
@RequestMapping("product/spuinfodesc")
public class SpuInfoDescController extends BaseController {
    @Autowired
    private SpuInfoDescService spuInfoDescService;

    /**
     * 查询spu信息介绍列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param spuInfoDesc spu信息介绍,查询字段选择性传入,默认为等值查询
     * @return spu信息介绍分页数据
     */
    @GetMapping("/list")
    public BaseResult<PageResult<SpuInfoDesc>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SpuInfoDesc spuInfoDesc
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<SpuInfoDesc> pageResult = spuInfoDescService.queryPage(pageDomain, spuInfoDesc);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return BaseResult.ok(pageResult);
        }
        return BaseResult.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取spu信息介绍详情
     *
     * @param spuId spu信息介绍主键id
     * @return spu信息介绍详细信息
     */
    @GetMapping("/info/{spuId}")
    public BaseResult<SpuInfoDesc> info(@PathVariable("spuId") Long spuId) {
        SpuInfoDesc spuInfoDesc = spuInfoDescService.getById(spuId);
        return ObjectUtils.anyNotNull(spuInfoDesc) ? BaseResult.ok(spuInfoDesc) : BaseResult.fail();
    }

    /**
     * 新增spu信息介绍
     *
     * @param spuInfoDesc spu信息介绍元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public BaseResult<Boolean> save(@RequestBody SpuInfoDesc spuInfoDesc) {
        boolean save = spuInfoDescService.save(spuInfoDesc);
        return toOperationResult(save);
    }

    /**
     * 修改spu信息介绍
     *
     * @param spuInfoDesc spu信息介绍,根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(@RequestBody SpuInfoDesc spuInfoDesc) {
        boolean update = spuInfoDescService.updateById(spuInfoDesc);
        return toOperationResult(update);
    }

    /**
     * 批量删除spu信息介绍
     *
     * @param spuIds spu信息介绍id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{spuIds}")
    public BaseResult<Boolean> delete(@PathVariable Long[] spuIds) {
        boolean delete = spuInfoDescService.removeByIds(Arrays.asList(spuIds));
        return toOperationResult(delete);
    }
}
