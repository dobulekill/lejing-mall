package cn.alphahub.mall.coupon.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.Coupon;
import cn.alphahub.mall.coupon.service.CouponService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 优惠券信息Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-14 18:57:50
 */
@RefreshScope
@RestController
@RequestMapping("coupon/coupon")
public class CouponController extends BaseController {
    @Autowired
    private CouponService couponService;

    /**
     * 查询优惠券信息列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param coupon      优惠券信息,查询字段选择性传入,默认为等值查询
     * @return 优惠券信息分页数据
     */
    @PostMapping("/list")
    public BaseResult<PageResult<Coupon>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            Coupon coupon
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<Coupon> pageResult = couponService.queryPage(pageDomain, coupon);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return BaseResult.ok(pageResult);
        }
        return BaseResult.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取优惠券信息详情
     *
     * @param id 优惠券信息主键id
     * @return 优惠券信息详细信息
     */
    @GetMapping("/info/{id}")
    public BaseResult<Coupon> info(@PathVariable("id") Long id) {
        Coupon coupon = couponService.getById(id);
        return ObjectUtils.anyNotNull(coupon) ? BaseResult.ok(coupon) : BaseResult.fail();
    }

    /**
     * 新增优惠券信息
     *
     * @param coupon 优惠券信息元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public BaseResult<Boolean> save(@RequestBody Coupon coupon) {
        boolean save = couponService.save(coupon);
        return toOperationResult(save);
    }

    /**
     * 修改优惠券信息
     *
     * @param coupon 优惠券信息,根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(@RequestBody Coupon coupon) {
        boolean update = couponService.updateById(coupon);
        return toOperationResult(update);
    }

    /**
     * 批量删除优惠券信息
     *
     * @param ids 优惠券信息id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = couponService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
