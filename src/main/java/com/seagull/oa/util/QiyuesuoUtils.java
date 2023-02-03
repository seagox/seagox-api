package com.seagull.oa.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qiyuesuo.sdk.v2.SdkClient;
import com.qiyuesuo.sdk.v2.bean.Action;
import com.qiyuesuo.sdk.v2.bean.Contract;
import com.qiyuesuo.sdk.v2.bean.Signatory;
import com.qiyuesuo.sdk.v2.bean.User;
import com.qiyuesuo.sdk.v2.http.StreamFile;
import com.qiyuesuo.sdk.v2.json.JSONUtils;
import com.qiyuesuo.sdk.v2.request.*;
import com.qiyuesuo.sdk.v2.response.*;
import com.qiyuesuo.sdk.v2.utils.IOUtils;
import com.seagull.oa.common.ResultCode;
import com.seagull.oa.common.ResultData;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 契约锁电子签章工具类
 */
public class QiyuesuoUtils {

    /**
     * url
     */
    public static final String URL = "https://openapi.qiyuesuo.cn";

    /**
     * ACCESS_KEY
     */
    public static final String ACCESS_KEY = "ivTvFaIsVB";

    /**
     * ACCESS_SECRET
     */
    public static final String ACCESS_SECRET = "fP9LXDQBhCLriMIF5Okd9au70wP8LA";

    /**
     * 创建合同草稿
     */
    public static ResultData contractDraft(JSONArray companysArray, JSONArray personalsArray, String subject,
                                           String description) {
        SdkClient sdkClient = new SdkClient(URL, ACCESS_KEY, ACCESS_SECRET);
        // 合同基本参数
        Contract contract = new Contract();
        contract.setSubject(subject);
        contract.setDescription(description);
//		contract.setEndTime(endDate);
        contract.setSend(false);
        contract.setOrdinal(false);

        if (companysArray.size() > 0) {
            for (Object object : companysArray) {
                JSONObject parseObject = JSONObject.parseObject(object.toString());
                System.err.println(parseObject.getString("companyName") + "===" + parseObject.getString("name")
                        + "===" + parseObject.getString("phone"));
                // 签署方
                Signatory signatory = new Signatory();
                signatory.setTenantName(parseObject.getString("companyName"));
                signatory.setTenantType("COMPANY");
                Action action = new Action("COMPANY", 1);
                signatory.addAction(action);
                signatory
                        .setReceiver(new User(parseObject.getString("name"), parseObject.getString("phone"), "MOBILE"));
                // 设置签署方
                contract.addSignatory(signatory);
            }
        }
        // 添加签署个人类型
        if (personalsArray.size() > 0) {
            for (Object object : personalsArray) {
                JSONObject parseObject = JSONObject.parseObject(object.toString());
                System.err.println(parseObject.getString("name")
                        + "===" + parseObject.getString("phone"));
                // 签署方
                Signatory signatory = new Signatory();
                signatory.setTenantName(parseObject.getString("name"));
                signatory.setTenantType("PERSONAL");
                signatory
                        .setReceiver(new User(parseObject.getString("name"), parseObject.getString("phone"), "MOBILE"));
                // 设置签署方
                contract.addSignatory(signatory);
            }
        }

        // 创建合同
        ContractDraftRequest request = new ContractDraftRequest(contract);
        String response = sdkClient.service(request);
        SdkResponse<Contract> responseObj = JSONUtils.toQysResponse(response, Contract.class);
        // 返回结果
        if (responseObj.getCode() == 0) {
            Contract result = responseObj.getResult();
            System.out.println("创建合同成功，合同ID:" + result.getId());
            return ResultData.success(result.getId());
        } else {
            return ResultData.warn(ResultCode.SEAL_ERROR, responseObj.getMessage());
        }
    }

    /**
     * 发起合同
     */
    public static ResultData contractSend(Long contractId) {
        SdkClient sdkClient = new SdkClient(URL, ACCESS_KEY, ACCESS_SECRET);
        // 发起合同
        ContractSendRequest request = new ContractSendRequest(contractId, null);
        String response = sdkClient.service(request);
        SdkResponse<Object> responseObj = JSONUtils.toQysResponse(response, Object.class);
        // 返回结果
        if (responseObj.getCode() == 0) {
            System.out.println("合同发起成功");
            return ResultData.success(null);
        } else {
            return ResultData.warn(ResultCode.SEAL_ERROR, responseObj.getMessage());
        }
    }

