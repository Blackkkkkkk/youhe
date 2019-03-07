package com.youhe.utils.pay.sdk.member;

import com.youhe.utils.pay.sdk.member.domain.balanceQuery.BalanceQueryRequest;
import com.youhe.utils.pay.sdk.member.domain.balanceQuery.BalanceQueryResponse;
import com.youhe.utils.pay.sdk.member.domain.transfer.TransferRequest;
import com.youhe.utils.pay.sdk.member.domain.transfer.TransferResponse;
import com.youhe.utils.pay.sdk.member.domain.transferQuery.TransferQueryRequest;
import com.youhe.utils.pay.sdk.member.domain.transferQuery.TransferQueryResponse;
import com.youhe.utils.pay.sdk.member.domain.withdraw.WithdrawRequest;
import com.youhe.utils.pay.sdk.member.domain.withdraw.WithdrawResponse;
import com.youhe.utils.pay.sdk.member.domain.withdrawQuery.WithdrawQueryRequest;
import com.youhe.utils.pay.sdk.member.domain.withdrawQuery.WithdrawQueryResponse;
import com.youhe.utils.pay.sdk.utils.Config;
import com.youhe.utils.pay.sdk.utils.RemoteInvoker;

public class MemberHelper {
    /**
     * 会员内转
     * @param object 订单内容
     * @throws Exception
     */
    public static TransferResponse transfer(TransferRequest request) throws Exception{
        return RemoteInvoker.invoke(request, Config.getMemberTransferUrl(), TransferResponse.class);
    }
    
    /**
     * 会员内转結果查詢
     * @param object 订单内容
     * @throws Exception
     */
    public static TransferQueryResponse transferQuery(TransferQueryRequest request) throws Exception{
        return RemoteInvoker.invoke(request, Config.getMemberTransferQueryUrl(), TransferQueryResponse.class);
    }

    /**
     * 余额查询
     * @param object 订单内容
     * @throws Exception
     */
    public static BalanceQueryResponse balanceQuery(BalanceQueryRequest request) throws Exception{
        return RemoteInvoker.invoke(request, Config.getMemberBalanceQueryUrl(), BalanceQueryResponse.class);
    }
    
    /**
     * 提现
     * @param object 订单内容
     * @throws Exception
     */
    public static WithdrawResponse withdraw(WithdrawRequest request) throws Exception{
        return RemoteInvoker.invoke(request, Config.getMemberWithdrawUrl(), WithdrawResponse.class);
    }
    
    /**
     * 提现
     * @param object 订单内容
     * @throws Exception
     */
    public static WithdrawQueryResponse withdrawQuery(WithdrawQueryRequest request) throws Exception{
        return RemoteInvoker.invoke(request, Config.getMemberWithdrawQueryUrl(), WithdrawQueryResponse.class);
    }
}
