package com.seagox.oa.disk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.disk.entity.JellyDisk;
import com.seagox.oa.disk.entity.JellyDiskRecycle;
import com.seagox.oa.disk.entity.JellyDiskShare;
import com.seagox.oa.disk.mapper.JellyDiskMapper;
import com.seagox.oa.disk.mapper.JellyDiskRecycleMapper;
import com.seagox.oa.disk.mapper.JellyDiskShareMapper;
import com.seagox.oa.disk.param.FileChunkParam;
import com.seagox.oa.disk.service.IJellyDiskService;
import com.seagox.oa.disk.service.JellyFileChunkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 网盘 服务实现类
 */
@Service
public class JellyDiskService implements IJellyDiskService {

    @Autowired
    private JellyDiskRecycleMapper diskRecycleMapper;

    @Autowired
    private JellyDiskMapper diskMapper;

    @Autowired
    private JellyFileChunkService jellyFileChunkService;

    @Autowired
    private JellyDiskShareMapper jellyDiskShareMapper;

    private final static String BUCKET_NAME = "disk";

    @Override
    public List<Map<String, Object>> queryFileByUserId(Long companyId, Long userId, Long parentId, String type, String name) {
        if (!type.equals("all") || !StringUtils.isEmpty(name)) {
            parentId = null;
        }
        List<Integer> typeList = new ArrayList<>();
        if ("image".equals(type)) {
            typeList.add(2);
        } else if ("document".equals(type)) {
            typeList.add(3);
            typeList.add(4);
            typeList.add(5);
            typeList.add(6);
            typeList.add(7);
            typeList.add(8);
            typeList.add(9);
            typeList.add(13);
        } else if ("video".equals(type)) {
            typeList.add(10);
        } else if ("music".equals(type)) {
            typeList.add(11);
        } else if ("other".equals(type)) {
            typeList.add(12);
        }
        return diskMapper.queryByUserId(companyId, userId, parentId, name, 1, typeList);
    }

    @Override
    public ResultData backToPrevious(Long parentId, Integer menuIndex, Long userId, Long companyId, Long shareUserId) {
        JellyDisk jellyDisk = diskMapper.selectById(parentId);

        if (menuIndex == 8) {
            LambdaQueryWrapper<JellyDiskShare> qwShare = new LambdaQueryWrapper<>();
            qwShare.eq(JellyDiskShare::getDiskId, jellyDisk.getId())
                    .eq(JellyDiskShare::getUserId, userId);
            List<JellyDiskShare> jellyDiskShares = jellyDiskShareMapper.selectList(qwShare);
            if (jellyDiskShares != null && jellyDiskShares.size() > 0) {
                return ResultData.success(jellyDiskShareMapper.queryByUserId(companyId, userId));
            }
        }
        LambdaQueryWrapper<JellyDisk> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyDisk::getParentId, jellyDisk.getParentId())
                .eq(JellyDisk::getCompanyId, companyId)
                .eq(JellyDisk::getStatus, 1);
        if (shareUserId != null) {
            qw.eq(JellyDisk::getUserId, shareUserId);
        } else {
            qw.eq(JellyDisk::getUserId, userId);
        }
        return ResultData.success(diskMapper.selectList(qw));
    }

    @Override
    public ResultData insert(JellyDisk jellyDisk) {
        jellyDisk.setStatus(1);
        if (isExist(jellyDisk)) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "文件已存在！");
        }
        setPath(jellyDisk);
        diskMapper.insert(jellyDisk);
        return ResultData.success(null);
    }

    @Override
    public ResultData update(JellyDisk jellyDisk) {
        if (isExist(jellyDisk)) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "文件已存在！");
        }
        diskMapper.updateById(jellyDisk);
        return ResultData.success(null);
    }

    @Transactional
    @Override
    public ResultData share(Long id, String toUserIds, Long userId, Long companyId) {
        JellyDisk disk = diskMapper.selectById(id);
        disk.setId(id);
        disk.setToUserIds(toUserIds);
        diskMapper.updateById(disk);

        JellyDisk updateDisk = new JellyDisk();
        updateDisk.setToUserIds(toUserIds);
        LambdaQueryWrapper<JellyDisk> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyDisk::getCompanyId, disk.getCompanyId())
                .eq(JellyDisk::getUserId, disk.getUserId())
                .likeRight(JellyDisk::getPath, disk.getPath() + disk.getName());
        diskMapper.update(updateDisk, qw);

        //更新上级目录状态