    /**
     * 添加签署方
     */
    public static ResultData signatoryAdd(Long contractId, String companyName, String name, String phone, String type) {
        SdkClient sdkClient = new SdkClient(URL, ACCESS_KEY, ACCESS_SECRET);
        SignatoryAddRequest signatoryAddRequest = new SignatoryAddRequest();
        signatoryAddRequest.setContractId(contractId);
        Signatory signatory = new Signatory();
        signatory.setTenantName(companyName);
        signatory.setTenantType(type);
        signatory.setReceiver(new User(name, phone, "MOBILE"));
        signatoryAddRequest.setSignatory(signatory);
        String response = sdkClient.service(signatoryAddRequest);
        SdkResponse<Signatory> responseObj = JSONUtils.toQysResponse(response, Signatory.class);
        // 返回结果
        if (responseObj.getCode() == 0) {
            System.out.println("添加签署方成功");
            return ResultData.success(null);
        } else {
            return ResultData.warn(ResultCode.SEAL_ERROR, responseObj.getMessage());
        }
    }

    /**
     * 修改签署方
     */
    public static ResultData signatoryEdit(Long signatoryId, String tenantName, String name, String phone) {
        SdkClient sdkClient = new SdkClient(URL, ACCESS_KEY, ACCESS_SECRET);
        SignatoryEditRequest request = new SignatoryEditRequest();
        request.setSignatoryId(signatoryId);
        request.setTenantName(tenantName);
        request.setReceiver(new User(name, phone, "MOBILE"));
        String response = sdkClient.service(request);
        SdkResponse<Signatory> responseObj = JSONUtils.toQysResponse(response, Signatory.class);
        // 返回结果
        if (responseObj.getCode() == 0) {
            System.out.println("添加签署方成功");
            return ResultData.success(null);
        } else {
            return ResultData.warn(ResultCode.SEAL_ERROR, responseObj.getMessage());
        }
    }

    /**
     * 强制结束合同
     */
    public static ResultData contractForceend(Long contractId, String reason) {
        SdkClient sdkClient = new SdkClient(URL, ACCESS_KEY, ACCESS_SECRET);
        ContractForceEndRequest request = new ContractForceEndRequest();
        request.setContractId(contractId);
        request.setReason(reason);
        String response = sdkClient.service(request);
        SdkResponse<Signatory> responseObj = JSONUtils.toQysResponse(response, Signatory.class);
        // 返回结果
        if (responseObj.getCode() == 0) {
            System.out.println("强制结束成功");
            return ResultData.success(null);
        } else {
            return ResultData.warn(ResultCode.SEAL_ERROR, responseObj.getMessage());
        }
    }

