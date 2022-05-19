package com.kuang.controller;


import com.kuang.pojo.Position;
import com.kuang.service.IPositionService;
import com.kuang.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sx-9773
 * @since 2022-05-18
 */
@RestController
@RequestMapping("/position")
@Api(tags = "Position-Controller")
public class PositionController {

    @Autowired
    IPositionService positionService;

    @ApiOperation(value = "添加职位")
    @PostMapping("/addPosition")
    public Response addPosition(@RequestBody Position position){
        position.setCreateDate(LocalDateTime.now());
        positionService.save(position);
        return Response.success("添加成功");
    }
    @ApiOperation(value = "删除职位")
    @DeleteMapping("/deletePosition/{id}")
    public Response deletePosition(@PathVariable Integer id){
        positionService.removeById(id);
        return Response.success("删除成功");
    }

    @ApiOperation(value = "修改职位")
    @PutMapping("/updatePosition")
    public Response updatePosition(@RequestBody Position position){
        positionService.updateById(position);
        return Response.success("修改成功");
    }
    @ApiOperation(value = "批量添加职位")
    @PostMapping("/addBatchPosition")
    public Response addBatchPosition(@RequestBody List<Position> positions){
        for (Position position : positions) {
            position.setCreateDate(LocalDateTime.now());
        }
        positionService.saveBatch(positions);
        return Response.success("批量添加成功");
    }

    @ApiOperation(value = "批量删除职位")
    @DeleteMapping("/deleteBatchPosition")
    public Response deleteBatchPosition( Integer[] ids){
        positionService.removeByIds(Arrays.asList(ids));
        return Response.success("批量删除成功");
    }

    @ApiOperation(value = "查询所有的职位")
    @GetMapping("/queryPosition")
    public Response queryPosition(){
        List<Position> positions = positionService.list();
        return Response.success("查询成功",positions);
    }


}
