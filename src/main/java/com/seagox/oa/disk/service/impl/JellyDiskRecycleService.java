package com.seagox.oa.disk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seagox.oa.disk.entity.JellyDisk;
import com.seagox.oa.disk.entity.JellyDiskRecycle;
import com.seagox.oa.disk.entity.JellyDiskShare;
import com.seagox.oa.disk.mapper.JellyDiskMapper;
import com.seagox.oa.disk.mapper.JellyDiskRecycleMapper;
import com.seagox.oa.disk.mapper.JellyDiskShareMapper;
import com.seagox.oa.disk.service.IJellyDiskRecycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 回收站 服务实现类
 */
@Service
public class JellyDiskRecycleService implements IJellyDiskRecycleService {

    @Autowired
    private JellyDiskMapper diskMapper;

    @Autowired
    private JellyDiskRecycleMapper diskRecycleMapper;

    @Autowired
    private JellyDiskShareMapper diskShareMapper;

    @Override
    public List<Map<String, Object>> queryByUserId(Long companyId, Long userId) {
        return diskRecycleMapper.queryByUserId(companyId, userId);
    }

    @Transactional
    @Override
    public void revert(Long companyId, Long userId, Long id) {
        JellyDiskRecycle diskRecycle = diskRecycleMapper.selectById(id);
        JellyDisk disk = diskMapper.selectById(diskRecycle.getDiskId());
        disk.setStatus(1);
        diskMapper.updateById(disk);
        if (disk.getType() == 1) {
            //还原文件夹
            LambdaQueryWrapper<JellyDisk> qw = new LambdaQueryWrapper<>();
            qw.eq(JellyDisk::getCompanyId, companyId)
                    .eq(JellyDisk::getUserId, userId)
                    .likeRight(JellyDisk::getPath, disk.getPath() + disk.getName());
            JellyDisk updateDisk = new JellyDisk();
            updateDisk.setStatus(1);
            diskMapper.update(updateDisk, qw);
            //排除不用还原文件
            List<Map<String, Object>> list = diskRecycleMapper.queryByUserId(companyId, userId);
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> item = list.get(i);
                if (item.get("path").toString().contains(disk.getPath() + disk.getName())) {
                    JellyDisk excludeDisk = new JellyDisk();
                    excludeDisk.setId(Long.valueOf(item.get("diskId").toString()));
                    excludeDisk.setStatus(2);
                    diskMapper.updateById(excludeDisk);
                    if (item.get("type").toString().equals("1")) {
                        //文件夹
                        LambdaQueryWrapper<JellyDisk> qwExclude = new LambdaQueryWrapper<>();
                        qwExclude.eq(JellyDisk::getCompanyId, companyId)
                                .eq(JellyDisk::getUserId, userId)
                                .likeRight(JellyDisk::getPath, item.get("path").toString() + item.get("name").toString());
                        JellyDisk excludeDiskDetail = new JellyDisk();
                        excludeDiskDetail.setStatus(2);
                        diskMapper.update(excludeDiskDetail, qwExclude);
                    }
                }
            }
        } else {
            //还原单个文件
            //更新上级目录状态
            Pattern p = Pattern.compile("/");
            Matcher m = p.matcher(disk.getPath());
            while (m.find()) {
                String fullPath = disk.getPath().substring(0, m.start() + 1);
                if (!fullPath.equals("/")) {
                    String temporary = fullPath.substring(0, fullPath.length() - 1);
                    String path = temporary.substring(0, temporary.lastIndexOf("/") + 1);
                    String name = temporary.substring(temporary.lastIndexOf("/") + 1);
                    LambdaQueryWrapper<JellyDisk> qw = new LambdaQueryWrapper<>();
                    qw.eq(JellyDisk::getCompanyId, companyId)
                            .eq(JellyDisk::getUserId, userId)
                            .eq(JellyDisk::getType, 1)
                            .eq(JellyDisk::getName, name)
                            .eq(JellyDisk::getPath, path);
                    JellyDisk updateDisk = new JellyDisk();
                    updateDisk.setStatus(1);
                    diskMapper.update(updateDisk, qw);
                }
            }
        }
        diskRecycleMapper.deleteById(id);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        JellyDiskRecycle diskRecycle = diskRecycleMapper.selectById(id);
        JellyDisk disk = diskMapper.selectById(diskRecycle.getDiskId());
        if (disk.getType() == 1) {
            //文件夹
            boolean flag = false;
            List<Map<String, Object>> list = diskRecycleMapper.queryByUserId(disk.getCompanyId(), disk.getUserId());
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> item = list.get(i);
                if (item.get("path").toString().contains(disk.getPath() + disk.getName())) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                disk.setStatus(3);
                diskMapper.updateById(disk);
            } else {
                diskMapper.deleteById(disk.getId());
            }
        } else {
            //单个文件
            diskMapper.deleteById(disk.getId());
        }
        diskRecycleMapper.deleteById(id);
        //删除他人分享记录
        LambdaQueryWrapper<JellyDiskShare> wq = new LambdaQueryWrapper<>();
        wq.eq(JellyDiskShare::getDiskId, diskRecycle.getDiskId());
        diskShareMapper.delete(wq);
    }
}
