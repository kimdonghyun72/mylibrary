package com.library.web.service;

import com.library.web.dao.NoticeDAO;
import com.library.web.vo.NoticeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeDAO noticeDAO;

    @Autowired
    public NoticeServiceImpl(NoticeDAO noticeDAO) {
        this.noticeDAO = noticeDAO;
    }

    @Override
    public List<NoticeVO> getRecentNotices(int limit) throws Exception {
        return noticeDAO.getRecentNotices(limit);
    }
}