    /**
     * 用文件添加合同文档
     */
    public static ResultData documentAddbyfile(Long contractId, String url, String fileType, String fileName) {
        SdkClient sdkClient = new SdkClient(URL, ACCESS_KEY, ACCESS_SECRET);
        try {
            java.net.URL url2 = new java.net.URL(url);
            // 添加合同文档
            DocumentAddByFileRequest request = new DocumentAddByFileRequest(contractId,
                    new StreamFile(url2.openStream()), fileType, fileName);
            String response = sdkClient.service(request);
            SdkResponse<DocumentAddResult> responseObj = JSONUtils.toQysResponse(response, DocumentAddResult.class);
            if (responseObj.getCode() == 0) {
                System.out.println("成功");
                return ResultData.success(null);
            } else {
                System.out.println(responseObj.getMessage());
                return ResultData.warn(ResultCode.SEAL_ERROR, responseObj.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultData.success(null);
    }

    /**
     * 浏览页面
     */
    public static ResultData contractViewurl(Long contractId) {
        SdkClient sdkClient = new SdkClient(URL, ACCESS_KEY, ACCESS_SECRET);
        try {
            ContractViewPageRequest request = new ContractViewPageRequest(contractId);
            String response = sdkClient.service(request);
            SdkResponse<ContractPageResult> responseObj = JSONUtils.toQysResponse(response, ContractPageResult.class);
            if (responseObj.getCode() == 0) {
                ContractPageResult result = responseObj.getResult();
                return ResultData.success(result.getPageUrl());
            } else {
                return ResultData.warn(ResultCode.SEAL_ERROR, responseObj.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultData.success(null);
    }

    /**
     * 合同详情
     */
    public static ResultData contractDetail(Long contractId) {
        SdkClient sdkClient = new SdkClient(URL, ACCESS_KEY, ACCESS_SECRET);
        try {
            // 合同详情
            ContractDetailRequest request = new ContractDetailRequest(contractId);
            String response = sdkClient.service(request);
            SdkResponse<Contract> responseObj = JSONUtils.toQysResponse(response, Contract.class);
            if (responseObj.getCode() == 0) {
                return ResultData.success(responseObj.getResult());
            } else {
                return ResultData.warn(ResultCode.SEAL_ERROR, responseObj.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultData.success(null);
    }
    
    /**
     * 合同详情
     */
    public static ResultData contractDetail(Long contractId, String ACCESS_KEY, String ACCESS_SECRET) {
        SdkClient sdkClient = new SdkClient(URL, ACCESS_KEY, ACCESS_SECRET);
        try {
            // 合同详情
            ContractDetailRequest request = new ContractDetailRequest(contractId);
            String response = sdkClient.service(request);
            SdkResponse<Contract> responseObj = JSONUtils.toQysResponse(response, Contract.class);
            if (responseObj.getCode() == 0) {
                return ResultData.success(responseObj.getResult());
            } else {
                return ResultData.warn(ResultCode.SEAL_ERROR, responseObj.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultData.success(null);
    }

    /**
     * 下载合同文档
     *
     * @param documentId 合同文档ID
     */
    public static void documentDownload(HttpServletResponse response, String documentId) {
        OutputStream outputStream = null;
        try {
            SdkClient sdkClient = new SdkClient(URL, ACCESS_KEY, ACCESS_SECRET);
            // 下载合同文档
            DocumentDownloadRequest request = new DocumentDownloadRequest(Long.parseLong(documentId));
            outputStream = response.getOutputStream();
            sdkClient.download(request, outputStream);
            IOUtils.safeClose(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    

    /**
     * 下载合同文档
     *
     * @param documentId 合同文档ID
     */
    public static void documentDownload(HttpServletResponse response, String documentId, String ACCESS_KEY, String ACCESS_SECRET) {
        OutputStream outputStream = null;
        try {
            SdkClient sdkClient = new SdkClient(URL, ACCESS_KEY, ACCESS_SECRET);
            // 下载合同文档
            DocumentDownloadRequest request = new DocumentDownloadRequest(Long.parseLong(documentId));
            outputStream = response.getOutputStream();
            sdkClient.download(request, outputStream);
            IOUtils.safeClose(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取合同列表
     */
    public static ResultData contractList(HttpServletRequest requestParam) {
        String status = requestParam.getParameter("status");
        Integer pageNoInt = requestParam.getParameter("pageNo") != null
                ? (Integer.parseInt(requestParam.getParameter("pageNo")) - 1)
                * (requestParam.getParameter("pageSize") != null
                ? Integer.parseInt(requestParam.getParameter("pageSize"))
                : 10)
                : 0;
        Integer pageSizeInt = requestParam.getParameter("pageSize") != null
                ? Integer.parseInt(requestParam.getParameter("pageSize"))
                : 10;
        SdkClient sdkClient = new SdkClient(URL, ACCESS_KEY, ACCESS_SECRET);
        // 创建请求对象
        ContractListRequest request = new ContractListRequest();
        request.setSelectOffset(pageNoInt);
        request.setSelectLimit(pageSizeInt);
        request.setStatus(status != null ? status : null);
        // 解析请求结果
        String response = sdkClient.service(request);
        SdkResponse<ContractListResult> qysResponse = JSONUtils.toQysResponse(response, ContractListResult.class);
        if (qysResponse.getCode() == 0) {
            ContractListResult contractListResult = qysResponse.getResult();
            System.out.println("合同列表查询，数量:" + contractListResult.getTotalCount());
            Integer nowCount = contractListResult.getTotalCount() - pageNoInt;
            Integer forI = null;
            if (nowCount < pageSizeInt) {
                forI = nowCount;
            } else {
                forI = pageSizeInt;
            }
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            for (int i = 0; i < forI; i++) {
                System.out.println(contractListResult.getList().get(i).getId());
                Contract contract = contractListResult.getList().get(i);
                try {
                    Map<String, String> map = BeanUtils.describe(contract);
                    list.add(map);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
            ResultData data = new ResultData();
            Map<String, Object> resMap = new HashMap<String, Object>();
            resMap.put("list", list);
            resMap.put("totalCount", contractListResult.getTotalCount());
            data.setData(resMap);
            return ResultData.success(data);
        } else {
            return ResultData.warn(ResultCode.SEAL_ERROR, qysResponse.getMessage());
        }
    }

    /**
     * 印章列表
     */
    public static SealListResult sealList() {
        SealListResult sealList = null;
        SdkClient sdkClient = new SdkClient(URL, ACCESS_KEY, ACCESS_SECRET);
        // 印章列表查询
        SealListRequest request = new SealListRequest();
        String response = sdkClient.service(request);
        SdkResponse<SealListResult> responseObj = JSONUtils.toQysResponse(response, SealListResult.class);
        if (responseObj.getCode() == 0) {
            sealList = responseObj.getResult();
        } else {
            System.out.println("印章列表失败，错误码:" + responseObj.getCode() + "，错误信息:{}" + responseObj.getMessage());
        }
        return sealList;
    }

    /**
     * 印章图片
     *
     * @param sealId 印章ID
     */
    public static void sealImage(HttpServletResponse response, String sealId) {
        OutputStream outputStream = null;
        try {
            SdkClient sdkClient = new SdkClient(URL, ACCESS_KEY, ACCESS_SECRET);
            // 印章图片下载
            SealImageRequest request = new SealImageRequest(2391453670764376075L);
            outputStream = response.getOutputStream();
            sdkClient.download(request, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 创建印章
     *
     * @param sealName 修改的印章名称
     */
    public static ResultData sealCreate(String sealName) {
        SdkClient sdkClient = new SdkClient(URL, ACCESS_KEY, ACCESS_SECRET);
        // 生成印章
        SealAutoCreateRequest request = new SealAutoCreateRequest();
        request.setName(sealName);
        String response = sdkClient.service(request);
        SdkResponse<?> responseObj = JSONUtils.toQysResponse(response);
        if (!responseObj.getCode().equals(0)) {
            return ResultData.warn(ResultCode.SEAL_ERROR, responseObj.getMessage());
        } else {
            return ResultData.success(null);
        }
    }

    /**
     * 编辑印章
     *
     * @param sealId   印章ID
     * @param sealName 修改的印章名称
     */
    public static ResultData sealEdit(Long sealId, String sealName) {
        SdkClient sdkClient = new SdkClient(URL, ACCESS_KEY, ACCESS_SECRET);
        SealEditRequest request = new SealEditRequest();
        request.setSealId(sealId);
        request.setSealName(sealName);
        String response = sdkClient.service(request);
        SdkResponse<?> responseObj = JSONUtils.toQysResponse(response);
        if (!responseObj.getCode().equals(0)) {
            return ResultData.warn(ResultCode.SEAL_ERROR, responseObj.getMessage());
        } else {
            return ResultData.success(null);
        }
    }

    /**
     * 管理印章状态
     *
     * @param sealId 印章ID
     * @param status 操作类型，ENABLE（“启用”）、DISABLE（“禁用”）
     */
    public static ResultData sealStatus(Long sealId, String status) {
        SdkClient sdkClient = new SdkClient(URL, ACCESS_KEY, ACCESS_SECRET);
        SealStatusManageRequest request = new SealStatusManageRequest(sealId, status);
        String response = sdkClient.service(request);
        SdkResponse<?> responseObj = JSONUtils.toQysResponse(response);
        if (!responseObj.getCode().equals(0)) {
            return ResultData.warn(ResultCode.SEAL_ERROR, responseObj.getMessage());
        } else {
            return ResultData.success(null);
        }
    }

    /**
     * 删除印章
     *
     * @param sealId 印章ID
     */
    public static ResultData sealRemove(Long sealId) {
        SdkClient sdkClient = new SdkClient(URL, ACCESS_KEY, ACCESS_SECRET);
        SealRemoveRequest request = new SealRemoveRequest(sealId);
        String response = sdkClient.service(request);
        SdkResponse<?> responseObj = JSONUtils.toQysResponse(response);
        if (!responseObj.getCode().equals(0)) {
            return ResultData.warn(ResultCode.SEAL_ERROR, responseObj.getMessage());
        } else {
            return ResultData.success(null);
        }
    }
}