//		Pattern p = Pattern.compile("/");
//		Matcher m = p.matcher(disk.getPath());
//		while(m.find()){
//			String fullPath = disk.getPath().substring(0, m.start()+1);
//			if(!fullPath.equals("/")) {
//				String temporary = fullPath.substring(0, fullPath.length()-1);
//				String path = temporary.substring(0, temporary.lastIndexOf("/")+1);
//				String name = temporary.substring(temporary.lastIndexOf("/")+1);
//				LambdaQueryWrapper<JellyDisk> qwFolder = new LambdaQueryWrapper<>();
//				qwFolder.eq(JellyDisk::getCompanyId, disk.getCompanyId())
//				.eq(JellyDisk::getUserId, disk.getUserId())
//				.eq(JellyDisk::getType, 1)
//				.eq(JellyDisk::getName, name)
//				.eq(JellyDisk::getPath, path);
//				JellyDisk updateDiskFolder = new JellyDisk();
//				updateDiskFolder.setToUserIds(toUserIds);
//				diskMapper.update(updateDiskFolder, qwFolder);
//			}
//		}
        //他人分享
        //删除原先分享记录
        LambdaQueryWrapper<JellyDiskShare> qwShare = new LambdaQueryWrapper<>();
        qwShare.eq(JellyDiskShare::getDiskId, id);
        jellyDiskShareMapper.delete(qwShare);
        if (!StringUtils.isEmpty(toUserIds)) {
            //新增分享记录
            String[] userIds = toUserIds.split(",");
            for (String toUserId : userIds) {
                JellyDiskShare jellyDiskShare = new JellyDiskShare();
                jellyDiskShare.setDiskId(id);
                jellyDiskShare.setCompanyId(companyId);
                jellyDiskShare.setShareTime(new Date());
                jellyDiskShare.setShareUserId(userId);
                jellyDiskShare.setUserId(Long.parseLong(toUserId));
                jellyDiskShareMapper.insert(jellyDiskShare);
            }
        }
        return ResultData.success(null);
    }

    @Override
    public ResultData uploadChunk(JellyDisk jellyDisk, FileChunkParam param) {
        ResultData result = jellyFileChunkService.uploadChunk(jellyDisk.getCompanyId(), param, BUCKET_NAME);
        if (result.getCode() == ResultCode.SUCCESS.getCode() && param.getTotalChunks() == 1) {
            jellyDisk.setName(param.getFilename());
            setPath(jellyDisk);
            jellyDisk.setLink(result.getData().toString());
            jellyDisk.setCapacity(String.valueOf(param.getFile().getSize()));
            diskMapper.insert(jellyDisk);
            return ResultData.success(null);
        } else {
            return result;
        }
    }

    @Override
    public ResultData mergeChunks(JellyDisk jellyDisk, String identifier) {
        ResultData result = jellyFileChunkService.mergeChunks(jellyDisk.getCompanyId(), identifier, BUCKET_NAME, jellyDisk.getName());
        if (result.getCode() == ResultCode.SUCCESS.getCode()) {
            setPath(jellyDisk);
            jellyDisk.setLink(result.getData().toString());
            diskMapper.insert(jellyDisk);
            return ResultData.success(null);
        } else {
            return result;
        }
    }

    @Override
    public ResultData insertOrUpdate(JellyDisk jellyDisk) {
        if (jellyDisk.getId() == null) {
            jellyDisk.setStatus(1);
        }
        if (isExist(jellyDisk)) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "文件已存在！");
        }
        setPath(jellyDisk);
        if (StringUtils.isEmpty(jellyDisk.getText())) {
            jellyDisk.setCapacity("0");
        } else {
            jellyDisk.setCapacity(String.valueOf(jellyDisk.getText().getBytes().length));
        }
        if (jellyDisk.getId() == null) {
            diskMapper.insert(jellyDisk);
        } else {
            diskMapper.updateById(jellyDisk);
        }
        return ResultData.success(null);
    }

    @Transactional
    @Override
    public void moveToRecycle(Long id) {
        JellyDisk jellyDisk = diskMapper.selectById(id);
        jellyDisk.setStatus(2);
        diskMapper.updateById(jellyDisk);
        // 如果是文件夹需要修改该文件状态及子文件状态
        if (1 == jellyDisk.getType()) {
            JellyDisk updateDisk = new JellyDisk();
            updateDisk.setStatus(2);
            LambdaQueryWrapper<JellyDisk> qw = new LambdaQueryWrapper<>();
            qw.eq(JellyDisk::getCompanyId, jellyDisk.getCompanyId())
                    .eq(JellyDisk::getUserId, jellyDisk.getUserId())
                    .likeRight(JellyDisk::getPath, jellyDisk.getPath() + jellyDisk.getName());
            diskMapper.update(updateDisk, qw);
        }

        JellyDiskRecycle jellyDiskRecycle = new JellyDiskRecycle();
        jellyDiskRecycle.setDiskId(id);
        // 插入回收表
        diskRecycleMapper.insert(jellyDiskRecycle);
    }

    @Override
    public boolean isExist(JellyDisk jellyDisk) {
        LambdaQueryWrapper<JellyDisk> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyDisk::getCompanyId, jellyDisk.getCompanyId())
                .eq(JellyDisk::getUserId, jellyDisk.getUserId())
                .eq(JellyDisk::getParentId, jellyDisk.getParentId())
                .eq(JellyDisk::getName, jellyDisk.getName())
                .eq(JellyDisk::getStatus, jellyDisk.getStatus())
                .eq(JellyDisk::getType, jellyDisk.getType())
                .ne(jellyDisk.getId() != null, JellyDisk::getId, jellyDisk.getId());
        return diskMapper.selectCount(qw) > 0;
    }

    /**
     * 设置文件路径
     *
     * @param jellyDisk 实体
     */
    private void setPath(JellyDisk jellyDisk) {
        Long parentId = jellyDisk.getParentId();
        if (parentId > 0) {
            JellyDisk parentDisk = diskMapper.selectById(parentId);
            jellyDisk.setPath(parentDisk.getPath() + parentDisk.getName() + "/");
            jellyDisk.setUserId(parentDisk.getUserId());
            jellyDisk.setToUserIds(parentDisk.getToUserIds());
        } else {
            jellyDisk.setPath("/");
        }
    }

    @Override
    public ResultData queryById(Long id) {
        return ResultData.success(diskMapper.selectById(id));
    }
}
